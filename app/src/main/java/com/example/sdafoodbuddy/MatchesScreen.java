package com.example.sdafoodbuddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchesScreen extends AppCompatActivity implements SelectListener{

    private RecyclerView mRecyclerView;
    private MatchesAdapter mMatchesAdapter;
    private List<Matches> matchesList;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_matches_screen);
        mAuth=FirebaseAuth.getInstance();
        mRecyclerView = findViewById(R.id.matches_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Initialize matchesList
        matchesList = new ArrayList<>();

        //Initialize MatchesAdapter
        mMatchesAdapter = new MatchesAdapter(this, matchesList,this);
        mRecyclerView.setAdapter(mMatchesAdapter);

        String uId=mAuth.getCurrentUser().getUid().toString();
        ArrayList<String> matchIDs=new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(uId).child("matches");


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    String matchID = ds.getKey().toString();
                    matchIDs.add(matchID);
                }
                    DatabaseReference mDatabase2 = FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users");
                    mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String matchId = ds.getKey();
                                if (!matchId.equals(uId)) {

                                    int matchTime = ds.child("Time in minutes").getValue(int.class);
                                    String matchGender = ds.child("gender").getValue(String.class);
                                    String matchName = ds.child("name").getValue(String.class);
                                    String matchDescription = ds.child("description").getValue(String.class);
                                    String matchLocation = ds.child("location").getValue(String.class);
                                    boolean createdActivity = ds.child("createdActivity").getValue(boolean.class);
                                    if (matchIDs.contains(ds.getKey())) {

                                        Matches match = new Matches(matchId, matchTime, matchGender, matchName, matchDescription, matchLocation, createdActivity);
                                        matchesList.add(match);

                                    }
                                }

                                if (matchesList.size() > 0) {
                                    mMatchesAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }

    @Override
    public void onItemClicked(Matches matches) {
        Intent intent= new Intent(MatchesScreen.this, ShowMatchOptions.class);
        intent.putExtra("matchId",matches.getMatchId());
        intent.putExtra("menuToken",true);
        intent.putExtra("matchTime",matches.getMatchTime());
        intent.putExtra("matchName",matches.getMatchName());
        intent.putExtra("matchLocation",matches.getLocation());
        intent.putExtra("matchDescription",matches.getDescription());
        startActivity(intent);
        finish();
        return;
    }
    public void inapoi(View view)
    {


        Intent intent = new Intent(MatchesScreen.this,Menu.class);
        startActivity(intent);
        finish();
        return;

    }
}