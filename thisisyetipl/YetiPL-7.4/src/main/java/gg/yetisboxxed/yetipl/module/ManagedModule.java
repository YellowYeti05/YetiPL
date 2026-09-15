package gg.yetisboxxed.yetipl.module;

public record ManagedModule(YetiModule module, ModuleState state, String failureId) {
    public ManagedModule withState(ModuleState newState, String newFailureId) {
        return new ManagedModule(module, newState, newFailureId);
    }
}
