package gg.yetisboxxed.yetipl.service;

import gg.yetisboxxed.yetipl.storage.YamlStore;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

@SuppressWarnings("deprecation")
public final class ScoreboardService {
    private final JavaPlugin plugin;
    private final CoinsService coins;
    private final PresetService presets;
    private final RankService ranks;
    private final YamlStore claims;
    private BukkitTask scoreboardTask;
    private BukkitTask rewardTask;

    public ScoreboardService(JavaPlugin plugin, CoinsService coins, PresetService presets, RankService ranks) {
        this.plugin = plugin;
        this.coins = coins;
        this.presets = presets;
        this.ranks = ranks;
        this.claims = new YamlStore(plugin, "playtime-rewards.yml");
    }

    public void start() {
        if (scoreboardTask != null) return;
        scoreboardTask = Bukkit.getScheduler().runTaskTimer(plugin,
                () -> Bukkit.getOnlinePlayers().forEach(this::update), 40L, 40L);
        rewardTask = Bukkit.getScheduler().runTaskTimer(plugin, this::rewardPlaytime, 1200L, 1200L);
    }

    public void stop() {
        if (scoreboardTask != null) scoreboardTask.cancel();
        if (rewardTask != null) rewardTask.cancel();
        scoreboardTask = null;
        rewardTask = null;
    }

    public void update(Player p) {
        if (!plugin.getConfig().getBoolean("scoreboard.enabled", true)) return;
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = sb.registerNewObjective("yetipl", "dummy", "§6✦ YETIBOXXED ✦");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        int n = 8;
        o.getScore("§e" + p.getName()).setScore(n--);
        o.getScore("§7Rank: §f" + ranks.rank(p.getUniqueId())).setScore(n--);
        o.getScore("§7Yeti Coins: §6" + (coins.infinite(p) ? "∞" : coins.balance(p.getUniqueId())) + " ❄").setScore(n--);
        o.getScore("§7Preset: §f" + (presets.owns(p.getUniqueId()) ? "Lv " + presets.level(p.getUniqueId()) : "None")).setScore(n--);
        o.getScore("§7Online: §f" + Bukkit.getOnlinePlayers().size()).setScore(n--);
        o.getScore("§7Ping: §f" + p.getPing() + "ms").setScore(n--);
        o.getScore("§eJava 1.8+ • Bedrock").setScore(n--);
        o.getScore("§6yetisboxxed.minehut.gg").setScore(n);
        p.setScoreboard(sb);
    }

    private void rewardPlaytime() {
        long[] milestones = {60, 300, 600, 1440, 6000};
        long[] rewards = {10, 25, 50, 100, 500};
        boolean changed = false;
        for (Player p : Bukkit.getOnlinePlayers()) {
            long minutes = p.getStatistic(Statistic.PLAY_ONE_MINUTE) / 1200L;
            for (int i = 0; i < milestones.length; i++) {
                if (minutes < milestones[i]) continue;
                String path = "claimed." + p.getUniqueId() + "." + milestones[i];
                if (claims.yaml().getBoolean(path, false)) continue;
                coins.add(p.getUniqueId(), rewards[i], "playtime " + milestones[i] + "m");
                claims.yaml().set(path, true);
                changed = true;
            }
        }
        if (changed) claims.save();
    }
}
