package com.rodico.duke0808.weatherforyou_duke0808_hw7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class item_view extends AppCompatActivity {
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");
        DetailedViewFragment fragment = new DetailedViewFragment();
        fragment.setItem(MainActivity.list.get(position));
        getSupportFragmentManager().beginTransaction().add(R.id.item_container,fragment).commit();
    }
}
