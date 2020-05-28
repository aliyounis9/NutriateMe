package com.Malnutrition.ProblemOfMalnutrition.ui.exercise;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.Malnutrition.ProblemOfMalnutrition.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseFragment extends Fragment {

    ImageView chest , back , shoulders , arm , abs , legs ;

    public ExerciseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        chest = view.findViewById(R.id.chest_ex);
        back = view.findViewById(R.id.back_ex);
        shoulders = view.findViewById(R.id.shoulder_ex);
        arm = view.findViewById(R.id.arm_ex);
        abs = view.findViewById(R.id.abs_ex);
        legs  = view.findViewById(R.id.leg_ex);

        chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , ExerActivity.class);
                i.putExtra("position" ,1);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , ExerActivity.class);
                i.putExtra("position" ,2);
                startActivity(i);
            }
        });
        shoulders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , ExerActivity.class);
                i.putExtra("position" ,3);
                startActivity(i);
            }
        });
        arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , ExerActivity.class);
                i.putExtra("position" ,4);
                startActivity(i);
            }
        });
        abs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , ExerActivity.class);
                i.putExtra("position" ,5);
                startActivity(i);
            }
        });
        legs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , ExerActivity.class);
                i.putExtra("position" ,6);
                startActivity(i);
            }
        });

        return view;
    }

}
