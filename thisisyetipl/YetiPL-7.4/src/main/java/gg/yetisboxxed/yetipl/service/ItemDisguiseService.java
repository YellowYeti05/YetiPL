package gg.yetisboxxed.yetipl.service;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public final class ItemDisguiseService {
    private final Map<UUID,Material> disguises=new HashMap<>();
    public void set(Player p,Material material){disguises.put(p.getUniqueId(),material);refresh(p);}
    public void reset(Player p){disguises.remove(p.getUniqueId());for(Player viewer:Bukkit.getOnlinePlayers())viewer.sendEquipmentChange(p,EquipmentSlot.HAND,p.getInventory().getItemInMainHand());}
    public Material get(Player p){return disguises.get(p.getUniqueId());}
    public void refresh(Player p){Material m=disguises.get(p.getUniqueId());if(m==null)return;ItemStack fake=new ItemStack(m);for(Player viewer:Bukkit.getOnlinePlayers())if(!viewer.equals(p))viewer.sendEquipmentChange(p,EquipmentSlot.HAND,fake);}
}
