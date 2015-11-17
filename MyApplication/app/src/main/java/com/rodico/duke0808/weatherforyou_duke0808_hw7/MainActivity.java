package com.rodico.duke0808.weatherforyou_duke0808_hw7;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

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
    JSONObject weatherJSON=null;
    static String cityName;
    JSONArray JSONlist =null;
    TextView test;
    static ArrayList<MyWeatherItem> list;
    static Context context;
    ItemFragment listFragment;
    DetailedViewFragment detailedViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        //Cherkassy id = 710791
        getWeather(703448);
        list = new ArrayList<>();
        detailedViewFragment = null;
        listFragment=null;
        context=this;
    }

    public void getWeather(int cityId) {
        new JSONGetter(){
            @Override
            protected void onPostExecute(String s) {
                //writeJSONtxt(s);
                try {
                    weatherJSON = new JSONObject(s);
                    cityName = weatherJSON.getJSONObject("city").get("name").toString();
                    JSONlist = weatherJSON.getJSONArray("list");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    onWeatherIsAvailable();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }.execute("http://api.openweathermap.org/data/2.5/forecast/city?id=" + cityId + "&units=metric&APPID=461b1d305c3126746b780624b5598308");
    }

    private void onWeatherIsAvailable() throws JSONException, ParseException {
        initList();
        listFragment = new ItemFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.list_container, listFragment, "LIST").commit();
    }

    private void initList() throws JSONException, ParseException {
        int count = JSONlist.length();
        MyWeatherItem item = null;
        for (int i=0;i<count;i++){
            item = new MyWeatherItem((JSONObject) JSONlist.get(i));
            list.add(item);
        }
    }


    public void onClick(int position) {
        FrameLayout itemContainer = (FrameLayout) findViewById(R.id.item_container);
        if (itemContainer!=null){
            detailedViewFragment = new DetailedViewFragment();
            detailedViewFragment.setItem(list.get(position));
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

    @Override
    protected void onPause() {
        super.onPause();
        getSupportFragmentManager().beginTransaction().remove(listFragment).remove(detailedViewFragment).commit();
        listFragment=null;
        detailedViewFragment=null;
    }
}
