// 

// 

package com.inspur.resources.bean;

import java.util.Date;
import java.io.Serializable;

public class JointInfoBean implements Serializable
{
    private String addr;
    private String addrType;
    private String authorizationManagementUnit;
    private Integer cableId;
    private String cableName;
    private String changjia;
    private String changshang;
    private String designPurposes;
    private String gjbj;
    private Integer isAuthorizationManagement;
    private Integer jointId;
    private String jointName;
    private String jointNameSub;
    private String jointNo;
    private String jointType;
    private String jointggxh;
    private Date lifecycleTime;
    private String note;
    private Integer poleId;
    private String propertyBqh;
    private Integer resourceLifePeriodEnumId;
    private String tcfs;
    private Integer wellId;
    private String whzt;
    private String dataQualityPrincipal;
    private String parties;
    private Integer stoneId;

    
    public JointInfoBean() {
        this.cableName = null;
        this.addrType = null;
        this.changjia = null;
        this.changshang = null;
        this.propertyBqh = null;
        this.addr = null;
        this.gjbj = null;
        this.authorizationManagementUnit = null;
        this.note = null;
    }
    
    public JointInfoBean(final JointInfoBean jointInfoBean) {
    	this.stoneId = jointInfoBean.stoneId;
        this.cableName = null;
        this.addrType = null;
        this.changjia = null;
        this.changshang = null;
        this.propertyBqh = null;
        this.addr = null;
        this.gjbj = null;
        this.authorizationManagementUnit = null;
        this.note = null;
        this.jointId = jointInfoBean.jointId;
        this.wellId = jointInfoBean.wellId;
        this.poleId = jointInfoBean.poleId;
        this.jointName = jointInfoBean.jointName;
        this.jointNo = jointInfoBean.jointNo;
        this.jointNameSub = jointInfoBean.jointNameSub;
        this.jointType = jointInfoBean.jointType;
        this.jointType = jointInfoBean.jointType;
        this.cableId = jointInfoBean.cableId;
        this.cableName = jointInfoBean.cableName;
        this.addrType = jointInfoBean.addrType;
        this.changjia = jointInfoBean.changjia;
        this.changshang = jointInfoBean.changshang;
        this.propertyBqh = jointInfoBean.propertyBqh;
        this.addr = jointInfoBean.addr;
        this.tcfs = jointInfoBean.tcfs;
        this.whzt = jointInfoBean.whzt;
        this.gjbj = jointInfoBean.gjbj;
        this.resourceLifePeriodEnumId = jointInfoBean.resourceLifePeriodEnumId;
        this.lifecycleTime = jointInfoBean.lifecycleTime;
        this.isAuthorizationManagement = jointInfoBean.isAuthorizationManagement;
        this.authorizationManagementUnit = jointInfoBean.authorizationManagementUnit;
        this.designPurposes = jointInfoBean.designPurposes;
        this.designPurposes = jointInfoBean.designPurposes;
        this.note = jointInfoBean.note;
    }
    
    public String getAddr() {
        return this.addr;
    }
    
    public String getAddrType() {
        return this.addrType;
    }
    
    public String getAuthorizationManagementUnit() {
        return this.authorizationManagementUnit;
    }
    
    public Integer getCableId() {
        return this.cableId;
    }
    
    public String getCableName() {
        return this.cableName;
    }
    
    public String getChangjia() {
        return this.changjia;
    }
    
    public String getChangshang() {
        return this.changshang;
    }
    
    public String getDesignPurposes() {
        return this.designPurposes;
    }
    
    public String getGjbj() {
        return this.gjbj;
    }
    
    public Integer getIsAuthorizationManagement() {
        return this.isAuthorizationManagement;
    }
    
    public Integer getJointId() {
        return this.jointId;
    }
    
    public String getJointName() {
        return this.jointName;
    }
    
    public String getJointNameSub() {
        return this.jointNameSub;
    }
    
    public String getJointNo() {
        return this.jointNo;
    }
    
    public String getJointType() {
        return this.jointType;
    }
    
    public String getJointggxh() {
        return this.jointggxh;
    }
    
    public Date getLifecycleTime() {
        return this.lifecycleTime;
    }
    
    public String getNote() {
        return this.note;
    }
    
    public Integer getPoleId() {
        return this.poleId;
    }
    
    public String getPropertyBqh() {
        return this.propertyBqh;
    }
    
    public Integer getResourceLifePeriodEnumId() {
        return this.resourceLifePeriodEnumId;
    }
    
    public String getTcfs() {
        return this.tcfs;
    }
    
    public Integer getWellId() {
        return this.wellId;
    }
    
    public String getWhzt() {
        return this.whzt;
    }
    
    public void setAddr(final String addr) {
        this.addr = addr;
    }
    
    public void setAddrType(final String addrType) {
        this.addrType = addrType;
    }
    
    public void setAuthorizationManagementUnit(final String authorizationManagementUnit) {
        this.authorizationManagementUnit = authorizationManagementUnit;
    }
    
    public void setCableId(final Integer cableId) {
        this.cableId = cableId;
    }
    
    public void setCableName(final String cableName) {
        this.cableName = cableName;
    }
    
    public void setChangjia(final String changjia) {
        this.changjia = changjia;
    }
    
    public void setChangshang(final String changshang) {
        this.changshang = changshang;
    }
    
    public void setDesignPurposes(final String designPurposes) {
        this.designPurposes = designPurposes;
    }
    
    public void setGjbj(final String gjbj) {
        this.gjbj = gjbj;
    }
    
    public void setIsAuthorizationManagement(final Integer isAuthorizationManagement) {
        this.isAuthorizationManagement = isAuthorizationManagement;
    }
    
    public void setJointId(final Integer jointId) {
        this.jointId = jointId;
    }
    
    public void setJointName(final String jointName) {
        this.jointName = jointName;
    }
    
    public void setJointNameSub(final String jointNameSub) {
        this.jointNameSub = jointNameSub;
    }
    
    public void setJointNo(final String jointNo) {
        this.jointNo = jointNo;
    }
    
    public void setJointType(final String jointType) {
        this.jointType = jointType;
    }
    
    public void setJointggxh(final String jointggxh) {
        this.jointggxh = jointggxh;
    }
    
    public void setLifecycleTime(final Date lifecycleTime) {
        this.lifecycleTime = lifecycleTime;
    }
    
    public void setNote(final String note) {
        this.note = note;
    }
    
    public void setPoleId(final Integer poleId) {
        this.poleId = poleId;
    }
    
    public void setPropertyBqh(final String propertyBqh) {
        this.propertyBqh = propertyBqh;
    }
    
    public void setResourceLifePeriodEnumId(final Integer resourceLifePeriodEnumId) {
        this.resourceLifePeriodEnumId = resourceLifePeriodEnumId;
    }
    
    public void setTcfs(final String tcfs) {
        this.tcfs = tcfs;
    }
    
    public void setWellId(final Integer wellId) {
        this.wellId = wellId;
    }
    
    public void setWhzt(final String whzt) {
        this.whzt = whzt;
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

	public Integer getStoneId()
	{
		return stoneId;
	}

	public void setStoneId(Integer stoneId)
	{
		this.stoneId = stoneId;
	}
	
	
}
