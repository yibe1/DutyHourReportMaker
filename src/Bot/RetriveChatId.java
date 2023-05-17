/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Jimma University
 */
public class RetriveChatId {
    String fullName;
    String chatId;

    RetriveChatId(String fullName) {
        this.fullName = fullName;
    }

    public String getChatId() throws FileNotFoundException, IOException{
        String chatid = null;
        
        File file = new File("chid");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        while(br.ready()){
            System.out.println("started reading chid.....");
            String st = br.readLine();
            String name = st.substring(0,st.indexOf(','));
            if(name.equals(fullName)){
               chatid = st.substring(st.indexOf(',')+2);
            }
        }
        br.close();
        return chatid;
    }

}
