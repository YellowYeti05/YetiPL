package gg.yetisboxxed.yetipl.service;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SelectionService {
    public record Selection(Location pos1, Location pos2) {
        public boolean complete(){ return pos1 != null && pos2 != null && pos1.getWorld().equals(pos2.getWorld()); }
        public boolean contains(Location l) {
            if (!complete() || !pos1.getWorld().equals(l.getWorld())) return false;
            int minX=Math.min(pos1.getBlockX(),pos2.getBlockX()), maxX=Math.max(pos1.getBlockX(),pos2.getBlockX());
            int minY=Math.min(pos1.getBlockY(),pos2.getBlockY()), maxY=Math.max(pos1.getBlockY(),pos2.getBlockY());
            int minZ=Math.min(pos1.getBlockZ(),pos2.getBlockZ()), maxZ=Math.max(pos1.getBlockZ(),pos2.getBlockZ());
            return l.getBlockX()>=minX&&l.getBlockX()<=maxX&&l.getBlockY()>=minY&&l.getBlockY()<=maxY&&l.getBlockZ()>=minZ&&l.getBlockZ()<=maxZ;
        }
    }
    private final Map<UUID,Location> p1=new HashMap<>(), p2=new HashMap<>();
    public void pos1(Player p,Location l){p1.put(p.getUniqueId(),l.clone());}
    public void pos2(Player p,Location l){p2.put(p.getUniqueId(),l.clone());}
    public Selection get(Player p){return new Selection(p1.get(p.getUniqueId()),p2.get(p.getUniqueId()));}
}
