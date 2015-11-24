package com.rodico.duke0808.weatherforyou_duke0808_hw7;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rodico.duke0808.weatherforyou_duke0808_hw7.Realm.MyRealmItem;
import com.squareup.picasso.Picasso;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedViewFragment extends Fragment {
    static int position;

    public static void setPosition(int position) {
        DetailedViewFragment.position = position;
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

        cityTv.setText("Weather in " + ItemFragment.cityName);
        MyRealmItem item = ItemFragment.results.get(position);

        Date date = new Date();
        date.setTime(item.getDt());
        dateTV.setText("at "+ date.toString());
        tempTv.setText("T: "+item.getTemp());
        descriptionTv.setText(item.getDescription());
        humidityTv.setText("Humidity: "+item.getHumidity());
        speedTv.setText("Wind speed: "+item.getSpeed());
        angleTv.setText("Wind angle: "+item.getDeg());
//
        String img_cd = (String) item.getIcon();
        String url = "http://openweathermap.org/img/w/"+img_cd+".png";
        Picasso.with(MainActivity.context).load(url).into(imageView);
    }
}
