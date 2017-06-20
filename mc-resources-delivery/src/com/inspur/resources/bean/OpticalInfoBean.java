// 

// 

package com.inspur.resources.bean;

import java.io.Serializable;

public class OpticalInfoBean implements Serializable
{
    private static final long serialVersionUID = -1418391199875192946L;
    private String Authorized_management_org_id;
    private String Creation_date;
    private String Designed_purpose;
    private String END_SITE_AREANO;
    private Integer End_site_id;
    private String End_site_name;
    private String Last_update_date;
    private String Maintenance_status_enum_id;
    private String Management_authorized_flag;
    private Integer Optical_cable_id;
    private String Optical_cable_level_enum_id;
//    private String Optical_cable_name;
    private String Optical_cable_name;
    private String Optical_cable_sign;
    private String Optical_cable_topology;
    private Integer Province_id;
    private String START_SITE_AREANO;
    private Integer Start_site_id;
    private String Start_site_name;
    private Integer cstate;
    private Integer deleted_flag;
    private String maintenanceAreaName;
    private String dataQualityPrincipal;
    private String parties;
    
    public OpticalInfoBean() {
        this.Optical_cable_name = null;
        this.Maintenance_status_enum_id = null;
        this.Optical_cable_topology = null;
        this.Start_site_name = null;
        this.START_SITE_AREANO = null;
        this.End_site_name = null;
        this.END_SITE_AREANO = null;
        this.Optical_cable_sign = null;
        this.Management_authorized_flag = null;
        this.Authorized_management_org_id = null;
        this.Designed_purpose = null;
        this.Creation_date = null;
        this.Last_update_date = null;
    }
    
    public String getAuthorized_management_org_id() {
        return this.Authorized_management_org_id;
    }
    
    public String getCreation_date() {
        return this.Creation_date;
    }
    
    public int getCstate() {
        return this.cstate;
    }
    
    public int getDeleted_flag() {
        return this.deleted_flag;
    }
    
    public String getDesigned_purpose() {
        return this.Designed_purpose;
    }
    
    public String getEND_SITE_AREANO() {
        return this.END_SITE_AREANO;
    }
    
    public int getEnd_site_id() {
        return this.End_site_id;
    }
    
    public String getEnd_site_name() {
        return this.End_site_name;
    }
    
    public String getLast_update_date() {
        return this.Last_update_date;
    }
    
    public String getMaintenanceAreaName() {
        return this.maintenanceAreaName;
    }
    
    public String getMaintenance_status_enum_id() {
        return this.Maintenance_status_enum_id;
    }
    
    public String getManagement_authorized_flag() {
        return this.Management_authorized_flag;
    }
    
    public int getOptical_cable_id() {
        return this.Optical_cable_id;
    }
    
    public String getOptical_cable_level_enum_id() {
        return this.Optical_cable_level_enum_id;
    }
    
    public String getOptical_cable_level_enum_idString() {
        if (this.getOptical_cable_level_enum_id().equals("1")) {
            return "\u4e00\u7ea7\u5e72\u7ebf";
        }
        if (this.getOptical_cable_level_enum_id().equals("2")) {
            return "\u4e8c\u7ea7\u5e72\u7ebf";
        }
        if (this.getOptical_cable_level_enum_id().equals("3")) {
            return "\u672c\u5730\u4e2d\u7ee7";
        }
        if (this.getOptical_cable_level_enum_id().equals("4")) {
            return "\u672c\u5730\u6838\u5fc3";
        }
        if (this.getOptical_cable_level_enum_id().equals("5")) {
            return "\u672c\u5730\u6c47\u805a";
        }
        if (this.getOptical_cable_level_enum_id().equals("6")) {
            return "\u672c\u5730\u63a5\u5165";
        }
        return "\u672a\u77e5\u7ea7\u522b";
    }
    
    public String getOptical_cable_name() {
        return this.Optical_cable_name;
    }
    
    public String getOptical_cable_sign() {
        return this.Optical_cable_sign;
    }
    
    public String getOptical_cable_topology() {
        return this.Optical_cable_topology;
    }
    
    public int getProvince_id() {
        return this.Province_id;
    }
    
    public String getSTART_SITE_AREANO() {
        return this.START_SITE_AREANO;
    }
    
    public int getStart_site_id() {
        return this.Start_site_id;
    }
    
    public String getStart_site_name() {
        return this.Start_site_name;
    }
    
    public void setAuthorized_management_org_id(final String authorized_management_org_id) {
        this.Authorized_management_org_id = authorized_management_org_id;
    }
    
    public void setCreation_date(final String creation_date) {
        this.Creation_date = creation_date;
    }
    
    public void setCstate(final int n) {
        this.cstate = n;
    }
    
    public void setDeleted_flag(final int n) {
        this.deleted_flag = n;
    }
    
    public void setDesigned_purpose(final String designed_purpose) {
        this.Designed_purpose = designed_purpose;
    }
    
    public void setEND_SITE_AREANO(final String end_SITE_AREANO) {
        this.END_SITE_AREANO = end_SITE_AREANO;
    }
    
    public void setEnd_site_id(final int n) {
        this.End_site_id = n;
    }
    
    public void setEnd_site_name(final String end_site_name) {
        this.End_site_name = end_site_name;
    }
    
    public void setLast_update_date(final String last_update_date) {
        this.Last_update_date = last_update_date;
    }
    
    public void setMaintenanceAreaName(final String maintenanceAreaName) {
        this.maintenanceAreaName = maintenanceAreaName;
    }
    
    public void setMaintenance_status_enum_id(final String maintenance_status_enum_id) {
        this.Maintenance_status_enum_id = maintenance_status_enum_id;
    }
    
    public void setManagement_authorized_flag(final String management_authorized_flag) {
        this.Management_authorized_flag = management_authorized_flag;
    }
    
    public void setOptical_cable_id(final int n) {
        this.Optical_cable_id = n;
    }
    
    public void setOptical_cable_level_enum_id(final String optical_cable_level_enum_id) {
        this.Optical_cable_level_enum_id = optical_cable_level_enum_id;
    }
    
    public void setOptical_cable_name(final String optical_cable_name) {
        this.Optical_cable_name = optical_cable_name;
    }
    
    public void setOptical_cable_sign(final String optical_cable_sign) {
        this.Optical_cable_sign = optical_cable_sign;
    }
    
    public void setOptical_cable_topology(final String optical_cable_topology) {
        this.Optical_cable_topology = optical_cable_topology;
    }
    
    public void setProvince_id(final int n) {
        this.Province_id = n;
    }
    
    public void setSTART_SITE_AREANO(final String start_SITE_AREANO) {
        this.START_SITE_AREANO = start_SITE_AREANO;
    }
    
    public void setStart_site_id(final int n) {
        this.Start_site_id = n;
    }
    
    public void setStart_site_name(final String start_site_name) {
        this.Start_site_name = start_site_name;
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
