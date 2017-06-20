package com.inspur.resources.bean;

import java.io.Serializable;

/**
 * 标石管理
 *
 */
public class StoneInfoBean implements Serializable{
	private Integer stoneId;//标石id
	private String buriedId;//所属直埋id
	private String stoneName;//标石名称
	private String stoneSubName;//标石子名称
	private Integer stonePosition;//标石方位
	private String stoneNum;//标石序号
	private String stoneArea;//所属区域
	private String longitude;//经度
	private String latitude;//纬度
	private Integer propertyNature;//产权性质
	private Integer propertyComp;//产权单位
	private String dataQualitier;//数据质量责任人
	private String maintainer;//一线维护人
	private String deleteflag;//删除标识
	private Integer chengzaidian_type;// 承载点类型
	private Integer previousStoneID;// 上一节点ID
	private String previousStoneName;// 上一节点名称

	public StoneInfoBean(){}

	public StoneInfoBean(StoneInfoBean bean)
	{
		this.stoneId = bean.getStoneId();
		this.buriedId = bean.getBuriedId();
		this.stoneName = bean.getStoneName();
		this.stoneSubName = bean.getStoneSubName();
		this.stonePosition = bean.getStonePosition();
		this.stoneNum = bean.getStoneNum();
		this.stoneArea = bean.getStoneArea();
		this.longitude = bean.getLongitude();
		this.latitude = bean.getLatitude();
		this.propertyNature = bean.getPropertyNature();
		this.propertyComp = bean.getPropertyComp();
		this.dataQualitier = bean.getDataQualitier();
		this.maintainer = bean.getMaintainer();
		this.deleteflag = bean.getDeleteflag();
		this.chengzaidian_type = bean.chengzaidian_type;
		this.previousStoneID = bean.previousStoneID;
		this.previousStoneName = bean.previousStoneName;
	}



	public Integer getPreviousStoneID()
	{
		return previousStoneID;
	}

	public void setPreviousStoneID(Integer previousStoneID)
	{
		this.previousStoneID = previousStoneID;
	}

	public String getPreviousStoneName()
	{
		return previousStoneName;
	}

	public void setPreviousStoneName(String previousStoneName)
	{
		this.previousStoneName = previousStoneName;
	}

	public Integer getChengzaidian_type()
	{
		return chengzaidian_type;
	}

	public void setChengzaidian_type(Integer chengzaidian_type)
	{
		this.chengzaidian_type = chengzaidian_type;
	}

	public Integer getStoneId() {
		return stoneId;
	}
	public void setStoneId(Integer stoneId) {
		this.stoneId = stoneId;
	}
	public String getBuriedId() {
		return buriedId;
	}
	public void setBuriedId(String buriedId) {
		this.buriedId = buriedId;
	}
	public String getStoneName() {
		return stoneName;
	}
	public void setStoneName(String stoneName) {
		this.stoneName = stoneName;
	}
	public String getStoneSubName() {
		return stoneSubName;
	}
	public void setStoneSubName(String stoneSubName) {
		this.stoneSubName = stoneSubName;
	}
	public Integer getStonePosition() {
		return stonePosition;
	}
	public void setStonePosition(Integer stonePosition) {
		this.stonePosition = stonePosition;
	}
	public String getStoneNum() {
		return stoneNum;
	}
	public void setStoneNum(String stoneNum) {
		this.stoneNum = stoneNum;
	}
	public String getStoneArea() {
		return stoneArea;
	}
	public void setStoneArea(String stoneArea) {
		this.stoneArea = stoneArea;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Integer getPropertyNature() {
		return propertyNature;
	}
	public void setPropertyNature(Integer propertyNature) {
		this.propertyNature = propertyNature;
	}
	public Integer getPropertyComp() {
		return propertyComp;
	}
	public void setPropertyComp(Integer propertyComp) {
		this.propertyComp = propertyComp;
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

}