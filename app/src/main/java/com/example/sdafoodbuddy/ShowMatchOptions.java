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

public class ShowMatchOptions extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String ID;
    private String nume;
    private String locatie;
    private String descriere;
    private String timp;
    private TextView n,l,d,t;
    private String watch;
    private boolean fromMenu=false;
    private boolean inapoi=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_match_options);
        mAuth=FirebaseAuth.getInstance();

        Bundle extras=getIntent().getExtras();

        if(extras!=null)
        {

            ID=extras.getString("matchId");
            inapoi=extras.getBoolean("menuToken");

            ID=extras.getString("matchId");
            nume=extras.getString("matchName");
            locatie=extras.getString("matchLocation");
            descriere=extras.getString("matchDescription");
            fromMenu= extras.getBoolean("fromMenu");
            int time=extras.getInt("matchTime");

            int hours =time/60;
            int minutes =time-hours*60;
            if(minutes<10)
                watch = hours +":0"+ minutes;
            else
                watch = hours +":"+ minutes;


        }

        n=findViewById(R.id.name);
        l=findViewById(R.id.location);
        t=findViewById(R.id.time);
        d=findViewById(R.id.description);
        n.setText(nume);
        l.setText(locatie);
        d.setText(descriere);
        t.setText(watch);

    }

    public void mesaj(View view)
    {


        Intent intent= new Intent(ShowMatchOptions.this, Measaje.class);
        intent.putExtra("inapoi",this.getLocalClassName());
        startActivity(intent);
        finish();
        return;
    }

    public void stergeIntalnire(View view)
    {


        String UId=mAuth.getCurrentUser().getUid();
        DatabaseReference mDatabase= FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(UId).child("matches");
        mDatabase.child(ID).removeValue();
        if(!fromMenu) {
            if (inapoi) {
                Intent intent = new Intent(ShowMatchOptions.this, MatchesScreen.class);
                startActivity(intent);
                finish();
                return;
            } else {
                Intent intent = new Intent(ShowMatchOptions.this, MatchesActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        }
        if(fromMenu)
        {

            DatabaseReference mDatabase2= FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(ID).child("matches");
            mDatabase2.child(mAuth.getCurrentUser().getUid()).removeValue();
            Log.d("hahahah",mAuth.getCurrentUser().getUid()+" ; "+ ID);
            Intent intent = new Intent(ShowMatchOptions.this, Menu.class);
            startActivity(intent);
            finish();
            return;

        }

    }
    public void inapoi(View view)
    {
        if(inapoi) {
            Intent intent = new Intent(ShowMatchOptions.this, MatchesScreen.class);
            startActivity(intent);
            finish();
            return;
        }
        else
        {
            Intent intent = new Intent(ShowMatchOptions.this, MatchesActivity.class);
            startActivity(intent);
            finish();
            return;

        }
    }



}