package com.inspur.resources.view.delivery.transroute.bean;
import java.io.Serializable;
import java.util.Date;

import android.util.Log;

public class PhotoInfoBean implements Serializable {

	/**
	 * 线路交割 和 光交交割 图片上传的bean
	 */
	private static final long serialVersionUID = -3667284296893507282L;

	private Integer ID;
	private Integer routeID;// 线路交割所属路由ID
	private Integer relatedID;
	// 照片类型（起点照片、终点照片、过程照片、隐患照片、光交交割照片等）
	private String photoType;
	private Double latitude;// 照片形成时所处的纬度
	private Double longitude;// 照片形成时所处的经度
	private String photoName;// 照片名
	private Date createTime;
	private String resourceType;//资源类型
	private String userId;
	private String photoString;


	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getRelatedID() {
		return relatedID;
	}
	public void setRelatedID(Integer relatedID) {
		this.relatedID = relatedID;
	}
	public String getPhotoType() {
		return photoType;
	}
	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}
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
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getPhotoString() {
		return photoString;
	}
	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}




}
