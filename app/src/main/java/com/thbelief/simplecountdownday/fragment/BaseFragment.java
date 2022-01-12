package com.thbelief.simplecountdownday.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.thbelief.simplecountdownday.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Unbinder;

/**
 * Author:thbelief
 * Date:2022/1/8 1:54 下午
 * Description:
 *
 * @author thbelief
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 是否ViewPage+Fragment切换
     */
    private boolean mFromVisibleHint = false;
    /**
     * 系统告诉的可见性
     */
    private boolean mFakeVisible = false;
    /**
     * 自定义的真正可见性
     */
    private boolean mIsVisible = false;

    public Unbinder mUnbinder;

    public Context mContext;
    public Activity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        onMessage(event);
    }

    /**
     * event事件分发
     *
     * @param event
     */
    public abstract void onMessage(MessageEvent event);

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFromVisibleHint) {
            mFakeVisible = getUserVisibleHint();
        } else {
            mFakeVisible = !isHidden();
        }
        //有ParentFragment时可见性跟随ParentFragment 不作处理
        if (getParentFragment() != null && getParentFragment() instanceof BaseFragment) {
            return;
        }

        if (mFakeVisible) {
            mIsVisible = true;
            onFragmentResume(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //有ParentFragment时可见性跟随ParentFragment 不作处理
        if (getParentFragment() != null && getParentFragment() instanceof BaseFragment) {
            return;
        }

        mIsVisible = false;
        onFragmentPause(true);
    }

    /**
     * fragment真正可见的时候回调
     *
     * @param isActivityResume
     */
    public void onFragmentResume(boolean isActivityResume) {
        if (!isAdded()) {
            return;
        }

        FragmentManager manager = getChildFragmentManager();

        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment childFragment : fragments) {
                if (childFragment != null && childFragment instanceof BaseFragment) {
                    if (((BaseFragment) childFragment).mFakeVisible) {
                        ((BaseFragment) childFragment).mIsVisible = true;
                        ((BaseFragment) childFragment).onFragmentResume(isActivityResume);
                    }
                }
            }
        }
    }

    /**
     * fragment不可见的时候回调
     *
     * @param isActivityPause
     */
    public void onFragmentPause(boolean isActivityPause) {
        if (!isAdded()) {
            return;
        }

        FragmentManager manager = getChildFragmentManager();

        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment childFragment : fragments) {
                if (childFragment != null && childFragment instanceof BaseFragment) {
                    if (!((BaseFragment) childFragment).mFakeVisible) {
                        ((BaseFragment) childFragment).mIsVisible = false;
                        ((BaseFragment) childFragment).onFragmentPause(isActivityPause);
                    }
                }
            }
        }
    }

    /**
     * FragmentManager hide
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        onTabChanged(!hidden);
    }

    /**
     * ViewPage+Fragment切换
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mFromVisibleHint = true;
        onTabChanged(isVisibleToUser);
    }

    private void onTabChanged(boolean visible) {
        mFakeVisible = visible;
        boolean curVisible = mFakeVisible;
        if (getParentFragment() != null && getParentFragment() instanceof BaseFragment) {
            BaseFragment parent = (BaseFragment) getParentFragment();
            if (parent.mIsVisible = false) {
                curVisible = false;
            }
        }
        if (curVisible && !mIsVisible) {
            mIsVisible = true;
            onFragmentResume(false);
        } else if (!curVisible && mIsVisible) {
            mIsVisible = false;
            onFragmentPause(true);
        }
    }
}
