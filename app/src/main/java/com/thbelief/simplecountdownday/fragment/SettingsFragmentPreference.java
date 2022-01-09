package com.thbelief.simplecountdownday.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.thbelief.simplecountdownday.R;

/**
 * Author:thbelief
 * Date:2022/1/9 1:50 下午
 * Description:
 *
 * @author thbelief
 */
public class SettingsFragmentPreference extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        addPreferencesFromResource(R.xml.fragment_settings);
    }
}
