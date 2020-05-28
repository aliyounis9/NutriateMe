package com.Malnutrition.ProblemOfMalnutrition.ui.Home;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.Malnutrition.ProblemOfMalnutrition.EditInfoActivity;
import com.Malnutrition.ProblemOfMalnutrition.InstantMeal;
import com.Malnutrition.ProblemOfMalnutrition.MainActivity;
import com.Malnutrition.ProblemOfMalnutrition.R;
import com.Malnutrition.ProblemOfMalnutrition.SginIn;
import com.Malnutrition.ProblemOfMalnutrition.ui.carbohydrates.CarbohydratesFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavHomeFragment extends Fragment {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView username, wel_bmi, weight_txt, hieght_txt;
    public static TextView main_screen_textbox_meal_name;
    Button editinfo, instantmeal, designmeal, basket, history;
    public static String name, phone_number;
    public static Boolean is_veg,is_have_high_blood,is_habe_diabetes;

    public NavHomeFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_home, container, false);

        editinfo = view.findViewById(R.id.editInfo_btn);
        instantmeal = view.findViewById(R.id.instant_meal);
        designmeal = view.findViewById(R.id.design_meal);
        username = view.findViewById(R.id.welcome_name);
        wel_bmi = view.findViewById(R.id.welcome_bmi);
        weight_txt = view.findViewById(R.id.welcome_weight);
        hieght_txt = view.findViewById(R.id.welcome_hieght);
        main_screen_textbox_meal_name = view.findViewById(R.id.main_screen_textbox_meal_name);


        reference.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("Personal Data").child("name").getValue(String.class);
                phone_number = dataSnapshot.child("Personal Data").child("phonenumber").getValue(String.class);

                is_veg=dataSnapshot.child("Medical Info").child("vegetarian").getValue(Boolean.class);
                is_have_high_blood=dataSnapshot.child("Medical Info").child("blood_pressure").getValue(Boolean.class);
                is_habe_diabetes=dataSnapshot.child("Medical Info").child("diabetes").getValue(Boolean.class);

                username.append("Welcome : " + name);

                DecimalFormat df = new DecimalFormat("##.##");
                DecimalFormat dft = new DecimalFormat("###.##");


                wel_bmi.append(df.format(MainActivity.BMI));
                weight_txt.append(dft.format(MainActivity.w) + " Kg");
                hieght_txt.append(dft.format(MainActivity.h) + " Cm");

                main_screen_textbox_meal_name.setText("( Meal : " + MainActivity.meal_time_name + " )");


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


        editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditInfoActivity.class));
            }
        });

        designmeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_order_avilability();

            }
        });

        instantmeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                check_order_avilability_to_instant_meal();
            }
        });


        return view;
    }


    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
                    TastyToast.makeText(getContext(), "Sorry, You Took " + MainActivity.meal_time_name + " Meal", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    return;
                }
                else
                {
                    CarbohydratesFragment frag = new CarbohydratesFragment();
                    replaceFragment(frag);
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

    public void check_order_avilability_to_instant_meal()
    {

        Calendar x1 = Calendar.getInstance();
        final int current_day_of_year = x1.get(Calendar.DAY_OF_YEAR);
        final String current_day = "" + current_day_of_year;

        reference.child("Daily_orders").child(user.getUid()).child(current_day).child(MainActivity.meal_time_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if ((dataSnapshot.hasChild(""))) {
                    TastyToast.makeText(getContext(), "Sorry, You Took " + MainActivity.meal_time_name + " Meal", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    return;
                }
                else
                {
                    startActivity(new Intent(getActivity(), InstantMeal.class));
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

    public  boolean nav_home_getInternetStatus()
    {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected)
        {
            //show no internet dialog
            TastyToast.makeText(getContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            final Intent i = new Intent(getContext(), SginIn.class);
            startActivity(i);
            new SginIn.InternetDialog(getContext()).getInternetStatus();
        }
        return isConnected;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        new SginIn.InternetDialog(getContext()).getInternetStatus();
        super.onCreate(savedInstanceState);
    }
}