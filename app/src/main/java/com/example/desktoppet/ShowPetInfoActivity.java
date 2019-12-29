package com.example.desktoppet;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.TextView;

public class ShowPetInfoActivity extends Activity {
    private TextView name, sex, year, character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pet_info);
        initView();
        showSettingInfo();
    }

    private void initView() {
        name = findViewById(R.id.name);
        sex = findViewById(R.id.sex);
        year = findViewById(R.id.year);
        character = findViewById(R.id.character);
    }

    private void showSettingInfo() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        name.setText(settings.getString(Consts.NAME_KEY, ""));
        sex.setText(settings.getString(Consts.SEX_KEY, "None"));
        year.setText(settings.getString(Consts.YEAR_KEY, "None"));
        character.setText(settings.getString(Consts.CHARACTER_KEY, "None"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
