package gg.yetisboxxed.yetipl.service;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class RulesService {
    private final JavaPlugin plugin;
    public RulesService(JavaPlugin plugin){this.plugin=plugin;}
    public List<String> rules(){return plugin.getConfig().getStringList("rules.lines");}
    public String discord(){return plugin.getConfig().getString("discord.url","https://discord.gg/pvxYxHkpE");}
}
