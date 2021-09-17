package com.osj.stockinfomation.util;

import android.app.Activity;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.osj.stockinfomation.R;

public class StatusBarCustom {

    public enum StatusBarColorType {
        BLACK_STATUS_BAR( R.color.white );


        private int backgroundColorId;
        StatusBarColorType(int backgroundColorId){
            this.backgroundColorId = backgroundColorId;
        }
        public int getBackgroundColorId() {
            return backgroundColorId;
        }
    }



    public static void setStatusBarColor(Activity activity, StatusBarColorType colorType) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, colorType.getBackgroundColorId()));
        }
    }


}
