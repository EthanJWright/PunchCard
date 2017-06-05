package com.example.ethanwright.javapunchcard;

/**
 * Created by ethanwright on 5/28/17.
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;

public class ViewPunchAdapter extends ArrayAdapter<CurrentReportView>{
    private int layoutResource;


    public ViewPunchAdapter(Context context, int layoutResource, ArrayList<CurrentReportView> report) {
        super(context, layoutResource, report);
        this.layoutResource = layoutResource;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_punch, null);
        }

        final CurrentReportView p = getItem(position);

        if (p != null) {
            int initial_color = Colors.dark_color;
            v.setBackgroundColor(initial_color);
            TextView tt1 = (TextView) v.findViewById(R.id.recipe_list_title);
            TextView tt2 = (TextView) v.findViewById(R.id.recipe_list_subtitle);
            TextView tt3 = (TextView) v.findViewById(R.id.recipe_list_detail);




            if (tt1 != null) {
                String results = "Start Time: \n" + p.getStartTime().toString();
                tt1.setText(results);
            }
            if(tt2 != null){
                String results = "End Time: \n" + p.getEndTime().toString();
                tt2.setText(results);
            }
            if(tt3 != null){
                FormatTime ftime = new FormatTime();
                long time = p.getTotalTime();
                tt3.setText(ftime.getTime(time));
            }


        }

        return v;
    }

}

