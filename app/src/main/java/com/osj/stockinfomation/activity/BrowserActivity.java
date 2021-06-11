package com.osj.stockinfomation.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.databinding.BrowserActivityBinding;
import com.osj.stockinfomation.util.WebViewSetter;

public class BrowserActivity extends BaseActivity {

    BrowserActivityBinding binding;
    String openType;
    String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_activity);

        initView();
        initClickEvent();
    }

    private void initView(){
        binding = DataBindingUtil.setContentView(this, R.layout.browser_activity);
        WebViewSetter.optimize(binding.wvBrowser, this);
        data = (String) getIntent().getSerializableExtra("data");
        openType = (String) getIntent().getSerializableExtra("openType");

        if(openType.equals("search"))
            binding.wvBrowser.loadUrl("https://finance.naver.com/item/main.nhn?code=" + data);
        else
            binding.wvBrowser.loadUrl("https://search.naver.com/search.naver?&query=" + data);
    }

    private void initClickEvent(){

    }
}
