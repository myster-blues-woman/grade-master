package com.example.services.test.fakes;

import com.example.interfaces.ModuleRepository;
import com.example.models.Module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryModuleRepository implements ModuleRepository {

    private List<Module> modules = new ArrayList<>();

    @Override
    public List<Module> loadModules() {
        return modules;
    }

    @Override
    public void saveModules(List<Module> modules) {
        // Stellt sicher, dass `this.modules` eine neue Instanz von ArrayList ist,
        // die die übergebenen Module enthält.
        this.modules = new ArrayList<>(modules);
    }

    @Override
    public void saveModules() {
        // Do nothingg
    }

    @Override
    public List<Module> deleteModule(String moduleName) {
        List<Module> mutableModules = new ArrayList<>(loadModules());

        boolean removed = mutableModules.removeIf(module -> module.getName().equals(moduleName));

        if (removed) {
            saveModules(mutableModules);
        }

        return mutableModules;
    }

}
