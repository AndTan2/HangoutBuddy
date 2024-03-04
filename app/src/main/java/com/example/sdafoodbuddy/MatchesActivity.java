package com.example.sdafoodbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity implements SelectListener {

    private RecyclerView mRecyclerView;
    private MatchesAdapter mMatchesAdapter;
    private List<Matches> matchesList;
    private String currentUserId;
    private int currentUserTime;
    private String currentUserGender;
    private String currentUserMatchGender;
    private FirebaseAuth mAuth;
    private TextView min;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_matches);






mAuth=FirebaseAuth.getInstance();

        mRecyclerView = findViewById(R.id.matches_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        matchesList = new ArrayList<>();


        mMatchesAdapter = new MatchesAdapter(this, matchesList,this);
        mRecyclerView.setAdapter(mMatchesAdapter);


        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference currentUserDb = FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(currentUserId);
        currentUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUserTime = dataSnapshot.child("Time in minutes").getValue(Integer.class);
                currentUserGender = dataSnapshot.child("gender").getValue(String.class);
                currentUserMatchGender = dataSnapshot.child("Gender Match").getValue(String.class);
                ArrayList<String> matchIDs=new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.child("matches").getChildren()) {

                    String matchID=ds.getKey().toString();
                    matchIDs.add(matchID);
                }

                Log.d("verifica","lista match: "+matchesList.size());

                DatabaseReference matchesDb = FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users");





                matchesDb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot matchSnapshot : dataSnapshot.getChildren()) {
                            String matchId = matchSnapshot.getKey();

                            if (!matchId.equals(currentUserId)) {
                                int matchTime = matchSnapshot.child("Time in minutes").getValue(Integer.class);
                                String matchGender = matchSnapshot.child("Gender Match").getValue(String.class);
                                String gender = matchSnapshot.child("gender").getValue(String.class);
                                String matchName=matchSnapshot.child("name").getValue(String.class);
                                String matchDescription=matchSnapshot.child("description").getValue(String.class);
                                String matchLocation=matchSnapshot.child("location").getValue(String.class);



                                boolean createdActivity=matchSnapshot.child("createdActivity").getValue(boolean.class);

                                Log.d("verifica5","lista match: "+currentUserGender+" ; "+matchGender);
                                if(!matchIDs.contains(matchSnapshot.getKey().toString()))
                                if (createdActivity && Math.abs(matchTime - currentUserTime) <= 10000 && ((currentUserMatchGender.equals("Oricare")&&(matchGender.equals(currentUserGender)||matchGender.equals("Oricare"))) || (currentUserGender.equals(matchGender)&&currentUserMatchGender.equals(gender))||(currentUserMatchGender.equals(gender)&&matchGender.equals("Oricare")))) {
                                    Matches match = new Matches(matchId, matchTime, matchGender,matchName,matchDescription,matchLocation,createdActivity);
                                    matchesList.add(match);
                                    Log.d("verifica5","lista match: "+matchesList.size());
                                }
                            }


                            if (matchesList.size() > 0) {
                                Log.d("verifica3","lista match: "+matchesList.size());
                                mMatchesAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void menu(View view)
    {

        Intent intent = new Intent(MatchesActivity.this,Menu.class);
        startActivity(intent);
        finish();
        return;

    }

    @Override
    public void onItemClicked(Matches matches) {
        Intent intent= new Intent(MatchesActivity.this, AcceptMatch.class);
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


        Intent intent= new Intent(MatchesActivity.this, TimePick.class);
        startActivity(intent);
        finish();
        return;

    }

}


