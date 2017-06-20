package com.inspur.resources.view.delivery.transroute;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.model.LatLng;
import com.inspur.resources.view.delivery.transroute.bean.ResourceInfoBean;

import android.graphics.Point;
import cn.trinea.android.common.util.RandomUtils;

/**
 * @author Dell-
 *假数据制作工厂
 */
public class FalseDataFactory
{

	public static List<ResourceInfoBean> createResourceInfoBeanList(MapView mapview, LatLng center)
	{
		Projection proj = mapview.getMap().getProjection();
		float pxLength = proj.metersToEquatorPixels(100f);

		Point screenCenter = proj.toScreenLocation(center);


		List<ResourceInfoBean> mResourceInfoBean = new ArrayList<ResourceInfoBean>();
		ResourceInfoBean bean;
		for(int i = 0;i < 5;i ++)
		{
			bean = new ResourceInfoBean();
			bean.setResourceType("");
			bean.setResourceName("资源点 " + i);
			bean.setResourceID(i);

			int randomScreenX = RandomUtils.getRandom((int)(screenCenter.x - (pxLength/2)), (int)(screenCenter.x + (pxLength/2)));
			int randomScreenY = RandomUtils.getRandom((int)(screenCenter.y - (pxLength/2)), (int)(screenCenter.y + (pxLength/2)));
			LatLng randomLatLng = proj.fromScreenLocation(new Point(randomScreenX, randomScreenY));
			bean.setLatitude(randomLatLng.latitude);
			bean.setLongitude(randomLatLng.longitude);
			mResourceInfoBean.add(bean);
		}
//		private Integer resourceID;// 资源ID
//		private String resourceType;// 资源类型
//		private String resourceName;// 资源名称
//		private Double latitude;// 资源纬度
//		private Double longitude;// 资源经度
//		private ArrayList<PhotoInfoBean> photos;// 资源点照片
		return mResourceInfoBean;
	}

	public static List<ResourceInfoBean> createResourceInfoBeanList(String type,LatLng center)
	{

		List<ResourceInfoBean> mResourceInfoBean = new ArrayList<ResourceInfoBean>();
		ResourceInfoBean bean;
		for(int i = 0;i < 5;i ++)
		{
			bean = new ResourceInfoBean();
			bean.setResourceType(type);
			bean.setResourceName("资源点 " + i);
			bean.setResourceID(i);

			bean.setLatitude(center.latitude);
			bean.setLongitude(center.longitude);
			mResourceInfoBean.add(bean);
		}
		return mResourceInfoBean;
	}
}
