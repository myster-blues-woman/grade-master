package com.example.interfaces;

import com.example.exceptions.UnauthorizedException;
import com.example.models.Module;

import java.util.List;

public interface ModuleService {
    List<Module> getAllModules() throws UnauthorizedException;

    void addModule(Module module) throws UnauthorizedException;

    List<Module> deleteModule(String userName, String name) throws UnauthorizedException;

    void save();
}
