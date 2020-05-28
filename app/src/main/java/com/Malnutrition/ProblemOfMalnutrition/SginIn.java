package com.Malnutrition.ProblemOfMalnutrition;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SginIn extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    // Write a message to the database
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    public static SharedPreferences SP ;
    public static String filename = "Keys";
    public static boolean isConnected;


    protected EditText userEmail_et, userPassword_et;
    protected Button login_btn;
    protected TextView signup;
    CheckBox remember_me;

    ProgressBar progressBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activity);

        userEmail_et = findViewById(R.id.ui_EmailAddress_EditText_LoginActivity);
        userPassword_et = findViewById(R.id.ui_Password_EditText_LoginActivity);
        login_btn = findViewById(R.id.ui_login_btn);
        signup = findViewById(R.id.loginAct_createacc);
        SpannableString content = new SpannableString("Sign Up");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        signup.setText(content);
        progressBar = findViewById(R.id.main_progress);
        remember_me=findViewById(R.id.remember_me_ui);

        SP = getSharedPreferences(filename, MODE_PRIVATE);
        String getname = SP.getString("UserName_saved_key1","");
        String getpass = SP.getString("Password_Saved_key2","");

//        if (getname!=null && getpass!=null){
//
//            startActivity(new Intent(SginIn.this , MainActivity.class));
//        }

        userEmail_et.setText(getname);
        userPassword_et.setText(getpass);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final Intent e = new Intent(SginIn.this, signUP_activity.class);
                startActivity(e);
                progressBar.setVisibility(View.GONE);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                if(remember_me.isChecked())
                {
                    rememberMe(userEmail_et.toString(),userPassword_et.toString());
                }
                else
                {
                    SharedPreferences preferences =getSharedPreferences(filename,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("UserName_saved_key1");
                    editor.remove("Password_Saved_key2");
                    editor.apply();
                    editor.clear();
                    editor.commit();
                }
                signin(userEmail_et.getText().toString(), userPassword_et.getText().toString());
            }
        });



    }

    public void rememberMe(String user, String password){
        //save username and password in SharedPreferences

        SharedPreferences.Editor editit = SP.edit();
        editit.putString("UserName_saved_key1", userEmail_et.getText().toString());
        editit.putString("Password_Saved_key2",userPassword_et.getText().toString());
        editit.commit();

    }



    public static class InternetDialog
    {
        private Context context;

        public InternetDialog()
        {

        }

        public InternetDialog(Context context)
        {
            this.context = context;
        }

        public void showNoInternetDialog()
        {
            final Dialog dialog1 = new Dialog(context, R.style.df_dialog);
            dialog1.setContentView(R.layout.dialog_no_internet);
            dialog1.setCancelable(true);
            dialog1.setCanceledOnTouchOutside(true);
            dialog1.findViewById(R.id.btnSpinAndWinRedeem).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(!isConnected)
                    {
                        getInternetStatus();
                    }
                    else
                    {
                        dialog1.dismiss();
                    }
                }
            });

            dialog1.show();
        }



        public  boolean getInternetStatus()
        {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if(!isConnected)
            {
                //show no internet dialog
                showNoInternetDialog();
            }
            return isConnected;
        }
    }



    public static boolean isBlank(String value)
    {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static boolean isValidPasswordLenghth(final String password)
    {
        return password.length() >= 8;
    }




    void signin(final String email, final String password)
    {

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        if (networkInfo != null)
        {
            if (isBlank(userEmail_et.getText().toString()))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Email Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if (isBlank(userPassword_et.getText().toString()))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Password Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if(!isEmailValid(userEmail_et.getText().toString()))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Email isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }


            if(!isValidPasswordLenghth(userPassword_et.getText().toString()))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "password must be at least 8 characters", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        reference.child("medical_check").addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                if(user!=null)
                                {
                                    if (dataSnapshot.hasChild(user.getUid()))
                                    {
                                        rememberMe(email, password);
                                        TastyToast.makeText(getApplicationContext(), "Login Successful", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        final Intent i = new Intent(SginIn.this, MainActivity.class);
                                        i.putExtra("user", userEmail_et.toString());
                                        startActivity(i);
                                        finish();
                                    }
                                    else {
                                        rememberMe(email, password);
                                        TastyToast.makeText(getApplicationContext(), "Login Successful", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        final Intent i = new Intent(SginIn.this, medical_data_upload.class);
                                        i.putExtra("user", userEmail_et.toString());
                                        startActivity(i);
                                        finish();
                                    }
                                }
                                else
                                {
                                    rememberMe(email, password);
                                    TastyToast.makeText(getApplicationContext(), "Login Successful", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                    final Intent i = new Intent(SginIn.this, medical_data_upload.class);
                                    i.putExtra("user", userEmail_et.toString());
                                    startActivity(i);
                                    finish();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError)
                            {
                                progressBar.setVisibility(View.GONE);
                                TastyToast.makeText(getApplicationContext(), "Login Failed", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            }
                        });

                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        TastyToast.makeText(getApplicationContext(), "Login Failed", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }
                }
            });
            return;

        }
        else
        {
                new InternetDialog(SginIn.this).getInternetStatus();
                progressBar.setVisibility(View.GONE);
                return;
        }

    }




}

