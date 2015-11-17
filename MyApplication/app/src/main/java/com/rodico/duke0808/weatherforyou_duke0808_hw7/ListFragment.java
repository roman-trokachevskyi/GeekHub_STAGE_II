package com.rodico.duke0808.weatherforyou_duke0808_hw7;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    MyListItemClickListener listener;

    public void setListener(MyListItemClickListener listener) {
        this.listener = listener;
    }

    ArrayList<MyWeatherItem> list;
    MyAdapter adapter;
    ListView lv;

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
        super.onViewCreated(view, savedInstanceState);
        listener.init(list,adapter,lv);
    }
}
