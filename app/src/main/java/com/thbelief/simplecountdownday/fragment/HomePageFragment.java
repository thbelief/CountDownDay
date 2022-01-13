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
import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.activity.MemorialDayActivity;
import com.thbelief.simplecountdownday.adapter.HomePageAdapter;
import com.thbelief.simplecountdownday.application.Application;
import com.thbelief.simplecountdownday.dialog.SelectBottomDialog;
import com.thbelief.simplecountdownday.interfaces.IClick;
import com.thbelief.simplecountdownday.model.DataModel;
import com.thbelief.simplecountdownday.model.MessageEvent;
import com.thbelief.simplecountdownday.utils.ResourceHelper;
import com.thbelief.simplecountdownday.utils.ToastyUtil;
import com.thbelief.simplecountdownday.utils.VibrationHelper;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    SwipeRecyclerView mRecyclerView;
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
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        mRecyclerView.setOnItemMenuClickListener(mItemMenuClickListener);
        mRecyclerView.setOnItemMoveListener(mItemMoveListener);
        mRecyclerView.useDefaultLoadMore();
        mRecyclerView.setAutoLoadMore(true);
        mRecyclerView.setLoadMoreListener(mLoadMoreListener);
        mRecyclerView.setLongPressDragEnabled(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void updateData() {
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
    }

    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
            deleteItem.setText(R.string.delete);
            deleteItem.setWidth(300);
            deleteItem.setImage(R.drawable.ic_delete);
            leftMenu.addMenuItem(deleteItem);
            SwipeMenuItem editItem = new SwipeMenuItem(mContext);
            editItem.setText(R.string.edit);
            editItem.setWidth(200);
            editItem.setImage(R.drawable.ic_edit);
            leftMenu.addMenuItem(editItem);
        }
    };

    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();

            if (direction == SwipeRecyclerView.LEFT_DIRECTION && menuPosition == 0) {
                ToastyUtil.success(ResourceHelper.getString(R.string.delete) + " " + mAdapter.getModel(position).getTitle());
                Application.getDaoSession().delete(mAdapter.getModel(position));
                mAdapter.notifyPositionRemove(position);
            } else if (direction == SwipeRecyclerView.LEFT_DIRECTION && menuPosition == 1) {
                Intent intent = new Intent(getActivity(), MemorialDayActivity.class);
                MemorialDayActivity.mModel = mAdapter.getModel(position);
                startActivity(intent);
            }
        }
    };

    OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            // 此方法在Item拖拽交换位置时被调用。
            // 第一个参数是要交换为之的Item，第二个是目标位置的Item。

            // 交换数据，并更新adapter。
            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();
            Collections.swap(mList, fromPosition, toPosition);
            mAdapter.notifyItemMoved(fromPosition, toPosition);

            // 返回true，表示数据交换成功，ItemView可以交换位置。
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            // 此方法在Item在侧滑删除时被调用。

            // 从数据源移除该Item对应的数据，并刷新Adapter。
            int position = srcHolder.getAdapterPosition();
            mList.remove(position);
            mAdapter.notifyItemRemoved(position);
        }
    };

    SwipeRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {

            // 请求数据，并更新数据源操作。
            updateData();

            mRecyclerView.loadMoreFinish(false, true);
        }
    };

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
        updateData();
        if (mAdapter != null) {
            mAdapter.resetData(mList);
        }
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
