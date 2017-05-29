package com.example.ethanwright.javapunchcard;

/**
 * Created by ethanwright on 5/28/17.
 */
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Button;
import android.view.View.OnClickListener;
public class CardAdapter extends ArrayAdapter<PunchCard>{
    private int layoutResource;


    public CardAdapter(Context context, int layoutResource, ArrayList<PunchCard> PunchCards) {
        super(context, layoutResource, PunchCards);
        this.layoutResource = layoutResource;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_recipe, null);
        }

        final PunchCard p = getItem(position);

        if (p != null) {
            int initial_color = Color.argb(255, 55, 79, 79);
            v.setBackgroundColor(initial_color);
            TextView tt1 = (TextView) v.findViewById(R.id.recipe_list_title);
            final TextView tt2 = (TextView) v.findViewById(R.id.recipe_list_detail);
            final TextView tt3 = (TextView) v.findViewById(R.id.recipe_list_subtitle);
            Button cards_buttons = (Button) v.findViewById(R.id.list_view_button);



            if (tt1 != null) {
                tt1.setText(p.getName());
                if(p.isActive()){
                    int color = Color.argb(255, 255, 255, 255);
                    tt1.setTextColor(color);
                }
                else{
                    int color = Color.argb(255, 0, 0, 0);
                    tt1.setTextColor(color);
                }
            }

            if (tt2 != null) {
                FormatTime time = new FormatTime();
                String results = time.getTime(p.getLogger().getActive_duration());
                tt2.setText(results);
                int color = Color.argb(255, 0, 0, 0);
                tt2.setTextColor(color);
                if(p.isActive()) {
                    int background_color = Color.argb(255, 75, 99, 99);
                    v.setBackgroundColor(background_color);

                    color = Color.argb(255, 255, 255, 255);
                    tt2.setTextColor(color);
                }
            }
             if (tt3 != null) {
                tt3.setText(p.getCategoryName());
                if(p.isActive()){
                    int color = Color.argb(255, 255, 255, 255);
                    tt3.setTextColor(color);
                }
                else{
                    int color = Color.argb(255, 0, 0, 0);
                    tt3.setTextColor(color);
                }
            }

            if (cards_buttons != null){
                cards_buttons.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if(p.isActive()){
                            p.setActive(false);
                            notifyDataSetChanged();
                        }
                        else{
                            p.setActive(true);
                            notifyDataSetChanged();
                        }

                    }
                });

                //here


            }

        }

        return v;
    }

}

