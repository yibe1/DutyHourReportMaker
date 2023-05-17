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
public class chatProperties {
    private String chatId;
    private String user;
    private int waiting;
    private boolean firstProb = true;

    public chatProperties(String chatId, int waiting, String gname) {
        this.chatId = chatId;
        this.waiting = waiting;
        this.user = gname;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public boolean isFirstProb() {
        return firstProb;
    }

    public void setFirstProb(boolean firstProb) {
        this.firstProb = firstProb;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    
}
