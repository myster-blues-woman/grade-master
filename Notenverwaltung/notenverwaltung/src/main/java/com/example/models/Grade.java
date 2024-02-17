package com.example.models;

public class Grade {

    private double grade;
    private double weight;
    private String description;

    public Grade(double grade, double weight, String description) {
        this.grade = grade;
        this.weight = weight;
        this.description = description;
    }

    public Grade() {
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
