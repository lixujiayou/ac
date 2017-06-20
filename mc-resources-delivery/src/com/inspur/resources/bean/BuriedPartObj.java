package com.inspur.resources.bean;

import java.io.Serializable;

/**
 * 直埋段
 * @author Administrator
 *
 */
public class BuriedPartObj implements Serializable{

	private Integer id;//直埋段id
	private String buriedId;//所属直埋id
	private String buriedPartName;//直埋段名称
	private String buriedPartArea;//维护区域
	private String buriedPartLength;//直埋段长度
	private String buriedPartStart;//开始设施
	private String buriedPartEnd;//终止设施
	private String buriedPartStartId;
	private String buriedPartEndId;
	private String buriedPartOptical;//承载光缆段
	private Integer propertyRight;//产权性质
	private Integer propertyDept;//产权单位
	private String dataQualitier;//质量责任人
	private String maintainer;//一线维护人
	private String deleteflag;//删除标识
	private String createTime;//创建时间
	private String creater;//创建人
	private String remark;//备注
	private String lastUpTime;//最近修改时间
	private String lastUpMan;//最近修改人
	
	public BuriedPartObj()
	{
		
	}
	
	public BuriedPartObj(BuriedPartObj mBuriedPartObj)
	{
		this.id = mBuriedPartObj.getId();
		this.buriedId = mBuriedPartObj.getBuriedId();
		this.buriedPartName = mBuriedPartObj.getBuriedPartName();
		this.buriedPartArea = mBuriedPartObj.getBuriedPartArea();
		this.buriedPartLength = mBuriedPartObj.getBuriedPartLength();
		this.buriedPartStart = mBuriedPartObj.getBuriedPartStart();
		this.buriedPartEnd = mBuriedPartObj.getBuriedPartEnd();
		this.buriedPartOptical = mBuriedPartObj.getBuriedPartOptical();
		this.propertyRight = mBuriedPartObj.getPropertyRight();
		this.propertyDept = mBuriedPartObj.getPropertyDept();
		this.dataQualitier = mBuriedPartObj.getDataQualitier();
		this.maintainer = mBuriedPartObj.getMaintainer();
		this.deleteflag = mBuriedPartObj.getDeleteflag();
		this.createTime = mBuriedPartObj.getCreateTime();
		this.creater = mBuriedPartObj.getCreater();
		this.remark = mBuriedPartObj.getRemark();
		this.lastUpTime = mBuriedPartObj.getLastUpTime();
		this.lastUpMan = mBuriedPartObj.getLastUpMan();
	}
	
	
	public String getLastUpTime() {
		return lastUpTime;
	}
	public void setLastUpTime(String lastUpTime) {
		this.lastUpTime = lastUpTime;
	}
	public String getLastUpMan() {
		return lastUpMan;
	}
	public void setLastUpMan(String lastUpMan) {
		this.lastUpMan = lastUpMan;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBuriedId() {
		return buriedId;
	}
	public void setBuriedId(String buriedId) {
		this.buriedId = buriedId;
	}
	public String getBuriedPartName() {
		return buriedPartName;
	}
	public void setBuriedPartName(String buriedPartName) {
		this.buriedPartName = buriedPartName;
	}
	public String getBuriedPartStartId() {
		return buriedPartStartId;
	}

	public String getBuriedPartEndId() {
		return buriedPartEndId;
	}

	public void setBuriedPartStartId(String buriedPartStartId) {
		this.buriedPartStartId = buriedPartStartId;
	}

	public void setBuriedPartEndId(String buriedPartEndId) {
		this.buriedPartEndId = buriedPartEndId;
	}

	public String getBuriedPartArea() {
		return buriedPartArea;
	}
	public void setBuriedPartArea(String buriedPartArea) {
		this.buriedPartArea = buriedPartArea;
	}
	public String getBuriedPartLength() {
		return buriedPartLength;
	}
	public void setBuriedPartLength(String buriedPartLength) {
		this.buriedPartLength = buriedPartLength;
	}
	public String getBuriedPartStart() {
		return buriedPartStart;
	}
	public void setBuriedPartStart(String buriedPartStart) {
		this.buriedPartStart = buriedPartStart;
	}
	public String getBuriedPartEnd() {
		return buriedPartEnd;
	}
	public void setBuriedPartEnd(String buriedPartEnd) {
		this.buriedPartEnd = buriedPartEnd;
	}
	public String getBuriedPartOptical() {
		return buriedPartOptical;
	}
	public void setBuriedPartOptical(String buriedPartOptical) {
		this.buriedPartOptical = buriedPartOptical;
	}
	public Integer getPropertyRight() {
		return propertyRight;
	}
	public void setPropertyRight(Integer propertyRight) {
		this.propertyRight = propertyRight;
	}
	public Integer getPropertyDept() {
		return propertyDept;
	}
	public void setPropertyDept(Integer propertyDept) {
		this.propertyDept = propertyDept;
	}
	public String getDataQualitier() {
		return dataQualitier;
	}
	public void setDataQualitier(String dataQualitier) {
		this.dataQualitier = dataQualitier;
	}
	public String getMaintainer() {
		return maintainer;
	}
	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}
	public String getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
