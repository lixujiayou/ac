package com.inspur.resources.view.delivery.guang;

import java.util.ArrayList;

import com.inspur.resources.view.delivery.transroute.bean.GuangBean;
import com.inspur.resources.view.delivery.transroute.bean.PhotoInfoBean;

/**
 * @author Dell-
 *路由中的点状资源实体
 */
public class GuanglikeResourceInfoBean extends GuangBean
{

	/**
	 *
	 */
	private static final long serialVersionUID = 4519053150277111549L;

	private Integer ID;
	private Integer routeID;// 所属路由ID

	private ArrayList<PhotoInfoBean> files = new ArrayList<PhotoInfoBean>();// 资源点照片

	public GuanglikeResourceInfoBean()
	{
		for(int i = 0;i < 25;i ++)
		{
			PhotoInfoBean pb = new PhotoInfoBean();
			pb.setPhotoName("5.png");
			files.add(pb);
		}

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

	public static GuanglikeResourceInfoBean generateStartOrEndPoint(GuangBean resource,int routeId){
		return generatePointlikeResource(resource,routeId);
	}

	public static GuanglikeResourceInfoBean generatePointlikeResource(GuangBean resource,int routeId){
		GuanglikeResourceInfoBean pointlikeResourceInfoBean = new GuanglikeResourceInfoBean();
		pointlikeResourceInfoBean.setLatitude(resource.getLatitude()+"");
		pointlikeResourceInfoBean.setLongitude(resource.getLongitude()+"");
		pointlikeResourceInfoBean.setIntId(resource.getIntId()+"");
		pointlikeResourceInfoBean.setGjName(resource.getGjName());
		pointlikeResourceInfoBean.setYhType(resource.getYhType());
		pointlikeResourceInfoBean.setRouteID(routeId);
		return pointlikeResourceInfoBean;
	}


}
