package gg.yetisboxxed.yetipl.diagnostics;

import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public final class ErrorReporter {
    private final JavaPlugin plugin;
    private final Deque<ErrorRecord> recent = new ArrayDeque<>();
    private final int maxRecent;

    public ErrorReporter(JavaPlugin plugin, int maxRecent) {
        this.plugin = plugin;
        this.maxRecent = Math.max(10, maxRecent);
    }

    public synchronized String report(String module, Throwable error) {
        String id = "YPL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String message = error.getMessage() == null ? error.getClass().getSimpleName() : error.getMessage();
        recent.addFirst(new ErrorRecord(id, module, message, Instant.now()));
        while (recent.size() > maxRecent) recent.removeLast();
        plugin.getLogger().log(Level.SEVERE, "[" + id + "] Module '" + module + "' failed: " + message, error);
        return id;
    }

    public synchronized List<ErrorRecord> recent() {
        return new ArrayList<>(recent);
    }
}
