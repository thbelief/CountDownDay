package com.thbelief.simplecountdownday.application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.jaredrummler.cyanea.Cyanea;
import com.thbelief.simplecountdownday.database.greenDao.db.DaoMaster;
import com.thbelief.simplecountdownday.database.greenDao.db.DaoSession;

/**
 * Application
 */
public class Application extends android.app.Application {
    private static Context mContext;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Cyanea.init(this, mContext.getResources());
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "thbelief.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static Context getInstance() {
        return mContext;
    }
}
