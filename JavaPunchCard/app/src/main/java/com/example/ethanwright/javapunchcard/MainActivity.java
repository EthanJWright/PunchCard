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
import android.view.View.OnClickListener;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    final CardView punchCardInterface = new CardView();
    public Button but;

    private void updateUI(){
        but.setText(CurrentCard.getCard().getName() + "\n" + CurrentCard.getFormattedTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create Button
        final Button card = (Button) findViewById(R.id.current_card);
        but = card;

        // Set Up Initial Current Card
        punchCardInterface.Current_Card_Time();

        // Get values set up for button
        card.setTextSize(20);
        updateUI();

        // color the button correctly
        int color = Color.argb(255, 55, 79, 79);
        card.setBackgroundTintList(ColorStateList.valueOf(color));
        // Done Creating Card


        // Set up options on
        card.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Timer timer = new Timer();
                // Check to see if it has been set to active or not
                if(CurrentCard.getCard().isActive() == false) {
                    // If it isn't active, a click means punch in
                    punchCardInterface.PunchIn();
                    // On punch in we need to update the display every second, we will do that here
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    // Update the UI
                                    updateUI();

                                }
                            });
                        }
                    }, 0, 1000);
                }
                else{
                    // If card is already active, we need to punch out
                    punchCardInterface.PunchOut();
                    // Since it was active there is running timer, cancel that
                    timer.cancel();
                    // Now update the new UI
                    updateUI();

                }

            }


        });

        // Create our Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_card);
        fab.setBackgroundTintList(ColorStateList.valueOf(color));

        // Designate what to do when clicked
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Currently set to nothing
                Snackbar.make(view, "filler", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

            }
        });

        // Create the menu drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Open and close the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Wait for user to click on menu option
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
        // Handle navigation view item clicks
        int id = item.getItemId();

        if (id == R.id.nav_new_card) {
            // Create a new activity, store results with request code 1
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
        // This means returning from new card create
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                // Get the name from the created card
                String name = data.getStringExtra("name");
                String category = data.getStringExtra("category");
                // Add the results to our cards
                punchCardInterface.addCard(name, category);
                updateUI();

            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
