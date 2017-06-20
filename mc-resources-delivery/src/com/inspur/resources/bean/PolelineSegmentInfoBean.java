// 

// 

package com.inspur.resources.bean;

import java.io.Serializable;

public class PolelineSegmentInfoBean implements Serializable
{
    private String bearCableSegment;
    private String bearCableSegmentId;
    private String constructionSharingEnumId;
    private String constructionSharingOrg;
    private String creationDate;
    private String deletedFlag;
    private String deletionDate;
    private String endDeviceId;
    private String endDeviceName;
    private String lastUpdateDate;
    private String maintenanceAreaName;
    private Integer poleLineId;
    private String poleLineSegmentCode;
    private Integer poleLineSegmentId;
    private String poleLineSegmentLength;
    private String poleLineSegmentName;
    private String provinceId;
    private String redunBearCableSegment;
    private String redunBearCableSegmentId;
    private Integer resourceLifePeriodEnumId;
    private String sharingTypeEnumId;
    private String startDeviceId;
    private String startDeviceName;
    private String status;
    private String dataQualityPrincipal;
    private String parties;
    
    public PolelineSegmentInfoBean() {
        this.redunBearCableSegment = null;
        this.redunBearCableSegmentId = null;
    }
    
    public PolelineSegmentInfoBean(final PolelineSegmentInfoBean polelineSegmentInfoBean) {
        this.redunBearCableSegment = null;
        this.redunBearCableSegmentId = null;
        this.poleLineSegmentId = polelineSegmentInfoBean.poleLineSegmentId;
        this.poleLineSegmentName = polelineSegmentInfoBean.poleLineSegmentName;
        this.poleLineSegmentCode = polelineSegmentInfoBean.poleLineSegmentCode;
        this.maintenanceAreaName = polelineSegmentInfoBean.maintenanceAreaName;
        this.bearCableSegment = polelineSegmentInfoBean.bearCableSegment;
        this.bearCableSegmentId = polelineSegmentInfoBean.bearCableSegmentId;
        this.redunBearCableSegment = polelineSegmentInfoBean.redunBearCableSegment;
        this.redunBearCableSegmentId = polelineSegmentInfoBean.redunBearCableSegmentId;
        this.poleLineId = polelineSegmentInfoBean.poleLineId;
        this.poleLineSegmentLength = polelineSegmentInfoBean.poleLineSegmentLength;
        this.status = polelineSegmentInfoBean.status;
        this.startDeviceId = polelineSegmentInfoBean.startDeviceId;
        this.endDeviceId = polelineSegmentInfoBean.endDeviceId;
        this.startDeviceName = polelineSegmentInfoBean.startDeviceName;
        this.endDeviceName = polelineSegmentInfoBean.endDeviceName;
        this.constructionSharingEnumId = polelineSegmentInfoBean.constructionSharingEnumId;
        this.constructionSharingOrg = polelineSegmentInfoBean.constructionSharingOrg;
        this.resourceLifePeriodEnumId = polelineSegmentInfoBean.resourceLifePeriodEnumId;
        this.creationDate = polelineSegmentInfoBean.creationDate;
        this.lastUpdateDate = polelineSegmentInfoBean.lastUpdateDate;
        this.deletedFlag = polelineSegmentInfoBean.deletedFlag;
        this.deletionDate = polelineSegmentInfoBean.deletionDate;
        this.provinceId = polelineSegmentInfoBean.provinceId;
    }
    
    public String getBearCableSegment() {
        return this.bearCableSegment;
    }
    
    public String getBearCableSegmentId() {
        return this.bearCableSegmentId;
    }
    
    public String getConstructionSharingEnumId() {
        return this.constructionSharingEnumId;
    }
    
    public String getConstructionSharingOrg() {
        return this.constructionSharingOrg;
    }
    
    public String getCreationDate() {
        return this.creationDate;
    }
    
    public String getDeletedFlag() {
        return this.deletedFlag;
    }
    
    public String getDeletionDate() {
        return this.deletionDate;
    }
    
    public String getEndDeviceId() {
        return this.endDeviceId;
    }
    
    public String getEndDeviceName() {
        return this.endDeviceName;
    }
    
    public String getLastUpdateDate() {
        return this.lastUpdateDate;
    }
    
    public String getMaintenanceAreaName() {
        return this.maintenanceAreaName;
    }
    
    public Integer getPoleLineId() {
        return this.poleLineId;
    }
    
    public String getPoleLineSegmentCode() {
        return this.poleLineSegmentCode;
    }
    
    public Integer getPoleLineSegmentId() {
        return this.poleLineSegmentId;
    }
    
    public String getPoleLineSegmentLength() {
        return this.poleLineSegmentLength;
    }
    
    public String getPoleLineSegmentName() {
        return this.poleLineSegmentName;
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
    
    public Integer getResourceLifePeriodEnumId() {
        return this.resourceLifePeriodEnumId;
    }
    
    public String getSharingTypeEnumId() {
        return this.sharingTypeEnumId;
    }
    
    public String getStartDeviceId() {
        return this.startDeviceId;
    }
    
    public String getStartDeviceName() {
        return this.startDeviceName;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setBearCableSegment(final String bearCableSegment) {
        this.bearCableSegment = bearCableSegment;
    }
    
    public void setBearCableSegmentId(final String bearCableSegmentId) {
        this.bearCableSegmentId = bearCableSegmentId;
    }
    
    public void setConstructionSharingEnumId(final String constructionSharingEnumId) {
        this.constructionSharingEnumId = constructionSharingEnumId;
    }
    
    public void setConstructionSharingOrg(final String constructionSharingOrg) {
        this.constructionSharingOrg = constructionSharingOrg;
    }
    
    public void setCreationDate(final String creationDate) {
        this.creationDate = creationDate;
    }
    
    public void setDeletedFlag(final String deletedFlag) {
        this.deletedFlag = deletedFlag;
    }
    
    public void setDeletionDate(final String deletionDate) {
        this.deletionDate = deletionDate;
    }
    
    public void setEndDeviceId(final String endDeviceId) {
        this.endDeviceId = endDeviceId;
    }
    
    public void setEndDeviceName(final String endDeviceName) {
        this.endDeviceName = endDeviceName;
    }
    
    public void setLastUpdateDate(final String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public void setMaintenanceAreaName(final String maintenanceAreaName) {
        this.maintenanceAreaName = maintenanceAreaName;
    }
    
    public void setPoleLineId(final Integer poleLineId) {
        this.poleLineId = poleLineId;
    }
    
    public void setPoleLineSegmentCode(final String poleLineSegmentCode) {
        this.poleLineSegmentCode = poleLineSegmentCode;
    }
    
    public void setPoleLineSegmentId(final Integer poleLineSegmentId) {
        this.poleLineSegmentId = poleLineSegmentId;
    }
    
    public void setPoleLineSegmentLength(final String poleLineSegmentLength) {
        this.poleLineSegmentLength = poleLineSegmentLength;
    }
    
    public void setPoleLineSegmentName(final String poleLineSegmentName) {
        this.poleLineSegmentName = poleLineSegmentName;
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
    
    public void setResourceLifePeriodEnumId(final Integer resourceLifePeriodEnumId) {
        this.resourceLifePeriodEnumId = resourceLifePeriodEnumId;
    }
    
    public void setSharingTypeEnumId(final String sharingTypeEnumId) {
        this.sharingTypeEnumId = sharingTypeEnumId;
    }
    
    public void setStartDeviceId(final String startDeviceId) {
        this.startDeviceId = startDeviceId;
    }
    
    public void setStartDeviceName(final String startDeviceName) {
        this.startDeviceName = startDeviceName;
    }
    
    public void setStatus(final String status) {
        this.status = status;
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
