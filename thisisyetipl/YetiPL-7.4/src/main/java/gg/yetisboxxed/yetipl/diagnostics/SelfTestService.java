package gg.yetisboxxed.yetipl.diagnostics;

import gg.yetisboxxed.yetipl.module.ManagedModule;
import gg.yetisboxxed.yetipl.module.ModuleManager;
import gg.yetisboxxed.yetipl.module.ModuleState;

import java.util.ArrayList;
import java.util.List;

public final class SelfTestService {
    private final ModuleManager modules;

    public SelfTestService(ModuleManager modules) {
        this.modules = modules;
    }

    public List<String> run() {
        List<String> failures = new ArrayList<>();
        for (ManagedModule managed : modules.snapshot().values()) {
            if (managed.state() != ModuleState.ENABLED) continue;
            try {
                managed.module().selfTest();
            } catch (Throwable t) {
                failures.add(managed.module().displayName() + ": " + t.getMessage());
            }
        }
        return failures;
    }
}
