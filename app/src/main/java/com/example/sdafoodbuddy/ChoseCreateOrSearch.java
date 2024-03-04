package com.example.sdafoodbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

public class ChoseCreateOrSearch extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chose_create_or_search);
        mAuth =FirebaseAuth.getInstance();
    }

    public void creeaza(View view) {

        Intent intent= new Intent(ChoseCreateOrSearch.this, CreeazaIntalnire.class);
        startActivity(intent);
        finish();
        return;

    }

    public void cauta(View view) {
        Intent intent= new Intent(ChoseCreateOrSearch.this, TimePick.class);
        startActivity(intent);
        finish();
        return;
    }



    public void inapoi(View view)
    {


        Intent intent = new Intent(ChoseCreateOrSearch.this,Menu.class);
        startActivity(intent);
        finish();
        return;

    }

}