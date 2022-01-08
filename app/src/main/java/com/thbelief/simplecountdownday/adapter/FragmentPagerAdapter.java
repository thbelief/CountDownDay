package com.thbelief.simplecountdownday.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/**
 * Author:thbelief
 * Date:2022/1/8 2:09 下午
 * Description:
 *
 * @author thbelief
 */
public class FragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {

    private List<Fragment> mList;

    public FragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return this.mList == null ? null : this.mList.get(position);
    }

    @Override
    public int getCount() {
        return this.mList == null ? 0 : this.mList.size();
    }
}
