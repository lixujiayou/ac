// 

// 

package com.inspur.resources.bean;

import java.io.Serializable;

public class LedupInfoBean implements Serializable
{
    private String LedupType;
    private String authorizationManagementUnit;
    private String creationDate;
    private Integer deletedFlag;
    private String deletionDate;
    private String designPurposes;
    private Integer isAuthorizationManagement;
    private String lastUpdateDate;
    private String latitude;
    private String ledupPointCode;
    private Integer ledupPointId;
    private String ledupPointName;
    private String ledupPointNameSub;
    private String longitude;
    private String note;
    private Integer occupiedTubeQuantity;
    private Integer poleId;
    private String poleName;
    private String region;
    private Integer reservedTubeQuantity;
    private Integer tubeQuantity;
    private String walladdr;
    private Integer wellId;
    private String wellName;
    private String dataQualityPrincipal;
    private String parties;
    
    public LedupInfoBean() {
    }
    
    public LedupInfoBean(final LedupInfoBean ledupInfoBean) {
        this.ledupPointId = ledupInfoBean.ledupPointId;
        this.ledupPointName = ledupInfoBean.ledupPointName;
        this.ledupPointNameSub = ledupInfoBean.ledupPointNameSub;
        this.ledupPointCode = ledupInfoBean.ledupPointCode;
        this.wellId = ledupInfoBean.wellId;
        this.poleId = ledupInfoBean.poleId;
        this.tubeQuantity = ledupInfoBean.tubeQuantity;
        this.occupiedTubeQuantity = ledupInfoBean.occupiedTubeQuantity;
        this.reservedTubeQuantity = ledupInfoBean.reservedTubeQuantity;
        this.creationDate = ledupInfoBean.creationDate;
        this.lastUpdateDate = ledupInfoBean.lastUpdateDate;
        this.deletedFlag = ledupInfoBean.deletedFlag;
        this.deletionDate = ledupInfoBean.deletionDate;
        this.region = ledupInfoBean.region;
        this.LedupType = ledupInfoBean.LedupType;
        this.longitude = ledupInfoBean.longitude;
        this.latitude = ledupInfoBean.latitude;
        this.walladdr = ledupInfoBean.walladdr;
        this.isAuthorizationManagement = ledupInfoBean.isAuthorizationManagement;
        this.authorizationManagementUnit = ledupInfoBean.authorizationManagementUnit;
        this.designPurposes = ledupInfoBean.designPurposes;
        this.note = ledupInfoBean.note;
        this.poleName = ledupInfoBean.poleName;
        this.wellName = ledupInfoBean.wellName;
    }
    
    public String getAuthorizationManagementUnit() {
        return this.authorizationManagementUnit;
    }
    
    public String getCreationDate() {
        return this.creationDate;
    }
    
    public Integer getDeletedFlag() {
        return this.deletedFlag;
    }
    
    public String getDeletionDate() {
        return this.deletionDate;
    }
    
    public String getDesignPurposes() {
        return this.designPurposes;
    }
    
    public Integer getIsAuthorizationManagement() {
        return this.isAuthorizationManagement;
    }
    
    public String getLastUpdateDate() {
        return this.lastUpdateDate;
    }
    
    public String getLatitude() {
        return this.latitude;
    }
    
    public String getLedupPointCode() {
        return this.ledupPointCode;
    }
    
    public Integer getLedupPointId() {
        return this.ledupPointId;
    }
    
    public String getLedupPointName() {
        return this.ledupPointName;
    }
    
    public String getLedupPointNameSub() {
        return this.ledupPointNameSub;
    }
    
    public String getLedupType() {
        return this.LedupType;
    }
    
    public String getLongitude() {
        return this.longitude;
    }
    
    public String getNote() {
        return this.note;
    }
    
    public Integer getOccupiedTubeQuantity() {
        return this.occupiedTubeQuantity;
    }
    
    public Integer getPoleId() {
        return this.poleId;
    }
    
    public String getPoleName() {
        return this.poleName;
    }
    
    public String getRegion() {
        return this.region;
    }
    
    public Integer getReservedTubeQuantity() {
        return this.reservedTubeQuantity;
    }
    
    public Integer getTubeQuantity() {
        return this.tubeQuantity;
    }
    
    public String getWalladdr() {
        return this.walladdr;
    }
    
    public Integer getWellId() {
        return this.wellId;
    }
    
    public String getWellName() {
        return this.wellName;
    }
    
    public void setAuthorizationManagementUnit(final String authorizationManagementUnit) {
        this.authorizationManagementUnit = authorizationManagementUnit;
    }
    
    public void setCreationDate(final String creationDate) {
        this.creationDate = creationDate;
    }
    
    public void setDeletedFlag(final Integer deletedFlag) {
        this.deletedFlag = deletedFlag;
    }
    
    public void setDeletionDate(final String deletionDate) {
        this.deletionDate = deletionDate;
    }
    
    public void setDesignPurposes(final String designPurposes) {
        this.designPurposes = designPurposes;
    }
    
    public void setIsAuthorizationManagement(final Integer isAuthorizationManagement) {
        this.isAuthorizationManagement = isAuthorizationManagement;
    }
    
    public void setLastUpdateDate(final String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }
    
    public void setLedupPointCode(final String ledupPointCode) {
        this.ledupPointCode = ledupPointCode;
    }
    
    public void setLedupPointId(final Integer ledupPointId) {
        this.ledupPointId = ledupPointId;
    }
    
    public void setLedupPointName(final String ledupPointName) {
        this.ledupPointName = ledupPointName;
    }
    
    public void setLedupPointNameSub(final String ledupPointNameSub) {
        this.ledupPointNameSub = ledupPointNameSub;
    }
    
    public void setLedupType(final String ledupType) {
        this.LedupType = ledupType;
    }
    
    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }
    
    public void setNote(final String note) {
        this.note = note;
    }
    
    public void setOccupiedTubeQuantity(final Integer occupiedTubeQuantity) {
        this.occupiedTubeQuantity = occupiedTubeQuantity;
    }
    
    public void setPoleId(final Integer poleId) {
        this.poleId = poleId;
    }
    
    public void setPoleName(final String poleName) {
        this.poleName = poleName;
    }
    
    public void setRegion(final String region) {
        this.region = region;
    }
    
    public void setReservedTubeQuantity(final Integer reservedTubeQuantity) {
        this.reservedTubeQuantity = reservedTubeQuantity;
    }
    
    public void setTubeQuantity(final Integer tubeQuantity) {
        this.tubeQuantity = tubeQuantity;
    }
    
    public void setWalladdr(final String walladdr) {
        this.walladdr = walladdr;
    }
    
    public void setWellId(final Integer wellId) {
        this.wellId = wellId;
    }
    
    public void setWellName(final String wellName) {
        this.wellName = wellName;
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
