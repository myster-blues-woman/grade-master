package com.example.services;

import com.example.interfaces.GradeService;
import com.example.interfaces.ModuleRepository;
import com.example.models.Grade;
import com.example.models.Module;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GradeServiceImpl implements GradeService {
    private final ModuleRepository moduleRepository;

    public GradeServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public void exportGradesToExcel(String fileName) {
        List<Module> modules = moduleRepository.loadModules();

        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(fileName)) {
            Sheet sheet = workbook.createSheet("Grades");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Modul");
            header.createCell(1).setCellValue("Beschreibung");
            header.createCell(2).setCellValue("Note");
            header.createCell(3).setCellValue("Gewichtung");

            int rowNum = 1;
            for (Module module : modules) {
                for (Grade grade : module.getGrades()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(module.getName());
                    row.createCell(1).setCellValue(grade.getDescription());
                    row.createCell(2).setCellValue(grade.getGrade());
                    row.createCell(3).setCellValue(grade.getWeight());
                }
            }

            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Grade> getAllGrades() {
        return moduleRepository.loadModules().stream().map(Module::getGrades).flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public void addGrade(Grade grade, Module module) {
        module.getGrades().add(grade);
        moduleRepository.saveModules();
    }

    @Override
    public void removeGrade(Grade grade) {
        Optional<Module> module = moduleRepository.loadModules().stream().filter(m -> m.getGrades().contains(grade))
                .findFirst();
        if (module.isEmpty()) {
            System.out.println("Could not remove grade");
            return;
        }

        module.get().getGrades().remove(grade);
        moduleRepository.saveModules();
    }
}
