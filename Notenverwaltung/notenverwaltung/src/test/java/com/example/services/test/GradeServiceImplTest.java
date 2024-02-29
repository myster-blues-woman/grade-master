package com.example.services.test;

import com.example.exceptions.UnauthorizedException;
import com.example.interfaces.AuthenticatedUserAccessor;
import com.example.interfaces.GradeService;
import com.example.interfaces.ModuleRepository;
import com.example.models.Grade;
import com.example.models.Module;
import com.example.models.User;
import com.example.services.AuthenticatedUserAccessorImpl;
import com.example.services.GradeServiceImpl;
import com.example.services.test.fakes.InMemoryModuleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GradeServiceImplTest {
    private ModuleRepository moduleRepository;
    private GradeService gradeService;
    private AuthenticatedUserAccessor authenticatedUserAccessor;
    private String fileName = "grades.xlsx";

    @BeforeEach
    void setUp() {
        moduleRepository = new InMemoryModuleRepository();
        authenticatedUserAccessor = new AuthenticatedUserAccessorImpl();
        gradeService = new GradeServiceImpl(moduleRepository, authenticatedUserAccessor);
    }

    @AfterEach
    void tearDown() {
        // Clean up by deleting the file if it exists
        File file = new File(fileName);
        if (file.exists()) {
            assertTrue(file.delete(), "The file should be deleted after test.");
        }
    }

    @Test
    void givenNotAuthenticated_whenExportGradesToExcel_thenThrowUnauthorizedException() {
        // Arrange
        authenticatedUserAccessor.setAuthenticatedUser(null);

        // Act, Assert
        assertThrows(UnauthorizedException.class, () -> gradeService.exportGradesToExcel(fileName));
    }

    @Test
    void givenNotAuthenticated_whenGetAllGrades_thenThrowUnauthorizedException() {
        // Arrange
        authenticatedUserAccessor.setAuthenticatedUser(null);

        // Act, Assert
        assertThrows(UnauthorizedException.class, () -> gradeService.getAllGrades());
    }

    @Test
    void givenNotAuthenticated_whenAddGrade_thenThrowUnauthorizedException() {
        // Arrange
        authenticatedUserAccessor.setAuthenticatedUser(null);
        Grade grade = new Grade();
        Module module = new Module();

        // Act, Assert
        assertThrows(UnauthorizedException.class, () -> gradeService.addGrade(grade, module));
    }

    @Test
    void givenNotAuthenticated_whenRemoveGrade_thenThrowUnauthorizedException() {
        // Arrange
        authenticatedUserAccessor.setAuthenticatedUser(null);
        Grade grade = new Grade();

        // Act, Assert
        assertThrows(UnauthorizedException.class, () -> gradeService.removeGrade(grade));
    }

    @Test
    void givenAuthenticated_whenExportGradesToExcel_thenSucceed() throws UnauthorizedException, IOException {
        // Arrange
        User user = new User("Peter", "Müller", "HFTM", "Grenchen", 2000, "Kurt", "peter", "1234");
        authenticatedUserAccessor.setAuthenticatedUser(user);
        List<Module> modules = List.of(new Module("OOP2", "peter", new ArrayList<>(), List.of(new Grade(6, 1, "test"))));
        moduleRepository.saveModules(modules);

        // Act
        assertDoesNotThrow(() -> gradeService.exportGradesToExcel(fileName));

        // Assert
        File file = new File(fileName);
        assertTrue(file.exists(), "The file should have been created.");
    }

    @Test
    void givenAuthenticated_whenGetAllGrades_thenSucceed() throws UnauthorizedException {
        // Arrange
        User user = new User("Peter", "Müller", "HFTM", "Grenchen", 2000, "Kurt", "peter", "1234");
        authenticatedUserAccessor.setAuthenticatedUser(user);
        List<Module> modules = List.of(new Module("OOP2", "peter", new ArrayList<>(), List.of(new Grade(5.0, 1.0, "Test"))));
        moduleRepository.saveModules(modules);

        // Act
        List<Grade> grades = gradeService.getAllGrades();

        // Assert
        Assertions.assertFalse(grades.isEmpty());
        Assertions.assertEquals(1, grades.size());
        Assertions.assertEquals("Test", grades.get(0).getDescription());
    }

    @Test
    void givenAuthenticatedAndAuthorized_whenAddGrade_thenSucceed() throws UnauthorizedException {
        // Arrange
        User user = new User("Peter", "Müller", "HFTM", "Grenchen", 2000, "Kurt", "peter", "1234");
        authenticatedUserAccessor.setAuthenticatedUser(user);
        Module module = new Module("OOP1", "peter", new ArrayList<>(), new ArrayList<>());
        ArrayList<Module> modules = new ArrayList<>();
        modules.add(module);
        moduleRepository.saveModules(modules);
        Grade grade = new Grade(5.5, 1.0, "test");

        // Act
        assertDoesNotThrow(() -> gradeService.addGrade(grade, module));

        // Assert
        List<Grade> grades = gradeService.getAllGrades();
        Assertions.assertFalse(grades.isEmpty());
    }

    @Test
    void givenAuthenticated_whenRemoveGrade_thenSucceed() throws UnauthorizedException {
        // Arrange
        User user = new User("Peter", "Müller", "HFTM", "Grenchen", 2000, "Kurt", "peter", "1234");
        authenticatedUserAccessor.setAuthenticatedUser(user);
        Grade grade = new Grade(5.5, 1.0, "test");
        Module module = new Module("OOP1", "peter", new ArrayList<>(), new ArrayList<>(List.of(grade)));
        ArrayList<Module> modules = new ArrayList<>();
        modules.add(module);
        moduleRepository.saveModules(modules);

        // Act
        assertDoesNotThrow(() -> gradeService.removeGrade(grade));

        // Assert
        assertTrue(module.getGrades().isEmpty());
    }

}
