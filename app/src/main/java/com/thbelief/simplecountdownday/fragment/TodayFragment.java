package com.thbelief.simplecountdownday.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thbelief.simplecountdownday.R;

import butterknife.ButterKnife;

/**
 * Author:thbelief
 * Date:2022/1/9 10:55 上午
 * Description:
 *
 * @author thbelief
 */
public class TodayFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }
}