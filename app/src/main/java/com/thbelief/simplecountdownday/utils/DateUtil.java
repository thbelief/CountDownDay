package com.thbelief.simplecountdownday.utils;

import com.thbelief.simplecountdownday.storage.SharedPreferenceHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * Author:thbelief
 * Date:2022/1/8 5:08 下午
 * Description:
 *
 * @author thbelief
 */
public class DateUtil {
    private static Calendar mCalendar;

    static {
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());
    }

    public static int getProportionDay() {
        return (int) Math.rint((((double) mCalendar.get(Calendar.HOUR_OF_DAY)) / 24.00) * 100.00);
    }

    public static int getProportionWeek() {
        int curDay = SharedPreferenceHelper.isWeekStartFromSunday() ? mCalendar.get(Calendar.DAY_OF_WEEK) : mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        return (int) Math.rint((((double) curDay) / 7.00) * 100.00);
    }

    public static int getProportionMonth() {
        return (int) Math.rint((((double) mCalendar.get(Calendar.DAY_OF_MONTH)) / ((double) getMonthDays())) * 100.00);
    }

    public static int getProportionYear() {
        return (int) Math.rint((((double) mCalendar.get(Calendar.DAY_OF_YEAR)) / ((double) getYearDays())) * 100.00);
    }

    public static int getProgressLife() {
        if ((SharedPreferenceHelper.getBirthYear() + SharedPreferenceHelper.getPredictYear()) <= mCalendar.get(Calendar.YEAR)) {
            return 100;
        }
        return (int) Math.rint(((double) (mCalendar.get(Calendar.YEAR) - SharedPreferenceHelper.getBirthYear()) / (double) SharedPreferenceHelper.getPredictYear()) * 100.00);
    }

    //判断闰年
    public static boolean isLeap(int year) {
        return ((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0);
    }

    //返回当年天数
    public static int getYearDays() {
        return isLeap(mCalendar.get(Calendar.YEAR)) ? 366 : 365;
    }

    //返回当月天数
    public static int getMonthDays() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int days;
        int febDay = 28;
        if (isLeap(year)) {
            febDay = 29;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = febDay;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }


}


