// 

// 

package com.inspur.resources.bean;

import java.io.Serializable;

public class PropertyInfoBean implements Serializable
{
    private static final long serialVersionUID = -563301133671533121L;
    private EquipmentInfoBean occ;
    private EquipmentInfoBean odf;
    private String proper_ablftrq;
    private String proper_cqpzh;
    private String proper_cscylx;
    private String proper_fjxx1;
    private String proper_fjxx2;
    private String proper_fjxx3;
    private String proper_fssbjfj;
    private String proper_fzjldw;
    private String proper_fzsl;
    private String proper_gdzcljzjzh;
    private String proper_gdzcljzjzhms;
    private String proper_gdzcyzzh;
    private String proper_gdzcyzzhms;
    private String proper_jldw;
    private String proper_krgzxm;
    private String proper_krgzxmms;
    private String proper_pdjg;
    private String proper_pghsksyyf;
    private String proper_pgjz;
    private String proper_qcgcxmbh;
    private String proper_qczckpbh;
    private String proper_sbbh;
    private String proper_sfcf;
    private String proper_ssqy;
    private String proper_ssqyms;
    private String proper_synx;
    private String proper_syys;
    private String proper_syzt;
    private String proper_syztms;
    private String proper_szdd;
    private String proper_szddms;
    private String proper_xmbh;
    private String proper_xmbhms;
    private String proper_ygbh;
    private String proper_ygxm;
    private String proper_zcbh;
    private String proper_zcbnzj;
    private String proper_zcbqh;
    private String proper_zcbqzj;
    private String proper_zccb;
    private String proper_zccjrq;
    private String proper_zccz;
    private String proper_zcgjz;
    private String proper_zcgjzms;
    private String proper_zcgs;
    private String proper_zcjz;
    private String proper_zcjzzb;
    private String proper_zclb;
    private String proper_zclbms;
    private String proper_zcljzj;
    private String proper_zcly;
    private String proper_zcmc;
    private String proper_zcqyrq;
    private String proper_zcsl;
    private String proper_zcxh;
    private String proper_zczb;
    private String proper_zczyglbm;
    private String proper_zjfyzh;
    private String proper_zjfyzhms;
    private String proper_zjzt;
    private String proper_zylx;
    private String proper_zysx;
    private String proper_zzs;
    private Integer propertyId;
    private String rfid_UID;
    private HighFrequencySwitchingPowerSupplyInfoBean switching;
    private String type;
    
    public PropertyInfoBean() {
    }
    
    public PropertyInfoBean(final PropertyInfoBean propertyInfoBean) {
        this.rfid_UID = propertyInfoBean.rfid_UID;
        this.propertyId = propertyInfoBean.propertyId;
        this.proper_zczb = propertyInfoBean.proper_zczb;
        this.proper_zcbh = propertyInfoBean.proper_zcbh;
        this.proper_zcbqh = propertyInfoBean.proper_zcbqh;
        this.proper_pdjg = propertyInfoBean.proper_pdjg;
        this.proper_zcmc = propertyInfoBean.proper_zcmc;
        this.proper_sfcf = propertyInfoBean.proper_sfcf;
        this.proper_zclb = propertyInfoBean.proper_zclb;
        this.proper_zclbms = propertyInfoBean.proper_zclbms;
        this.proper_zzs = propertyInfoBean.proper_zzs;
        this.proper_zcxh = propertyInfoBean.proper_zcxh;
        this.proper_zcsl = propertyInfoBean.proper_zcsl;
        this.proper_jldw = propertyInfoBean.proper_jldw;
        this.proper_zcgjz = propertyInfoBean.proper_zcgjz;
        this.proper_zcgjzms = propertyInfoBean.proper_zcgjzms;
        this.proper_zccjrq = propertyInfoBean.proper_zccjrq;
        this.proper_zcqyrq = propertyInfoBean.proper_zcqyrq;
        this.proper_ablftrq = propertyInfoBean.proper_ablftrq;
        this.proper_synx = propertyInfoBean.proper_synx;
        this.proper_syys = propertyInfoBean.proper_syys;
        this.proper_gdzcyzzh = propertyInfoBean.proper_gdzcyzzh;
        this.proper_gdzcyzzhms = propertyInfoBean.proper_gdzcyzzhms;
        this.proper_gdzcljzjzh = propertyInfoBean.proper_gdzcljzjzh;
        this.proper_gdzcljzjzhms = propertyInfoBean.proper_gdzcljzjzhms;
        this.proper_zjfyzh = propertyInfoBean.proper_zjfyzh;
        this.proper_zjfyzhms = propertyInfoBean.proper_zjfyzhms;
        this.proper_zccb = propertyInfoBean.proper_zccb;
        this.proper_zcjz = propertyInfoBean.proper_zcjz;
        this.proper_zccz = propertyInfoBean.proper_zccz;
        this.proper_zcbqzj = propertyInfoBean.proper_zcbqzj;
        this.proper_zcbnzj = propertyInfoBean.proper_zcbnzj;
        this.proper_zcljzj = propertyInfoBean.proper_zcljzj;
        this.proper_zcjzzb = propertyInfoBean.proper_zcjzzb;
        this.proper_ygbh = propertyInfoBean.proper_ygbh;
        this.proper_ygxm = propertyInfoBean.proper_ygxm;
        this.proper_ssqy = propertyInfoBean.proper_ssqy;
        this.proper_ssqyms = propertyInfoBean.proper_ssqyms;
        this.proper_szdd = propertyInfoBean.proper_szdd;
        this.proper_szddms = propertyInfoBean.proper_szddms;
        this.proper_syzt = propertyInfoBean.proper_syzt;
        this.proper_syztms = propertyInfoBean.proper_syztms;
        this.proper_qczckpbh = propertyInfoBean.proper_qczckpbh;
        this.proper_cqpzh = propertyInfoBean.proper_cqpzh;
        this.proper_sbbh = propertyInfoBean.proper_sbbh;
        this.proper_zysx = propertyInfoBean.proper_zysx;
        this.proper_zylx = propertyInfoBean.proper_zylx;
        this.proper_zcly = propertyInfoBean.proper_zcly;
        this.proper_zcgs = propertyInfoBean.proper_zcgs;
        this.proper_krgzxm = propertyInfoBean.proper_krgzxm;
        this.proper_krgzxmms = propertyInfoBean.proper_krgzxmms;
        this.proper_zjzt = propertyInfoBean.proper_zjzt;
        this.proper_pgjz = propertyInfoBean.proper_pgjz;
        this.proper_pghsksyyf = propertyInfoBean.proper_pghsksyyf;
        this.proper_fjxx1 = propertyInfoBean.proper_fjxx1;
        this.proper_fjxx2 = propertyInfoBean.proper_fjxx2;
        this.proper_fjxx3 = propertyInfoBean.proper_fjxx3;
        this.proper_cscylx = propertyInfoBean.proper_cscylx;
        this.proper_fssbjfj = propertyInfoBean.proper_fssbjfj;
        this.proper_fzsl = propertyInfoBean.proper_fzsl;
        this.proper_fzjldw = propertyInfoBean.proper_fzjldw;
        this.proper_xmbh = propertyInfoBean.proper_xmbh;
        this.proper_xmbhms = propertyInfoBean.proper_xmbhms;
        this.proper_qcgcxmbh = propertyInfoBean.proper_qcgcxmbh;
        this.proper_zczyglbm = propertyInfoBean.proper_zczyglbm;
        this.type = propertyInfoBean.type;
        this.odf = propertyInfoBean.odf;
        this.occ = propertyInfoBean.occ;
        this.switching = propertyInfoBean.switching;
    }
    
    public EquipmentInfoBean getOcc() {
        return this.occ;
    }
    
    public EquipmentInfoBean getOdf() {
        return this.odf;
    }
    
    public String getProper_ablftrq() {
        return this.proper_ablftrq;
    }
    
    public String getProper_cqpzh() {
        return this.proper_cqpzh;
    }
    
    public String getProper_cscylx() {
        return this.proper_cscylx;
    }
    
    public String getProper_fjxx1() {
        return this.proper_fjxx1;
    }
    
    public String getProper_fjxx2() {
        return this.proper_fjxx2;
    }
    
    public String getProper_fjxx3() {
        return this.proper_fjxx3;
    }
    
    public String getProper_fssbjfj() {
        return this.proper_fssbjfj;
    }
    
    public String getProper_fzjldw() {
        return this.proper_fzjldw;
    }
    
    public String getProper_fzsl() {
        return this.proper_fzsl;
    }
    
    public String getProper_gdzcljzjzh() {
        return this.proper_gdzcljzjzh;
    }
    
    public String getProper_gdzcljzjzhms() {
        return this.proper_gdzcljzjzhms;
    }
    
    public String getProper_gdzcyzzh() {
        return this.proper_gdzcyzzh;
    }
    
    public String getProper_gdzcyzzhms() {
        return this.proper_gdzcyzzhms;
    }
    
    public String getProper_jldw() {
        return this.proper_jldw;
    }
    
    public String getProper_krgzxm() {
        return this.proper_krgzxm;
    }
    
    public String getProper_krgzxmms() {
        return this.proper_krgzxmms;
    }
    
    public String getProper_pdjg() {
        return this.proper_pdjg;
    }
    
    public String getProper_pghsksyyf() {
        return this.proper_pghsksyyf;
    }
    
    public String getProper_pgjz() {
        return this.proper_pgjz;
    }
    
    public String getProper_qcgcxmbh() {
        return this.proper_qcgcxmbh;
    }
    
    public String getProper_qczckpbh() {
        return this.proper_qczckpbh;
    }
    
    public String getProper_sbbh() {
        return this.proper_sbbh;
    }
    
    public String getProper_sfcf() {
        return this.proper_sfcf;
    }
    
    public String getProper_ssqy() {
        return this.proper_ssqy;
    }
    
    public String getProper_ssqyms() {
        return this.proper_ssqyms;
    }
    
    public String getProper_synx() {
        return this.proper_synx;
    }
    
    public String getProper_syys() {
        return this.proper_syys;
    }
    
    public String getProper_syzt() {
        return this.proper_syzt;
    }
    
    public String getProper_syztms() {
        return this.proper_syztms;
    }
    
    public String getProper_szdd() {
        return this.proper_szdd;
    }
    
    public String getProper_szddms() {
        return this.proper_szddms;
    }
    
    public String getProper_xmbh() {
        return this.proper_xmbh;
    }
    
    public String getProper_xmbhms() {
        return this.proper_xmbhms;
    }
    
    public String getProper_ygbh() {
        return this.proper_ygbh;
    }
    
    public String getProper_ygxm() {
        return this.proper_ygxm;
    }
    
    public String getProper_zcbh() {
        return this.proper_zcbh;
    }
    
    public String getProper_zcbnzj() {
        return this.proper_zcbnzj;
    }
    
    public String getProper_zcbqh() {
        return this.proper_zcbqh;
    }
    
    public String getProper_zcbqzj() {
        return this.proper_zcbqzj;
    }
    
    public String getProper_zccb() {
        return this.proper_zccb;
    }
    
    public String getProper_zccjrq() {
        return this.proper_zccjrq;
    }
    
    public String getProper_zccz() {
        return this.proper_zccz;
    }
    
    public String getProper_zcgjz() {
        return this.proper_zcgjz;
    }
    
    public String getProper_zcgjzms() {
        return this.proper_zcgjzms;
    }
    
    public String getProper_zcgs() {
        return this.proper_zcgs;
    }
    
    public String getProper_zcjz() {
        return this.proper_zcjz;
    }
    
    public String getProper_zcjzzb() {
        return this.proper_zcjzzb;
    }
    
    public String getProper_zclb() {
        return this.proper_zclb;
    }
    
    public String getProper_zclbms() {
        return this.proper_zclbms;
    }
    
    public String getProper_zcljzj() {
        return this.proper_zcljzj;
    }
    
    public String getProper_zcly() {
        return this.proper_zcly;
    }
    
    public String getProper_zcmc() {
        return this.proper_zcmc;
    }
    
    public String getProper_zcqyrq() {
        return this.proper_zcqyrq;
    }
    
    public String getProper_zcsl() {
        return this.proper_zcsl;
    }
    
    public String getProper_zcxh() {
        return this.proper_zcxh;
    }
    
    public String getProper_zczb() {
        return this.proper_zczb;
    }
    
    public String getProper_zczyglbm() {
        return this.proper_zczyglbm;
    }
    
    public String getProper_zjfyzh() {
        return this.proper_zjfyzh;
    }
    
    public String getProper_zjfyzhms() {
        return this.proper_zjfyzhms;
    }
    
    public String getProper_zjzt() {
        return this.proper_zjzt;
    }
    
    public String getProper_zylx() {
        return this.proper_zylx;
    }
    
    public String getProper_zysx() {
        return this.proper_zysx;
    }
    
    public String getProper_zzs() {
        return this.proper_zzs;
    }
    
    public Integer getPropertyId() {
        return this.propertyId;
    }
    
    public String getRfid_UID() {
        return this.rfid_UID;
    }
    
    public HighFrequencySwitchingPowerSupplyInfoBean getSwitching() {
        return this.switching;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setOcc(final EquipmentInfoBean occ) {
        this.occ = occ;
    }
    
    public void setOdf(final EquipmentInfoBean odf) {
        this.odf = odf;
    }
    
    public void setProper_ablftrq(final String proper_ablftrq) {
        this.proper_ablftrq = proper_ablftrq;
    }
    
    public void setProper_cqpzh(final String proper_cqpzh) {
        this.proper_cqpzh = proper_cqpzh;
    }
    
    public void setProper_cscylx(final String proper_cscylx) {
        this.proper_cscylx = proper_cscylx;
    }
    
    public void setProper_fjxx1(final String proper_fjxx1) {
        this.proper_fjxx1 = proper_fjxx1;
    }
    
    public void setProper_fjxx2(final String proper_fjxx2) {
        this.proper_fjxx2 = proper_fjxx2;
    }
    
    public void setProper_fjxx3(final String proper_fjxx3) {
        this.proper_fjxx3 = proper_fjxx3;
    }
    
    public void setProper_fssbjfj(final String proper_fssbjfj) {
        this.proper_fssbjfj = proper_fssbjfj;
    }
    
    public void setProper_fzjldw(final String proper_fzjldw) {
        this.proper_fzjldw = proper_fzjldw;
    }
    
    public void setProper_fzsl(final String proper_fzsl) {
        this.proper_fzsl = proper_fzsl;
    }
    
    public void setProper_gdzcljzjzh(final String proper_gdzcljzjzh) {
        this.proper_gdzcljzjzh = proper_gdzcljzjzh;
    }
    
    public void setProper_gdzcljzjzhms(final String proper_gdzcljzjzhms) {
        this.proper_gdzcljzjzhms = proper_gdzcljzjzhms;
    }
    
    public void setProper_gdzcyzzh(final String proper_gdzcyzzh) {
        this.proper_gdzcyzzh = proper_gdzcyzzh;
    }
    
    public void setProper_gdzcyzzhms(final String proper_gdzcyzzhms) {
        this.proper_gdzcyzzhms = proper_gdzcyzzhms;
    }
    
    public void setProper_jldw(final String proper_jldw) {
        this.proper_jldw = proper_jldw;
    }
    
    public void setProper_krgzxm(final String proper_krgzxm) {
        this.proper_krgzxm = proper_krgzxm;
    }
    
    public void setProper_krgzxmms(final String proper_krgzxmms) {
        this.proper_krgzxmms = proper_krgzxmms;
    }
    
    public void setProper_pdjg(final String proper_pdjg) {
        this.proper_pdjg = proper_pdjg;
    }
    
    public void setProper_pghsksyyf(final String proper_pghsksyyf) {
        this.proper_pghsksyyf = proper_pghsksyyf;
    }
    
    public void setProper_pgjz(final String proper_pgjz) {
        this.proper_pgjz = proper_pgjz;
    }
    
    public void setProper_qcgcxmbh(final String proper_qcgcxmbh) {
        this.proper_qcgcxmbh = proper_qcgcxmbh;
    }
    
    public void setProper_qczckpbh(final String proper_qczckpbh) {
        this.proper_qczckpbh = proper_qczckpbh;
    }
    
    public void setProper_sbbh(final String proper_sbbh) {
        this.proper_sbbh = proper_sbbh;
    }
    
    public void setProper_sfcf(final String proper_sfcf) {
        this.proper_sfcf = proper_sfcf;
    }
    
    public void setProper_ssqy(final String proper_ssqy) {
        this.proper_ssqy = proper_ssqy;
    }
    
    public void setProper_ssqyms(final String proper_ssqyms) {
        this.proper_ssqyms = proper_ssqyms;
    }
    
    public void setProper_synx(final String proper_synx) {
        this.proper_synx = proper_synx;
    }
    
    public void setProper_syys(final String proper_syys) {
        this.proper_syys = proper_syys;
    }
    
    public void setProper_syzt(final String proper_syzt) {
        this.proper_syzt = proper_syzt;
    }
    
    public void setProper_syztms(final String proper_syztms) {
        this.proper_syztms = proper_syztms;
    }
    
    public void setProper_szdd(final String proper_szdd) {
        this.proper_szdd = proper_szdd;
    }
    
    public void setProper_szddms(final String proper_szddms) {
        this.proper_szddms = proper_szddms;
    }
    
    public void setProper_xmbh(final String proper_xmbh) {
        this.proper_xmbh = proper_xmbh;
    }
    
    public void setProper_xmbhms(final String proper_xmbhms) {
        this.proper_xmbhms = proper_xmbhms;
    }
    
    public void setProper_ygbh(final String proper_ygbh) {
        this.proper_ygbh = proper_ygbh;
    }
    
    public void setProper_ygxm(final String proper_ygxm) {
        this.proper_ygxm = proper_ygxm;
    }
    
    public void setProper_zcbh(final String proper_zcbh) {
        this.proper_zcbh = proper_zcbh;
    }
    
    public void setProper_zcbnzj(final String proper_zcbnzj) {
        this.proper_zcbnzj = proper_zcbnzj;
    }
    
    public void setProper_zcbqh(final String proper_zcbqh) {
        this.proper_zcbqh = proper_zcbqh;
    }
    
    public void setProper_zcbqzj(final String proper_zcbqzj) {
        this.proper_zcbqzj = proper_zcbqzj;
    }
    
    public void setProper_zccb(final String proper_zccb) {
        this.proper_zccb = proper_zccb;
    }
    
    public void setProper_zccjrq(final String proper_zccjrq) {
        this.proper_zccjrq = proper_zccjrq;
    }
    
    public void setProper_zccz(final String proper_zccz) {
        this.proper_zccz = proper_zccz;
    }
    
    public void setProper_zcgjz(final String proper_zcgjz) {
        this.proper_zcgjz = proper_zcgjz;
    }
    
    public void setProper_zcgjzms(final String proper_zcgjzms) {
        this.proper_zcgjzms = proper_zcgjzms;
    }
    
    public void setProper_zcgs(final String proper_zcgs) {
        this.proper_zcgs = proper_zcgs;
    }
    
    public void setProper_zcjz(final String proper_zcjz) {
        this.proper_zcjz = proper_zcjz;
    }
    
    public void setProper_zcjzzb(final String proper_zcjzzb) {
        this.proper_zcjzzb = proper_zcjzzb;
    }
    
    public void setProper_zclb(final String proper_zclb) {
        this.proper_zclb = proper_zclb;
    }
    
    public void setProper_zclbms(final String proper_zclbms) {
        this.proper_zclbms = proper_zclbms;
    }
    
    public void setProper_zcljzj(final String proper_zcljzj) {
        this.proper_zcljzj = proper_zcljzj;
    }
    
    public void setProper_zcly(final String proper_zcly) {
        this.proper_zcly = proper_zcly;
    }
    
    public void setProper_zcmc(final String proper_zcmc) {
        this.proper_zcmc = proper_zcmc;
    }
    
    public void setProper_zcqyrq(final String proper_zcqyrq) {
        this.proper_zcqyrq = proper_zcqyrq;
    }
    
    public void setProper_zcsl(final String proper_zcsl) {
        this.proper_zcsl = proper_zcsl;
    }
    
    public void setProper_zcxh(final String proper_zcxh) {
        this.proper_zcxh = proper_zcxh;
    }
    
    public void setProper_zczb(final String proper_zczb) {
        this.proper_zczb = proper_zczb;
    }
    
    public void setProper_zczyglbm(final String proper_zczyglbm) {
        this.proper_zczyglbm = proper_zczyglbm;
    }
    
    public void setProper_zjfyzh(final String proper_zjfyzh) {
        this.proper_zjfyzh = proper_zjfyzh;
    }
    
    public void setProper_zjfyzhms(final String proper_zjfyzhms) {
        this.proper_zjfyzhms = proper_zjfyzhms;
    }
    
    public void setProper_zjzt(final String proper_zjzt) {
        this.proper_zjzt = proper_zjzt;
    }
    
    public void setProper_zylx(final String proper_zylx) {
        this.proper_zylx = proper_zylx;
    }
    
    public void setProper_zysx(final String proper_zysx) {
        this.proper_zysx = proper_zysx;
    }
    
    public void setProper_zzs(final String proper_zzs) {
        this.proper_zzs = proper_zzs;
    }
    
    public void setPropertyId(final Integer propertyId) {
        this.propertyId = propertyId;
    }
    
    public void setRfid_UID(final String rfid_UID) {
        this.rfid_UID = rfid_UID;
    }
    
    public void setSwitching(final HighFrequencySwitchingPowerSupplyInfoBean switching) {
        this.switching = switching;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
}
