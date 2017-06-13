package com.example.ethanwright.javapunchcard;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.TextView;

public class CreateCard extends AppCompatActivity {

    long setGoal = 0;

    public void returning(final String name, String category){
            final String _category = "default";
            //here
        if(category.toLowerCase().equals("category") || category.toLowerCase().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("No Category Set")
                    .setMessage("Would you like to create your card without a category?")
                    .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                    .setPositiveButton(android.R.string.ok, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // do the acknowledged action, beware, this is run on UI thread
                            doFinish(name, _category);
                        }
                    })
                    .create()
                    .show();
            //here
        }
        else{
            doFinish(name, category);
        }
    }

    public void doFinish(String name, String category){
        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("category", category);
        intent.putExtra("goal", setGoal);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_card);
        fab.setImageResource(R.drawable.ic_add_car);
        int color = Colors.light_color;
        fab.setBackgroundTintList(ColorStateList.valueOf(color));


        final Button goal = (Button) findViewById(R.id.set_goal);
        final TextView goalText = (TextView) findViewById(R.id.goal_text_create);
        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGoal += 3600000;
                FormatTime ftime = new FormatTime();
                goalText.setText(ftime.getTime(setGoal));
            }
        });

        final EditText new_name = (EditText) findViewById(R.id.new_card_name);
        final EditText new_category = (EditText) findViewById(R.id.new_card_category);


        // Set up action when added is pressed
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = new_name.getText().toString();
                String category = new_category.getText().toString();
                returning(name, category);
            }
        });


        // Set a function for if return is pressed while in new_name
         new_name.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Enter Submission
                    returning(new_name.getText().toString(), new_category.getText().toString());
                    return true;
                }
                return false;
            }
        });

        new_category.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Enter Submission
                    returning(new_name.getText().toString(), new_category.getText().toString());
                    return true;
                }
                return false;
            }
        });


        }

}
