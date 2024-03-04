package com.example.sdafoodbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AcceptMatch extends AppCompatActivity {

    private TextView nume;
    private TextView locatie;
    private TextView timp;
    private TextView descriere;
    private FirebaseAuth mAuth;
    private String ID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_accept_match);
        mAuth=FirebaseAuth.getInstance();
        nume=findViewById(R.id.nume);
        locatie=findViewById(R.id.locatie);
        timp=findViewById(R.id.timp);
        descriere=findViewById(R.id.descriere);

        String watch;
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            nume.setText(extras.getString("matchName"));
            int time=extras.getInt("matchTime");

            int hours =time/60;
            int minutes =time-hours*60;
            if(minutes<10)
                watch = hours +":0"+ minutes;
            else
                watch = hours +":"+ minutes;
            timp.setText(watch);
            descriere.setText(extras.getString("matchDescription"));
            locatie.setText(extras.getString("matchLocation"));
            ID=extras.getString("matchId");


        }

    }

    public void inapoi(View view)
    {
        Intent intent= new Intent(AcceptMatch.this, MatchesActivity.class);
        startActivity(intent);
        finish();
        return;
    }
    public void accepta(View view)
    {

        String UId=mAuth.getCurrentUser().getUid();
        DatabaseReference mDatabase= FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(UId).child("matches");
        mDatabase.child(ID).push().setValue("lol");
        Intent intent= new Intent(AcceptMatch.this, ShowMatchOptions.class);
        intent.putExtra("matchId",ID);
        intent.putExtra("matchTime",timp.getText());
        intent.putExtra("matchName",nume.getText());
        intent.putExtra("matchLocation",locatie.getText());
        intent.putExtra("matchDescription",descriere.getText());



        startActivity(intent);
        finish();
        return;
    }



}