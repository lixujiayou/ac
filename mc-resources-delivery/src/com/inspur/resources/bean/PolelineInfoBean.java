// 

// 

package com.inspur.resources.bean;

import java.util.Date;
import java.io.Serializable;

public class PolelineInfoBean implements Serializable
{
    private String areaname;
    private String areano;
    private String creationDate;
    private String deletedFlag;
    private String deletionDate;
    private String endDeviceId;
    private String endDeviceName;
    private String lastUpdateDate;
    private String maintainerId;
    private String maintenanceAreaName;
    private String maintenanceModeEnumId;
    private String maintenanceOrgId;
    private Integer maintenanceTypeEnumId;
    private String poleLineCode;
    private Integer poleLineId;
    private String poleLineLength;
    private String poleLineLevel;
    private String poleLineName;
    private String projectCode;
    private String projectName;
    private Date projectWarrantyExpireDate;
    private String provinceId;
    private Integer purchasedMaintenanceTime;
    private Date renewalExpirationDate;
    private Integer resourceLifePeriodEnumId;
    private String startDeviceId;
    private String startDeviceName;
    private String thirdPartyMaintenanceOrg;
    private String dataQualityPrincipal;
    private String parties;
    
    public PolelineInfoBean() {
        this.areano = null;
        this.areaname = null;
    }
    
    public PolelineInfoBean(final PolelineInfoBean polelineInfoBean) {
        this.areano = null;
        this.areaname = null;
        this.poleLineId = polelineInfoBean.poleLineId;
        this.poleLineName = polelineInfoBean.poleLineName;
        this.poleLineCode = polelineInfoBean.poleLineCode;
        this.areaname = polelineInfoBean.areaname;
        this.areano = polelineInfoBean.areano;
        this.poleLineLevel = polelineInfoBean.poleLineLevel;
        this.maintenanceAreaName = polelineInfoBean.maintenanceAreaName;
        this.poleLineLength = polelineInfoBean.poleLineLength;
        this.startDeviceName = polelineInfoBean.startDeviceName;
        this.endDeviceName = polelineInfoBean.endDeviceName;
        this.startDeviceId = polelineInfoBean.startDeviceId;
        this.endDeviceId = polelineInfoBean.endDeviceId;
        this.maintenanceModeEnumId = polelineInfoBean.maintenanceModeEnumId;
        this.maintenanceOrgId = polelineInfoBean.maintenanceOrgId;
        this.maintainerId = polelineInfoBean.maintainerId;
        this.thirdPartyMaintenanceOrg = polelineInfoBean.thirdPartyMaintenanceOrg;
        this.renewalExpirationDate = polelineInfoBean.renewalExpirationDate;
        this.maintenanceTypeEnumId = polelineInfoBean.maintenanceTypeEnumId;
        this.purchasedMaintenanceTime = polelineInfoBean.purchasedMaintenanceTime;
        this.projectCode = polelineInfoBean.projectCode;
        this.projectName = polelineInfoBean.projectName;
        this.projectWarrantyExpireDate = polelineInfoBean.projectWarrantyExpireDate;
        this.resourceLifePeriodEnumId = polelineInfoBean.resourceLifePeriodEnumId;
        this.creationDate = polelineInfoBean.creationDate;
        this.lastUpdateDate = polelineInfoBean.lastUpdateDate;
        this.deletedFlag = polelineInfoBean.deletedFlag;
        this.deletionDate = polelineInfoBean.deletionDate;
        this.provinceId = polelineInfoBean.provinceId;
        dataQualityPrincipal = polelineInfoBean.dataQualityPrincipal;
        parties = polelineInfoBean.parties;
    }
    
    public String getAreaName() {
        return this.areaname;
    }
    
    public String getAreaNo() {
        return this.areano;
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
    
    public String getLevelString() {
        if (this.getPoleLineLevel().equals("1")) {
            return "\u4e00\u5e72";
        }
        if (this.getPoleLineLevel().equals("2")) {
            return "\u4e8c\u5e72";
        }
        if (this.getPoleLineLevel().equals("0")) {
            return "\u672a\u77e5";
        }
        if (this.getPoleLineLevel().equals("3")) {
            return "\u672c\u5730";
        }
        return "";
    }
    
    public String getMaintainerId() {
        return this.maintainerId;
    }
    
    public String getMaintenanceAreaName() {
        return this.maintenanceAreaName;
    }
    
    public String getMaintenanceModeEnumId() {
        return this.maintenanceModeEnumId;
    }
    
    public String getMaintenanceOrgId() {
        return this.maintenanceOrgId;
    }
    
    public Integer getMaintenanceTypeEnumId() {
        return this.maintenanceTypeEnumId;
    }
    
    public String getPoleLineCode() {
        return this.poleLineCode;
    }
    
    public Integer getPoleLineId() {
        return this.poleLineId;
    }
    
    public String getPoleLineLength() {
        return this.poleLineLength;
    }
    
    public String getPoleLineLevel() {
        return this.poleLineLevel;
    }
    
    public String getPoleLineName() {
        return this.poleLineName;
    }
    
    public String getProjectCode() {
        return this.projectCode;
    }
    
    public String getProjectName() {
        return this.projectName;
    }
    
    public Date getProjectWarrantyExpireDate() {
        return this.projectWarrantyExpireDate;
    }
    
    public String getProvinceId() {
        return this.provinceId;
    }
    
    public Integer getPurchasedMaintenanceTime() {
        return this.purchasedMaintenanceTime;
    }
    
    public Date getRenewalExpirationDate() {
        return this.renewalExpirationDate;
    }
    
    public Integer getResourceLifePeriodEnumId() {
        return this.resourceLifePeriodEnumId;
    }
    
    public String getStartDeviceId() {
        return this.startDeviceId;
    }
    
    public String getStartDeviceName() {
        return this.startDeviceName;
    }
    
    public String getThirdPartyMaintenanceOrg() {
        return this.thirdPartyMaintenanceOrg;
    }
    
    public void setAreaName(final String areaname) {
        this.areaname = areaname;
    }
    
    public void setAreaNo(final String areano) {
        this.areano = areano;
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
    
    public void setMaintainerId(final String maintainerId) {
        this.maintainerId = maintainerId;
    }
    
    public void setMaintenanceAreaName(final String maintenanceAreaName) {
        this.maintenanceAreaName = maintenanceAreaName;
    }
    
    public void setMaintenanceModeEnumId(final String maintenanceModeEnumId) {
        this.maintenanceModeEnumId = maintenanceModeEnumId;
    }
    
    public void setMaintenanceOrgId(final String maintenanceOrgId) {
        this.maintenanceOrgId = maintenanceOrgId;
    }
    
    public void setMaintenanceTypeEnumId(final Integer maintenanceTypeEnumId) {
        this.maintenanceTypeEnumId = maintenanceTypeEnumId;
    }
    
    public void setPoleLineCode(final String poleLineCode) {
        this.poleLineCode = poleLineCode;
    }
    
    public void setPoleLineId(final Integer poleLineId) {
        this.poleLineId = poleLineId;
    }
    
    public void setPoleLineLength(final String poleLineLength) {
        this.poleLineLength = poleLineLength;
    }
    
    public void setPoleLineLevel(final String poleLineLevel) {
        this.poleLineLevel = poleLineLevel;
    }
    
    public void setPoleLineName(final String poleLineName) {
        this.poleLineName = poleLineName;
    }
    
    public void setProjectCode(final String projectCode) {
        this.projectCode = projectCode;
    }
    
    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }
    
    public void setProjectWarrantyExpireDate(final Date projectWarrantyExpireDate) {
        this.projectWarrantyExpireDate = projectWarrantyExpireDate;
    }
    
    public void setProvinceId(final String provinceId) {
        this.provinceId = provinceId;
    }
    
    public void setPurchasedMaintenanceTime(final Integer purchasedMaintenanceTime) {
        this.purchasedMaintenanceTime = purchasedMaintenanceTime;
    }
    
    public void setRenewalExpirationDate(final Date renewalExpirationDate) {
        this.renewalExpirationDate = renewalExpirationDate;
    }
    
    public void setResourceLifePeriodEnumId(final Integer resourceLifePeriodEnumId) {
        this.resourceLifePeriodEnumId = resourceLifePeriodEnumId;
    }
    
    public void setStartDeviceId(final String startDeviceId) {
        this.startDeviceId = startDeviceId;
    }
    
    public void setStartDeviceName(final String startDeviceName) {
        this.startDeviceName = startDeviceName;
    }
    
    public void setThirdPartyMaintenanceOrg(final String thirdPartyMaintenanceOrg) {
        this.thirdPartyMaintenanceOrg = thirdPartyMaintenanceOrg;
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
