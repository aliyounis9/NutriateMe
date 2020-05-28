package com.Malnutrition.ProblemOfMalnutrition;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Fragment_Tabbed_Breakfast extends Fragment
{

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ListView listView;
    ArrayList<TakeMealModel> list = new ArrayList<TakeMealModel>();
    TextView day_data;
    Dialog dialog ;



    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_tabbed_breakfast, container, false);

        listView = root.findViewById(R.id.listview_history_Breakfast);
        day_data=root.findViewById(R.id.day_edit_Breakfast);


        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        Calendar x1 = Calendar.getInstance();
        final int this_day_of_year_int=x1.get(Calendar.DAY_OF_YEAR);
        final String this_day_of_year_str=""+this_day_of_year_int;



        String CurrentString = thisDate;
        String first_term_of_date;
        String temp_data_complete;
        String second_term_of_date;
        String third_term_of_date;

        String[] separated = CurrentString.split("/");

        first_term_of_date=separated[0];
        History_tapped.first_term_of_date_int=Integer.parseInt(first_term_of_date);
        temp_data_complete=separated[1];

        String[] separated2 = temp_data_complete.split("/");

        second_term_of_date=separated2[0];
        third_term_of_date=temp_data_complete;


        String temp_txt_date;
        temp_txt_date=" Day : "+ History_tapped.first_term_of_date_int+"/"+second_term_of_date+"/"+third_term_of_date;
        day_data.setText(temp_txt_date);


        init();
        showdialog();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1500);


        reference.child("Daily_orders").child(user.getUid()).child(String.valueOf(this_day_of_year_str)).child("BreakFast").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if (!(dataSnapshot.hasChild("")))
                {
                    return;
                }

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    TakeMealModel model = snapshot.getValue(TakeMealModel.class);
                    list.add(model);
                }

                HistoryAdapter adapter = new HistoryAdapter(getContext(),R.layout.row_basket , list);
                listView.setAdapter(adapter);
                dialog.dismiss();

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


        return root;
    }

    public void init()
    {
        this.dialog = new Dialog(getContext());
    }

    public void showdialog()
    {
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();
    }


}
