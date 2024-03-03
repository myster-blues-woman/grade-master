package com.example.services.test.fakes;

import com.example.interfaces.ModuleRepository;
import com.example.models.Module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryModuleRepository implements ModuleRepository {

    private List<Module> modules = new ArrayList<>();

    @Override
    public List<Module> load() {
        return modules;
    }

    @Override
    public void save(List<Module> modules) {
        // Stellt sicher, dass `this.modules` eine neue Instanz von ArrayList ist,
        // die die übergebenen Module enthält.
        this.modules = new ArrayList<>(modules);
    }

    @Override
    public void save() {
        // Do nothingg
    }

    @Override
    public List<Module> deleteModule(String moduleName) {
        List<Module> mutableModules = new ArrayList<>(load());

        boolean removed = mutableModules.removeIf(module -> module.getName().equals(moduleName));

        if (removed) {
            save(mutableModules);
        }

        return mutableModules;
    }

}
