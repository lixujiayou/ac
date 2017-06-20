// 

// 

package com.inspur.resources.bean;

import java.util.Date;
import java.io.Serializable;

public class HighFrequencySwitchingPowerSupplyInfoBean implements Serializable
{
    private static final long serialVersionUID = 8708643579504703864L;
    private String GID;
    private String SuppliedModuleNum;
    private String actualUsePower;
    private String alreadUsedModuleNum;
    private String authorizationManagementUnit;
    private String charteredMan;
    private Integer cutMark;
    private String deleteFlag;
    private String designPurposes;
    private String deviceAlias;
    private Integer deviceMaker;
    private String deviceName;
    private String fixedAssetsCode;
    private String generator;
    private String imageNames;
    private Date inNetTime;
    private Integer isAuthorizationManagement;
    private Integer isEnvironmentMonitoring;
    private String lat;
    private Integer lifecycle;
    private Date lifecycleTime;
    private String littleDeviceMaker;
    private String lon;
    private Integer maintainState;
    private Integer maintenanceMode;
    private Integer maintenanceType;
    private String maintenanceUnit;
    private String model;
    private String monitoringSystemName;
    private String note;
    private String oldResourceSystemEquName;
    private String oldResourceSystemEquNum;
    private String otherMaintainUnit;
    private String posX;
    private String posY;
    private String powerSystem;
    private String projectCode;
    private Date projectGuaranteeOverTime;
    private String projectName;
    private String purchasedMaintenanceTime;
    private String ratedElectricity;
    private String ratedVoltage;
    private String rectifierModuleModel;
    private String rectifierModuleNum;
    private Date renewalOverTime;
    private String supervisoryModuleModel;
    private Integer switchingId;
    
    public HighFrequencySwitchingPowerSupplyInfoBean() {
        this.lon = null;
        this.lat = null;
        this.note = null;
    }
    
    public String getActualUsePower() {
        return this.actualUsePower;
    }
    
    public String getAlreadUsedModuleNum() {
        return this.alreadUsedModuleNum;
    }
    
    public String getAuthorizationManagementUnit() {
        return this.authorizationManagementUnit;
    }
    
    public String getCharteredMan() {
        return this.charteredMan;
    }
    
    public Integer getCutMark() {
        return this.cutMark;
    }
    
    public String getDeleteFlag() {
        return this.deleteFlag;
    }
    
    public String getDesignPurposes() {
        return this.designPurposes;
    }
    
    public String getDeviceAlias() {
        return this.deviceAlias;
    }
    
    public Integer getDeviceMaker() {
        return this.deviceMaker;
    }
    
    public String getDeviceName() {
        return this.deviceName;
    }
    
    public String getFixedAssetsCode() {
        return this.fixedAssetsCode;
    }
    
    public String getGID() {
        return this.GID;
    }
    
    public String getGenerator() {
        return this.generator;
    }
    
    public String getImageNames() {
        return this.imageNames;
    }
    
    public Date getInNetTime() {
        return this.inNetTime;
    }
    
    public Integer getIsAuthorizationManagement() {
        return this.isAuthorizationManagement;
    }
    
    public Integer getIsEnvironmentMonitoring() {
        return this.isEnvironmentMonitoring;
    }
    
    public String getLat() {
        return this.lat;
    }
    
    public Integer getLifecycle() {
        return this.lifecycle;
    }
    
    public Date getLifecycleTime() {
        return this.lifecycleTime;
    }
    
    public String getLittleDeviceMaker() {
        return this.littleDeviceMaker;
    }
    
    public String getLon() {
        return this.lon;
    }
    
    public Integer getMaintainState() {
        return this.maintainState;
    }
    
    public Integer getMaintenanceMode() {
        return this.maintenanceMode;
    }
    
    public Integer getMaintenanceType() {
        return this.maintenanceType;
    }
    
    public String getMaintenanceUnit() {
        return this.maintenanceUnit;
    }
    
    public String getModel() {
        return this.model;
    }
    
    public String getMonitoringSystemName() {
        return this.monitoringSystemName;
    }
    
    public String getNote() {
        return this.note;
    }
    
    public String getOldResourceSystemEquName() {
        return this.oldResourceSystemEquName;
    }
    
    public String getOldResourceSystemEquNum() {
        return this.oldResourceSystemEquNum;
    }
    
    public String getOtherMaintainUnit() {
        return this.otherMaintainUnit;
    }
    
    public String getPosX() {
        return this.posX;
    }
    
    public String getPosY() {
        return this.posY;
    }
    
    public String getPowerSystem() {
        return this.powerSystem;
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
    
    public String getRatedElectricity() {
        return this.ratedElectricity;
    }
    
    public String getRatedVoltage() {
        return this.ratedVoltage;
    }
    
    public String getRectifierModuleModel() {
        return this.rectifierModuleModel;
    }
    
    public String getRectifierModuleNum() {
        return this.rectifierModuleNum;
    }
    
    public Date getRenewalOverTime() {
        return this.renewalOverTime;
    }
    
    public String getSupervisoryModuleModel() {
        return this.supervisoryModuleModel;
    }
    
    public String getSuppliedModuleNum() {
        return this.SuppliedModuleNum;
    }
    
    public Integer getSwitchingId() {
        return this.switchingId;
    }
    
    public void setActualUsePower(final String actualUsePower) {
        this.actualUsePower = actualUsePower;
    }
    
    public void setAlreadUsedModuleNum(final String alreadUsedModuleNum) {
        this.alreadUsedModuleNum = alreadUsedModuleNum;
    }
    
    public void setAuthorizationManagementUnit(final String authorizationManagementUnit) {
        this.authorizationManagementUnit = authorizationManagementUnit;
    }
    
    public void setCharteredMan(final String charteredMan) {
        this.charteredMan = charteredMan;
    }
    
    public void setCutMark(final Integer cutMark) {
        this.cutMark = cutMark;
    }
    
    public void setDeleteFlag(final String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
    public void setDesignPurposes(final String designPurposes) {
        this.designPurposes = designPurposes;
    }
    
    public void setDeviceAlias(final String deviceAlias) {
        this.deviceAlias = deviceAlias;
    }
    
    public void setDeviceMaker(final Integer deviceMaker) {
        this.deviceMaker = deviceMaker;
    }
    
    public void setDeviceName(final String deviceName) {
        this.deviceName = deviceName;
    }
    
    public void setFixedAssetsCode(final String fixedAssetsCode) {
        this.fixedAssetsCode = fixedAssetsCode;
    }
    
    public void setGID(final String gid) {
        this.GID = gid;
    }
    
    public void setGenerator(final String generator) {
        this.generator = generator;
    }
    
    public void setImageNames(final String imageNames) {
        this.imageNames = imageNames;
    }
    
    public void setInNetTime(final Date inNetTime) {
        this.inNetTime = inNetTime;
    }
    
    public void setIsAuthorizationManagement(final Integer isAuthorizationManagement) {
        this.isAuthorizationManagement = isAuthorizationManagement;
    }
    
    public void setIsEnvironmentMonitoring(final Integer isEnvironmentMonitoring) {
        this.isEnvironmentMonitoring = isEnvironmentMonitoring;
    }
    
    public void setLat(final String lat) {
        this.lat = lat;
    }
    
    public void setLifecycle(final Integer lifecycle) {
        this.lifecycle = lifecycle;
    }
    
    public void setLifecycleTime(final Date lifecycleTime) {
        this.lifecycleTime = lifecycleTime;
    }
    
    public void setLittleDeviceMaker(final String littleDeviceMaker) {
        this.littleDeviceMaker = littleDeviceMaker;
    }
    
    public void setLon(final String lon) {
        this.lon = lon;
    }
    
    public void setMaintainState(final Integer maintainState) {
        this.maintainState = maintainState;
    }
    
    public void setMaintenanceMode(final Integer maintenanceMode) {
        this.maintenanceMode = maintenanceMode;
    }
    
    public void setMaintenanceType(final Integer maintenanceType) {
        this.maintenanceType = maintenanceType;
    }
    
    public void setMaintenanceUnit(final String maintenanceUnit) {
        this.maintenanceUnit = maintenanceUnit;
    }
    
    public void setModel(final String model) {
        this.model = model;
    }
    
    public void setMonitoringSystemName(final String monitoringSystemName) {
        this.monitoringSystemName = monitoringSystemName;
    }
    
    public void setNote(final String note) {
        this.note = note;
    }
    
    public void setOldResourceSystemEquName(final String oldResourceSystemEquName) {
        this.oldResourceSystemEquName = oldResourceSystemEquName;
    }
    
    public void setOldResourceSystemEquNum(final String oldResourceSystemEquNum) {
        this.oldResourceSystemEquNum = oldResourceSystemEquNum;
    }
    
    public void setOtherMaintainUnit(final String otherMaintainUnit) {
        this.otherMaintainUnit = otherMaintainUnit;
    }
    
    public void setPosX(final String posX) {
        this.posX = posX;
    }
    
    public void setPosY(final String posY) {
        this.posY = posY;
    }
    
    public void setPowerSystem(final String powerSystem) {
        this.powerSystem = powerSystem;
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
    
    public void setRatedElectricity(final String ratedElectricity) {
        this.ratedElectricity = ratedElectricity;
    }
    
    public void setRatedVoltage(final String ratedVoltage) {
        this.ratedVoltage = ratedVoltage;
    }
    
    public void setRectifierModuleModel(final String rectifierModuleModel) {
        this.rectifierModuleModel = rectifierModuleModel;
    }
    
    public void setRectifierModuleNum(final String rectifierModuleNum) {
        this.rectifierModuleNum = rectifierModuleNum;
    }
    
    public void setRenewalOverTime(final Date renewalOverTime) {
        this.renewalOverTime = renewalOverTime;
    }
    
    public void setSupervisoryModuleModel(final String supervisoryModuleModel) {
        this.supervisoryModuleModel = supervisoryModuleModel;
    }
    
    public void setSuppliedModuleNum(final String suppliedModuleNum) {
        this.SuppliedModuleNum = suppliedModuleNum;
    }
    
    public void setSwitchingId(final Integer switchingId) {
        this.switchingId = switchingId;
    }
}
