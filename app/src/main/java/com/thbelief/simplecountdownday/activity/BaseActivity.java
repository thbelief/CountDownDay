package com.thbelief.simplecountdownday.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;
import com.thbelief.simplecountdownday.R;
import com.thbelief.simplecountdownday.utils.VibrationHelper;

/**
 * Author:thbelief
 * Date:2022/1/8 12:23 下午
 * Description:BaseActivity 用于基本Activity
 *
 * @author thbelief
 */
public class BaseActivity extends CyaneaAppCompatActivity {

    public boolean mIsDisplayBackIcon = false;
    public int mStatusColor = R.color.cyanea_primary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        immersiveStatusBar();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        VibrationHelper.clickVibration();
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(mIsDisplayBackIcon);
        }
    }

    /**
     * 沉浸式状态栏
     */
    public void immersiveStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, mStatusColor));
            setDarkStatusIcon(true);
        }
    }

    /**
     * 设置状态栏反色
     */
    protected void setDarkStatusIcon(boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (isDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }
}

