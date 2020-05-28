package com.Malnutrition.ProblemOfMalnutrition;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sdsmdg.tastytoast.TastyToast;

import static java.lang.Integer.parseInt;

public class medical_data_upload extends AppCompatActivity
{

    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public medical_info_Model person_medical_data =new medical_info_Model();

    public String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
public static SharedPreferences SP ;
    ImageButton back ;
    EditText age;
    EditText weight;
    EditText height;
    EditText Blood_pressure;
    String Blood_pressure_optinal="Non";
    EditText Heartrate;
    String Heartrate_optinal="Non";
    RadioGroup radioGroupDia;
    RadioGroup radioGroupveg;
    RadioGroup radioGroupBlood;
    RadioButton Vegetarianyes;
    RadioButton Vegetarianno;
    RadioButton DiaYes;
    RadioButton DiaNo;
    RadioButton BloodYes;
    RadioButton BloodNO;
    TextView next;

    Boolean checkgroup1_Vegetarianyes;
    Boolean checkgroup2_Diabetes;
    Boolean checkgroup3_Blood_pressure;

    ProgressBar progressBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_data_upload);
        init_data_ui();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(medical_data_upload.this , SginIn.class));
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                uploaad_data();
            }
        });

    }

    @SuppressLint("WrongViewCast")
    public void init_data_ui()
    {
        age=findViewById(R.id.age_ui_EditText);
        weight=findViewById(R.id.weight_ui_EditText);
        height=findViewById(R.id.Height_ui_EditText);
        Blood_pressure=findViewById(R.id.blood_ui_EditText);
        Heartrate=findViewById(R.id.Heartrate_ui_EditText);
        radioGroupDia=findViewById(R.id.radioGroup_veg);
        Vegetarianyes=findViewById(R.id.Vegetarian_ui_EditText_yes);
        Vegetarianno=findViewById(R.id.Vegetarian_ui_EditText_no);
        radioGroupveg=findViewById(R.id.radioGroup_Diabetes);
        DiaYes=findViewById(R.id.Diabetes_ui_EditText_yes);
        DiaNo=findViewById(R.id.Diabetes_ui_EditText_no);
        next=findViewById(R.id.ui_submit_MedicalData_Button_ui);
        progressBar = findViewById(R.id.main_progress);
        radioGroupBlood=findViewById(R.id.radioGroup_blood_pressure);
        BloodYes=findViewById(R.id.blood_pressure_ui_EditText_yes);
        BloodNO=findViewById(R.id.blood_pressure_ui_EditText_no);
        back = findViewById(R.id.backarrow);

    }

    public void on_data_changed_listner_groups()
    {
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
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Check Boxs In Vegetarianyes", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            return;
        }
        if(DiaYes.isChecked())
        {
            checkgroup2_Diabetes=true;
        }
        else if(DiaNo.isChecked())
        {
            checkgroup2_Diabetes=false;
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Check Boxs In Diabetes", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            return;
        }

        if(BloodYes.isChecked())
        {
            checkgroup3_Blood_pressure=true;
        }
        else if(BloodNO.isChecked())
        {
            checkgroup3_Blood_pressure=false;
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Check Boxs In Blood pressure", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            return;
        }
    }

    public void complete_optional_data()
    {
        if (isBlank(Blood_pressure.getText().toString()))
        {
            Blood_pressure.setText(Blood_pressure_optinal);
        }

        if (isBlank(Heartrate.getText().toString()))
        {
            Heartrate.setText(Heartrate_optinal);
        }

    }


    // Checkers Et
    public static boolean isBlank(String value)
    {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }


    public void uploaad_data()
    {
        complete_optional_data();
       // on_data_changed_listner_groups();
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
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Check Boxs In Vegetarianyes", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            return;
        }
        if(DiaYes.isChecked())
        {
            checkgroup2_Diabetes=true;
        }
        else if(DiaNo.isChecked())
        {
            checkgroup2_Diabetes=false;
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Check Boxs In Diabetes", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            return;
        }

        if(BloodYes.isChecked())
        {
            checkgroup3_Blood_pressure=true;
        }
        else if(BloodNO.isChecked())
        {
            checkgroup3_Blood_pressure=false;
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Check Boxs In Blood pressure", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            return;
        }

        if (isBlank(weight.getText().toString()))
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Weight Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
            return;
        }


        if (isBlank(height.getText().toString()))
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Height Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
            return;
        }

        if (isBlank(age.getText().toString()))
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Age Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
            return;
        }

        if(!is_valid_height_and_weight(parseInt(weight.getText().toString())))
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Weight Isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
            return;
        }

        if(!is_valid_height_and_weight(parseInt(height.getText().toString())))
        {
            progressBar.setVisibility(View.GONE);
            TastyToast.makeText(getApplicationContext(), "Height Isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
            return;
        }

        person_medical_data.setAge(age.getText().toString());
        person_medical_data.setBlood_type(Blood_pressure.getText().toString());
        person_medical_data.setDiabetes(checkgroup2_Diabetes);
        person_medical_data.setVegetarian(checkgroup1_Vegetarianyes);
        person_medical_data.setHeart_Rate(Heartrate.getText().toString());
        person_medical_data.setWeight(Double.parseDouble(weight.getText().toString()));
        person_medical_data.setHieght(Double.parseDouble(height.getText().toString()));
        person_medical_data.setBlood_pressure(checkgroup3_Blood_pressure);


        databaseReference.child("users").child(currentuser).child("Medical Info").setValue(person_medical_data).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    TastyToast.makeText(getApplicationContext(), "Successful SignUp", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    final Intent i = new Intent(medical_data_upload.this, breakfast_meal.class);
                    startActivity(i);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Error While Upload Medical Data", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                    startActivity(i);
                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                    return;
                }
            }
        });



    }



    public Boolean is_valid_height_and_weight(Integer num)
    {
        return num > 30 && num < 250;
    }


}


