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
            v = vi.inflate(R.layout.list_item_recipe, null);
        }

        final CurrentReportView p = getItem(position);

        if (p != null) {
            int initial_color = Color.argb(255, 55, 79, 79);
            v.setBackgroundColor(initial_color);
            TextView tt1 = (TextView) v.findViewById(R.id.recipe_list_title);
            TextView tt2 = (TextView) v.findViewById(R.id.recipe_list_subtitle);




            if (tt1 != null) {
                tt1.setText("you");
            }
            if(tt2 != null){
                tt2.setText("hey");
            }


        }

        return v;
    }

}

