package com.osj.stockinfomation.activity;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.osj.stockinfomation.Adapter.VpAdapterMain;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.databinding.ActivityMainBinding;
import com.osj.stockinfomation.databinding.ActivitySettingBinding;
import com.osj.stockinfomation.util.ErrorController;

public class SettingActivity extends BaseActivity {

    ActivitySettingBinding binding;
    private VpAdapterMain adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        initViewPager();
        initClickEvent();
    }

    private void initView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

    }

    private void initViewPager() {

        try {

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void initClickEvent(){
    }


















}