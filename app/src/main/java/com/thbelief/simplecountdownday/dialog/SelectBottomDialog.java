package com.thbelief.simplecountdownday.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.adapter.BottomDialogAdapter;
import com.thbelief.simplecountdownday.application.Application;
import com.thbelief.simplecountdownday.decoration.BottomItemDecoration;
import com.thbelief.simplecountdownday.interfaces.IClick;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.shaohui.bottomdialog.BaseBottomDialog;

/**
 * Author:thbelief
 * Date:2022/1/8 11:05 下午
 * Description:
 *
 * @author thbelief
 */
public class SelectBottomDialog extends BaseBottomDialog {
    private Unbinder mUnbinder;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private BottomDialogAdapter mAdapter;
    private int mHeight = 0;
    private FragmentManager mFragmentManager;

    public SelectBottomDialog(List<String> options, IClick click) {
        mAdapter = new BottomDialogAdapter(options, click);
    }

    public SelectBottomDialog setHeight(int height) {
        this.mHeight = height;
        return this;
    }

    public SelectBottomDialog setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) {
            mUnbinder = ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Application.getInstance());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new BottomItemDecoration());
        mRecyclerView.setAdapter(mAdapter);
        if (mRecyclerView != null && mHeight == 0) {
            mHeight = mRecyclerView.getHeight();
        }
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_select_bottom_dialog;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    public void show() {
        show(mFragmentManager);
    }

    @Override
    public boolean getCancelOutside() {
        return true;
    }

    @Override
    public void bindView(View v) {

    }
}
