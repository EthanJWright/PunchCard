package com.example.ethanwright.javapunchcard;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;
import android.text.Selection;
import android.text.Editable;
import android.content.Intent;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class CreateCard extends AppCompatActivity {


    public void returning(final String name, String category){
            final String _category = "default4";
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
        int color = Color.argb(255, 55, 79, 79);
        fab.setBackgroundTintList(ColorStateList.valueOf(color));



        final EditText new_name = (EditText) findViewById(R.id.new_card_name);
        final EditText new_category = (EditText) findViewById(R.id.new_card_category);

        new_name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new_name.setText("");

            }
        });

        new_category.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new_category.setText("");

            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = new_name.getText().toString();
                String category = new_category.getText().toString();
                String response = name + " in category " + category + " created.";
                 Snackbar.make(view, response, Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
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
