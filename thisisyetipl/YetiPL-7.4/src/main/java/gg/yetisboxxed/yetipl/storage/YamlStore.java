package gg.yetisboxxed.yetipl.storage;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class YamlStore {
    private final JavaPlugin plugin;
    private final File file;
    private YamlConfiguration yaml;

    public YamlStore(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), name);
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
        this.yaml = YamlConfiguration.loadConfiguration(file);
    }

    public synchronized YamlConfiguration yaml() { return yaml; }

    public synchronized void reload() { this.yaml = YamlConfiguration.loadConfiguration(file); }

    public synchronized void save() {
        File tmp = new File(file.getParentFile(), file.getName() + ".tmp");
        try {
            yaml.save(tmp);
            try {
                Files.move(tmp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException atomicFailure) {
                Files.move(tmp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save " + file.getName() + ": " + e.getMessage());
        }
    }
}
