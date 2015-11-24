package com.rodico.duke0808.weatherforyou_duke0808_hw7;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnFragmentInteractionListener{

    static Context context;
    public static boolean isNetworkAv;
    ItemFragment listFragment;
    DetailedViewFragment detailedViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        isNetworkAv = isNetworkAvailable();
        if (savedInstanceState==null) {
            addFragmentList();
        }
    }

    private void addFragmentList() {
            listFragment = new ItemFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.list_container, listFragment, "LIST").commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
            context=this;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onClick(int position) {
        FrameLayout itemContainer = (FrameLayout) findViewById(R.id.item_container);
        if (itemContainer!=null){
            detailedViewFragment = new DetailedViewFragment();
            detailedViewFragment.setPosition(position);
            getSupportFragmentManager().beginTransaction().replace(R.id.item_container,detailedViewFragment,"DETAILED").commit();
        } else {
            Intent intent = new Intent(this, item_view.class);
            intent.putExtra("position",position);
            startActivity(intent);
        }
    }

    @Override
    public void onFragmentInteraction(int position) {
        onClick(position);
    }
}
