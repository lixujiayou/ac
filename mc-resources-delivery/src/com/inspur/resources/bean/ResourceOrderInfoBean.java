// 

// 

package com.inspur.resources.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.io.Serializable;

public class ResourceOrderInfoBean implements Serializable
{
    private static final long serialVersionUID = 5118013688545530690L;
    private Date createTime;
    private String no;
    private List<ResourceNode> nodes;
    private String receiver;
    private Integer resourceOrderId;
    private String sender;
    private String senderName;
    private String info;
    
    public ResourceOrderInfoBean() {
        this.nodes = new ArrayList<ResourceNode>();
    }
    
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public String getNo() {
        return this.no;
    }
    
    public List<ResourceNode> getNodes() {
        return this.nodes;
    }
    
    public String getReceiver() {
        return this.receiver;
    }
    
    public Integer getResourceOrderId() {
        return this.resourceOrderId;
    }
    
    public String getSender() {
        return this.sender;
    }
    
    public String getSenderName() {
        return this.senderName;
    }
    
    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
    }
    
    public void setNo(final String no) {
        this.no = no;
    }
    
    public void setNodes(final List<ResourceNode> nodes) {
        this.nodes = nodes;
    }
    
    public void setReceiver(final String receiver) {
        this.receiver = receiver;
    }
    
    public void setResourceOrderId(final Integer resourceOrderId) {
        this.resourceOrderId = resourceOrderId;
    }
    
    public void setSender(final String sender) {
        this.sender = sender;
    }
    
    public void setSenderName(final String senderName) {
        this.senderName = senderName;
    }

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
