package com.thbelief.simplecountdownday.application;

import android.content.Context;

import com.jaredrummler.cyanea.Cyanea;

/**
 * Application
 */
public class Application extends android.app.Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Cyanea.init(this, mContext.getResources());
    }

    public static Context getInstance() {
        return mContext;
    }
}
