package com.rodico.duke0808.weatherforyou_duke0808_hw7.WeatherManager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.rodico.duke0808.weatherforyou_duke0808_hw7.MainActivity;
import com.rodico.duke0808.weatherforyou_duke0808_hw7.R;

public class MyService extends Service {

    private Intent intentDownload;
    private Intent intentApp;
    private android.support.v4.app.NotificationCompat.Builder builder;
    private NotificationManager mNotificationManager;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            intentDownload = new Intent(this, DownloadWeather.class);
            intentApp = new Intent(this, MainActivity.class);
            PendingIntent pendingIntentDownLoad = PendingIntent.getService(this, 0, intentDownload, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntentApp = PendingIntent.getActivity(this, 0, intentApp, PendingIntent.FLAG_UPDATE_CURRENT);
            builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentText("U can update weather forecast now!)")
                    .setContentTitle("Weather for you")
                    .setContentIntent(pendingIntentApp)
                    .addAction(R.drawable.autorenew,"Update", pendingIntentDownLoad);
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Thread thread = new Thread(){
            @Override
            public void run() {
                while (true) {
                    mNotificationManager.notify(1,builder.build());
                    try {
                        sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(MyService.this, "Service created", Toast.LENGTH_SHORT).show();
    }

}
