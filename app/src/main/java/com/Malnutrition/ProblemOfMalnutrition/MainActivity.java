package com.Malnutrition.ProblemOfMalnutrition;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.Malnutrition.ProblemOfMalnutrition.ui.Home.NavHomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static  String meal_time_name;
    public static Double w;
    public static Double h;
    public static Double BMI;
    public static int mini_carbo;
    public static Double carbo_spicified_beakfast;
    public static Double protein_spicified_beakfast;
    public static Double fat_spicified_beakfast;
    public static Double carbo_spicified_dinner;
    public static Double protein_spicified_dinner;
    public static Double fat_spicified_dinner;
    public static Double carbo_spicified_launch;
    public static Double protein_spicified_launch;
    public static Double fat_spicified_launch;
    public static Double carbo_spicified_beakfast_max_for_medium_bmi;
    public static Double protein_spicified_beakfast_max_for_medium_bmi;
    public static Double fat_spicified_beakfast_max_for_medium_bmi;
    public static Double carbo_spicified_dinner_max_for_medium_bmi;
    public static Double protein_spicified_dinner_max_for_medium_bmi;
    public static Double fat_spicified_dinner_max_for_medium_bmi;
    public static Double carbo_spicified_launch_max_for_medium_bmi;
    public static Double protein_spicified_launch_max_for_medium_bmi;
    public static Double fat_spicified_launch_max_for_medium_bmi;
    public static Double carbo_spicified_beakfast_max_for_under_bmi;
    public static Double protein_spicified_beakfast_max_for_under_bmi;
    public static Double fat_spicified_beakfast_max_for_under_bmi;
    public static Double carbo_spicified_dinner_max_for_under_bmi;
    public static Double protein_spicified_dinner_max_for_under_bmi;
    public static Double fat_spicified_dinner_max_for_under_bmi;
    public static Double carbo_spicified_launch_max_for_under_bmi;
    public static Double protein_spicified_launch_max_for_under_bmi;
    public static Double fat_spicified_launch_max_for_under_bmi;

    public static Double carbo_spicified;
    public static Double protein_spicified;
    public static Double fat_spicified;
    public static Double carbo_spicified_max_for_medium_bmi;
    public static Double protein_spicified_max_for_medium_bmi;
    public static Double fat_spicified_max_for_medium_bmi;
    public static Double carbo_spicified_max_for_under_bmi;
    public static Double protein_spicified_max_for_under_bmi;
    public static Double fat_spicified_max_for_under_bmi;


    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth mAuth;

    public static SharedPreferences SP ;
    public static String filename = "Keys";

    Button basket , history ;
    Boolean Is_checked_orders_today=false;
    Switch reminder ;
    int  breakfast_timehours , breakfast_timeminutes , lunch_timehours ,lunch_timeminutes , dinner_timehours, dinner_timeminutes ;
    Button reset ;
    int time_breakfast,time_Launch,time_Dinner;
    Model_IS_CHECK_Today_Orders check_daily=new Model_IS_CHECK_Today_Orders(Is_checked_orders_today);
    public static Boolean is_blood_pressure_have;
    public static Boolean is_vegetarian;
    public static Boolean is_diabetes_have;

    public static String meal_name_shared_ref;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        showdialog();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000);

        basket = findViewById(R.id.basket_btn);
        history = findViewById(R.id.history_btn);

        SharedPreferences pref = getSharedPreferences(breakfast_meal.TIME_PREF,MODE_PRIVATE);
        breakfast_timehours = pref.getInt(breakfast_meal.BREAKFAST_HOURS , 0);
        breakfast_timeminutes = pref.getInt(breakfast_meal.BREAKFAST_MINUTES , 0 );
        dinner_timehours = pref.getInt(Dinner_meal.DINNER_HOURS , 0);
        dinner_timeminutes = pref.getInt(Dinner_meal.DINNER_MINUTES , 0 );
        lunch_timehours = pref.getInt(lunch_meal.LUNCH_HOURS , 0);
        lunch_timeminutes = pref.getInt(lunch_meal.LUNCH_MINUTES , 0 );
        is_vegetarian=false;
        is_blood_pressure_have=false;
        is_diabetes_have=false;


        reference.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               w = dataSnapshot.child("Medical Info").child("weight").getValue(Double.class);
               h = dataSnapshot.child("Medical Info").child("hieght").getValue(Double.class);

               if (w != null && h != null) {
                   BMI = w / (h/100 * h/100);


                   if (BMI != null && MainActivity.BMI < 18.5)
                   {
                       //Underweight
                       carbo_spicified_beakfast = 93.75;
                       protein_spicified_beakfast = 37.5  ;
                       fat_spicified_beakfast = 25.0;
                       carbo_spicified_beakfast_max_for_under_bmi=300.0;
                       protein_spicified_beakfast_max_for_under_bmi=300.0;
                       fat_spicified_beakfast_max_for_under_bmi=300.0;

                       carbo_spicified_launch=125.0;
                       protein_spicified_launch=50.0;
                       fat_spicified_launch=33.33;
                       carbo_spicified_launch_max_for_under_bmi=400.0;
                       protein_spicified_launch_max_for_under_bmi=400.0;
                       fat_spicified_launch_max_for_under_bmi=400.0;

                       carbo_spicified_dinner=93.75;
                       protein_spicified_dinner=37.5;
                       fat_spicified_dinner=25.0;
                       carbo_spicified_dinner_max_for_under_bmi=300.0;
                       protein_spicified_dinner_max_for_under_bmi=300.0;
                       fat_spicified_dinner_max_for_under_bmi=300.0;
                   }
                   else if (BMI != null && MainActivity.BMI > 25)
                   {
                       // OVERWEIGHT
                       carbo_spicified_beakfast = 56.25;
                       protein_spicified_beakfast = 22.5 ;
                       fat_spicified_beakfast = 15.0;

                       carbo_spicified_launch=75.0;
                       protein_spicified_launch=30.0;
                       fat_spicified_launch=20.0;

                       carbo_spicified_dinner=56.25;
                       protein_spicified_dinner=22.5;
                       fat_spicified_dinner=15.0;

                   }
                   else if (BMI!=null && MainActivity.BMI >18.5 && MainActivity.BMI<25)
                   {
                       carbo_spicified_beakfast = 66.0;
                       protein_spicified_beakfast = 26.0 ;
                       fat_spicified_beakfast = 18.0;
                       carbo_spicified_beakfast_max_for_medium_bmi=84.0;
                       protein_spicified_beakfast_max_for_medium_bmi=34.0;
                       fat_spicified_beakfast_max_for_medium_bmi=23.0;

                       carbo_spicified_launch=88.0;
                       protein_spicified_launch=35.0;
                       fat_spicified_launch=23.0;
                       carbo_spicified_launch_max_for_medium_bmi=113.0;
                       protein_spicified_launch_max_for_medium_bmi=45.0;
                       fat_spicified_launch_max_for_medium_bmi=30.0;


                       carbo_spicified_dinner=66.0;
                       protein_spicified_dinner=26.0;
                       fat_spicified_dinner=18.0;
                       carbo_spicified_dinner_max_for_medium_bmi=84.0;
                       protein_spicified_dinner_max_for_medium_bmi=34.0;
                       fat_spicified_dinner_max_for_medium_bmi=23.0;

                   }

                   Calendar c1 = Calendar.getInstance();
                   final int Current_Hr24=c1.get(Calendar.HOUR_OF_DAY);

                   reference.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener()
                   {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           is_vegetarian=dataSnapshot.child("Medical Info").child("vegetarian").getValue(Boolean.class);
                           is_blood_pressure_have=dataSnapshot.child("Medical Info").child("blood_pressure").getValue(Boolean.class);
                           is_diabetes_have=dataSnapshot.child("Medical Info").child("diabetes").getValue(Boolean.class);

                           time_breakfast = dataSnapshot.child("Meals").child("Breakfast").child("hours").getValue(int.class);
                           time_Launch = dataSnapshot.child("Meals").child("Dinner").child("hours").getValue(int.class);
                           time_Dinner = dataSnapshot.child("Meals").child("The Lunch").child("hours").getValue(int.class);

                           if (0<Current_Hr24&&Current_Hr24<=time_breakfast&&Current_Hr24<time_Launch&&Current_Hr24<time_Dinner)
                           {
                               carbo_spicified=MainActivity.carbo_spicified_beakfast;
                               protein_spicified=MainActivity.protein_spicified_beakfast;
                               fat_spicified=MainActivity.fat_spicified_beakfast;

                               carbo_spicified_max_for_medium_bmi=MainActivity.carbo_spicified_beakfast_max_for_medium_bmi;
                               protein_spicified_max_for_medium_bmi=MainActivity.protein_spicified_beakfast_max_for_medium_bmi;
                               fat_spicified_max_for_medium_bmi=MainActivity.fat_spicified_beakfast_max_for_medium_bmi;

                               carbo_spicified_max_for_under_bmi=MainActivity.carbo_spicified_beakfast_max_for_under_bmi;
                               protein_spicified_max_for_under_bmi=MainActivity.protein_spicified_beakfast_max_for_under_bmi;
                               fat_spicified_max_for_under_bmi=MainActivity.fat_spicified_beakfast_max_for_under_bmi;

                               meal_time_name="BreakFast";
                               TastyToast.makeText(getApplicationContext(), "Welcome In BreakFast Meal", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                           }
                           else if (time_breakfast<Current_Hr24&&Current_Hr24<=time_Launch&&Current_Hr24<time_Dinner)
                           {
                               carbo_spicified=MainActivity.carbo_spicified_launch;
                               protein_spicified=MainActivity.protein_spicified_launch;
                               fat_spicified=MainActivity.fat_spicified_launch;

                               carbo_spicified_max_for_medium_bmi=MainActivity.carbo_spicified_launch_max_for_medium_bmi;
                               protein_spicified_max_for_medium_bmi=MainActivity.protein_spicified_launch_max_for_medium_bmi;
                               fat_spicified_max_for_medium_bmi=MainActivity.fat_spicified_launch_max_for_medium_bmi;

                               carbo_spicified_max_for_under_bmi=MainActivity.carbo_spicified_launch_max_for_under_bmi;
                               protein_spicified_max_for_under_bmi=MainActivity.protein_spicified_launch_max_for_under_bmi;
                               fat_spicified_max_for_under_bmi=MainActivity.fat_spicified_launch_max_for_under_bmi;

                               meal_time_name="Lunch";
                               TastyToast.makeText(getApplicationContext(), "Welcome In Lunch Meal", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                           }
                           else
                           {
                               carbo_spicified=MainActivity.carbo_spicified_dinner;
                               protein_spicified=MainActivity.protein_spicified_dinner;
                               fat_spicified=MainActivity.fat_spicified_dinner;

                               carbo_spicified_max_for_medium_bmi=MainActivity.carbo_spicified_dinner_max_for_medium_bmi;
                               protein_spicified_max_for_medium_bmi=MainActivity.protein_spicified_dinner_max_for_medium_bmi;
                               fat_spicified_max_for_medium_bmi=MainActivity.fat_spicified_dinner_max_for_medium_bmi;

                               carbo_spicified_max_for_under_bmi=MainActivity.carbo_spicified_dinner_max_for_under_bmi;
                               protein_spicified_max_for_under_bmi=MainActivity.protein_spicified_dinner_max_for_under_bmi;
                               fat_spicified_max_for_under_bmi=MainActivity.fat_spicified_dinner_max_for_under_bmi;

                               meal_time_name="Dinner";
                               TastyToast.makeText(getApplicationContext(), "Welcome In Dinner Meal", TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                           }

                           String temp_txt_date;
                           temp_txt_date="( Current Meal : "+ MainActivity.meal_time_name+" )";
                           NavHomeFragment.main_screen_textbox_meal_name.setText(temp_txt_date);

                           /*
                           SharedPreferences.Editor editor = SP.edit();

                           editor.putString("Meal_name_String",meal_time_name);        // Saving integer



                            */



                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError)
                       {
                           TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                           final Intent i = new Intent(getApplicationContext(), SginIn.class);
                           startActivity(i);
                           new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                           return;
                       }
                   });



               }

           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError)
           {
               TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
               final Intent i = new Intent(getApplicationContext(), SginIn.class);
               startActivity(i);
               new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
               return;
           }
       });

           breakfast_reminder();
           dinner_reminder();
           lunch_reminder();


        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BasketActivity.class));
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, History_tapped.class));
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_carbohydrates, R.id.nav_proteins, R.id.nav_nuts,
                R.id.nav_spices, R.id.nav_legumes, R.id.nav_fruits
                , R.id.nav_diary,R.id.nav_strachy,R.id.nav_cereals)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener()
        {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments)
            {
                if (destination.getId() == R.id.nav_home) {

                }

                if (destination.getId() == R.id.nav_carbohydrates) {

                }
                if (destination.getId() == R.id.nav_proteins) {

                }
                if (destination.getId() == R.id.nav_nuts) {

                }
                if (destination.getId() == R.id.nav_spices) {

                }
                if (destination.getId() == R.id.nav_legumes) {

                }
                if (destination.getId() == R.id.nav_fruits) {

                }
                if (destination.getId() == R.id.nav_diary) {

                }
                if (destination.getId() == R.id.nav_strachy) {

                }
                if (destination.getId() == R.id.nav_cereals) {

                }
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout_main :
                logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void logout(){
//        SharedPreferences sharedPrefs =getSharedPreferences(MainActivity.PREFS_NAME,MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        editor.clear();
//        editor.commit();
        //show login form

        Intent intent=new Intent(this, SginIn.class);
        startActivity(intent);
        TastyToast.makeText(getApplicationContext(), "Logout Successfuly", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
        finish();

    }


    private void breakfast_reminder(){

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 22);
        cal.set(Calendar.MINUTE, 51);

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pintent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pintent);

    }

    private void dinner_reminder(){

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, dinner_timehours);
        cal.set(Calendar.MINUTE, dinner_timeminutes);

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pintent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pintent);

    }

    private void lunch_reminder(){

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, lunch_timehours);
        cal.set(Calendar.MINUTE, lunch_timeminutes);

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pintent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pintent);

    }

    public void init()
    {
        this.dialog = new Dialog(MainActivity.this);
    }

    public void showdialog()
    {
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();
    }


}
