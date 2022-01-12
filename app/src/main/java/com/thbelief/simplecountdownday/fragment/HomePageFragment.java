package com.thbelief.simplecountdownday.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionMenu;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.activity.MemorialDayActivity;
import com.thbelief.simplecountdownday.adapter.HomePageAdapter;
import com.thbelief.simplecountdownday.application.Application;
import com.thbelief.simplecountdownday.dialog.SelectBottomDialog;
import com.thbelief.simplecountdownday.interfaces.IClick;
import com.thbelief.simplecountdownday.model.DataModel;
import com.thbelief.simplecountdownday.model.MessageEvent;
import com.thbelief.simplecountdownday.utils.ResourceHelper;
import com.thbelief.simplecountdownday.utils.VibrationHelper;

import java.util.ArrayList;
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
    @BindView(R.id.recycler_view)
    XRecyclerView mRecyclerView;
    private HomePageFragment mFragment = this;
    private SelectBottomDialog mDialog;
    private List<String> mOptions = new LinkedList<>(Arrays.asList(ResourceHelper.getString(R.string.create_memorial_day), ResourceHelper.getString(R.string.create_count_down_day)));
    private List<Integer> mIcons = new LinkedList<>(Arrays.asList(R.drawable.ic_memory_day, R.drawable.ic_countdown_day));

    private HomePageAdapter mAdapter;
    private List<DataModel> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        initAdapter();
        initRecyclerView();
        return view;
    }

    private void initAdapter() {
        updateData();
        mAdapter = new HomePageAdapter(mList);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayout = new LinearLayoutManager(mContext);
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayout);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void updateData(){
        mList.clear();
        mList = Application.getDaoSession().loadAll(DataModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMenu.setOnMenuButtonClickListener(v -> {
            VibrationHelper.clickVibration();
//            mDialog = new SelectBottomDialog(mOptions, this, false, mIcons);
//            mDialog.setFragmentManager(getFragmentManager()).show();
            Intent intent = new Intent(getActivity(), MemorialDayActivity.class);
            startActivity(intent);
        });
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                updateData();
                mAdapter.resetData(mList);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                updateData();
                mAdapter.resetData(mList);
                mRecyclerView.loadMoreComplete();
            }
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
    public void onFragmentResume(boolean isActivityResume) {
        super.onFragmentResume(isActivityResume);
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
