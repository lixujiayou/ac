// 

// 

package com.inspur.resources.bean;

import java.util.Date;
import java.io.Serializable;

public class PoleInfoBean implements Serializable
{
    private String areaname;
    private String areano;
    private String buriedDepth;
    private String creationDate;
    private String deletedFlag;
    private String deletionDate;
    private String direction;
    private String id;
    private String imageNames;
    private String lastUpdateDate;
    private String maintainerId;
    private String maintenanceModeEnumId;
    private String maintenanceOrgId;
    private String maintenanceTypeEnumId;
    private String note;
    private String overhaulRecord;
    private String poleAddress;
    private String poleCode;
    private Integer poleId;
    private String poleLatitude;
    private String poleLength;
    private Integer poleLineId;
    private String poleLongitude;
    private String poleMaterial;
    private String poleName;
    private String poleNameSub;
    private String poleNo;
    private String poleRadius;
    private String poleStd;
    private String poleSubNo;
    private String poleTypeEnumId;
    private String poleWireForm;
    private String provinceId;
    private String purchasedMaintenanceTime;
    private String region;
    private Date renewalExpirationDate;
    private String status;
    private int subordinateUnits;
    private String thirdPartyMaintenanceOrg;
    private int gjgxlx;
    private String dataQualityPrincipal;
    private String parties;
    private Integer chengzaidian_type;
    private Integer previousPoleID;//
    private String previousPoleName;//
    
    public PoleInfoBean() {
        this.id = null;
        this.areano = null;
        this.areaname = null;
        this.region = null;
        this.poleStd = null;
        this.poleWireForm = null;
        this.status = null;
        this.overhaulRecord = null;
        this.note = null;
    }
    
    public PoleInfoBean(final PoleInfoBean poleInfoBean) {
        this.id = null;
        this.areano = null;
        this.areaname = null;
        this.region = null;
        this.poleStd = null;
        this.poleWireForm = null;
        this.status = null;
        this.overhaulRecord = null;
        this.note = null;
        this.id = poleInfoBean.id;
        this.poleId = poleInfoBean.poleId;
        this.poleName = poleInfoBean.poleName;
        this.imageNames = poleInfoBean.imageNames;
        this.direction = poleInfoBean.direction;
        this.region = poleInfoBean.region;
        this.poleNo = poleInfoBean.poleNo;
        this.poleSubNo = poleInfoBean.poleSubNo;
        this.poleNameSub = poleInfoBean.poleNameSub;
        this.poleCode = poleInfoBean.poleCode;
        this.areano = poleInfoBean.areano;
        this.poleLineId = poleInfoBean.poleLineId;
        this.poleTypeEnumId = poleInfoBean.poleTypeEnumId;
        this.poleMaterial = poleInfoBean.poleMaterial;
        this.poleLength = poleInfoBean.poleLength;
        this.buriedDepth = poleInfoBean.buriedDepth;
        this.poleRadius = poleInfoBean.poleRadius;
        this.poleAddress = poleInfoBean.poleAddress;
        this.poleLongitude = poleInfoBean.poleLongitude;
        this.poleLatitude = poleInfoBean.poleLatitude;
        this.maintenanceModeEnumId = poleInfoBean.maintenanceModeEnumId;
        this.maintenanceOrgId = poleInfoBean.maintenanceOrgId;
        this.maintainerId = poleInfoBean.maintainerId;
        this.thirdPartyMaintenanceOrg = poleInfoBean.thirdPartyMaintenanceOrg;
        this.renewalExpirationDate = poleInfoBean.renewalExpirationDate;
        this.maintenanceTypeEnumId = poleInfoBean.maintenanceTypeEnumId;
        this.purchasedMaintenanceTime = poleInfoBean.purchasedMaintenanceTime;
        this.creationDate = poleInfoBean.creationDate;
        this.lastUpdateDate = poleInfoBean.lastUpdateDate;
        this.deletedFlag = poleInfoBean.deletedFlag;
        this.deletionDate = poleInfoBean.deletionDate;
        this.provinceId = poleInfoBean.provinceId;
        this.areaname = poleInfoBean.areaname;
        this.poleStd = poleInfoBean.poleStd;
        this.poleWireForm = poleInfoBean.poleWireForm;
        this.status = poleInfoBean.status;
        this.overhaulRecord = poleInfoBean.overhaulRecord;
        this.note = poleInfoBean.note;
        this.chengzaidian_type = poleInfoBean.chengzaidian_type;
        this.previousPoleID = poleInfoBean.previousPoleID;
        this.previousPoleName = poleInfoBean.previousPoleName;
    }
    
    
    
    public Integer getChengzaidian_type()
	{
		return chengzaidian_type;
	}

	public void setChengzaidian_type(Integer chengzaidian_type)
	{
		this.chengzaidian_type = chengzaidian_type;
	}

	public String getAreaName() {
        return this.areaname;
    }
	
	
    
    public Integer getPreviousPoleID()
	{
		return previousPoleID;
	}

	public void setPreviousPoleID(Integer previousPoleID)
	{
		this.previousPoleID = previousPoleID;
	}

	public String getPreviousPoleName()
	{
		return previousPoleName;
	}

	public void setPreviousPoleName(String previousPoleName)
	{
		this.previousPoleName = previousPoleName;
	}

	public String getAreaNo() {
        return this.areano;
    }
    
    public String getAreaname() {
        return this.areaname;
    }
    
    public String getAreano() {
        return this.areano;
    }
    
    public String getBuriedDepth() {
        return this.buriedDepth;
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
    
    public String getDirection() {
        return this.direction;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getImageNames() {
        return this.imageNames;
    }
    
    public String getLastUpdateDate() {
        return this.lastUpdateDate;
    }
    
    public String getMaintainerId() {
        return this.maintainerId;
    }
    
    public String getMaintenanceModeEnumId() {
        return this.maintenanceModeEnumId;
    }
    
    public String getMaintenanceOrgId() {
        return this.maintenanceOrgId;
    }
    
    public String getMaintenanceTypeEnumId() {
        return this.maintenanceTypeEnumId;
    }
    
    public String getNote() {
        return this.note;
    }
    
    public String getOverhaulRecord() {
        return this.overhaulRecord;
    }
    
    public String getPoleAddress() {
        return this.poleAddress;
    }
    
    public String getPoleCode() {
        return this.poleCode;
    }
    
    public Integer getPoleId() {
        return this.poleId;
    }
    
    public String getPoleLatitude() {
        return this.poleLatitude;
    }
    
    public String getPoleLength() {
        return this.poleLength;
    }
    
    public Integer getPoleLineId() {
        return this.poleLineId;
    }
    
    public String getPoleLongitude() {
        return this.poleLongitude;
    }
    
    public String getPoleMaterial() {
        return this.poleMaterial;
    }
    
    public String getPoleName() {
        return this.poleName;
    }
    
    public String getPoleNameSub() {
        return this.poleNameSub;
    }
    
    public String getPoleNo() {
        return this.poleNo;
    }
    
    public String getPoleRadius() {
        return this.poleRadius;
    }
    
    public String getPoleStd() {
        return this.poleStd;
    }
    
    public String getPoleSubNo() {
        return this.poleSubNo;
    }
    
    public String getPoleTypeEnumId() {
        return this.poleTypeEnumId;
    }
    
    public String getPoleWireForm() {
        return this.poleWireForm;
    }
    
    public String getProvinceId() {
        return this.provinceId;
    }
    
    public String getPurchasedMaintenanceTime() {
        return this.purchasedMaintenanceTime;
    }
    
    public String getRegion() {
        return this.region;
    }
    
    public Date getRenewalExpirationDate() {
        return this.renewalExpirationDate;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public int getSubordinateUnits() {
        return this.subordinateUnits;
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
    
    public void setAreaname(final String areaname) {
        this.areaname = areaname;
    }
    
    public void setAreano(final String areano) {
        this.areano = areano;
    }
    
    public void setBuriedDepth(final String buriedDepth) {
        this.buriedDepth = buriedDepth;
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
    
    public void setDirection(final String direction) {
        this.direction = direction;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public void setImageNames(final String imageNames) {
        this.imageNames = imageNames;
    }
    
    public void setLastUpdateDate(final String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public void setMaintainerId(final String maintainerId) {
        this.maintainerId = maintainerId;
    }
    
    public void setMaintenanceModeEnumId(final String maintenanceModeEnumId) {
        this.maintenanceModeEnumId = maintenanceModeEnumId;
    }
    
    public void setMaintenanceOrgId(final String maintenanceOrgId) {
        this.maintenanceOrgId = maintenanceOrgId;
    }
    
    public void setMaintenanceTypeEnumId(final String maintenanceTypeEnumId) {
        this.maintenanceTypeEnumId = maintenanceTypeEnumId;
    }
    
    public void setNote(final String note) {
        this.note = note;
    }
    
    public void setOverhaulRecord(final String overhaulRecord) {
        this.overhaulRecord = overhaulRecord;
    }
    
    public void setPoleAddress(final String poleAddress) {
        this.poleAddress = poleAddress;
    }
    
    public void setPoleCode(final String poleCode) {
        this.poleCode = poleCode;
    }
    
    public void setPoleId(final Integer poleId) {
        this.poleId = poleId;
    }
    
    public void setPoleLatitude(final String poleLatitude) {
        this.poleLatitude = poleLatitude;
    }
    
    public void setPoleLength(final String poleLength) {
        this.poleLength = poleLength;
    }
    
    public void setPoleLineId(final Integer poleLineId) {
        this.poleLineId = poleLineId;
    }
    
    public void setPoleLongitude(final String poleLongitude) {
        this.poleLongitude = poleLongitude;
    }
    
    public void setPoleMaterial(final String poleMaterial) {
        this.poleMaterial = poleMaterial;
    }
    
    public void setPoleName(final String poleName) {
        this.poleName = poleName;
    }
    
    public void setPoleNameSub(final String poleNameSub) {
        this.poleNameSub = poleNameSub;
    }
    
    public void setPoleNo(final String poleNo) {
        this.poleNo = poleNo;
    }
    
    public void setPoleRadius(final String poleRadius) {
        this.poleRadius = poleRadius;
    }
    
    public void setPoleStd(final String poleStd) {
        this.poleStd = poleStd;
    }
    
    public void setPoleSubNo(final String poleSubNo) {
        this.poleSubNo = poleSubNo;
    }
    
    public void setPoleTypeEnumId(final String poleTypeEnumId) {
        this.poleTypeEnumId = poleTypeEnumId;
    }
    
    public void setPoleWireForm(final String poleWireForm) {
        this.poleWireForm = poleWireForm;
    }
    
    public void setProvinceId(final String provinceId) {
        this.provinceId = provinceId;
    }
    
    public void setPurchasedMaintenanceTime(final String purchasedMaintenanceTime) {
        this.purchasedMaintenanceTime = purchasedMaintenanceTime;
    }
    
    public void setRegion(final String region) {
        this.region = region;
    }
    
    public void setRenewalExpirationDate(final Date renewalExpirationDate) {
        this.renewalExpirationDate = renewalExpirationDate;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    public void setSubordinateUnits(final int subordinateUnits) {
        this.subordinateUnits = subordinateUnits;
    }
    
    public void setThirdPartyMaintenanceOrg(final String thirdPartyMaintenanceOrg) {
        this.thirdPartyMaintenanceOrg = thirdPartyMaintenanceOrg;
    }

	public int getGjgxlx() {
		return gjgxlx;
	}

	public void setGjgxlx(int gjgxlx) {
		this.gjgxlx = gjgxlx;
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
