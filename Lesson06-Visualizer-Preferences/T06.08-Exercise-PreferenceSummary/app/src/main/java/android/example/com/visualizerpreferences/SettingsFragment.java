package android.example.com.visualizerpreferences;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;

// DONE (1) Implement OnSharedPreferenceChangeListener
public class SettingsFragment extends PreferenceFragmentCompat implements OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_visualizer);

        // DONE (3) Get the preference screen, get the number of preferences and iterate through
        // all of the preferences if it is not a checkbox preference, call the setSummary method
        // passing in a preference and the value of the preference
        int preferenceCount = getPreferenceScreen().getPreferenceCount();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        for(int prefIndex = 0; prefIndex < preferenceCount; prefIndex++) {
            Preference curPref = getPreferenceScreen().getPreference(prefIndex);
            if (curPref instanceof ListPreference)
                setPreferenceSummary(curPref, ((ListPreference) curPref).getValue());
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference pref = getPreferenceScreen().findPreference(s);

        if (pref == null || !(pref instanceof ListPreference))
            return;

        setPreferenceSummary(pref, ((ListPreference) pref).getValue());

    }

    // DONE (4) Override onSharedPreferenceChanged and, if it is not a checkbox preference,
    // call setPreferenceSummary on the changed preference

    // DONE (2) Create a setPreferenceSummary which takes a Preference and String value as parameters.
    // This method should check if the preference is a ListPreference and, if so, find the label
    // associated with the value. You can do this by using the findIndexOfValue and getEntries methods
    // of Preference.
    private void setPreferenceSummary(Preference pref, String value) {
        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            int valIndex = listPref.findIndexOfValue(value);
            if (valIndex != -1)
                listPref.setSummary(listPref.getEntries()[valIndex]);
            else
                listPref.setSummary("");
        }
    }
    // DONE (5) Register and unregister the OnSharedPreferenceChange listener (this class) in
    // onCreate and onDestroy respectively.


    @Override
    public void onDestroy() {
        Log.d("BRAT", "SettingsFragment: onDestroy()");
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}