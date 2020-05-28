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

public class HistoryAdapter extends ArrayAdapter {
    public HistoryAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TakeMealModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.row_basket,parent,false);

        final TextView mealname = view.findViewById(R.id.basket_name);
        final TextView amount = view.findViewById(R.id.basket_components);
        final TextView carbo = view.findViewById(R.id.basket_carbohydrates);
        final TextView proteins  = view.findViewById(R.id.basket_proteins);
        final TextView fats = view.findViewById(R.id.basket_fats);
        final TextView energy = view.findViewById(R.id.basket_energy);
        final TextView water = view.findViewById(R.id.basket_water);
        final TextView sugar = view.findViewById(R.id.basket_sugar);
        final CircleImageView image = view.findViewById(R.id.basket_image);

        TakeMealModel model = (TakeMealModel) getItem(position);

        mealname.setText("Mealname :   "+model.getMeal_name());
        amount.setText("Components :   "+model.getComponents());
        carbo.setText("Carbohydrates :   " +model.getCarbohydrates());
        proteins.setText("Proteins :   "+model.getProteins());
        fats.setText("Fats :   "+model.getFats());
        energy.setText("Energy :   "+model.getEnergy());
        water.setText("Water :   "+model.getWater());
        sugar.setText("Sugar :   "+model.getSugar());
        Picasso.get().load(model.getMeal_img()).into(image);

        return view;
    }
}
