package com.thbelief.simplecountdownday.utils;

import static android.content.Context.VIBRATOR_SERVICE;

import android.os.Vibrator;

import com.thbelief.simplecountdownday.application.Application;
import com.thbelief.simplecountdownday.storage.SharedPreferenceHelper;

/**
 * Author:thbelief
 * Date:2022/1/8 1:26 下午
 * Description:振动
 *
 * @author thbelief
 */
public class VibrationHelper {
    private static volatile Vibrator mInstance;
    private static final int TIME_VIBRATION_CLICK = 10;

    public static Vibrator getInstance() {
        if (mInstance != null) {
            return mInstance;
        }
        synchronized (VibrationHelper.class) {
            if (mInstance == null) {
                mInstance = (Vibrator) Application.getInstance().getSystemService(VIBRATOR_SERVICE);
            }
        }
        return mInstance;
    }

    public static void clickVibration() {
        if (!SharedPreferenceHelper.isVibration()) {
            return;
        }
        getInstance().vibrate(TIME_VIBRATION_CLICK);
    }
}
