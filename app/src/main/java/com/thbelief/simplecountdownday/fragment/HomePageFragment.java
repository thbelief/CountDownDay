package com.thbelief.simplecountdownday.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.activity.LifeProgressActivity;
import com.thbelief.simplecountdownday.activity.MemorialDayActivity;
import com.thbelief.simplecountdownday.dialog.SelectBottomDialog;
import com.thbelief.simplecountdownday.interfaces.IClick;
import com.thbelief.simplecountdownday.model.MessageEvent;
import com.thbelief.simplecountdownday.utils.ResourceHelper;
import com.thbelief.simplecountdownday.utils.VibrationHelper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:thbelief
 * Date:2022/1/8 1:51 下午
 * Description:
 *
 * @author thbelief
 */
public class HomePageFragment extends BaseFragment implements IClick {

    @BindView(R.id.float_button_menu)
    FloatingActionMenu mMenu;
    private HomePageFragment mFragment = this;
    private SelectBottomDialog mDialog;
    private List<String> mOptions = new LinkedList<>(Arrays.asList(ResourceHelper.getString(R.string.create_memorial_day), ResourceHelper.getString(R.string.create_count_down_day)));
    private List<Integer> mIcons = new LinkedList<>(Arrays.asList(R.drawable.ic_memory_day, R.drawable.ic_countdown_day));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMenu.setOnMenuButtonClickListener(v -> {
            VibrationHelper.clickVibration();
            mDialog = new SelectBottomDialog(mOptions, this, false, mIcons);
            mDialog.setFragmentManager(getFragmentManager()).show();
        });
    }

    @Override
    public void onMessage(MessageEvent event) {

    }

    @Override
    public void onFragmentPause(boolean isActivityPause) {
        super.onFragmentPause(isActivityPause);
        mMenu.close(true);
    }

    @Override
    public void itemClick(int pos) {
        VibrationHelper.clickVibration();
        mDialog.dismiss();
        if (pos == 0) {
            Intent intent = new Intent(getActivity(), MemorialDayActivity.class);
            startActivity(intent);
        }
    }

}
