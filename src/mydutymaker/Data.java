/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mydutymaker;

import java.util.ArrayList;

/**
 *
 * @author Yibe2
 */
public class Data {
    private String name;//shift and name
    private ArrayList<String> shifts;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getShifts() {
        return shifts;
    }

    public void setShifts(ArrayList<String> shifts) {
        this.shifts = shifts;
    }
    
}
