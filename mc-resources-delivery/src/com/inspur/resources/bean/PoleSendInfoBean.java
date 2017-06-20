// 

// 

package com.inspur.resources.bean;

public class PoleSendInfoBean
{
    private String dotype;
    private String id;
    private String poleId;
    private String sendXML;
    private String username;
    
    public PoleSendInfoBean() {
        this.id = null;
        this.username = null;
        this.poleId = null;
        this.sendXML = null;
        this.dotype = null;
    }
    
    public String getDotype() {
        return this.dotype;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getPoleId() {
        return this.poleId;
    }
    
    public String getSendXML() {
        return this.sendXML;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setDotype(final String dotype) {
        this.dotype = dotype;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public void setPoleId(final String poleId) {
        this.poleId = poleId;
    }
    
    public void setSendXML(final String sendXML) {
        this.sendXML = sendXML;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
}
