// 

// 

package com.inspur.resources.bean;

import java.util.Date;
import java.io.Serializable;

public class SuspensionWireSegInfoBean implements Serializable
{
    private String authorizationManagementUnit;
    private String bearCableSegment;
    private String bearCableSegmentId;
    private String caizhi;
    private String designPurposes;
    private Integer endId;
    private String endName;
    private String endType;
    private String guige;
    private Integer isAuthorizationManagement;
    private String length;
    private Date lifecycleTime;
    private String note;
    private String redunBearCableSegment;
    private String redunBearCableSegmentId;
    private String region;
    private Integer resourceLifePeriodEnumId;
    private Integer startId;
    private String startName;
    private String startType;
    private Integer suspensionWireId;
    private String suspensionWireName;
    private String suspensionWireSegCode;
    private Integer suspensionWireSegId;
    private String suspensionWireSegName;
    private String tiaoshu;
    private String dataQualityPrincipal;
    private String parties;
    
    public SuspensionWireSegInfoBean() {
        this.suspensionWireSegName = null;
        this.suspensionWireSegCode = null;
        this.region = null;
        this.suspensionWireName = null;
        this.redunBearCableSegment = null;
        this.redunBearCableSegmentId = null;
        this.startName = null;
        this.endName = null;
        this.startId = null;
        this.endId = null;
        this.startType = null;
        this.endType = null;
        this.caizhi = null;
        this.guige = null;
        this.tiaoshu = null;
        this.length = null;
        this.authorizationManagementUnit = null;
        this.note = null;
    }
    
    public SuspensionWireSegInfoBean(final SuspensionWireSegInfoBean suspensionWireSegInfoBean) {
        this.suspensionWireSegName = null;
        this.suspensionWireSegCode = null;
        this.region = null;
        this.suspensionWireName = null;
        this.redunBearCableSegment = null;
        this.redunBearCableSegmentId = null;
        this.startName = null;
        this.endName = null;
        this.startId = null;
        this.endId = null;
        this.startType = null;
        this.endType = null;
        this.caizhi = null;
        this.guige = null;
        this.tiaoshu = null;
        this.length = null;
        this.authorizationManagementUnit = null;
        this.note = null;
        this.suspensionWireSegId = suspensionWireSegInfoBean.suspensionWireSegId;
        this.suspensionWireSegName = suspensionWireSegInfoBean.suspensionWireSegName;
        this.region = suspensionWireSegInfoBean.region;
        this.bearCableSegment = suspensionWireSegInfoBean.bearCableSegment;
        this.bearCableSegmentId = suspensionWireSegInfoBean.bearCableSegmentId;
        this.redunBearCableSegment = suspensionWireSegInfoBean.redunBearCableSegment;
        this.redunBearCableSegmentId = suspensionWireSegInfoBean.redunBearCableSegmentId;
        this.suspensionWireSegCode = suspensionWireSegInfoBean.suspensionWireSegCode;
        this.suspensionWireName = suspensionWireSegInfoBean.suspensionWireName;
        this.suspensionWireId = suspensionWireSegInfoBean.suspensionWireId;
        this.startName = suspensionWireSegInfoBean.startName;
        this.endName = suspensionWireSegInfoBean.endName;
        this.startId = suspensionWireSegInfoBean.startId;
        this.endId = suspensionWireSegInfoBean.endId;
        this.startType = suspensionWireSegInfoBean.startType;
        this.lifecycleTime = suspensionWireSegInfoBean.lifecycleTime;
        this.note = suspensionWireSegInfoBean.note;
        this.endType = suspensionWireSegInfoBean.endType;
        this.caizhi = suspensionWireSegInfoBean.caizhi;
        this.guige = suspensionWireSegInfoBean.guige;
        this.tiaoshu = suspensionWireSegInfoBean.tiaoshu;
        this.length = suspensionWireSegInfoBean.length;
        this.resourceLifePeriodEnumId = suspensionWireSegInfoBean.resourceLifePeriodEnumId;
        this.isAuthorizationManagement = suspensionWireSegInfoBean.isAuthorizationManagement;
        this.authorizationManagementUnit = suspensionWireSegInfoBean.authorizationManagementUnit;
        this.designPurposes = suspensionWireSegInfoBean.designPurposes;
    }
    
    public String getAuthorizationManagementUnit() {
        return this.authorizationManagementUnit;
    }
    
    public String getBearCableSegment() {
        return this.bearCableSegment;
    }
    
    public String getBearCableSegmentId() {
        return this.bearCableSegmentId;
    }
    
    public String getCaizhi() {
        return this.caizhi;
    }
    
    public String getDesignPurposes() {
        return this.designPurposes;
    }
    
    public Integer getEndId() {
        return this.endId;
    }
    
    public String getEndName() {
        return this.endName;
    }
    
    public String getEndType() {
        return this.endType;
    }
    
    public String getGuige() {
        return this.guige;
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
    
    public String getRedunBearCableSegment() {
        return this.redunBearCableSegment;
    }
    
    public String getRedunBearCableSegmentId() {
        return this.redunBearCableSegmentId;
    }
    
    public String getRegion() {
        return this.region;
    }
    
    public Integer getResourceLifePeriodEnumId() {
        return this.resourceLifePeriodEnumId;
    }
    
    public Integer getStartId() {
        return this.startId;
    }
    
    public String getStartName() {
        return this.startName;
    }
    
    public String getStartType() {
        return this.startType;
    }
    
    public Integer getSuspensionWireId() {
        return this.suspensionWireId;
    }
    
    public String getSuspensionWireName() {
        return this.suspensionWireName;
    }
    
    public String getSuspensionWireSegCode() {
        return this.suspensionWireSegCode;
    }
    
    public Integer getSuspensionWireSegId() {
        return this.suspensionWireSegId;
    }
    
    public String getSuspensionWireSegName() {
        return this.suspensionWireSegName;
    }
    
    public String getTiaoshu() {
        return this.tiaoshu;
    }
    
    public void setAuthorizationManagementUnit(final String authorizationManagementUnit) {
        this.authorizationManagementUnit = authorizationManagementUnit;
    }
    
    public void setBearCableSegment(final String bearCableSegment) {
        this.bearCableSegment = bearCableSegment;
    }
    
    public void setBearCableSegmentId(final String bearCableSegmentId) {
        this.bearCableSegmentId = bearCableSegmentId;
    }
    
    public void setCaizhi(final String caizhi) {
        this.caizhi = caizhi;
    }
    
    public void setDesignPurposes(final String designPurposes) {
        this.designPurposes = designPurposes;
    }
    
    public void setEndId(final Integer endId) {
        this.endId = endId;
    }
    
    public void setEndName(final String endName) {
        this.endName = endName;
    }
    
    public void setEndType(final String endType) {
        this.endType = endType;
    }
    
    public void setGuige(final String guige) {
        this.guige = guige;
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
    
    public void setRedunBearCableSegment(final String redunBearCableSegment) {
        this.redunBearCableSegment = redunBearCableSegment;
    }
    
    public void setRedunBearCableSegmentId(final String redunBearCableSegmentId) {
        this.redunBearCableSegmentId = redunBearCableSegmentId;
    }
    
    public void setRegion(final String region) {
        this.region = region;
    }
    
    public void setResourceLifePeriodEnumId(final Integer resourceLifePeriodEnumId) {
        this.resourceLifePeriodEnumId = resourceLifePeriodEnumId;
    }
    
    public void setStartId(final Integer startId) {
        this.startId = startId;
    }
    
    public void setStartName(final String startName) {
        this.startName = startName;
    }
    
    public void setStartType(final String startType) {
        this.startType = startType;
    }
    
    public void setSuspensionWireId(final Integer suspensionWireId) {
        this.suspensionWireId = suspensionWireId;
    }
    
    public void setSuspensionWireName(final String suspensionWireName) {
        this.suspensionWireName = suspensionWireName;
    }
    
    public void setSuspensionWireSegCode(final String suspensionWireSegCode) {
        this.suspensionWireSegCode = suspensionWireSegCode;
    }
    
    public void setSuspensionWireSegId(final Integer suspensionWireSegId) {
        this.suspensionWireSegId = suspensionWireSegId;
    }
    
    public void setSuspensionWireSegName(final String suspensionWireSegName) {
        this.suspensionWireSegName = suspensionWireSegName;
    }
    
    public void setTiaoshu(final String tiaoshu) {
        this.tiaoshu = tiaoshu;
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
