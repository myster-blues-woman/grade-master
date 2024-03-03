package com.example.repositories;

import com.example.interfaces.UserRepository;
import com.example.models.Module;
import com.example.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final String jsonFilePath = "./users.json";

    private final ObjectMapper mapper = new ObjectMapper();

    private boolean tainted = false;
    private List<User> users;

    @Override
    public List<User> loadUsers() {
        if (!tainted && users != null)
            return users;

        File file = new File(jsonFilePath);
        if (!file.exists()) {
            // If the file doesn't exist, create an empty list and return it
            try {
                // Ensure parent directories exist
                file.getParentFile().mkdirs();

                // Create the file
                file.createNewFile();

                // Write an empty list to the new file
                mapper.writeValue(file, new ArrayList<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
            users = new ArrayList<>();
        } else {
            try {
                users = mapper.readValue(file, new TypeReference<List<User>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return users;
    }

    @Override
    public void saveUsers(List<User> users) {
        try {
            mapper.writeValue(new File(jsonFilePath), users);
            tainted = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUsers() {
        try {
            mapper.writeValue(new File(jsonFilePath), users);
            tainted = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(String originalUsername, User updatedUser) {
        List<User> users = loadUsers(); // Lade die existierenden Benutzer

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUserName().equals(originalUsername)) {
                // Aktualisiere den Benutzer mit den neuen Daten
                users.set(i, updatedUser);
                tainted = true;
                break; // Beende die Schleife, nachdem der Benutzer gefunden und aktualisiert wurde
            }
        }
        System.out.println("Im Repository: Benutzer aktualisiert.");
        if (tainted) {
            saveUsers(); // Speichere die aktualisierte Liste von Benutzern
        }
    }

}
