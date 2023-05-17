/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import java.util.ArrayList;

/**
 *
 * @author Jimma University
 */
public class Pair{
     private ArrayList<String> key = new ArrayList();
     private ArrayList<String> value = new ArrayList();

    
 
    public String getValue(String k) {
        String val = null;
        for (int i = 0; i < key.size(); i++) {
            if(k.equals(key.get(i)))return value.get(i);
        }
        return val;
    }

    public int addValue(String k, String v) {
        if(key.contains(k))return -1;
       key.add(k);
       value.add(v);
         return 0;
    }
     
     
 }
