package com.rodico.duke0808.weatherforyou_duke0808_hw7;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class MainActivity extends AppCompatActivity {
    JSONObject weatherJSON=null;
    JSONArray list=null;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Cherkassy id = 710791
        getWeather(703448);
        test  = (TextView) findViewById(R.id.textView);
    }

    public void getWeather(int cityId) {
        new JSONGetter(){
            @Override
            protected void onPostExecute(String s) {
                writeJSONtxt(s);
                try {
                    weatherJSON = new JSONObject(s);
                    list = weatherJSON.getJSONArray("list");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute("http://api.openweathermap.org/data/2.5/forecast/city?id="+cityId+"&units=metric&lang=ua&APPID=461b1d305c3126746b780624b5598308");
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
}
