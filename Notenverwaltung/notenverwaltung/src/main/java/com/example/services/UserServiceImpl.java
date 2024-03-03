package com.example.services;

import com.example.interfaces.UserRepository;
import com.example.interfaces.UserService;
import com.example.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private AuthenticatedUserAccessorImpl authenticatedUserAccessorImpl;

    public UserServiceImpl(UserRepository userRepository, AuthenticatedUserAccessorImpl authenticatedUserAccessorImpl) {
        this.userRepository = userRepository;
        this.authenticatedUserAccessorImpl = authenticatedUserAccessorImpl;
    }

    @Override
    public boolean login(String username, String password) {
        logger.info("try to login with user " + username);
        Optional<User> userOptional = userRepository.load().stream()
                .filter(user -> username.equals(user.getUserName()) && password.equals(user.getPassword()))
                .findFirst();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            authenticatedUserAccessorImpl.setAuthenticatedUser(user);
            logger.info("Successfully logged in user " + username + username);
            return true;
        } else {
            logger.warn("Failed to log in user " + username);
            return false;
        }
    }

    @Override
    public Optional<User> getUserByUsernameAndPassword(String username, String password) {
        return userRepository.load().stream()
                .filter(user -> username.equals(user.getUserName()) && password.equals(user.getPassword()))
                .findFirst();
    }

    @Override
    public User register(String username, String name, String vorname, String schule, String ortDerSchule, int jahrgang,
                         String lehrperson, String password) {

        logger.info("Register user " + username);
        List<User> users = userRepository.load();

        User user = new User(name, vorname, schule, ortDerSchule, jahrgang, lehrperson, username, password);

        users.add(user);

        userRepository.save(users);

        return user;
    }

    @Override
    public boolean resetPassword(String username, int jahrgang, String lehrperson, String newPassword) {
        logger.info("Try resetting password for user " + username);
        Optional<User> first = userRepository.load().stream().filter(u -> u.getUserName().equals(username)
                && u.getJahrgang() == jahrgang && u.getLehrperson().equals(lehrperson)).findFirst();

        if (first.isEmpty()) {
            logger.warn("Failed to reset password for user " + username);
            return false;
        }

        User user = first.get();

        user.setPassword(newPassword);

        userRepository.save();

        logger.info("Successfully reset password for user " + username);

        return true;
    }

    @Override
    public boolean update(String originalUsername, User updatedUser) {
        logger.info("Updating user " + updatedUser.getUserName());
        Optional<User> existingUser = userRepository.load().stream()
                .filter(user -> user.getUserName().equals(originalUsername))
                .findFirst();

        if (existingUser.isPresent()) {
            userRepository.updateUser(originalUsername, updatedUser);
            logger.info("User updated");
            return true;
        } else {
            return false;
        }
    }
}
