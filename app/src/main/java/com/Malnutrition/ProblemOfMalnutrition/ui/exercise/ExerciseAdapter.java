package com.Malnutrition.ProblemOfMalnutrition.ui.exercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Malnutrition.ProblemOfMalnutrition.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExerciseAdapter extends ArrayAdapter {
    public ExerciseAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ExerciseModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.row_exercise,parent , false);

        ImageView exerimg = view.findViewById(R.id.exer_img);
        TextView exername = view.findViewById(R.id.exer_name);
        TextView exercount = view.findViewById(R.id.exer_count);

        ExerciseModel model = (ExerciseModel) getItem(position);

        Picasso.get().load(model.getExerimg()).into(exerimg);
        exername.setText(model.getExername());
        exercount.setText(model.getExercount());

        return view ;
    }
}
