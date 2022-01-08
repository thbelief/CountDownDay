package com.thbelief.simplecountdownday.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.thbelief.simplecountdownday.application.Application;

/**
 * Author:thbelief
 * Date:2022/1/8 4:45 下午
 * Description:
 *
 * @author thbelief
 */
public class ResourceHelper {
    private static Context mContext = Application.getInstance();

    public static String getString(int id) {
        return mContext.getResources().getString(id);
    }

    public static int getColor(int id) {
        return mContext.getResources().getColor(id);
    }

    public static Drawable getDrawable(int id) {
        return mContext.getResources().getDrawable(id);
    }

    public static float getDimen(int id){
        return mContext.getResources().getDimensionPixelOffset(id);
    }
}
