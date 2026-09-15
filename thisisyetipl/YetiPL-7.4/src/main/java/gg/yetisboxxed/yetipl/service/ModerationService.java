package gg.yetisboxxed.yetipl.service;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public final class ModerationService {
    private final Set<UUID> frozen=new HashSet<>(), vanished=new HashSet<>();
    private final Map<UUID,Location> spectateBack=new HashMap<>();
    private final Map<UUID,GameMode> spectateMode=new HashMap<>();
    public boolean frozen(UUID id){return frozen.contains(id);}
    public boolean toggleFreeze(Player p){if(frozen.remove(p.getUniqueId()))return false;frozen.add(p.getUniqueId());return true;}
    public boolean vanished(Player p){return vanished.contains(p.getUniqueId());}
    public boolean toggleVanish(Player p){boolean on;if(vanished.remove(p.getUniqueId()))on=false;else{vanished.add(p.getUniqueId());on=true;}for(Player viewer:Bukkit.getOnlinePlayers()){if(viewer.equals(p))continue;if(on&&!viewer.hasPermission("yetipl.vanish.see"))viewer.hidePlayer(Bukkit.getPluginManager().getPlugin("YetiPL"),p);else viewer.showPlayer(Bukkit.getPluginManager().getPlugin("YetiPL"),p);}return on;}
    public void spectate(Player staff,Player target){spectateBack.put(staff.getUniqueId(),staff.getLocation().clone());spectateMode.put(staff.getUniqueId(),staff.getGameMode());staff.setGameMode(GameMode.SPECTATOR);staff.teleport(target);}
    public void stopSpectate(Player staff){Location l=spectateBack.remove(staff.getUniqueId());GameMode g=spectateMode.remove(staff.getUniqueId());if(g!=null)staff.setGameMode(g);if(l!=null)staff.teleport(l);}
}
