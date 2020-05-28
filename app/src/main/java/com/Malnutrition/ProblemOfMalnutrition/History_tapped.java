package com.Malnutrition.ProblemOfMalnutrition;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class History_tapped extends AppCompatActivity {

    public static int current_day ;
    public static Integer first_term_of_date_int;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_tapped);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab_up_day = findViewById(R.id.fab_up_day);
        FloatingActionButton fab_down_day = findViewById(R.id.fab_down_day);




        fab_up_day.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "The Day After", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                current_day -= 1;
                first_term_of_date_int-=1;
                recreate();
            }
        });

        fab_down_day.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "The Day Before", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                current_day+=1;
                first_term_of_date_int+=1;
                recreate();
            }
        });



    }
}