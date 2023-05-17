/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author yibe1
 */
public class Repo {

    private String dbname = "at";
    private String sec = "Gvl6tl(FGC(KJ/[h";
    private String user = "admin";
    private java.util.Date dt;
    private String date;
    private XSSFSheet sheet;
    private XSSFWorkbook workbook;
    private XSSFRow row;
    private XSSFCell cell;
    private XSSFCellStyle styleHeader, style;
    private XSSFFont font;
    private final File file;
    private FileInputStream fileStream;

    public Repo() throws FileNotFoundException, IOException {
        dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(dt);
        File dir = new File("Employe");
        
        file = new File("Employe\\Employee_Data.xlsx");

        if (!dir.exists()) {
            dir.mkdir();

        }
        if (!file.exists()) {

            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Employee_Detail");

            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                createFile();
                workbook.write(outputStream);
                outputStream.close();

            }

            fileStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileStream);
            fileStream.close();
            sheet = workbook.getSheet("Employee_Detail");

        } else {
            fileStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileStream);
            fileStream.close();
            sheet = workbook.getSheet("Employee_Detail");
        }

        style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
//        style.setWrapText(true); //Set wordwrap

    }

    public void register(String[] data) throws IOException {
        int rowCount = 0;

        int lastRow = sheet.getLastRowNum();
        row = sheet.createRow(lastRow + 1);

        for (int i = 0; i < data.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(data[i]);
        }
        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        outFile.close();

    }

//    public ArrayList<employe> getUser() {
//
//        ArrayList<employe> list = new ArrayList<>();
//        try {
//
//            stmt = (Statement) conn.createStatement();
//            String query1 = "select * from employee";
//            ResultSet rs = stmt.executeQuery(query1);
//            while (rs.next()) {
//                Employe emp = new Employe();
//                emp.setFname(rs.getString("fname"));
//                emp.setMname(rs.getString("mname"));
//                emp.setLname(rs.getString("lname"));
//                emp.setSex(rs.getString("sex"));
//                emp.setBdate(rs.getString("bdate"));
//                emp.setEdate(rs.getString("edate"));
//                emp.setJob(rs.getString("job_position"));
//                emp.setLevel(rs.getString("level"));
//                emp.setId(rs.getInt("ID"));
//                emp.setSalary(rs.getInt("salary"));
//
//                list.add(emp);
//            }
//        } catch (SQLException excep) {
//            excep.printStackTrace();
//            System.out.println("exception1 is: " + excep);
//        } catch (Exception excep) {
//            excep.printStackTrace();
//            System.out.println("exception2 is: " + excep);
//        } finally {
//            try {
//                if (stmt != null) {
//                    conn.close();
//                }
//            } catch (SQLException se) {
//            }
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException se) {
//                se.printStackTrace();
//            }
//        }
//        return list;
//    }
    private void createFile() throws FileNotFoundException, IOException {

        String[] header = {"First Name", "Middle Name", "Last Name", "Gender", "Birth Date", "Employment Date", "Job Position", "ID", "JEG Level", "Salary","M_count","A_count","N_count","D_count","bidN_count"};
        int colCount = 0;
        int rowCount = 0;

        font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        styleHeader = workbook.createCellStyle();;
        styleHeader.setBorderBottom(BorderStyle.THIN);
        styleHeader.setBorderTop(BorderStyle.THIN);
        styleHeader.setBorderLeft(BorderStyle.THIN);
        styleHeader.setBorderRight(BorderStyle.THIN);
        styleHeader.setWrapText(true); //Set wordwrap
        styleHeader.setFont(font);
        row = sheet.createRow(rowCount);

        for (int i = 0; i < header.length; i++) {
//            sheet.autoSizeColumn(i);
            if (i == 0) {
                sheet.setColumnWidth(i, 4000);
            }
            if (i == 1) {
                sheet.setColumnWidth(i, 4000);
            }
            if (i == 2) {
                sheet.setColumnWidth(i, 4000);
            }
            if (i == 5) {
                sheet.setColumnWidth(i, 4000);
            }
            if (i == 6) {
                sheet.setColumnWidth(i, 4000);
            }
            if (i == 7) {
                sheet.setColumnWidth(i, 3000);
            }
            if (i == 8) {
                sheet.setColumnWidth(i, 4000);
            }

            cell = row.createCell(i);
            cell.setCellStyle(styleHeader);
            cell.setCellValue(header[i]);
        }

        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            workbook.write(outputStream);
            outputStream.close();
        }
    }

    ArrayList<Employe> getUser() {

        ArrayList<Employe> emp = new ArrayList<>();

        int number_of_row = sheet.getLastRowNum()+1;
        for (int i = 0; i < number_of_row; i++) {
            if (i == 0) {
                continue;
            }
            Employe employe = new Employe();
            row = sheet.getRow(i);
            employe.setFname(row.getCell(0).getStringCellValue());
            employe.setMname(row.getCell(1).getStringCellValue());
            employe.setLname(row.getCell(2).getStringCellValue());
            employe.setSex(row.getCell(3).getStringCellValue());
            employe.setBdate(row.getCell(4).getStringCellValue());
            employe.setEdate(row.getCell(5).getStringCellValue());
            employe.setJob(row.getCell(6).getStringCellValue());
            employe.setId(row.getCell(7).getStringCellValue());
            employe.setLevel(row.getCell(8).getStringCellValue());
            employe.setMcount((int)row.getCell(10).getNumericCellValue());
            employe.setAcount((int)row.getCell(11).getNumericCellValue());
            employe.setNcount((int)row.getCell(12).getNumericCellValue());
            employe.setDcount((int)row.getCell(13).getNumericCellValue());
            employe.setBidncount((int)row.getCell(14).getNumericCellValue());

            String value = null;
            CellType x = row.getCell(9).getCellTypeEnum();
            if (x == CellType.STRING) {
                value = row.getCell(9).getStringCellValue();
            } else {
                value = String.valueOf(row.getCell(9).getNumericCellValue());
            }
            employe.setSalary(value);
            emp.add(employe);
        }
        return emp;
    }

    /**
     *
     * @author yibe1
     */
}
