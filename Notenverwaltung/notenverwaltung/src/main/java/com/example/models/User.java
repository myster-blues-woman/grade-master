package com.example.models;

public class User {
    private String name;
    private String vorname;
    private String schule;
    private String ortDerSchule;
    private int jahrgang; // Angenommen, das ist immer eine gültige Zahl
    private String lehrperson;
    private String userName;
    private String password;

    // Konstruktor
    public User(String name, String vorname, String schule, String ortDerSchule, int jahrgang, String lehrperson,
                String userName, String password) {
        this.name = name;
        this.vorname = vorname;
        this.schule = schule;
        this.ortDerSchule = ortDerSchule;
        this.jahrgang = jahrgang;
        this.lehrperson = lehrperson;
        this.userName = userName;
        this.password = password;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getSchule() {
        return schule;
    }

    public void setSchule(String schule) {
        this.schule = schule;
    }

    public String getOrtDerSchule() {
        return ortDerSchule;
    }

    public void setOrtDerSchule(String ortDerSchule) {
        this.ortDerSchule = ortDerSchule;
    }

    public int getJahrgang() {
        return jahrgang;
    }

    public void setJahrgang(int jahrgang) {
        this.jahrgang = jahrgang;
    }

    public String getLehrperson() {
        return lehrperson;
    }

    public void setLehrperson(String lehrperson) {
        this.lehrperson = lehrperson;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}