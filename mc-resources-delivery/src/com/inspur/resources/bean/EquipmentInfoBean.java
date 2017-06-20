// 

// 

package com.inspur.resources.bean;

import java.util.Date;
import java.io.Serializable;

public class EquipmentInfoBean implements Serializable
{
    private static final long serialVersionUID = -8000033312482916303L;
    private String AREANO;
    private String EADDR;
    private String ECODE;
    private String EID;
    private String ENAME;
    private Short ESTAT;
    private Short ETYPE;
    private String GID;
    private Double LAT;
    private Double LON;
    private Short MFLAG;
    private String MP;
    private Date MTIME;
    private String NOTE;
    private String STATION;
    private String del;
    private String generatorName;
    private String imageNames;
    private String posX;
    private String posY;
    private String proper_zcbqh;
    private String changjia;
	private Integer jijialeixing;
    private String jijiahanghao;
    private String jijialiehao;
    
    private Integer gjxmianshu;
    private Integer gjxhangshu;
    private Integer gjxlieshu;
    private String dataQualityPrincipal;
	private String parties;
    
    public EquipmentInfoBean() {
        this.GID = null;
        this.generatorName = null;
        this.EID = null;
        this.ECODE = null;
        this.ENAME = null;
        this.EADDR = null;
        this.MP = null;
        this.NOTE = null;
        this.STATION = null;
        this.AREANO = null;
        this.proper_zcbqh = null;
    }
    
    public EquipmentInfoBean(final EquipmentInfoBean equipmentInfoBean) {
        this.GID = null;
        this.generatorName = null;
        this.EID = null;
        this.ECODE = null;
        this.ENAME = null;
        this.EADDR = null;
        this.MP = null;
        this.NOTE = null;
        this.STATION = null;
        this.AREANO = null;
        this.proper_zcbqh = null;
        if (equipmentInfoBean == null) {
            return;
        }
        this.GID = equipmentInfoBean.GID;
        this.generatorName = equipmentInfoBean.generatorName;
        this.EID = equipmentInfoBean.EID;
        this.ECODE = equipmentInfoBean.ECODE;
        this.ENAME = equipmentInfoBean.ENAME;
        this.EADDR = equipmentInfoBean.EADDR;
        this.MFLAG = equipmentInfoBean.MFLAG;
        this.ESTAT = equipmentInfoBean.ESTAT;
        this.ETYPE = equipmentInfoBean.ETYPE;
        this.LON = equipmentInfoBean.LON;
        this.LAT = equipmentInfoBean.LAT;
        this.MTIME = equipmentInfoBean.MTIME;
        this.MP = equipmentInfoBean.MP;
        this.NOTE = equipmentInfoBean.NOTE;
        this.STATION = equipmentInfoBean.STATION;
        this.posX = equipmentInfoBean.posX;
        this.posY = equipmentInfoBean.posY;
        this.proper_zcbqh = equipmentInfoBean.proper_zcbqh;
        this.imageNames = equipmentInfoBean.imageNames;
    }
    
    public String getAREANO() {
        return this.AREANO;
    }
    
    public String getDeleteFlag() {
        return this.del;
    }
    
    public String getEADDR() {
        return this.EADDR;
    }
    
    public String getECODE() {
        return this.ECODE;
    }
    
    public String getEID() {
        return this.EID;
    }
    
    public String getENAME() {
        return this.ENAME;
    }
    
    public Short getESTAT() {
        return this.ESTAT;
    }
    
    public short getETYPE() {
        return this.ETYPE;
    }
    
    public String getEstatString() {
        if (this.getESTAT() == 1) {
            return "\u7a7a\u95f2";
        }
        if (this.getESTAT() == 2) {
            return "\u6545\u969c";
        }
        if (this.getESTAT() == 0) {
            return "\u53ef\u7528";
        }
        if (this.getESTAT() == 3) {
            return "\u53ef\u7528";
        }
        return "\u6ca1\u6709\u8bbe\u5907\u72b6\u6001\u4fe1\u606f";
    }
    
    public String getEtypeString() {
        if (this.getETYPE() == 9) {
            return "\u672a\u77e5\u8bbe\u5907";
        }
        if (this.getETYPE() == 1) {
            return "\u5149\u914d\u7ebf\u67b6\uff08ODF\uff09";
        }
        if (this.getETYPE() == 3) {
            return "\u5149\u4ea4\u63a5\u7bb1";
        }
        return "\u6ca1\u6709\u8bbe\u5907\u7c7b\u578b\u4fe1\u606f";
    }
    
    public String getGID() {
        return this.GID;
    }
    
    public String getGNAME() {
        return this.generatorName;
    }
    
    public String getImageNames() {
        return this.imageNames;
    }
    
    public Double getLAT() {
        return this.LAT;
    }
    
    public Double getLON() {
        return this.LON;
    }
    
    public short getMFLAG() {
        return this.MFLAG;
    }
    
    public String getMP() {
        return this.MP;
    }
    
    public Date getMTIME() {
        return this.MTIME;
    }
    
    public String getNOTE() {
        return this.NOTE;
    }
    
    public String getPosX() {
        return this.posX;
    }
    
    public String getPosY() {
        return this.posY;
    }
    
    public String getProper_zcbqh() {
        return this.proper_zcbqh;
    }
    
    public String getSTATION() {
        return this.STATION;
    }
    
    public void setAREANO(final String areano) {
        this.AREANO = areano;
    }
    
    public void setDeleteFlag(final String del) {
        this.del = del;
    }
    
    public void setEADDR(final String eaddr) {
        this.EADDR = eaddr;
    }
    
    public void setECODE(final String ecode) {
        this.ECODE = ecode;
    }
    
    public void setEID(final String eid) {
        this.EID = eid;
    }
    
    public void setENAME(final String ename) {
        this.ENAME = ename;
    }
    
    public void setESTAT(final Short estat) {
        this.ESTAT = estat;
    }
    
    public void setETYPE(final short n) {
        this.ETYPE = n;
    }
    
    public void setGID(final String gid) {
        this.GID = gid;
    }
    
    public void setGNAME(final String generatorName) {
        this.generatorName = generatorName;
    }
    
    public void setImageNames(final String imageNames) {
        this.imageNames = imageNames;
    }
    
    public void setLAT(final Double lat) {
        this.LAT = lat;
    }
    
    public void setLON(final Double lon) {
        this.LON = lon;
    }
    
    public void setMFLAG(final short n) {
        this.MFLAG = n;
    }
    
    public void setMP(final String mp) {
        this.MP = mp;
    }
    
    public void setMTIME(final Date mtime) {
        this.MTIME = mtime;
    }
    
    public void setNOTE(final String note) {
        this.NOTE = note;
    }
    
    public void setPosX(final String posX) {
        this.posX = posX;
    }
    
    public void setPosY(final String posY) {
        this.posY = posY;
    }
    
    public void setProper_zcbqh(final String proper_zcbqh) {
        this.proper_zcbqh = proper_zcbqh;
    }
    
    public void setSTATION(final String station) {
        this.STATION = station;
    }
    
    public String getChangjia() {
		return changjia;
	}

	public void setChangjia(String changjia) {
		this.changjia = changjia;
	}

	public Integer getJijialeixing() {
		return jijialeixing;
	}

	public void setJijialeixing(Integer jijialeixing) {
		this.jijialeixing = jijialeixing;
	}

	public String getJijiahanghao() {
		return jijiahanghao;
	}

	public void setJijiahanghao(String jijiahanghao) {
		this.jijiahanghao = jijiahanghao;
	}

	public String getJijialiehao() {
		return jijialiehao;
	}

	public void setJijialiehao(String jijialiehao) {
		this.jijialiehao = jijialiehao;
	}

	
	public Integer getGjxmianshu() {
		return gjxmianshu;
	}

	public void setGjxmianshu(Integer gjxmianshu) {
		this.gjxmianshu = gjxmianshu;
	}

	public Integer getGjxhangshu() {
		return gjxhangshu;
	}

	public void setGjxhangshu(Integer gjxhangshu) {
		this.gjxhangshu = gjxhangshu;
	}

	public Integer getGjxlieshu() {
		return gjxlieshu;
	}

	public void setGjxlieshu(Integer gjxlieshu) {
		this.gjxlieshu = gjxlieshu;
	}

	public String getDataQualityPrincipal() {
		return dataQualityPrincipal;
	}

	public void setDataQualityPrincipal(String dataQualityPrincipal) {
		this.dataQualityPrincipal = dataQualityPrincipal;
	}

	public String getParties() {
		return parties;
	}

	public void setParties(String parties) {
		this.parties = parties;
	}
}
