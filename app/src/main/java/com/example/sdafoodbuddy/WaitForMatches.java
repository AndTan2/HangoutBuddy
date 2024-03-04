package com.example.sdafoodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WaitForMatches extends AppCompatActivity implements SelectListener{
    private RecyclerView mRecyclerView;
    private MatchesAdapter mMatchesAdapter;
    private List<Matches> matchesList;
    private FirebaseAuth mAuth;
    private String currentUserId;
    private TextView locatie, timp, descriere;
    private Context context;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_wait_for_matches);
        locatie =findViewById(R.id.locatie);
        timp =findViewById(R.id.timp);
        descriere =findViewById(R.id.descriere);
        b=findViewById(R.id.stergeIntalnire);
        mAuth= FirebaseAuth.getInstance();
        //Initialize RecyclerView
        mRecyclerView = findViewById(R.id.matches_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize matchesList
        matchesList = new ArrayList<>();

        //Initialize MatchesAdapter
        mMatchesAdapter = new MatchesAdapter(this, matchesList,this);
        mRecyclerView.setAdapter(mMatchesAdapter);

        //Get current user ID
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference currentUserDb = FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users");


        currentUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!(boolean)snapshot.child(currentUserId).child("createdActivity").getValue())
                    b.setText("Creeaza o intalnire");

                if((boolean) snapshot.child(currentUserId).child("createdActivity").getValue())
                {



                locatie.setText(snapshot.child(currentUserId).child("location").getValue().toString());
                String watch;
                int time=snapshot.child(currentUserId).child("Time in minutes").getValue(Integer.class);
                int hours =time/60;
                int minutes =time-hours*60;
                if(minutes<10)
                    watch =String.valueOf(hours)+":0"+String.valueOf(minutes);
                else
                    watch =String.valueOf(hours)+":"+String.valueOf(minutes);
                timp.setText(watch);
                descriere.setText(snapshot.child(currentUserId).child("description").getValue().toString());
                for (DataSnapshot ds : snapshot.getChildren()) {


                    if (ds.getKey() != currentUserId && ds.child("matches").exists()) {


                        String matchId = ds.getKey();
                        int matchTime = ds.child("Time in minutes").getValue(Integer.class);
                        String matchGender = ds.child("Gender Match").getValue(String.class);
                        String gender = ds.child("gender").getValue(String.class);
                        String matchName = ds.child("name").getValue(String.class);
                        String matchDescription = ds.child("description").getValue(String.class);
                        String matchLocation = ds.child("location").getValue(String.class);
                        boolean createdActivity = ds.child("createdActivity").getValue(boolean.class);
                        Log.d("joker","lista match: "+matchId+" ; "+ds.child("matches").exists());
                        for (DataSnapshot matchesSnapshot : ds.child("matches").getChildren()) {
                            Log.d("joker","lista match: "+currentUserId+";"+matchesSnapshot.getKey());
                            if (currentUserId.equals(matchesSnapshot.getKey())) {
                                Log.d("joker","lista match: "+matchesList.size());
                                Matches match = new Matches(matchId, matchTime, matchGender, matchName, matchDescription, matchLocation, createdActivity);
                                matchesList.add(match);

                            }

                        }
                        if (matchesList.size() > 0) {

                            mMatchesAdapter.notifyDataSetChanged();

                        }

                    }


                }
            }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onItemClicked(Matches matches) {
        Intent intent= new Intent(WaitForMatches.this, ShowMatchOptions.class);
        intent.putExtra("fromMenu",(boolean)true);
        intent.putExtra("matchName",matches.getMatchName());
        intent.putExtra("matchTime",matches.getMatchTime());
        intent.putExtra("matchDescription",matches.getDescription());
        intent.putExtra("matchLocation",matches.getLocation());
        intent.putExtra("matchId",matches.getMatchId());
        startActivity(intent);
        finish();
        return;
    }

    public void inapoi(View view)
    {
        Intent intent= new Intent(WaitForMatches.this, Menu.class);
        startActivity(intent);
        finish();
        return;
    }
    public void stergeIntalnire(View view)
    {
        if(b.getText()=="Creeaza o intalnire")
        {

            Intent intent= new Intent(WaitForMatches.this, CreeazaIntalnire.class);
            startActivity(intent);
            finish();
            return;

        }


        String uid=mAuth.getCurrentUser().getUid();
        DatabaseReference mDatabase =FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(uid);
        mDatabase.child("createdActivity").setValue((boolean)false);
        mDatabase.child("description").setValue(null);
        mDatabase.child("location").setValue(null);
        Context context = getApplicationContext();
        Toast.makeText(context,"Intalnirea creeata de tine a fost stearsa",Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(WaitForMatches.this, Menu.class);
        startActivity(intent);
        finish();
        return;
    }

}