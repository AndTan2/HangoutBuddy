package com.example.sdafoodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Menu extends AppCompatActivity {
FirebaseAuth mAuth;
private boolean userCreatedActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);



        mAuth= FirebaseAuth.getInstance();



    }


    public void logoutUser(View view)
    {

        mAuth.signOut();
        Intent intent = new Intent(Menu.this,ChooseLogReg.class);
        startActivity(intent);
        finish();
        return;

    }
    public void goToMatches(View view)
    {





        Intent intent = new Intent(Menu.this,MatchesScreen.class);
        intent.putExtra("menuToken",true);
        startActivity(intent);
        finish();
        return;

    }

    public void goToCCOS(View view)
    {


        Intent intent = new Intent(Menu.this,ChoseCreateOrSearch.class);
        intent.putExtra("menuToken",true);
        startActivity(intent);
        finish();
        return;

    }
    public void goToIntalnirileMele(View view)
    {

        Intent intent = new Intent(Menu.this,WaitForMatches.class);
        intent.putExtra("menuToken",true);
        startActivity(intent);
        finish();
        return;


    }

}