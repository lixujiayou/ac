
package com.inspur.resources.bean;

import java.io.Serializable;
/**
 * 閻╂潙鐓佹穱鈩冧紖
 * @author Administrator
 *
 */
public class BuriedInfoObj implements Serializable{
	private Integer buriedId;
	private String buriedName;//直埋名称
	private String buriedArea;//维护区域
	private Integer buriedModel;//维护方式
	private String buriedDept;//维护单位
	private String buriedLength;//直埋长度
	private String dataQualitier;//质量责任人
	private String maintainer;//一线维护人
	private String deleteflag;//删除标识
	private String createTime;//创建时间
	private String creater;//创建人
	private String remark;//备注
	private String lastUpTime;//最近修改时间
	private String lastUpMan;//最近修改人
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
	public Integer getBuriedId() {
		return buriedId;
	}
	public void setBuriedId(Integer buriedId) {
		this.buriedId = buriedId;
	}
	public String getBuriedName() {
		return buriedName;
	}
	public void setBuriedName(String buriedName) {
		this.buriedName = buriedName;
	}
	public String getBuriedArea() {
		return buriedArea;
	}
	public void setBuriedArea(String buriedArea) {
		this.buriedArea = buriedArea;
	}
	public Integer getBuriedModel() {
		return buriedModel;
	}
	public void setBuriedModel(Integer buriedModel) {
		this.buriedModel = buriedModel;
	}
	public String getBuriedDept() {
		return buriedDept;
	}
	public void setBuriedDept(String buriedDept) {
		this.buriedDept = buriedDept;
	}
	public String getBuriedLength() {
		return buriedLength;
	}
	public void setBuriedLength(String buriedLength) {
		this.buriedLength = buriedLength;
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
