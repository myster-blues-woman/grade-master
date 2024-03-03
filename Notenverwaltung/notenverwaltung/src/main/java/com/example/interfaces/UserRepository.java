package com.example.interfaces;

import com.example.models.User;

import java.util.List;

public interface UserRepository {
    List<User> loadUsers();

    void saveUsers(List<User> users);

    void saveUsers();

    void updateUser(String originalUsername, User updatedUser);
}
