/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Jimma University
 */
public class IndividualShiftStatus {

    private String full_name;
    private int morning_count;
    private int afternoon_count;
    private int night_count;
    private int day_count;
    private int bid_night_count;

    ArrayList<IndividualShiftStatus> list = new ArrayList<>();
    ArrayList<Employe> empList = new ArrayList<>();

    IndividualShiftStatus(ArrayList<Employe> empList) {

        this.empList = empList;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getMorning_count() {
        return morning_count;
    }

    public void setMorning_count(int morning_count) {
        this.morning_count = morning_count;
    }

    public int getAfternoon_count() {
        return afternoon_count;
    }

    public void setAfternoon_count(int afternoon_count) {
        this.afternoon_count = afternoon_count;
    }

    public int getNight_count() {
        return night_count;
    }

    public void setNight_count(int night_count) {
        this.night_count = night_count;
    }

    public int getDay_count() {
        return day_count;
    }

    public void setDay_count(int day_count) {
        this.day_count = day_count;
    }

    public int getBid_night_count() {
        return bid_night_count;
    }

    public void setBid_night_count(int bid_night_count) {
        this.bid_night_count = bid_night_count;
    }

    String getMorningEligible() throws IOException {
        updateList(empList);
        int min = empList.get(0).getMcount();
        String name = empList.get(0).getFname() + " " + empList.get(0).getMname() + " " + empList.get(0).getLname();
        for (int i = 1; i < empList.size(); i++) {
            if (empList.get(i).getMcount() < min) {
                min = empList.get(i).getMcount();
                name = empList.get(i).getFname() + " " + empList.get(i).getMname() + " " + empList.get(i).getLname();
            }
        }

        return name;
    }

    String getAfternoonEligible() throws IOException {
        updateList(empList);
        int min = empList.get(0).getAcount();
        String name = empList.get(0).getFname() + " " + empList.get(0).getMname() + " " + empList.get(0).getLname();
        for (int i = 1; i < empList.size(); i++) {
            if (empList.get(i).getAcount() < min) {
                min = empList.get(i).getAcount();
                name = empList.get(i).getFname() + " " + empList.get(i).getMname() + " " + empList.get(i).getLname();
            }
        }
        System.out.println("naem......." + name);
        return name;
    }

    String getNightEligible() throws IOException {
        updateList(empList);
        int min = empList.get(0).getNcount();
        String name = empList.get(0).getFname() + " " + empList.get(0).getMname() + " " + empList.get(0).getLname();
        for (int i = 1; i < empList.size(); i++) {
            if (empList.get(i).getNcount() < min) {
                min = empList.get(i).getNcount();
                name = empList.get(i).getFname() + " " + empList.get(i).getMname() + " " + empList.get(i).getLname();
            }
        }

        return name;
    }

    public void updateList(ArrayList empList) throws IOException {

        this.empList = empList;
    }

    String getBidNightEligible() throws IOException {
        updateList(empList);
        int min = empList.get(0).getBidncount();
        String name = empList.get(0).getFname() + " " + empList.get(0).getMname() + " " + empList.get(0).getLname();
        for (int i = 1; i < empList.size(); i++) {
            if (empList.get(i).getBidncount() < min) {
                min = empList.get(i).getBidncount();
                name = empList.get(i).getFname() + " " + empList.get(i).getMname() + " " + empList.get(i).getLname();
            }
        }

        return name;
    }

    String getDayEligible() throws IOException {
        updateList(empList);
        int min = empList.get(0).getDcount();
        String name = empList.get(0).getFname() + " " + empList.get(0).getMname() + " " + empList.get(0).getLname();
        for (int i = 1; i < empList.size(); i++) {
            if (empList.get(i).getDcount() < min) {
                min = empList.get(i).getDcount();
                name = empList.get(i).getFname() + " " + empList.get(i).getMname() + " " + empList.get(i).getLname();
            }
        }

        return name;
    }

}
