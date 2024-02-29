package com.example.services;

import com.example.interfaces.UserRepository;
import com.example.interfaces.UserService;
import com.example.models.User;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean login(String username, String password) {
        return userRepository.loadUsers().stream()
                .anyMatch(user -> username.equals(user.getUserName()) && password.equals(user.getPassword()));
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
}
