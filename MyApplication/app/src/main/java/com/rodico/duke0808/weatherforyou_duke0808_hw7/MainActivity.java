package com.rodico.duke0808.weatherforyou_duke0808_hw7;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    JSONObject weatherJSON=null;
    JSONArray JSONlist =null;
    TextView test;
    ArrayList<MyWeatherItem> list;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Cherkassy id = 710791
        getWeather(703448);
        list = new ArrayList<>();
    }

    public void getWeather(int cityId) {
        new JSONGetter(){
            @Override
            protected void onPostExecute(String s) {
                //writeJSONtxt(s);
                try {
                    weatherJSON = new JSONObject(s);
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
        }.execute("http://api.openweathermap.org/data/2.5/forecast/city?id="+cityId+"&units=metric&lang=ua&APPID=461b1d305c3126746b780624b5598308");
    }

    private void onWeatherIsAvailable() throws JSONException, ParseException {
        initList();
        ListFragment listFragment = new ListFragment();
        listFragment.setList(list);
        getSupportFragmentManager().beginTransaction().add(R.id.list_container,listFragment).commit();
    }

    private void initList() throws JSONException, ParseException {
        int count = JSONlist.length();
        MyWeatherItem item = null;
        for (int i=0;i<count;i++){
            item = new MyWeatherItem((JSONObject) JSONlist.get(i));
            list.add(item);
        }
    }
    private void writeJSONtxt(String s) {
        File file = new File(getFilesDir(),"wetherJSON.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(s.getBytes());
            bos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        context=this;
    }
}
