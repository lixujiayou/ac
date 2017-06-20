package com.inspur.resources.view.delivery.transroute.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ErrorInfoZzBean implements Serializable{

	/**
	 * 综资数据
	 */
	private static final long serialVersionUID = -7128120409437455214L;
	
	private Integer ID;
	private Integer routeID;// 所属路由ID
	
	private String city;// 地市
	private String county;// 地市

	//综资数据问题
	private String errorResType;//数据不准确资源类别  传输网元 光缆段
	
	private String errorResName;//
	private Integer errorResId;
	private String errorDescribe;
	
	private String errorResRank;//数据不准确归类
	

	
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
	public String getErrorResType() {
		return errorResType;
	}
	public void setErrorResType(String errorResType) {
		this.errorResType = errorResType;
	}
	public String getErrorNetName() {
		return errorResName;
	}
	public void setErrorNetName(String errorNetName) {
		this.errorResName = errorNetName;
	}
	
	public String getErrorResRank() {
		return errorResRank;
	}
	public void setErrorResRank(String errorResRank) {
		this.errorResRank = errorResRank;
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
	public Integer getResId() {
		return errorResId;
	}
	public void setResId(Integer resId) {
		this.errorResId = resId;
	}


	public String getErrorDescribe() {
		return errorDescribe;
	}

	public void setErrorDescribe(String errorDescribe) {
		this.errorDescribe = errorDescribe;
	}
}
