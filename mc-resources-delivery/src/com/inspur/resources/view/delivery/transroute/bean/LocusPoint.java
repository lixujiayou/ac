package com.inspur.resources.view.delivery.transroute.bean;

import java.io.Serializable;

/**
 */
public class LocusPoint implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2535060223773355585L;
	
	private Integer ID;
	private Integer routeID;
	
	private Double latitude;
	private Double longitude;
	public Integer getID() {
		return ID;
	}
	public Integer getRouteID() {
		return routeID;
	}
	public Double getLatitude() {
		return latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
