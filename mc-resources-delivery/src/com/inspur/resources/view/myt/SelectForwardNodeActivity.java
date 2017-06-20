package com.inspur.resources.view.myt;

import com.inspur.resources.base.BaseActivity;
import com.inspur.resources.bean.ResourceNode;
import com.inspur.resources.bean.ResourceOrderInfoBean;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;

public class SelectForwardNodeActivity extends BaseActivity {//implements onNodeSelectedCallBack{

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setTitle("选择转派资源");
	//	getSupportFragmentManager().beginTransaction().add(new NodeListFragment(), "").commit();
	}
	
	/*@Override
	public void onNodeSelected(SparseArray<ResourceNode> nodeMap) {
		setResult(resultCode, data);
		finish();
	}*/
	
	

}
