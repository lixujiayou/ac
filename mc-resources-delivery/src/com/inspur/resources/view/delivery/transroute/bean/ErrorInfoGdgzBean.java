package com.inspur.resources.view.delivery.transroute.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ErrorInfoGdgzBean implements Serializable{

	/**
	 */
	private static final long serialVersionUID = 3192707205443450593L;
	
	private Integer ID;
	private Integer routeID;// 所属路由ID
	
	private String city;// 地市
	private String county;// 地市

	private String gdgzSeg;//管道故障段落 起点井-终点井（系统自动生成）
	private Integer startResId;//起点资源ID
	private String startResName;//起点资源
	private Double startLatitude;// 起点纬度
	private Double startLongitude;// 起点经度
	private Integer endResId;//终点资源
	private String endResName;//终点资源
	private Double endLatitude;// 终点纬度
	private Double endLongitude;// 终点经度
	
	private String segSbLength;//上报故障长度（米）
	private String segSysLength;//系统自动计算故障长度（米）
	
	private ArrayList<PhotoInfoBean> files;// 隐患照片	
	private String isZg;//是否整改
	private String zgRq;//整改日期
	private String userId;//操作人
	private Date createTime;//操作时间
	private String errorDescribe;
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
	public String getGdgzSeg() {
		return gdgzSeg;
	}
	public void setGdgzSeg(String gdgzSeg) {
		this.gdgzSeg = gdgzSeg;
	}
	
	public Double getStartLatitude() {
		return startLatitude;
	}
	public void setStartLatitude(Double startLatitude) {
		this.startLatitude = startLatitude;
	}
	public Double getStartLongitude() {
		return startLongitude;
	}
	public void setStartLongitude(Double startLongitude) {
		this.startLongitude = startLongitude;
	}
	
	public Integer getStartResId() {
		return startResId;
	}
	public void setStartResId(Integer startResId) {
		this.startResId = startResId;
	}
	public String getStartResName() {
		return startResName;
	}
	public void setStartResName(String startResName) {
		this.startResName = startResName;
	}
	public Integer getEndResId() {
		return endResId;
	}
	public void setEndResId(Integer endResId) {
		this.endResId = endResId;
	}
	public String getEndResName() {
		return endResName;
	}
	public void setEndResName(String endResName) {
		this.endResName = endResName;
	}
	public Double getEndLatitude() {
		return endLatitude;
	}
	public void setEndLatitude(Double endLatitude) {
		this.endLatitude = endLatitude;
	}
	public Double getEndLongitude() {
		return endLongitude;
	}
	public void setEndLongitude(Double endLongitude) {
		this.endLongitude = endLongitude;
	}
	public String getSegSbLength() {
		return segSbLength;
	}
	public void setSegSbLength(String segSbLength) {
		this.segSbLength = segSbLength;
	}
	public String getSegSysLength() {
		return segSysLength;
	}
	public void setSegSysLength(String segSysLength) {
		this.segSysLength = segSysLength;
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

	public String getErrorDescribe() {
		return errorDescribe;
	}

	public void setErrorDescribe(String errorDescribe) {
		this.errorDescribe = errorDescribe;
	}
}
