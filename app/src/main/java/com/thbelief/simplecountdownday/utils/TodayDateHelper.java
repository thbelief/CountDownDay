package com.thbelief.simplecountdownday.utils;

import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thbelief.simplecountdownday.interfaces.IDataLoad;
import com.thbelief.simplecountdownday.model.TodayModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:thbelief
 * Date:2022/1/10 9:44 下午
 * Description:
 *
 * @author thbelief
 */
public class TodayDateHelper {
    private static final String TAG = "TodayDateHelper";
    private static final String URL_GET_TODAY = "https://api.oick.cn/lishi/api.php";
    private static TodayModel mModel;
    private static boolean mIsLoaded = false;

    private TodayDateHelper() {

    }

    public static TodayModel getData() {
        return mModel;
    }

    public static void requestData(IDataLoad iDataLoad) {
        if (mIsLoaded) {
            iDataLoad.loaded();
            return;
        }
        Log.d(TAG, "request");
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .get()
                .url(URL_GET_TODAY)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "failure");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();
                mModel = TodayModel.objectFromData(res);
                mIsLoaded = true;
                iDataLoad.loaded();
                Log.d(TAG, "success");
            }
        });
    }

    public static boolean isLoadedData() {
        return mIsLoaded;
    }
}
