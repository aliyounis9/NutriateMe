package com.Malnutrition.ProblemOfMalnutrition;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signUP_activity extends AppCompatActivity

{

    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();



    public UserModel person =new UserModel();
    public String url;
    public String uid;
    EditText Email;
    EditText Password;
    EditText name;
    EditText phone_num;
    Button Sign_up;
    ImageView img_upload_select;
    Button btn_upload_photo;
    Boolean bool_check_dataMedical;

    // Creating URI.
    Uri FilePathUri;
    ProgressBar progressBar ;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);

        progressBar = findViewById(R.id.main_progress);
        Email=findViewById(R.id.ui_EmailAddress_EditText_LoginActivity);
        Password=findViewById(R.id.ui_Password_EditText_LoginActivity);
        name=findViewById(R.id.name_ui_EditText);
        phone_num=findViewById(R.id.phonenum_ui_EditText);
        Sign_up=findViewById(R.id.ui_signup_Button_ui);
        /*
        img_upload_select=findViewById(R.id.imgPersonToUploadUi);
        btn_upload_photo=findViewById(R.id.buttontouploadui);



         */


        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                createAccount(Email.getText().toString(),Password.getText().toString(),name.getText().toString(),phone_num.getText().toString());
            }
        });

        /*
        img_upload_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                selectImage();
            }
        });

        btn_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                selectImage();
            }
        });

         */


    }

    private void createAccount(final String email, String password, final String name,final String phone)
    {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null)
        {
            if (isBlank(email))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Email Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if(!isEmailValid(email))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Email isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }

            if (isBlank(name))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Name Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if(!isValidLastName(name))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Name isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }


            if(!isValidNumberLenghth(phone_num))
            {

                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Number isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }

            if(!isValidMobile_build_in_adnroid_stuido(phone_num.getText().toString()))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Number isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }

            if(!isValidMobile(phone_num.getText().toString()))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Number isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }


            if (isBlank(password)) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Password Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }


            if(!isValidPasswordLenghth(password))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "password must be at least 8 characters", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }




            // Code For Create The Account
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(signUP_activity.this, new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        SginIn.SP = getSharedPreferences(SginIn.filename, MODE_PRIVATE);
                        SharedPreferences.Editor editit = SginIn.SP.edit();
                        editit.putString("user_name", name);
                        editit.putString("phonenumber", phone);
                        editit.commit();
                        uid = task.getResult().getUser().getUid();
                        //uploadUserImage(uid);
                        upload_data_sign(Email.getText().toString(),Password.getText().toString(),name,phone_num.getText().toString());
                    }
                    if (!task.isSuccessful())
                    {
                        try
                        {

                            throw task.getException();
                        }
                        // if user enters wrong email.
                        catch (FirebaseAuthWeakPasswordException weakPassword)
                        {

                            TastyToast.makeText(getApplicationContext(), "Weak password .. Please Choose A Valid Password", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            progressBar.setVisibility(View.GONE);
                            return;
                            // TODO: take your actions!
                        }
                        // if user enters wrong password.
                        catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                        {

                            TastyToast.makeText(getApplicationContext(), "Malformed Email .. Please Change Your Email", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            progressBar.setVisibility(View.GONE);
                            return;

                            // TODO: Take your action
                        }
                        catch (FirebaseAuthUserCollisionException existEmail)
                        {


                            TastyToast.makeText(getApplicationContext(), "Exist Email .. Please Change Your Email", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            progressBar.setVisibility(View.GONE);
                            return;

                            // TODO: Take your action
                        }
                        catch (Exception e)
                        {
                            TastyToast.makeText(getApplicationContext(), "Error In :"+e.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                    }

                }
            });
            return;

        }
        else
        {
            if(new SginIn.InternetDialog(signUP_activity.this).getInternetStatus())
            {

            }
            return;
        }


    }

    // Checkers Et
    public static boolean isBlank(String value)
    {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }

    public static boolean isEmailValid(String email)
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static boolean isValidPasswordLenghth(final String password)
    {
        return password.length() >= 8;
    }


    public static boolean isValidNumberLenghth(final EditText number)
    {
        return number.toString().length() >= 10;
    }


    //Photo Upload Functions

    /*
    private void selectImage() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        startActivityForResult(pickPhoto, 10);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            FilePathUri = data.getData();
            img_upload_select.setImageURI(FilePathUri);
        }
    }

     */




    /*
    private void uploadUserImage(final String uid)
    {
        if (FilePathUri == null)
        {
            TastyToast.makeText(getApplicationContext(), " Please select image ", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            selectImage();
            return;
        }
        else
        {
            storageReference.child("profileImages").child(uid).putFile(FilePathUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                {
                    if (task.isSuccessful())
                    {
                        storageReference.child("profileImages").child(uid).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task)
                            {
                                if (task.isSuccessful())
                                {
                                    url = task.getResult().toString();
                                    upload_data_sign(Email.getText().toString(),Password.getText().toString(),name.getText().toString(),phone_num.getText().toString());
                                }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    TastyToast.makeText(getApplicationContext(), "Error While Getting Link Upload The Photo", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                }
                            }
                        });
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        TastyToast.makeText(getApplicationContext(), "Error While Upload The Photo", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }
                }
            });
        }

    }


     */


    public void upload_data_sign(final String email, String password, final String name, final String phonenum)
    {
        person.setEmail(email);
        person.setName(name);
        person.setPhonenumber(phonenum);
        person.setUID(uid);
        person.setBool_check_dataMedical(false);

        databaseReference.child("users").child(uid).child("Personal Data").setValue(person).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    TastyToast.makeText(getApplicationContext(), "Successful SignUp", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    final Intent i = new Intent(signUP_activity.this, SginIn.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Error While Create User Data", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }

            }
        });
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


}


/* XML To IMG :

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:orientation="horizontal"
        android:gravity="center"
        >
        <ImageView
            android:id="@+id/imgPersonToUploadUi"
            android:layout_width="100dp"
            android:layout_height="match_parent"
            android:src="@drawable/person"
            android:layout_gravity="center"
            />

        <Button
            android:id="@+id/buttontouploadui"
            android:layout_width="match_parent"
            android:layout_height="48dp"
            android:layout_gravity="center"
            android:layout_marginEnd="16dp"
            android:background="@drawable/button"
            android:gravity="center"
            android:textAllCaps="false"
            android:padding="10dp"
            android:text="Add Photo"
            android:textColor="@color/btn_Text_color"
            android:textSize="20dp">

        </Button>

    </LinearLayout>

 */
