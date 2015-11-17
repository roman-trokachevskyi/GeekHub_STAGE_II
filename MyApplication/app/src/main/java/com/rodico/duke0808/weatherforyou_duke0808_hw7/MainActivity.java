package com.rodico.duke0808.weatherforyou_duke0808_hw7;

import android.content.Context;
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

public class MainActivity extends AppCompatActivity implements MyListItemClickListener{
    JSONObject weatherJSON=null;
    static String cityName;
    JSONArray JSONlist =null;
    TextView test;
    static ArrayList<MyWeatherItem> list;
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
        }.execute("http://api.openweathermap.org/data/2.5/forecast/city?id="+cityId+"&units=metric&APPID=461b1d305c3126746b780624b5598308");
    }

    private void onWeatherIsAvailable() throws JSONException, ParseException {
        initList();
        ListFragment listFragment = new ListFragment();
        listFragment.setList(list);
        listFragment.setListener(this);
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

    @Override
    public void onClick(int position) {
        FrameLayout itemContainer = (FrameLayout) findViewById(R.id.item_container);
        if (itemContainer!=null){
            DetailedViewFragment detailedViewFragment = new DetailedViewFragment();
            detailedViewFragment.setItem(list.get(position));
            getSupportFragmentManager().beginTransaction().replace(R.id.item_container,detailedViewFragment).commit();
        } else {

        }
    }

    @Override
    public void init(ArrayList<MyWeatherItem> list, MyAdapter adapter, ListView lv) {
        String[] from = {"date","day","month","temp","description"};
        int[] to = {R.id.day_of_month_TV,R.id.day_of_week_TV,R.id.month_name_TV,R.id.temperature_TV
                ,R.id.description_TV};
        adapter = new MyAdapter(MainActivity.context,list,R.layout.item_layout,from,to);
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClick(position);
            }
        });
    }
}
