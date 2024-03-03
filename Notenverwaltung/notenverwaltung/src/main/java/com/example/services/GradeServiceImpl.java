package com.example.services;

import com.example.exceptions.UnauthorizedException;
import com.example.interfaces.AuthenticatedUserAccessor;
import com.example.interfaces.GradeService;
import com.example.interfaces.ModuleRepository;
import com.example.models.Grade;
import com.example.models.Module;
import com.example.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static Logger logger = LogManager.getLogger(GradeServiceImpl.class);
    private final ModuleRepository moduleRepository;
    private AuthenticatedUserAccessor authenticatedUserAccessor;

    public GradeServiceImpl(ModuleRepository moduleRepository, AuthenticatedUserAccessor authenticatedUserAccessor) {
        this.moduleRepository = moduleRepository;
        this.authenticatedUserAccessor = authenticatedUserAccessor;
    }

    @Override
    public void exportGradesToExcel(String fileName) throws UnauthorizedException {
        logger.info("Exporting grades to excel with filename " + fileName);
        User authenticatedUser = authenticatedUserAccessor.getAuthenticatedUser();
        if (authenticatedUser == null) throw new UnauthorizedException();

        List<Module> modules = moduleRepository.load().stream().filter(module -> module.getUsername().equals(authenticatedUser.getUserName())).toList();

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
            logger.info("Successfully exported grades to excel file");
        } catch (IOException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public List<Grade> getAllGrades() throws UnauthorizedException {
        logger.info("Get all grades");
        User authenticatedUser = authenticatedUserAccessor.getAuthenticatedUser();
        if (authenticatedUser == null) throw new UnauthorizedException();

        return moduleRepository.load().stream().filter(module -> module.getUsername().equals(authenticatedUser.getUserName())).map(Module::getGrades).flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public void addGrade(Grade grade, Module module) throws UnauthorizedException {
        logger.info("Add grade");
        User authenticatedUser = authenticatedUserAccessor.getAuthenticatedUser();
        if (authenticatedUser == null || !module.getUsername().equals(authenticatedUser.getUserName()))
            throw new UnauthorizedException();

        module.getGrades().add(grade);
        moduleRepository.save();
    }

    @Override
    public void removeGrade(Grade grade) throws UnauthorizedException {
        logger.info("remove grade");
        User authenticatedUser = authenticatedUserAccessor.getAuthenticatedUser();
        if (authenticatedUser == null) throw new UnauthorizedException();

        Optional<Module> module = moduleRepository.load().stream().filter(m -> m.getUsername().equals(authenticatedUser.getUserName())).filter(m -> m.getGrades().contains(grade)).findFirst();
        if (module.isEmpty()) {
            System.out.println("Could not remove grade");
            return;
        }

        module.get().getGrades().remove(grade);
        moduleRepository.save();
    }
}
