package com.rodico.duke0808.weatherforyou_duke0808_hw7;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rodico.duke0808.weatherforyou_duke0808_hw7.Realm.MyRealmItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import io.realm.Realm;
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
    Realm realm;
    static RealmResults<MyRealmItem> results;
    MyAdapter adapter;
    static ArrayList<MyWeatherItem> list;
    JSONObject weatherJSON=null;
    static String cityName;
    JSONArray JSONlist =null;

    public void setList(ArrayList<MyWeatherItem> list) {
        this.list = list;
    }

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
        realm = Realm.getInstance(MainActivity.context);
        RealmQuery<MyRealmItem> query = realm.where(MyRealmItem.class);
        results = query.findAll();
        adapter=new MyAdapter(MainActivity.context,results,true);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (MainActivity.isNetworkAv) {
            getWeather(710791);
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

    public void getWeather(int cityId) {
        new JSONGetter(){
            @Override
            protected void onPostExecute(String s) {
                //writeJSONtxt(s);
                try {
                    weatherJSON = new JSONObject(s);
                    cityName = weatherJSON.getJSONObject("city").get("name").toString();
                    JSONlist = weatherJSON.getJSONArray("list");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
//                    initList();
                    loadToRealm();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }.execute("http://api.openweathermap.org/data/2.5/forecast/city?id=" + cityId + "&units=metric&APPID=461b1d305c3126746b780624b5598308");
    }

    private void initList() throws JSONException, ParseException {
        int count = JSONlist.length();
        MyWeatherItem item = null;
        for (int i=0;i<count;i++){
            item = new MyWeatherItem((JSONObject) JSONlist.get(i));
            list.add(item);
            adapter.notifyDataSetChanged();
        }
    }

    public void loadToRealm() throws JSONException, ParseException {
        int count = JSONlist.length();
        realm.beginTransaction();
        for (int i=0;i<count;i++){
            MyWeatherItem myWeatherItem = new MyWeatherItem((JSONObject) JSONlist.get(i));
            realm.copyToRealmOrUpdate(myWeatherItem.realmItem);
        }
        realm.commitTransaction();
        RealmQuery<MyRealmItem> query = realm.where(MyRealmItem.class);
        results = query.findAll();
        for (int i=0;i<results.size()-40;i++){
            results.remove(i);
        }
        adapter.notifyDataSetChanged();
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(int position);
    }

}
