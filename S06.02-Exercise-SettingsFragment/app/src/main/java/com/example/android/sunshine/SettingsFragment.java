package com.example.android.sunshine;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by csaenz on 5/9/2017.
 */
// Do steps 5 - 11 within SettingsFragment
// TODO (10) Implement OnSharedPreferenceChangeListener from SettingsFragment

// TODO (8) Create a method called setPreferenceSummary that accepts a Preference and an Object and sets the summary of the preference

// complete (5) Override onCreatePreferences and add the preference pref file using addPreferencesFromResource
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref);
    }
}
