/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

/**
 *
 * @author Yibe2
 */
import Bot.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXReader {

    private ArrayList<ArrayList<Pair>> data = new ArrayList<>();
//    private ArrayList<String> header = new ArrayList<>();
    String[] header = {"Name", "Week1", "Week2", "Week3", "Week4", "Week5", "Week6", "Additional Hours", "Monthly Hours", "Expected Hours", "Overtime"};
    boolean start = true;

    public ArrayList GetData(String month, String year) {
        try {
            File file = new File("Duty_Report\\"+year +"\\"+month+"\\"+ month + "_Report.xlsx");   //creating a new file instance  
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
//creating Workbook instance that refers to .xlsx file  
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheet(month+"_Duty_Report");     //creating a Sheet object to retrieve object  
            
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
            int counter = 0;
//            while (itr.hasNext()) {
//                String values = null;
//                Row row = itr.next();
//               if(counter<3){
//                   counter++;
//                   continue;
//               }
//                counter++;
//
//                boolean first = true;
//                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column 
//                if (first) {
//                    first = false;
//                    continue;
//                }
//                if (start) {
//
////                    while (cellIterator.hasNext()) {
////
////                        Cell cell = cellIterator.next();
////
////                        CellType x = cell.getCellTypeEnum();
////                        if (x == CellType.STRING) {
////                            values = cell.getStringCellValue();
////                        } else {
////                            values = String.valueOf(cell.getNumericCellValue());
////                        }
////                        System.out.println("vvvvvvvvvvvvvv----------" + values);
////                        header.add(values);
////                    }
//                     for (int i = 0; i < 10; i++) {
//                        
//                    }
//                    start = false;
//                }
//                break;
//            }

            while (itr.hasNext()) {
                String values = null;
                Row row = itr.next();
                if (counter < 4) {
                    counter++;
                    continue;
                }
                counter++;

                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column 

                Pair map = new Pair();
                ArrayList<Pair> pairList = new ArrayList();
                int i = 0;
                boolean first = true;
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    if (first) {
                        first = false;
                        continue;
                    }
                    CellType x = cell.getCellTypeEnum();
                    if (x == CellType.STRING) {
                        values = cell.getStringCellValue();
                    } else {
                        values = String.valueOf((int) cell.getNumericCellValue());
                    }
                    System.out.println(header[i] + ",,,,,,,,,,,,,,,,,,    " + values);
                    map.addValue(header[i], values);
                    pairList.add(map);
                    i++;

                }
                data.add(pairList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
