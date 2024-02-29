package com.example.services.test;

import com.example.exceptions.UnauthorizedException;
import com.example.interfaces.AuthenticatedUserAccessor;
import com.example.interfaces.ModuleRepository;
import com.example.interfaces.ModuleService;
import com.example.models.User;
import com.example.models.Module;
import com.example.services.AuthenticatedUserAccessorImpl;
import com.example.services.ModuleServiceImpl;
import com.example.services.test.fakes.InMemoryModuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModuleServiceImplTest {
    private ModuleRepository moduleRepository;
    private ModuleService moduleService;
    private AuthenticatedUserAccessor authenticatedUserAccessor;

    @BeforeEach
    void setUp() {
        moduleRepository = new InMemoryModuleRepository();
        authenticatedUserAccessor = new AuthenticatedUserAccessorImpl();
        moduleService = new ModuleServiceImpl(moduleRepository, authenticatedUserAccessor);
    }

    @Test
    void givenNotAuthenticated_whenGetAllModules_thenThrowUnauthorizedException() {
        // Arrange
        authenticatedUserAccessor.setAuthenticatedUser(null);

        // Act, Assert
        assertThrows(UnauthorizedException.class, () -> moduleService.getAllModules());
    }

    @Test
    void givenAuthenticated_whenGetAllModules_thenSucceed() throws UnauthorizedException {
        // Arrange
        User user = new User("Peter", "Müller", "HFTM", "Grenchen", 2000, "Kurt",
                "peter", "1234");
        authenticatedUserAccessor.setAuthenticatedUser(user);
        Module module = new Module("OOP2", "peter", new ArrayList<>(), new ArrayList<>());
        List<Module> initialModules = moduleRepository.loadModules();
        initialModules.add(module);
        moduleRepository.saveModules(initialModules);

        // Act
        List<Module> modules = moduleService.getAllModules();

        // Assert
        assertFalse(modules.isEmpty());
        assertEquals(1, modules.size());
        assertEquals("OOP2", modules.get(0).getName());
    }

    @Test
    void givenAuthenticatedButDifferentUser_whenAddModule_thenThrowUnauthorizedException() {
        // Arrange
        User user = new User("Peter", "Müller", "HFTM", "Grenchen", 2000, "Kurt",
                "peter", "1234");
        authenticatedUserAccessor.setAuthenticatedUser(user);
        Module module = new Module("OOP2", "ueli", new ArrayList<>(), new ArrayList<>());

        // Act, Assert
        assertThrows(UnauthorizedException.class, () -> moduleService.addModule(module));
    }

    @Test
    void givenAuthenticatedAndAuthorized_whenAddModule_thenSucceed() throws UnauthorizedException {
        // Arrange
        User user = new User("Peter", "Müller", "HFTM", "Grenchen", 2000, "Kurt",
                "peter", "1234");
        authenticatedUserAccessor.setAuthenticatedUser(user);
        Module module = new Module("OOP1", "peter", new ArrayList<>(), new ArrayList<>());

        // Act
        assertDoesNotThrow(() -> moduleService.addModule(module));

        // Assert
        List<Module> modules = moduleService.getAllModules();
        assertFalse(modules.isEmpty());
        assertEquals("OOP1", modules.get(0).getName());
    }
}