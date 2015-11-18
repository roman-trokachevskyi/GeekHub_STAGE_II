package com.rodico.duke0808.weatherforyou_duke0808_hw7;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by duke0808 on 17.11.15.
 */
public class MyAdapter extends SimpleAdapter {
    public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  super.getView(position, convertView, parent);
        ImageView imageView = (ImageView) view.findViewById(R.id.list_image_view);
        String img_cd = ItemFragment.list.get(position).get("icon").toString();
        String url = "http://openweathermap.org/img/w/"+img_cd+".png";
        Picasso.with(MainActivity.context).load(url).into(imageView);
        return view;
    }
}
