/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mydutymaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Yibe2
 */
public class MyDutyMaker {

    String firstDayofMonth;
    int dayofMonth;
    int Weeks;
    String month;
    ArrayList<Data> theList = new ArrayList();
    ArrayList<String> weeks = new ArrayList();
    ArrayList<String> weekDays = new ArrayList();
    private ArrayList<String> names = new ArrayList();
    private ArrayList<Integer> hollydays;
    private String inst;

    public MyDutyMaker(String month, ArrayList<Integer> hollydays, String inst) {

        this.month = month;
        this.hollydays = hollydays;
        this.inst = inst;

    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//        
//        new MyDutyMaker("December");
//    }
    public ArrayList<Data> Read(String year) throws FileNotFoundException, IOException, Exception {
//        String st = "G:/Other computers/My Computer/Office/Duty related/Schedules/2015/" + month + ".xlsx";
        String st = "2015/" + month + ".xlsx";
        File file = new File(st);   //creating a new file instance  
        FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
//creating Workbook instance that refers to .xlsx file  
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
        Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
        boolean jump = true;
        ArrayList<String> shift = new ArrayList();
        while (itr.hasNext()) {
            if (jump) {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column 
                boolean jump2 = true;
                while (cellIterator.hasNext()) {

                    String val;
                    Cell cell = cellIterator.next();
                    val = cell.getStringCellValue();
                    if (jump2) {
                        jump2 = false;
                        continue;
                    }
                    weeks.add(val);
                }
                row = itr.next();
                cellIterator = row.cellIterator();
                jump2 = true;
                int i = 1;
                shift = new ArrayList();
                while (cellIterator.hasNext()) {
                    String val;
                    Cell cell = cellIterator.next();
                    CellType x = cell.getCellTypeEnum();
                    if (x == CellType.STRING) {
                        val = cell.getStringCellValue();
                    } else {
                        val = String.valueOf(cell.getNumericCellValue());
                    }
                    if (jump2) {
                        jump2 = false;
                        continue;
                    }
                    weekDays.add(val);

                    i++;

                }

                jump = false;
//                for (int j = 0; j < 10; j++) {
//                    System.out.println(weeks.get(j) + ".........." + weekDays.get(j) + "................." + j);
//                }
            }

            Row row = itr.next();
            Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column 
            int count = 1;
            boolean jump2 = true;
            shift = new ArrayList();
            Data data = new Data();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                CellType x = cell.getCellTypeEnum();
                String values;
                if (x == CellType.STRING) {
                    values = cell.getStringCellValue();
                } else {
                    values = String.valueOf(cell.getNumericCellValue());
                }

                if (jump2) {//because the first value in the row is name
                    data.setName(values);
                    jump2 = false;
                    continue;
                }

                shift.add(values);//shift comfirmed

            }

            data.setShifts(shift);
            shift = new ArrayList();
            theList.add(data);
            data = new Data();
        }
        for (int i = 0; i < theList.size(); i++) {
            Data data = (Data) theList.get(i);
        }
        new WriteExcell(theList, weekDays, weeks, month, hollydays, inst).write(year);
        return theList;
    }

}
