package com.inspur.resources.view.delivery.transroute.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Dell-
 *隐患实体类
 */
public class GdErrorInfoBean implements Serializable
{
	private static final long serialVersionUID = 6205639516913559127L;


	private Double latitudeStart;// 隐患纬度
	private Double longitudeStart;// 隐患经度


	private Double latitudeEnd;// 隐患纬度
	private Double longitudeEnd;// 隐患经度

	public Double getLatitudeStart() {
		return latitudeStart;
	}
	public void setLatitudeStart(Double latitude) {
		this.latitudeStart = latitude;
	}
	public Double getLongitudeStart() {
		return longitudeStart;
	}
	public void setLongitudeStart(Double longitude) {
		this.longitudeStart = longitude;
	}

	public Double getLatitudeEnd() {
		return latitudeEnd;
	}
	public void setLatitudeEnd(Double latitude) {
		this.latitudeEnd = latitude;
	}
	public Double getLongitudeEnd() {
		return longitudeEnd;
	}
	public void setLongitudeEnd(Double longitude) {
		this.longitudeEnd = longitude;
	}
	/**
	 *
	 */
	/*private static final long serialVersionUID = 9017840021842179179L;

	private Integer ID;
	private Integer routeID;// 所属路由ID

	private String area;// 区域
	private Double latitude;// 隐患纬度
	private Double longitude;// 隐患经度
	private Integer resourceID;// 隐患资源唯一标识
	private String resourceType;// 隐患资源类型
	private String errorObjectName;// 隐患资源的名称
	private String urgencyLevel;// 紧急程度
	private String errorDescribe;// 隐患描述
	private String situation;// 现场处理情况
	private String leftProblem;// 遗留问题
	private String suggest;// 处理建议
	private ArrayList<PhotoInfoBean> files;// 隐患照片
	public Integer getID() {
		return ID;
	}
	public Integer getRouteID() {
		return routeID;
	}
	public String getArea() {
		return area;
	}
	public Double getLatitude() {
		return latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public Integer getResourceID() {
		return resourceID;
	}
	public String getResourceType() {
		return resourceType;
	}
	public String getErrorObjectName() {
		return errorObjectName;
	}
	public String getUrgencyLevel() {
		return urgencyLevel;
	}
	public String getErrorDescribe() {
		return errorDescribe;
	}
	public String getSituation() {
		return situation;
	}
	public String getLeftProblem() {
		return leftProblem;
	}
	public String getSuggest() {
		return suggest;
	}
	public ArrayList<PhotoInfoBean> getErrorsPhotos() {
		return files;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public void setResourceID(Integer resourceID) {
		this.resourceID = resourceID;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public void setErrorObjectName(String errorObjectName) {
		this.errorObjectName = errorObjectName;
	}
	public void setUrgencyLevel(String urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}
	public void setErrorDescribe(String errorDescribe) {
		this.errorDescribe = errorDescribe;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public void setLeftProblem(String leftProblem) {
		this.leftProblem = leftProblem;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	public void setErrorsPhotos(ArrayList<PhotoInfoBean> errorsPhotos) {
		this.files = errorsPhotos;
	}*/

}
