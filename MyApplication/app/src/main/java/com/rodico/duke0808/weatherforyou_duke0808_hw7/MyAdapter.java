package com.rodico.duke0808.weatherforyou_duke0808_hw7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.rodico.duke0808.weatherforyou_duke0808_hw7.Realm.MyRealmItem;
import com.rodico.duke0808.weatherforyou_duke0808_hw7.Realm.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by duke0808 on 17.11.15.
 */
public class MyAdapter extends RealmBaseAdapter<MyRealmItem> implements ListAdapter {


    public MyAdapter(Context context, RealmResults<MyRealmItem> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }


    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_layout,null);
            TextView dayOfMonthTV = (TextView) convertView.findViewById(R.id.day_of_month_TV);
            TextView dayOfWeekTV = (TextView) convertView.findViewById(R.id.day_of_week_TV);
            TextView monthNameTV = (TextView) convertView.findViewById(R.id.month_name_TV);
            TextView temperatureTV = (TextView) convertView.findViewById(R.id.temperature_TV);
            TextView descriptionTV = (TextView) convertView.findViewById(R.id.description_TV);
            ImageView listIV = (ImageView) convertView.findViewById(R.id.list_image_view);
            convertView.setTag(viewHolder);
            viewHolder.dayOfMonthTV=dayOfMonthTV;
            viewHolder.dayOfWeekTV=dayOfWeekTV;
            viewHolder.monthNameTV=monthNameTV;
            viewHolder.temperatureTV=temperatureTV;
            viewHolder.descriptionTV=descriptionTV;
            viewHolder.listIV=listIV;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyRealmItem item = realmResults.get(position);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date(item.getDt()));
        String dateNstr = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String dayNstr = c.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT, Locale.ENGLISH);
        String monthStr = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        viewHolder.dayOfMonthTV.setText(dateNstr);
        viewHolder.dayOfWeekTV.setText(dayNstr);
        viewHolder.monthNameTV.setText(monthStr);
        viewHolder.temperatureTV.setText(String.valueOf(item.getTemp()));
        viewHolder.descriptionTV.setText(item.getDescription());
        String img_cd = realmResults.get(position).getIcon();
        String url = "http://openweathermap.org/img/w/"+img_cd+".png";
        Picasso.with(MainActivity.context).load(url).into(viewHolder.listIV);

        return convertView;
    }
}
