// 

// 

package com.inspur.resources.bean;

import java.util.List;
import java.io.Serializable;

public class SusLocationBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Integer SusId;
    private String lat;
    private String lon;
    private List<PoleInfoBean> poleList;
    private List<SupportInfoBean> supList;
    private List<SuspensionWireSegInfoBean> susSegList;
    
    public String getLat() {
        return this.lat;
    }
    
    public String getLon() {
        return this.lon;
    }
    
    public List<PoleInfoBean> getPoleList() {
        return this.poleList;
    }
    
    public List<SupportInfoBean> getSupList() {
        return this.supList;
    }
    
    public Integer getSusId() {
        return this.SusId;
    }
    
    public List<SuspensionWireSegInfoBean> getSusSegList() {
        return this.susSegList;
    }
    
    public void setLat(final String lat) {
        this.lat = lat;
    }
    
    public void setLon(final String lon) {
        this.lon = lon;
    }
    
    public void setPoleList(final List<PoleInfoBean> poleList) {
        this.poleList = poleList;
    }
    
    public void setSupList(final List<SupportInfoBean> supList) {
        this.supList = supList;
    }
    
    public void setSusId(final Integer susId) {
        this.SusId = susId;
    }
    
    public void setSusSegList(final List<SuspensionWireSegInfoBean> susSegList) {
        this.susSegList = susSegList;
    }
}
