package com.thbelief.simplecountdownday.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:thbelief
 * Date:2022/1/10 10:33 下午
 * Description:
 *
 * @author thbelief
 */
public class ResultDTO {
    private String date;
    private String title;

    public static ResultDTO objectFromData(String str) {

        return new Gson().fromJson(str, ResultDTO.class);
    }

    public static ResultDTO objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), ResultDTO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<ResultDTO> arrayResultDTOFromData(String str) {

        Type listType = new TypeToken<ArrayList<ResultDTO>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<ResultDTO> arrayResultDTOFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<ResultDTO>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
