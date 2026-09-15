package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import gg.yetisboxxed.yetipl.util.Locations;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class PresetService {
    public static final String WORLD_NAME="yetipl_presets";
    private static final int[] SIZES={64,80,96,128,160,192,224,256,320,384};
    private static final long[] COSTS={2500,1500,2500,4000,6000,9000,13000,18000,25000,35000};
    private final JavaPlugin plugin; private final YamlStore store; private final YamlStore sessions; private final CoinsService coins;
    private World world;

    public PresetService(JavaPlugin plugin, CoinsService coins){this.plugin=plugin;this.coins=coins;store=new YamlStore(plugin,"presets.yml");sessions=new YamlStore(plugin,"preset-sessions.yml");}

    public void enable(){ world=Bukkit.getWorld(WORLD_NAME); if(world==null){ WorldCreator c=new WorldCreator(WORLD_NAME); c.environment(World.Environment.NORMAL); c.generator(new VoidGenerator()); world=Bukkit.createWorld(c);} if(world!=null){world.setSpawnLocation(0,65,0);world.setGameRule(GameRule.DO_MOB_SPAWNING,false);world.setGameRule(GameRule.DO_WEATHER_CYCLE,false);} }
    public World world(){return world;}
    public boolean owns(UUID id){return store.yaml().contains("players."+id+".index");}
    public int level(UUID id){return Math.max(1,Math.min(10,store.yaml().getInt("players."+id+".level",1)));}
    public int size(UUID id){return SIZES[level(id)-1];}
    public long nextUpgradeCost(UUID id){int l=level(id);return l>=10?0:COSTS[l];}
    public long purchaseCost(){return COSTS[0];}
    public Location center(UUID id){if(!owns(id)||world==null)return null;int index=store.yaml().getInt("players."+id+".index");int spacing=1000;int x=(index%1000)*spacing;int z=(index/1000)*spacing;return new Location(world,x+0.5,65,z+0.5);}
    public UUID ownerAt(Location l){if(l==null||l.getWorld()!=world)return null;var sec=store.yaml().getConfigurationSection("players");if(sec==null)return null;for(String k:sec.getKeys(false)){try{UUID id=UUID.fromString(k);Location c=center(id);if(c!=null&&Math.abs(l.getX()-c.getX())<=size(id)/2.0&&Math.abs(l.getZ()-c.getZ())<=size(id)/2.0)return id;}catch(Exception ignored){}}return null;}
    public boolean canVisit(Player p,UUID owner){if(p.getUniqueId().equals(owner)||p.hasPermission("yetipl.presets.admin"))return true;String base="players."+owner+".";return store.yaml().getBoolean(base+"public",false)||store.yaml().getStringList(base+"trusted").contains(p.getUniqueId().toString())||store.yaml().getStringList(base+"hypertrusted").contains(p.getUniqueId().toString());}
    public boolean canEdit(Player p,UUID owner){if(p.getUniqueId().equals(owner)||p.hasPermission("yetipl.presets.admin"))return true;return owns(p.getUniqueId())&&store.yaml().getStringList("players."+owner+".hypertrusted").contains(p.getUniqueId().toString());}
    public boolean buy(Player p){if(owns(p.getUniqueId()))return false;if(!coins.spend(p,purchaseCost(),"preset purchase"))return false;int index=store.yaml().getInt("next-index",0);store.yaml().set("next-index",index+1);store.yaml().set("players."+p.getUniqueId()+".index",index);store.yaml().set("players."+p.getUniqueId()+".level",1);store.save();generatePlatform(p.getUniqueId());return true;}
    public boolean upgrade(Player p){if(!owns(p.getUniqueId()))return false;int l=level(p.getUniqueId());if(l>=10)return false;long cost=COSTS[l];if(!coins.spend(p,cost,"preset level "+(l+1)))return false;store.yaml().set("players."+p.getUniqueId()+".level",l+1);store.save();applyBorder(p,p.getUniqueId());return true;}
    public void trust(UUID owner,UUID target,boolean hyper){String path="players."+owner+"."+(hyper?"hypertrusted":"trusted");List<String> list=new ArrayList<>(store.yaml().getStringList(path));if(!list.contains(target.toString()))list.add(target.toString());store.yaml().set(path,list);store.save();}
    public void untrust(UUID owner,UUID target,boolean hyper){String path="players."+owner+"."+(hyper?"hypertrusted":"trusted");List<String> list=new ArrayList<>(store.yaml().getStringList(path));list.remove(target.toString());store.yaml().set(path,list);store.save();}
    public void setPublic(UUID owner,boolean value){store.yaml().set("players."+owner+".public",value);store.save();}
    public void reset(UUID owner){Location c=center(owner);if(c==null)return;int radius=size(owner)/2;int minChunkX=(c.getBlockX()-radius)>>4,maxChunkX=(c.getBlockX()+radius)>>4,minChunkZ=(c.getBlockZ()-radius)>>4,maxChunkZ=(c.getBlockZ()+radius)>>4;Deque<int[]> queue=new ArrayDeque<>();for(int x=minChunkX;x<=maxChunkX;x++)for(int z=minChunkZ;z<=maxChunkZ;z++)queue.add(new int[]{x,z});new org.bukkit.scheduler.BukkitRunnable(){@Override public void run(){int budget=6;while(budget-->0&&!queue.isEmpty()){int[] q=queue.removeFirst();world.regenerateChunk(q[0],q[1]);}if(queue.isEmpty()){generatePlatform(owner);cancel();}}}.runTaskTimer(plugin,1L,1L); }
    private void generatePlatform(UUID owner){Location c=center(owner);if(c==null)return;for(int x=-4;x<=4;x++)for(int z=-4;z<=4;z++)world.getBlockAt(c.getBlockX()+x,64,c.getBlockZ()+z).setType(Material.SMOOTH_STONE,false);}
    public void enter(Player p,UUID owner){if(!canVisit(p,owner))return;saveSession(p);Location c=center(owner);p.teleport(c);p.setGameMode(canEdit(p,owner)?GameMode.CREATIVE:GameMode.ADVENTURE);p.getInventory().clear();p.setLevel(0);p.setExp(0);applyBorder(p,owner);}
    public void leave(Player p){restoreSession(p);try{p.setWorldBorder(null);}catch(Throwable ignored){} }
    private void saveSession(Player p){String base="sessions."+p.getUniqueId();if(sessions.yaml().contains(base+"active"))return;sessions.yaml().set(base+"active",true);sessions.yaml().set(base+"location",Locations.encode(p.getLocation()));sessions.yaml().set(base+"gamemode",p.getGameMode().name());sessions.yaml().set(base+"inventory",Arrays.asList(p.getInventory().getContents()));sessions.yaml().set(base+"armor",Arrays.asList(p.getInventory().getArmorContents()));sessions.yaml().set(base+"offhand",p.getInventory().getItemInOffHand());sessions.yaml().set(base+"level",p.getLevel());sessions.yaml().set(base+"exp",p.getExp());sessions.save();}
    @SuppressWarnings("unchecked") private void restoreSession(Player p){String base="sessions."+p.getUniqueId();if(!sessions.yaml().getBoolean(base+"active",false))return;p.getInventory().clear();List<ItemStack> inv=(List<ItemStack>)(List<?>)sessions.yaml().getList(base+"inventory",List.of());p.getInventory().setContents(inv.toArray(ItemStack[]::new));List<ItemStack> armor=(List<ItemStack>)(List<?>)sessions.yaml().getList(base+"armor",List.of());if(armor.size()==4)p.getInventory().setArmorContents(armor.toArray(ItemStack[]::new));ItemStack off=sessions.yaml().getItemStack(base+"offhand");if(off!=null)p.getInventory().setItemInOffHand(off);try{p.setGameMode(GameMode.valueOf(sessions.yaml().getString(base+"gamemode","SURVIVAL")));}catch(Exception ignored){}p.setLevel(sessions.yaml().getInt(base+"level",0));p.setExp((float)sessions.yaml().getDouble(base+"exp",0));Location back=Locations.decode(sessions.yaml().getString(base+"location"));if(back!=null&&back.getWorld()!=world)p.teleport(back);sessions.yaml().set(base,null);sessions.save();}
    public void applyBorder(Player p,UUID owner){Location c=center(owner);if(c==null)return;try{WorldBorder b=Bukkit.createWorldBorder();b.setCenter(c);b.setSize(size(owner));p.setWorldBorder(b);}catch(Throwable ignored){} }
    public boolean insideOwn(Player p){return world!=null&&p.getWorld()==world&&p.getUniqueId().equals(ownerAt(p.getLocation()));}
    public int[] sizes(){return SIZES.clone();}
    public long[] costs(){return COSTS.clone();}

    private static final class VoidGenerator extends ChunkGenerator {
        @Override public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome){return createChunkData(world);}
    }
}
