package com.rodico.duke0808.weatherforyou_duke0808_hw7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class item_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
