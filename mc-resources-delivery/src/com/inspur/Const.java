package com.inspur;

import java.io.File;

import android.os.Environment;

public class Const
{
	// 分页时每页的个数
	public static final int pagesize = 5;
	// 增加光终端盒
	public static final String tag_onTerminalBoxAdded = "onTerminalBoxAdded";
	// 删除光终端盒
	public static final String tag_onTerminalBoxDeleted = "onTerminalBoxDeleted";
	// 修改光终端盒
	public static final String tag_onTerminalBoxUpdated = "onTerminalBoxUpdated";
	// 选中前一节点的电杆
	public static final String tag_onPreviousPoleSelected = "onPreviousPoleSelected";
	// 选中前一节点的标石
	public static final String tag_onPreviousStoneSelected = "onPreviousStoneSelected";

	public static final String BASE_DIR = Environment.getExternalStorageDirectory().getPath()+File.separator+"yc";

	public static final String LOG_PATH = BASE_DIR+File.separator+"log" +File.separator;


}
