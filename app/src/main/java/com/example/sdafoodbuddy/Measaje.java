package com.example.sdafoodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Measaje extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MesajeAdapter mesajeAdapter;
    private List<Msj> msjList;
    private FirebaseAuth mAuth;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_measaje);



        mAuth=FirebaseAuth.getInstance();

        mRecyclerView = findViewById(R.id.recview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        msjList = new ArrayList<>();


        mesajeAdapter = new MesajeAdapter();
        mRecyclerView.setAdapter(mesajeAdapter);


        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(currentUserId).child("mesaje");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                msjList.clear();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    String text = messageSnapshot.child("textMessage").getValue(String.class);
                    Msj msj = new Msj(text);
                    msjList.add(msj);
                }
                mesajeAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




}