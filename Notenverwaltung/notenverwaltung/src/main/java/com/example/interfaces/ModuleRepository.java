package com.example.interfaces;

import com.example.models.Module;

import java.util.List;

public interface ModuleRepository {
    List<Module> loadModules();

    void saveModules(List<Module> modules);

    void saveModules();

    List<Module> deleteModule(String name);
}
