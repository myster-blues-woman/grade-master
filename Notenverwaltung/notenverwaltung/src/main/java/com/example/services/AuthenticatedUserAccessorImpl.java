package com.example.services;

import com.example.interfaces.AuthenticatedUserAccessor;
import com.example.models.User;

public class AuthenticatedUserAccessorImpl implements AuthenticatedUserAccessor {
    private User user;

    @Override
    public void setAuthenticatedUser(User user) {
        this.user = user;
    }

    @Override
    public User getAuthenticatedUser() {
        return user;
    }
}
