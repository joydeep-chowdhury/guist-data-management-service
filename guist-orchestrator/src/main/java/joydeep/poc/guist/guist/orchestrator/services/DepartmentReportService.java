package joydeep.poc.guist.guist.orchestrator.services;

import joydeep.poc.guist.guist.orchestrator.domains.Department;
import joydeep.poc.guist.guist.orchestrator.domains.DepartmentSpec;
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
public class DepartmentReportService {

    private final String FILENAME = "department_details.xlsx";
    private DepartmentClientService departmentClientService;

    public DepartmentReportService(DepartmentClientService departmentClientService) {
        this.departmentClientService = departmentClientService;
    }

    public void fetchAll(HttpServletResponse servletResponse) throws IOException {
        List<Department> departmentList = departmentClientService.fetchAll();
        prepareReport(servletResponse, departmentList);
    }


    public void fetchByPage(int pageNo, int pageSize, HttpServletResponse servletResponse) throws IOException {
        List<Department> departmentList = departmentClientService.fetchByPage(pageNo, pageSize);
        prepareReport(servletResponse, departmentList);
    }

    public void retrieveByDepartmentName(String departmentName, HttpServletResponse servletResponse) throws IOException {
        List<Department> departmentList = departmentClientService.retrieveByDepartmentName(departmentName);
        prepareReport(servletResponse, departmentList);
    }

    public void prepareReport(HttpServletResponse servletResponse, List<Department> departmentList) throws IOException {
        List<String> headerNames = Arrays.asList(DepartmentSpec.values()).stream().map(header -> header.toString()).collect(Collectors.toList());
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sh = workbook.createSheet("Department Details");
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
        for (int rowCount = 1; rowCount <= departmentList.size(); rowCount++) {
            Row dataRow = sh.createRow(rowCount);
            Department department = departmentList.get(rowCount - 1);
            dataRow.createCell(0).setCellValue(department.getDepartmentName());
            dataRow.createCell(1).setCellValue(department.getDepartmentDetails());
        }
        FileOutputStream out = new FileOutputStream(FILENAME);
        workbook.write(out);
        out.close();
        FileCopyUtils.copy(new FileInputStream(Paths.get(FILENAME).toFile()), servletResponse.getOutputStream());
    }


}
