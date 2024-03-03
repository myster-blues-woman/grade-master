package com.example.services;

import com.example.exceptions.UnauthorizedException;
import com.example.interfaces.AuthenticatedUserAccessor;
import com.example.interfaces.ModuleRepository;
import com.example.interfaces.ModuleService;
import com.example.models.Module;
import com.example.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleServiceImpl implements ModuleService {
    private static Logger logger = LogManager.getLogger(ModuleServiceImpl.class);
    private ModuleRepository moduleRepository;
    private AuthenticatedUserAccessor authenticatedUserAccessor;

    public ModuleServiceImpl(ModuleRepository moduleRepository, AuthenticatedUserAccessor authenticatedUserAccessor) {
        this.moduleRepository = moduleRepository;
        this.authenticatedUserAccessor = authenticatedUserAccessor;
    }

    @Override
    public List<Module> getAllModules() throws UnauthorizedException {
        logger.info("Get all modules");
        User authenticatedUser = authenticatedUserAccessor.getAuthenticatedUser();
        if (authenticatedUser == null)
            throw new UnauthorizedException();

        return moduleRepository.load().stream()
                .filter(module -> module.getUsername().equals(authenticatedUser.getUserName()))
                .sorted(Comparator.comparing(Module::getName))
                .collect(Collectors.toList());
    }

    @Override
    public void addModule(Module module) throws UnauthorizedException {
        logger.info("Add module " + module.getName() + " for user " + module.getUsername());
        User authenticatedUser = authenticatedUserAccessor.getAuthenticatedUser();
        if (authenticatedUser == null || !module.getUsername().equals(authenticatedUser.getUserName()))
            throw new UnauthorizedException();

        moduleRepository.load().add(module);
        moduleRepository.save();
    }

    @Override
    public List<Module> deleteModule(String userName, String name) throws UnauthorizedException {
        logger.info("Deleting module " + name);
        User authenticatedUser = authenticatedUserAccessor.getAuthenticatedUser();
        if (authenticatedUser == null || !userName.equals(authenticatedUser.getUserName()))
            throw new UnauthorizedException();
        List<Module> newModule = moduleRepository.deleteModule(name);
        return newModule;
    }

    @Override
    public void save() {
        logger.info("Saving modules");
        moduleRepository.save();
    }
}
