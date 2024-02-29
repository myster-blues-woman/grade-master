package com.example.interfaces;

import com.example.models.User;

public interface AuthenticatedUserAccessor {
    void setAuthenticatedUser(User user);

    User getAuthenticatedUser();
}
