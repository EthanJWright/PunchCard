package com.example.ethanwright.javapunchcard;

/**
 * Created by ethanwright on 5/28/17.
 */
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_recipe, null);
        }

        final PunchCard p = getItem(position);
        Typeface roboto_thin = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Thin.ttf");
        Typeface roboto = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto.ttf");
        if (p != null) {
            int initial_color = Colors.light_color;
            v.setBackgroundColor(initial_color);
            TextView tt1 = (TextView) v.findViewById(R.id.recipe_list_title);
            final TextView tt2 = (TextView) v.findViewById(R.id.recipe_list_detail);
            final TextView tt3 = (TextView) v.findViewById(R.id.recipe_list_subtitle);
            tt1.setTypeface(roboto_thin);
            tt2.setTypeface(roboto);
            tt3.setTypeface(roboto_thin);
            Button cards_buttons = (Button) v.findViewById(R.id.list_view_button);
//            cards_buttons.setBackgroundColor(p.color);
            cards_buttons.setBackgroundTintList(ColorStateList.valueOf(p.color));




            if (tt1 != null) {
                tt1.setText(p.getName());
                if(p.isActive()){
                    int color = Colors.white;
                    tt1.setTextColor(color);
                }
                else{
                    int color = Colors.black;
                    tt1.setTextColor(color);
                }
            }

            if (tt2 != null) {
                FormatTime time = new FormatTime();
                String results = time.getTime(p.getActiveDuration());
                tt2.setText(results);
                int color = Colors.black;
                tt2.setTextColor(color);
                if(p.isActive()) {
                    int background_color = Colors.dark_color;
                    v.setBackgroundColor(background_color);

                    color = Colors.white;
                    tt2.setTextColor(color);
                }
            }
             if (tt3 != null) {
                  if(p.getCategoryName().equals("default")){
                      tt3.setText("");
                 }
                 else{
                      tt3.setText(p.getCategoryName());
                  }

                if(p.isActive()){
                    int color = Colors.white;
                    tt3.setTextColor(color);
                }
                else{
                    int color = Colors.black;
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

