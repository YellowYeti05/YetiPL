package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.util.Items;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class TrimService {
    private final NamespacedKey key; private final List<String> custom=List.of("yeti","boxed","royal","glacier","golden");
    public TrimService(JavaPlugin plugin){key=new NamespacedKey(plugin,"custom_trim");}
    public List<String> custom(){return custom;}
    public ItemStack template(String id){if(!custom.contains(id.toLowerCase()))return null;ItemStack i=Items.named(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,"YetiPL "+id.toUpperCase()+" Trim Template","Custom trim template • resource pack required");var m=i.getItemMeta();m.getPersistentDataContainer().set(key,PersistentDataType.STRING,id.toLowerCase());i.setItemMeta(m);return i;}
}
