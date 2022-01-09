package com.thbelief.simplecountdownday.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.thbelief.simplecountdownday.application.Application;

/**
 * Author:thbelief
 * Date:2022/1/9 10:59 上午
 * Description: SharedPreference的工具类主要用于存储设置信息
 *
 * @author thbelief
 */
public class SharedPreferenceHelper {
    private static final String KEY_FILE = "SharedPreferenceHelper";
    private static volatile SharedPreferences mSpf = Application.getInstance().getSharedPreferences(KEY_FILE, Context.MODE_PRIVATE);
    private static final String KEY_WEEK_START_FROM_SUNDAY = "week_start_from_sunday";
    private static final String KEY_LIFE_PROGRESS = "life_progress";
    private static final String KEY_VIBRATION = "vibration";
    private static final String KEY_BIRTH_YEAR = "birth_year";
    private static final String KEY_PREDICT_YEAR = "predict_year";

    public static boolean isWeekStartFromSunday() {
        return mSpf.getBoolean(KEY_WEEK_START_FROM_SUNDAY, false);
    }

    public static void setWeekStartFromSunday(boolean value) {
        SharedPreferences.Editor editor = mSpf.edit();
        editor.putBoolean(KEY_WEEK_START_FROM_SUNDAY, value);
        editor.apply();
    }

    public static boolean isLifeProgress() {
        return mSpf.getBoolean(KEY_LIFE_PROGRESS, false);
    }

    public static void setLifeProgress(boolean value) {
        SharedPreferences.Editor editor = mSpf.edit();
        editor.putBoolean(KEY_LIFE_PROGRESS, value);
        editor.apply();
    }

    public static boolean isVibration() {
        return mSpf.getBoolean(KEY_VIBRATION, true);
    }

    public static void setVibration(boolean value) {
        SharedPreferences.Editor editor = mSpf.edit();
        editor.putBoolean(KEY_VIBRATION, value);
        editor.apply();
    }

    public static int getBirthYear() {
        return mSpf.getInt(KEY_BIRTH_YEAR, 2000);
    }

    public static void setBirthYear(int value) {
        SharedPreferences.Editor editor = mSpf.edit();
        editor.putInt(KEY_BIRTH_YEAR, value);
        editor.apply();
    }

    public static int getPredictYear() {
        return mSpf.getInt(KEY_PREDICT_YEAR, 100);
    }

    public static void setPredictYear(int value) {
        SharedPreferences.Editor editor = mSpf.edit();
        editor.putInt(KEY_PREDICT_YEAR, value);
        editor.apply();
    }
}
