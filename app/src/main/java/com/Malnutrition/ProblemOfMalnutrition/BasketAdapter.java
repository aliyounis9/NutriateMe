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

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BasketAdapter extends ArrayAdapter {
    public BasketAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TakeMealModel> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.basket_row,parent,false);

        final DecimalFormat precision = new DecimalFormat("0.00");

        final TextView name = view.findViewById(R.id.basket_meal_name);
        final TextView comp = view.findViewById(R.id.basket_components_breakfast);
        final TextView details = view.findViewById(R.id.basket_bmi_breakfast);
        final TextView callory = view.findViewById(R.id.basket_callory);
        final CircleImageView image = view.findViewById(R.id.basket_meal_img);

        TakeMealModel model = (TakeMealModel) getItem(position);

        Picasso.get().load(model.getMeal_img()).into(image);
        name.append(model.getMeal_name());
        comp.append(model.getComponents() +" Gram");
        details.append(""+precision.format(model.getCarbohydrates()) +" g Carbo -- " + precision.format(model.getProteins()) +" g Prot\n              "+precision.format(model.getFats())+" g Fats");
        callory.setText("Callories : "+model.getEnergy()+" C");

        return view ;
    }
}
