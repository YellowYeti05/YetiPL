package gg.yetisboxxed.yetipl.module;

import gg.yetisboxxed.yetipl.diagnostics.ErrorReporter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ModuleManager {
    private final ErrorReporter errors;
    private final Map<String, ManagedModule> modules = new LinkedHashMap<>();

    public ModuleManager(ErrorReporter errors) {
        this.errors = errors;
    }

    public void register(YetiModule module) {
        modules.put(module.id(), new ManagedModule(module, ModuleState.DISABLED, null));
    }

    public void enable(String id) {
        ManagedModule managed = modules.get(id);
        if (managed == null) return;
        try {
            managed.module().enable();
            modules.put(id, managed.withState(ModuleState.ENABLED, null));
        } catch (Throwable t) {
            String errorId = errors.report(id, t);
            modules.put(id, managed.withState(ModuleState.FAILED, errorId));
        }
    }

    public void disableAll() {
        for (ManagedModule managed : List.copyOf(modules.values())) {
            if (managed.state() != ModuleState.ENABLED) continue;
            try {
                managed.module().disable();
                modules.put(managed.module().id(), managed.withState(ModuleState.DISABLED, null));
            } catch (Throwable t) {
                String errorId = errors.report(managed.module().id(), t);
                modules.put(managed.module().id(), managed.withState(ModuleState.FAILED, errorId));
            }
        }
    }

    public Map<String, ManagedModule> snapshot() {
        return Map.copyOf(modules);
    }
}
