package com.thbelief.simplecountdownday.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.model.MessageEvent;
import com.thbelief.simplecountdownday.storage.SharedPreferenceHelper;
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
    @BindView(R.id.life)
    LinearLayout mLayoutLife;
    @BindView(R.id.day)
    LinearLayout mLayoutDay;
    @BindView(R.id.week)
    LinearLayout mLayoutWeek;
    @BindView(R.id.month)
    LinearLayout mLayoutMonth;
    @BindView(R.id.year)
    LinearLayout mLayoutYear;
    @BindView(R.id.life_donut)
    DonutProgressView mProgressLife;
    @BindView(R.id.day_donut)
    DonutProgressView mProgressDay;
    @BindView(R.id.week_donut)
    DonutProgressView mProgressWeek;
    @BindView(R.id.month_donut)
    DonutProgressView mProgressMonth;
    @BindView(R.id.year_donut)
    DonutProgressView mProgressYear;
    @BindView(R.id.layout_life_progress)
    CardView mLifeCardView;

    private Float[] mProgressFloat = new Float[]{0f, 0f, 0f, 0f, 0f};

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
    public void onStart() {
        super.onStart();
        initNumberProgressData();
        if (mIsFirstResume) {
            mIsFirstResume = false;
            initCircleProgressData();
        }
        if (SharedPreferenceHelper.isLifeProgress()) {
            mLifeCardView.setVisibility(View.VISIBLE);
        } else {
            mLifeCardView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMessage(MessageEvent event) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 重置状态
        mIsFirstResume = true;
    }

    private void initData() {
        ((TextView) mLayoutLife.findViewById(R.id.name)).setText(ResourceHelper.getString(R.string.life));
        ((TextView) mLayoutDay.findViewById(R.id.name)).setText(ResourceHelper.getString(R.string.today));
        ((TextView) mLayoutWeek.findViewById(R.id.name)).setText(ResourceHelper.getString(R.string.week));
        ((TextView) mLayoutMonth.findViewById(R.id.name)).setText(ResourceHelper.getString(R.string.month));
        ((TextView) mLayoutYear.findViewById(R.id.name)).setText(ResourceHelper.getString(R.string.year));

        NumberProgressBar life = ((NumberProgressBar) mLayoutLife.findViewById(R.id.number_progress_bar));
        NumberProgressBar today = ((NumberProgressBar) mLayoutDay.findViewById(R.id.number_progress_bar));
        NumberProgressBar week = ((NumberProgressBar) mLayoutWeek.findViewById(R.id.number_progress_bar));
        NumberProgressBar month = ((NumberProgressBar) mLayoutMonth.findViewById(R.id.number_progress_bar));
        NumberProgressBar year = ((NumberProgressBar) mLayoutYear.findViewById(R.id.number_progress_bar));
        life.setReachedBarColor(ResourceHelper.getColor(R.color.cyanea_primary));
        today.setReachedBarColor(ResourceHelper.getColor(R.color.red_active));
        week.setReachedBarColor(ResourceHelper.getColor(R.color.yellow_active));
        month.setReachedBarColor(ResourceHelper.getColor(R.color.blue_active));
        year.setReachedBarColor(ResourceHelper.getColor(R.color.green_active));
        life.setProgressTextColor(ResourceHelper.getColor(R.color.cyanea_primary));
        today.setProgressTextColor(ResourceHelper.getColor(R.color.red_active));
        week.setProgressTextColor(ResourceHelper.getColor(R.color.yellow_active));
        month.setProgressTextColor(ResourceHelper.getColor(R.color.blue_active));
        year.setProgressTextColor(ResourceHelper.getColor(R.color.green_active));

        initNumberProgressData();
    }

    private void initNumberProgressData() {
        NumberProgressBar life = ((NumberProgressBar) mLayoutLife.findViewById(R.id.number_progress_bar));
        NumberProgressBar today = ((NumberProgressBar) mLayoutDay.findViewById(R.id.number_progress_bar));
        NumberProgressBar week = ((NumberProgressBar) mLayoutWeek.findViewById(R.id.number_progress_bar));
        NumberProgressBar month = ((NumberProgressBar) mLayoutMonth.findViewById(R.id.number_progress_bar));
        NumberProgressBar year = ((NumberProgressBar) mLayoutYear.findViewById(R.id.number_progress_bar));
        life.setProgress(DateUtil.getProgressLife());
        today.setProgress(DateUtil.getProportionDay());
        week.setProgress(DateUtil.getProportionWeek());
        month.setProgress(DateUtil.getProportionMonth());
        year.setProgress(DateUtil.getProportionYear());

        mProgressFloat[0] = ((float) DateUtil.getProportionDay()) / 100f;
        mProgressFloat[1] = ((float) DateUtil.getProportionWeek()) / 100f;
        mProgressFloat[2] = ((float) DateUtil.getProportionMonth()) / 100f;
        mProgressFloat[3] = ((float) DateUtil.getProportionYear()) / 100f;
        mProgressFloat[4] = ((float) DateUtil.getProgressLife()) / 100f;
    }

    private void initCircleProgressData() {
        if (mProgressDay == null || mProgressWeek == null || mProgressMonth == null | mProgressYear == null) {
            return;
        }
        mProgressDay.addAmount(ResourceHelper.getString(R.string.today), mProgressFloat[0], ResourceHelper.getColor(R.color.red_active));
        mProgressWeek.addAmount(ResourceHelper.getString(R.string.week), mProgressFloat[1], ResourceHelper.getColor(R.color.yellow_active));
        mProgressMonth.addAmount(ResourceHelper.getString(R.string.month), mProgressFloat[2], ResourceHelper.getColor(R.color.blue_active));
        mProgressYear.addAmount(ResourceHelper.getString(R.string.year), mProgressFloat[3], ResourceHelper.getColor(R.color.green_active));
        mProgressLife.addAmount(ResourceHelper.getString(R.string.life), mProgressFloat[4], ResourceHelper.getColor(R.color.orange_active));
    }

}
