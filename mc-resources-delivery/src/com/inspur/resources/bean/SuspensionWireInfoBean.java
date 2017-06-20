// 

// 

package com.inspur.resources.bean;

import java.util.Date;
import java.io.Serializable;

public class SuspensionWireInfoBean implements Serializable
{
    private static final long serialVersionUID = -6961047198235531478L;
    private String authorizationManagementUnit;
    private String designPurposes;
    private Integer isAuthorizationManagement;
    private String length;
    private Date lifecycleTime;
    private String note;
    private Integer poleLineId;
    private String poleLineName;
    private String region;
    private Integer resourceLifePeriodEnumId;
    private String suspensionWireCode;
    private Integer suspensionWireId;
    private String suspensionWireName;
    private String dataQualityPrincipal;
    private String parties;
    
    public SuspensionWireInfoBean() {
        this.suspensionWireName = null;
        this.suspensionWireCode = null;
        this.region = null;
        this.poleLineName = null;
        this.length = null;
        this.authorizationManagementUnit = null;
        this.note = null;
    }
    
    public String getAuthorizationManagementUnit() {
        return this.authorizationManagementUnit;
    }
    
    public String getDesignPurposes() {
        return this.designPurposes;
    }
    
    public Integer getIsAuthorizationManagement() {
        return this.isAuthorizationManagement;
    }
    
    public String getLength() {
        return this.length;
    }
    
    public Date getLifecycleTime() {
        return this.lifecycleTime;
    }
    
    public String getNote() {
        return this.note;
    }
    
    public Integer getPoleLineId() {
        return this.poleLineId;
    }
    
    public String getPoleLineName() {
        return this.poleLineName;
    }
    
    public String getRegion() {
        return this.region;
    }
    
    public Integer getResourceLifePeriodEnumId() {
        return this.resourceLifePeriodEnumId;
    }
    
    public String getSuspensionWireCode() {
        return this.suspensionWireCode;
    }
    
    public Integer getSuspensionWireId() {
        return this.suspensionWireId;
    }
    
    public String getSuspensionWireName() {
        return this.suspensionWireName;
    }
    
    public void setAuthorizationManagementUnit(final String authorizationManagementUnit) {
        this.authorizationManagementUnit = authorizationManagementUnit;
    }
    
    public void setDesignPurposes(final String designPurposes) {
        this.designPurposes = designPurposes;
    }
    
    public void setIsAuthorizationManagement(final Integer isAuthorizationManagement) {
        this.isAuthorizationManagement = isAuthorizationManagement;
    }
    
    public void setLength(final String length) {
        this.length = length;
    }
    
    public void setLifecycleTime(final Date lifecycleTime) {
        this.lifecycleTime = lifecycleTime;
    }
    
    public void setNote(final String note) {
        this.note = note;
    }
    
    public void setPoleLineId(final Integer poleLineId) {
        this.poleLineId = poleLineId;
    }
    
    public void setPoleLineName(final String poleLineName) {
        this.poleLineName = poleLineName;
    }
    
    public void setRegion(final String region) {
        this.region = region;
    }
    
    public void setResourceLifePeriodEnumId(final Integer resourceLifePeriodEnumId) {
        this.resourceLifePeriodEnumId = resourceLifePeriodEnumId;
    }
    
    public void setSuspensionWireCode(final String suspensionWireCode) {
        this.suspensionWireCode = suspensionWireCode;
    }
    
    public void setSuspensionWireId(final Integer suspensionWireId) {
        this.suspensionWireId = suspensionWireId;
    }
    
    public void setSuspensionWireName(final String suspensionWireName) {
        this.suspensionWireName = suspensionWireName;
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
