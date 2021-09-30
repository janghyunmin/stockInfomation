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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.BaseDAO;
import com.osj.stockinfomation.DAO.VersionDAO;
import com.osj.stockinfomation.Http.Util_osj;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.databinding.SplashActivityBinding;
import com.osj.stockinfomation.util.LogUtil;
import com.osj.stockinfomation.util.MessageEvent;
import com.osj.stockinfomation.util.PreferencesUtil;
import com.osj.stockinfomation.util.PushPayloadDAO;
import com.osj.stockinfomation.util.StatusBarCustom;

import org.greenrobot.eventbus.EventBus;

public class SplashActivity extends BaseActivity {
    SplashActivityBinding binding;
    Context mContext;
    private Window mWindow;
    CustomerMainPresenter customerMainPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWindow = getWindow();
        mWindow.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity);
        mContext = this;

        /** jhm 2021-09-15 오후 5:34
         * 애니메이션 및 statusbar 커스텀 셋팅
         ***/
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.intro_animation);
        binding.logo.startAnimation(animation);
        // Status bar custom
        StatusBarCustom.setStatusBarColor(this, StatusBarCustom.StatusBarColorType.BLACK_STATUS_BAR);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getVersion(); // 앱 버전 체크
            }
        },3500);
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
                        hideProgress();
                        goInfo();
                    } else {
                        showCustomAlert(SplashActivity.this, "알림", "최신버젼이 있습니다. 마켓으로 이동 하시겠습니까?", false, R.drawable.img_alert_error, 2, "아니오", "확인"
                                , new OnCancelListener() {
                            @Override
                            public void onClick() {
                                hideProgress();
                                goInfo();
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

    public void goInfo(){

        customerMainPresenter.getUser(SplashActivity.this, Util_osj.getAndroidId(mContext),new CommonCallback.SingleObjectCallback<BaseDAO>() {
            @Override
            public void onSuccess(BaseDAO result) {
                LogUtil.logE("resulctCode" + result.getResultCode());

                switch (result.getResultCode()){
                    case "99":
                        Intent go_insert = new Intent(getApplicationContext(), InfoInsertActivity.class);
                        startActivity(go_insert);
                        finish();
                        break;
                    case "00":
                        hideProgress();
                        LogUtil.logE("이미 통과");
                        Intent go_main = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(go_main);
                        finish();
                        break;
                }
            }

            @Override
            public void onFailed(String fault) {

            }
        });

    }
}
