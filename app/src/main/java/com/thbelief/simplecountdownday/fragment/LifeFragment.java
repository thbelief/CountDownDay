package com.thbelief.simplecountdownday.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.utils.DateUtil;
import com.thbelief.simplecountdownday.utils.ResourceHelper;

import app.futured.donut.DonutProgressView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:thbelief
 * Date:2022/1/8 1:51 下午
 * Description:
 *
 * @author thbelief
 */
public class LifeFragment extends BaseFragment {
    @BindView(R.id.day)
    LinearLayout mLayoutDay;
    @BindView(R.id.week)
    LinearLayout mLayoutWeek;
    @BindView(R.id.month)
    LinearLayout mLayoutMonth;
    @BindView(R.id.year)
    LinearLayout mLayoutYear;
    @BindView(R.id.day_donut)
    DonutProgressView mProgressDay;
    @BindView(R.id.week_donut)
    DonutProgressView mProgressWeek;
    @BindView(R.id.month_donut)
    DonutProgressView mProgressMonth;
    @BindView(R.id.year_donut)
    DonutProgressView mProgressYear;

    private Float[] mProgressFloat = new Float[]{0f, 0f, 0f, 0f};

    private boolean mIsFirstResume = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_life, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void onFragmentResume(boolean isActivityResume) {
        super.onFragmentResume(isActivityResume);
        if (mIsFirstResume) {
            mIsFirstResume = false;
            initCircleProgressData();
        }
    }

    private void initData() {
        ((TextView) mLayoutDay.findViewById(R.id.name)).setText(ResourceHelper.getString(R.string.today));
        ((TextView) mLayoutWeek.findViewById(R.id.name)).setText(ResourceHelper.getString(R.string.week));
        ((TextView) mLayoutMonth.findViewById(R.id.name)).setText(ResourceHelper.getString(R.string.month));
        ((TextView) mLayoutYear.findViewById(R.id.name)).setText(ResourceHelper.getString(R.string.year));

        NumberProgressBar today = ((NumberProgressBar) mLayoutDay.findViewById(R.id.number_progress_bar));
        NumberProgressBar week = ((NumberProgressBar) mLayoutWeek.findViewById(R.id.number_progress_bar));
        NumberProgressBar month = ((NumberProgressBar) mLayoutMonth.findViewById(R.id.number_progress_bar));
        NumberProgressBar year = ((NumberProgressBar) mLayoutYear.findViewById(R.id.number_progress_bar));
        today.setReachedBarColor(ResourceHelper.getColor(R.color.red_active));
        week.setReachedBarColor(ResourceHelper.getColor(R.color.yellow_active));
        month.setReachedBarColor(ResourceHelper.getColor(R.color.blue_active));
        year.setReachedBarColor(ResourceHelper.getColor(R.color.green_active));
        today.setProgressTextColor(ResourceHelper.getColor(R.color.red_active));
        week.setProgressTextColor(ResourceHelper.getColor(R.color.yellow_active));
        month.setProgressTextColor(ResourceHelper.getColor(R.color.blue_active));
        year.setProgressTextColor(ResourceHelper.getColor(R.color.green_active));

        initNumberProgressData();
    }

    private void initNumberProgressData() {
        NumberProgressBar today = ((NumberProgressBar) mLayoutDay.findViewById(R.id.number_progress_bar));
        NumberProgressBar week = ((NumberProgressBar) mLayoutWeek.findViewById(R.id.number_progress_bar));
        NumberProgressBar month = ((NumberProgressBar) mLayoutMonth.findViewById(R.id.number_progress_bar));
        NumberProgressBar year = ((NumberProgressBar) mLayoutYear.findViewById(R.id.number_progress_bar));
        today.setProgress(DateUtil.getProportionDay());
        week.setProgress(DateUtil.getProportionWeek());
        month.setProgress(DateUtil.getProportionMonth());
        year.setProgress(DateUtil.getProportionYear());

        mProgressFloat[0] = ((float) DateUtil.getProportionDay()) / 100f;
        mProgressFloat[1] = ((float) DateUtil.getProportionWeek()) / 100f;
        mProgressFloat[2] = ((float) DateUtil.getProportionMonth()) / 100f;
        mProgressFloat[3] = ((float) DateUtil.getProportionYear()) / 100f;
    }

    private void initCircleProgressData() {
        mProgressDay.addAmount(ResourceHelper.getString(R.string.today), mProgressFloat[0], ResourceHelper.getColor(R.color.red_active));
        mProgressWeek.addAmount(ResourceHelper.getString(R.string.week), mProgressFloat[1], ResourceHelper.getColor(R.color.yellow_active));
        mProgressMonth.addAmount(ResourceHelper.getString(R.string.month), mProgressFloat[2], ResourceHelper.getColor(R.color.blue_active));
        mProgressYear.addAmount(ResourceHelper.getString(R.string.year), mProgressFloat[3], ResourceHelper.getColor(R.color.green_active));
    }

}
