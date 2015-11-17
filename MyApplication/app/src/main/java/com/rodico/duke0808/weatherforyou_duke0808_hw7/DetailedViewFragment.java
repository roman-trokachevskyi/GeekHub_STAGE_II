package com.rodico.duke0808.weatherforyou_duke0808_hw7;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedViewFragment extends Fragment {
    MyWeatherItem item;

    public void setItem(MyWeatherItem item) {
        this.item = item;
    }

    public DetailedViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView cityTv = (TextView) getView().findViewById(R.id.city_TV);
        TextView dateTV = (TextView) getView().findViewById(R.id.date_TV);
        TextView tempTv = (TextView) getView().findViewById(R.id.temp_TV);
        TextView descriptionTv = (TextView) getView().findViewById(R.id.description_TV);
        TextView humidityTv = (TextView) getView().findViewById(R.id.humidity_TV);
        TextView speedTv = (TextView) getView().findViewById(R.id.speed_TV);
        TextView angleTv = (TextView) getView().findViewById(R.id.angle_TV);
        ImageView imageView = (ImageView) getView().findViewById(R.id.detailed_IV);

        cityTv.setText("Weather in "+MainActivity.cityName);
        dateTV.setText("at "+item.get("date")+" "+item.get("month")+", "+item.get("day"));
        tempTv.setText("T: "+item.get("temp"));
        descriptionTv.setText(item.get("description").toString());
        humidityTv.setText("Humidity: "+item.get("humidity"));
        speedTv.setText("Wind speed: "+item.get("wind_speed")+"m/s");
        angleTv.setText("Wind angle: "+item.get("wind_angle"));

        String img_cd = (String) item.get("icon");
        String url = "http://openweathermap.org/img/w/"+img_cd+".png";
        Picasso.with(MainActivity.context).load(url).into(imageView);
    }
}
