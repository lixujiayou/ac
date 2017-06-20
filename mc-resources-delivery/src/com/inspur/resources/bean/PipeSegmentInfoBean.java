// 

// 

package com.inspur.resources.bean;

import java.io.Serializable;

public class PipeSegmentInfoBean implements Serializable
{
    private String PipeName;
    private Integer buildAndShareNum;
    private String buriedDepth;
    private Integer constructionSharingEnumId;
    private String constructionSharingOrg;
    private String creationDate;
    private Integer cstate;
    private Integer deletedFlag;
    private String deletionDate;
    private Integer endDeviceId;
    private String endDeviceName;
    private Integer endDeviceType;
    private String endFaceLocation;
    private String endNestBottomElevation;
    private String endNestTopElevation;
    private Integer holeQuantity;
    private String lastUpdateDate;
    private Integer maintenanceAreaId;
    private String maintenanceAreaName;
    private Integer occupiedSharingHoleQuantity;
    private Integer pipeId;
    private Integer pipeLineLayingEnumId;
    private String pipeSegmentCode;
    private Integer pipeSegmentId;
    private String pipeSegmentLength;
    private String pipeSegmentName;
    private Integer pipeSegmentType;
    private String provinceId;
    private String rentChargingCode;
    private String rentEndDate;
    private Integer rentFlag;
    private String rentOrg;
    private String rentStartDate;
    private Integer resourceLifePeriodEnumId;
    private String roadName;
    private Integer sharingTypeEnumId;
    private Integer startDeviceId;
    private String startDeviceName;
    private Integer startDeviceType;
    private String startFaceLocation;
    private String startNestBottomElevation;
    private String startNestTopElevation;
    private String dataQualityPrincipal;
    private String parties;
    
    public PipeSegmentInfoBean() {
        this.PipeName = null;
        this.startDeviceName = null;
        this.endDeviceName = null;
    }
    
    public PipeSegmentInfoBean(final PipeSegmentInfoBean pipeSegmentInfoBean) {
        this.PipeName = null;
        this.startDeviceName = null;
        this.endDeviceName = null;
        this.pipeSegmentId = pipeSegmentInfoBean.pipeSegmentId;
        this.pipeSegmentName = pipeSegmentInfoBean.pipeSegmentName;
        this.pipeSegmentCode = pipeSegmentInfoBean.pipeSegmentCode;
        this.maintenanceAreaId = pipeSegmentInfoBean.maintenanceAreaId;
        this.maintenanceAreaName = pipeSegmentInfoBean.maintenanceAreaName;
        this.PipeName = pipeSegmentInfoBean.PipeName;
        this.pipeId = pipeSegmentInfoBean.pipeId;
        this.roadName = pipeSegmentInfoBean.roadName;
        this.pipeSegmentLength = pipeSegmentInfoBean.pipeSegmentLength;
        this.pipeSegmentType = pipeSegmentInfoBean.pipeSegmentType;
        this.buriedDepth = pipeSegmentInfoBean.buriedDepth;
        this.startNestTopElevation = pipeSegmentInfoBean.startNestTopElevation;
        this.startNestBottomElevation = pipeSegmentInfoBean.startNestBottomElevation;
        this.startDeviceId = pipeSegmentInfoBean.startDeviceId;
        this.startDeviceType = pipeSegmentInfoBean.startDeviceType;
        this.startDeviceName = pipeSegmentInfoBean.startDeviceName;
        this.endDeviceId = pipeSegmentInfoBean.endDeviceId;
        this.endDeviceType = pipeSegmentInfoBean.endDeviceType;
        this.endDeviceName = pipeSegmentInfoBean.endDeviceName;
        this.endNestTopElevation = pipeSegmentInfoBean.endNestTopElevation;
        this.endNestBottomElevation = pipeSegmentInfoBean.endNestBottomElevation;
        this.pipeLineLayingEnumId = pipeSegmentInfoBean.pipeLineLayingEnumId;
        this.holeQuantity = pipeSegmentInfoBean.holeQuantity;
        this.constructionSharingEnumId = pipeSegmentInfoBean.constructionSharingEnumId;
        this.constructionSharingOrg = pipeSegmentInfoBean.constructionSharingOrg;
        this.sharingTypeEnumId = pipeSegmentInfoBean.sharingTypeEnumId;
        this.buildAndShareNum = pipeSegmentInfoBean.buildAndShareNum;
        this.occupiedSharingHoleQuantity = pipeSegmentInfoBean.occupiedSharingHoleQuantity;
        this.rentFlag = pipeSegmentInfoBean.rentFlag;
        this.rentStartDate = pipeSegmentInfoBean.rentStartDate;
        this.rentEndDate = pipeSegmentInfoBean.rentEndDate;
        this.rentOrg = pipeSegmentInfoBean.rentOrg;
        this.rentChargingCode = pipeSegmentInfoBean.rentChargingCode;
        this.resourceLifePeriodEnumId = pipeSegmentInfoBean.resourceLifePeriodEnumId;
        this.creationDate = pipeSegmentInfoBean.creationDate;
        this.lastUpdateDate = pipeSegmentInfoBean.lastUpdateDate;
        this.deletedFlag = pipeSegmentInfoBean.deletedFlag;
        this.deletionDate = pipeSegmentInfoBean.deletionDate;
        this.provinceId = pipeSegmentInfoBean.provinceId;
        this.startFaceLocation = pipeSegmentInfoBean.startFaceLocation;
        this.endFaceLocation = pipeSegmentInfoBean.endFaceLocation;
    }
    
    public Integer getBuildAndShareNum() {
        return this.buildAndShareNum;
    }
    
    public String getBuriedDepth() {
        return this.buriedDepth;
    }
    
    public Integer getConstructionSharingEnumId() {
        return this.constructionSharingEnumId;
    }
    
    public String getConstructionSharingOrg() {
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
    
    public Integer getEndDeviceId() {
        return this.endDeviceId;
    }
    
    public String getEndDeviceName() {
        return this.endDeviceName;
    }
    
    public Integer getEndDeviceType() {
        return this.endDeviceType;
    }
    
    public String getEndFaceLocation() {
        return this.endFaceLocation;
    }
    
    public String getEndNestBottomElevation() {
        return this.endNestBottomElevation;
    }
    
    public String getEndNestTopElevation() {
        return this.endNestTopElevation;
    }
    
    public Integer getHoleQuantity() {
        return this.holeQuantity;
    }
    
    public String getLastUpdateDate() {
        return this.lastUpdateDate;
    }
    
    public Integer getMaintenanceAreaId() {
        return this.maintenanceAreaId;
    }
    
    public String getMaintenanceAreaName() {
        return this.maintenanceAreaName;
    }
    
    public Integer getOccupiedSharingHoleQuantity() {
        return this.occupiedSharingHoleQuantity;
    }
    
    public Integer getPipeId() {
        return this.pipeId;
    }
    
    public Integer getPipeLineLayingEnumId() {
        return this.pipeLineLayingEnumId;
    }
    
    public String getPipeName() {
        return this.PipeName;
    }
    
    public String getPipeSegmentCode() {
        return this.pipeSegmentCode;
    }
    
    public Integer getPipeSegmentId() {
        return this.pipeSegmentId;
    }
    
    public String getPipeSegmentLength() {
        return this.pipeSegmentLength;
    }
    
    public String getPipeSegmentName() {
        return this.pipeSegmentName;
    }
    
    public Integer getPipeSegmentType() {
        return this.pipeSegmentType;
    }
    
    public String getProvinceId() {
        return this.provinceId;
    }
    
    public String getRentChargingCode() {
        return this.rentChargingCode;
    }
    
    public String getRentEndDate() {
        return this.rentEndDate;
    }
    
    public Integer getRentFlag() {
        return this.rentFlag;
    }
    
    public String getRentOrg() {
        return this.rentOrg;
    }
    
    public String getRentStartDate() {
        return this.rentStartDate;
    }
    
    public Integer getResourceLifePeriodEnumId() {
        return this.resourceLifePeriodEnumId;
    }
    
    public String getRoadName() {
        return this.roadName;
    }
    
    public Integer getSharingTypeEnumId() {
        return this.sharingTypeEnumId;
    }
    
    public Integer getStartDeviceId() {
        return this.startDeviceId;
    }
    
    public String getStartDeviceName() {
        return this.startDeviceName;
    }
    
    public Integer getStartDeviceType() {
        return this.startDeviceType;
    }
    
    public String getStartFaceLocation() {
        return this.startFaceLocation;
    }
    
    public String getStartNestBottomElevation() {
        return this.startNestBottomElevation;
    }
    
    public String getStartNestTopElevation() {
        return this.startNestTopElevation;
    }
    
    public String pipeSegmentTypeString(final int n) {
        switch (n) {
            default: {
                return "\u5176\u4ed6";
            }
            case 1: {
                return "\u4eba\u4e95\u7ba1\u6bb5";
            }
            case 2: {
                return "\u5f15\u4e0a\u7ba1\u6bb5";
            }
            case 3: {
                return "\u5f15\u5165\u7ba1\u6bb5";
            }
            case 4: {
                return "\u6865\u6881\u7ba1\u6bb5";
            }
            case 5: {
                return "\u901a\u9053";
            }
        }
    }
    
    public void setBuildAndShareNum(final Integer buildAndShareNum) {
        this.buildAndShareNum = buildAndShareNum;
    }
    
    public void setBuriedDepth(final String buriedDepth) {
        this.buriedDepth = buriedDepth;
    }
    
    public void setConstructionSharingEnumId(final Integer constructionSharingEnumId) {
        this.constructionSharingEnumId = constructionSharingEnumId;
    }
    
    public void setConstructionSharingOrg(final String constructionSharingOrg) {
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
    
    public void setEndDeviceId(final Integer endDeviceId) {
        this.endDeviceId = endDeviceId;
    }
    
    public void setEndDeviceName(final String endDeviceName) {
        this.endDeviceName = endDeviceName;
    }
    
    public void setEndDeviceType(final Integer endDeviceType) {
        this.endDeviceType = endDeviceType;
    }
    
    public void setEndFaceLocation(final String endFaceLocation) {
        this.endFaceLocation = endFaceLocation;
    }
    
    public void setEndNestBottomElevation(final String endNestBottomElevation) {
        this.endNestBottomElevation = endNestBottomElevation;
    }
    
    public void setEndNestTopElevation(final String endNestTopElevation) {
        this.endNestTopElevation = endNestTopElevation;
    }
    
    public void setHoleQuantity(final Integer holeQuantity) {
        this.holeQuantity = holeQuantity;
    }
    
    public void setLastUpdateDate(final String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public void setMaintenanceAreaId(final Integer maintenanceAreaId) {
        this.maintenanceAreaId = maintenanceAreaId;
    }
    
    public void setMaintenanceAreaName(final String maintenanceAreaName) {
        this.maintenanceAreaName = maintenanceAreaName;
    }
    
    public void setOccupiedSharingHoleQuantity(final Integer occupiedSharingHoleQuantity) {
        this.occupiedSharingHoleQuantity = occupiedSharingHoleQuantity;
    }
    
    public void setPipeId(final Integer pipeId) {
        this.pipeId = pipeId;
    }
    
    public void setPipeLineLayingEnumId(final Integer pipeLineLayingEnumId) {
        this.pipeLineLayingEnumId = pipeLineLayingEnumId;
    }
    
    public void setPipeName(final String pipeName) {
        this.PipeName = pipeName;
    }
    
    public void setPipeSegmentCode(final String pipeSegmentCode) {
        this.pipeSegmentCode = pipeSegmentCode;
    }
    
    public void setPipeSegmentId(final Integer pipeSegmentId) {
        this.pipeSegmentId = pipeSegmentId;
    }
    
    public void setPipeSegmentLength(final String pipeSegmentLength) {
        this.pipeSegmentLength = pipeSegmentLength;
    }
    
    public void setPipeSegmentName(final String pipeSegmentName) {
        this.pipeSegmentName = pipeSegmentName;
    }
    
    public void setPipeSegmentType(final Integer pipeSegmentType) {
        this.pipeSegmentType = pipeSegmentType;
    }
    
    public void setProvinceId(final String provinceId) {
        this.provinceId = provinceId;
    }
    
    public void setRentChargingCode(final String rentChargingCode) {
        this.rentChargingCode = rentChargingCode;
    }
    
    public void setRentEndDate(final String rentEndDate) {
        this.rentEndDate = rentEndDate;
    }
    
    public void setRentFlag(final Integer rentFlag) {
        this.rentFlag = rentFlag;
    }
    
    public void setRentOrg(final String rentOrg) {
        this.rentOrg = rentOrg;
    }
    
    public void setRentStartDate(final String rentStartDate) {
        this.rentStartDate = rentStartDate;
    }
    
    public void setResourceLifePeriodEnumId(final Integer resourceLifePeriodEnumId) {
        this.resourceLifePeriodEnumId = resourceLifePeriodEnumId;
    }
    
    public void setRoadName(final String roadName) {
        this.roadName = roadName;
    }
    
    public void setSharingTypeEnumId(final Integer sharingTypeEnumId) {
        this.sharingTypeEnumId = sharingTypeEnumId;
    }
    
    public void setStartDeviceId(final Integer startDeviceId) {
        this.startDeviceId = startDeviceId;
    }
    
    public void setStartDeviceName(final String startDeviceName) {
        this.startDeviceName = startDeviceName;
    }
    
    public void setStartDeviceType(final Integer startDeviceType) {
        this.startDeviceType = startDeviceType;
    }
    
    public void setStartFaceLocation(final String startFaceLocation) {
        this.startFaceLocation = startFaceLocation;
    }
    
    public void setStartNestBottomElevation(final String startNestBottomElevation) {
        this.startNestBottomElevation = startNestBottomElevation;
    }
    
    public void setStartNestTopElevation(final String startNestTopElevation) {
        this.startNestTopElevation = startNestTopElevation;
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
