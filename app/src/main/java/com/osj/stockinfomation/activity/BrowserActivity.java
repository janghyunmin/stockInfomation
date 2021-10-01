package com.osj.stockinfomation.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.databinding.BrowserActivityBinding;
import com.osj.stockinfomation.util.LogUtil;
import com.osj.stockinfomation.util.WebViewSetter;

public class BrowserActivity extends BaseActivity {

    BrowserActivityBinding binding;
    String openType;
    String data;
    private WebSettings mWebSettings; //웹뷰세팅

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_activity);

        initView();
        initClickEvent();
    }

    private void initView(){
        binding = DataBindingUtil.setContentView(this, R.layout.browser_activity);
        //WebViewSetter.optimize(binding.wvBrowser, this);
        data = (String) getIntent().getSerializableExtra("data");

        binding.wvBrowser.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebSettings = binding.wvBrowser.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부

        binding.wvBrowser.loadUrl("https://finance.naver.com/item/main.nhn?code=" + data);
        LogUtil.logE("url : " + "https://finance.naver.com/item/main.nhn?code=" + data);
    }

    private void initClickEvent(){

    }
}
