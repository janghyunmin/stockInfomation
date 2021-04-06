package com.osj.stockinfomation.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.osj.stockinfomation.activity.MainActivity;

public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static volatile BaseApplication instance = null;

    private boolean mMainActivityLive = false;

    private boolean mAppForground = false;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // Activity Lifecycle
        registerActivityLifecycleCallbacks(this);

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity instanceof MainActivity) {
            mMainActivityLive = true;
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {}
    @Override
    public void onActivityResumed(Activity activity) {
        mAppForground = true;
    }
    @Override
    public void onActivityPaused(Activity activity) {
        mAppForground = false;
    }
    @Override
    public void onActivityStopped(Activity activity) {}
    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof MainActivity) {
            mMainActivityLive = false;
        }
    }

    public boolean isMainActivityLive() {
        return mMainActivityLive;
    }
}
