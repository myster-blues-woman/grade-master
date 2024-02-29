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
}
