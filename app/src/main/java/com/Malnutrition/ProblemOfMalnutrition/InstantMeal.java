package com.Malnutrition.ProblemOfMalnutrition;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Calendar;

public class InstantMeal extends AppCompatActivity {

    Button takemeal_bf ;
    ListView listview_instant;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<TakeMealModel>models = new ArrayList<TakeMealModel>();
    ArrayList<TakeMealModel> list = new ArrayList<TakeMealModel>();

    TextView wecome_name_meal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_meal);
        wecome_name_meal=findViewById(R.id.weclome_plus_name_meal);

        wecome_name_meal.setText("Welcome In INSTANT Meal ( "+MainActivity.meal_time_name+" )");


        takemeal_bf = findViewById(R.id.breakfast_btn);

        listview_instant = findViewById(R.id.listview_instant);


        Calendar x1 = Calendar.getInstance();
        final int current_day_of_year=x1.get(Calendar.DAY_OF_YEAR);
        final String current_day=""+current_day_of_year;




        if (MainActivity.BMI != null && MainActivity.BMI < 18.5)
            {

                if(MainActivity.meal_time_name=="BreakFast")
                {

                    final String key = reference.push().getKey();
                    final TakeMealModel model = new TakeMealModel();

                    model.setMeal_name("Bread");
                    model.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/bread.jpg?alt=media&token=abe6ee4f-767d-49ec-91b3-9f52ed8e12ce");
                    model.setComponents(100.0);
                    model.setEnergy(306.5);
                    model.setCarbohydrates(48.85);
                    model.setProteins(8.35);
                    model.setKey(key);
                    model.setFats(3.2);
                    models.add(model);

                    list.add(model);


                    final String key1 = reference.push().getKey();
                    final TakeMealModel model1 = new TakeMealModel();

                    model1.setMeal_name("Halva");
                    model1.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/halva.jpg?alt=media&token=425d83af-7c71-4d9f-afd1-694df3eebc6e");
                    model1.setComponents(94.5);
                    model1.setEnergy(443.2);
                    model1.setKey(key1);
                    model1.setCarbohydrates(56.7);
                    model1.setProteins(11.34);
                    model1.setFats(20.79);
                    models.add(model1);

                    list.add(model1);




                    takemeal_bf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(InstantMeal.this);
                            builder1.setTitle("NutriateMe");
                            builder1.setMessage("Are you want to take this Meal ?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, int id)
                                        {

                                            reference.child("Daily_orders").child(user.getUid()).child(current_day).child(MainActivity.meal_time_name).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    if ((dataSnapshot.hasChild("")))
                                                    {
                                                        TastyToast.makeText(InstantMeal.this,"Sorry, You Took "+MainActivity.meal_time_name+" Meal",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                                        return;
                                                    }

                                                    reference.child("Daily_orders").child(user.getUid()).child(current_day)
                                                            .child(MainActivity.meal_time_name).setValue(list);

                                                    TastyToast.makeText(InstantMeal.this ,"Done" , TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

                                                    dialog.cancel();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                                                    startActivity(i);
                                                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                                                    return;
                                                }
                                            });




                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    });

                    BasketAdapter adapter = new BasketAdapter(InstantMeal.this,R.layout.basket_row,models);
                    listview_instant.setAdapter(adapter);

                }

                else if(MainActivity.meal_time_name=="Lunch")
                {

                    ArrayList<TakeMealModel>models1 = new ArrayList<TakeMealModel>();

                    final String key2 = reference.push().getKey();
                    final TakeMealModel model2 = new TakeMealModel();

                    model2.setMeal_name("Peas");
                    model2.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/peas.jpg?alt=media&token=f61ee7f9-521d-4344-9134-db3f7bd8fbc5");
                    model2.setComponents(145.2);
                    model2.setEnergy(111.8);
                    model2.setCarbohydrates(19.8);
                    model2.setProteins(7.6);
                    model2.setFats(0.6);
                    model2.setKey(key2);
                    models1.add(model2);
                    list.add(model2);


                    final String key3 = reference.push().getKey();
                    final TakeMealModel model3 = new TakeMealModel();

                    model3.setMeal_name("Rice");
                    model3.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/rice.jpg?alt=media&token=06b528e8-055f-4a18-b549-da511ed65c0a");
                    model3.setComponents(200.0);
                    model3.setEnergy(730.4);
                    model3.setCarbohydrates(160.0);
                    model3.setProteins(14.2);
                    model3.setFats(1.32);
                    model3.setKey(key3);
                    models1.add(model3);
                    list.add(model3);


                    final String key4 = reference.push().getKey();
                    final TakeMealModel model4 = new TakeMealModel();

                    model4.setMeal_name("Salad");
                    model4.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/salad.jpg?alt=media&token=5213ccb9-0c00-423c-a732-138ee479b80b");
                    model4.setComponents(100.0);
                    model4.setEnergy(152.0);
                    model4.setCarbohydrates(15.0);
                    model4.setProteins(1.0);
                    model4.setFats(10.0);
                    model4.setKey(key4);
                    models1.add(model4);
                    list.add(model4);


                    takemeal_bf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(InstantMeal.this);
                            builder1.setTitle("NutriateMe");
                            builder1.setMessage("Are you want to take this Meal ?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, int id) {

                                            reference.child("Daily_orders").child(user.getUid()).child(current_day).child(MainActivity.meal_time_name).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    if ((dataSnapshot.hasChild("")))
                                                    {
                                                        TastyToast.makeText(InstantMeal.this,"Sorry, You Took "+MainActivity.meal_time_name+" Meal",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                                        return;
                                                    }

                                                    reference.child("Daily_orders").child(user.getUid()).child(current_day)
                                                            .child(MainActivity.meal_time_name).setValue(list);

                                                    TastyToast.makeText(InstantMeal.this ,"Done" , TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

                                                    dialog.cancel();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                                                    startActivity(i);
                                                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                                                    return;
                                                }
                                            });




                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    });

                    BasketAdapter adapter1 = new BasketAdapter(InstantMeal.this,R.layout.basket_row,models1);
                    listview_instant.setAdapter(adapter1);


                }


                else if(MainActivity.meal_time_name=="Dinner")
                {


                    ArrayList<TakeMealModel>models2 = new ArrayList<TakeMealModel>();

                    final String key5 = reference.push().getKey();
                    final TakeMealModel model5 = new TakeMealModel();

                    model5.setMeal_name("Milk");
                    model5.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/milk.jpg?alt=media&token=12cc2926-d3af-4f61-863d-064b1c5eacdb");
                    model5.setComponents(200.0);
                    model5.setEnergy(84.0);
                    model5.setCarbohydrates(10.0);
                    model5.setProteins(6.0);
                    model5.setFats(2.0);
                    model5.setKey(key5);
                    models2.add(model5);
                    list.add(model5);


                    final String key6 = reference.push().getKey();
                    final TakeMealModel model6 = new TakeMealModel();

                    model6.setMeal_name("Bread");
                    model6.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/bread.jpg?alt=media&token=abe6ee4f-767d-49ec-91b3-9f52ed8e12ce");
                    model6.setComponents(80.0);
                    model6.setEnergy(245.2);
                    model6.setCarbohydrates(39.08);
                    model6.setProteins(6.68);
                    model6.setFats(2.56);
                    model6.setKey(key6);
                    models2.add(model6);
                    list.add(model6);


                    final String key7 = reference.push().getKey();
                    final TakeMealModel model7 = new TakeMealModel();

                    model7.setMeal_name("Cheese");
                    model7.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/cheese.jpg?alt=media&token=df6bad24-3739-43f6-b7f5-ed4e157a157e");
                    model7.setComponents(70.0);
                    model7.setEnergy(281.0);
                    model7.setCarbohydrates(0.9);
                    model7.setProteins(18.0);
                    model7.setKey(key7);
                    model7.setFats(23.0);
                    models2.add(model7);
                    list.add(model7);


                    final String key8 = reference.push().getKey();
                    final TakeMealModel model8 = new TakeMealModel();

                    model8.setMeal_name("Date Piece");
                    model8.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/datepiece.jpg?alt=media&token=8e3d70e1-5ce3-4505-b982-9f383fb21fd1");
                    model8.setComponents(7.0);
                    model8.setEnergy(140.0);
                    model8.setCarbohydrates(37.1);
                    model8.setProteins(1.4);
                    model8.setFats(0.0);
                    model8.setKey(key8);
                    models2.add(model8);
                    list.add(model8);


                    takemeal_bf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(InstantMeal.this);
                            builder1.setTitle("NutriateMe");
                            builder1.setMessage("Are you want to take this Meal ?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, int id) {

                                            reference.child("Daily_orders").child(user.getUid()).child(current_day).child(MainActivity.meal_time_name).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    if ((dataSnapshot.hasChild("")))
                                                    {
                                                        TastyToast.makeText(InstantMeal.this,"Sorry, You Took "+MainActivity.meal_time_name+" Meal",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                                        return;
                                                    }

                                                    reference.child("Daily_orders").child(user.getUid()).child(current_day)
                                                            .child(MainActivity.meal_time_name).setValue(list);

                                                    TastyToast.makeText(InstantMeal.this ,"Done" , TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

                                                    dialog.cancel();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                                                    startActivity(i);
                                                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                                                    return;
                                                }
                                            });


                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    });

                    BasketAdapter adapter2 = new BasketAdapter(InstantMeal.this,R.layout.basket_row,models2);
                    listview_instant.setAdapter(adapter2);

                }


            }
            else if (MainActivity.BMI != null && MainActivity.BMI > 25)
            {

                if(MainActivity.meal_time_name=="BreakFast")
                {
                    final String key = reference.push().getKey();
                    final TakeMealModel model = new TakeMealModel();

                    model.setMeal_name("Bread");
                    model.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/bread.jpg?alt=media&token=abe6ee4f-767d-49ec-91b3-9f52ed8e12ce");
                    model.setComponents(60.0);
                    model.setEnergy(183.36);
                    model.setCarbohydrates(29.31);
                    model.setProteins(5.01);
                    model.setFats(1.92);
                    model.setKey(key);
                    models.add(model);
                    list.add(model);


                    final String key1 = reference.push().getKey();
                    final TakeMealModel model1 = new TakeMealModel();

                    model1.setMeal_name("Halva");
                    model1.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/halva.jpg?alt=media&token=425d83af-7c71-4d9f-afd1-694df3eebc6e");
                    model1.setComponents(75.6);
                    model1.setEnergy(354.56);
                    model1.setKey(key1);
                    model1.setCarbohydrates(45.36);
                    model1.setProteins(9.07);
                    model1.setFats(16.63);
                    models.add(model1);
                    list.add(model1);


                    takemeal_bf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(InstantMeal.this);
                            builder1.setTitle("NutriateMe");
                            builder1.setMessage("Are you want to take this Meal ?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, int id) {
                                            reference.child("Daily_orders").child(user.getUid()).child(current_day).child(MainActivity.meal_time_name).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    if ((dataSnapshot.hasChild("")))
                                                    {
                                                        TastyToast.makeText(InstantMeal.this,"Sorry, You Took "+MainActivity.meal_time_name+" Meal",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                                        return;
                                                    }

                                                    reference.child("Daily_orders").child(user.getUid()).child(current_day)
                                                            .child(MainActivity.meal_time_name).setValue(list);

                                                    TastyToast.makeText(InstantMeal.this ,"Done" , TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

                                                    dialog.cancel();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                                                    startActivity(i);
                                                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                                                    return;
                                                }
                                            });


                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    });

                    BasketAdapter adapter = new BasketAdapter(InstantMeal.this,R.layout.basket_row,models);
                    listview_instant.setAdapter(adapter);

                }

                else if(MainActivity.meal_time_name=="Lunch")
                {


                    ArrayList<TakeMealModel>models1 = new ArrayList<TakeMealModel>();

                    final String key2 = reference.push().getKey();
                    final TakeMealModel model2 = new TakeMealModel();

                    model2.setMeal_name("Peas");
                    model2.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/peas.jpg?alt=media&token=f61ee7f9-521d-4344-9134-db3f7bd8fbc5");
                    model2.setComponents(87.12);
                    model2.setEnergy(70.567);
                    model2.setCarbohydrates(12.197);
                    model2.setProteins(4.356);
                    model2.setFats(0.3485);
                    model2.setKey(key2);
                    models1.add(model2);
                    list.add(model2);


                    final String key3 = reference.push().getKey();
                    final TakeMealModel model3 = new TakeMealModel();

                    model3.setMeal_name("Rice");
                    model3.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/rice.jpg?alt=media&token=06b528e8-055f-4a18-b549-da511ed65c0a");
                    model3.setComponents(120.0);
                    model3.setEnergy(438.24);
                    model3.setCarbohydrates(96.0);
                    model3.setProteins(8.52);
                    model3.setFats(0.792);
                    model3.setKey(key3);
                    models1.add(model3);
                    list.add(model3);


                    final String key4 = reference.push().getKey();
                    final TakeMealModel model4 = new TakeMealModel();

                    model4.setMeal_name("Salad");
                    model4.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/salad.jpg?alt=media&token=5213ccb9-0c00-423c-a732-138ee479b80b");
                    model4.setComponents(60.0);
                    model4.setEnergy(91.0);
                    model4.setCarbohydrates(9.0);
                    model4.setProteins(0.6);
                    model4.setKey(key4);
                    model4.setFats(6.0);
                    models1.add(model4);
                    list.add(model4);


                    takemeal_bf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(InstantMeal.this);
                            builder1.setTitle("NutriateMe");
                            builder1.setMessage("Are you want to take this Meal ?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, int id) {

                                            reference.child("Daily_orders").child(user.getUid()).child(current_day).child(MainActivity.meal_time_name).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    if ((dataSnapshot.hasChild("")))
                                                    {
                                                        TastyToast.makeText(InstantMeal.this,"Sorry, You Took "+MainActivity.meal_time_name+" Meal",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                                        return;
                                                    }

                                                    reference.child("Daily_orders").child(user.getUid()).child(current_day)
                                                            .child(MainActivity.meal_time_name).setValue(list);

                                                    TastyToast.makeText(InstantMeal.this ,"Done" , TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

                                                    dialog.cancel();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                                                    startActivity(i);
                                                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                                                    return;
                                                }
                                            });


                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    });

                    BasketAdapter adapter1 = new BasketAdapter(InstantMeal.this,R.layout.basket_row,models1);
                    listview_instant.setAdapter(adapter1);

                }

                else if(MainActivity.meal_time_name=="Dinner")
                {

                    ArrayList<TakeMealModel>models2 = new ArrayList<TakeMealModel>();

                    final String key5 = reference.push().getKey();
                    final TakeMealModel model5 = new TakeMealModel();

                    model5.setMeal_name("Milk");
                    model5.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/milk.jpg?alt=media&token=12cc2926-d3af-4f61-863d-064b1c5eacdb");
                    model5.setComponents(120.0);
                    model5.setEnergy(50.0);
                    model5.setCarbohydrates(6.0);
                    model5.setProteins(4.1);
                    model5.setKey(key5);
                    model5.setFats(1.2);
                    models2.add(model5);
                    list.add(model5);


                    final String key6 = reference.push().getKey();
                    final TakeMealModel model6 = new TakeMealModel();

                    model6.setMeal_name("Bread");
                    model6.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/bread.jpg?alt=media&token=abe6ee4f-767d-49ec-91b3-9f52ed8e12ce");
                    model6.setComponents(48.0);
                    model6.setEnergy(147.12);
                    model6.setCarbohydrates(23.448);
                    model6.setProteins(4.0);
                    model6.setKey(key6);
                    model6.setFats(1.536);
                    models2.add(model6);
                    list.add(model6);


                    final String key7 = reference.push().getKey();
                    final TakeMealModel model7 = new TakeMealModel();

                    model7.setMeal_name("Cheese");
                    model7.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/cheese.jpg?alt=media&token=df6bad24-3739-43f6-b7f5-ed4e157a157e");
                    model7.setComponents(42.0);
                    model7.setEnergy(169.0);
                    model7.setCarbohydrates(0.5);
                    model7.setProteins(11.0);
                    model7.setFats(14.0);
                    model7.setKey(key7);
                    models2.add(model7);
                    list.add(model7);


                    final String key8 = reference.push().getKey();
                    final TakeMealModel model8 = new TakeMealModel();

                    model8.setMeal_name("Date Piece");
                    model8.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/datepiece.jpg?alt=media&token=8e3d70e1-5ce3-4505-b982-9f383fb21fd1");
                    model8.setComponents(4.0);
                    model8.setEnergy(80.0);
                    model8.setCarbohydrates(21.2);
                    model8.setProteins(0.8);
                    model8.setKey(key8);
                    model8.setFats(0.0);
                    models2.add(model8);
                    list.add(model8);


                    takemeal_bf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(InstantMeal.this);
                            builder1.setTitle("NutriateMe");
                            builder1.setMessage("Are you want to take this Meal ?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, int id) {

                                            reference.child("Daily_orders").child(user.getUid()).child(current_day).child(MainActivity.meal_time_name).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    if ((dataSnapshot.hasChild("")))
                                                    {
                                                        TastyToast.makeText(InstantMeal.this,"Sorry, You Took "+MainActivity.meal_time_name+" Meal",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                                        return;
                                                    }

                                                    reference.child("Daily_orders").child(user.getUid()).child(current_day)
                                                            .child(MainActivity.meal_time_name).setValue(list);

                                                    TastyToast.makeText(InstantMeal.this ,"Done" , TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

                                                    dialog.cancel();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                                                    startActivity(i);
                                                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                                                    return;
                                                }
                                            });


                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    });

                    BasketAdapter adapter2 = new BasketAdapter(InstantMeal.this,R.layout.basket_row,models2);
                    listview_instant.setAdapter(adapter2);

                }


            }

            else if (MainActivity.BMI!=null && MainActivity.BMI >18.5 && MainActivity.BMI<25)
            {


                if(MainActivity.meal_time_name=="BreakFast")
                {

                    final String key = reference.push().getKey();
                    final TakeMealModel model = new TakeMealModel();

                    model.setMeal_name("Bread");
                    model.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/bread.jpg?alt=media&token=abe6ee4f-767d-49ec-91b3-9f52ed8e12ce");
                    model.setComponents(80.0);
                    model.setEnergy(245.2);
                    model.setKey(key);
                    model.setCarbohydrates(39.08);
                    model.setProteins(6.68);
                    model.setFats(2.56);
                    models.add(model);
                    list.add(model);


                    final String key1 = reference.push().getKey();
                    final TakeMealModel model1 = new TakeMealModel();

                    model1.setMeal_name("Halva");
                    model1.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/halva.jpg?alt=media&token=425d83af-7c71-4d9f-afd1-694df3eebc6e");
                    model1.setComponents(56.7);
                    model1.setEnergy(265.92);
                    model1.setCarbohydrates(34.02);
                    model1.setProteins(6.80);
                    model1.setKey(key1);
                    model1.setFats(12.47);
                    models.add(model1);
                    list.add(model1);


                    takemeal_bf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(InstantMeal.this);
                            builder1.setTitle("NutriateMe");
                            builder1.setMessage("Are you want to take this Meal ?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, int id) {

                                            reference.child("Daily_orders").child(user.getUid()).child(current_day).child(MainActivity.meal_time_name).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    if ((dataSnapshot.hasChild("")))
                                                    {
                                                        TastyToast.makeText(InstantMeal.this,"Sorry, You Took "+MainActivity.meal_time_name+" Meal",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                                        return;
                                                    }

                                                    reference.child("Daily_orders").child(user.getUid()).child(current_day)
                                                            .child(MainActivity.meal_time_name).setValue(list);

                                                    TastyToast.makeText(InstantMeal.this ,"Done" , TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

                                                    dialog.cancel();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                                                    startActivity(i);
                                                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                                                    return;
                                                }
                                            });


                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    });

                    BasketAdapter adapter = new BasketAdapter(InstantMeal.this,R.layout.basket_row,models);
                    listview_instant.setAdapter(adapter);

                }

                else if(MainActivity.meal_time_name=="Lunch")
                {



                    ArrayList<TakeMealModel>models1 = new ArrayList<TakeMealModel>();

                    final String key2 = reference.push().getKey();
                    final TakeMealModel model2 = new TakeMealModel();

                    model2.setMeal_name("Peas");
                    model2.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/peas.jpg?alt=media&token=f61ee7f9-521d-4344-9134-db3f7bd8fbc5");
                    model2.setComponents(116.16);
                    model2.setEnergy(94.09);
                    model2.setCarbohydrates(16.263);
                    model2.setProteins(5.80);
                    model2.setFats(0.6);
                    model2.setKey(key2);
                    models1.add(model2);
                    list.add(model2);


                    final String key3 = reference.push().getKey();
                    final TakeMealModel model3 = new TakeMealModel();

                    model3.setMeal_name("Rice");
                    model3.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/rice.jpg?alt=media&token=06b528e8-055f-4a18-b549-da511ed65c0a");
                    model3.setComponents(160.0);
                    model3.setEnergy(584.32);
                    model3.setCarbohydrates(128.0);
                    model3.setProteins(11.36);
                    model3.setFats(1.056);
                    model3.setKey(key3);
                    models1.add(model3);
                    list.add(model3);


                    final String key4 = reference.push().getKey();
                    final TakeMealModel model4 = new TakeMealModel();

                    model4.setMeal_name("Salad");
                    model4.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/salad.jpg?alt=media&token=5213ccb9-0c00-423c-a732-138ee479b80b");
                    model4.setComponents(80.0);
                    model4.setEnergy(122.0);
                    model4.setCarbohydrates(12.0);
                    model4.setProteins(0.8);
                    model4.setKey(key4);
                    model4.setFats(8.0);
                    models1.add(model4);
                    list.add(model4);


                    takemeal_bf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(InstantMeal.this);
                            builder1.setTitle("NutriateMe");
                            builder1.setMessage("Are you want to take this Meal ?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, int id) {

                                            reference.child("Daily_orders").child(user.getUid()).child(current_day).child(MainActivity.meal_time_name).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    if ((dataSnapshot.hasChild("")))
                                                    {
                                                        TastyToast.makeText(InstantMeal.this,"Sorry, You Took "+MainActivity.meal_time_name+" Meal",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                                        return;
                                                    }

                                                    reference.child("Daily_orders").child(user.getUid()).child(current_day)
                                                            .child(MainActivity.meal_time_name).setValue(list);

                                                    TastyToast.makeText(InstantMeal.this ,"Done" , TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

                                                    dialog.cancel();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                                                    startActivity(i);
                                                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                                                    return;
                                                }
                                            });


                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    });

                    BasketAdapter adapter1 = new BasketAdapter(InstantMeal.this,R.layout.basket_row,models1);
                    listview_instant.setAdapter(adapter1);

                }

                else if(MainActivity.meal_time_name=="Dinner")
                {

                    ArrayList<TakeMealModel>models2 = new ArrayList<TakeMealModel>();

                    final String key5 = reference.push().getKey();
                    final TakeMealModel model5 = new TakeMealModel();

                    model5.setMeal_name("Milk");
                    model5.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/milk.jpg?alt=media&token=12cc2926-d3af-4f61-863d-064b1c5eacdb");
                    model5.setComponents(160.0);
                    model5.setEnergy(67.0);
                    model5.setKey(key5);
                    model5.setCarbohydrates(8.0);
                    model5.setProteins(5.4);
                    model5.setFats(1.6);
                    models2.add(model5);
                    list.add(model5);


                    final String key6 = reference.push().getKey();
                    final TakeMealModel model6 = new TakeMealModel();

                    model6.setMeal_name("Bread");
                    model6.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/bread.jpg?alt=media&token=abe6ee4f-767d-49ec-91b3-9f52ed8e12ce");
                    model6.setComponents(64.0);
                    model6.setEnergy(196.16);
                    model6.setCarbohydrates(31.264);
                    model6.setProteins(5.344);
                    model6.setFats(2.048);
                    model6.setKey(key6);
                    models2.add(model6);
                    list.add(model6);


                    final String key7 = reference.push().getKey();
                    final TakeMealModel model7 = new TakeMealModel();

                    model7.setMeal_name("Cheese");
                    model7.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/cheese.jpg?alt=media&token=df6bad24-3739-43f6-b7f5-ed4e157a157e");
                    model7.setComponents(56.0);
                    model7.setEnergy(225.0);
                    model7.setCarbohydrates(0.7);
                    model7.setProteins(14.0);
                    model7.setKey(key7);
                    model7.setFats(18.0);
                    models2.add(model7);
                    list.add(model7);


                    final String key8 = reference.push().getKey();
                    final TakeMealModel model8 = new TakeMealModel();

                    model8.setMeal_name("Date Piece");
                    model8.setMeal_img("https://firebasestorage.googleapis.com/v0/b/problem-of-malnutrition.appspot.com/o/datepiece.jpg?alt=media&token=8e3d70e1-5ce3-4505-b982-9f383fb21fd1");
                    model8.setComponents(5.0);
                    model8.setEnergy(100.0);
                    model8.setCarbohydrates(26.5);
                    model8.setProteins(1.0);
                    model8.setKey(key8);
                    model8.setFats(0.0);
                    models2.add(model8);
                    list.add(model8);


                    takemeal_bf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(InstantMeal.this);
                            builder1.setTitle("NutriateMe");
                            builder1.setMessage("Are you want to take this Meal ?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, int id) {

                                            reference.child("Daily_orders").child(user.getUid()).child(current_day).child(MainActivity.meal_time_name).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    if ((dataSnapshot.hasChild("")))
                                                    {
                                                        TastyToast.makeText(InstantMeal.this,"Sorry, You Took "+MainActivity.meal_time_name+" Meal",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                                        return;
                                                    }

                                                    reference.child("Daily_orders").child(user.getUid()).child(current_day)
                                                            .child(MainActivity.meal_time_name).setValue(list);

                                                    TastyToast.makeText(InstantMeal.this ,"Done" , TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

                                                    dialog.cancel();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                                                    startActivity(i);
                                                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                                                    return;
                                                }
                                            });


                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    });

                    BasketAdapter adapter2 = new BasketAdapter(InstantMeal.this,R.layout.basket_row,models2);
                    listview_instant.setAdapter(adapter2);

                }


            }

    }

}