package com.example.interfaces;

import com.example.models.Grade;
import com.example.models.Module;

import java.util.List;

public interface GradeService extends GradeExportService {
    List<Grade> getAllGrades();

    void addGrade(Grade grade, Module module);
    void removeGrade(Grade grade);
}
