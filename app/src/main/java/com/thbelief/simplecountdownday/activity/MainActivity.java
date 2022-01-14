package com.thbelief.simplecountdownday.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.gauravk.bubblenavigation.BubbleToggleView;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.thbelief.simplecountdownday.adapter.FragmentPagerAdapter;

import androidx.viewpager.widget.ViewPager;

import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.adapter.ViewPagerAdapter;
import com.thbelief.simplecountdownday.fragment.HistoryFragment;
import com.thbelief.simplecountdownday.fragment.HomePageFragment;
import com.thbelief.simplecountdownday.fragment.LifeFragment;
import com.thbelief.simplecountdownday.fragment.SettingsFragment;
import com.thbelief.simplecountdownday.fragment.TodayFragment;
import com.thbelief.simplecountdownday.interfaces.IViewPagerChange;
import com.thbelief.simplecountdownday.model.MessageEvent;
import com.thbelief.simplecountdownday.storage.SharedPreferenceHelper;
import com.thbelief.simplecountdownday.utils.ToastyUtil;
import com.thbelief.simplecountdownday.utils.VibrationHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:thbelief
 * Date:2022/1/8 12:23 下午
 * Description:MainActivity
 *
 * @author thbelief
 */
public class MainActivity extends BaseActivity implements IViewPagerChange {

    @BindView(R.id.floating_top_bar_navigation)
    BubbleNavigationConstraintView mBubbleNavigation;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.item_today)
    BubbleToggleView mItemToday;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mStatusColor = R.color.cyanea_background_light;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mBubbleNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                VibrationHelper.clickVibration();
                mViewPager.setCurrentItem(position, true);
            }
        });

        initViewPager();

        mRxPermissions.request(Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR)
                .subscribe(granted -> {
                    if (granted) {

                    } else {

                    }
                });
    }

    @Override
    public void onMessage(MessageEvent event) {

    }

    private void initViewPager() {
        mFragmentList.add(new HomePageFragment());
        mFragmentList.add(new LifeFragment());
        mFragmentList.add(new HistoryFragment());
        if (SharedPreferenceHelper.isHistoryToday()) {
            mItemToday.setVisibility(View.VISIBLE);
            mFragmentList.add(new TodayFragment());
        }
        mFragmentList.add(new SettingsFragment());
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPagerAdapter(this));
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onPageSelected(int pos) {
        mBubbleNavigation.setCurrentActiveItem(pos);
    }
}
