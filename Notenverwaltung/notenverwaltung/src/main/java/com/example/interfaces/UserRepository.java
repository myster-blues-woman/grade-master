package com.example.interfaces;

import com.example.models.User;

import java.util.List;

public interface UserRepository extends BaseRepository<User> {
    List<User> load();

    void save(List<User> users);

    void save();
    void updateUser(String originalUsername, User updatedUser);
}
