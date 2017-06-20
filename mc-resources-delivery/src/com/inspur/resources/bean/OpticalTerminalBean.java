package com.inspur.resources.bean;

import java.io.Serializable;

/**
 * 光终端盒
 *
 */
public class OpticalTerminalBean implements Serializable
{

	private Integer id;// 主键标识
	private String terminalName;// 光终端盒名称
	private Integer terminalNum;// 序号
	private String longitude;// 经度
	private String latitude;// 纬度
	private Integer rowNum;// 行数
	private Integer colNum;// 列数
	private Integer attachType;// 归属点类型
	private String attachName;// 归属点名称
	private String attachId;// 归属点id
	private String terminalAddres;// 设备地址
	private String dataQualitier;// 数据质量责任人
	private String maintainer;// 一线维护人
	private String deleteflag;// 删除标识

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getTerminalName()
	{
		return terminalName;
	}

	public void setTerminalName(String terminalName)
	{
		this.terminalName = terminalName;
	}

	public Integer getTerminalNum()
	{
		return terminalNum;
	}

	public void setTerminalNum(Integer terminalNum)
	{
		this.terminalNum = terminalNum;
	}

	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public Integer getRowNum()
	{
		return rowNum;
	}

	public void setRowNum(Integer rowNum)
	{
		this.rowNum = rowNum;
	}

	public Integer getColNum()
	{
		return colNum;
	}

	public void setColNum(Integer colNum)
	{
		this.colNum = colNum;
	}

	public Integer getAttachType()
	{
		return attachType;
	}

	public void setAttachType(Integer attachType)
	{
		this.attachType = attachType;
	}

	public String getAttachName()
	{
		return attachName;
	}

	public void setAttachName(String attachName)
	{
		this.attachName = attachName;
	}

	public String getAttachId()
	{
		return attachId;
	}

	public void setAttachId(String attachId)
	{
		this.attachId = attachId;
	}

	public String getTerminalAddres()
	{
		return terminalAddres;
	}

	public void setTerminalAddres(String terminalAddres)
	{
		this.terminalAddres = terminalAddres;
	}

	public String getDataQualitier()
	{
		return dataQualitier;
	}

	public void setDataQualitier(String dataQualitier)
	{
		this.dataQualitier = dataQualitier;
	}

	public String getMaintainer()
	{
		return maintainer;
	}

	public String getDeleteflag()
	{
		return deleteflag;
	}

	public void setDeleteflag(String deleteflag)
	{
		this.deleteflag = deleteflag;
	}

	public void setMaintainer(String maintainer)
	{
		this.maintainer = maintainer;
	}
}
