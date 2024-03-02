package com.example.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Module {
    private String name;
    private String username;
    private List<Occurrence> occurrences;
    private List<Grade> grades;
    private LocalDate startDate;

    public Module(String name, String username, List<Occurrence> occurrences, List<Grade> grades, LocalDate startDate) {
        this.name = name;
        this.username = username;
        this.occurrences = occurrences;
        this.grades = grades;
        this.startDate = startDate;
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

    @JsonIgnore
    public double getGradeAvg() {
        double sumWeightedGrades = 0;
        double sumWeights = 0;

        for (Grade grade : grades) {
            sumWeightedGrades += grade.getGrade() * grade.getWeight();
            sumWeights += grade.getWeight();
        }

        if (sumWeights == 0) {
            return 0;
        }

        return sumWeightedGrades / sumWeights;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return name;
    }
}
