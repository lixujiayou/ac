package com.inspur.resources.view.delivery.transroute.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Dell-
 *一条完整的路由信息实体类
 */
public class RouteInfoBean implements Serializable
{

	private Integer ID;
	private Integer routeID;// 一条交割路由的唯一标识

	private PointlikeResourceInfoBean startPosition;// 起始点
	private PointlikeResourceInfoBean endPosition;// 终止点
	private List<LocusPoint> locusPoints;// 轨迹点
	private List<ErrorInfoBean> errors;// 隐患列表

	private String matchScores;//点资源匹配率
	private Integer routeState;//路由状态
	private Integer deliveryState;//交割状态
	private Date deliveryDate;//交割时间

	private String dealPerson;//交割人
	private String name;//路由段名称
	private String userId;//用户账号

	private Integer start;
	private Integer limit;

	private String flag;//防止重复提交标识

	private ArrayList<PointlikeResourceInfoBean> locusResourcePosition = new ArrayList<PointlikeResourceInfoBean>();// 中途资源点



	@Override
	public String toString() {
		return "RouteInfoBean [ID=" + ID + ", routeID=" + routeID
				+ ", matchScores=" + matchScores + ", routeState=" + routeState
				+ ", deliveryState=" + deliveryState + ", deliveryDate="
				+ deliveryDate + ", dealPerson=" + dealPerson + ", name="
				+ name + ", userId=" + userId + ", start=" + start + ", limit="
				+ limit + ", flag=" + flag + ", createTime=" + createTime
				+ ", state=" + state + ", dealState=" + dealState + "]";
	}
	//前台列表展示用
	private String createTime;
	private String state;
	private String dealState;
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
	public PointlikeResourceInfoBean getStartPosition()
	{
		return startPosition;
	}
	public void setStartPosition(PointlikeResourceInfoBean startPosition)
	{
		this.startPosition = startPosition;
	}
	public PointlikeResourceInfoBean getEndPosition()
	{
		return endPosition;
	}
	public void setEndPosition(PointlikeResourceInfoBean endPosition)
	{
		this.endPosition = endPosition;
	}
	
	/*public ArrayList<PhotoInfoBean> getLocusPhotos()
	{
		return locusPhotos;
	}
	public void setLocusPhotos(ArrayList<PhotoInfoBean> locusPhotos)
	{
		this.locusPhotos = locusPhotos;
	}*/


	public String getDealPerson() {
		return dealPerson;
	}
	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}
	public String getMatchScores() {
		return matchScores;
	}
	public Integer getRouteState() {
		return routeState;
	}
	public Integer getDeliveryState() {
		return deliveryState;
	}
	public void setMatchScores(String matchScores) {
		this.matchScores = matchScores;
	}
	public void setRouteState(Integer routeState) {
		this.routeState = routeState;
	}
	public void setDeliveryState(Integer deliveryState) {
		this.deliveryState = deliveryState;
	}

	public ArrayList<PointlikeResourceInfoBean> getLocusResourcePosition() {
		return locusResourcePosition;
	}
	public void setLocusResourcePosition(ArrayList<PointlikeResourceInfoBean> locusPosition) {
		this.locusResourcePosition = locusPosition;
	}
	public List<LocusPoint> getLocusPoints() {
		return locusPoints;
	}
	public void setLocusPoints(List<LocusPoint> locusPoints) {
		this.locusPoints = locusPoints;
	}
	public List<ErrorInfoBean> getErrors() {
		return errors;
	}
	public void setErrors(List<ErrorInfoBean> errors) {
		this.errors = errors;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDealState() {
		return dealState;
	}
	public void setDealState(String dealState) {
		this.dealState = dealState;
	}







}
