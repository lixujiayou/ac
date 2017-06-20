package com.inspur.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.inspur.easyresources.R;

/**
 * Created by lixu on 2017/5/19.
 */

public class TestActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_main);
    }
}
