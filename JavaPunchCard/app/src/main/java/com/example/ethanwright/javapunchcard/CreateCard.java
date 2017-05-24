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
import android.support.v4.app.NavUtils;

public class CreateCard extends AppCompatActivity {

    public void returning(String name, String category){
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
                    // Move focus to category
                    Selection.setSelection((Editable) new_category.getText(),new_name.getSelectionStart());
                    new_category.setText("");
                    new_category.requestFocus();
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
                    // Move focus to category
                    //Selection.setSelection((Editable) new_category.getText(),new_name.getSelectionStart());
                    String name = new_name.getText().toString();
                    String category = new_category.getText().toString();
                    returning(name, category);
                    return true;
                }
                return false;
            }
        });

        }

}
