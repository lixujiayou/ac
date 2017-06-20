package com.inspur.resources.view.delivery.transroute.bean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ErrorInfoBean implements Serializable {

	/**
	 * 璧勬簮闅愭偅
	 */
	private static final long serialVersionUID = -6918255507173968615L;

	private Integer ID;
	private Integer routeID;//// 所属路由ID


	private String city;// 地市
	private String county;// 区县
	private Double latitude;// 隐患纬度
	private Double longitude;// 隐患经度
	private Integer resourceID;// 隐患资源唯一标识
	private String resourceType;// 隐患资源类型
	private String errorObjectName;// 隐患资源的名称

	//资源隐患
	private String errorLocateDes;//隐患位置描述
	private String errorBig;//隐患大类
	private String errorSmall;//隐患小类
	private String errorDescribe;// 隐患描述

	private String isZg;//是否整改
	private String zgRq;//整改日期
	private ArrayList<PhotoInfoBean> files;// 隐患照片
	private String userId;//操作人
	private Date createTime;//操作时间

	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getRouteID() {
		return routeID;
	}
	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Integer getResourceID() {
		return resourceID;
	}
	public void setResourceID(Integer resourceID) {
		this.resourceID = resourceID;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getErrorObjectName() {
		return errorObjectName;
	}
	public void setErrorObjectName(String errorObjectName) {
		this.errorObjectName = errorObjectName;
	}
	public String getErrorLocateDes() {
		return errorLocateDes;
	}
	public void setErrorLocateDes(String errorLocateDes) {
		this.errorLocateDes = errorLocateDes;
	}
	public String getErrorBig() {
		return errorBig;
	}
	public void setErrorBig(String errorBig) {
		this.errorBig = errorBig;
	}
	public String getErrorSmall() {
		return errorSmall;
	}
	public void setErrorSmall(String errorSmall) {
		this.errorSmall = errorSmall;
	}
	public String getErrorDescribe() {
		return errorDescribe;
	}
	public void setErrorDescribe(String errorDescribe) {
		this.errorDescribe = errorDescribe;
	}
	public String getIsZg() {
		return isZg;
	}
	public void setIsZg(String isZg) {
		this.isZg = isZg;
	}
	public String getZgRq() {
		return zgRq;
	}
	public void setZgRq(String zgRq) {
		this.zgRq = zgRq;
	}
	public ArrayList<PhotoInfoBean> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<PhotoInfoBean> files) {
		this.files = files;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
