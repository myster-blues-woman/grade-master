package com.example.services;

import com.example.Controller.App;
import com.example.interfaces.AuthenticatedUserAccessor;
import com.example.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthenticatedUserAccessorImpl implements AuthenticatedUserAccessor {
    private static Logger logger = LogManager.getLogger(AuthenticatedUserAccessorImpl.class);
    private User user;

    @Override
    public void setAuthenticatedUser(User user) {
        logger.info("Set authenticated user to " + user.getUserName());
        this.user = user;
    }

    @Override
    public User getAuthenticatedUser() {
        return user;
    }
}
