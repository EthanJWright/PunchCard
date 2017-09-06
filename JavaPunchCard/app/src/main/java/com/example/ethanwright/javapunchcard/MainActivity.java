package com.example.ethanwright.javapunchcard;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.NotificationCompat;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import java.util.Iterator;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    final public CardView punchCardInterface = new CardView();
    public Button but;
    public boolean isSettingGoal = true;


    private void startNotification(){
        String extra = "";
        if(punchCardInterface.current.getCard().getGoal() != 0){
            extra = punchCardInterface.getGoalPercent(punchCardInterface.getCurrent().getCard());
            extra = "  (" + extra.substring(0,extra.indexOf(".")) + "%)";
        }
        FormatTime ftime = new FormatTime();
                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.ic_stat_current_card)
                                .setContentTitle(punchCardInterface.getCurrent().getName())
                                .setContentText(ftime.getTime(punchCardInterface.getCurrent().getCard().getActiveDuration()) + extra);
                int mNotificationId = 001;

                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
            }

    private void stopNotification(){
        NotificationManager notifManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancelAll();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateUI() {
        Typeface roboto_thin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        Typeface roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto.ttf");
        but.setTypeface(roboto_thin);
        int color = Colors.light_color;
        FormatTime ftime = new FormatTime();
        if (!punchCardInterface.getCurrent().isActive()) {
            stopNotification();
            but.setBackgroundTintList(ColorStateList.valueOf(color));
            int text = Colors.black;
            but.setTextColor(text);
        } else {
            startNotification();
            int background_color = Colors.colorPrimaryDark;
            but.setBackgroundTintList(ColorStateList.valueOf(background_color));
            int text = Colors.white;
            but.setTextColor(text);
        }
        String name = punchCardInterface.getCurrent().getCard().getName();

        /* Check */
        String category = punchCardInterface.getCurrent().getCard().getCategoryName();
        if(category.equals("default")){
            category = "";
        }
        else{
            category = "\n" + category;
        }

        String result = ftime.getTime(punchCardInterface.getCurrent().getCard().getActiveDuration());

        String extra;
        String returning;
        if(punchCardInterface.current.getCard().getGoal() != 0){
            extra = punchCardInterface.getGoalPercent(punchCardInterface.getCurrent().getCard());
            String substr = extra.substring(0,extra.indexOf("."));
            extra = "Accomplished: " + substr + "%" + "\n Total Goal: " + ftime.getTime(punchCardInterface.getCurrent().getCard().getGoal());
            returning = name + "  " + category + "\n" + result + "\n" + extra;
        }
        else{
            returning = name +  category + "\n" + result;
        }

        but.setText(returning);



    }

    public void deletePrompt(final PunchCard card) {
        if (punchCardInterface.model.getAllCards().size() == 1) {
            new AlertDialog.Builder(this)
                    .setTitle("Last Card")
                    .setMessage("You must have one card")
                    .setNegativeButton(android.R.string.cancel, null)
                    .create()
                    .show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Deleted Card")
                    .setMessage("Are you sure you want to delete this card?")
                    .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // do the acknowledged action, beware, this is run on UI thread
                            punchCardInterface.removeCard(card);
                            punchCardInterface.getCurrent().setCurrentCard(punchCardInterface.model.getAllCards().get(0), punchCardInterface.model);
                            updateUI();
                        }
                    })
                    .create()
                    .show();

        }
    }

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public void startInterfaceTimer(){
     // Create the timer to set on click
    Timer timer = new Timer();
    // Check to see if it has been set to active or not
    if(punchCardInterface.current.isActive()) {
        int background_color = Colors.colorPrimaryDark;
        but.setBackgroundTintList(ColorStateList.valueOf(background_color));
        int color = Colors.white; //white
        but.setTextColor(color);

        // If punched in we need to update the display every second, we will do that here
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
        }, 0, 500);
    }
    else {
        int initial_color = Colors.light_color;
        but.setBackgroundTintList(ColorStateList.valueOf(initial_color));
        int color = Colors.black;
        but.setTextColor(color);
        // If card is already active, we need to punch out
        punchCardInterface.PunchOut();
        // Since it was active there is running timer, cancel that
        timer.cancel();
        // Now update the new UI
        updateUI();

    }
   }

    // Fucking here
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public void punchInOut(){
    // Create the timer to set on click
    Timer timer = new Timer();
    // Check to see if it has been set to active or not
    if(!punchCardInterface.current.isActive()) {
        punchCardInterface.PunchIn();
        startInterfaceTimer();
    }
    else {
        punchCardInterface.PunchOut();
        startInterfaceTimer();
    }

}

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public void changeValues(Long amount){
    long adding = amount * subtracting;
        if (isSettingGoal) {
            punchCardInterface.getCurrent().getCard().addGoal(adding);
        } else {
            punchCardInterface.getCurrent().getCard().addUserBuffer(adding);
        }
        updateUI();
}


    private int subtracting = 1;

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

        ArrayList<PunchCard> getCards = new ArrayList<>();

        SharedPreferences settings = getSharedPreferences("cards", 0);

        if(settings.contains("punchcard")) {
            String JSONgetCards = settings.getString("punchcard", null);
            Type type = new TypeToken<ArrayList<PunchCard>>() {
            }.getType();
            getCards = new Gson().fromJson(JSONgetCards, type);

            ParcelPackageManager manager = new ParcelPackageManager();
            manager.setModel(punchCardInterface.model);
            manager.insertAll(getCards);
        }

        if(settings.contains("current")) {
            Gson cgson = new Gson();
            String currentJSON = settings.getString("current", null);
            PunchCard currentCard = cgson.fromJson(currentJSON, PunchCard.class);
            punchCardInterface.current.setCurrentCard(currentCard, punchCardInterface.model);
        }

        if(getCards.size() == 0) {
            // Set Up Initial Current Card
            PunchCard default_card = new PunchCard();
            default_card.generateNewCard("Default Card", 4);
            default_card.setCategoryName("other");
            punchCardInterface.addCard(default_card);
        }

        // Get values set up for button
        card.setTextSize(20);

        // Setup the UI
        startInterfaceTimer();



        final Typeface roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto.ttf");
        final Button addMin = (Button) findViewById(R.id.add_one);
        addMin.setTypeface(roboto);
         addMin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeValues((long) 60000);
            }
        });

        final Button addFifteen = (Button) findViewById(R.id.add_fifteen);
        addFifteen.setTypeface(roboto);
         addFifteen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeValues((long) 900000 );
            }
        });

        final Button addThirty = (Button) findViewById(R.id.add_thirty);
        addThirty.setTypeface(roboto);
         addThirty.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeValues((long) 1800000);
            }
        });

        final Button addHour = (Button) findViewById(R.id.add_hour);
        addHour.setTypeface(roboto);
         addHour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeValues((long) 3600000 );
            }
        });


        final Button setSubtract = (Button) findViewById(R.id.minus_sign);
        final Button setAdd = (Button) findViewById(R.id.plus_sign);
        setSubtract.setBackgroundTintList(ColorStateList.valueOf(Colors.light_color));
        setAdd.setBackgroundTintList(ColorStateList.valueOf(Colors.colorPrimaryDark));
        setAdd.setTypeface(roboto);
        setSubtract.setTypeface(roboto);

        setSubtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addMin.setText("-" + "1 min");
                addFifteen.setText("-" + "15 min");
                addThirty.setText("-" + "30 min");
                addHour.setText("-" + "1 hour");
                setSubtract.setBackgroundTintList(ColorStateList.valueOf(Colors.colorPrimaryDark));
                setAdd.setBackgroundTintList(ColorStateList.valueOf(Colors.light_color));
                subtracting = -1;
            }
        });

        setAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addMin.setText("+" + "1 min");
                addFifteen.setText("+" + "15 min");
                addThirty.setText("+" + "30 min");
                addHour.setText("+" + "1 hour");
                subtracting = 1;
                setSubtract.setBackgroundTintList(ColorStateList.valueOf(Colors.light_color));
                setAdd.setBackgroundTintList(ColorStateList.valueOf(Colors.colorPrimaryDark));
            }
        });


        final Button deleteButton = (Button) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final PunchCard delete_card = punchCardInterface.getCurrent().getCard();
                deletePrompt(delete_card);

            }
        });

        final Button clearAndSave = (Button) findViewById(R.id.clear_all);
         clearAndSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                punchCardInterface.getCurrent().getCard().clearProgress();
                updateUI();
            }
        });

        final Button removeGoalButton = (Button) findViewById(R.id.remove_hour_goal);
        final Button addGoalButton = (Button) findViewById(R.id.add_hour_goal);

        addGoalButton.setTypeface(roboto);
        removeGoalButton.setTypeface(roboto);
        addGoalButton.setBackgroundTintList(ColorStateList.valueOf(Colors.colorPrimaryDark));
        removeGoalButton.setBackgroundTintList(ColorStateList.valueOf(Colors.white));

        addGoalButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addGoalButton.setBackgroundTintList(ColorStateList.valueOf(Colors.colorPrimaryDark));
                removeGoalButton.setBackgroundTintList(ColorStateList.valueOf(Colors.white));
                isSettingGoal = true;
                updateUI();
            }
        });

        removeGoalButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addGoalButton.setBackgroundTintList(ColorStateList.valueOf(Colors.light_color));
                removeGoalButton.setBackgroundTintList(ColorStateList.valueOf(Colors.colorPrimaryDark));
                isSettingGoal = false;
                updateUI();
            }
        });

        // Set up options on
        card.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // Create an animation for button when clicked
                final Animation animation = new TranslateAnimation(0,5,0,0);
                // set Animation for 5 sec
                animation.setDuration(200);
                //for button stops in the new position.
                animation.setFillAfter(true);
                card.startAnimation(animation);

                punchInOut();

            }


        });

        // Create our Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_card);

        int color = Colors.light_color;
        fab.setBackgroundTintList(ColorStateList.valueOf(color));

        // Designate what to do when clicked

        final Intent allCards = new Intent(this, ViewAllCards.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BundleCards cards = new BundleCards();
                ArrayList<PunchCard> make_stack = punchCardInterface.model.getAllCards();
                cards.setCards(make_stack);
                allCards.putExtra("parcelable_extra", cards);
                allCards.putExtra("current_card", punchCardInterface.current.getCard());
                startActivityForResult(allCards, 2);

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
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
            Intent categories = new Intent(this, ViewCategories.class);
            BundleCards cards = new BundleCards();
            ArrayList<PunchCard> make_stack = punchCardInterface.model.getAllCards();
            cards.setCards(make_stack);
            categories.putExtra("card_parcel", cards);
            categories.putExtra("current_card", punchCardInterface.getCurrent().getCard());
            startActivityForResult(categories, 2);

        } else if (id == R.id.nav_stats) {
            Intent report = new Intent(this, Report.class);
            BundleCards cards = new BundleCards();
            ArrayList<PunchCard> make_stack = punchCardInterface.model.getAllCards();
            cards.setCards(make_stack);
            PunchCard current = punchCardInterface.getCurrent().getCard();
            report.putExtra("parcelable_extra", current);
            report.putExtra("card_bundle", cards);
            startActivity(report);
        } else if (id == R.id.nav_reset_category){
            Intent categories = new Intent(this, ResetByCategory.class);
            BundleCards cards = new BundleCards();
            ArrayList<PunchCard> make_stack = punchCardInterface.model.getAllCards();
            cards.setCards(make_stack);
            categories.putExtra("card_parcel", cards);
            categories.putExtra("current_card", punchCardInterface.getCurrent().getCard());
            startActivityForResult(categories, 2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // This means returning from new card create
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                // Get the name from the created card
                String name = data.getStringExtra("name");
                String category = data.getStringExtra("category");
               long newGoal = data.getLongExtra("goal", 0);
                but.setText(category);
                // Add the results to our cards
                PunchCard new_card = new PunchCard();
                new_card.addGoal(newGoal);
                new_card.generateNewCard(name, 4);
                new_card.setCategoryName(category);
                new_card.setActive(false);
                punchCardInterface.addCard(new_card);
                but.setText(punchCardInterface.getCurrent().getCard().getName() + "\n" + punchCardInterface.getCurrent().getFormattedTime());
                updateUI();
            }

           }
           // go here
           if(requestCode == 2) {
               // If returning from select all cards screen
               if(resultCode == RESULT_OK){
                   // Get bundle of all modified cards
                   BundleCards new_current = data.getParcelableExtra("card_parcel");
                   PunchCard current_card = data.getParcelableExtra("current_card");
                   ParcelPackageManager manager = new ParcelPackageManager();
                   manager.insertAll(new_current.getCards());
                   // rebuild the model
                   punchCardInterface.model = manager.getModel();
                   punchCardInterface.current.setCurrentCard(current_card, punchCardInterface.model);

                   // Start updating the UI
                   startInterfaceTimer();
                }

            }
    }

    @Override
    protected void onStop(){
        super .onStop();

        SharedPreferences settings = getSharedPreferences("cards", 0);
        SharedPreferences.Editor editor = settings.edit();

        Gson gson = new Gson();

        ArrayList<PunchCard> storeCards = punchCardInterface.model.getAllCards();
        PunchCard current = punchCardInterface.getCurrent().getCard();
        String JSONcurrent = gson.toJson(current);

        String json = gson.toJson(storeCards);

        editor.putString("punchcard", json);
        editor.putString("current", JSONcurrent);
        editor.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
