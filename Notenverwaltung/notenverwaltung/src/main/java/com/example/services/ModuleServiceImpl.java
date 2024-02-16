package com.example.services;

import com.example.interfaces.ModuleRepository;
import com.example.interfaces.ModuleService;
import com.example.models.Module;

import java.util.List;

public class ModuleServiceImpl implements ModuleService {
    private ModuleRepository moduleRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public List<Module> getAllModules() {
        return moduleRepository.loadModules();
    }

    @Override
    public void addModule(Module module) {
        moduleRepository.loadModules().add(module);
        moduleRepository.saveModules();
    }
}
