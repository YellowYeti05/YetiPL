package gg.yetisboxxed.yetipl.service;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class LagProtectorService {
    public enum Level{NORMAL,WARNING,HIGH,EMERGENCY}
    private final JavaPlugin plugin; private volatile Level level=Level.NORMAL; private BukkitTask task;
    public LagProtectorService(JavaPlugin plugin){this.plugin=plugin;}
    public void start(){if(task!=null)return;task=Bukkit.getScheduler().runTaskTimer(plugin,()->{double tps=Bukkit.getTPS()[0];Level old=level;level=tps>=18?Level.NORMAL:tps>=16?Level.WARNING:tps>=13?Level.HIGH:Level.EMERGENCY;if(old!=level)plugin.getLogger().warning("Lag Protector: "+old+" -> "+level+" (TPS "+String.format("%.2f",tps)+")");},20L,100L);}
    public void stop(){if(task!=null)task.cancel();task=null;}
    public Level level(){return level;}
    public boolean blockExplosions(){return level==Level.HIGH||level==Level.EMERGENCY;}
}
