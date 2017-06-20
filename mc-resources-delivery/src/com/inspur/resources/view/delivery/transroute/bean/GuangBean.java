package com.inspur.resources.view.delivery.transroute.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.inspur.resources.view.delivery.guang.bean.GuangPhotoInfoBean;

public class GuangBean implements Serializable {
	
	private static final long serialVersionUID = -7483152179319055727L;

	private int ID;
	private String gjId;
	private String city;
	private String county;
	private String gjName;
	private String isZg;
	private String isJrkh;
	private String jrkhName;
	private String isYh;
	private String yhType;
	private String yhMs;
	private String isZgWc;
	private String zgDate;
	private String jgStatus;
	private String longitude;
	private String latitude;
	private String czr;
	private String czDate;
	private ArrayList<PhotoInfoBean> files;//
	
	public GuangBean(){}
	
	
	public String getIntId() {
		return gjId;
	}


	public void setIntId(String intId) {
	//	this.intId = Integer.parseInt(intId);
		this.gjId = intId;
	}

	
	
	

	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
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
	public String getGjName() {
		return gjName;
	}
	public void setGjName(String gjName) {
		this.gjName = gjName;
	}
	public String getIsZg() {
		return isZg;
	}
	public void setIsZg(String isZg) {
		this.isZg = isZg;
	}
	public String getIsJrkh() {
		return isJrkh;
	}
	public void setIsJrkh(String isJrkh) {
		this.isJrkh = isJrkh;
	}
	public String getJrkhName() {
		return jrkhName;
	}
	public void setJrkhName(String jrkhName) {
		this.jrkhName = jrkhName;
	}
	public String getIsYh() {
		return isYh;
	}
	public void setIsYh(String isYh) {
		this.isYh = isYh;
	}
	public String getYhType() {
		return yhType;
	}
	public void setYhType(String yhType) {
		this.yhType = yhType;
	}
	public String getYhMs() {
		return yhMs;
	}
	public void setYhMs(String yhMs) {
		this.yhMs = yhMs;
	}
	public String getIsZgWc() {
		return isZgWc;
	}
	public void setIsZgWc(String isZgWc) {
		this.isZgWc = isZgWc;
	}
	public String getZgDate() {
		return zgDate;
	}
	public void setZgDate(String zgDate) {
		this.zgDate = zgDate;
	}
	public String getJgStatus() {
		return jgStatus;
	}
	public void setJgStatus(String jgStatus) {
		this.jgStatus = jgStatus;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude =longitude;
	}


	public int getId() {
		return ID;
	}


	public void setId(int id) {
		this.ID = id;
	}


	public String getCzr() {
		return czr;
	}


	public void setCzr(String czr) {
		this.czr = czr;
	}


	public String getCzDate() {
		return czDate;
	}


	public void setCzDate(String czDate) {
		this.czDate = czDate;
	}


	public ArrayList<PhotoInfoBean> getPhoto() {
		return files;
	}


	public void setPhoto(ArrayList<PhotoInfoBean> files) {
		this.files = files;
	}
	
	
}
