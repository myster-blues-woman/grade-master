package com.example.models;

import java.util.List;

public class Module {
    private String name;
    private List<Occurrence> occurrences;
    private List<Grade> grades;

    public Module(String name, List<Occurrence> occurrences, List<Grade> grades) {
        this.name = name;
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

}
