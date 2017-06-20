// 

// 

package com.inspur.resources.bean;

import java.util.ArrayList;
import java.util.List;

public class SearchListInfoBean
{
    private List<GeneratorInfoBean> generatorList;
    private Integer generatorTotal;
    private String lat;
    private String lon;
    private String name;
    private List<EquipmentInfoBean> occList;
    private Integer occTotal;
    private List<PoleInfoBean> poleList;
    private Integer poleTotal;
    private String region;
    private List<StationBaseInfoBean> stationList;
    private Integer stationTotal;
    private List<WellInfoBean> wellList;
    private Integer wellTotal;
    
    public SearchListInfoBean() {
        this.stationList = new ArrayList<StationBaseInfoBean>();
        this.generatorList = new ArrayList<GeneratorInfoBean>();
        this.wellList = new ArrayList<WellInfoBean>();
        this.poleList = new ArrayList<PoleInfoBean>();
        this.occList = new ArrayList<EquipmentInfoBean>();
    }
    
    public List<GeneratorInfoBean> getGeneratorList() {
        return this.generatorList;
    }
    
    public Integer getGeneratorTotal() {
        return this.generatorTotal;
    }
    
    public String getLat() {
        return this.lat;
    }
    
    public String getLon() {
        return this.lon;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<EquipmentInfoBean> getOccList() {
        return this.occList;
    }
    
    public Integer getOccTotal() {
        return this.occTotal;
    }
    
    public List<PoleInfoBean> getPoleList() {
        return this.poleList;
    }
    
    public Integer getPoleTotal() {
        return this.poleTotal;
    }
    
    public String getRegion() {
        return this.region;
    }
    
    public List<StationBaseInfoBean> getStationList() {
        return this.stationList;
    }
    
    public Integer getStationTotal() {
        return this.stationTotal;
    }
    
    public List<WellInfoBean> getWellList() {
        return this.wellList;
    }
    
    public Integer getWellTotal() {
        return this.wellTotal;
    }
    
    public void setGeneratorList(final List<GeneratorInfoBean> generatorList) {
        this.generatorList = generatorList;
    }
    
    public void setGeneratorTotal(final Integer generatorTotal) {
        this.generatorTotal = generatorTotal;
    }
    
    public void setLat(final String lat) {
        this.lat = lat;
    }
    
    public void setLon(final String lon) {
        this.lon = lon;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setOccList(final List<EquipmentInfoBean> occList) {
        this.occList = occList;
    }
    
    public void setOccTotal(final Integer occTotal) {
        this.occTotal = occTotal;
    }
    
    public void setPoleList(final List<PoleInfoBean> poleList) {
        this.poleList = poleList;
    }
    
    public void setPoleTotal(final Integer poleTotal) {
        this.poleTotal = poleTotal;
    }
    
    public void setRegion(final String region) {
        this.region = region;
    }
    
    public void setStationList(final List<StationBaseInfoBean> stationList) {
        this.stationList = stationList;
    }
    
    public void setStationTotal(final Integer stationTotal) {
        this.stationTotal = stationTotal;
    }
    
    public void setWellList(final List<WellInfoBean> wellList) {
        this.wellList = wellList;
    }
    
    public void setWellTotal(final Integer wellTotal) {
        this.wellTotal = wellTotal;
    }
}
