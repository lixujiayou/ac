// 

// 

package com.inspur.resources.bean;

import java.util.Date;
import java.io.Serializable;

public class PointInfoBean implements Serializable
{
    private static final long serialVersionUID = -9072380905628431269L;
    private String CABLENAME;
    private Short DIRECTION;
    private String EADDR;
    private String ECODE;
    private String ECODE2;
    private String EID;
    private String EID2;
    private String ENAME;
    private String ENAME2;
    private String FIBERSTATIONNAME;
    private String FIBNAME;
    private Short MFLAG;
    private String MP;
    private Date MTIME;
    private String NOTE;
    private String ODMCODE;
    private String OFPCODE;
    private String OFPNAME;
    private String OFPNO;
    private String PCODE;
    private String PCODE2;
    private String PID;
    private String PID2;
    private String PSERV;
    private Short PSTAT;
    private Short PTYPE;
    private String ROUTENAME;
    private String SERVNO;
    private String SERVTYPE;
    
    public PointInfoBean() {
        this.PID = null;
        this.EID = null;
        this.PCODE = null;
        this.NOTE = null;
        this.MP = null;
        this.PSERV = null;
        this.SERVTYPE = null;
        this.SERVNO = null;
        this.ECODE = null;
        this.EADDR = null;
        this.ENAME = null;
        this.PID2 = null;
        this.EID2 = null;
        this.FIBNAME = null;
        this.FIBERSTATIONNAME = null;
        this.OFPCODE = null;
        this.OFPNAME = null;
        this.PCODE2 = null;
        this.ENAME2 = null;
        this.ECODE2 = null;
        this.OFPNO = null;
        this.ROUTENAME = null;
        this.CABLENAME = null;
        this.ODMCODE = null;
    }
    
    public PointInfoBean(final PointInfoBean pointInfoBean) {
        this.PID = null;
        this.EID = null;
        this.PCODE = null;
        this.NOTE = null;
        this.MP = null;
        this.PSERV = null;
        this.SERVTYPE = null;
        this.SERVNO = null;
        this.ECODE = null;
        this.EADDR = null;
        this.ENAME = null;
        this.PID2 = null;
        this.EID2 = null;
        this.FIBNAME = null;
        this.FIBERSTATIONNAME = null;
        this.OFPCODE = null;
        this.OFPNAME = null;
        this.PCODE2 = null;
        this.ENAME2 = null;
        this.ECODE2 = null;
        this.OFPNO = null;
        this.ROUTENAME = null;
        this.CABLENAME = null;
        this.ODMCODE = null;
        this.EID = pointInfoBean.EID;
        this.EID2 = pointInfoBean.EID2;
        this.MP = pointInfoBean.MP;
        this.PCODE = pointInfoBean.PCODE;
        this.PSERV = pointInfoBean.PSERV;
        this.MFLAG = pointInfoBean.MFLAG;
        this.MTIME = pointInfoBean.MTIME;
        this.PID = pointInfoBean.PID;
        this.PID2 = pointInfoBean.PID2;
        this.PSTAT = pointInfoBean.PSTAT;
        this.FIBNAME = pointInfoBean.FIBNAME;
        this.OFPCODE = pointInfoBean.OFPCODE;
        this.OFPNAME = pointInfoBean.OFPNAME;
        this.NOTE = pointInfoBean.NOTE;
        this.PTYPE = pointInfoBean.PTYPE;
        this.DIRECTION = pointInfoBean.DIRECTION;
        this.SERVTYPE = pointInfoBean.SERVTYPE;
        this.SERVNO = pointInfoBean.SERVNO;
        this.ECODE = pointInfoBean.ECODE;
        this.EADDR = pointInfoBean.EADDR;
        this.ENAME = pointInfoBean.ENAME;
        this.ROUTENAME = pointInfoBean.ROUTENAME;
        this.CABLENAME = pointInfoBean.CABLENAME;
        this.FIBERSTATIONNAME = pointInfoBean.FIBERSTATIONNAME;
        this.PCODE2 = pointInfoBean.PCODE2;
        this.ENAME2 = pointInfoBean.ENAME2;
        this.ECODE2 = pointInfoBean.ECODE2;
        this.OFPNO = pointInfoBean.OFPNO;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o != null) {
            if (this == o) {
                return true;
            }
            if (o instanceof PointInfoBean) {
                final PointInfoBean pointInfoBean = (PointInfoBean)o;
                if (this.EID.equals(pointInfoBean.EID) && this.PID.equals(pointInfoBean.PID)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public String getCABLENAME() {
        return this.CABLENAME;
    }
    
    public int getCABLENAMELength() {
        if (this.CABLENAME == null) {
            return 0;
        }
        return this.CABLENAME.length() * 2;
    }
    
    public Short getDIRECTION() {
        return this.DIRECTION;
    }
    
    public String getDIRECTIONString() {
        if (this.DIRECTION == null) {
            return "\u672a\u77e5";
        }
        switch (this.DIRECTION) {
            default: {
                return "\u672a\u77e5";
            }
            case 1: {
                return "\u5c40\u7aef";
            }
            case 2: {
                return "\u7528\u6237\u7aef";
            }
        }
    }
    
    public String getEADDR() {
        return this.EADDR;
    }
    
    public String getECODE() {
        return this.ECODE;
    }
    
    public String getECODE2() {
        return this.ECODE2;
    }
    
    public int getECODE2Length() {
        if (this.ECODE2 == null) {
            return 0;
        }
        return this.ECODE2.length() * 2;
    }
    
    public String getEID() {
        return this.EID;
    }
    
    public String getEID2() {
        return this.EID2;
    }
    
    public int getEID2Length() {
        if (this.EID2 == null) {
            return 0;
        }
        return this.EID2.length() * 2;
    }
    
    public String getENAME() {
        return this.ENAME;
    }
    
    public String getENAME2() {
        return this.ENAME2;
    }
    
    public int getENAME2Length() {
        if (this.ENAME2 == null) {
            return 0;
        }
        return this.ENAME2.length() * 2;
    }
    
    public String getFIBERSTATIONNAME() {
        return this.FIBERSTATIONNAME;
    }
    
    public String getFIBNAME() {
        return this.FIBNAME;
    }
    
    public int getFIBNAMELength() {
        if (this.FIBNAME == null) {
            return 0;
        }
        return this.FIBNAME.length() * 2;
    }
    
    public Short getMFLAG() {
        return this.MFLAG;
    }
    
    public String getMP() {
        return this.MP;
    }
    
    public int getMPLength() {
        if (this.MP == null) {
            return 0;
        }
        return this.MP.length() * 2;
    }
    
    public Date getMTIME() {
        return this.MTIME;
    }
    
    public String getNOTE() {
        return this.NOTE;
    }
    
    public int getNOTELength() {
        if (this.NOTE == null) {
            return 0;
        }
        return this.NOTE.length() * 2;
    }
    
    public String getODMCODE() {
        return this.ODMCODE;
    }
    
    public String getOFPCODE() {
        return this.OFPCODE;
    }
    
    public int getOFPCODELength() {
        if (this.OFPCODE == null) {
            return 0;
        }
        return this.OFPCODE.length() * 2;
    }
    
    public String getOFPNAME() {
        return this.OFPNAME;
    }
    
    public int getOFPNAMELength() {
        if (this.OFPNAME == null) {
            return 0;
        }
        return this.OFPNAME.length() * 2;
    }
    
    public String getOFPNO() {
        return this.OFPNO;
    }
    
    public int getOFPNOLength() {
        if (this.OFPNO == null) {
            return 0;
        }
        return this.OFPNO.length() * 2;
    }
    
    public String getPCODE() {
        return this.PCODE;
    }
    
    public String getPCODE2() {
        return this.PCODE2;
    }
    
    public int getPCODE2Length() {
        if (this.PCODE2 == null) {
            return 0;
        }
        return this.PCODE2.length() * 2;
    }
    
    public int getPCODELength() {
        if (this.PCODE == null) {
            return 0;
        }
        return this.PCODE.length() * 2;
    }
    
    public String getPID() {
        return this.PID;
    }
    
    public String getPID2() {
        return this.PID2;
    }
    
    public int getPID2Length() {
        if (this.PID2 == null) {
            return 0;
        }
        return this.PID2.length() * 2;
    }
    
    public String getPSERV() {
        return this.PSERV;
    }
    
    public int getPSERVLength() {
        if (this.PSERV == null) {
            return 0;
        }
        return this.PSERV.length() * 2;
    }
    
    public Short getPSTAT() {
        return this.PSTAT;
    }
    
    public Short getPTYPE() {
        return this.PTYPE;
    }
    
    public String getPtypeString() {
        if (this.PTYPE == null) {
            return "\u666e\u901a\u7aef\u5b50";
        }
        switch (this.PTYPE) {
            default: {
                return "\u666e\u901a\u7aef\u5b50";
            }
            case 1: {
                return "\u666e\u901a\u7aef\u5b50";
            }
            case 2: {
                return "\u5206\u5149\u5668\u7aef\u5b50";
            }
            case 3: {
                return "\u03bb\u7aef\u5b50";
            }
            case 4: {
                return "ONU\u4e0a\u8fde\u7aef\u5b50";
            }
            case 5: {
                return "ONU\u7f51\u53e3";
            }
        }
    }
    
    public String getROUTENAME() {
        return this.ROUTENAME;
    }
    
    public int getROUTENAMELength() {
        if (this.ROUTENAME == null) {
            return 0;
        }
        return this.ROUTENAME.length() * 2;
    }
    
    public String getSERVNO() {
        return this.SERVNO;
    }
    
    public int getSERVNOLength() {
        if (this.SERVNO == null) {
            return 0;
        }
        return this.SERVNO.length() * 2;
    }
    
    public String getSERVTYPE() {
        return this.SERVTYPE;
    }
    
    public int getSERVTYPELength() {
        if (this.SERVTYPE == null) {
            return 0;
        }
        return this.SERVTYPE.length() * 2;
    }
    
    public String getStatString() {
        if (this.PSTAT == null) {
            return "\u65e0\u7aef\u5b50\u72b6\u6001\u4fe1\u606f";
        }
        if (this.PSTAT == 1) {
            return "\u7a7a\u95f2";
        }
        if (this.PSTAT == 2) {
            return "\u6545\u969c";
        }
        if (this.PSTAT == 3) {
            return "\u53ef\u7528";
        }
        if (this.PSTAT == 4) {
            return "\u5360\u7528";
        }
        if (this.PSTAT == 5) {
            return "\u5f85\u6838\u67e5";
        }
        if (this.PSTAT == 6) {
            return "\u672a\u4f7f\u7528";
        }
        return "\u65e0\u7aef\u5b50\u72b6\u6001\u4fe1\u606f";
    }
    
    public void setCABLENAME(final String cablename) {
        this.CABLENAME = cablename;
    }
    
    public void setDIRECTION(final Short direction) {
        this.DIRECTION = direction;
    }
    
    public void setEADDR(final String eaddr) {
        this.EADDR = eaddr;
    }
    
    public void setECODE(final String ecode) {
        this.ECODE = ecode;
    }
    
    public void setECODE2(final String ecode2) {
        this.ECODE2 = ecode2;
    }
    
    public void setEID(final String eid) {
        this.EID = eid;
    }
    
    public void setEID2(final String eid2) {
        this.EID2 = eid2;
    }
    
    public void setENAME(final String ename) {
        this.ENAME = ename;
    }
    
    public void setENAME2(final String ename2) {
        this.ENAME2 = ename2;
    }
    
    public void setFIBERSTATIONNAME(final String fiberstationname) {
        this.FIBERSTATIONNAME = fiberstationname;
    }
    
    public void setFIBNAME(final String fibname) {
        this.FIBNAME = fibname;
    }
    
    public void setMFLAG(final Short mflag) {
        this.MFLAG = mflag;
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
    
    public void setODMCODE(final String odmcode) {
        this.ODMCODE = odmcode;
    }
    
    public void setOFPCODE(final String ofpcode) {
        this.OFPCODE = ofpcode;
    }
    
    public void setOFPNAME(final String ofpname) {
        this.OFPNAME = ofpname;
    }
    
    public void setOFPNO(final String ofpno) {
        this.OFPNO = ofpno;
    }
    
    public void setPCODE(final String pcode) {
        this.PCODE = pcode;
    }
    
    public void setPCODE2(final String pcode2) {
        this.PCODE2 = pcode2;
    }
    
    public void setPID(final String pid) {
        this.PID = pid;
    }
    
    public void setPID2(final String pid2) {
        this.PID2 = pid2;
    }
    
    public void setPSERV(final String pserv) {
        this.PSERV = pserv;
    }
    
    public void setPSTAT(final Short pstat) {
        this.PSTAT = pstat;
    }
    
    public void setPTYPE(final Short ptype) {
        this.PTYPE = ptype;
    }
    
    public void setROUTENAME(final String routename) {
        this.ROUTENAME = routename;
    }
    
    public void setSERVNO(final String servno) {
        this.SERVNO = servno;
    }
    
    public void setSERVTYPE(final String servtype) {
        this.SERVTYPE = servtype;
    }
}
