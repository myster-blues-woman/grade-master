package com.example.repositories;

import com.example.interfaces.ModuleRepository;
import com.example.models.Module;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModuleRepositoryImpl implements ModuleRepository {
    private final String jsonFilePath = "./modules.json";
    private final ObjectMapper mapper = new ObjectMapper();
    private boolean tainted = false;
    private List<Module> modules;

    public ModuleRepositoryImpl() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<Module> loadModules() {
        if (!tainted && modules != null)
            return modules;

        File file = new File(jsonFilePath);
        if (!file.exists()) {
            // If the file doesn't exist, create an empty list and return it
            try {
                // Ensure parent directories exist
                file.getParentFile().mkdirs();

                // Create the file
                file.createNewFile();

                // Write an empty list to the new file
                mapper.writeValue(file, new ArrayList<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
            modules = new ArrayList<>();
        } else {
            try {
                modules = mapper.readValue(file, new TypeReference<List<Module>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return modules;
    }

    @Override
    public void saveModules() {
        try {
            mapper.writeValue(new File(jsonFilePath), modules);
            tainted = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveModules(List<Module> modules) {
        try {
            mapper.writeValue(new File(jsonFilePath), modules);
            tainted = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
