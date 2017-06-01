package com.example.ethanwright.javapunchcard;

/**
 * Created by ethanwright on 5/28/17.
 */

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Button;
import android.view.View.OnClickListener;

public class CategoryAdapter extends ArrayAdapter<CardDeck>{
    private int layoutResource;


    public CategoryAdapter(Context context, int layoutResource, ArrayList<CardDeck> decks) {
        super(context, layoutResource, decks);
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
            v = vi.inflate(R.layout.list_item_category, null);
        }

        final CardDeck p = getItem(position);

        if (p != null) {
//            v.setBackgroundColor(p.getColor());
            int initial_color = Color.argb(255, 55, 79, 79);
            v.setBackgroundColor(initial_color);
            TextView tt1 = (TextView) v.findViewById(R.id.recipe_list_title);
            TextView tt2 = (TextView) v.findViewById(R.id.recipe_list_subtitle);
            TextView tt3 = (TextView) v.findViewById(R.id.recipe_list_detail);
            ImageView image = (ImageView) v.findViewById(R.id.list_view_image);

            image.setColorFilter(p.getColor());

            if (tt1 != null) {
                String catName;
                if(p.getCategory().length() > 20) {
                    catName = p.getCategory().substring(0, 20) + "...";
                }
                else{
                    catName = p.getCategory();
                }
                String result = "Category: " + catName;
                tt1.setText(result);
                if(p.getDeck().get(0).isActive()){
                    int color = Color.argb(255, 255, 255, 255);
                    tt1.setTextColor(color);
                    int background_color = Color.argb(255, 75, 99, 99);
                    v.setBackgroundColor(background_color);
                }
                else{
                    int color = Color.argb(255, 0, 0, 0);
                    tt1.setTextColor(color);
                }

            }

            if (tt2 != null) {
                if(p.getDeck().get(0).isActive()){
                    int color = Color.argb(255, 255, 255, 255);
                    tt2.setTextColor(color);
                }
                else{
                    int color = Color.argb(255, 0, 0, 0);
                    tt2.setTextColor(color);
                }


                String result = "Number in Category: " + Integer.toString(p.getTotalCount());
//                String result = "Random: " + Double.toString(p.random);
                tt2.setText(result);
            }
            if(tt3 != null){
                FormatTime ftime = new FormatTime();
                String result = ftime.getTime(p.getTimeWorked());

                tt3.setText(result);
                 if(p.getDeck().get(0).isActive()){
                    int color = Color.argb(255, 255, 255, 255);
                    tt3.setTextColor(color);
                }
                else{
                    int color = Color.argb(255, 0, 0, 0);
                    tt3.setTextColor(color);
                }

            }


        }

        return v;
    }

}
