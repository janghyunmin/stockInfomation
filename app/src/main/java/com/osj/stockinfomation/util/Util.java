package com.osj.stockinfomation.util;

import android.content.Context;
import android.provider.Settings;

public class Util {

    public static String getAndroidId(Context mContext){
        return android.provider.Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
