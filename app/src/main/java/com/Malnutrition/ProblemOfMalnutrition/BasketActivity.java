package com.Malnutrition.ProblemOfMalnutrition;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class BasketActivity extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ListView listView;
    ArrayList<TakeMealModel> list = new ArrayList<TakeMealModel>();
    ArrayList<TakeMealModel> upload = new ArrayList<TakeMealModel>();
    ProgressBar progressBar;
    Button confirm_meal;
    TextView dayCurrent;
    Double allcarbo_basket = 0.0 , allprotein_basket =0.0 , allfats_basket = 0.0 ;
    Double allcarbo_basket_txt = 0.0 , allprotein_basket_txt =0.0 , allfats_basket_txt = 0.0 ;
    TextView carbo , prot , fat ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        Calendar c = Calendar.getInstance();
        final int dayOfWeek = c.get(Calendar.DAY_OF_YEAR);

        final DecimalFormat precision = new DecimalFormat("0.00");

        listView = findViewById(R.id.listview_basket);
        carbo = findViewById(R.id.allcarbo_basket);
        prot = findViewById(R.id.allprotein_basket);
        fat = findViewById(R.id.allfat_basket);

        confirm_meal= findViewById(R.id.confirm_meal);
        progressBar = findViewById(R.id.confirm_progressbar);

        reference.child("basket").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    TakeMealModel model = snapshot.getValue(TakeMealModel.class);
                    list.add(model);
                    allcarbo_basket_txt += model.getCarbohydrates();
                    allprotein_basket_txt += model.getProteins();
                    allfats_basket_txt += model.getFats();
                }
                BasketAdapter adapter = new BasketAdapter(BasketActivity.this,R.layout.basket_row , list);
                listView.setAdapter(adapter);
                carbo.setText(String.valueOf(precision.format(allcarbo_basket_txt)));
                prot.setText(String.valueOf(precision.format(allprotein_basket_txt)));
                fat.setText(String.valueOf(precision.format(allfats_basket_txt)));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        final Dialog dialog = new Dialog(BasketActivity.this);
                        dialog.setContentView(R.layout.onclick_dialog);
                        dialog.show();
                        final TextView det = dialog.findViewById(R.id.dial_details);
                        final TextView mealname = dialog.findViewById(R.id.dial_name);
                        final EditText amount = dialog.findViewById(R.id.dial_amount);
                        final CircleImageView image = dialog.findViewById(R.id.dial_image);
                    }
                });

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(BasketActivity.this);
                        builder1.setTitle("NutriateMe");
                        builder1.setMessage("Are You Want To Remove This Order ?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int id)
                                    {

                                        Double amou;
                                        amou=list.get(position).getComponents();
                                        Double protein;
                                        protein=list.get(position).getProteins();
                                        Double fat;
                                        fat=list.get(position).getFats();
                                        Double carbohydrates;
                                        carbohydrates=list.get(position).getCarbohydrates();

                                        if (MainActivity.BMI<18.5 ){
                                            MainActivity.protein_spicified_max_for_under_bmi = MainActivity.protein_spicified_max_for_under_bmi + (protein);
                                            MainActivity.fat_spicified_max_for_under_bmi = MainActivity.fat_spicified_max_for_under_bmi + (fat);
                                            MainActivity.carbo_spicified_max_for_under_bmi = MainActivity.carbo_spicified_max_for_under_bmi + (carbohydrates);
                                            Toast.makeText(BasketActivity.this, "Order Is Removed", Toast.LENGTH_SHORT);
                                        }
                                        else if (MainActivity.BMI >18.5 && MainActivity.BMI<25){
                                            MainActivity.protein_spicified_max_for_medium_bmi = MainActivity.protein_spicified_max_for_medium_bmi + (protein);
                                            MainActivity.fat_spicified_max_for_medium_bmi = MainActivity.fat_spicified_max_for_medium_bmi + (fat);
                                            MainActivity.carbo_spicified_max_for_medium_bmi = MainActivity.carbo_spicified_max_for_medium_bmi+ (carbohydrates);
                                            Toast.makeText(BasketActivity.this, "Order Is Removed", Toast.LENGTH_SHORT);
                                        }
                                        else {
                                            MainActivity.protein_spicified = MainActivity.protein_spicified + (protein);
                                            MainActivity.fat_spicified = MainActivity.fat_spicified + (fat);
                                            MainActivity.carbo_spicified = MainActivity.carbo_spicified + (carbohydrates);
                                            Toast.makeText(BasketActivity.this, "Order Is Removed", Toast.LENGTH_SHORT);
                                        }
                                        reference.child("basket").child(user.getUid()).child(list.get(position).getMeal_name()).removeValue();

                                        dialog.cancel();
                                        recreate();

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

                        return true;
                    }
                });
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



        confirm_meal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                progressBar.setVisibility(View.VISIBLE);

                Calendar x1 = Calendar.getInstance();
                final int current_day_of_year=x1.get(Calendar.DAY_OF_YEAR);
                final String current_day=""+current_day_of_year;

                reference.child("Daily_orders").child(user.getUid()).child(current_day).child(MainActivity.meal_time_name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if ((dataSnapshot.hasChild("")))
                        {
                            TastyToast.makeText(BasketActivity.this,"Sorry, You Took "+MainActivity.meal_time_name+" Meal",TastyToast.LENGTH_LONG,TastyToast.INFO);
                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                        else
                            {

                                    reference.child("basket").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                TakeMealModel model = snapshot.getValue(TakeMealModel.class);
                                                allcarbo_basket += model.getCarbohydrates();
                                                allprotein_basket += model.getProteins();
                                                allfats_basket += model.getFats();
                                            }

                                            if (MainActivity.BMI<18.5 || MainActivity.BMI >18.5 && MainActivity.BMI<25) {

                                                if(allcarbo_basket==0&&allfats_basket==0&&allprotein_basket==0)
                                                {
                                                    Toast.makeText(BasketActivity.this, "Please Add Elements To Basket", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.GONE);
                                                    return;
                                                }

                                            if (allcarbo_basket < MainActivity.carbo_spicified) {
                                                Toast.makeText(BasketActivity.this, "You have a little CARBO, Please Increase it", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                return;

                                            } else if (allprotein_basket < MainActivity.protein_spicified) {
                                                Toast.makeText(BasketActivity.this, "You have a little PROTEIN, Please Increase it", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                return;
                                            } else if (allfats_basket < MainActivity.fat_spicified) {
                                                Toast.makeText(BasketActivity.this, "You have a little FAT, Please Increase it", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                return;
                                            }
                                        }
                                            reference.child("basket").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        TakeMealModel model = snapshot.getValue(TakeMealModel.class);
                                                        upload.add(model);
                                                        reference.child("Daily_orders").child(user.getUid()).child(current_day)
                                                                .child(MainActivity.meal_time_name).setValue(upload);
                                                    }
                                                    progressBar.setVisibility(View.GONE);
                                                    reference.child("basket").child(user.getUid()).removeValue();
                                                    recreate();
                                                    TastyToast.makeText(BasketActivity.this, "Enjoy Your Meal", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError)
                                                {
                                                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                                                    startActivity(i);
                                                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError)
                                        {
                                            TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                            final Intent i = new Intent(getApplicationContext(), SginIn.class);
                                            startActivity(i);
                                            new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                                        }
                                    });


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });
    }
}
