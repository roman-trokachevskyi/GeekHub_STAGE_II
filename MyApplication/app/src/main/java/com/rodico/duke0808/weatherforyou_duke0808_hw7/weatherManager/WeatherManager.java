package com.rodico.duke0808.weatherforyou_duke0808_hw7.weatherManager;

import com.rodico.duke0808.weatherforyou_duke0808_hw7.util.MyAdapter;
import com.rodico.duke0808.weatherforyou_duke0808_hw7.util.MyWeatherItem;
import com.rodico.duke0808.weatherforyou_duke0808_hw7.realm.MyRealmItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by duke0808 on 07.12.15.
 */
public class WeatherManager {

    private static WeatherManager weatherManager = new WeatherManager(null,null);

    public static WeatherManager getWeatherManager() {
        return weatherManager;
    }

    JSONObject weatherJSON=null;
    static String cityName;
    JSONArray JSONlist =null;
    static RealmResults<MyRealmItem> results;
    MyAdapter adapter;
    Realm realm;

    public WeatherManager(MyAdapter adapter, Realm realm) {
        this.adapter = adapter;
        this.realm = realm;
    }

    public void getWeather(int cityId) {
        new JSONGetter(){
            @Override
            protected void onPostExecute(String s) {
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
    public void loadToRealm() throws JSONException, ParseException {
        int count = JSONlist.length();

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.clear(MyRealmItem.class);
        for (int i=0;i<count;i++){
            MyWeatherItem myWeatherItem = new MyWeatherItem((JSONObject) JSONlist.get(i));
            realm.copyToRealmOrUpdate(myWeatherItem.realmItem);
        }
        realm.commitTransaction();
        RealmQuery<MyRealmItem> query = realm.where(MyRealmItem.class);
        results = query.findAll();
        if (adapter!=null) {
            adapter.notifyDataSetChanged();
        }
//        realm.close();
    }

}

