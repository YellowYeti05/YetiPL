package gg.yetisboxxed.yetipl.module;

public interface YetiModule {
    String id();
    String displayName();
    void enable() throws Exception;
    void disable() throws Exception;
    default void selfTest() throws Exception {}
}
