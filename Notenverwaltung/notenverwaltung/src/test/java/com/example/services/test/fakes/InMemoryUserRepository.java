package com.example.services.test.fakes;

import com.example.interfaces.UserRepository;
import com.example.models.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {
    private List<User> users = new ArrayList<>();

    @Override
    public List<User> loadUsers() {
        return users;
    }

    @Override
    public void saveUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void saveUsers() {
        // Do nothing
    }

    @Override
    public void updateUser(String originalUsername, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUserName().equals(originalUsername)) {
                users.set(i, updatedUser);
                return;
            }
        }
        throw new IllegalArgumentException("User with username " + originalUsername + " not found");
    }

}
