package com.inspur.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ScrollListViewUtil {
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		/*ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			if(listItem==null){
				totalHeight += 0;
			}else{
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
			}
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		listView.setLayoutParams(params);*/
	}
}