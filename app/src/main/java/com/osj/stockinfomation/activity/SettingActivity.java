package com.osj.stockinfomation.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.osj.stockinfomation.Adapter.VpAdapterMain;
import com.osj.stockinfomation.BuildConfig;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAOList;
import com.osj.stockinfomation.DAO.VersionDAO;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.databinding.ActivityMainBinding;
import com.osj.stockinfomation.databinding.ActivitySettingBinding;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.WebViewSetter;

import java.net.URISyntaxException;

public class SettingActivity extends BaseActivity {

    ActivitySettingBinding binding;
    CustomerMainPresenter mPresenter;

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

        setProgress(binding.progress);
        mPresenter = new CustomerMainPresenter();
        getVersion();
    }

    private void getVersion(){
        showProgress();
        mPresenter.getVersion(new CommonCallback.SingleObjectCallback<VersionDAO>() {
            @Override
            public void onSuccess(VersionDAO result) {
                hideProgress();
                String versionName = BuildConfig.VERSION_NAME;
                String newVersionName = "";
                if(result.getResultCode().equals("00")){
                    versionName = versionName.replace(".", "");
                    newVersionName = result.getAppVersion().replace(".", "");
                    if(Integer.parseInt(versionName) >= Integer.parseInt(newVersionName)){
                        binding.txtSettingVersion.setText("version : " + BuildConfig.VERSION_NAME + " (최신버젼 입니다.)");
                    } else {
                        binding.txtSettingVersion.setText("version : " + BuildConfig.VERSION_NAME + " (새로운 버젼이 나왔습니다.)");
                        binding.txtSettingVersion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailed(String fault) {
                hideProgress();
                binding.txtSettingVersion.setText("version : " + BuildConfig.VERSION_NAME);
                showCustomAlert(SettingActivity.this, "오류", fault, false, R.drawable.img_alert_error, 1, "", "재시도", null, new OnClickListener() {
                    @Override
                    public void onClick() {
                        getVersion();
                    }
                });
            }
        });
    }

    private void initViewPager() {

        try {

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void initClickEvent(){
        binding.txtSettingNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, NickNameSettingActivity.class);
                startActivity(intent);
            }
        });

        binding.txtSettingWebview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showModalView("/bbs/content.php?co_id=provision");
            }
        });

        binding.txtSettingWebview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showModalView("/bbs/content.php?co_id=privacy");
            }
        });

        binding.txtSettingWebview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showModalView("/bbs/content.php?co_id=message");
            }
        });

        binding.txtSettingAlram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, AlramSettingActivity.class);
                startActivity(intent);
            }
        });

        binding.ivSettingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void showModalView(String url){
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_setting_webview, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        WebView webview = (WebView)alertDialog.findViewById(R.id.dialog_setting_webview);
        WebViewSetter.optimize(webview, this);
        webview.loadUrl(C.BASE_URL + url);

        ((ImageView)alertDialog.findViewById(R.id.iv_setting_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}