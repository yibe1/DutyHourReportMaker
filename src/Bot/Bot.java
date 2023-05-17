/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author Jimma University
 */
public class Bot extends TelegramLongPollingBot {

    boolean start = true;
    int waiting = 0;
    String name, month, year;
    private boolean firstgreet = true;
    private boolean firstProb = true;
    private ArrayList<String> idList = new ArrayList();//to prevent creation of duplicate chatid, we store a unique chatid
    private ArrayList<Chats> chatList = new ArrayList();//stor conversation in chat class mode
    private ArrayList<chatProperties> chatProp = new ArrayList<>();// all information about current session
    JTextArea talks;
    private SendMessage message;

    public Bot(JTextArea talks, String year) {
        this.talks = talks;
        this.year = year;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // TODO
        String qs = null;
        Chatbot bot = new Chatbot();
        String guest_name;
        if (update.hasMessage() && update.getMessage().hasText()) {

            try {

                message = new SendMessage(); // Create a SendMessage object with mandatory fields
                String chatId = update.getMessage().getChatId().toString();

                Chats chats = new Chats(chatId);
                message.setChatId(update.getMessage().getChatId().toString());
                guest_name = update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName();
                registerChatID(guest_name, chatId);
//            add_user(update.getMessage().getFrom().getFirstName() + "_" + update.getMessage().getFrom().getLastName());
                String rs = update.getMessage().getText();
                chats.setRequest(rs);
                String response = null;
                String gr = "";
                if (!idList.contains(chatId)) {
                    chatProp.add(new chatProperties(chatId, 0, guest_name));
                    String req = "my name is " + update.getMessage().getFrom().getFirstName();
                    talks.setText("Human: " + talks.getText() + "\n" + req);
                    gr = bot.response(req);
                    talks.setText("Bot: " + talks.getText() + "\n" + gr);
                    message.setText(gr);
                    try {
                        chatList.add(chats);
                        execute(message);
                    } catch (TelegramApiException ex) {
                        Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    idList.add(chatId);
//                firstgreet = false;
                } else {
                    waiting = getProperties(chatId).getWaiting();
                }

                String month = null;
                switch (waiting) {
                    case 0:
                        if (rs.equals("A") || rs.equals("a")) {
                            message.setText("Ok I will assist you in your STI treatment using syndromic approach"
                                    + "\nNow Tell me the Gender of the patient");
                            getProperties(chatId).setWaiting(2);
                        } else if (rs.equals("B") || rs.equals("b")) {
                            message.setText("\tSelect Month\n"
                                    + "\t1) መስከረም 2015ዓ.ም\n"
                                    + "\t2) ጥቅምት 2015ዓ.ም\n"
                                    + "\t3) ህዳር 2015ዓ.ም\n"
                                    + "\t4) ታህሳስ 2015ዓ.ም\n"
                                    + "\t5) ጥር 2015ዓ.ም\n"
                                    + "\t6) የካቲት 2015ዓ.ም\n"
                                    + "\t7) መጋቢት 2015ዓ.ም\n"
                                    + "\t8) ሚያዝያ 2015ዓ.ም\n"
                                    + "\t9) ግንቦት 2015ዓ.ም\n"
                                    + "\t10) ሰኔ 2015ዓ.ም\n"
                                    + "\t11) ሐምሌ 2015ዓ.ም\n"
                                    + "\t12) ነሐሴ 2015ዓ.ም\n"
                            );
                            getProperties(chatId).setWaiting(1);
                        } else if (rs.equals("C") || rs.equals("c")) {
                            getProperties(chatId).setWaiting(3);
                            System.out.println("Sending................");
                        } else if (rs.equals("D") || rs.equals("d")) {
                            message.setText("Enter pass phrase");
                            getProperties(chatId).setWaiting(5);
                            System.out.println("Sending................");
                        } else {
                            if (getProperties(chatId).isFirstProb()) {
                                message.setText("Please select from the options:\n\t"
                                        + "A) STI Guideline"
                                        + "\nB) Duty inquiry"
                                        + "\nC) Your Profile"
                                        + "\nD) Send Duty result to all");
                                firstProb = false;
                                getProperties(chatId).setFirstProb(false);
                            } else {
                                message.setText(response);
                            }
                        }
                        break;
                    case 1:

                        ArrayList list = new XLSXReader().GetData(getMonth(rs), year);
                        String hr = "";
                        boolean success = false;
                        if (list.isEmpty()) {
                            message.setText("Sorry, the information you requested is not available for now\n"
                                    + "Please select from the options:\n\t"
                                    + "A) STI Guideline\n"
                                    + "B) Duty inquiry"
                                    + "\nC) Your Profile");
                            firstProb = true;
                            getProperties(chatId).setWaiting(0);
                            break;
                        } else {
                            for (int i = 0; i < list.size(); i++) {
                                ArrayList<Pair> theList = (ArrayList<Pair>) list.get(i);
                                if (theList.get(i).getValue("Name").equals(guest_name)) {
                                    hr = "Your duty hours are as follows:\nName = " + theList.get(i).getValue("Name") + "\n\tWeek1 = " + theList.get(0).getValue("Week1")
                                            + "\n\tWeek2 = " + theList.get(i).getValue("Week2")
                                            + "\n\tWeek3 = " + theList.get(i).getValue("Week3")
                                            + "\n\tWeek4 = " + theList.get(i).getValue("Week4")
                                            + "\n\tWeek5 = " + theList.get(i).getValue("Week5")
                                            + "\n\tWeek6 = " + theList.get(i).getValue("Week6")
                                            + "\n\tAditional hours = " + theList.get(i).getValue("Additional Hours")
                                            + "\n\tYour total hour is = " + theList.get(i).getValue("Monthly Hours")
                                            + "\n\tExpected Work Hour = " + theList.get(i).getValue("Expected Hours")
                                            + "\n\tOvertime(" + theList.get(i).getValue("Monthly Hours") + " - " + theList.get(i).getValue("Expected Hours") + ") = " + theList.get(i).getValue("Overtime")
                                            + "\nThank you for visiting";
                                    message.setText(hr);
                                    success = true;
                                    break;
                                }

                            }
                            if (!success) {
                                message.setText("Sorry your name not found on the database. \nI will report to the admin"
                                        + "\nPlease select from the options:"
                                        + "\nA) STI Guideline"
                                        + "\nB) Duty inquiry\n"
                                        + "\nC) Your Profile"
                                );
                                getProperties(chatId).setWaiting(0);
                            }
                            //                    waiting = 0;
                            getProperties(chatId).setWaiting(0);
                        }

                        break;
                    case 2:
                        if (rs.toLowerCase().equals("male") || rs.toLowerCase().equals("m")) {
                            message.setText("Sorry this feuture is under construction"
                                    + "\nPlease select from the options:"
                                    + "\nA) STI Guideline"
                                    + "\nB) Duty inquiry\n"
                                    + "\nC) Your Profile"
                            );
                            getProperties(chatId).setWaiting(0);
                        } else if (rs.toLowerCase().equals("female") || rs.toLowerCase().equals("f")) {
                            message.setText("Sorry this feuture is under construction"
                                    + "\nPlease select from the options:"
                                    + "\nA) STI Guideline"
                                    + "\nB) Duty inquiry\n"
                                    + "\nC) Your Profile"
                            );
                        }
                        break;
                    case 3:
                        System.out.println("Redirected..............");
                        String[] profile_header = {"First Name", "Middle Name", "Last Name", "Gender", "Birth Date", "Employment Date", "Job Position", "ID Number", "JEG Level", "Salary"};
                        ArrayList profile = new XLSXReader().GetProfile();
                        for (int i = 0; i < profile.size(); i++) {
                            ArrayList<Pair> theList = (ArrayList<Pair>) profile.get(i);
                            System.out.println(theList.get(i).getValue("Name") + "          " + guest_name);
                            String fullName = theList.get(i).getValue("First Name") + theList.get(i).getValue("Middle Name");
                            if (fullName.equals(guest_name)) {
                                hr = "Your duty hours are as follows:\n";
                                for (int j = 0; j < theList.size(); j++) {
                                    hr = hr + profile_header[j] + " = " + theList.get(i).getValue(profile_header[j]) + "\n\t";
                                }
                                hr = hr + "\nThank you for visiting";
                                message.setText(hr);
                                success = true;
                                break;
                            }
                        }
                        break;

                    case 5:
                        if (rs.equals("ok")) {
                            ArrayList list2 = new XLSXReader().GetData("January", year);
                            for (int i = 0; i < list2.size(); i++) {
                                ArrayList<Pair> theList = (ArrayList<Pair>) list2.get(i);
                                String localName = theList.get(i).getValue("Name");
                                RetriveChatId temp = new RetriveChatId(localName);
                                String chid = temp.getChatId();
                                System.out.println("chat id is: " + chid);
                                hr = "Your duty hours are as follows:\nName = " + localName + "\n\tWeek1 = " + theList.get(0).getValue("Week1")
                                        + "\n\tWeek2 = " + theList.get(i).getValue("Week2")
                                        + "\n\tWeek3 = " + theList.get(i).getValue("Week3")
                                        + "\n\tWeek4 = " + theList.get(i).getValue("Week4")
                                        + "\n\tWeek5 = " + theList.get(i).getValue("Week5")
                                        + "\n\tWeek6 = " + theList.get(i).getValue("Week6")
                                        + "\n\tAditional hours = " + theList.get(i).getValue("Additional Hours")
                                        + "\n\tYour total hour is = " + theList.get(i).getValue("Monthly Hours")
                                        + "\n\tExpected Work Hour = " + theList.get(i).getValue("Expected Hours")
                                        + "\n\tOvertime(" + theList.get(i).getValue("Monthly Hours") + " - " + theList.get(i).getValue("Expected Hours") + ") = " + theList.get(i).getValue("Overtime")
                                        + "\nThank you for visiting";
                                message.setText(hr);
                                System.out.println("message set");

                                if (chid != null) {
                                    message.setChatId(chid);
                                    try {

                                        execute(message);
                                        System.out.println("Message sent......");
                                    } catch (TelegramApiException ex) {
                                        JOptionPane.showMessageDialog(null, "chid =" + chid + "\n" + ex);
                                    }
                                } else {
                                    System.out.println("Null value.................");
                                }

                            }

                        } else {
                            message.setText("Wrong pass phrase");
                            System.out.println("wrong pass phrase");
                        }
                        getProperties(chatId).setWaiting(0);
                        break;
                    default:
                        getProperties(chatId).setWaiting(0);
                        break;
                }
                try {
                    execute(message); // Call method to send the message
                } catch (TelegramApiException ex) {
                    Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {
                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @Override
    public String getBotUsername() {
        // TODO
        return "Yibe1481_bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "5788311306:AAEbXxZxFWxcGJiP2nJuWM5TLjWaqByH4cs";
    }

    public static void main(String[] args) throws TelegramApiException {

    }

    public void registerChatID(String fullName, String chatId) throws IOException {
        ArrayList<String> chatIds = new ArrayList();
        File file = new File("chid");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        while (br.ready()) {
            chatIds.add(br.readLine());
        }
        br.close();
        String idInfo = fullName + ", " + chatId;
        if (!chatIds.contains(idInfo)) {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(idInfo);
            bw.close();
        }

    }

    public void addKnowledge(String string) throws IOException {
        File file = new File("C:/Users/Jimma University/OneDrive/Documents/NetBeansProjects/Tel/src/tel/kb.pl");
        FileWriter wr = new FileWriter(file, true);
        BufferedWriter bwr = new BufferedWriter(wr);

        bwr.append(string);
        bwr.close();

    }

    private void add_user(String guest_name) {
        File file = new File("C:/Users/Yibe2/Documents/NetBeansProjects/Tel/bots/super/aimlif/" + guest_name + ".csv");
        if (file.exists()) {
            JOptionPane.showMessageDialog(null, "File exits!");
        } else {
            JOptionPane.showMessageDialog(null, file.getName() + " File does not exits!");
        }
    }

    private chatProperties getProperties(String chatId) {

        for (int i = 0; i < chatProp.size(); i++) {
            chatProperties prop = chatProp.get(i);
            if (prop.getChatId() == null ? chatId == null : prop.getChatId().equals(chatId)) {
                return prop;
            }
        }
        return null;
    }

    private String getMonth(String rs) {
        String month = null;
        switch (rs) {
            case "1":
                month = "September";
                break;
            case "2":
                month = "October";
                break;
            case "3":
                month = "November";
                break;
            case "4":
                month = "December";
                break;
            case "5":
                month = "January";
                break;
            case "6":
                month = "February";
                break;
            case "7":
                month = "March";
                break;
            case "8":
                month = "April";
                break;
            case "9":
                month = "May";
                break;
            case "10":
                month = "June";
                break;
            case "11":
                month = "July";
                break;
            case "12":
                month = "August";
                break;
        }
        return month;
    }
}
