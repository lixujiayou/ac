// 

// 

package com.inspur.resources.bean;

import java.io.Serializable;

public class TubeInfoBean implements Serializable
{
    private static final long serialVersionUID = -6346877731109347157L;
    private String bearCableSegment;
    private String bearCableSegmentId;
    private Integer constructionSharingEnumId;
    private int constructionSharingOrg;
    private String creationDate;
    private Integer cstate;
    private Integer deletedFlag;
    private String deletionDate;
    private String faceId;
    private Integer fatherPoreId;
    private String fatherPoreName;
    private Integer isFather;
    private String lastUpdateDate;
    private Integer maintenanceAreaId;
    private Integer pipeSegmentId;
    private String pipeSegmentName;
    private String provinceId;
    private String redunBearCableSegment;
    private String redunBearCableSegmentId;
    private Integer rentFlag;
    private String rentOrg;
    private Integer resourceLifePeriodEnumId;
    private String rfid;
    private Integer sharingTypeEnumId;
    private Integer subTubeAmount;
    private String subTubeArrangeMode;
    private Integer tubeColor;
    private String tubeDiameter;
    private Integer tubeId;
    private Integer tubeMaterial;
    private String tubeName;
    private String tubeNumber;
    private Integer tubeShape;
    private Integer tubeStatusEnumId;
    private Integer wellId;
    
    public TubeInfoBean() {
        this.rentOrg = null;
        this.bearCableSegment = null;
        this.bearCableSegmentId = null;
        this.redunBearCableSegment = null;
        this.redunBearCableSegmentId = null;
    }
    
    public TubeInfoBean(final TubeInfoBean tubeInfoBean) {
        this.rentOrg = null;
        this.bearCableSegment = null;
        this.bearCableSegmentId = null;
        this.redunBearCableSegment = null;
        this.redunBearCableSegmentId = null;
        this.tubeId = tubeInfoBean.tubeId;
        this.tubeName = tubeInfoBean.tubeName;
        this.tubeNumber = tubeInfoBean.tubeNumber;
        this.pipeSegmentId = tubeInfoBean.pipeSegmentId;
        this.pipeSegmentName = tubeInfoBean.pipeSegmentName;
        this.fatherPoreId = tubeInfoBean.fatherPoreId;
        this.fatherPoreName = tubeInfoBean.fatherPoreName;
        this.subTubeArrangeMode = tubeInfoBean.subTubeArrangeMode;
        this.tubeStatusEnumId = tubeInfoBean.tubeStatusEnumId;
        this.tubeDiameter = tubeInfoBean.tubeDiameter;
        this.tubeColor = tubeInfoBean.tubeColor;
        this.tubeMaterial = tubeInfoBean.tubeMaterial;
        this.tubeShape = tubeInfoBean.tubeShape;
        this.rentFlag = tubeInfoBean.rentFlag;
        this.rentOrg = tubeInfoBean.rentOrg;
        this.resourceLifePeriodEnumId = tubeInfoBean.resourceLifePeriodEnumId;
        this.constructionSharingEnumId = tubeInfoBean.constructionSharingEnumId;
        this.constructionSharingOrg = tubeInfoBean.constructionSharingOrg;
        this.sharingTypeEnumId = tubeInfoBean.sharingTypeEnumId;
        this.bearCableSegment = tubeInfoBean.bearCableSegment;
        this.creationDate = tubeInfoBean.creationDate;
        this.lastUpdateDate = tubeInfoBean.lastUpdateDate;
        this.deletedFlag = tubeInfoBean.deletedFlag;
        this.deletionDate = tubeInfoBean.deletionDate;
        this.provinceId = tubeInfoBean.provinceId;
        this.maintenanceAreaId = tubeInfoBean.maintenanceAreaId;
        this.wellId = tubeInfoBean.wellId;
        this.isFather = tubeInfoBean.isFather;
        this.cstate = tubeInfoBean.cstate;
        this.faceId = tubeInfoBean.faceId;
        this.subTubeAmount = tubeInfoBean.subTubeAmount;
        this.bearCableSegmentId = tubeInfoBean.bearCableSegmentId;
        this.redunBearCableSegment = tubeInfoBean.redunBearCableSegment;
        this.redunBearCableSegmentId = tubeInfoBean.redunBearCableSegmentId;
    }
    
    public String getBearCableSegment() {
        return this.bearCableSegment;
    }
    
    public String getBearCableSegmentId() {
        return this.bearCableSegmentId;
    }
    
    public Integer getConstructionSharingEnumId() {
        return this.constructionSharingEnumId;
    }
    
    public int getConstructionSharingOrg() {
        return this.constructionSharingOrg;
    }
    
    public String getCreationDate() {
        return this.creationDate;
    }
    
    public Integer getCstate() {
        return this.cstate;
    }
    
    public Integer getDeletedFlag() {
        return this.deletedFlag;
    }
    
    public String getDeletionDate() {
        return this.deletionDate;
    }
    
    public String getFaceId() {
        return this.faceId;
    }
    
    public Integer getFatherPoreId() {
        return this.fatherPoreId;
    }
    
    public String getFatherPoreName() {
        return this.fatherPoreName;
    }
    
    public Integer getIsFather() {
        return this.isFather;
    }
    
    public String getLastUpdateDate() {
        return this.lastUpdateDate;
    }
    
    public Integer getMaintenanceAreaId() {
        return this.maintenanceAreaId;
    }
    
    public Integer getPipeSegmentId() {
        return this.pipeSegmentId;
    }
    
    public String getPipeSegmentName() {
        return this.pipeSegmentName;
    }
    
    public String getProvinceId() {
        return this.provinceId;
    }
    
    public String getRedunBearCableSegment() {
        return this.redunBearCableSegment;
    }
    
    public String getRedunBearCableSegmentId() {
        return this.redunBearCableSegmentId;
    }
    
    public Integer getRentFlag() {
        return this.rentFlag;
    }
    
    public String getRentOrg() {
        return this.rentOrg;
    }
    
    public Integer getResourceLifePeriodEnumId() {
        return this.resourceLifePeriodEnumId;
    }
    
    public String getRfid() {
        return this.rfid;
    }
    
    public Integer getSharingTypeEnumId() {
        return this.sharingTypeEnumId;
    }
    
    public Integer getSubTubeAmount() {
        return this.subTubeAmount;
    }
    
    public String getSubTubeArrangeMode() {
        return this.subTubeArrangeMode;
    }
    
    public Integer getTubeColor() {
        return this.tubeColor;
    }
    
    public String getTubeDiameter() {
        return this.tubeDiameter;
    }
    
    public Integer getTubeId() {
        return this.tubeId;
    }
    
    public Integer getTubeMaterial() {
        return this.tubeMaterial;
    }
    
    public String getTubeName() {
        return this.tubeName;
    }
    
    public String getTubeNumber() {
        return this.tubeNumber;
    }
    
    public Integer getTubeShape() {
        return this.tubeShape;
    }
    
    public Integer getTubeStatusEnumId() {
        return this.tubeStatusEnumId;
    }
    
    public Integer getWellId() {
        return this.wellId;
    }
    
    public void setBearCableSegment(final String bearCableSegment) {
        this.bearCableSegment = bearCableSegment;
    }
    
    public void setBearCableSegmentId(final String bearCableSegmentId) {
        this.bearCableSegmentId = bearCableSegmentId;
    }
    
    public void setConstructionSharingEnumId(final Integer constructionSharingEnumId) {
        this.constructionSharingEnumId = constructionSharingEnumId;
    }
    
    public void setConstructionSharingOrg(final int constructionSharingOrg) {
        this.constructionSharingOrg = constructionSharingOrg;
    }
    
    public void setCreationDate(final String creationDate) {
        this.creationDate = creationDate;
    }
    
    public void setCstate(final Integer cstate) {
        this.cstate = cstate;
    }
    
    public void setDeletedFlag(final Integer deletedFlag) {
        this.deletedFlag = deletedFlag;
    }
    
    public void setDeletionDate(final String deletionDate) {
        this.deletionDate = deletionDate;
    }
    
    public void setFaceId(final String faceId) {
        this.faceId = faceId;
    }
    
    public void setFatherPoreId(final Integer fatherPoreId) {
        this.fatherPoreId = fatherPoreId;
    }
    
    public void setFatherPoreName(final String fatherPoreName) {
        this.fatherPoreName = fatherPoreName;
    }
    
    public void setIsFather(final Integer isFather) {
        this.isFather = isFather;
    }
    
    public void setLastUpdateDate(final String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public void setMaintenanceAreaId(final Integer maintenanceAreaId) {
        this.maintenanceAreaId = maintenanceAreaId;
    }
    
    public void setPipeSegmentId(final Integer pipeSegmentId) {
        this.pipeSegmentId = pipeSegmentId;
    }
    
    public void setPipeSegmentName(final String pipeSegmentName) {
        this.pipeSegmentName = pipeSegmentName;
    }
    
    public void setProvinceId(final String provinceId) {
        this.provinceId = provinceId;
    }
    
    public void setRedunBearCableSegment(final String redunBearCableSegment) {
        this.redunBearCableSegment = redunBearCableSegment;
    }
    
    public void setRedunBearCableSegmentId(final String redunBearCableSegmentId) {
        this.redunBearCableSegmentId = redunBearCableSegmentId;
    }
    
    public void setRentFlag(final Integer rentFlag) {
        this.rentFlag = rentFlag;
    }
    
    public void setRentOrg(final String rentOrg) {
        this.rentOrg = rentOrg;
    }
    
    public void setResourceLifePeriodEnumId(final Integer resourceLifePeriodEnumId) {
        this.resourceLifePeriodEnumId = resourceLifePeriodEnumId;
    }
    
    public void setRfid(final String rfid) {
        this.rfid = rfid;
    }
    
    public void setSharingTypeEnumId(final Integer sharingTypeEnumId) {
        this.sharingTypeEnumId = sharingTypeEnumId;
    }
    
    public void setSubTubeAmount(final Integer subTubeAmount) {
        this.subTubeAmount = subTubeAmount;
    }
    
    public void setSubTubeArrangeMode(final String subTubeArrangeMode) {
        this.subTubeArrangeMode = subTubeArrangeMode;
    }
    
    public void setTubeColor(final Integer tubeColor) {
        this.tubeColor = tubeColor;
    }
    
    public void setTubeDiameter(final String tubeDiameter) {
        this.tubeDiameter = tubeDiameter;
    }
    
    public void setTubeId(final Integer tubeId) {
        this.tubeId = tubeId;
    }
    
    public void setTubeMaterial(final Integer tubeMaterial) {
        this.tubeMaterial = tubeMaterial;
    }
    
    public void setTubeName(final String tubeName) {
        this.tubeName = tubeName;
    }
    
    public void setTubeNumber(final String tubeNumber) {
        this.tubeNumber = tubeNumber;
    }
    
    public void setTubeShape(final Integer tubeShape) {
        this.tubeShape = tubeShape;
    }
    
    public void setTubeStatusEnumId(final Integer tubeStatusEnumId) {
        this.tubeStatusEnumId = tubeStatusEnumId;
    }
    
    public void setWellId(final Integer wellId) {
        this.wellId = wellId;
    }
}
