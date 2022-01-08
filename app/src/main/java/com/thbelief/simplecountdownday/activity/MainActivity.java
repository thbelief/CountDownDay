package com.thbelief.simplecountdownday.activity;

import android.os.Bundle;
import android.view.View;

import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.thbelief.simplecountdownday.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:thbelief
 * Date:2022/1/8 12:23 下午
 * Description:MainActivity
 *
 * @author thbelief
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.floating_top_bar_navigation)
    BubbleNavigationConstraintView mBubbleNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


    }

}
