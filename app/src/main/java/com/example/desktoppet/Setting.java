package com.example.desktoppet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class Setting extends PreferenceActivity
        implements OnSharedPreferenceChangeListener, OnPreferenceClickListener {

    private EditTextPreference name;
    private ListPreference sex;
    private EditTextPreference year;
    public EditTextPreference set_time;
    private ListPreference character;
    private CheckBoxPreference start;
    private PreferenceScreen notice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        initPreferences();
    }

    private void initPreferences() {
        name = (EditTextPreference) findPreference(Consts.NAME_KEY);
        sex = (ListPreference) findPreference((Consts.SEX_KEY));
        year = (EditTextPreference) findPreference((Consts.YEAR_KEY));
        set_time = (EditTextPreference) findPreference((Consts.CLOCK_KEY));
        character = (ListPreference) findPreference(Consts.CHARACTER_KEY);
        start = (CheckBoxPreference) findPreference((Consts.START_KEY));
        notice = (PreferenceScreen) findPreference(Consts.NOTICE_KEY);
        notice.setOnPreferenceClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Setup the initial values
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
//        mListPreference.setSummary(sharedPreferences.getString(Consts.LIST_KEY, ""));
//        mEtPreference.setSummary(sharedPreferences.getString(Consts.EDIT_KEY, "linc"));

        // Set up a listener whenever a key changes
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Consts.NAME_KEY)) {
            name.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(Consts.SEX_KEY)) {
            sex.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(Consts.YEAR_KEY)) {
            year.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(Consts.CHARACTER_KEY)) {
            character.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(Consts.START_KEY)) {
//            start.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(Consts.CLOCK_KEY)) {
            set_time.setSummary(sharedPreferences.getString(key, ""));
        }
    }


    private void openSetting() {
        try {
            Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        //判断是哪个Preference被点击了
        if (preference.getKey().equals(Consts.NOTICE_KEY)) {
            openSetting();
        } else {
            return false;
        }
        return true;
    }

}

