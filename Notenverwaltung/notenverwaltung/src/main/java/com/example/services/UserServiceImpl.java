package com.example.services;

import com.example.interfaces.UserRepository;
import com.example.interfaces.UserService;
import com.example.models.User;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private AuthenticatedUserAccessorImpl authenticatedUserAccessorImpl;

    public UserServiceImpl(UserRepository userRepository, AuthenticatedUserAccessorImpl authenticatedUserAccessorImpl) {
        this.userRepository = userRepository;
        this.authenticatedUserAccessorImpl = authenticatedUserAccessorImpl;
    }

    @Override
    public boolean login(String username, String password) {
        Optional<User> userOptional = userRepository.loadUsers().stream()
                .filter(user -> username.equals(user.getUserName()) && password.equals(user.getPassword()))
                .findFirst();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            authenticatedUserAccessorImpl.setAuthenticatedUser(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<User> getUserByUsernameAndPassword(String username, String password) {
        return userRepository.loadUsers().stream()
                .filter(user -> username.equals(user.getUserName()) && password.equals(user.getPassword()))
                .findFirst();
    }

    @Override
    public User register(String username, String name, String vorname, String schule, String ortDerSchule, int jahrgang,
            String lehrperson, String password) {
        List<User> users = userRepository.loadUsers();

        User user = new User(name, vorname, schule, ortDerSchule, jahrgang, lehrperson, username, password);

        users.add(user);

        userRepository.saveUsers(users);

        return user;
    }

    @Override
    public boolean resetPassword(String username, int jahrgang, String lehrperson, String newPassword) {
        Optional<User> first = userRepository.loadUsers().stream().filter(u -> u.getUserName().equals(username)
                && u.getJahrgang() == jahrgang && u.getLehrperson().equals(lehrperson)).findFirst();

        if (first.isEmpty())
            return false;

        User user = first.get();

        user.setPassword(newPassword);

        userRepository.saveUsers();

        return true;
    }

    @Override
    public boolean Update(User user) {
        boolean removed = userRepository.loadUsers().removeIf(user1 -> user1.getUserName().equals(user.getUserName()));

        if (!removed)
            return false;

        userRepository.loadUsers().add(user);

        userRepository.saveUsers();

        return true;
    }
}
