package gg.yetisboxxed.yetipl.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public final class Locations {
    private Locations() {}
    public static String encode(Location l) {
        return l.getWorld().getName()+","+l.getX()+","+l.getY()+","+l.getZ()+","+l.getYaw()+","+l.getPitch();
    }
    public static Location decode(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            String[] p=s.split(","); World w=Bukkit.getWorld(p[0]); if(w==null)return null;
            return new Location(w,Double.parseDouble(p[1]),Double.parseDouble(p[2]),Double.parseDouble(p[3]),Float.parseFloat(p[4]),Float.parseFloat(p[5]));
        } catch (Exception e) { return null; }
    }
    public static String blockKey(Location l) { return l.getWorld().getName()+":"+l.getBlockX()+":"+l.getBlockY()+":"+l.getBlockZ(); }
}
