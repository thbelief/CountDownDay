package com.thbelief.simplecountdownday.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bitvale.switcher.SwitcherX;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.storage.SharedPreferenceHelper;
import com.thbelief.simplecountdownday.utils.DateUtil;
import com.thbelief.simplecountdownday.utils.ResourceHelper;
import com.thbelief.simplecountdownday.utils.ToastyUtil;
import com.thbelief.simplecountdownday.utils.VibrationHelper;

import java.util.Calendar;
import java.util.Objects;

import app.futured.donut.DonutProgressView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:thbelief
 * Date:2022/1/9 6:00 下午
 * Description:
 *
 * @author thbelief
 */
public class LifeProgressActivity extends BaseActivity {
    @BindView(R.id.switch_life_myself)
    SwitcherX mSwitch;
    @BindView(R.id.birth_year)
    MaterialEditText mBirthYear;
    @BindView(R.id.predict_year)
    MaterialEditText mPredictYear;
    @BindView(R.id.life_donut)
    DonutProgressView mCircleProgress;
    @BindView(R.id.number_progress_bar)
    NumberProgressBar mNumberProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mIsDisplayBackIcon = true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_progress);

        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mSwitch.setEnabled(false);
        mSwitch.setChecked(SharedPreferenceHelper.isLifeProgress(), true);
        if (mSwitch.isChecked()) {
            mBirthYear.setText(SharedPreferenceHelper.getBirthYear() + "");
            mPredictYear.setText(SharedPreferenceHelper.getPredictYear() + "");
            mNumberProgress.setProgress(DateUtil.getProgressLife());
            mCircleProgress.addAmount(getLocalClassName(), (float) DateUtil.getProgressLife() / 100f, ResourceHelper.getColor(R.color.blue_active));
        }
        mSwitch.setEnabled(isAllInput());
        mBirthYear.addTextChangedListener(mTextWatcher1);
        mPredictYear.addTextChangedListener(mTextWatcher2);
        mSwitch.setOnCheckedChangeListener(state -> {
            VibrationHelper.clickVibration();
            SharedPreferenceHelper.setLifeProgress(state);
            return null;
        });
        if (!mSwitch.isEnabled()) {
            ToastyUtil.info(ResourceHelper.getString(R.string.tips_life_progress));
        }
    }

    private boolean isAllInput() {
        return !"".contentEquals(mBirthYear.getText()) && !"".contentEquals(mPredictYear.getText());
    }

    private TextWatcher mTextWatcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!"".contentEquals(editable) && Integer.parseInt(editable.toString()) > Calendar.getInstance().get(Calendar.YEAR)) {
                mBirthYear.setText("");
                mSwitch.setEnabled(false);
                mSwitch.setChecked(false, true);
                ToastyUtil.error(ResourceHelper.getString(R.string.tips_input_error_year));
                return;
            }
            if (isAllInput()) {
                mSwitch.setEnabled(true);
            } else {
                mSwitch.setEnabled(false);
                mSwitch.setChecked(false, true);
            }
            if (!"".contentEquals(editable)) {
                SharedPreferenceHelper.setBirthYear(Integer.parseInt(String.valueOf(editable)));
            }
        }
    };

    private TextWatcher mTextWatcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (isAllInput()) {
                mSwitch.setEnabled(true);
            } else {
                mSwitch.setChecked(false, true);
                mSwitch.setEnabled(false);
            }
            if (!"".contentEquals(editable)) {
                SharedPreferenceHelper.setPredictYear(Integer.parseInt(String.valueOf(editable)));
            }
        }
    };
}
