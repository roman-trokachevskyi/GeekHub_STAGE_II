package com.rodico.duke0808.weatherforyou_duke0808_hw7.view;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.rodico.duke0808.weatherforyou_duke0808_hw7.R;

public class SettingsActivity extends AppCompatActivity {
    public boolean isNotifuEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toast.makeText(SettingsActivity.this, "You can change city in code))", Toast.LENGTH_LONG).show();
        final ToggleButton toggleButton  = (ToggleButton) findViewById(R.id.isNotifyEnabled);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor ed = sharedPreferences.edit();
        isNotifuEnabled=sharedPreferences.getBoolean(getString(R.string.is_notify_enabled_key), false);
        toggleButton.setChecked(isNotifuEnabled);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isNotifuEnabled = isChecked;
                ed.putBoolean(getString(R.string.is_notify_enabled_key), isNotifuEnabled).commit();
            }
        });
    }
}
