package com.thbelief.simplecountdownday.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.bitvale.switcher.SwitcherX;
import com.github.gzuliyujiang.calendarpicker.CalendarPicker;
import com.github.gzuliyujiang.calendarpicker.OnSingleDatePickListener;
import com.github.gzuliyujiang.colorpicker.ColorPicker;
import com.github.gzuliyujiang.colorpicker.OnColorPickedListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.application.Application;
import com.thbelief.simplecountdownday.dialog.SelectBottomDialog;
import com.thbelief.simplecountdownday.interfaces.IClick;
import com.thbelief.simplecountdownday.model.DataModel;
import com.thbelief.simplecountdownday.model.MessageEvent;
import com.thbelief.simplecountdownday.utils.DateUtil;
import com.thbelief.simplecountdownday.utils.ResourceHelper;
import com.thbelief.simplecountdownday.utils.ToastyUtil;
import com.thbelief.simplecountdownday.utils.VibrationHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:thbelief
 * Date:2022/1/10 8:54 上午
 * Description:
 *
 * @author thbelief
 */
public class MemorialDayActivity extends BaseActivity implements IClick {
    @BindView(R.id.layout_date)
    RelativeLayout mDateLayout;
    @BindView(R.id.date)
    TextView mDateTv;
    @BindView(R.id.layout_unit)
    RelativeLayout mUnitLayout;
    @BindView(R.id.unit)
    TextView mUnitTv;
    @BindView(R.id.clock)
    SwitcherX mClockSwitchX;
    @BindView(R.id.layout_color)
    RelativeLayout mColorLayout;
    @BindView(R.id.color)
    ImageView mColorImage;
    @BindView(R.id.title)
    MaterialEditText mTitleEdit;
    @BindView(R.id.content)
    MaterialEditText mContentEdit;
    @BindView(R.id.display)
    SwitcherX mDisplaySwitch;

    private SelectBottomDialog mDialog;
    private List<String> mOptions = new LinkedList<>(Arrays.asList(ResourceHelper.getString(R.string.time_unit_2),
            ResourceHelper.getString(R.string.time_unit_3), ResourceHelper.getString(R.string.time_unit_4)));
    private int mCurColor = R.color.blue_bg_light;
    public static DataModel mModel;
    private boolean mIsUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mIsDisplayBackIcon = true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial_day);

        ButterKnife.bind(this);

        if (mModel == null) {
            mDateTv.setText(DateUtil.formatDate(new Date()));
        } else {
            mIsUpdate = true;
            initData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        VibrationHelper.clickVibration();
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.save) {
            if (!isAvailable()) {
                ToastyUtil.warning(ResourceHelper.getString(R.string.warning_title));
                return super.onOptionsItemSelected(item);
            }
            ToastyUtil.success(ResourceHelper.getString(R.string.save_success));
            saveData();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        if (mModel == null) {
            return;
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(ResourceHelper.getString(R.string.edit));

        }
        mTitleEdit.setText(mModel.getTitle());
        mDateTv.setText(mModel.getDate());
        mUnitTv.setText(mOptions.get(mModel.getUnit()));
        mClockSwitchX.setChecked(mModel.getIsClock(), true);
        Drawable drawable = ResourceHelper.getDrawable(R.drawable.shape_circle);
        drawable.setColorFilter(mModel.getColorId(), PorterDuff.Mode.SRC_ATOP);
        mColorImage.setImageDrawable(drawable);
        mCurColor = mModel.getColorId();
        mContentEdit.setText(mModel.getContent());
        mDisplaySwitch.setChecked(mModel.getIsDisplay(), true);
    }

    private void saveData() {
        DataModel model = null;
        if (mIsUpdate) {
            model = mModel;
        } else {
            model = new DataModel();
        }
        model.setTitle(mTitleEdit.getText().toString());
        model.setDate(mDateTv.getText().toString());
        model.setUnit(mOptions.indexOf(mUnitTv.getText()));
        model.setClock(mClockSwitchX.isChecked());
        model.setColorId(mCurColor);
        model.setContent(mContentEdit.getText().toString());
        model.setDisplay(mDisplaySwitch.isChecked());
        if (mIsUpdate) {
            Application.getDaoSession().insertOrReplace(model);
        } else {
            Application.getDaoSession().insert(model);
        }
        mIsUpdate = false;
        mModel = null;
    }

    private boolean isAvailable() {
        //标题不允许为空
        return mTitleEdit.getText() != null && mTitleEdit.getText().length() != 0;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDateLayout.setOnClickListener(v -> {
            VibrationHelper.clickVibration();
            CalendarPicker picker = new CalendarPicker(this);
            picker.setRangeDateOnFuture(12);
            int singleTimeInMillis = (int) System.currentTimeMillis();
            picker.setSelectedDate(singleTimeInMillis);
            picker.setOnSingleDatePickListener(new OnSingleDatePickListener() {
                @Override
                public void onSingleDatePicked(@NonNull Date date) {
                    mDateTv.setText(DateUtil.formatDate(date));
                }
            });
            picker.show();
        });
        mUnitLayout.setOnClickListener(v -> {
            VibrationHelper.clickVibration();
            mDialog = new SelectBottomDialog(mOptions, this, false, null);
            mDialog.setFragmentManager(getSupportFragmentManager()).show();
        });
        mClockSwitchX.setOnCheckedChangeListener(state -> {
            VibrationHelper.clickVibration();
            return null;
        });
        mColorLayout.setOnClickListener(v -> {
            VibrationHelper.clickVibration();
            ColorPicker picker = new ColorPicker(this);
            picker.setOnColorPickListener(new OnColorPickedListener() {
                @Override
                public void onColorPicked(int pickedColor) {
                    mCurColor = pickedColor;
                    VibrationHelper.clickVibration();
                    Drawable drawable = ResourceHelper.getDrawable(R.drawable.shape_circle);
                    drawable.setColorFilter(pickedColor, PorterDuff.Mode.SRC_ATOP);
                    mColorImage.setImageDrawable(drawable);
                }
            });
            picker.show();
        });
    }

    @Override
    protected void onDestroy() {
        mModel = null;
        mIsUpdate = false;
        super.onDestroy();
    }

    @Override
    public void onMessage(MessageEvent event) {

    }

    @Override
    public void itemClick(int pos) {
        mUnitTv.setText(mOptions.get(pos));
        VibrationHelper.clickVibration();
        mDialog.dismiss();
    }
}
