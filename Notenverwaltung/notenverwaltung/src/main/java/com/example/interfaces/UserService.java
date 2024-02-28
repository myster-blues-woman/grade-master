package com.example.interfaces;

import com.example.models.User;

public interface UserService {
    boolean login(String username, String password);

    User register(String username, String name, String vorname, String schule, String ortDerSchule, int jahrgang, String lehrperson, String password);

    boolean resetPassword(String username, int jahrgang, String lehrperson, String newPassword);
}
