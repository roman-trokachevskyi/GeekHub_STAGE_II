package com.rodico.duke0808.weatherforyou_duke0808_hw7;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by duke0808 on 17.11.15.
 */
public class MyAdapter extends SimpleAdapter {
    public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

}
