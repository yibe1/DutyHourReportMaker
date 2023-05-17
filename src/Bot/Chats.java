/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

/**
 *
 * @author Yibe2
 */
public class Chats {
    private String chatId;
    private String request;
    private String response;

    public Chats(String chatId) {
        this.chatId = chatId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    
    
}
