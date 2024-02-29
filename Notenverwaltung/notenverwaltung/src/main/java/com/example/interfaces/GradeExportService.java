package com.example.interfaces;

import com.example.exceptions.UnauthorizedException;

public interface GradeExportService {
    void exportGradesToExcel(String fileName) throws UnauthorizedException;
}
