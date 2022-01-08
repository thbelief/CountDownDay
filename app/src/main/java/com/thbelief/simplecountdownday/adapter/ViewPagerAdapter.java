package com.thbelief.simplecountdownday.adapter;

import androidx.viewpager.widget.ViewPager;

import com.thbelief.simplecountdownday.interfaces.IViewPagerChange;

/**
 * Author:thbelief
 * Date:2022/1/8 2:28 下午
 * Description:
 *
 * @author thbelief
 */
public class ViewPagerAdapter implements ViewPager.OnPageChangeListener {
    private IViewPagerChange mViewPagerChange;

    public ViewPagerAdapter(IViewPagerChange viewPagerChange) {
        this.mViewPagerChange = viewPagerChange;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mViewPagerChange.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
