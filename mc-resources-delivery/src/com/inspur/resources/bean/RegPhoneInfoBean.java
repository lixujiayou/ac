// 

// 

package com.inspur.resources.bean;

import java.io.Serializable;

public class RegPhoneInfoBean implements Serializable
{
    private static final long serialVersionUID = 5792573220382441648L;
    private String description;
    private boolean issuccess;
    private String power;
    private String server;
    
    public RegPhoneInfoBean() {
        this.issuccess = false;
        this.description = null;
        this.server = null;
        this.power = null;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getPower() {
        return this.power;
    }
    
    public String getServer() {
        return this.server;
    }
    
    public boolean isIssuccess() {
        return this.issuccess;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public void setIssuccess(final boolean issuccess) {
        this.issuccess = issuccess;
    }
    
    public void setPower(final String power) {
        this.power = power;
    }
    
    public void setServer(final String server) {
        this.server = server;
    }
}
