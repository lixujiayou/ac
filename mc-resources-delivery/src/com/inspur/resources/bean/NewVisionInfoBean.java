// 

// 

package com.inspur.resources.bean;

public class NewVisionInfoBean
{
    private String visionNote;
    private String visionNum;
    private String visionSize;
    private String visionTime;
    private String visionurl;
    
    public NewVisionInfoBean() {
        this.visionNum = null;
        this.visionNote = null;
        this.visionTime = null;
        this.visionSize = null;
        this.visionurl = null;
    }
    
    public String getVisionNote() {
        return this.visionNote;
    }
    
    public String getVisionNum() {
        return this.visionNum;
    }
    
    public String getVisionSize() {
        return this.visionSize;
    }
    
    public String getVisionTime() {
        return this.visionTime;
    }
    
    public String getVisionurl() {
        return this.visionurl;
    }
    
    public void setVisionNote(final String visionNote) {
        this.visionNote = visionNote;
    }
    
    public void setVisionNum(final String visionNum) {
        this.visionNum = visionNum;
    }
    
    public void setVisionSize(final String visionSize) {
        this.visionSize = visionSize;
    }
    
    public void setVisionTime(final String visionTime) {
        this.visionTime = visionTime;
    }
    
    public void setVisionurl(final String visionurl) {
        this.visionurl = visionurl;
    }
}
