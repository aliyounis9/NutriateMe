package com.Malnutrition.ProblemOfMalnutrition.ui.exercise;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Malnutrition.ProblemOfMalnutrition.R;
import com.Malnutrition.ProblemOfMalnutrition.SginIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class ExerActivity extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    int position;
    ListView listView;
    ArrayList<ExerciseModel> arrayList = new ArrayList<ExerciseModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer);

        listView = findViewById(R.id.listview_exercise);
        position = getIntent().getIntExtra("position", 0);

        if (position == 1) {
            reference.child("exercise").child("chest").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ExerciseModel model = snapshot.getValue(ExerciseModel.class);
                        arrayList.add(model);
                    }
                    ExerciseAdapter adapter = new ExerciseAdapter(ExerActivity.this, R.layout.row_exercise, arrayList);
                    listView.setAdapter(adapter);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            if(position==0)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=rT7DgCr-3pg"));
                                startActivity(intent);
                            }
                            else if(position==1)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=omGiL5h2R_I"));
                                startActivity(intent);
                            }
                            else if(position==2)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=SrqOu55lrYU"));
                                startActivity(intent);
                            }
                            else if(position==3)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=xUm0BiZCWlQ"));
                                startActivity(intent);
                            }
                            else if(position==4)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=0G2_XV7slIg"));
                                startActivity(intent);
                            }
                            else if(position==5)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=53KM-mmJhic"));
                                startActivity(intent);
                            }
                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                    startActivity(i);
                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                    return;
                }
            });
        } else if (position == 2) {
            reference.child("exercise").child("back").addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ExerciseModel model = snapshot.getValue(ExerciseModel.class);
                        arrayList.add(model);
                    }
                    ExerciseAdapter adapter = new ExerciseAdapter(ExerActivity.this, R.layout.row_exercise, arrayList);
                    listView.setAdapter(adapter);




                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            if(position==0)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=F-jSX1SRneI"));
                                startActivity(intent);
                            }
                            else if(position==1)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=vT2GjY_Umpw"));
                                startActivity(intent);
                            }
                            else if(position==2)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=KDEl3AmZbVE"));
                                startActivity(intent);
                            }
                            else if(position==3)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=3UwO0fKukRw"));
                                startActivity(intent);
                            }

                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                    startActivity(i);
                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                    return;
                }
            });
        }
        else if (position == 3)
        {
            reference.child("exercise").child("shoulders").addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ExerciseModel model = snapshot.getValue(ExerciseModel.class);
                        arrayList.add(model);
                    }
                    ExerciseAdapter adapter = new ExerciseAdapter(ExerActivity.this, R.layout.row_exercise, arrayList);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            if(position==0)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=qEwKCR5JCog"));
                                startActivity(intent);
                            }
                            else if(position==1)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=nIJGvsdNtFE"));
                                startActivity(intent);
                            }
                            else if(position==2)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=DEeUsQ9rCso"));
                                startActivity(intent);
                            }
                            else if(position==3)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=3VcKaXpzqRo"));
                                startActivity(intent);
                            }

                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                    startActivity(i);
                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                    return;
                }
            });
        } else if (position == 4)
        {
            reference.child("exercise").child("arms").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ExerciseModel model = snapshot.getValue(ExerciseModel.class);
                        arrayList.add(model);
                    }
                    ExerciseAdapter adapter = new ExerciseAdapter(ExerActivity.this, R.layout.row_exercise, arrayList);
                    listView.setAdapter(adapter);



                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            if(position==0)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=6eFd7JB9WH4"));
                                startActivity(intent);
                            }
                            else if(position==1)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=zG2xJ0Q5QtI"));
                                startActivity(intent);
                            }
                            else if(position==2)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=zC3nLlEvin4"));
                                startActivity(intent);
                            }
                            else if(position==3)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=2-LAMcpzODU"));
                                startActivity(intent);
                            }
                            else if(position==4)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=9wxRhONFsRA"));
                                startActivity(intent);
                            }

                        }
                    });



                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                    startActivity(i);
                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                    return;
                }
            });
        } else if (position == 5) {
            reference.child("exercise").child("abs").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ExerciseModel model = snapshot.getValue(ExerciseModel.class);
                        arrayList.add(model);
                    }
                    ExerciseAdapter adapter = new ExerciseAdapter(ExerActivity.this, R.layout.row_exercise, arrayList);
                    listView.setAdapter(adapter);



                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            if(position==0)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=DbUeY1namqU"));
                                startActivity(intent);
                            }
                            else if(position==1)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=4ot0S6kD-Wo"));
                                startActivity(intent);
                            }
                            else if(position==2)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=3fDjzzfovZE"));
                                startActivity(intent);
                            }
                            else if(position==3)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=dL9ZzqtQI5c"));
                                startActivity(intent);
                            }

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                    startActivity(i);
                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                    return;
                }
            });
        } else if (position == 6) {
            reference.child("exercise").child("legs").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ExerciseModel model = snapshot.getValue(ExerciseModel.class);
                        arrayList.add(model);
                    }
                    ExerciseAdapter adapter = new ExerciseAdapter(ExerActivity.this, R.layout.row_exercise, arrayList);
                    listView.setAdapter(adapter);



                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            if(position==0)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=tlfahNdNPPI"));
                                startActivity(intent);
                            }
                            else if(position==1)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=D7KaRcUTQeE"));
                                startActivity(intent);
                            }
                            else if(position==2)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=IZxyjW7MPJQ"));
                                startActivity(intent);
                            }
                            else if(position==3)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=Wp4BlxcFTkE"));
                                startActivity(intent);
                            }
                            else if(position==4)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "watch?v=YyvSfVjQeL0"));
                                startActivity(intent);
                            }

                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    TastyToast.makeText(getApplicationContext(), "Check Your Internet Connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    final Intent i = new Intent(getApplicationContext(), SginIn.class);
                    startActivity(i);
                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                    return;
                }
            });
        }



    }
}
