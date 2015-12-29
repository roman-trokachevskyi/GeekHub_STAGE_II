package com.rodico.duke0808.weatherforyou_duke0808_hw7.weatherManager;

import android.app.IntentService;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.rodico.duke0808.weatherforyou_duke0808_hw7.R;

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
        int code = PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.location_code_key), 710791);
        weatherManager.getWeather(code);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(DownloadWeather.this, "Updated in service", Toast.LENGTH_SHORT).show();
    }
}
