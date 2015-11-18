package com.rodico.duke0808.weatherforyou_duke0808_hw7;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends ListFragment {
//    Cherkassy id = 710791
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
        getWeather(710791);


        // TODO: Change Adapter to display your content
        String[] from = {"date","day","month","temp","description"};
        int[] to = {R.id.day_of_month_TV,R.id.day_of_week_TV,R.id.month_name_TV,R.id.temperature_TV
                ,R.id.description_TV};
        adapter = new MyAdapter(MainActivity.context,list,R.layout.item_layout,from,to);

        setListAdapter(adapter);
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
                    initList();
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
