// 

// 

package com.inspur.resources.bean;

import java.io.Serializable;

public class FaceInfoBean implements Serializable
{
    private static final long serialVersionUID = -6346877731109347157L;
    private Integer cols;
    private Integer cstate;
    private Integer deletedFlag;
    private String disableTubes;
    private Integer faceId;
    private Integer isVisible;
    private String locationNo;
    private Integer oppoFaceId;
    private String pipeSegmentId;
    private Integer rows;
    private Integer wellId;
    
    public FaceInfoBean() {
        this.disableTubes = "";
    }
    
    public FaceInfoBean(final FaceInfoBean faceInfoBean) {
        this.disableTubes = "";
        this.faceId = faceInfoBean.faceId;
        this.wellId = faceInfoBean.wellId;
        this.cols = faceInfoBean.cols;
        this.rows = faceInfoBean.rows;
        this.locationNo = faceInfoBean.locationNo;
        this.oppoFaceId = faceInfoBean.oppoFaceId;
        this.pipeSegmentId = faceInfoBean.pipeSegmentId;
        this.deletedFlag = faceInfoBean.deletedFlag;
        this.cstate = faceInfoBean.cstate;
        this.disableTubes = faceInfoBean.disableTubes;
    }
    
    public Integer getCols() {
        return this.cols;
    }
    
    public Integer getCstate() {
        return this.cstate;
    }
    
    public Integer getDeletedflag() {
        return this.deletedFlag;
    }
    
    public String getDisableTubes() {
        return this.disableTubes;
    }
    
    public Integer getFaceId() {
        return this.faceId;
    }
    
    public Integer getIsVisible() {
        return this.isVisible;
    }
    
    public String getLocationNo() {
        return this.locationNo;
    }
    
    public Integer getOppofaceid() {
        return this.oppoFaceId;
    }
    
    public String getPipeSegmentid() {
        return this.pipeSegmentId;
    }
    
    public Integer getRows() {
        return this.rows;
    }
    
    public Integer getWellId() {
        return this.wellId;
    }
    
    public void setCols(final Integer cols) {
        this.cols = cols;
    }
    
    public void setCstate(final Integer cstate) {
        this.cstate = cstate;
    }
    
    public void setDeletedflag(final Integer deletedFlag) {
        this.deletedFlag = deletedFlag;
    }
    
    public void setDisableTubes(final String disableTubes) {
        this.disableTubes = disableTubes;
    }
    
    public void setFaceId(final Integer faceId) {
        this.faceId = faceId;
    }
    
    public void setIsVisible(final Integer isVisible) {
        this.isVisible = isVisible;
    }
    
    public void setLocationNo(final String locationNo) {
        this.locationNo = locationNo;
    }
    
    public void setOppofaceid(final Integer oppoFaceId) {
        this.oppoFaceId = oppoFaceId;
    }
    
    public void setPipeSegmentid(final String pipeSegmentId) {
        this.pipeSegmentId = pipeSegmentId;
    }
    
    public void setRows(final Integer rows) {
        this.rows = rows;
    }
    
    public void setWellId(final Integer wellId) {
        this.wellId = wellId;
    }
}
