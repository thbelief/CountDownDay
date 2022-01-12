package com.thbelief.simplecountdownday.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bitvale.switcher.SwitcherX;
import com.jaredrummler.cyanea.prefs.CyaneaSettingsActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.activity.LifeProgressActivity;
import com.thbelief.simplecountdownday.application.Application;
import com.thbelief.simplecountdownday.dialog.SelectBottomDialog;
import com.thbelief.simplecountdownday.interfaces.IClick;
import com.thbelief.simplecountdownday.model.MessageEvent;
import com.thbelief.simplecountdownday.storage.SharedPreferenceHelper;
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
public class SettingsFragment extends BaseFragment implements IClick {

    @BindView(R.id.layout_week_start)
    View mViewMenuWeekStart;
    @BindView(R.id.layout_theme)
    RelativeLayout mLayoutTheme;
    @BindView(R.id.layout_life)
    RelativeLayout mLayoutLife;
    @BindView(R.id.switch_vibrate)
    SwitcherX mVibrationSwitch;
    @BindView(R.id.switch_open_today)
    SwitcherX mOpenToday;
    private SettingsFragment mFragment = this;
    private SelectBottomDialog mDialog;
    private List<String> mOptions = new LinkedList<>(Arrays.asList(ResourceHelper.getString(R.string.monday), ResourceHelper.getString(R.string.sunday)));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initSettings();
        mViewMenuWeekStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VibrationHelper.clickVibration();
                mDialog = new SelectBottomDialog(mOptions, mFragment, false, null);
                mDialog.setFragmentManager(getFragmentManager()).show();
            }
        });
        mLayoutTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VibrationHelper.clickVibration();
                Intent intent = new Intent(getActivity(), CyaneaSettingsActivity.class);
                startActivity(intent);
            }
        });
        mLayoutLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VibrationHelper.clickVibration();
                Intent intent = new Intent(getActivity(), LifeProgressActivity.class);
                startActivity(intent);
            }
        });
        mVibrationSwitch.setOnCheckedChangeListener(state -> {
            VibrationHelper.clickVibration();
            SharedPreferenceHelper.setVibration(state);
            return null;
        });
        mOpenToday.setOnClickListener(v -> {
            VibrationHelper.clickVibration();
            new XPopup.Builder(mContext).asConfirm(ResourceHelper.getString(R.string.title_close_open_today),
                    ResourceHelper.getString(R.string.summary_close_open_today), new OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            mOpenToday.setChecked(!mOpenToday.isChecked(), true);
                            SharedPreferenceHelper.setHistoryToday(mOpenToday.isChecked());
                            final Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(Application.getInstance().getPackageName());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mContext.startActivity(intent);
                        }
                    }).show();
        });
    }

    private void initSettings() {
        ((TextView) mViewMenuWeekStart.findViewById(R.id.content)).setText(SharedPreferenceHelper.isWeekStartFromSunday() ? mOptions.get(1) : mOptions.get(0));
        mVibrationSwitch.setChecked(SharedPreferenceHelper.isVibration(), true);
        mOpenToday.setChecked(SharedPreferenceHelper.isHistoryToday(), true);
    }

    @Override
    public void onMessage(MessageEvent event) {

    }

    @Override
    public void onDestroyView() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        super.onDestroyView();
    }

    @Override
    public void itemClick(int pos) {
        ((TextView) mViewMenuWeekStart.findViewById(R.id.content)).setText(mOptions.get(pos));
        SharedPreferenceHelper.setWeekStartFromSunday(pos == 1);
        VibrationHelper.clickVibration();
        mDialog.dismiss();
    }

}
