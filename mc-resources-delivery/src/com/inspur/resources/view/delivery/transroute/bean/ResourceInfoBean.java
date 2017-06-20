package com.inspur.resources.view.delivery.transroute.bean;

import java.io.Serializable;

/**
 *点状资源实体，包括机房、电杆......这样的所有的点状资源
 */
public class ResourceInfoBean implements Serializable{
	private Integer resourceID;// 资源ID
	private String resourceType;// 资源类型
	private String resourceName;// 资源名称
	private Double latitude;// 资源纬度
	private Double longitude;// 资源经度
	private String isPass;




	@Override
	public String toString() {
		return "ResourceInfoBean [resourceID=" + resourceID + ", resourceType="
				+ resourceType + ", resourceName=" + resourceName
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", isPass=" + isPass + "]";
	}
	public Integer getResourceID() {
		return resourceID;
	}
	public String getResourceType() {
		return resourceType;
	}
	public String getResourceName() {
		return resourceName;
	}
	public Double getLatitude() {
		return latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setResourceID(Integer resourceID) {
		this.resourceID = resourceID;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ResourceInfoBean)){
			return false;
		}
		return resourceID.compareTo(((ResourceInfoBean)o).getResourceID())==0&&resourceType.equalsIgnoreCase(((ResourceInfoBean)o).getResourceType());
	}


}
