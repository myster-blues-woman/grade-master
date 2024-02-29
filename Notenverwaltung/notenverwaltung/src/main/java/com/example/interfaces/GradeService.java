package com.example.interfaces;

import com.example.exceptions.UnauthorizedException;
import com.example.models.Grade;
import com.example.models.Module;

import java.util.List;

public interface GradeService extends GradeExportService {
    List<Grade> getAllGrades() throws UnauthorizedException;

    void addGrade(Grade grade, Module module) throws UnauthorizedException;
    void removeGrade(Grade grade) throws UnauthorizedException;
}
