package com.example.services.test;

import com.example.interfaces.UserRepository;
import com.example.interfaces.UserService;
import com.example.models.User;
import com.example.services.UserServiceImpl;
import com.example.services.test.fakes.InMemoryUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImplTests {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void givenInvalidCredentials_whenLogin_thenFail() {
        // Arrange
        String username = "johnDoe";
        String password = "password123";

        // Act
        boolean result = userService.login(username, password);

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    void givenNewUserDetails_whenRegister_thenUserIsAdded() {
        // Arrange

        // Act
        User newUser = userService.register("newUser", "New", "User", "School", "City", 2021, "Mr. New", "newPassword");

        // Assert
        Assertions.assertNotNull(newUser);
        Assertions.assertEquals("newUser", newUser.getUserName());
    }

    @Test
    void givenExistingUser_whenResetPassword_thenSucceed() {
        // Arrange
        String username = "existingUser";
        int jahrgang = 2020;
        String lehrperson = "Mr. Existing";
        List<User> users = List.of(new User("Existing", "User", "School", "City", jahrgang, lehrperson, username, "oldPassword"));
        userRepository.saveUsers(users);

        // Act
        boolean result = userService.resetPassword(username, jahrgang, lehrperson, "newPassword");

        // Assert
        Assertions.assertTrue(result);
        Assertions.assertEquals("newPassword", users.get(0).getPassword());
    }

    @Test
    void givenExistingUser_whenUpdate_thenUserIsUpdated() {
        // Arrange
        User existingUser = new User("Existing", "User", "School", "City", 2020, "Mr. Existing", "existingUser", "oldPassword");
        List<User> users = new ArrayList<>(List.of(existingUser));
        userRepository.saveUsers(users);
        User updatedUser = new User("Updated", "User", "School", "City", 2020, "Mr. Existing", "existingUser", "updatedPassword");

        // Act
        boolean result = userService.Update(updatedUser);

        // Assert
        Assertions.assertTrue(result);
        Assertions.assertTrue(users.contains(updatedUser));
    }
}
