// 

// 

package com.inspur.resources.database.databasebeans;

public class SendInfoBean
{
    private String dotype;
    private String id;
    private String password;
    private String sendMessage;
    
    public SendInfoBean() {
        this.id = null;
        this.password = null;
        this.sendMessage = null;
        this.dotype = null;
    }
    
    public String getDotype() {
        return this.dotype;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getSendMessage() {
        return this.sendMessage;
    }
    
    public void setDotype(final String dotype) {
        this.dotype = dotype;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public void setSendMessage(final String sendMessage) {
        this.sendMessage = sendMessage;
    }
}
