package com.example.sdafoodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
private Button mRegister;
private EditText mEmail,mPassword, mName;
private RadioGroup mRadioGroup;
private FirebaseAuth mAuth;
private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registration);
         mAuth = FirebaseAuth.getInstance();
firebaseAuthListener=new FirebaseAuth.AuthStateListener() {
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null)
        {
            Intent intent= new Intent(Registration.this, ChoseCreateOrSearch.class);
            startActivity(intent);
            finish();
            return;

        }
    }
};
        mRegister = (Button) findViewById(R.id.register);
        mEmail= (EditText) findViewById(R.id.email);
        mPassword= (EditText) findViewById(R.id.password);
        mRadioGroup=(RadioGroup) findViewById(R.id.radioGroup);
        mName= (EditText) findViewById(R.id.name);
        mRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int selectId=mRadioGroup.getCheckedRadioButtonId();
                final RadioButton radioButton=(RadioButton) findViewById(selectId);

                if(radioButton.getText()==null)
                {

                    return;

                }

final String email=mEmail.getText().toString();
final String password =mPassword.getText().toString();
final String name =mName.getText().toString();
mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(!task.isSuccessful())
        {

            Toast.makeText(Registration.this,"Registration unsuccessful",Toast.LENGTH_SHORT).show();

        }else{
            String userId=mAuth.getCurrentUser().getUid();
            DatabaseReference currentUserDb= FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(userId).child("name");
            currentUserDb.setValue(name);
            DatabaseReference currentUsergDb= FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(userId).child("gender");
            currentUsergDb.setValue(radioButton.getText().toString());
            DatabaseReference currentUserCOS= FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(userId).child("createdActivity");
            currentUserCOS.setValue(false);
        }
    }
});
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

    public void inapoi(View view)
    {


        Intent intent= new Intent(Registration.this, ChooseLogReg.class);
        startActivity(intent);
        finish();
        return;

    }


}