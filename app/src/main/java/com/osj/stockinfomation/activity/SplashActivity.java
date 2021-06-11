package com.osj.stockinfomation.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.osj.stockinfomation.BuildConfig;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.BaseDAO;
import com.osj.stockinfomation.DAO.VersionDAO;
import com.osj.stockinfomation.Http.Util_osj;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.databinding.SplashActivityBinding;
import com.osj.stockinfomation.util.PreferencesUtil;

public class SplashActivity extends BaseActivity {
    SplashActivityBinding binding;
    Context mContext;
    private Window mWindow;
    CustomerMainPresenter customerMainPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);

        mWindow = getWindow();
        mWindow.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity);
        mContext = this;
        setProgress(binding.progress);


        if(PreferencesUtil.getString(mContext, PreferencesUtil.PreferenceKey.PREF_PERMISSION_INFO, "N").equals("N")){
            showPermisstionCustomAlert(this, new OnCancelListener() {
                @Override
                public void onClick() {
                    finish();
                }
            }, new OnClickListener() {
                @Override
                public void onClick() {
                    PreferencesUtil.putString(mContext, PreferencesUtil.PreferenceKey.PREF_PERMISSION_INFO, "Y");
                    getVersion();
                }
            });
        } else {
            getVersion();
        }
    }

    private void getVersion(){
        showProgress();
        customerMainPresenter = new CustomerMainPresenter();
        customerMainPresenter.getVersion(new CommonCallback.SingleObjectCallback<VersionDAO>() {
            @Override
            public void onSuccess(VersionDAO result) {
                String versionName = BuildConfig.VERSION_NAME;
                String newVersionName = "";
                if(result.getResultCode().equals("00")){
                    versionName = versionName.replace(".", "");
                    newVersionName = result.getAppVersion().replace(".", "");
                    if(Integer.parseInt(versionName) >= Integer.parseInt(newVersionName)){
                        getPushYn();
                    } else {
                        showCustomAlert(SplashActivity.this, "알림", "최신버젼이 있습니다. 마켓으로 이동 하시겠습니까?", false, R.drawable.img_alert_error, 2, "아니오", "확인"
                                , new OnCancelListener() {
                            @Override
                            public void onClick() {
                                hideProgress();
                                getPushYn();
                            }
                        }, new OnClickListener() {
                            @Override
                            public void onClick() {
                                hideProgress();
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
                showCustomAlert(SplashActivity.this, "오류", fault, true, R.drawable.img_alert_error, 1, "", "재시도", null, new OnClickListener() {
                    @Override
                    public void onClick() {
                        getVersion();
                    }
                });
            }
        });
    }

    /**
     * setPushData
     * push 데이터 서버 전송
     */
    private void getPushYn(){
        getToken();
    }

    /**
     * PUSH 토큰 획득
     */
    private void getToken(){
        showProgress();
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()){
                    hideProgress();
                    goMain();
                } else {
                    hideProgress();
                    String token = task.getResult().getToken();
                    Log.d("osj", "token :: " + token);
                    PreferencesUtil.putString(mContext, PreferencesUtil.PreferenceKey.PREF_TOKEN, token);
//                    goMain();
                    showProgress();
                    customerMainPresenter.setUser(SplashActivity.this, Build.MODEL +  " | " + Build.VERSION.SDK_INT, token , new CommonCallback.SingleObjectCallback<BaseDAO>() {
                        @Override
                        public void onSuccess(BaseDAO result) {
                            hideProgress();
                            goMain();
                        }

                        @Override
                        public void onFailed(String fault) {
                            hideProgress();
                            showCustomAlert(SplashActivity.this, "", fault, true, R.drawable.img_alert_error, 1, "", "재시도", null, new OnClickListener() {
                                @Override
                                public void onClick() {
                                    getToken();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private void goMain(){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 100);


    }
}
