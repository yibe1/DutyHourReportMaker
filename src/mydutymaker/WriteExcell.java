/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mydutymaker;

/**
 *
 * @author Yibe2
 */
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.poi.ss.usermodel.BorderStyle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 * A very simple program that writes some data to an Excel file using the Apache
 * POI library.
 *
 * @author www.codejava.net
 *
 */
public class WriteExcell {
    
    private int wk;
    private int firstDay = 0;
    private int lastDay = 0;
    ArrayList<String> header = new ArrayList();
    ArrayList<Data> theList = new ArrayList();
    ArrayList<String> weekDays = new ArrayList();//mon, tue,....
    ArrayList<String> weeks;//wk1 wk1,,......
    ArrayList<String> wks = new ArrayList();//wk1-6
    ArrayList<Integer> tobeAdded = new ArrayList<>();
    ArrayList<Integer> hollydays = new ArrayList<>();

//    String dir = "G:\\Other computers\\My Computer\\Office\\Duty related\\Duty Report\\2015\\";
    private String st;
    private final String month;
    private XSSFCellStyle style;
    private XSSFCellStyle style2;
    private XSSFCellStyle style3;
    private XSSFCellStyle style4;
    private XSSFSheet sheet;
    private XSSFWorkbook workbook;
    private XSSFFont font;
    private XSSFFont font2;
    private int rowCount;
    private XSSFRow row;
    private int count;
    private int columnCount = 0;
    Cell cell;
    private CellStyle styler1;
    private CellStyle stylerh;
    private Document pdfR;
    private PdfPTable my_table;
    private PdfPCell table_cell;
    private PdfPCell atendM_cell;
    private FileOutputStream outputStream;
    private int fontSize;
    private String institution;
    private Document frontSummary;
    private PdfPTable summary_table;
    private PdfPCell summary_cell;
    private String dir;
    private String dir2;
    private File tempfiledir;
    private PdfPTable attendance;
    private Document atendM;
    private FileOutputStream outputStreamAtendM;
    private Document atendAft;
    private Document atendN;
    private PdfPTable attendanceAft;
    private PdfPTable attendanceN;
    private String attendanceDir;
    private FileOutputStream outputStreamAtendAft;
    private FileOutputStream outputStreamAtendN;
    private ArrayList<String> AL;
    
    WriteExcell(ArrayList<Data> theList, ArrayList<String> weekDays, ArrayList<String> weeks, String month, ArrayList<Integer> hollydays, String inst) throws DocumentException, IOException {
        
        this.institution = inst;
        workbook = new XSSFWorkbook();
        fontSize = 11;
        style = workbook.createCellStyle(); //Create new style
        style2 = workbook.createCellStyle(); //Create new style
        style3 = workbook.createCellStyle(); //Create new style
        style4 = workbook.createCellStyle(); //Create new style 
        styler1 = workbook.createCellStyle(); //Create new style
        stylerh = workbook.createCellStyle(); //Create new style

        font = workbook.createFont();
        font2 = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        font2.setBold(true);
        
        style.setWrapText(true); //Set wordwrap
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style2.setWrapText(true); //Set wordwrap
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style4.setBorderBottom(BorderStyle.THIN);
        style4.setBorderTop(BorderStyle.THIN);
        style4.setBorderLeft(BorderStyle.THIN);
        style4.setBorderRight(BorderStyle.THIN);
        style4.setAlignment(HorizontalAlignment.CENTER);
        style4.setVerticalAlignment(VerticalAlignment.CENTER);
        style4.setWrapText(true); //Set wordwrap

        styler1.setWrapText(true); //Set wordwrap
        styler1.setBorderBottom(BorderStyle.THIN);
        styler1.setBorderTop(BorderStyle.THIN);
        styler1.setBorderLeft(BorderStyle.THIN);
        styler1.setBorderRight(BorderStyle.THIN);
        styler1.setAlignment(HorizontalAlignment.LEFT);
        
        stylerh.setWrapText(true); //Set wordwrap
        stylerh.setBorderBottom(BorderStyle.THIN);
        stylerh.setBorderTop(BorderStyle.THIN);
        stylerh.setBorderLeft(BorderStyle.THIN);
        stylerh.setBorderRight(BorderStyle.THIN);
        stylerh.setAlignment(HorizontalAlignment.CENTER);
        stylerh.setFont(font2);
//        style4.setWrapText(true);
        style3.setFont(font);
        style4.setFont(font2);
        
        this.weekDays = weekDays;
        this.theList = theList;
        this.month = month;
        this.theList.remove(0);
        this.weeks = weeks;
        for (int i = 0; i < weeks.size(); i++) {
            if (!wks.contains(weeks.get(i))) {
                wks.add(weeks.get(i));
            }
        }
        
        this.hollydays = hollydays;
        AL = new ArrayList();
        setAnualLeaves();
    }
    
    public void setAnualLeaves() {
        for (int i = 0; i < theList.size(); i++) {
            String name = theList.get(i).getName();
            Data data = theList.get(i);
            for (int j = 0; j < data.getShifts().size(); j++) {
                if (data.getShifts().get(j).equals("AL")) {
                    AL.add(name + " " + (j + 1));
                }
            }
        }
        for (int i = 0; i < AL.size(); i++) {
            System.out.println(AL.get(i));
        }
        
    }
    
    public void setHeader(String week) {
//        int day = getFday(wk);
        header.add("Name");
        header.add("Room");
        int count = 0;
        for (int i = 0; i < 30; i++) {
            if (weeks.get(i).equals(week)) {
                String st = weekDays.get(i) + "(" + (i + 1) + ")";
                header.add(st);
                count++;
            }
        }
        for (int j = 0; j < 7 - count; j++) {
            header.add("");
        }
        header.add("Weekly Hour");
        header.add("Add. Hour");
        header.add("Total Hour");
        
    }
    
    public void write(String year) throws IOException, Exception {
        
        dir = "Duty_Report\\" + year + "\\" + month + "\\";
        dir2 = "Duty_Report\\temp\\" + year + "\\";
        attendanceDir = "Duty_Report\\temp\\Attendances\\";
        File file = new File(dir + month + "_Report.xlsx");
        File file2 = new File("Duty_Report\\temp\\" + year + "\\");
        File attFile = new File(attendanceDir);
        
        if (!file.exists()) {
            File dr = new File(dir);
            dr.mkdirs();
        }
        if (!file2.exists()) {
            file2.mkdirs();
        }
        if (!attFile.exists()) {
            attFile.mkdirs();
        }
        tempfiledir = new File("Duty_Report\\temp\\");
        
        System.out.println("wks .size is = "+wks.size());
        for (int wk = 0; wk < wks.size(); wk++) {
            pdfR = new Document();
            atendM = new Document();
            atendAft = new Document();
            atendN = new Document();
            
            my_table = new PdfPTable(12);
            attendance = new PdfPTable(8);
            attendanceAft = new PdfPTable(8);
            attendanceN = new PdfPTable(8);
            
            String dest = dir2 + "\\" + month + "_" + wks.get(wk) + ".pdf";
            outputStream = new FileOutputStream(dest);
            PdfWriter.getInstance(pdfR, outputStream);
            
            String destMor = attendanceDir + month + "_" + wks.get(wk) + "_Morning_attendance.pdf";
            outputStreamAtendM = new FileOutputStream(destMor);
            PdfWriter.getInstance(atendM, outputStreamAtendM);
            
            String destAft = attendanceDir + month + "_" + wks.get(wk) + "_Afternoon_attendance.pdf";
            outputStreamAtendAft = new FileOutputStream(destAft);
            PdfWriter.getInstance(atendAft, outputStreamAtendAft);
            
            String destNight = attendanceDir + month + "_" + wks.get(wk) + "_Night_attendance.pdf";
            outputStreamAtendN = new FileOutputStream(destNight);
            PdfWriter.getInstance(atendN, outputStreamAtendN);
            
            pdfR.setPageSize(PageSize.A4.rotate());
            float m = 450 + pdfR.getPageSize().getWidth() / 2;//determines page margin
            pdfR.setMargins(m, m, 50, 50);
            
            atendM.setPageSize(PageSize.A4.rotate());
            atendM.setMargins(m, m, 50, 50);
            
            atendAft.setPageSize(PageSize.A4.rotate());
            atendAft.setMargins(m, m, 50, 50);
            
            atendN.setPageSize(PageSize.A4.rotate());
            atendN.setMargins(m, m, 50, 50);
            
            float[] columnWidths = new float[]{75f, 18f, 25f, 25f, 25f, 25f, 25f, 25f, 25f, 30f, 26f, 25f};
            float[] columnWidths2 = new float[]{75f, 39f, 39f, 39f, 39f, 39f, 39f, 39f};
            my_table.setWidths(columnWidths);

            //Set column width for attendances
            attendance.setWidths(columnWidths2);
            attendanceAft.setWidths(columnWidths2);
            attendanceN.setWidths(columnWidths2);
            
            table_cell = null;
            
            atendM_cell = null;
            
            rowCount = 0;
            switch (wks.get(wk)) {
                case "wk1":
                    st = "Week One";
                    break;
                case "wk2":
                    st = "Week Two";
                    break;
                case "wk3":
                    st = "Week Three";
                    break;
                case "wk4":
                    st = "Week Four";
                    break;
                case "wk5":
                    st = "Week Five";
                    break;
                case "wk6":
                    st = "Week Six";
                    break;
            }
            sheet = workbook.createSheet(st);
            sheet.setColumnWidth(0, 5000);
            setHeader(wks.get(wk));
            columnCount = 0;

/////////////////////////////////////////////////////////////
            table_cell = new PdfPCell(new Phrase(new Chunk("Weekly Worked Hours Summary, " + institution + " " + month + " 2015EC", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD))));
            table_cell.setHorizontalAlignment(0);
            table_cell.setColspan(12);
            table_cell.setPaddingBottom(25);
            table_cell.setBorder(Rectangle.NO_BORDER);
            my_table.completeRow();
            my_table.addCell(table_cell);
            
            atendM_cell = new PdfPCell(new Phrase(new Chunk("Weekly Attendance, " + institution + " " + month + " 2015EC", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD))));
            atendM_cell.setHorizontalAlignment(0);
            atendM_cell.setColspan(8);
            atendM_cell.setPaddingBottom(25);
            atendM_cell.setBorder(Rectangle.NO_BORDER);
            attendance.completeRow();
            attendance.addCell(atendM_cell);
            
            atendM_cell = new PdfPCell(new Phrase(new Chunk("Weekly Attendance, " + institution + " " + month + " 2015EC", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD))));
            atendM_cell.setHorizontalAlignment(0);
            atendM_cell.setColspan(8);
            atendM_cell.setPaddingBottom(25);
            atendM_cell.setBorder(Rectangle.NO_BORDER);
            attendanceAft.completeRow();
            attendanceAft.addCell(atendM_cell);
            
            atendM_cell = new PdfPCell(new Phrase(new Chunk("Weekly Attendance, " + institution + " " + month + " 2015EC", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD))));
            atendM_cell.setHorizontalAlignment(0);
            atendM_cell.setColspan(8);
            atendM_cell.setPaddingBottom(25);
            atendM_cell.setBorder(Rectangle.NO_BORDER);
            attendanceN.completeRow();
            attendanceN.addCell(atendM_cell);
            
            row = sheet.createRow(rowCount);
            sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 11));
            rowCount++;
            columnCount = 0;
            cell = row.createCell(columnCount);
            cell.setCellStyle(style);
            columnCount++;
            
            cell.setCellStyle(style4); //Apply style to cell    

            //open pdf  editing............
            pdfR.open();
            atendM.open();
            atendAft.open();
            atendN.open();
            
            cell.setCellValue(st);
            table_cell = new PdfPCell(new Phrase(new Chunk(st, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD))));
            table_cell.setHorizontalAlignment(1);
            table_cell.setColspan(12);
            my_table.completeRow();
            my_table.addCell(table_cell);

//Attendance
            table_cell = new PdfPCell(new Phrase(new Chunk(st, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD))));
            table_cell.setHorizontalAlignment(1);
            table_cell.setColspan(12);
            attendance.completeRow();
            attendance.addCell(table_cell);
            
            table_cell = new PdfPCell(new Phrase(new Chunk(st, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD))));
            table_cell.setHorizontalAlignment(1);
            table_cell.setColspan(12);
            attendanceAft.completeRow();
            attendanceAft.addCell(table_cell);
            
            table_cell = new PdfPCell(new Phrase(new Chunk(st, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD))));
            table_cell.setHorizontalAlignment(1);
            table_cell.setColspan(12);
            attendanceN.completeRow();
            attendanceN.addCell(table_cell);
            
            for (int i = 0; i < 11; i++) {
                cell = row.createCell(columnCount);
                cell.setCellStyle(style4);
                columnCount++;
            }

//            
            //////////////////////////////////row 1////////////////////
            row = sheet.createRow(rowCount);
            columnCount = 0;

            //////////////////////////////////////////////////////PDF writer
//            table_cell.setColumn(new Phrase(new Chunk("", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.NORMAL))),
//                    46, 190, 530, 36, 25, Element.ALIGN_LEFT | Element.ALIGN_TOP);
//            ct.Go();
            ////////////////////////////////
            for (int j = 0; j < 12; j++) {
                
                cell = row.createCell(columnCount);
                cell.setCellStyle(style4);
                cell.setCellValue(header.get(j));
                table_cell = new PdfPCell(new Phrase(new Chunk(header.get(j), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
                table_cell.setHorizontalAlignment(1);
                my_table.addCell(table_cell);
                columnCount++;

                ///For attendance
                if (j == 1) {
                    continue;//not requered for attendance sheet
                }
                if (j == 10) {
                    continue;
                }
                if (j == 11) {
                    continue;
                }
                if (j == 9) {
                    continue;
                }
                if (j == 0) {
                    table_cell = new PdfPCell(new Phrase(new Chunk("Week days>>", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
                    table_cell.setHorizontalAlignment(1);
                    attendance.addCell(table_cell);
                    attendanceAft.addCell(table_cell);
                    attendanceN.addCell(table_cell);
                    
                    continue;
                }
                
                table_cell = new PdfPCell(new Phrase(new Chunk(header.get(j), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
                table_cell.setHorizontalAlignment(1);
                attendance.addCell(table_cell);
                attendanceAft.addCell(table_cell);
                attendanceN.addCell(table_cell);
                
            }
            my_table.completeRow();
            attendance.completeRow();
            attendanceAft.completeRow();
            attendanceN.completeRow();

//            pdfR.left((float) 0.5);
/////////////////////////////////////////////////
//For attendance
            PdfPTable ptable = new PdfPTable(2);
            PdfPCell pcell = new PdfPCell(new Phrase(new Chunk("IN", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            pcell.setHorizontalAlignment(1);
            ptable.addCell(pcell);
            
            pcell = new PdfPCell(new Phrase(new Chunk("OUT", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            pcell.setHorizontalAlignment(1);
            ptable.addCell(pcell);

            //Place for signature
            PdfPTable empty_table = new PdfPTable(2);
            PdfPCell empty_cell = new PdfPCell(new Phrase(new Chunk(" ", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            empty_cell.setPaddingTop(10);
            empty_cell.setPaddingBottom(10);
            empty_table.addCell(empty_cell);
            
            empty_cell = new PdfPCell(new Phrase(new Chunk(" ", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            empty_cell.setPaddingTop(10);
            empty_cell.setPaddingBottom(10);
            empty_table.addCell(empty_cell);
            
            for (int i = 0; i < 8; i++) {
                if (i == 0) {
                    pcell = new PdfPCell(new Phrase(new Chunk("Sign Time>>", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
                    pcell.setHorizontalAlignment(1);
                    attendance.addCell(pcell);
                    attendanceAft.addCell(pcell);
                    attendanceN.addCell(pcell);
                    continue;
                }
                if (header.get(i + 1).equals(""))// if week is not full 7 day
                {
                    pcell = new PdfPCell(new Phrase(new Chunk(">>", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
                    pcell.setHorizontalAlignment(1);
                    attendance.addCell(pcell);
                    attendanceAft.addCell(pcell);
                    attendanceN.addCell(pcell);
                } else {
                    table_cell = new PdfPCell(ptable);
                    table_cell.setHorizontalAlignment(1);
                    attendance.addCell(table_cell);
                    attendanceAft.addCell(table_cell);
                    attendanceN.addCell(table_cell);
                }
            }
//            attendance.completeRow();
            rowCount++;
            columnCount = 0;
            row = sheet.createRow(rowCount);
            
            cell = row.createCell(columnCount);
            columnCount++;
            cell.setCellStyle(style4);
            cell.setCellValue("Morning");
            
            table_cell = new PdfPCell(new Phrase(new Chunk("Morning", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            table_cell.setColspan(12);
            table_cell.setHorizontalAlignment(1);
            my_table.addCell(table_cell);
            my_table.completeRow();

            //For attendance
            table_cell = new PdfPCell(new Phrase(new Chunk("Name", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            table_cell.setHorizontalAlignment(1);
            attendance.addCell(table_cell);
            attendanceAft.addCell(table_cell);
            attendanceN.addCell(table_cell);
            
            table_cell = new PdfPCell(new Phrase(new Chunk("Morning", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            table_cell.setColspan(7);
            table_cell.setHorizontalAlignment(1);
            attendance.addCell(table_cell);
            attendance.completeRow();
            
            sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 11));
            rowCount++;
/////////////////////////////////////////////////

            for (int i = 0; i < theList.size(); i++) {
                boolean firstm = true;
                String name = theList.get(i).getName();
                ArrayList<String> data = theList.get(i).getShifts();
                boolean putName = true;
                boolean first = true;
                boolean room = true;
                columnCount = 0;
                count = 0;
                boolean start = false;
                int countHollydays = 0;
                int alCount = 0;
                for (int j = 0; j < weeks.size(); j++) {
                  
                    if (weeks.get(j).equals(wks.get(wk)) && data.get(j).equals("M")) {
                        String curentShift = data.get(j);
                        String curentWk = weeks.get(j);
                        
                        if (j < 29) {
                            if (!data.get(j + 1).equals(curentShift) || !weeks.get(j + 1).equals(curentWk)) {
                                start = true;
                            }
                        } else {
                            start = true;
                        }
                        
                        if (putName) {
                            firstDay = j + 1;
                            columnCount = 0;
                            row = sheet.createRow(rowCount);
                            rowCount++;
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style2);
                            cell.setCellValue(name);
                            table_cell = new PdfPCell(new Phrase(new Chunk(name, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell(table_cell);

                            //For attendance
                            table_cell = new PdfPCell(new Phrase(new Chunk(name, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            table_cell.setPaddingTop(10);
                            table_cell.setPaddingBottom(10);
                            attendance.addCell(table_cell);
                            
                            putName = false;
                            columnCount++;
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style);
                            cell.setCellValue("");
                            columnCount++;
                            table_cell = new PdfPCell(new Phrase(new Chunk("", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell(table_cell);
                            
                        }
                        
                        cell = row.createCell(columnCount);
                        cell.setCellStyle(style);
                        
                        if (AL.contains(name + " " + (j + 1))) {
                            if(name.equals("Meselu Degefu")){
                                System.out.println("meselu M"+ 0);
                            }
                            cell.setCellValue(0);
                            if (weekDays.get(j).equals("Sat") || weekDays.get(j).equals("Sun")) {
                                table_cell = new PdfPCell(new Phrase(new Chunk("0", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            } else {
                                table_cell = new PdfPCell(new Phrase(new Chunk("AL*", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            }
                            my_table.addCell(table_cell);
                            //For attendance
                            table_cell = new PdfPCell(new Phrase(new Chunk("AL", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            attendance.addCell(table_cell);
                            alCount++;
                        } else if (hollydays.contains(j + 1)) {
                            cell.setCellValue(12);
                            table_cell = new PdfPCell(new Phrase(new Chunk("12", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell(table_cell);
                            count++;
                            countHollydays++;
                            //For attendance
                            table_cell = new PdfPCell(empty_table);
                            attendance.addCell(table_cell);
                            if(name.equals("Meselu Degefu")){
                                System.out.println("meselu M"+12);
                            }
                        } else {
                            cell.setCellValue(6);
                            table_cell = new PdfPCell(new Phrase(new Chunk("6", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell(table_cell);
                            count++;

                            //For attendance
                            table_cell = new PdfPCell(empty_table);
                            attendance.addCell(table_cell);
                            if(name.equals("Meselu Degefu")){
                                System.out.println("meselu M"+6);
                            }
                        }
                        
                        columnCount++;
                        
                        int cc = 7 - count - alCount;
                        if (start) {
                            lastDay = j;
                            for (int jj = 0; jj < cc; jj++) {
                                cell = row.createCell(columnCount);
                                cell.setCellStyle(style);
                                cell.setCellValue("");
                                table_cell = new PdfPCell(new Phrase(new Chunk("", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                                my_table.addCell(table_cell);
                                columnCount++;
                                //For attendance
//                                table_cell = new PdfPCell(new Phrase(new Chunk(">>", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
//                                table_cell.setHorizontalAlignment(1);
//                                attendance.addCell(table_cell);
                            }
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style);
                            int val;
                            val = count * 6 + countHollydays * 6;
                            
                            cell.setCellValue(val);
                            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(val), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell((PdfPCell) table_cell);
                            columnCount++;
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style);
                            cell.setCellValue(0);//tecemari seat
                            table_cell = new PdfPCell(new Phrase(new Chunk("0", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell((PdfPCell) table_cell);
                            columnCount++;

                            //total weekly
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style);
                            cell.setCellValue(val);
                            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(val), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell(table_cell);
                            
                            start = false;
                            
                        }
//                columnCount++;

                    }
                }
                my_table.completeRow();
                attendance.completeRow();
            }

////////////////////////////////
///////////////////////////////
/////////////////////////////////////////////////
            columnCount = 0;
            row = sheet.createRow(rowCount);
            cell = row.createCell(columnCount);
            cell.setCellStyle(style4);
            cell.setCellValue("Afternoon");
            table_cell = new PdfPCell(new Phrase(new Chunk("Afternoon", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            table_cell.setColspan(12);
            table_cell.setHorizontalAlignment(1);
            my_table.addCell(table_cell);
            my_table.completeRow();
            
            table_cell = new PdfPCell(new Phrase(new Chunk("Afternoon", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            table_cell.setColspan(7);
            table_cell.setHorizontalAlignment(1);
            attendanceAft.addCell(table_cell);
            attendanceAft.completeRow();
            sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 11));
            rowCount++;
/////////////////////////////////////////////////

            for (int i = 0; i < theList.size(); i++) {
                boolean firstm = true;
                String name = theList.get(i).getName();
                ArrayList<String> data = theList.get(i).getShifts();
                boolean putName = true;
                boolean first = true;
                boolean room = true;
                columnCount = 0;
                count = 0;
                boolean start = false;
                int countHollydays = 0;
                int alCount = 0;
                for (int j = 0; j < weeks.size(); j++) {
                    
                    if (weeks.get(j).equals(wks.get(wk)) && data.get(j).equals("A")) {
                        String curentShift = data.get(j);
                        String curentWk = weeks.get(j);
                        
                        if (j < 29) {
                            if (!data.get(j + 1).equals(curentShift) || !weeks.get(j + 1).equals(curentWk)) {
                                start = true;
                            }
                        } else {
                            start = true;
                        }
                        if (putName) {
                            
                            columnCount = 0;
                            row = sheet.createRow(rowCount);
                            rowCount++;
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style2);
                            cell.setCellValue(name);
                            table_cell = new PdfPCell(new Phrase(new Chunk(name, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell((PdfPCell) table_cell);
                            
                            table_cell = new PdfPCell(new Phrase(new Chunk(name, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            table_cell.setPaddingTop(10);
                            table_cell.setPaddingBottom(10);
                            attendanceAft.addCell(table_cell);
                            putName = false;
                            columnCount++;
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style);
                            cell.setCellValue("");
                            table_cell = new PdfPCell(new Phrase(new Chunk("", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell(table_cell);
                            columnCount++;
                        }
                        cell = row.createCell(columnCount);
                        cell.setCellStyle(style);
                        
                        if (AL.contains(name + " " + (j + 1))) {
                            cell.setCellValue(0);
                            if (weekDays.get(j).equals("Sat") || weekDays.get(j).equals("Sun")) {
                                table_cell = new PdfPCell(new Phrase(new Chunk("0", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            } else {
                                table_cell = new PdfPCell(new Phrase(new Chunk("AL*", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            }
                            my_table.addCell(table_cell);
                            //For attendance
                            table_cell = new PdfPCell(new Phrase(new Chunk("AL", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            attendanceAft.addCell(table_cell);
                            alCount++;
                            if(name.equals("Meselu Degefu")){
                                System.out.println("meselu A"+0);
                            }
                        } else if (hollydays.contains(j + 1)) {
                            cell.setCellValue(12);
                            table_cell = new PdfPCell(new Phrase(new Chunk("12", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell(table_cell);
                            count++;
                            countHollydays++;

                            //For attendance
                            table_cell = new PdfPCell(empty_table);
                            attendanceAft.addCell(table_cell);
                            if(name.equals("Meselu Degefu")){
                                System.out.println("meselu A"+12);
                            }
                        } else {
                            cell.setCellValue(6);
                            table_cell = new PdfPCell(new Phrase(new Chunk("6", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell((PdfPCell) table_cell);
                            count++;
                            //For attendance
                            table_cell = new PdfPCell(empty_table);
                            attendanceAft.addCell(table_cell);
                            if(name.equals("Meselu Degefu")){
                                System.out.println("meselu A"+6);
                            }
                        }
                        columnCount++;
                        
                        int cc = 7 - count - alCount;
                        if (start) {
                            
                            for (int jj = 0; jj < cc; jj++) {
                                cell = row.createCell(columnCount);
                                cell.setCellStyle(style);
                                cell.setCellValue("");
                                table_cell = new PdfPCell(new Phrase(new Chunk("", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                                my_table.addCell((PdfPCell) table_cell);
                                columnCount++;

                                //For attendance
                                table_cell = new PdfPCell(new Phrase(new Chunk(">>", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                                table_cell.setHorizontalAlignment(1);
                                attendanceAft.addCell(table_cell);
                            }
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style);
                            int val;
                            val = count * 6 + countHollydays * 6;
                            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(val), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL))));
                            my_table.addCell(table_cell);
                            
                            cell.setCellValue(val);
                            columnCount++;
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style);
                            cell.setCellValue(0);//tecemari seat
                            table_cell = new PdfPCell(new Phrase(new Chunk("0", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell((PdfPCell) table_cell);
                            columnCount++;

                            //total weekly
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style);
                            cell.setCellValue(val);
                            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(val), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell((PdfPCell) table_cell);
                            
                            start = false;
                        }

//                columnCount++;
                    }
                }
                my_table.completeRow();
                attendanceAft.completeRow();
            }
////////////////////////////////
///////////////////////////////

/////////////////////////////////////////////////
            columnCount = 0;
            row = sheet.createRow(rowCount);
            cell = row.createCell(columnCount);
            cell.setCellStyle(style4);
            cell.setCellValue("Night");
            table_cell = new PdfPCell(new Phrase(new Chunk("Night", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            table_cell.setColspan(12);
            table_cell.setHorizontalAlignment(1);
            my_table.addCell(table_cell);
            my_table.completeRow();
            
            table_cell = new PdfPCell(new Phrase(new Chunk("Night", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            table_cell.setColspan(7);
            table_cell.setHorizontalAlignment(1);
            attendanceN.addCell(table_cell);
            attendanceN.completeRow();
            
            sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 11));
            rowCount++;
/////////////////////////////////////////////////

            for (int i = 0; i < theList.size(); i++) {
                boolean firstm = true;
                String name = theList.get(i).getName();
                ArrayList<String> data = theList.get(i).getShifts();
                boolean putName = true;
                boolean first = true;
                boolean room = true;
                columnCount = 0;
                count = 0;
                
                boolean start = false;
                int countHollyDays = 0;
                int alCount = 0;
                for (int j = 0; j < weeks.size(); j++) {
                    
                    if (weeks.get(j).equals(wks.get(wk)) && data.get(j).equals("N")) {
                        String curentShift = data.get(j);
                        String curentWk = weeks.get(j);
                        
                        if (j < 29) {//a logic to start filling week total; the next day changes shift, it indicates the end of the week
                            if (!data.get(j + 1).equals(curentShift) || !weeks.get(j + 1).equals(curentWk)) {
                                start = true;
                            }
                        } else {
                            start = true;
                        }
//                        System.out.println(wks.get(wk) + " " + weeks.get(j) + "............." + data.get(j) + "............" + name);

                        if (putName) {
                            columnCount = 0;
                            row = sheet.createRow(rowCount);
                            rowCount++;
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style2);
                            cell.setCellValue(name);
                            table_cell = new PdfPCell(new Phrase(new Chunk(name, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell((PdfPCell) table_cell);

                            //For attendance
                            table_cell = new PdfPCell(new Phrase(new Chunk(name, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            table_cell.setPaddingTop(10);
                            table_cell.setPaddingBottom(10);
                            attendanceN.addCell(table_cell);
                            putName = false;
                            columnCount++;
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style);
                            cell.setCellValue("");
                            table_cell = new PdfPCell(new Phrase(new Chunk("", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell(table_cell);
                            columnCount++;
                        }
                        cell = row.createCell(columnCount);
                        cell.setCellStyle(style);
                        
                        if (AL.contains(name + " " + (j + 1))) {
                            cell.setCellValue(0);
                            if (weekDays.get(j).equals("Sat") || weekDays.get(j).equals("Sun")) {
                                table_cell = new PdfPCell(new Phrase(new Chunk("0", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            } else {
                                table_cell = new PdfPCell(new Phrase(new Chunk("AL*", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            }
                            
                            my_table.addCell(table_cell);
                            //For attendance
                            table_cell = new PdfPCell(new Phrase(new Chunk("AL", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            attendanceN.addCell(table_cell);
                            alCount++;
                        } else if (hollydays.contains(j + 1)) {
                            cell.setCellValue(24);
                            table_cell = new PdfPCell(new Phrase(new Chunk("24", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell((PdfPCell) table_cell);
                            count++;
                            countHollyDays++;

                            //For attendance
                            table_cell = new PdfPCell(empty_table);
                            attendanceN.addCell(table_cell);
                        } else {
                            cell.setCellValue(12);
                            table_cell = new PdfPCell(new Phrase(new Chunk("12", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell((PdfPCell) table_cell);
                            count++;

                            //For attendance
                            table_cell = new PdfPCell(empty_table);
                            attendanceN.addCell(table_cell);
                        }
                        columnCount++;
                        
                        int cc = 7 - count - alCount;
                        if (start) {
                            
                            for (int jj = 0; jj < cc; jj++) {
                                cell = row.createCell(columnCount);
                                cell.setCellStyle(style);
                                cell.setCellValue("");
                                table_cell = new PdfPCell(new Phrase(new Chunk("", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                                my_table.addCell((PdfPCell) table_cell);
                                columnCount++;

                                //For attendance
                                table_cell = new PdfPCell(new Phrase(new Chunk(">>", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                                table_cell.setHorizontalAlignment(1);
                                attendanceN.addCell(table_cell);
                            }
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style);
                            
                            int val;
                            
                            val = count * 12 + countHollyDays * 12;
                            
                            cell.setCellValue(val);
                            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(val), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell((PdfPCell) table_cell);
                            columnCount++;
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style);
                            cell.setCellValue(0);//tecemari seat
                            table_cell = new PdfPCell(new Phrase(new Chunk("0", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell((PdfPCell) table_cell);
                            columnCount++;

                            //total weekly
                            cell = row.createCell(columnCount);
                            cell.setCellStyle(style);
                            cell.setCellValue(val);
                            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(val), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
                            my_table.addCell((PdfPCell) table_cell);
                            start = false;
                            
                        }

//                columnCount++;
                    }
                    
                }
                
                my_table.completeRow();
                attendanceN.completeRow();
            }
            rowCount++;
            row = sheet.createRow(rowCount);
            rowCount++;
//            sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 11));

            cell = row.createCell(0);
//            columnCount++;
            cell.setCellStyle(style); //Apply style to cell
            header.clear();
            
            String title = "From " + month + " " + firstDay + " to " + month + " " + (lastDay + 1) + " 2015 EC";
            cell.setCellStyle(style3);
            cell.setCellValue(title);
            my_table.completeRow();
            
            table_cell = new PdfPCell(new Phrase(new Chunk(title, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            table_cell.setBorderWidthBottom(0);
            table_cell.setColspan(12);
            table_cell.setBorder(Rectangle.NO_BORDER);
            my_table.addCell(table_cell);
            
            String note = "*AL = Annual Leave";
            table_cell = new PdfPCell(new Phrase(new Chunk(note, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            table_cell.setBorderWidthBottom(0);
            table_cell.setColspan(12);
            table_cell.setBorder(Rectangle.NO_BORDER);
            my_table.addCell(table_cell);
            
            row = sheet.createRow(rowCount);
            rowCount++;
            
            row = sheet.createRow(rowCount);
            rowCount++;
            my_table.completeRow();
            cell = row.createCell(0);
            String signature = "Prepared by: ______________________sign_________ Approved by_______________ sign_________";
            cell.setCellValue(signature);
            my_table.completeRow();
            table_cell = new PdfPCell(new Phrase(new Chunk(signature, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setColspan(12);
            
            table_cell.setBorder(Rectangle.NO_BORDER);
            table_cell.setPaddingTop(30);
            my_table.addCell(table_cell);
            
            pdfR.add(my_table);
            sheet.setFitToPage(true);
            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setFitHeight((short) 0);
            printSetup.setFitWidth((short) 1);
//            outputStream.close();

            table_cell = new PdfPCell(new Phrase(new Chunk("Morning Entrance Time = 8:00am;  Exit Time = 2pm", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setColspan(12);
            table_cell.setBorder(Rectangle.NO_BORDER);
            table_cell.setPaddingTop(15);
            attendance.addCell(table_cell);
            
            table_cell = new PdfPCell(new Phrase(new Chunk("Afternoon Entrance Time = 2:00pm;  Exit Time = 8pm", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setColspan(12);
            table_cell.setBorder(Rectangle.NO_BORDER);
            table_cell.setPaddingTop(15);
            attendanceAft.addCell(table_cell);
            
            table_cell = new PdfPCell(new Phrase(new Chunk("Night Entrance Time = 2:00pm;  Exit Time = 8pm", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setColspan(12);
            table_cell.setBorder(Rectangle.NO_BORDER);
            table_cell.setPaddingTop(15);
            attendanceN.addCell(table_cell);
            
            atendM.add(attendance);
            atendAft.add(attendanceAft);
            atendN.add(attendanceN);
            
            atendM.close();
            atendAft.close();
            atendN.close();
            pdfR.close();
        }
        
        MakeSummary();
        
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
            outputStream.close();
        }

//        new toPdf(month).main();
    }
    
    public static void main(String[] args) throws IOException {
    }
    
    private int getAnualLeave(String name) {
        
        int work_hour_count = 0;
        boolean startCount = false;
        boolean breakit = false;
        for (int i = theList.size()-1; i >=0 ; i--) {
           work_hour_count = 0; 
           startCount = false;
            Data data = theList.get(i);
            System.out.println(name);
            System.out.println(data.getName()+"  //////////////");
            if (data.getName().equals(name)) {
               System.out.println(data.getName()+"  *************"); 
               ArrayList<String> temp = data.getShifts();
                for (int j = 0; j < temp.size(); j++) {
                    if (temp.get(j).equals("AL")) {
                        System.out.println(temp.get(j));
                        startCount = true;
                    } else {
                        if (weekDays.get(j).equals("Fri")) {
                            work_hour_count = work_hour_count + 7;
                        } else if (weekDays.get(j).equals("Sun") || weekDays.get(j).equals("Sat")) {
                        } else {
                            work_hour_count = work_hour_count + 8;
                        }
                    }
                }
                breakit = true;
            }
            if(breakit)
            break;
        }
        System.out.println("Al "+work_hour_count);
        if (startCount) {
            if (work_hour_count > 156) {
                return 156;
            }
            return work_hour_count;
        } else {
            return 156;
        }
        
    }
    
    private void MakeSummary() throws FileNotFoundException, DocumentException, MalformedURLException {
        
        pdfR = new Document();
        frontSummary = new Document();
        String dest = dir2 + "\\" + month + "_Final" + ".pdf";
        String dest2 = dir2 + "\\1" + month + "_summary" + ".pdf";
        pdfR.setPageSize(PageSize.A4.rotate());
        frontSummary.setPageSize(PageSize.A4);
        float m = 450 + pdfR.getPageSize().getWidth() / 2;//determines page margin

        float m2 = 450 + frontSummary.getPageSize().getWidth() / 2;//determines page margin
        pdfR.setMargins(m, m, 50, 50);
        frontSummary.setMargins(m2, m2, 50, 50);
        
        my_table = new PdfPTable(12);
        summary_table = new PdfPTable(3);
        
        float[] columnWidths = new float[]{18f, 75f, 25f, 25f, 25f, 25f, 25f, 25f, 25f, 30f, 26f, 25f};
        float[] columnWidths2 = new float[]{10f, 100f, 25f};
        
        my_table.setWidths(columnWidths);
        summary_table.setWidths(columnWidths2);
        
        outputStream = new FileOutputStream(dest);
        PdfWriter writer = PdfWriter.getInstance(pdfR, outputStream);
        
        PdfWriter writer2 = PdfWriter.getInstance(frontSummary, outputStream);
        
        pdfR.open();
        frontSummary.open();
        
        sheet = workbook.createSheet(month + "_Duty_Report");
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 5000);
        rowCount = 0;
        String[] header = {"No", "Name of Professional", "Hours in the Week", "", "", "", "", "", "Additional Hours", "Monthly Hour", "Expected Hour", "Over Time"};
        String[] header2 = {"No", "Name of Professional", "Additional Hours", "Monthly Hour", "Expected Hour", "Over Time"};
        
        table_cell = null;
        ////////////Title
        columnCount = 0;
        row = sheet.createRow(rowCount);
        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 11));
        rowCount++;
        String title = "Monthly  Duty Hour Reporting Format";
        cell = row.createCell(columnCount);
        cell.setCellStyle(style3);
        cell.setCellValue(title);
        
        table_cell = new PdfPCell(new Phrase(new Chunk(title, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
        table_cell.setColspan(12);
        table_cell.setPaddingTop(5);
        table_cell.setPaddingBottom(5);
        table_cell.setBorder(Rectangle.NO_BORDER);
        my_table.addCell(table_cell);
        my_table.completeRow();
        
        summary_cell = new PdfPCell(new Phrase(new Chunk(title, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
        summary_cell.setColspan(5);
        summary_cell.setPaddingTop(25);
        summary_cell.setPaddingBottom(10);
        summary_cell.setBorder(Rectangle.NO_BORDER);
        summary_table.addCell(summary_cell);
        summary_table.completeRow();

        //summary table header
        summary_cell = new PdfPCell(new Phrase(new Chunk("S.No", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
        summary_cell.setPaddingTop(10);
        summary_cell.setPaddingBottom(10);
        summary_table.addCell(summary_cell);
        
        summary_cell = new PdfPCell(new Phrase(new Chunk("Name of Professional", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
        summary_cell.setPaddingTop(10);
        summary_cell.setPaddingBottom(10);
        summary_table.addCell(summary_cell);
        
        summary_cell = new PdfPCell(new Phrase(new Chunk("Overtime", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
        summary_cell.setPaddingTop(10);
        summary_cell.setPaddingBottom(10);
        summary_table.addCell(summary_cell);
        summary_table.completeRow();

        ///////////// Date of report
        Date date = new Date();
        String strDateFormat = "E MMM-dd,yyyy hh:mm a ";
        SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String dt = dateFormat.format(date);
        
        String intro = "Reporting Date: " + dt + "            Month: " + month + "      year: 2015     Unit: JUMC Student HC";
        
        row = sheet.createRow(rowCount);
        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 11));
        rowCount++;
        cell = row.createCell(columnCount);
        cell.setCellStyle(styler1);
        cell.setCellValue(intro);
        
        table_cell = new PdfPCell(new Phrase(new Chunk(intro, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
        table_cell.setColspan(12);
        table_cell.setBorder(Rectangle.NO_BORDER);
        my_table.addCell(table_cell);
        my_table.completeRow();
        
        row = sheet.createRow(rowCount);
        rowCount++;

        //////////talbe
        row = sheet.createRow(rowCount);
        rowCount++;
        columnCount = 0;
        String[] weeks2 = {"wk1", "wk2", "wk3", "wk4", "wk5", "wk6"};
        
        PdfPTable ptable = new PdfPTable(6);
        PdfPCell pcell = new PdfPCell();
        
        pcell = new PdfPCell(new Phrase(new Chunk("Hours in the Week", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
        pcell.setColspan(6);
        pcell.setHorizontalAlignment(1);
        pcell.setPaddingTop(5);
        pcell.setPaddingBottom(5);
        ptable.addCell(pcell);
        ptable.completeRow();
        for (int j = 0; j < 6; j++) {
            pcell = new PdfPCell(new Phrase(new Chunk(weeks2[j], FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            pcell.setPaddingTop(5);
            pcell.setPaddingBottom(5);
            ptable.addCell(pcell);
        }
        ptable.completeRow();
        
        for (int i = 0; i < 6; i++) {
            if (i == 2) {
                
                table_cell = new PdfPCell(ptable);
                table_cell.setColspan(6);
                my_table.addCell(table_cell);
            }
            table_cell = new PdfPCell(new Phrase(new Chunk(header2[i], FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);
            
        }
        for (int i = 0; i < 12; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(stylerh);
            cell.setCellValue(header[i]);
            
        }
        my_table.completeRow();
        sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount - 1, 2, 7));

        ////////////////// table second row
        columnCount = 0;
        row = sheet.createRow(rowCount);
        sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount, 1, 1));
        
        for (int i = 0; i < 6; i++) {
            cell = row.createCell(i + 2);
            cell.setCellStyle(stylerh);
            cell.setCellValue(weeks2[i]);
            
        }
        
        sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(rowCount - 1, rowCount, 12, 12));
        
        rowCount++;
        ArrayList<String> names = new ArrayList();
        
        for (int c = 0; c < theList.size(); c++) {
            String st = theList.get(c).getName();
            if (!names.contains(st)) {
                names.add(st);
                
            }
        }
        for (int d = 0; d < names.size(); d++) {
            columnCount = 0;
            Data data = theList.get(d);
            row = sheet.createRow(rowCount);
            rowCount++;
            cell = row.createCell(columnCount);
            cell.setCellStyle(styler1);
            cell.setCellValue(d + 1);
            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(d + 1), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);
            columnCount++;
            
            summary_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(d + 1), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD))));
            summary_table.addCell(summary_cell);
            
            cell = row.createCell(columnCount);//for names of professionals
            cell.setCellStyle(styler1);
            cell.setCellValue(data.getName());
            
            table_cell = new PdfPCell(new Phrase(new Chunk(data.getName(), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);
            
            summary_cell = new PdfPCell(new Phrase(new Chunk(data.getName(), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            summary_cell.setPaddingTop(5);
            summary_cell.setPaddingBottom(5);
            summary_table.addCell(summary_cell);
            
            columnCount++;
            
            String name = names.get(d);
            
            int wk1 = 0, wk2 = 0, wk3 = 0, wk4 = 0, wk5 = 0, wk6 = 0;
            for (int i = 0; i < theList.size(); i++) {
                
                if (theList.get(i).getName().equals(name)) {
                    Data data2 = theList.get(i);
                    ArrayList<String> shifts = data2.getShifts();
                    
                    columnCount = 2;
                    
                    for (int j = 0; j < shifts.size(); j++) {
                        
                        if (weeks.get(j).equals("wk1")) {
                            if (AL.contains(name + " " + (j + 1))) {
                                continue;
                            }
                            if (shifts.get(j).equals("M")) {
                                if (hollydays.contains(j)) {
                                    wk1 = wk1 + 12;
                                } else {
                                    wk1 = wk1 + 6;
                                }
                            }
                            if (shifts.get(j).equals("A")) {
                                if (hollydays.contains(j)) {
                                    wk1 = wk1 + 12;
                                } else {
                                    wk1 = wk1 + 6;
                                }
                            }
                            if (shifts.get(j).equals("N")) {
                                if (hollydays.contains(j)) {
                                    wk1 = wk1 + 24;
                                } else {
                                    wk1 = wk1 + 12;
                                }
                            }
                            
                        }
                        if (weeks.get(j).equals("wk2")) {
                            if (AL.contains(name + " " + (j + 1))) {
                                continue;
                            }
                            if (shifts.get(j).equals("M")) {
                                if (hollydays.contains(j)) {
                                    wk2 = wk2 + 12;
                                } else {
                                    wk2 = wk2 + 6;
                                }
                            }
                            if (shifts.get(j).equals("A")) {
                                if (hollydays.contains(j)) {
                                    wk2 = wk2 + 12;
                                } else {
                                    wk2 = wk2 + 6;
                                }
                            }
                            if (shifts.get(j).equals("N")) {
                                if (hollydays.contains(j)) {
                                    wk2 = wk2 + 24;
                                } else {
                                    wk2 = wk2 + 12;
                                }
                            }
                        }
                        if (weeks.get(j).equals("wk3")) {
                            if (AL.contains(name + " " + (j + 1))) {
                                continue;
                            }
                            if (shifts.get(j).equals("M")) {
                                if (hollydays.contains(j)) {
                                    wk3 = wk3 + 12;
                                } else {
                                    wk3 = wk3 + 6;
                                }
                            }
                            if (shifts.get(j).equals("A")) {
                                if (hollydays.contains(j)) {
                                    wk3 = wk3 + 12;
                                } else {
                                    wk3 = wk3 + 6;
                                }
                            }
                            if (shifts.get(j).equals("N")) {
                                if (hollydays.contains(j)) {
                                    wk3 = wk3 + 24;
                                } else {
                                    wk3 = wk3 + 12;
                                }
                            }
                        }
                        if (weeks.get(j).equals("wk4")) {
                            if (AL.contains(name + " " + (j + 1))) {
                                continue;
                            }
                            if (shifts.get(j).equals("M")) {
                                if (hollydays.contains(j)) {
                                    wk4 = wk4 + 12;
                                } else {
                                    wk4 = wk4 + 6;
                                }
                            }
                            if (shifts.get(j).equals("A")) {
                                if (hollydays.contains(j)) {
                                    wk4 = wk4 + 12;
                                } else {
                                    wk4 = wk4 + 6;
                                }
                            }
                            if (shifts.get(j).equals("N")) {
                                if (hollydays.contains(j)) {
                                    wk4 = wk4 + 24;
                                } else {
                                    wk4 = wk4 + 12;
                                }
                            }
                        }
                        if (weeks.get(j).equals("wk5")) {
                            if (AL.contains(name + " " + (j + 1))) {
                                continue;
                            }
                            if (shifts.get(j).equals("M")) {
                                if (hollydays.contains(j)) {
                                    wk5 = wk5 + 12;
                                } else {
                                    wk5 = wk5 + 6;
                                }
                            }
                            if (shifts.get(j).equals("A")) {
                                if (hollydays.contains(j)) {
                                    wk5 = wk5 + 12;
                                } else {
                                    wk5 = wk5 + 6;
                                }
                            }
                            if (shifts.get(j).equals("N")) {
                                if (hollydays.contains(j)) {
                                    wk5 = wk5 + 24;
                                } else {
                                    wk5 = wk5 + 12;
                                }
                            }
                        }
                        if (weeks.get(j).equals("wk6")) {
                            if (AL.contains(name + " " + (j + 1))) {
                                continue;
                            }
                            if (shifts.get(j).equals("M")) {
                                if (hollydays.contains(j)) {
                                    wk6 = wk6 + 12;
                                } else {
                                    wk6 = wk6 + 6;
                                }
                            }
                            if (shifts.get(j).equals("A")) {
                                if (hollydays.contains(j)) {
                                    wk6 = wk6 + 12;
                                } else {
                                    wk6 = wk6 + 6;
                                }
                            }
                            if (shifts.get(j).equals("N")) {
                                if (hollydays.contains(j)) {
                                    wk6 = wk6 + 24;
                                } else {
                                    wk6 = wk6 + 12;
                                }
                            }
                        }
                        
                    }
                    
                }
            }
            cell = row.createCell(columnCount);//for names of professionals

            cell.setCellStyle(style2);
            cell.setCellValue(wk1);
            columnCount++;
            
            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(wk1), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);
            
            cell = row.createCell(columnCount);//for names of professionals
            cell.setCellStyle(style2);
            cell.setCellValue(wk2);
            columnCount++;
            
            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(wk2), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);
            
            cell = row.createCell(columnCount);//for names of professionals
            cell.setCellStyle(style2);
            cell.setCellValue(wk3);
            columnCount++;
            
            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(wk3), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);
            
            cell = row.createCell(columnCount);//for names of professionals
            cell.setCellStyle(style2);
            cell.setCellValue(wk4);
            columnCount++;
            
            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(wk4), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);
            
            cell = row.createCell(columnCount);//for names of professionals
            cell.setCellStyle(style2);
            cell.setCellValue(wk5);
            columnCount++;
            
            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(wk5), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);
            
            cell = row.createCell(columnCount);//for names of professionals
            cell.setCellStyle(style2);
            cell.setCellValue(wk6);
            columnCount++;
            
            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(wk6), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);
            
            cell = row.createCell(columnCount);//for additional hours
            cell.setCellStyle(style2);
            cell.setCellValue(0);
            columnCount++;
            
            table_cell = new PdfPCell(new Phrase(new Chunk("0", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);
            
            int sum = wk6 + wk5 + wk4 + wk3 + wk2 + wk1;
            ////Total hour
            cell = row.createCell(columnCount);//for total hours
            cell.setCellStyle(style2);
            cell.setCellValue(sum);
            columnCount++;
            
            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(sum), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);

//            int A_leave = 156 - getAnualLeave(data.getName());
            int A_leave = getAnualLeave(data.getName());
            
            cell = row.createCell(columnCount);//expected hour
            cell.setCellStyle(style2);
            cell.setCellValue(A_leave);
            columnCount++;
            
            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(A_leave), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);
            
            cell = row.createCell(columnCount);//expected hour
            cell.setCellStyle(style2);
            cell.setCellValue(sum - A_leave);
            columnCount++;
            
            table_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(sum - A_leave), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            table_cell.setPaddingTop(5);
            table_cell.setPaddingBottom(5);
            my_table.addCell(table_cell);
            my_table.completeRow();
            
            summary_cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(sum - A_leave), FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
            summary_cell.setPaddingTop(5);
            summary_cell.setPaddingBottom(5);
            summary_table.addCell(summary_cell);
            summary_table.completeRow();
            
        }
        row = sheet.createRow(rowCount);
        rowCount++;
        
        row = sheet.createRow(rowCount);
        rowCount++;
        
        cell = row.createCell(0);
        String signature = "Prepared by: ______________________sign_________ Approved by_______________ sign_________";
        cell.setCellValue(signature);
        
        table_cell = new PdfPCell(new Phrase(new Chunk(signature, FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
        table_cell.setColspan(12);
        table_cell.setBorder(Rectangle.NO_BORDER);
        table_cell.setPaddingTop(20);
        my_table.addCell(table_cell);
        
        FileOutputStream outputStream2 = new FileOutputStream(dest2);
        Document dc = new Document();
        PdfPTable ltable = new PdfPTable(3);
        PdfWriter pw = PdfWriter.getInstance(dc, outputStream2);
        
        dc.open();
        
        table_cell = new PdfPCell(new Phrase(new Chunk("Department Head", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
        table_cell.setPaddingTop(5);
        table_cell.setPaddingBottom(5);
        table_cell.setBorder(Rectangle.NO_BORDER);
        ltable.addCell(table_cell);
        
        table_cell = new PdfPCell(new Phrase(new Chunk("           ", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
        table_cell.setPaddingTop(5);
        table_cell.setPaddingBottom(5);
        table_cell.setBorder(Rectangle.NO_BORDER);
        ltable.addCell(table_cell);
        
        table_cell = new PdfPCell(new Phrase(new Chunk("Chief Clinical Director", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
        table_cell.setPaddingTop(5);
        table_cell.setPaddingBottom(5);
        table_cell.setBorder(Rectangle.NO_BORDER);
        ltable.addCell(table_cell);
        ltable.completeRow();
        
        table_cell = new PdfPCell(new Phrase(new Chunk("__________________", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
        table_cell.setPaddingTop(20);
        table_cell.setPaddingBottom(5);
        table_cell.setBorder(Rectangle.NO_BORDER);
        ltable.addCell(table_cell);
        
        table_cell = new PdfPCell(new Phrase(new Chunk("           ", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
        table_cell.setPaddingTop(5);
        table_cell.setPaddingBottom(5);
        table_cell.setBorder(Rectangle.NO_BORDER);
        ltable.addCell(table_cell);
        
        table_cell = new PdfPCell(new Phrase(new Chunk("__________________", FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL))));
        table_cell.setPaddingTop(20);
        table_cell.setPaddingBottom(5);
        table_cell.setBorder(Rectangle.NO_BORDER);
        ltable.addCell(table_cell);
        
        table_cell = new PdfPCell(ltable);
        table_cell.setPaddingTop(20);
        table_cell.setColspan(3);
        table_cell.setBorder(Rectangle.NO_BORDER);
        table_cell.setBorder(Rectangle.NO_BORDER);
        summary_table.addCell(table_cell);
        dc.add(summary_table);
        dc.close();
        
        pdfR.add(my_table);
        pdfR.close();
        
        new PDFMerge().meargePDF(dir2, dir + month + "_Final_Report");
        new PDFMerge().meargePDF(attendanceDir, dir + month + "_Attendance");
        
        new DeleteDirectory().deleteDirectory(tempfiledir);
        
    }
}
