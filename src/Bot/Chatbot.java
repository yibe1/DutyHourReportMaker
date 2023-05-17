/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

/**
 *
 * @author Jimma University
 */
import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;

public class Chatbot {

    private static final boolean TRACE_MODE = false;
    static String botName = "super";
    private String response;
    String st = "C:/Users/Jimma University/OneDrive/Documents/NetBeansProjects/Tel";
//    String st = "C:/Users/Jimma University/OneDrive/Documents/NetBeansProjects/Tel";

    public String response(String request) {
        try {

            String resourcesPath = st;
//            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot("super", resourcesPath);
            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();

//            bot.writeAIMLFiles();
                if (MagicBooleans.trace_mode) {
                    System.out.println(
                            "STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
                            + ":TOPIC=" + chatSession.predicates.get("topic"));
                }
                response = chatSession.multisentenceRespond(request);
                while (response.contains("&lt;")) {
                    response = response.replace("&lt;", "<");
                }
                while (response.contains("&gt;")) {
                    response = response.replace("&gt;", ">");
                }
                System.out.println("Robot : " + response);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return response;
    }

    private static String getResourcesPath(){
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        System.out.println("///////////////////////// "+resourcesPath);
        return resourcesPath;
    }
}
