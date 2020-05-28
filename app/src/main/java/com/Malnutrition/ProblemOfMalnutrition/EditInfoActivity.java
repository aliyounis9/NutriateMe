package com.Malnutrition.ProblemOfMalnutrition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Malnutrition.ProblemOfMalnutrition.ui.Home.NavHomeFragment;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditInfoActivity extends AppCompatActivity {

    EditText weight , hieght , name , phonenum ;
    Button submit ;
    Boolean checkgroup1_Vegetarianyes;
    Boolean checkgroup2_Diabetes;
    Boolean checkgroup3_Blood_pressure;
    RadioButton diabetes_yes , diabetesno , bloodpres_yes,bloodpres_no,Vegetarianyes,Vegetarianno ;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ProgressBar progressBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        progressBar = findViewById(R.id.main_progress_edit_info);

        weight = findViewById(R.id.editinfo_weight);
        hieght = findViewById(R.id.editinfo_hieght);
        name = findViewById(R.id.editinfo_name);
        phonenum = findViewById(R.id.editinfo_phonenumber);
        submit = findViewById(R.id.editInfo_submit);
        diabetes_yes = findViewById(R.id.editInfo_Diabetesyes);
        diabetesno = findViewById(R.id.editInfo_Diabetesno);
        bloodpres_yes = findViewById(R.id.editInfo_bloodyes);
        bloodpres_no = findViewById(R.id.editInfo_bloodno);
        Vegetarianyes=findViewById(R.id.veg1_ui_EditText_yes);
        Vegetarianno=findViewById(R.id.veg1_ui_EditText_no);

        weight.setText(String.valueOf(MainActivity.w));
        DecimalFormat df = new DecimalFormat("###.##");

        hieght.setText(df.format((Double.parseDouble(String.valueOf(MainActivity.h)))));

        name.setText(NavHomeFragment.name);
        phonenum.setText(NavHomeFragment.phone_number);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                upload();
            }
        });
    }

    private void upload(){

        if(diabetes_yes.isChecked())
        {
            checkgroup2_Diabetes=true;
        }
        else if(diabetesno.isChecked())
        {
            checkgroup2_Diabetes=false;
        }
        else
        {
            TastyToast.makeText(getApplicationContext(), "Check Boxs In Diabetes", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(Vegetarianyes.isChecked())
        {
            checkgroup1_Vegetarianyes=true;
        }
        else if(Vegetarianno.isChecked())
        {
            checkgroup1_Vegetarianyes=false;
        }
        else
        {
            TastyToast.makeText(getApplicationContext(), "Check Boxs In Vegiterian", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(bloodpres_yes.isChecked())
        {
            checkgroup3_Blood_pressure=true;
        }
        else if(bloodpres_no.isChecked())
        {
            checkgroup3_Blood_pressure=false;
        }
        else
        {

            TastyToast.makeText(getApplicationContext(), "Check Boxs In Blood pressure", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (isBlank(name.getText().toString()))
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Name Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
            return;
        }



        if(!isValidNumberLenghth(phonenum.getText().toString()))
        {

            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Number isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            return;
        }

        if(!isValidMobile_build_in_adnroid_stuido(phonenum.getText().toString()))
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Number isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            return;
        }

        if(!isValidMobile(phonenum.getText().toString()))
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Number isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            return;
        }

        if (isBlank(weight.getText().toString()))
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Weight Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
            return;
        }


        if (isBlank(hieght.getText().toString()))
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Height Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
            return;
        }


        if(!is_valid_height_and_weight(Double.parseDouble(weight.getText().toString())))
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Weight Isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
            return;
        }

        if(!is_valid_height_and_weight(Double.parseDouble(hieght.getText().toString())))
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Height Isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
            return;
        }


        reference.child("users").child(user.getUid()).child("Medical Info").child("blood_pressure").setValue(checkgroup3_Blood_pressure);
        reference.child("users").child(user.getUid()).child("Medical Info").child("diabetes").setValue(checkgroup2_Diabetes);
        reference.child("users").child(user.getUid()).child("Medical Info").child("vegetarian").setValue(checkgroup1_Vegetarianyes);

        reference.child("users").child(user.getUid()).child("Medical Info").child("weight").setValue(Double.parseDouble(weight.getText().toString()));
        reference.child("users").child(user.getUid()).child("Medical Info").child("hieght").setValue(Double.parseDouble(hieght.getText().toString()));

        reference.child("users").child(user.getUid()).child("Personal Data").child("name").setValue(name.getText().toString()).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                final Intent i = new Intent(getApplicationContext(), SginIn.class);
                startActivity(i);
                new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                return;
            }
        });
        reference.child("users").child(user.getUid()).child("Personal Data").child("blood_pressure").setValue(phonenum.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                TastyToast.makeText(getApplicationContext(), "Changed Success", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                progressBar.setVisibility(View.GONE);
                final Intent i = new Intent(getApplicationContext(), SginIn.class);
                startActivity(i);
                new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                finish();
            }
        });



    }


    // Checkers Et
    public static boolean isBlank(String value)
    {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }



    public static boolean isValidNumberLenghth(final String number)
    {
        return number.length() >= 10;
    }



    private boolean isValidMobile_build_in_adnroid_stuido(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isValidMobile(String phone)
    {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 13;
        }
        return false;
    }

    public boolean isValidLastName(final String lastname_a){
        Pattern pattern;
        Matcher matcher;
        final String LASTNAME_PATTERN = "[a-zA-Z]+\\.?";
        pattern = Pattern.compile(LASTNAME_PATTERN);
        matcher = pattern.matcher(lastname_a);
        return matcher.matches();
    }



    public Boolean is_valid_height_and_weight(Double num)
    {
        return num > 30 && num < 250;
    }


}
