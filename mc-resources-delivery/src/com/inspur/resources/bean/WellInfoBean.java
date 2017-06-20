// 

// 

package com.inspur.resources.bean;

import java.io.Serializable;

public class WellInfoBean implements Serializable
{
    private static final long serialVersionUID = -8128450747533643672L;
    private String alias;
    private String areaname;
    private String areano;
    private String bottomAltitude;
    private Integer constructionSharingEnumId;
    private Integer constructionSharingOrg = 0;
    private String coverMaterialEnumId;
    private String coverQuantity;
    private String coverShapeEnumId;
    private String coverTopElevation;
    private String creationDate;
    private Integer cstate;
    private Integer deletedFlag;
    private String deletionDate;
    private String direction;
    private String faceids;
    private String hrwellmap;
    private String imageNames;
    private String lastUpdateDate;
    private String latitude;
    private String longitude;
    private String maintainerId;
    private Integer maintenanceAreaId;
    private Integer maintenanceModeEnumId;
    private String maintenanceOrgId;
    private Integer maintenanceTypeEnumId;
    private String manholeDepth;
    private String manholeLength;
    private Integer manholeShape;
    private Integer manholeSpecification;
    private Integer manholeStructure;
    private Integer manholeTypeEnumId;
    private String manholeWidth;
    private Integer pipeId;
    private String projectName;
    private String projectNumber;
    private String projectWarrantyExpireDate;
    private String provinceId;
    private String purchasedMaintenanceTime;
    private String region;
    private String renewalExpirationDate;
    private Integer rentFlag;
    private Integer resourceLifePeriodEnumId;
    private String responsiblePersonId;
    private Integer roadSurfaceTypeEnumId;
    private Integer roadTypeEnumId;
    private Integer sharingTypeEnumId;
    private String subbaseThickness;
    private String thirdPartyMaintenanceOrg;
    private String wallThickness;
    private String wellAddress;
    private Integer wellId;
    private String wellMap;
    private String wellName;
    private String wellNameSub;
    private String wellNo;
    private String wellSubNo;
    private String dataQualityPrincipal;
    private String parties;
    
    public WellInfoBean() {
        this.coverMaterialEnumId = null;
        this.coverShapeEnumId = null;
        this.coverQuantity = null;
        this.region = null;
        this.areaname = null;
        this.areano = null;
        this.wellMap = null;
        this.hrwellmap = null;
        this.faceids = null;
    }
    
    public WellInfoBean(final WellInfoBean wellInfoBean) {
        this.coverMaterialEnumId = null;
        this.coverShapeEnumId = null;
        this.coverQuantity = null;
        this.region = null;
        this.areaname = null;
        this.areano = null;
        this.wellMap = null;
        this.hrwellmap = null;
        this.faceids = null;
        this.wellId = wellInfoBean.wellId;
        this.pipeId = wellInfoBean.pipeId;
        this.wellName = wellInfoBean.wellName;
        this.direction = wellInfoBean.direction;
        this.imageNames = wellInfoBean.imageNames;
        this.wellNo = wellInfoBean.wellNo;
        this.wellSubNo = wellInfoBean.wellSubNo;
        this.wellNameSub = wellInfoBean.wellNameSub;
        this.alias = wellInfoBean.alias;
        this.maintenanceAreaId = wellInfoBean.maintenanceAreaId;
        this.manholeTypeEnumId = wellInfoBean.manholeTypeEnumId;
        this.wellAddress = wellInfoBean.wellAddress;
        this.longitude = wellInfoBean.longitude;
        this.latitude = wellInfoBean.latitude;
        this.coverTopElevation = wellInfoBean.coverTopElevation;
        this.bottomAltitude = wellInfoBean.bottomAltitude;
        this.manholeDepth = wellInfoBean.manholeDepth;
        this.manholeLength = wellInfoBean.manholeLength;
        this.manholeWidth = wellInfoBean.manholeWidth;
        this.manholeShape = wellInfoBean.manholeShape;
        this.manholeSpecification = wellInfoBean.manholeSpecification;
        this.manholeStructure = wellInfoBean.manholeStructure;
        this.roadTypeEnumId = wellInfoBean.roadTypeEnumId;
        this.roadSurfaceTypeEnumId = wellInfoBean.roadSurfaceTypeEnumId;
        this.coverMaterialEnumId = wellInfoBean.coverMaterialEnumId;
        this.coverShapeEnumId = wellInfoBean.coverShapeEnumId;
        this.coverQuantity = wellInfoBean.coverQuantity;
        this.wallThickness = wellInfoBean.wallThickness;
        this.subbaseThickness = wellInfoBean.subbaseThickness;
        this.maintenanceModeEnumId = wellInfoBean.maintenanceModeEnumId;
        this.maintenanceOrgId = wellInfoBean.maintenanceOrgId;
        this.responsiblePersonId = wellInfoBean.responsiblePersonId;
        this.maintainerId = wellInfoBean.maintainerId;
        this.thirdPartyMaintenanceOrg = wellInfoBean.thirdPartyMaintenanceOrg;
        this.renewalExpirationDate = wellInfoBean.renewalExpirationDate;
        this.maintenanceTypeEnumId = wellInfoBean.maintenanceTypeEnumId;
        this.purchasedMaintenanceTime = wellInfoBean.purchasedMaintenanceTime;
        this.projectNumber = wellInfoBean.projectNumber;
        this.projectName = wellInfoBean.projectName;
        this.projectWarrantyExpireDate = wellInfoBean.projectWarrantyExpireDate;
        this.resourceLifePeriodEnumId = wellInfoBean.resourceLifePeriodEnumId;
        this.creationDate = wellInfoBean.creationDate;
        this.lastUpdateDate = wellInfoBean.lastUpdateDate;
        this.deletedFlag = wellInfoBean.deletedFlag;
        this.deletionDate = wellInfoBean.deletionDate;
        this.provinceId = wellInfoBean.provinceId;
        this.faceids = wellInfoBean.faceids;
        this.wellMap = wellInfoBean.wellMap;
        this.hrwellmap = wellInfoBean.hrwellmap;
        this.areano = wellInfoBean.areano;
        this.areaname = wellInfoBean.areaname;
        this.constructionSharingEnumId = wellInfoBean.constructionSharingEnumId;
        this.constructionSharingOrg = wellInfoBean.constructionSharingOrg;
        this.sharingTypeEnumId = wellInfoBean.sharingTypeEnumId;
        this.rentFlag = wellInfoBean.rentFlag;
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
    
    public String getBottomAltitude() {
        return this.bottomAltitude;
    }
    
    public Integer getConstructionSharingEnumId() {
        return this.constructionSharingEnumId;
    }
    
    public int getConstructionSharingOrg() {
        return this.constructionSharingOrg;
    }
    
    public String getCoverMaterialEnumId() {
        return this.coverMaterialEnumId;
    }
    
    public String getCoverQuantity() {
        return this.coverQuantity;
    }
    
    public String getCoverShapeEnumId() {
        return this.coverShapeEnumId;
    }
    
    public String getCoverTopElevation() {
        return this.coverTopElevation;
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
    
    public String getDirection() {
        return this.direction;
    }
    
    public String getFaceids() {
        return this.faceids;
    }
    
    public String getHrwellmap() {
        return this.hrwellmap;
    }
    
    public String getImageNames() {
        return this.imageNames;
    }
    
    public String getLastUpdateDate() {
        return this.lastUpdateDate;
    }
    
    public String getLatitude() {
        return this.latitude;
    }
    
    public String getLongitude() {
        return this.longitude;
    }
    
    public String getMaintainerId() {
        return this.maintainerId;
    }
    
    public Integer getMaintenanceAreaId() {
        return this.maintenanceAreaId;
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
    
    public String getManholeDepth() {
        return this.manholeDepth;
    }
    
    public String getManholeLength() {
        return this.manholeLength;
    }
    
    public Integer getManholeShape() {
        return this.manholeShape;
    }
    
    public Integer getManholeSpecification() {
        return this.manholeSpecification;
    }
    
    public Integer getManholeStructure() {
        return this.manholeStructure;
    }
    
    public Integer getManholeTypeEnumId() {
        return this.manholeTypeEnumId;
    }
    
    public String getManholeWidth() {
        return this.manholeWidth;
    }
    
    public Integer getPipeId() {
        return this.pipeId;
    }
    
    public String getProjectName() {
        return this.projectName;
    }
    
    public String getProjectNumber() {
        return this.projectNumber;
    }
    
    public String getProjectWarrantyExpireDate() {
        return this.projectWarrantyExpireDate;
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
    
    public String getRenewalExpirationDate() {
        return this.renewalExpirationDate;
    }
    
    public Integer getRentFlag() {
        return this.rentFlag;
    }
    
    public Integer getResourceLifePeriodEnumId() {
        return this.resourceLifePeriodEnumId;
    }
    
    public String getResponsiblePersonId() {
        return this.responsiblePersonId;
    }
    
    public Integer getRoadSurfaceTypeEnumId() {
        return this.roadSurfaceTypeEnumId;
    }
    
    public Integer getRoadTypeEnumId() {
        return this.roadTypeEnumId;
    }
    
    public Integer getSharingTypeEnumId() {
        return this.sharingTypeEnumId;
    }
    
    public String getSubbaseThickness() {
        return this.subbaseThickness;
    }
    
    public String getThirdPartyMaintenanceOrg() {
        return this.thirdPartyMaintenanceOrg;
    }
    
    public String getWallThickness() {
        return this.wallThickness;
    }
    
    public String getWellAddress() {
        return this.wellAddress;
    }
    
    public Integer getWellId() {
        return this.wellId;
    }
    
    public String getWellMap() {
        return this.wellMap;
    }
    
    public String getWellName() {
        return this.wellName;
    }
    
    public String getWellNameSub() {
        return this.wellNameSub;
    }
    
    public String getWellNo() {
        return this.wellNo;
    }
    
    public String getWellSubNo() {
        return this.wellSubNo;
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
    
    public void setBottomAltitude(final String bottomAltitude) {
        this.bottomAltitude = bottomAltitude;
    }
    
    public void setConstructionSharingEnumId(final Integer constructionSharingEnumId) {
        this.constructionSharingEnumId = constructionSharingEnumId;
    }
    
    public void setConstructionSharingOrg(final int constructionSharingOrg) {
        this.constructionSharingOrg = constructionSharingOrg;
    }
    
    public void setCoverMaterialEnumId(final String coverMaterialEnumId) {
        this.coverMaterialEnumId = coverMaterialEnumId;
    }
    
    public void setCoverQuantity(final String coverQuantity) {
        this.coverQuantity = coverQuantity;
    }
    
    public void setCoverShapeEnumId(final String coverShapeEnumId) {
        this.coverShapeEnumId = coverShapeEnumId;
    }
    
    public void setCoverTopElevation(final String coverTopElevation) {
        this.coverTopElevation = coverTopElevation;
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
    
    public void setDirection(final String direction) {
        this.direction = direction;
    }
    
    public void setFaceids(final String faceids) {
        this.faceids = faceids;
    }
    
    public void setHrwellmap(final String hrwellmap) {
        this.hrwellmap = hrwellmap;
    }
    
    public void setImageNames(final String imageNames) {
        this.imageNames = imageNames;
    }
    
    public void setLastUpdateDate(final String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }
    
    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }
    
    public void setMaintainerId(final String maintainerId) {
        this.maintainerId = maintainerId;
    }
    
    public void setMaintenanceAreaId(final Integer maintenanceAreaId) {
        this.maintenanceAreaId = maintenanceAreaId;
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
    
    public void setManholeDepth(final String manholeDepth) {
        this.manholeDepth = manholeDepth;
    }
    
    public void setManholeLength(final String manholeLength) {
        this.manholeLength = manholeLength;
    }
    
    public void setManholeShape(final Integer manholeShape) {
        this.manholeShape = manholeShape;
    }
    
    public void setManholeSpecification(final Integer manholeSpecification) {
        this.manholeSpecification = manholeSpecification;
    }
    
    public void setManholeStructure(final Integer manholeStructure) {
        this.manholeStructure = manholeStructure;
    }
    
    public void setManholeTypeEnumId(final Integer manholeTypeEnumId) {
        this.manholeTypeEnumId = manholeTypeEnumId;
    }
    
    public void setManholeWidth(final String manholeWidth) {
        this.manholeWidth = manholeWidth;
    }
    
    public void setPipeId(final Integer pipeId) {
        this.pipeId = pipeId;
    }
    
    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }
    
    public void setProjectNumber(final String projectNumber) {
        this.projectNumber = projectNumber;
    }
    
    public void setProjectWarrantyExpireDate(final String projectWarrantyExpireDate) {
        this.projectWarrantyExpireDate = projectWarrantyExpireDate;
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
    
    public void setRenewalExpirationDate(final String renewalExpirationDate) {
        this.renewalExpirationDate = renewalExpirationDate;
    }
    
    public void setRentFlag(final Integer rentFlag) {
        this.rentFlag = rentFlag;
    }
    
    public void setResourceLifePeriodEnumId(final Integer resourceLifePeriodEnumId) {
        this.resourceLifePeriodEnumId = resourceLifePeriodEnumId;
    }
    
    public void setResponsiblePersonId(final String responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }
    
    public void setRoadSurfaceTypeEnumId(final Integer roadSurfaceTypeEnumId) {
        this.roadSurfaceTypeEnumId = roadSurfaceTypeEnumId;
    }
    
    public void setRoadTypeEnumId(final Integer roadTypeEnumId) {
        this.roadTypeEnumId = roadTypeEnumId;
    }
    
    public void setSharingTypeEnumId(final Integer sharingTypeEnumId) {
        this.sharingTypeEnumId = sharingTypeEnumId;
    }
    
    public void setSubbaseThickness(final String subbaseThickness) {
        this.subbaseThickness = subbaseThickness;
    }
    
    public void setThirdPartyMaintenanceOrg(final String thirdPartyMaintenanceOrg) {
        this.thirdPartyMaintenanceOrg = thirdPartyMaintenanceOrg;
    }
    
    public void setWallThickness(final String wallThickness) {
        this.wallThickness = wallThickness;
    }
    
    public void setWellAddress(final String wellAddress) {
        this.wellAddress = wellAddress;
    }
    
    public void setWellId(final Integer wellId) {
        this.wellId = wellId;
    }
    
    public void setWellMap(final String wellMap) {
        this.wellMap = wellMap;
    }
    
    public void setWellName(final String wellName) {
        this.wellName = wellName;
    }
    
    public void setWellNameSub(final String wellNameSub) {
        this.wellNameSub = wellNameSub;
    }
    
    public void setWellNo(final String wellNo) {
        this.wellNo = wellNo;
    }
    
    public void setWellSubNo(final String wellSubNo) {
        this.wellSubNo = wellSubNo;
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
