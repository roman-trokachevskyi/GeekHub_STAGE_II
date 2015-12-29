package com.rodico.duke0808.weatherforyou_duke0808_hw7.util;

import com.rodico.duke0808.weatherforyou_duke0808_hw7.realm.MyRealmItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by duke0808 on 17.11.15.
 */
public class MyWeatherItem extends HashMap<String, Object> {
    public MyRealmItem realmItem;
    public MyWeatherItem(JSONObject jsonObject) throws JSONException, ParseException {
        super();
        this.fromJSON(jsonObject);
        convertToRealmItem();
    }

    private void convertToRealmItem() {
        realmItem=new MyRealmItem();
        realmItem.setDeg((String) this.get("wind_angle"));
        realmItem.setDescription((String) this.get("description"));
        realmItem.setDt((Long) this.get("dt"));
        realmItem.setHumidity((String) this.get("humidity"));
        realmItem.setSpeed((String) this.get("wind_speed"));
        realmItem.setIcon((String) this.get("icon"));
        realmItem.setTemp((String) this.get("temp"));
    }

    public void fromJSON(JSONObject json) throws JSONException, ParseException {
         //parsing date,day,month
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String dateStr  = json.getString("dt_txt");
         Date date = sdf.parse(dateStr);
         this.put("dt",date.getTime());
         Calendar c = Calendar.getInstance();
         c.setTime(date);
         String dateNstr = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
         String dayNstr = c.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT,Locale.ENGLISH);
         String monthStr = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
         this.put("date",dateNstr);
         this.put("day",dayNstr);
         this.put("month",monthStr);
         //parsing temp
         String temp = json.getJSONObject("main").get("temp").toString();
         temp+="°C";
         this.put("temp",temp);
         //parsing weather description
         String decript = json.getJSONArray("weather").getJSONObject(0).getString("description");
         this.put("description",decript);
         //parsing ic code
         String ic_code = json.getJSONArray("weather").getJSONObject(0).getString("icon");
         this.put("icon",ic_code);
         //parsing additional features
         String humidity = json.getJSONObject("main").get("humidity").toString();
         String wind_speed = json.getJSONObject("wind").get("speed").toString();
         String wind_angle = json.getJSONObject("wind").get("deg").toString();
         this.put("humidity",humidity);
         this.put("wind_speed",wind_speed);
         this.put("wind_angle",wind_angle);
    }

}
