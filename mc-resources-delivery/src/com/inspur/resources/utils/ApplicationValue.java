// 

// 

package com.inspur.resources.utils;

import java.util.ArrayList;
import java.util.List;

import com.inspur.resources.bean.BuriedInfoObj;
import com.inspur.resources.bean.BuriedPartObj;
import com.inspur.resources.bean.CableSectionInfoBean;
import com.inspur.resources.bean.EquipmentInfoBean;
import com.inspur.resources.bean.FiberBoxInfoBean;
import com.inspur.resources.bean.GeneratorInfoBean;
import com.inspur.resources.bean.HighFrequencySwitchingPowerSupplyInfoBean;
import com.inspur.resources.bean.JointInfoBean;
import com.inspur.resources.bean.LedupInfoBean;
import com.inspur.resources.bean.NewVisionInfoBean;
import com.inspur.resources.bean.OpticalInfoBean;
import com.inspur.resources.bean.OpticalTerminalBean;
import com.inspur.resources.bean.PipeInfoBean;
import com.inspur.resources.bean.PipeSegmentInfoBean;
import com.inspur.resources.bean.PoleInfoBean;
import com.inspur.resources.bean.PolelineInfoBean;
import com.inspur.resources.bean.PolelineSegmentInfoBean;
import com.inspur.resources.bean.PropertyInfoBean;
import com.inspur.resources.bean.StationBaseInfoBean;
import com.inspur.resources.bean.StoneInfoBean;
import com.inspur.resources.bean.SupportInfoBean;
import com.inspur.resources.bean.SuspensionWireInfoBean;
import com.inspur.resources.bean.SuspensionWireSegInfoBean;
import com.inspur.resources.bean.TubeInfoBean;
import com.inspur.resources.bean.UserInfoBean;
import com.inspur.resources.bean.WellInfoBean;

public class ApplicationValue
{
    public static final String[] single_list = {"资源隐患", "综资数据", "管道故障"};
    public static final String GuangJiao = "光交交割";

    public static String DECODE;
    public static String MY_BLUETOOTH_MAC;
    public static String UID;
    public static String addrFull;
    public static byte[] byte1;
    public static List<CableSectionInfoBean> cableSectionList;
    public static String dayinji_default_bluetooth_address;
    public static String dayinji_default_bluetooth_name;
    public static List<FiberBoxInfoBean> fiberBoxInfoBeanList;
    public static List<GeneratorInfoBean> generatorList;
    public static boolean isConnectedblue;
    public static boolean isOffLine;
    public static boolean isUpLoad;
    public static String jfSuoshuwhqu;
    public static List<JointInfoBean> jointList;
    public static double lat;
    public static List<LedupInfoBean> ledupList;
    public static boolean login_set_username_pwd_save;
    public static double lon;
    public static String mPassword;
    public static String mUser;
    public static NewVisionInfoBean newVisionInfoBean;
    public static String nowAddress;
    public static List<EquipmentInfoBean> occList;
    public static List<EquipmentInfoBean> odfList;
    public static List<OpticalInfoBean> opticalCableList;
    public static List<PipeSegmentInfoBean> pipeSegmentInfoBeanList;
    public static List<PipeInfoBean> pipelineInfoBeanList;
    public static List<PoleInfoBean> poleInfoBeanList;
    public static List<PolelineInfoBean> polelineInfoBeanList;
    public static List<PolelineSegmentInfoBean> polelineSegmentInfoBeanList;
    public static List<PropertyInfoBean> porpertyList;
    public static int q;
    public static String rfid_default_bluetooth_address;
    public static String rfid_default_bluetooth_name;
    public static List<StationBaseInfoBean> stationList;
    public static List<SupportInfoBean> supportInfoBeanList;
    public static List<SuspensionWireInfoBean> suspensionWireList;
    public static List<SuspensionWireSegInfoBean> suspensionWireSegList;
    public static List<HighFrequencySwitchingPowerSupplyInfoBean> switchingList;
    public static List<TubeInfoBean> tubeInfoBeanList;
    public static String url;
    public static List<UserInfoBean> userList;
    public static String webUrlUpImage;
    public static List<WellInfoBean> wellList;
    public static List<OpticalTerminalBean> opticalTerminalList;

    public static List<BuriedInfoObj> buriedInfoObjList = new ArrayList<BuriedInfoObj>();
    public static List<BuriedPartObj> buriedPartObjList = new ArrayList<BuriedPartObj>();
    public static List<StoneInfoBean> stoneInfoBeanList = new ArrayList<StoneInfoBean>();

    /**
     * 离线照片字段相关
     */
    public static final String PHOTOTABLENAME = "photos_";//全离线图片表名
    public static final String PHOTOTABLE_LAT = "latitude";//
    public static final String PHOTOTABLE_LON = "longitude";//经度
    public static final String PHOTOTABLE_ROUTEID = "route_id";//routeid
    public static final String PHOTOTABLE_USERID = "user_id";//userID
    public static final String PHOTOTABLE_PHOTOTYPE = "photo_type";//photo_type
    public static final String PHOTOTABLE_RESOURCETYPE = "resource_type";//resource_type
    public static final String PHOTOTABLE_POHOTO = "photo_base";//photo_string
    public static final String PHOTOTABLE_RELATEDID = "related_id";//related_id

    /**
     * 隐患离线相关
     */
    public static final String ERRO_ZYYH = "erro_zyyh";//资源隐患表
    public static final String ERRO_ZZSJ = "erro_zzsj";//综资数据表
    public static final String ERRO_GDGZ = "erro_gdgz";//管道故障表
    public static final String ERRO_ROUTEID = "erro_routeid";//路由id，唯一键
    public static final String ERRO_CONTENT = "erro_json";//json


    static {
        ApplicationValue.MY_BLUETOOTH_MAC = null;

       // ApplicationValue.url = "http://218.207.69.235:7014/InventoryManager/";//原IP

        //ApplicationValue.url = "http://10.72.56.239:8080/InventoryManager/";//济南测试
       // ApplicationValue.url = "http://10.110.2.84:8070/InventoryManager/";//济南外网
    //    ApplicationValue.url = "http://10.18.11.99:8080/InventoryManager/";//
        ApplicationValue.url = "http://218.207.69.235:7014/Inventory/";//河北测试


        ApplicationValue.login_set_username_pwd_save = false;
//        ApplicationValue.UID = null;
        ApplicationValue.mUser = null;
        ApplicationValue.mPassword = null;
        ApplicationValue.newVisionInfoBean = null;
        ApplicationValue.lat = 0.0;
        ApplicationValue.lon = 0.0;
        ApplicationValue.nowAddress = "";
        ApplicationValue.isOffLine = false;
        ApplicationValue.isConnectedblue = false;
        ApplicationValue.q = 0;
        ApplicationValue.byte1 = new byte[100];
        ApplicationValue.addrFull = "";
        ApplicationValue.jfSuoshuwhqu = "";
        ApplicationValue.isUpLoad = true;
        ApplicationValue.webUrlUpImage = "upload";
        ApplicationValue.rfid_default_bluetooth_name = null;
        ApplicationValue.rfid_default_bluetooth_address = null;
        ApplicationValue.dayinji_default_bluetooth_name = null;
        ApplicationValue.dayinji_default_bluetooth_address = null;
        ApplicationValue.DECODE = null;
        ApplicationValue.stationList = new ArrayList<StationBaseInfoBean>();
        ApplicationValue.generatorList = new ArrayList<GeneratorInfoBean>();
        ApplicationValue.switchingList = new ArrayList<HighFrequencySwitchingPowerSupplyInfoBean>();
        ApplicationValue.porpertyList = new ArrayList<PropertyInfoBean>();
        ApplicationValue.poleInfoBeanList = new ArrayList<PoleInfoBean>();
        ApplicationValue.polelineInfoBeanList = new ArrayList<PolelineInfoBean>();
        ApplicationValue.pipelineInfoBeanList = new ArrayList<PipeInfoBean>();
        ApplicationValue.pipeSegmentInfoBeanList = new ArrayList<PipeSegmentInfoBean>();
        ApplicationValue.polelineSegmentInfoBeanList = new ArrayList<PolelineSegmentInfoBean>();
        ApplicationValue.opticalCableList = new ArrayList<OpticalInfoBean>();
        ApplicationValue.jointList = new ArrayList<JointInfoBean>();
        ApplicationValue.cableSectionList = new ArrayList<CableSectionInfoBean>();
        ApplicationValue.suspensionWireList = new ArrayList<SuspensionWireInfoBean>();
        ApplicationValue.suspensionWireSegList = new ArrayList<SuspensionWireSegInfoBean>();
        ApplicationValue.wellList = new ArrayList<WellInfoBean>();
        ApplicationValue.userList = null;
        ApplicationValue.tubeInfoBeanList = new ArrayList<TubeInfoBean>();
        ApplicationValue.ledupList = new ArrayList<LedupInfoBean>();
        ApplicationValue.supportInfoBeanList = new ArrayList<SupportInfoBean>();
        ApplicationValue.odfList = new ArrayList<EquipmentInfoBean>();
        ApplicationValue.occList = new ArrayList<EquipmentInfoBean>();
        ApplicationValue.fiberBoxInfoBeanList = new ArrayList<FiberBoxInfoBean>();
        ApplicationValue.opticalTerminalList = new ArrayList<OpticalTerminalBean>();

        //http://218.207.69.235:7014/InventoryManager/pdaMainTask!getMyRoute.interface?jsonRequest={%22page%22:%22+0+%22,%22pageSize%22:%22+10+%22,%22status%22:%22+1+%22}&UID=ChoaTest
    }
}
