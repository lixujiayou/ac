// 

// 

package com.inspur.resources.bean;

import java.util.Date;
import java.io.Serializable;

public class GeneratorInfoBean implements Serializable
{
    private static final long serialVersionUID = 1244674356902456231L;
    private Integer affiliation;
    private String areano;// 站点ID
    private String authorizationManagementUnit;
    private String bearing;
    private Integer broadHeading;
    private Integer buildingType;
    private String canUseArea;
    private String charteredMan;
    private Date commissioningDate;
    private String deleteFlag;
    private String fireFightingSystem;
    private String floorArea;
    private String generatorAddr;
    private String generatorCommonName;
    private Integer generatorId;
    private String generatorName;
    private String height;
    private String imageNames;
    private Integer isAuthorizationManagement;
    private Integer isBaseStation;
    private Integer isHaveFloor;
    private Integer isHaveMonitor;
    private Integer isShare;
    private String lat;
    private String length;
    private String lon;
    private Integer maIntegerenanceType;
    private Integer maintainType;
    private String maintainUnit;
    private String menFX;
    private String note;
    private String otherMaintainUnit;
    private Date overTime;
    private Integer overseasPopBusinessType;
    private String projectCode;
    private Date projectGuaranteeOverTime;
    private String projectName;
    private String purchasedMaintenanceTime;
    private String region;
    private Date renewalOverTime;
    private String shareOperator;
    private Integer singleLayerChamfer;
    private String station;
    private Integer subclass;
    private Integer takeUpMode;
    private String usedArea;
    private String width;
    private Integer jflx = 0;
    private Integer ywjb = 0;
    private Integer szlc = 1;
    private String dataQualityPrincipal;
    private String parties;

    public GeneratorInfoBean() {
        this.menFX = null;
        this.generatorName = null;
        this.region = null;
        this.station = null;
        this.areano = null;
        this.lon = null;
        this.lat = null;
        this.generatorAddr = null;
        this.generatorCommonName = null;
        this.floorArea = null;
        this.canUseArea = null;
        this.usedArea = null;
        this.length = null;
        this.width = null;
        this.height = null;
        this.shareOperator = null;
        this.commissioningDate = null;
        this.bearing = null;
        this.fireFightingSystem = null;
        this.overTime = null;
        this.maintainUnit = null;
        this.charteredMan = null;
        this.otherMaintainUnit = null;
        this.renewalOverTime = null;
        this.purchasedMaintenanceTime = null;
        this.projectCode = null;
        this.projectName = null;
        this.projectGuaranteeOverTime = null;
        this.authorizationManagementUnit = null;
        this.note = null;
    }

    public Integer getAffiliation() {
        return this.affiliation;
    }

    public String getAreano() {
        return this.areano;
    }

    public String getAuthorizationManagementUnit() {
        return this.authorizationManagementUnit;
    }

    public String getBearing() {
        return this.bearing;
    }

    public Integer getBroadHeading() {
        return this.broadHeading;
    }

    public Integer getBuildingType() {
        return this.buildingType;
    }

    public String getCanUseArea() {
        return this.canUseArea;
    }

    public String getCharteredMan() {
        return this.charteredMan;
    }

    public Date getCommissioningDate() {
        return this.commissioningDate;
    }

    public String getDeleteFlag() {
        return this.deleteFlag;
    }

    public String getFireFightingSystem() {
        return this.fireFightingSystem;
    }

    public String getFloorArea() {
        return this.floorArea;
    }

    public String getGeneratorAddr() {
        return this.generatorAddr;
    }

    public String getGeneratorCommonName() {
        return this.generatorCommonName;
    }

    public Integer getGeneratorId() {
        return this.generatorId;
    }

    public String getGeneratorName() {
        return this.generatorName;
    }

    public String getHeight() {
        return this.height;
    }

    public String getImageNames() {
        return this.imageNames;
    }

    public Integer getIsAuthorizationManagement() {
        return this.isAuthorizationManagement;
    }

    public Integer getIsBaseStation() {
        return this.isBaseStation;
    }

    public Integer getIsHaveFloor() {
        return this.isHaveFloor;
    }

    public Integer getIsHaveMonitor() {
        return this.isHaveMonitor;
    }

    public Integer getIsShare() {
        return this.isShare;
    }

    public String getLat() {
        return this.lat;
    }

    public String getLength() {
        return this.length;
    }

    public String getLon() {
        return this.lon;
    }

    public Integer getMaintainType() {
        return this.maintainType;
    }

    public String getMaintainUnit() {
        return this.maintainUnit;
    }

    public Integer getMaintenanceType() {
        return this.maIntegerenanceType;
    }

    public String getMenFX() {
        return this.menFX;
    }

    public String getNote() {
        return this.note;
    }

    public String getOtherMaintainUnit() {
        return this.otherMaintainUnit;
    }

    public Date getOverTime() {
        return this.overTime;
    }

    public Integer getOverseasPopBusinessType() {
        return this.overseasPopBusinessType;
    }

    public String getProjectCode() {
        return this.projectCode;
    }

    public Date getProjectGuaranteeOverTime() {
        return this.projectGuaranteeOverTime;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public String getPurchasedMaintenanceTime() {
        return this.purchasedMaintenanceTime;
    }

    public String getRegion() {
        return this.region;
    }

    public Date getRenewalOverTime() {
        return this.renewalOverTime;
    }

    public String getShareOperator() {
        return this.shareOperator;
    }

    public Integer getSingleLayerChamfer() {
        return this.singleLayerChamfer;
    }

    public String getStation() {
        return this.station;
    }

    public Integer getSubclass() {
        return this.subclass;
    }

    public Integer getTakeUpMode() {
        return this.takeUpMode;
    }

    public String getUsedArea() {
        return this.usedArea;
    }

    public String getWidth() {
        return this.width;
    }

    public void setAffiliation(final Integer affiliation) {
        this.affiliation = affiliation;
    }

    public void setAreano(final String areano) {
        this.areano = areano;
    }

    public void setAuthorizationManagementUnit(final String authorizationManagementUnit) {
        this.authorizationManagementUnit = authorizationManagementUnit;
    }

    public void setBearing(final String bearing) {
        this.bearing = bearing;
    }

    public void setBroadHeading(final Integer broadHeading) {
        this.broadHeading = broadHeading;
    }

    public void setBuildingType(final Integer buildingType) {
        this.buildingType = buildingType;
    }

    public void setCanUseArea(final String canUseArea) {
        this.canUseArea = canUseArea;
    }

    public void setCharteredMan(final String charteredMan) {
        this.charteredMan = charteredMan;
    }

    public void setCommissioningDate(final Date commissioningDate) {
        this.commissioningDate = commissioningDate;
    }

    public void setDeleteFlag(final String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public void setFireFightingSystem(final String fireFightingSystem) {
        this.fireFightingSystem = fireFightingSystem;
    }

    public void setFloorArea(final String floorArea) {
        this.floorArea = floorArea;
    }

    public void setGeneratorAddr(final String generatorAddr) {
        this.generatorAddr = generatorAddr;
    }

    public void setGeneratorCommonName(final String generatorCommonName) {
        this.generatorCommonName = generatorCommonName;
    }

    public void setGeneratorId(final Integer generatorId) {
        this.generatorId = generatorId;
    }

    public void setGeneratorName(final String generatorName) {
        this.generatorName = generatorName;
    }

    public void setHeight(final String height) {
        this.height = height;
    }

    public void setImageNames(final String imageNames) {
        this.imageNames = imageNames;
    }

    public void setIsAuthorizationManagement(final Integer isAuthorizationManagement) {
        this.isAuthorizationManagement = isAuthorizationManagement;
    }

    public void setIsBaseStation(final Integer isBaseStation) {
        this.isBaseStation = isBaseStation;
    }

    public void setIsHaveFloor(final Integer isHaveFloor) {
        this.isHaveFloor = isHaveFloor;
    }

    public void setIsHaveMonitor(final Integer isHaveMonitor) {
        this.isHaveMonitor = isHaveMonitor;
    }

    public void setIsShare(final Integer isShare) {
        this.isShare = isShare;
    }

    public void setLat(final String lat) {
        this.lat = lat;
    }

    public void setLength(final String length) {
        this.length = length;
    }

    public void setLon(final String lon) {
        this.lon = lon;
    }

    public void setMaintainType(final Integer maintainType) {
        this.maintainType = maintainType;
    }

    public void setMaintainUnit(final String maintainUnit) {
        this.maintainUnit = maintainUnit;
    }

    public void setMaintenanceType(final Integer maIntegerenanceType) {
        this.maIntegerenanceType = maIntegerenanceType;
    }

    public void setMenFX(final String menFX) {
        this.menFX = menFX;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public void setOtherMaintainUnit(final String otherMaintainUnit) {
        this.otherMaintainUnit = otherMaintainUnit;
    }

    public void setOverTime(final Date overTime) {
        this.overTime = overTime;
    }

    public void setOverseasPopBusinessType(final Integer overseasPopBusinessType) {
        this.overseasPopBusinessType = overseasPopBusinessType;
    }

    public void setProjectCode(final String projectCode) {
        this.projectCode = projectCode;
    }

    public void setProjectGuaranteeOverTime(final Date projectGuaranteeOverTime) {
        this.projectGuaranteeOverTime = projectGuaranteeOverTime;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public void setPurchasedMaintenanceTime(final String purchasedMaintenanceTime) {
        this.purchasedMaintenanceTime = purchasedMaintenanceTime;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public void setRenewalOverTime(final Date renewalOverTime) {
        this.renewalOverTime = renewalOverTime;
    }

    public void setShareOperator(final String shareOperator) {
        this.shareOperator = shareOperator;
    }

    public void setSingleLayerChamfer(final Integer singleLayerChamfer) {
        this.singleLayerChamfer = singleLayerChamfer;
    }

    public void setStation(final String station) {
        this.station = station;
    }

    public void setSubclass(final Integer subclass) {
        this.subclass = subclass;
    }

    public void setTakeUpMode(final Integer takeUpMode) {
        this.takeUpMode = takeUpMode;
    }

    public void setUsedArea(final String usedArea) {
        this.usedArea = usedArea;
    }

    public void setWidth(final String width) {
        this.width = width;
    }

    public int getJflx() {
        return jflx;
    }

    public void setJflx(int jflx) {
        this.jflx = jflx;
    }

    public int getYwjb() {
        return ywjb;
    }

    public void setYwjb(int ywjb) {
        this.ywjb = ywjb;
    }

    public int getSzlc() {
        return szlc;
    }

    public void setSzlc(int szlc) {
        this.szlc = szlc;
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
