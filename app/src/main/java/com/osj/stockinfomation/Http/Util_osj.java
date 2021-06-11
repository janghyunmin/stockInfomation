package com.osj.stockinfomation.Http;

import android.app.AlertDialog;
import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;

public class Util_osj {
    public static String getAndroidId(Context mContext){
        return android.provider.Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

}
