package com.john.www.abu.PreferenceStuffs;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import com.john.www.abu.R;

public class PreferenceDataUsage extends PreferenceActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_data_and_usage);
    }
}
