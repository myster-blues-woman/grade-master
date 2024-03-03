package com.example.services;

import com.example.exceptions.UnauthorizedException;
import com.example.interfaces.AuthenticatedUserAccessor;
import com.example.interfaces.ModuleRepository;
import com.example.interfaces.ModuleService;
import com.example.models.Module;
import com.example.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class ModuleServiceImpl implements ModuleService {
    private ModuleRepository moduleRepository;
    private AuthenticatedUserAccessor authenticatedUserAccessor;

    public ModuleServiceImpl(ModuleRepository moduleRepository, AuthenticatedUserAccessor authenticatedUserAccessor) {
        this.moduleRepository = moduleRepository;
        this.authenticatedUserAccessor = authenticatedUserAccessor;
    }

    @Override
    public List<Module> getAllModules() throws UnauthorizedException {
        User authenticatedUser = authenticatedUserAccessor.getAuthenticatedUser();
        if (authenticatedUser == null)
            throw new UnauthorizedException();

        return moduleRepository.loadModules().stream()
                .filter(module -> module.getUsername().equals(authenticatedUser.getUserName()))
                .collect(Collectors.toList());
    }

    @Override
    public void addModule(Module module) throws UnauthorizedException {
        User authenticatedUser = authenticatedUserAccessor.getAuthenticatedUser();
        if (authenticatedUser == null || !module.getUsername().equals(authenticatedUser.getUserName()))
            throw new UnauthorizedException();

        moduleRepository.loadModules().add(module);
        moduleRepository.saveModules();
    }

    @Override
    public List<Module> deleteModule(String userName, String name) throws UnauthorizedException {
        User authenticatedUser = authenticatedUserAccessor.getAuthenticatedUser();
        if (authenticatedUser == null || !userName.equals(authenticatedUser.getUserName()))
            throw new UnauthorizedException();
        List<Module> newModule = moduleRepository.deleteModule(name);
        return newModule;
    }

    @Override
    public void save() {
        moduleRepository.saveModules();
    }
}
