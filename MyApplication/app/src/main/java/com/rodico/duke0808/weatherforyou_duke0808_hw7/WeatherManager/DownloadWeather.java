package com.rodico.duke0808.weatherforyou_duke0808_hw7.WeatherManager;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.rodico.duke0808.weatherforyou_duke0808_hw7.MainActivity;
import com.rodico.duke0808.weatherforyou_duke0808_hw7.R;

import org.json.JSONArray;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class DownloadWeather extends IntentService {
    public DownloadWeather() {
        super("DownloadWeather");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        RealmConfiguration configuration = new RealmConfiguration.Builder(this).name("weather-db.realm").build();
        Realm.setDefaultConfiguration(configuration);
        WeatherManager weatherManager = WeatherManager.getWeatherManager();
        weatherManager.getWeather(710791);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(DownloadWeather.this, "Updated in service", Toast.LENGTH_SHORT).show();
    }
}
