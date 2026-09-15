package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import gg.yetisboxxed.yetipl.util.Items;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class DiscService {
    private final YamlStore store; private final NamespacedKey key;
    public DiscService(JavaPlugin plugin){store=new YamlStore(plugin,"discs.yml");key=new NamespacedKey(plugin,"custom_disc");}
    public void create(String name,String youtube,String sound){String b="discs."+name.toLowerCase();store.yaml().set(b+".youtube",youtube);store.yaml().set(b+".sound",sound);store.save();}
    public Set<String> names(){var s=store.yaml().getConfigurationSection("discs");return s==null?Set.of():s.getKeys(false);}
    public String sound(String name){return store.yaml().getString("discs."+name.toLowerCase()+".sound");}
    public ItemStack item(String name){if(!names().contains(name.toLowerCase()))return null;ItemStack i=Items.named(Material.MUSIC_DISC_13,"YetiPL Disc: "+name,"Source: "+store.yaml().getString("discs."+name.toLowerCase()+".youtube",""));var m=i.getItemMeta();m.getPersistentDataContainer().set(key,PersistentDataType.STRING,name.toLowerCase());i.setItemMeta(m);return i;}
    public String id(ItemStack i){if(i==null||!i.hasItemMeta())return null;return i.getItemMeta().getPersistentDataContainer().get(key,PersistentDataType.STRING);}
}
