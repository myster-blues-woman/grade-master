package com.example.models;

import java.util.List;

public class Module {
    private String name;
    private String username;
    private List<Occurrence> occurrences;
    private List<Grade> grades;

    public Module(String name, String username, List<Occurrence> occurrences, List<Grade> grades) {
        this.name = name;
        this.username = username;
        this.occurrences = occurrences;
        this.grades = grades;
    }

    public Module() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Occurrence> getOccurrences() {
        return occurrences;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
