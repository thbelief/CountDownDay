package com.thbelief.simplecountdownday.activity;

import android.os.Bundle;

import com.thbelief.simplecountdownday.R;

import butterknife.ButterKnife;

/**
 * Author:thbelief
 * Date:2022/1/10 8:54 上午
 * Description:
 *
 * @author thbelief
 */
public class MemorialDayActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mIsDisplayBackIcon = true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial_day);

        ButterKnife.bind(this);

    }
}
