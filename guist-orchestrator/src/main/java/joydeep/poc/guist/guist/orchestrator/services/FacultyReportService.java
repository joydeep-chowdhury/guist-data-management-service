package joydeep.poc.guist.guist.orchestrator.services;

import joydeep.poc.guist.guist.orchestrator.domains.Faculty;
import joydeep.poc.guist.guist.orchestrator.domains.FacultySpec;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyReportService {

    private FacultyClientService facultyClientService;
    private final String FILENAME = "faculty_report.xlsx";

    public FacultyReportService(FacultyClientService facultyClientService) {
        this.facultyClientService = facultyClientService;
    }


    public void fetchAll(HttpServletResponse servletResponse) throws IOException {
        List<Faculty> facultyList = facultyClientService.fetchAll();
        prepareReport(servletResponse, facultyList);
    }


    public void fetchByPage(int pageNo, int pageSize, HttpServletResponse servletResponse) throws IOException {
        List<Faculty> facultyList = facultyClientService.fetchByPage(pageNo, pageSize);
        prepareReport(servletResponse, facultyList);
    }

    public void retrieveByDepartmentNameAndDesignation(String departmentName, String designation, HttpServletResponse servletResponse) throws IOException {
        List<Faculty> facultyList = facultyClientService.retrieveByDepartmentNameAndDesignation(departmentName, designation);
        prepareReport(servletResponse, facultyList);

    }


    public void retrieveByFacultyName(String facultyName, HttpServletResponse servletResponse) throws IOException {
        List<Faculty> facultyList = facultyClientService.retrieveByFacultyName(facultyName);
        prepareReport(servletResponse, facultyList);

    }

    public void retrieveByDepartmentName(String departmentName, HttpServletResponse servletResponse) throws IOException {
        List<Faculty> facultyList = facultyClientService.retrieveByDepartmentName(departmentName);
        prepareReport(servletResponse, facultyList);

    }

    public void retrieveByDesignation(String designation, HttpServletResponse servletResponse) throws IOException {
        List<Faculty> facultyList = facultyClientService.retrieveByDesignation(designation);
        prepareReport(servletResponse, facultyList);
    }

    public void prepareReport(HttpServletResponse servletResponse, List<Faculty> facultyList) throws IOException {
        List<String> headerNames = Arrays.asList(FacultySpec.values()).stream().map(header -> header.toString()).collect(Collectors.toList());
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sh = workbook.createSheet("Faculty Details");
        Row headerRow = sh.createRow(0);
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        int cellCount = 0;
        for (String headerName : headerNames) {
            Cell cell = headerRow.createCell(cellCount);
            cell.setCellStyle(style);
            cell.setCellValue(headerName);
            cellCount++;
        }
        for (int rowCount = 1; rowCount <= facultyList.size(); rowCount++) {
            Row dataRow = sh.createRow(rowCount);
            Faculty faculty = facultyList.get(rowCount - 1);
            String facultyDetails = faculty.getFacultyDetails().stream().map(item -> item + System.lineSeparator()).collect(Collectors.joining());
            dataRow.createCell(0).setCellValue(facultyDetails);
            dataRow.createCell(1).setCellValue(faculty.getDepartmentName());
            dataRow.createCell(2).setCellValue(faculty.getDesignation());
        }
        FileOutputStream out = new FileOutputStream(FILENAME);
        workbook.write(out);
        out.close();
        FileCopyUtils.copy(new FileInputStream(Paths.get(FILENAME).toFile()), servletResponse.getOutputStream());
    }

}
