package com.thbelief.simplecountdownday.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:thbelief
 * Date:2022/1/10 9:16 下午
 * Description:
 *
 * @author thbelief
 */

public class TodayModel implements Serializable {

    private String code;
    private String day;
    private List<ResultDTO> result;

    public static TodayModel objectFromData(String str) {

        return new Gson().fromJson(str, TodayModel.class);
    }

    public static TodayModel objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), TodayModel.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<TodayModel> arrayTodayModelFromData(String str) {

        Type listType = new TypeToken<ArrayList<TodayModel>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<TodayModel> arrayTodayModelFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<TodayModel>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<ResultDTO> getResult() {
        return result;
    }

    public void setResult(List<ResultDTO> result) {
        this.result = result;
    }
}
