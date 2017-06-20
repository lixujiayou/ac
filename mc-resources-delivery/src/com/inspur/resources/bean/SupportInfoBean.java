// 

// 

package com.inspur.resources.bean;

import java.util.Date;
import java.io.Serializable;

public class SupportInfoBean implements Serializable
{
    private String address;
    private String hight;
    private String lat;
    private String lifecycle;
    private Date lifecycleTime;
    private String lon;
    private String note;
    private String region;
    private String supportBieName;
    private Integer supportId;
    private String supportJZ;
    private String supportName;
    private String supportType;
    private String dataQualityPrincipal;
    private String parties;
    
    public SupportInfoBean() {
        this.region = null;
        this.address = null;
        this.supportType = null;
        this.lat = null;
        this.lon = null;
        this.supportJZ = null;
        this.hight = null;
        this.note = null;
    }
    
    public SupportInfoBean(final SupportInfoBean supportInfoBean) {
        this.region = null;
        this.address = null;
        this.supportType = null;
        this.lat = null;
        this.lon = null;
        this.supportJZ = null;
        this.hight = null;
        this.note = null;
        this.supportId = supportInfoBean.supportId;
        this.supportName = supportInfoBean.supportName;
        this.region = supportInfoBean.region;
        this.supportBieName = supportInfoBean.supportBieName;
        this.address = supportInfoBean.address;
        this.supportType = supportInfoBean.supportType;
        this.lat = supportInfoBean.lat;
        this.lon = supportInfoBean.lon;
        this.supportJZ = supportInfoBean.supportJZ;
        this.hight = supportInfoBean.hight;
        this.lifecycle = supportInfoBean.lifecycle;
        this.lifecycleTime = supportInfoBean.lifecycleTime;
        this.note = supportInfoBean.note;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public String getHight() {
        return this.hight;
    }
    
    public String getLat() {
        return this.lat;
    }
    
    public String getLifecycle() {
        return this.lifecycle;
    }
    
    public Date getLifecycleTime() {
        return this.lifecycleTime;
    }
    
    public String getLon() {
        return this.lon;
    }
    
    public String getNote() {
        return this.note;
    }
    
    public String getRegion() {
        return this.region;
    }
    
    public String getSupportBieName() {
        return this.supportBieName;
    }
    
    public Integer getSupportId() {
        return this.supportId;
    }
    
    public String getSupportJZ() {
        return this.supportJZ;
    }
    
    public String getSupportName() {
        return this.supportName;
    }
    
    public String getSupportType() {
        return this.supportType;
    }
    
    public void setAddress(final String address) {
        this.address = address;
    }
    
    public void setHight(final String hight) {
        this.hight = hight;
    }
    
    public void setLat(final String lat) {
        this.lat = lat;
    }
    
    public void setLifecycle(final String lifecycle) {
        this.lifecycle = lifecycle;
    }
    
    public void setLifecycleTime(final Date lifecycleTime) {
        this.lifecycleTime = lifecycleTime;
    }
    
    public void setLon(final String lon) {
        this.lon = lon;
    }
    
    public void setNote(final String note) {
        this.note = note;
    }
    
    public void setRegion(final String region) {
        this.region = region;
    }
    
    public void setSupportBieName(final String supportBieName) {
        this.supportBieName = supportBieName;
    }
    
    public void setSupportId(final Integer supportId) {
        this.supportId = supportId;
    }
    
    public void setSupportJZ(final String supportJZ) {
        this.supportJZ = supportJZ;
    }
    
    public void setSupportName(final String supportName) {
        this.supportName = supportName;
    }
    
    public void setSupportType(final String supportType) {
        this.supportType = supportType;
    }

	public String getDataQualityPrincipal() {
		return dataQualityPrincipal;
	}

	public void setDataQualityPrincipal(String dataQualityPrincipal) {
		this.dataQualityPrincipal = dataQualityPrincipal;
	}

	public String getParties() {
		return parties;
	}

	public void setParties(String parties) {
		this.parties = parties;
	}
}
