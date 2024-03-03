package com.example.interfaces;

import com.example.models.Module;

import java.util.List;

public interface ModuleRepository extends BaseRepository<Module> {
    List<Module> load();

    void save(List<Module> modules);

    void save();

    List<Module> deleteModule(String name);
}