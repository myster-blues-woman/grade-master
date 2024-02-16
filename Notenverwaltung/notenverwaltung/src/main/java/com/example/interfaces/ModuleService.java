package com.example.interfaces;

import com.example.models.Module;

import java.util.List;

public interface ModuleService {
    List<Module> getAllModules();
    void addModule(Module module);
}
