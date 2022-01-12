package com.thbelief.simplecountdownday.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.adapter.FragmentTodayAdapter;
import com.thbelief.simplecountdownday.interfaces.IDataLoad;
import com.thbelief.simplecountdownday.model.MessageEvent;
import com.thbelief.simplecountdownday.utils.TodayDateHelper;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:thbelief
 * Date:2022/1/9 10:55 上午
 * Description:
 *
 * @author thbelief
 */
public class TodayFragment extends BaseFragment implements IDataLoad {
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    FragmentTodayAdapter mAdapter;
    @BindView(R.id.loading_animate)
    AVLoadingIndicatorView mLoadingView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        TodayDateHelper.requestData(this);
        mLoadingView.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);

        return view;
    }

    @Override
    public void onMessage(MessageEvent event) {

    }

    @Override
    public void onDestroyView() {
        if (mRecyclerView != null) {
            mRecyclerView.destroy();
            mRecyclerView = null;
        }
        super.onDestroyView();
    }

    private void initRecyclerView() {
        if (mRecyclerView != null) {
            mAdapter = new FragmentTodayAdapter(TodayDateHelper.getData());
            mRecyclerView.setAdapter(mAdapter);
            mLoadingView.smoothToHide();
            mLoadingView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loaded() {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initRecyclerView();
            }
        });
    }
}
