package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

/**
 * Created by csaenz on 5/9/2017.
 */
// Do steps 5 - 11 within SettingsFragment
// complete (10) Implement OnSharedPreferenceChangeListener from SettingsFragment

// complete (8) Create a method called setPreferenceSummary that accepts a Preference and an Object and sets the summary of the preference

// Do step 9 within onCreatePreference
// complete (9) Set the preference summary on each preference that isn't a CheckBoxPreference

// complete (13) Unregister SettingsFragment (this) as a SharedPreferenceChangedListener in onStop

// complete (12) Register SettingsFragment (this) as a SharedPreferenceChangedListener in onStart

// complete (11) Override onSharedPreferenceChanged to update non CheckBoxPreferences when they are changed

// complete (5) Override onCreatePreferences and add the preference pref file using addPreferencesFromResource
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();

        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            // You don't need to set up preference summaries for checkbox preferences because
            // they are already set up in xml using summaryOff and summary On
            if (!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(p.getKey(),"");
                setPreferenceSummary(p, value);
            }
        }
    }

    private void setPreferenceSummary(Preference p, String value){
     if (p instanceof EditTextPreference) {
            // For EditTextPreferences, set the summary to the value's simple string representation.
            p.setSummary(value);
        }else if(p instanceof ListPreference){
             int index = ((ListPreference) p).findIndexOfValue(value);
             p.setSummary(((ListPreference) p).getEntries()[index]);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (null != preference) {
            // Updates the summary for the preference
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
