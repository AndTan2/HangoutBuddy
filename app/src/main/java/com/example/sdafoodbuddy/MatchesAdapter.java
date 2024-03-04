package com.example.sdafoodbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    private List<Matches> matchesList;
    private Context context;
    private SelectListener listener;


    public MatchesAdapter(Context context, List<Matches> matchesList, SelectListener listener) {
        this.context = context;
        this.matchesList = matchesList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Matches match = matchesList.get(position);
holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        listener.onItemClicked(matchesList.get(position));

    }

});


        DatabaseReference matchDb = FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(match.getMatchId());
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String watch;
                String name = dataSnapshot.child("name").getValue(String.class);
                String location = dataSnapshot.child("location").getValue(String.class);
                int time = dataSnapshot.child("Time in minutes").getValue(int.class);

                holder.nameTextView.setText(name);
                holder.locationTextView.setText(location);
                int hours =time/60;
                int minutes =time-hours*60;
                if(minutes<10)
                 watch =String.valueOf(hours)+":0"+String.valueOf(minutes);
                else
                    watch =String.valueOf(hours)+":"+String.valueOf(minutes);
                holder.timeTextView.setText(String.valueOf(watch));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void removeAt(int position) {
        matchesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, matchesList.size());
    }


    @Override
    public int getItemCount() {
        return matchesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView locationTextView;
        public TextView timeTextView;
        public ConstraintLayout constraintLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.match_name);
            locationTextView = itemView.findViewById(R.id.match_location);
            timeTextView = itemView.findViewById(R.id.match_time);
            constraintLayout= itemView.findViewById(R.id.mainContainer);
        }
    }
}
