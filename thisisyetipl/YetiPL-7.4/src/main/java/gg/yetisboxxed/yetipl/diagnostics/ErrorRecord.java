package gg.yetisboxxed.yetipl.diagnostics;

import java.time.Instant;

public record ErrorRecord(String id, String module, String message, Instant timestamp) {}
