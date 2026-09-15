package gg.yetisboxxed.yetipl.service;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.*;

public final class WorldEditService {
    private record Change(Location location, BlockData before, BlockData after){}
    private record Clip(int x,int y,int z,BlockData data){}
    private final SelectionService selections;
    private final Map<UUID,Deque<List<Change>>> undo=new HashMap<>(), redo=new HashMap<>();
    private final Map<UUID,List<Clip>> clipboard=new HashMap<>();
    private final Map<UUID,Location> clipOrigin=new HashMap<>();
    private final int maxBlocks;
    public WorldEditService(SelectionService selections,int maxBlocks){this.selections=selections;this.maxBlocks=Math.max(1000,maxBlocks);}
    public int set(Player p,Material material){SelectionService.Selection s=selections.get(p);if(!s.complete())return -1;List<Block> blocks=blocks(s);if(blocks.size()>maxBlocks)return -2;List<Change> changes=new ArrayList<>();BlockData after=material.createBlockData();for(Block b:blocks){BlockData before=b.getBlockData().clone();if(before.matches(after))continue;changes.add(new Change(b.getLocation(),before,after.clone()));b.setBlockData(after,false);}push(p,changes);return changes.size();}
    public int replace(Player p,Material from,Material to){SelectionService.Selection s=selections.get(p);if(!s.complete())return -1;List<Block> blocks=blocks(s);if(blocks.size()>maxBlocks)return -2;List<Change> changes=new ArrayList<>();BlockData after=to.createBlockData();for(Block b:blocks)if(b.getType()==from){BlockData before=b.getBlockData().clone();changes.add(new Change(b.getLocation(),before,after.clone()));b.setBlockData(after,false);}push(p,changes);return changes.size();}
    public int copy(Player p){SelectionService.Selection s=selections.get(p);if(!s.complete())return -1;List<Block> blocks=blocks(s);if(blocks.size()>maxBlocks)return -2;Location origin=p.getLocation().getBlock().getLocation();List<Clip> clips=new ArrayList<>();for(Block b:blocks)clips.add(new Clip(b.getX()-origin.getBlockX(),b.getY()-origin.getBlockY(),b.getZ()-origin.getBlockZ(),b.getBlockData().clone()));clipboard.put(p.getUniqueId(),clips);clipOrigin.put(p.getUniqueId(),origin);return clips.size();}
    public int paste(Player p){List<Clip> clips=clipboard.get(p.getUniqueId());if(clips==null)return -1;Location origin=p.getLocation().getBlock().getLocation();List<Change> changes=new ArrayList<>();for(Clip c:clips){Block b=origin.clone().add(c.x,c.y,c.z).getBlock();BlockData before=b.getBlockData().clone();changes.add(new Change(b.getLocation(),before,c.data.clone()));b.setBlockData(c.data,false);}push(p,changes);return changes.size();}
    public int undo(Player p){Deque<List<Change>> u=undo.computeIfAbsent(p.getUniqueId(),k->new ArrayDeque<>());if(u.isEmpty())return 0;List<Change> c=u.pop();for(Change x:c)x.location.getBlock().setBlockData(x.before,false);redo.computeIfAbsent(p.getUniqueId(),k->new ArrayDeque<>()).push(c);return c.size();}
    public int redo(Player p){Deque<List<Change>> r=redo.computeIfAbsent(p.getUniqueId(),k->new ArrayDeque<>());if(r.isEmpty())return 0;List<Change> c=r.pop();for(Change x:c)x.location.getBlock().setBlockData(x.after,false);undo.computeIfAbsent(p.getUniqueId(),k->new ArrayDeque<>()).push(c);return c.size();}
    private void push(Player p,List<Change> c){Deque<List<Change>> u=undo.computeIfAbsent(p.getUniqueId(),k->new ArrayDeque<>());u.push(c);while(u.size()>10)u.removeLast();redo.computeIfAbsent(p.getUniqueId(),k->new ArrayDeque<>()).clear();}
    private List<Block> blocks(SelectionService.Selection s){Location a=s.pos1(),b=s.pos2();long volume=(long)(Math.abs(a.getBlockX()-b.getBlockX())+1)*(Math.abs(a.getBlockY()-b.getBlockY())+1)*(Math.abs(a.getBlockZ()-b.getBlockZ())+1);if(volume>maxBlocks)return Collections.nCopies(maxBlocks+1,a.getBlock());List<Block> out=new ArrayList<>((int)volume);for(int x=Math.min(a.getBlockX(),b.getBlockX());x<=Math.max(a.getBlockX(),b.getBlockX());x++)for(int y=Math.min(a.getBlockY(),b.getBlockY());y<=Math.max(a.getBlockY(),b.getBlockY());y++)for(int z=Math.min(a.getBlockZ(),b.getBlockZ());z<=Math.max(a.getBlockZ(),b.getBlockZ());z++)out.add(a.getWorld().getBlockAt(x,y,z));return out;}
}
