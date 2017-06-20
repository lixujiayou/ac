// 

// 

package com.inspur.resources.bean;

import java.util.Date;
import java.io.Serializable;

public class StationBaseInfoBean implements Serializable
{
    private static final long serialVersionUID = 5837325545857167119L;
    private String authorizationManagementUnit;
    private String deleteFlag;
    private String fixedDedicated;
    private String imageNames;
    private Integer isAuthorizationManagement;
    private String lat;
    private String lon;
    private String note;
    private String projectCode;
    private Date projectGuaranteeOverTime;
    private String projectName;
    private String region;
    private String regionalism;
    private String sameStation;
    private String stationAddr;
    private Integer stationBaseId;
    private Integer stationLevel;
    private String stationName;
    private Integer stationNetWorkType;
    private Integer stationRange;
    private Integer stationType;
    private String dataQualityPrincipal;
    private String parties;
    
    public StationBaseInfoBean() {
        this.stationName = null;
        this.region = null;
        this.regionalism = null;
        this.lon = null;
        this.lat = null;
        this.stationAddr = null;
        this.projectCode = null;
        this.projectName = null;
        this.projectGuaranteeOverTime = null;
        this.authorizationManagementUnit = null;
        this.note = null;
    }
    
    public String getAuthorizationManagementUnit() {
        return this.authorizationManagementUnit;
    }
    
    public String getDeleteFlag() {
        return this.deleteFlag;
    }
    
    public String getFixedDedicated() {
        return this.fixedDedicated;
    }
    
    public String getImageNames() {
        return this.imageNames;
    }
    
    public Integer getIsAuthorizationManagement() {
        return this.isAuthorizationManagement;
    }
    
    public String getLat() {
        return this.lat;
    }
    
    public String getLon() {
        return this.lon;
    }
    
    public String getNote() {
        return this.note;
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
    
    public String getRegion() {
        return this.region;
    }
    
    public String getRegionalism() {
        return this.regionalism;
    }
    
    public String getSameStation() {
        return this.sameStation;
    }
    
    public String getStationAddr() {
        return this.stationAddr;
    }
    
    public Integer getStationBaseId() {
        return this.stationBaseId;
    }
    
    public Integer getStationLevel() {
        return this.stationLevel;
    }
    
    public String getStationName() {
        return this.stationName;
    }
    
    public Integer getStationNetWorkType() {
        return this.stationNetWorkType;
    }
    
    public Integer getStationRange() {
        return this.stationRange;
    }
    
    public Integer getStationType() {
        return this.stationType;
    }
    
    public void setAuthorizationManagementUnit(final String authorizationManagementUnit) {
        this.authorizationManagementUnit = authorizationManagementUnit;
    }
    
    public void setDeleteFlag(final String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
    public void setFixedDedicated(final String fixedDedicated) {
        this.fixedDedicated = fixedDedicated;
    }
    
    public void setImageNames(final String imageNames) {
        this.imageNames = imageNames;
    }
    
    public void setIsAuthorizationManagement(final Integer isAuthorizationManagement) {
        this.isAuthorizationManagement = isAuthorizationManagement;
    }
    
    public void setLat(final String lat) {
        this.lat = lat;
    }
    
    public void setLon(final String lon) {
        this.lon = lon;
    }
    
    public void setNote(final String note) {
        this.note = note;
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
    
    public void setRegion(final String region) {
        this.region = region;
    }
    
    public void setRegionalism(final String regionalism) {
        this.regionalism = regionalism;
    }
    
    public void setSameStation(final String sameStation) {
        this.sameStation = sameStation;
    }
    
    public void setStationAddr(final String stationAddr) {
        this.stationAddr = stationAddr;
    }
    
    public void setStationBaseId(final Integer stationBaseId) {
        this.stationBaseId = stationBaseId;
    }
    
    public void setStationLevel(final Integer stationLevel) {
        this.stationLevel = stationLevel;
    }
    
    public void setStationName(final String stationName) {
        this.stationName = stationName;
    }
    
    public void setStationNetWorkType(final Integer stationNetWorkType) {
        this.stationNetWorkType = stationNetWorkType;
    }
    
    public void setStationRange(final Integer stationRange) {
        this.stationRange = stationRange;
    }
    
    public void setStationType(final Integer stationType) {
        this.stationType = stationType;
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
