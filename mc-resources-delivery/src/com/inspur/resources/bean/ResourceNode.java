// 

// 

package com.inspur.resources.bean;

import java.util.Date;
import java.io.Serializable;

public class ResourceNode implements Serializable
{
    private static final long serialVersionUID = 717971934826647072L;
    private String id;
    private String name;
    private String no;
    private Integer nodeId;
    private Date sendDate;
    private Integer state;
    private String type;
    
    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getNo() {
        return this.no;
    }
    
    public Integer getNodeId() {
        return this.nodeId;
    }
    
    public Date getSendDate() {
        return this.sendDate;
    }
    
    public Integer getState() {
        return this.state;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setNo(final String no) {
        this.no = no;
    }
    
    public void setNodeId(final Integer nodeId) {
        this.nodeId = nodeId;
    }
    
    public void setSendDate(final Date sendDate) {
        this.sendDate = sendDate;
    }
    
    public void setState(final Integer state) {
        this.state = state;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
}
