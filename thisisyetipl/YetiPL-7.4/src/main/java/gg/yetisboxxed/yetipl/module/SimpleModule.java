package gg.yetisboxxed.yetipl.module;

public final class SimpleModule implements YetiModule {
    private final String id;
    private final String displayName;
    private boolean enabled;

    public SimpleModule(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    @Override public String id() { return id; }
    @Override public String displayName() { return displayName; }
    @Override public void enable() { enabled = true; }
    @Override public void disable() { enabled = false; }
    @Override public void selfTest() {
        if (!enabled) throw new IllegalStateException(displayName + " is not enabled");
    }
}
