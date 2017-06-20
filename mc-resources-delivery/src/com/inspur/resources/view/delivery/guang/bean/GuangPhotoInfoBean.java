package com.inspur.resources.view.delivery.guang.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lixu- 光交接箱照片信息实体类
 */
public class GuangPhotoInfoBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3002389411914397132L;

	private Integer ID;
	private Integer relatedID;//关联光交ID

	private String photoName;// 照片名
	private Date createTime;
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getRelatedID() {
		return relatedID;
	}
	public void setRelatedID(Integer relatedID) {
		this.relatedID = relatedID;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
