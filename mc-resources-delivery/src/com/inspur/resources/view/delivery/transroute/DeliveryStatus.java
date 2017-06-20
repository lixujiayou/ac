package com.inspur.resources.view.delivery.transroute;

/**
 * @author Dell-
 *
 */
public enum DeliveryStatus
{
	NOT_START, // 点击出发按钮之前的状态
	START, // 点击了出发按钮，并成功将一个资源点选作了起始点，但起始点的拍照还没结束
	ONGOING, // 起始点拍照已完成，可以开始计时
	ON_REPORT_ERROR, // 中途上报隐患信息
	OVER,// 点击了结束按钮，单路由表单还没有提交或保存
}
