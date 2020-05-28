package com.Malnutrition.ProblemOfMalnutrition;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class splash_Screen extends AppCompatActivity {

    ImageView logo_splash_screen;
    private FirebaseAuth mAuth;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public static String  loginPrefs="m.ypre";
    public static String PREF_USERNAME , PREF_PASSWORD ;
    public static SharedPreferences SP ;
    public static String filename = "Keys";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

//        logo_splash_screen = findViewById(R.id.ui_splash_Screen_Main_logo);

        if(user!=null)
        reference.child("basket").child(user.getUid()).removeValue();
//


        YoYo.with(Techniques.Pulse)
                .duration(300)
                .repeat(3)
                .playOn(findViewById(R.id.img_spash));

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);

                    Intent intent = new Intent(getApplicationContext(), SginIn.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();


//        ExerciseModel model = new ExerciseModel();
//        model.setExername("Barbell");
//        model.setExercount("10 sets");
//        model.setExerimg(Integer.parseInt("dsssssssawdsadl;masd64a6s5d4"));


    }


//    public void getUser(){
//        SharedPreferences pref = getSharedPreferences(SginIn.PREFS_NAME,MODE_PRIVATE);
//        String username = pref.getString(SginIn.PREF_USERNAME, "");
//
//        String password = pref.getString(SginIn.PREF_PASSWORD, "");
//
//
//        if (username != null || password != null) {
//
//            Intent intent = new Intent(splash_Screen.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//}
}
