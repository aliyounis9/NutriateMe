package com.Malnutrition.ProblemOfMalnutrition.ui.Nuts;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.Malnutrition.ProblemOfMalnutrition.CustomAdapter;
import com.Malnutrition.ProblemOfMalnutrition.MainActivity;
import com.Malnutrition.ProblemOfMalnutrition.Meals_get_Model;
import com.Malnutrition.ProblemOfMalnutrition.R;
import com.Malnutrition.ProblemOfMalnutrition.SginIn;
import com.Malnutrition.ProblemOfMalnutrition.TakeMealModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

//Nuts Fragment
public class NutsFragment extends Fragment
{


    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ListView listView;
    Dialog dialog ;
    ArrayList<Meals_get_Model> list = new ArrayList<Meals_get_Model>();
    public Boolean availabe_to_order;
    double afterTextChanged = 0.0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nuts, container, false);

        listView = view.findViewById(R.id.list_nuts);

        check_order_avilability();

        final DecimalFormat precision = new DecimalFormat("0.00");

        init();
        showdialog();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1500);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("Meals").child("List").child("Nuts").getChildren()) {

                    Meals_get_Model modelMenu = snapshot.getValue(Meals_get_Model.class);
                    list.add(modelMenu);
                }
                CustomAdapter customAdapter = new CustomAdapter(getContext(),R.layout.row , list);
                listView.setAdapter(customAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                        final Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.onclick_dialog);
                        dialog.show();
                        final TextView det = dialog.findViewById(R.id.dial_details);
                        final TextView mealname = dialog.findViewById(R.id.dial_name);
                        final TextView comp = dialog.findViewById(R.id.dial_components);
                        final TextView carbo = dialog.findViewById(R.id.dial_carbo);
                        final TextView proteins = dialog.findViewById(R.id.dial_proteins);
                        final TextView fats = dialog.findViewById(R.id.dial_fats);
                        final TextView energy = dialog.findViewById(R.id.dial_energy);
                        final TextView water = dialog.findViewById(R.id.dial_water);
                        final TextView sugar = dialog.findViewById(R.id.dial_sugar);
                        final EditText amount = dialog.findViewById(R.id.dial_amount);
                        final CircleImageView image = dialog.findViewById(R.id.dial_image);
                        final Button cancel = dialog.findViewById(R.id.dial_cancel);
                        final Button add = dialog.findViewById(R.id.dial_add);
                        final String name = list.get(position).getMeal_name();
                        final Double components = list.get(position).getComponents();
                        final Double carbohydrates = list.get(position).getCarbohydrates();
                        final Double eng = list.get(position).getEnergy();
                        final Double fat = list.get(position).getFats();
                        final Double protein = list.get(position).getProteins();
                        final Double sug = list.get(position).getSugar();
                        final Double wat = list.get(position).getWater();
                        final String img = list.get(position).getMeal_img();

                        Picasso.get().load(img).into(image);
                        mealname.setText("Meal Name : " + name);
                        comp.setText("Components : " + components + " gram");
                        carbo.setText("Carbohydrates : " + carbohydrates + " gram");
                        energy.setText("Energy : " + eng + " calory");
                        fats.setText("Fats : " + fat + " gram");
                        proteins.setText("Proteins : " + protein + " gram");
                        sugar.setText("Sugar : " + sug + " gram");
                        water.setText("Water : " + wat + " gram");

                        amount.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                                if (!amount.getText().toString().isEmpty()) {
                                    afterTextChanged = Double.parseDouble(amount.getText().toString());
                                    comp.setText("Components : " + precision.format((afterTextChanged * components)) + " gram");
                                    carbo.setText("Carbohydrates : " + precision.format((afterTextChanged * carbohydrates)) + " gram");
                                    energy.setText("Energy : " + precision.format((afterTextChanged*eng)) + " calory");
                                    fats.setText("Fats : " + precision.format((afterTextChanged * fat)) + " gram");
                                    proteins.setText("Proteins : " + precision.format((afterTextChanged * protein)) + " gram");
                                    sugar.setText("Sugar : " + precision.format((afterTextChanged * sug)) + " gram");
                                    water.setText("Water : " + precision.format((afterTextChanged * wat)) + " gram");
                                }
                                else{
                                    comp.setText("Components : " + components + " gram");
                                    carbo.setText("Carbohydrates : " + carbohydrates + " gram");
                                    energy.setText("Energy : " + eng + " calory");
                                    fats.setText("Fats : " + fat + " gram");
                                    proteins.setText("Proteins : " + protein + " gram");
                                    sugar.setText("Sugar : " + sug + " gram");
                                    water.setText("Water : " + wat + " gram");
                                }
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (!availabe_to_order == true) {
                                    TastyToast.makeText(getContext(), "Sorry, You Took " + MainActivity.meal_time_name + " Meal", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                    return;
                                }

                                if (TextUtils.isEmpty(amount.getText()) || amount == null || amount.getText().toString()=="0" || amount.getText().toString()=="00" ||amount.getText().toString()=="000") {
                                    TastyToast.makeText(getContext(), "Enter Your Amount", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    dialog.dismiss();
                                    return;
                                }

                                if (Double.parseDouble(amount.getText().toString()) <= 0.0 || Double.parseDouble(amount.getText().toString()) <= 9.9) {
                                    TastyToast.makeText(getContext(), "Enter A Valid Amount", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    dialog.dismiss();
                                    return;
                                }


                                final Double amou = Double.parseDouble(String.valueOf(amount.getText()));


                                if (MainActivity.BMI > 25)
                                {
                                    if (MainActivity.carbo_spicified < (amou * carbohydrates)) {
                                        TastyToast.makeText(getContext(), "You'are OverWeight So your Max from CARBO is " + MainActivity.carbo_spicified + " Cal In " + MainActivity.meal_time_name, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        dialog.dismiss();
                                        return;
                                    }
                                    else if (MainActivity.protein_spicified < (amou * protein)) {
                                        TastyToast.makeText(getContext(), "You'are OverWeight So your Max from PROTEIN is " + MainActivity.protein_spicified + " Cal In " + MainActivity.meal_time_name, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        dialog.dismiss();
                                        return;

                                    }
                                    else if (MainActivity.fat_spicified < (amou * fat)) {
                                        TastyToast.makeText(getContext(), "You'are OverWeight So your Max from FAT is " + MainActivity.fat_spicified + " Cal In " + MainActivity.meal_time_name, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        dialog.dismiss();
                                        return;
                                    }

                                }
                                else if (MainActivity.BMI < 18.5) {


                                    if (MainActivity.carbo_spicified_max_for_under_bmi < (amou * carbohydrates))
                                    {
                                        TastyToast.makeText(getContext(), "You'are UnderWeight So your Max from CARBO is "+MainActivity.carbo_spicified_max_for_under_bmi+ " Cal In " + MainActivity.meal_time_name, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        dialog.dismiss();
                                        return;
                                    }

                                    else if (MainActivity.protein_spicified_max_for_under_bmi < (amou * protein))
                                    {
                                        TastyToast.makeText(getContext(), "You'are UnderWeight So your Max from PROTEIN is "+MainActivity.protein_spicified_max_for_under_bmi+" Cal In " + MainActivity.meal_time_name, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        dialog.dismiss();
                                        return;

                                    }

                                    else if (MainActivity.fat_spicified_max_for_under_bmi < (amou * fat)) {
                                        TastyToast.makeText(getContext(), "You'are UnderWeight So your Max from FAT is "+MainActivity.fat_spicified_max_for_under_bmi+" Cal In " + MainActivity.meal_time_name, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        dialog.dismiss();
                                        return;
                                    }
                                }

                                else if (MainActivity.BMI > 18.5 && MainActivity.BMI < 25)
                                {


                                    if ((MainActivity.carbo_spicified_max_for_medium_bmi < (amou * carbohydrates) )) {
                                        TastyToast.makeText(getContext(), "You'are Normal Weight So your Max from CARBO is " + MainActivity.carbo_spicified_max_for_medium_bmi + " Cal In " + MainActivity.meal_time_name, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        dialog.dismiss();
                                        return;

                                    }

                                    if ((MainActivity.fat_spicified_max_for_medium_bmi < (amou * fat) )) {
                                        TastyToast.makeText(getContext(), "You'are Normal Weight So your Max from FAT is " + MainActivity.fat_spicified_max_for_medium_bmi + " Cal In " + MainActivity.meal_time_name, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        dialog.dismiss();
                                        return;
                                    }

                                    if ((MainActivity.protein_spicified_max_for_medium_bmi < (amou * protein) )) {
                                        TastyToast.makeText(getContext(), "You'are Normal Weight So your Max from PROTEIN is " + MainActivity.protein_spicified_max_for_medium_bmi + " Cal In " + MainActivity.meal_time_name, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        dialog.dismiss();
                                        return;
                                    }


                                }


                                final String key = reference.push().getKey();

                                final TakeMealModel model = new TakeMealModel();
                                model.setMeal_img(img);
                                model.setComponents(amou);
                                model.setCarbohydrates(amou * carbohydrates);
                                model.setEnergy(amou * eng);
                                model.setFats(amou * fat);
                                model.setMeal_name(name);
                                model.setKey(key);
                                model.setProteins(amou * protein);
                                model.setSugar(amou * sug);
                                model.setWater(amou * wat);


                                Calendar x1 = Calendar.getInstance();
                                final int current_day_of_year = x1.get(Calendar.DAY_OF_YEAR);
                                final String current_day = "" + current_day_of_year;


                                reference.child("basket").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild(name)){
                                            Double anotheramou = amou + dataSnapshot.child(name).child("components").getValue(Double.class);
                                            Double anothercarbo = (amou*carbohydrates) + dataSnapshot.child(name).child("carbohydrates").getValue(Double.class);
                                            Double anothereng = (amou*eng) + dataSnapshot.child(name).child("energy").getValue(Double.class);
                                            Double anotherfat = (amou*fat) + dataSnapshot.child(name).child("fats").getValue(Double.class);
                                            Double anotherprot = (amou*protein) + dataSnapshot.child(name).child("proteins").getValue(Double.class);
                                            Double anothersug = (amou*sug) + dataSnapshot.child(name).child("sugar").getValue(Double.class);
                                            Double anotherwat = (amou*wat) + dataSnapshot.child(name).child("water").getValue(Double.class);

                                            TakeMealModel model2 = new TakeMealModel();
                                            model2.setMeal_img(img);
                                            model2.setComponents(anotheramou);
                                            model2.setCarbohydrates(anothercarbo);
                                            model2.setEnergy(anothereng);
                                            model2.setFats(anotherfat);
                                            model2.setMeal_name(name);
                                            model2.setKey(key);
                                            model2.setProteins(anotherprot);
                                            model2.setSugar(anothersug);
                                            model2.setWater(anotherwat);

                                            reference.child("basket").child(user.getUid()).child(name).setValue(model2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (MainActivity.BMI < 18.5) {

                                                            MainActivity.protein_spicified_max_for_under_bmi = MainActivity.protein_spicified_max_for_under_bmi - (amou * protein);
                                                            MainActivity.fat_spicified_max_for_under_bmi = MainActivity.fat_spicified_max_for_under_bmi - (amou * fat);
                                                            MainActivity.carbo_spicified_max_for_under_bmi = MainActivity.carbo_spicified_max_for_under_bmi - (amou * carbohydrates);
                                                            TastyToast.makeText(getContext(), "Added Success to your plate,you have : (" + precision.format(MainActivity.protein_spicified_max_for_under_bmi) + " Protein) -- (" + precision.format(MainActivity.fat_spicified_max_for_under_bmi) +
                                                                    " Fats) -- (" + precision.format(MainActivity.carbo_spicified_max_for_under_bmi) + " Carbo)", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                            dialog.dismiss();
                                                            return;
                                                        } else if (MainActivity.BMI > 18.5 && MainActivity.BMI < 25) {

                                                            MainActivity.protein_spicified_max_for_medium_bmi = MainActivity.protein_spicified_max_for_medium_bmi - (amou * protein);
                                                            MainActivity.fat_spicified_max_for_medium_bmi = MainActivity.fat_spicified_max_for_medium_bmi - (amou * fat);
                                                            MainActivity.carbo_spicified_max_for_medium_bmi = MainActivity.carbo_spicified_max_for_medium_bmi - (amou * carbohydrates);
                                                            TastyToast.makeText(getContext(), "Added Success to your plate,you have : (" + precision.format(MainActivity.protein_spicified_max_for_medium_bmi) + " Protein) -- (" + precision.format(MainActivity.fat_spicified_max_for_medium_bmi) +
                                                                    " Fats) -- (" + precision.format(MainActivity.carbo_spicified_max_for_medium_bmi) + " Carbo)", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                            dialog.dismiss();
                                                            return;
                                                        } else {

                                                            MainActivity.protein_spicified = MainActivity.protein_spicified - (amou * protein);
                                                            MainActivity.fat_spicified = MainActivity.fat_spicified - (amou * fat);
                                                            MainActivity.carbo_spicified = MainActivity.carbo_spicified - (amou * carbohydrates);

                                                            TastyToast.makeText(getContext(), "Added Success to your plate,you have : (" + precision.format(MainActivity.protein_spicified) + " Protein) -- (" + precision.format(MainActivity.fat_spicified) +
                                                                    " Fats) -- (" + precision.format(MainActivity.carbo_spicified) + " Carbo)", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                            dialog.dismiss();
                                                            return;
                                                        }

                                                    } else {
                                                        TastyToast.makeText(getContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                        new SginIn.InternetDialog(getContext()).getInternetStatus();
                                                        return;
                                                    }


                                                }
                                            });
                                        }
                                        else{

                                            reference.child("basket").child(user.getUid()).child(name).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (MainActivity.BMI < 18.5) {

                                                            MainActivity.protein_spicified_max_for_under_bmi = MainActivity.protein_spicified_max_for_under_bmi - (amou * protein);
                                                            MainActivity.fat_spicified_max_for_under_bmi = MainActivity.fat_spicified_max_for_under_bmi - (amou * fat);
                                                            MainActivity.carbo_spicified_max_for_under_bmi = MainActivity.carbo_spicified_max_for_under_bmi - (amou * carbohydrates);
                                                            TastyToast.makeText(getContext(), "Added Success to your plate,you have : (" + precision.format(MainActivity.protein_spicified_max_for_under_bmi) + " Protein) -- (" + precision.format(MainActivity.fat_spicified_max_for_under_bmi) +
                                                                    " Fats) -- (" + precision.format(MainActivity.carbo_spicified_max_for_under_bmi) + " Carbo)", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                            dialog.dismiss();
                                                            return;
                                                        } else if (MainActivity.BMI > 18.5 && MainActivity.BMI < 25) {

                                                            MainActivity.protein_spicified_max_for_medium_bmi = MainActivity.protein_spicified_max_for_medium_bmi - (amou * protein);
                                                            MainActivity.fat_spicified_max_for_medium_bmi = MainActivity.fat_spicified_max_for_medium_bmi - (amou * fat);
                                                            MainActivity.carbo_spicified_max_for_medium_bmi = MainActivity.carbo_spicified_max_for_medium_bmi - (amou * carbohydrates);
                                                            TastyToast.makeText(getContext(), "Added Success to your plate,you have : (" + precision.format(MainActivity.protein_spicified_max_for_medium_bmi) + " Protein) -- (" + precision.format(MainActivity.fat_spicified_max_for_medium_bmi) +
                                                                    " Fats) -- (" + precision.format(MainActivity.carbo_spicified_max_for_medium_bmi) + " Carbo)", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                            dialog.dismiss();
                                                            return;
                                                        } else {

                                                            MainActivity.protein_spicified = MainActivity.protein_spicified - (amou * protein);
                                                            MainActivity.fat_spicified = MainActivity.fat_spicified - (amou * fat);
                                                            MainActivity.carbo_spicified = MainActivity.carbo_spicified - (amou * carbohydrates);

                                                            TastyToast.makeText(getContext(), "Added Success to your plate,you have : (" + precision.format(MainActivity.protein_spicified) + " Protein) -- (" + precision.format(MainActivity.fat_spicified) +
                                                                    " Fats) -- (" + precision.format(MainActivity.carbo_spicified) + " Carbo)", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                            dialog.dismiss();
                                                            return;
                                                        }

                                                    } else {
                                                        TastyToast.makeText(getContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                        new SginIn.InternetDialog(getContext()).getInternetStatus();
                                                        return;
                                                    }


                                                }
                                            });

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                        TastyToast.makeText(getContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    }
                                });

                            }


                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                TastyToast.makeText(getContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                final Intent i = new Intent(getContext(), SginIn.class);
                startActivity(i);
                new SginIn.InternetDialog(getContext()).getInternetStatus();
                return;
            }

        });

        return view;
    }


    public void init() {
        this.dialog = new Dialog(getContext());
    }

    public void showdialog() {
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();
    }



    public void check_order_avilability()
    {

        Calendar x1 = Calendar.getInstance();
        final int current_day_of_year = x1.get(Calendar.DAY_OF_YEAR);
        final String current_day = "" + current_day_of_year;

        reference.child("Daily_orders").child(user.getUid()).child(current_day).child(MainActivity.meal_time_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if ((dataSnapshot.hasChild(""))) {
                    availabe_to_order=false;
                    TastyToast.makeText(getContext(), "Sorry, You Took " + MainActivity.meal_time_name + " Meal", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    return;
                }
                else
                {
                    availabe_to_order=true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                TastyToast.makeText(getContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                final Intent i = new Intent(getContext(), SginIn.class);
                startActivity(i);
                new SginIn.InternetDialog(getContext()).getInternetStatus();
                return;
            }
        });

    }


}



