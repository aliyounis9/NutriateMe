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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sdsmdg.tastytoast.TastyToast;

public class lunch_meal extends AppCompatActivity
{
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    public String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public static String LUNCH_HOURS ,LUNCH_MINUTES;

    TimePicker picker_breakfast;
    ImageButton back ;
    TextView next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_meal);

        back = findViewById(R.id.backarrow2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(lunch_meal.this , breakfast_meal.class));
            }
        });

        picker_breakfast = findViewById(R.id.timePicker1_reminder_lunch);

        picker_breakfast.setIs24HourView(true);
        next = findViewById(R.id.ui_submit_getTime_lunch_Button_ui);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;

                if (Build.VERSION.SDK_INT >= 23) {
                    hour = picker_breakfast.getHour();
                    minute = picker_breakfast.getMinute();
                } else {
                    hour = picker_breakfast.getCurrentHour();
                    minute = picker_breakfast.getCurrentMinute();
                }

                getSharedPreferences(breakfast_meal.TIME_PREF,MODE_PRIVATE)
                        .edit()
                        .putInt(LUNCH_HOURS,hour)
                        .putInt(LUNCH_MINUTES,minute)
                        .commit();

                timePickModel model = new timePickModel();

                if(!(hour<=19 && hour>13))
                {
                    TastyToast.makeText(getApplicationContext(), "It's Not Healthy To Make Your Lunch At Time : "+hour+":"+minute, TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                    return;
                }

                model.setHours(hour);
                model.setMinutes(minute);

                databaseReference.child("users").child(currentuser).child("Meals").child("The Lunch").setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            TastyToast.makeText(getApplicationContext(), "Submit Successful", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            Intent i = new Intent(lunch_meal.this, Dinner_meal.class);
                            startActivity(i);
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