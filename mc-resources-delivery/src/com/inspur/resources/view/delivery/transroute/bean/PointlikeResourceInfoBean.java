package com.inspur.resources.view.delivery.transroute.bean;

import java.util.ArrayList;

/**
 * @author Dell-
 *路由中的点状资源实体
 */
public class PointlikeResourceInfoBean extends ResourceInfoBean
{

	/**
	 *
	 */
	private static final long serialVersionUID = 4519053150277111549L;

	private Integer ID;
	private Integer routeID;// 所属路由ID

	private ArrayList<PhotoInfoBean> files = new ArrayList<PhotoInfoBean>();// 资源点照片






	@Override
	public String toString() {
		return "PointlikeResourceInfoBean [ID=" + ID + ", routeID=" + routeID
				+ ", toString()=" + super.toString() + "]";
	}

	public PointlikeResourceInfoBean()
	{
		for(int i = 0;i < 25;i ++)
		{
			PhotoInfoBean pb = new PhotoInfoBean();
			pb.setPhotoName("5.png");
			files.add(pb);
		}

	}

	public Integer getID()
	{
		return ID;
	}
	public void setID(Integer iD)
	{
		ID = iD;
	}
	public Integer getRouteID()
	{
		return routeID;
	}
	public void setRouteID(Integer routeID)
	{
		this.routeID = routeID;
	}

	public ArrayList<PhotoInfoBean> getPhotos()
	{
		return files;
	}
	public void setPhotos(ArrayList<PhotoInfoBean> photos)
	{
		this.files = photos;
	}

	public static PointlikeResourceInfoBean generateStartOrEndPoint(ResourceInfoBean resource,int routeId){
		return generatePointlikeResource(resource,routeId);
	}

	public static PointlikeResourceInfoBean generatePointlikeResource(ResourceInfoBean resource,int routeId){
		PointlikeResourceInfoBean pointlikeResourceInfoBean = new PointlikeResourceInfoBean();
		pointlikeResourceInfoBean.setLatitude(resource.getLatitude());
		pointlikeResourceInfoBean.setLongitude(resource.getLongitude());
		pointlikeResourceInfoBean.setResourceID(resource.getResourceID());
		pointlikeResourceInfoBean.setResourceName(resource.getResourceName());
		pointlikeResourceInfoBean.setResourceType(resource.getResourceType());
		pointlikeResourceInfoBean.setRouteID(routeId);
		return pointlikeResourceInfoBean;
	}

}
