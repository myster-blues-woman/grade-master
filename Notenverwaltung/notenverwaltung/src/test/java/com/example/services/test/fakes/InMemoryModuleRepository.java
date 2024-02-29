package com.example.services.test.fakes;

import com.example.interfaces.ModuleRepository;
import com.example.models.Module;

import java.util.ArrayList;
import java.util.List;

public class InMemoryModuleRepository implements ModuleRepository {

    private List<Module> modules = new ArrayList<>();

    @Override
    public List<Module> loadModules() {
        return modules;
    }

    @Override
    public void saveModules(List<Module> modules) {
        this.modules = modules;
    }

    @Override
    public void saveModules() {
        // Do nothingg
    }
}
