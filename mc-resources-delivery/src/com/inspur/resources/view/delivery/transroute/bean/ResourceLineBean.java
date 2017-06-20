package com.inspur.resources.view.delivery.transroute.bean;

import java.io.Serializable;

public class ResourceLineBean implements Serializable {
	private ResourceInfoBean start;
	private ResourceInfoBean end;
	private Integer relatedBranch;

	public ResourceInfoBean getStart() {
		return start;
	}

	public ResourceInfoBean getEnd() {
		return end;
	}

	public void setStart(ResourceInfoBean start) {
		this.start = start;
	}

	public void setEnd(ResourceInfoBean end) {
		this.end = end;
	}

	public Integer getRelatedBranch() {
		return relatedBranch;
	}

	public void setRelatedBranch(Integer relatedBranch) {
		this.relatedBranch = relatedBranch;
	}
}
