package com.Malnutrition.ProblemOfMalnutrition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends ArrayAdapter
{
    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Meals_get_Model> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.row , parent , false);
        final TextView meal_name = view.findViewById(R.id.row_meal_name_details_txt);
        final CircleImageView meal_img = view.findViewById(R.id.row_meal_img);

        Meals_get_Model modelMenu = (Meals_get_Model) getItem(position);
        meal_name.setText(modelMenu.getMeal_name());
        Picasso.get().load(modelMenu.getMeal_img()).into(meal_img);

        return view ;
    }
}

