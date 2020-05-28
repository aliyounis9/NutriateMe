package com.Malnutrition.ProblemOfMalnutrition;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sdsmdg.tastytoast.TastyToast;

public class Dinner_meal extends AppCompatActivity
{
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    public String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public static String DINNER_HOURS ,DINNER_MINUTES ;

    TimePicker picker_breakfast;

    ImageButton back ;
    TextView next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_meal);

        back = findViewById(R.id.backarrow3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dinner_meal.this , lunch_meal.class));
            }
        });

        picker_breakfast = findViewById(R.id.timePicker1_reminder_dinner);

        picker_breakfast.setIs24HourView(true);
        next = findViewById(R.id.ui_submit_getTime_dinner_Button_ui);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;
                String am_pm;
                if (Build.VERSION.SDK_INT >= 23) {
                    hour = picker_breakfast.getHour();
                    minute = picker_breakfast.getMinute();
                } else {
                    hour = picker_breakfast.getCurrentHour();
                    minute = picker_breakfast.getCurrentMinute();
                }

                getSharedPreferences(breakfast_meal.TIME_PREF,MODE_PRIVATE)
                        .edit()
                        .putInt(DINNER_HOURS,hour)
                        .putInt(DINNER_MINUTES,minute)
                        .commit();

                timePickModel model = new timePickModel();

                if(!(hour>19))
                {
                    TastyToast.makeText(getApplicationContext(), "It's Not Healthy To Make Your Dinner At Time : "+hour+":"+minute, TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                    return;
                }

                if(!(hour>=0&&hour>=13))
                {
                    TastyToast.makeText(getApplicationContext(), "It's Not Healthy To Make Your Dinner At Time : "+hour+":"+minute, TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                    return;
                }

                model.setHours(hour);
                model.setMinutes(minute);
                String key = databaseReference.push().getKey();
                databaseReference.child("medical_check").child(user.getUid()).child(key).child("checked").setValue("true");
                databaseReference.child("users").child(currentuser).child("Meals").child("Dinner").setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            TastyToast.makeText(getApplicationContext(), "Submit Successful", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            Intent i = new Intent(Dinner_meal.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            final Intent i = new Intent(getApplicationContext(), SginIn.class);
                            startActivity(i);
                            new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                            return;
                        }
                    }
                });
            }
        });

    }
}
