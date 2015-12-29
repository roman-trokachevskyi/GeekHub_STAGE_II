package com.rodico.duke0808.weatherforyou_duke0808_hw7.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.rodico.duke0808.weatherforyou_duke0808_hw7.util.MyAdapter;
import com.rodico.duke0808.weatherforyou_duke0808_hw7.util.MyWeatherItem;
import com.rodico.duke0808.weatherforyou_duke0808_hw7.realm.MyRealmItem;
import com.rodico.duke0808.weatherforyou_duke0808_hw7.weatherManager.WeatherManager;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends ListFragment {
//    Cherkassy id = 710791
    static RealmResults<MyRealmItem> results;
    MyAdapter adapter;
    static ArrayList<MyWeatherItem> list;
    static String cityName;
    WeatherManager weatherManager;


    private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=new ArrayList<>();

        RealmConfiguration configuration = new RealmConfiguration.Builder(MainActivity.context).name("weather-db.realm").build();
        Realm realm = Realm.getInstance(configuration);

        RealmQuery<MyRealmItem> query = realm.where(MyRealmItem.class);
        results = query.findAll();
        adapter=new MyAdapter(MainActivity.context,results,true);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
//        realm.close();
        if (MainActivity.isNetworkAv) {
//            realm = Realm.getDefaultInstance();
            weatherManager = WeatherManager.getWeatherManager();
            weatherManager.getWeather(710791);
//            getWeather(710791);
        } else {
            Toast.makeText(MainActivity.context, "No Network Available", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;



        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(position);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int position);
    }

}
