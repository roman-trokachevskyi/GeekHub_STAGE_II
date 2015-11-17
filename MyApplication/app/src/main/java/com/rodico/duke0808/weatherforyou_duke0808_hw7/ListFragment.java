package com.rodico.duke0808.weatherforyou_duke0808_hw7;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    ArrayList<MyWeatherItem> list;

    public ArrayList<MyWeatherItem> getList() {
        return list;
    }

    public void setList(ArrayList<MyWeatherItem> list) {
        this.list = list;
    }

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        String[] from = {"date","day","month","temp","description"};
        int[] to = {R.id.day_of_month_TV,R.id.day_of_week_TV,R.id.month_name_TV,R.id.temperature_TV
        ,R.id.description_TV};
        MyAdapter adapter = new MyAdapter(MainActivity.context,list,R.layout.item_layout,from,to);
        ListView lv = (ListView) getView().findViewById(R.id.listView);
        lv.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }
}
