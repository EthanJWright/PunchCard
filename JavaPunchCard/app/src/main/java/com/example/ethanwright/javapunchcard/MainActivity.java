package com.example.ethanwright.javapunchcard;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.CountDownTimer;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Handler;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    final CardView testView = new CardView();
    public Button but;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create Card
        final Button card = (Button) findViewById(R.id.current_card);
        but = card;
        testView.Current_Card_Time();
        // Task to be executed every second

        card.setTextSize(20);
        card.setText(CurrentCard.getName() + "\n" + CurrentCard.getFormatedTime());
        int color = Color.argb(255, 55, 79, 79);
        card.setBackgroundTintList(ColorStateList.valueOf(color));




        // Done Creating Card
        // Setup click actions

        // Try threading

        // Done try threading

        // Check if card is active, either punch in or out

        card.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Timer timer = new Timer();
                if(CurrentCard.isActive() == false) {
                    testView.PunchIn();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    //update ui here
                                    String name_of_button = CurrentCard.getName();
                                    card.setText(name_of_button + "\n" + CurrentCard.getFormatedTime());
                                }
                            });
                        }
                    }, 1000, 1000);
                }
                else{
                    testView.PunchOut();
                    timer.cancel();
                    long time = CurrentCard.getCurrent_duration();
                    FormatTime ftime = new FormatTime();
                    String response = ftime.getTime(time);
                    String name_of_button = CurrentCard.getName();
                    card.setText(name_of_button + "\n" +response);

                }

            }


        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_card);



        fab.setBackgroundTintList(ColorStateList.valueOf(color));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "filler", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new_card) {
            Intent intent = new Intent(this, CreateCard.class);
            startActivityForResult(intent, 1);

        } else if (id == R.id.nav_categories) {

        } else if (id == R.id.nav_stats) {

        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_settings){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String name = data.getStringExtra("name");
                String category = data.getStringExtra("category");
                //but.setText(name + "\n" + category);
                testView.addCard(name, category);
                but.setText(CurrentCard.getName() + "\n" + CurrentCard.getFormatedTime());

            }
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
