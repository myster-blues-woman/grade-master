module com.example {
    opens com.example.models;

    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires log4j.api;
    requires log4j.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.ooxml.schemas;

    opens com.example.Controller;
    opens com.example.services;
    opens com.example.repositories;
    opens com.example.interfaces;

    exports com.example.Controller;
    exports com.example.services;
    exports com.example.repositories;
    exports com.example.interfaces;
    exports com.example.models;
    exports com.example.exceptions;
}
