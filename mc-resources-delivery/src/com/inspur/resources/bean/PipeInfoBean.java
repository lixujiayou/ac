// 

// 

package com.inspur.resources.bean;

import java.io.Serializable;

public class PipeInfoBean implements Serializable
{
    private static final long serialVersionUID = 4960928057149379320L;
    private String alias;
    private String areaname;
    private String areano;
    private String creationDate;
    private Integer cstate;
    private Integer deletedFlag;
    private String deletionDate;
    private Integer endDeviceId;
    private String endDeviceName;
    private Integer endDeviceType;
    private String lastUpdateDate;
    private String length;
    private String maintainerId;
    private Integer maintenanceAreaId;
    private String maintenanceAreaName;
    private Integer maintenanceModeEnumId;
    private String maintenanceOrgId;
    private Integer maintenanceTypeEnumId;
    private Integer pipeId;
    private Integer pipeLevel;
    private String pipeName;
    private String projectCode;
    private String projectName;
    private String projectWarrantyExpireDate;
    private String provinceId;
    private Integer purchasedMaintenanceTime;
    private String renewalExpirationDate;
    private Integer resourceLifePeriodEnumId;
    private Integer startDeviceId;
    private String startDeviceName;
    private Integer startDeviceType;
    private String thirdPartyMaintenanceOrg;
    private String dataQualityPrincipal;
    private String parties;
    
    public PipeInfoBean() {
        this.areaname = null;
        this.areano = null;
    }
    
    public PipeInfoBean(final PipeInfoBean pipeInfoBean) {
        this.areaname = null;
        this.areano = null;
        this.pipeId = pipeInfoBean.pipeId;
        this.pipeName = pipeInfoBean.pipeName;
        this.alias = pipeInfoBean.alias;
        this.maintenanceAreaId = pipeInfoBean.maintenanceAreaId;
        this.maintenanceAreaName = pipeInfoBean.maintenanceAreaName;
        this.pipeLevel = pipeInfoBean.pipeLevel;
        this.length = pipeInfoBean.length;
        this.startDeviceName = pipeInfoBean.startDeviceName;
        this.endDeviceName = pipeInfoBean.endDeviceName;
        this.maintenanceModeEnumId = pipeInfoBean.maintenanceModeEnumId;
        this.maintenanceOrgId = pipeInfoBean.maintenanceOrgId;
        this.maintainerId = pipeInfoBean.maintainerId;
        this.thirdPartyMaintenanceOrg = pipeInfoBean.thirdPartyMaintenanceOrg;
        this.renewalExpirationDate = pipeInfoBean.renewalExpirationDate;
        this.maintenanceTypeEnumId = pipeInfoBean.maintenanceTypeEnumId;
        this.purchasedMaintenanceTime = pipeInfoBean.purchasedMaintenanceTime;
        this.projectCode = pipeInfoBean.projectCode;
        this.projectName = pipeInfoBean.projectName;
        this.projectWarrantyExpireDate = pipeInfoBean.projectWarrantyExpireDate;
        this.resourceLifePeriodEnumId = pipeInfoBean.resourceLifePeriodEnumId;
        this.creationDate = pipeInfoBean.creationDate;
        this.lastUpdateDate = pipeInfoBean.lastUpdateDate;
        this.deletedFlag = pipeInfoBean.deletedFlag;
        this.deletionDate = pipeInfoBean.deletionDate;
        this.provinceId = pipeInfoBean.provinceId;
    }
    
    public String getAlias() {
        return this.alias;
    }
    
    public String getAreaname() {
        return this.areaname;
    }
    
    public String getAreano() {
        return this.areano;
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
    
    public String getLastUpdateDate() {
        return this.lastUpdateDate;
    }
    
    public String getLength() {
        return this.length;
    }
    
    public String getMaintainerId() {
        return this.maintainerId;
    }
    
    public Integer getMaintenanceAreaId() {
        return this.maintenanceAreaId;
    }
    
    public String getMaintenanceAreaName() {
        return this.maintenanceAreaName;
    }
    
    public Integer getMaintenanceModeEnumId() {
        return this.maintenanceModeEnumId;
    }
    
    public String getMaintenanceOrgId() {
        return this.maintenanceOrgId;
    }
    
    public Integer getMaintenanceTypeEnumId() {
        return this.maintenanceTypeEnumId;
    }
    
    public Integer getPipeId() {
        return this.pipeId;
    }
    
    public Integer getPipeLevel() {
        return this.pipeLevel;
    }
    
    public String getPipeLevelString() {
        switch (this.pipeLevel) {
            default: {
                return "\u672a\u77e5";
            }
            case 1: {
                return "\u4e00\u5e72";
            }
            case 2: {
                return "\u4e8c\u5e72";
            }
            case 3: {
                return "\u672c\u5730";
            }
        }
    }
    
    public String getPipeName() {
        return this.pipeName;
    }
    
    public String getProjectCode() {
        return this.projectCode;
    }
    
    public String getProjectName() {
        return this.projectName;
    }
    
    public String getProjectWarrantyExpireDate() {
        return this.projectWarrantyExpireDate;
    }
    
    public String getProvinceId() {
        return this.provinceId;
    }
    
    public Integer getPurchasedMaintenanceTime() {
        return this.purchasedMaintenanceTime;
    }
    
    public String getRenewalExpirationDate() {
        return this.renewalExpirationDate;
    }
    
    public Integer getResourceLifePeriodEnumId() {
        return this.resourceLifePeriodEnumId;
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
    
    public String getThirdPartyMaintenanceOrg() {
        return this.thirdPartyMaintenanceOrg;
    }
    
    public void setAlias(final String alias) {
        this.alias = alias;
    }
    
    public void setAreaname(final String areaname) {
        this.areaname = areaname;
    }
    
    public void setAreano(final String areano) {
        this.areano = areano;
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
    
    public void setLastUpdateDate(final String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public void setLength(final String length) {
        this.length = length;
    }
    
    public void setMaintainerId(final String maintainerId) {
        this.maintainerId = maintainerId;
    }
    
    public void setMaintenanceAreaId(final Integer maintenanceAreaId) {
        this.maintenanceAreaId = maintenanceAreaId;
    }
    
    public void setMaintenanceAreaName(final String maintenanceAreaName) {
        this.maintenanceAreaName = maintenanceAreaName;
    }
    
    public void setMaintenanceModeEnumId(final Integer maintenanceModeEnumId) {
        this.maintenanceModeEnumId = maintenanceModeEnumId;
    }
    
    public void setMaintenanceOrgId(final String maintenanceOrgId) {
        this.maintenanceOrgId = maintenanceOrgId;
    }
    
    public void setMaintenanceTypeEnumId(final Integer maintenanceTypeEnumId) {
        this.maintenanceTypeEnumId = maintenanceTypeEnumId;
    }
    
    public void setPipeId(final Integer pipeId) {
        this.pipeId = pipeId;
    }
    
    public void setPipeLevel(final Integer pipeLevel) {
        this.pipeLevel = pipeLevel;
    }
    
    public void setPipeName(final String pipeName) {
        this.pipeName = pipeName;
    }
    
    public void setProjectCode(final String projectCode) {
        this.projectCode = projectCode;
    }
    
    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }
    
    public void setProjectWarrantyExpireDate(final String projectWarrantyExpireDate) {
        this.projectWarrantyExpireDate = projectWarrantyExpireDate;
    }
    
    public void setProvinceId(final String provinceId) {
        this.provinceId = provinceId;
    }
    
    public void setPurchasedMaintenanceTime(final Integer purchasedMaintenanceTime) {
        this.purchasedMaintenanceTime = purchasedMaintenanceTime;
    }
    
    public void setRenewalExpirationDate(final String renewalExpirationDate) {
        this.renewalExpirationDate = renewalExpirationDate;
    }
    
    public void setResourceLifePeriodEnumId(final Integer resourceLifePeriodEnumId) {
        this.resourceLifePeriodEnumId = resourceLifePeriodEnumId;
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
