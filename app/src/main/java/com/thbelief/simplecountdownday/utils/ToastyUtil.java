package com.thbelief.simplecountdownday.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.thbelief.simplecountdownday.application.Application;

import es.dmoral.toasty.Toasty;

/**
 * Author:thbelief
 * Date:2022/1/8 12:59 下午
 * Description:ToastyUtil 工具类
 *
 * @author thbelief
 */
public class ToastyUtil {
    private static Context mContext = Application.getInstance();

    public static void error(String info) {
        Toasty.error(mContext, info).show();
    }

    public static void info(String info) {
        Toasty.info(mContext, info).show();
    }

    public static void warning(String info) {
        Toasty.warning(mContext, info).show();
    }

    public static void normal(String info) {
        Toasty.normal(mContext, info).show();
    }

    public static void normalAndIcon(String info, Drawable icon) {
        Toasty.normal(mContext, info, icon).show();
    }
}
