package com.example.sdafoodbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class TimePick extends AppCompatActivity {
    private TimePicker mTimepicker;
    private Button mConfirm;
    private RadioGroup mRadioGroup;
    private SeekBar seekBar;
    TextView min;
    public int timeRange;
    private String genderM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_time_pick);

        final Calendar calendar = Calendar.getInstance();


        mTimepicker=(TimePicker) findViewById(R.id.simpleTimePicker);
        mTimepicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        mTimepicker.setCurrentMinute(calendar.get(Calendar.MINUTE));

        mConfirm=(Button) findViewById(R.id.confirm);
        mRadioGroup=(RadioGroup) findViewById(R.id.radioGroup);





        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int timeInMinutes= 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    timeInMinutes = mTimepicker.getHour()*60 + mTimepicker.getMinute();
                }

                int selectId=mRadioGroup.getCheckedRadioButtonId();
                final RadioButton radioButton=(RadioButton) findViewById(selectId);

                if(radioButton.getText()==null)
                {
                    return;


                }

                String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference currentUserDb= FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(userId).child("Time in minutes");
                currentUserDb.setValue(timeInMinutes);
                DatabaseReference currentUserGDb= FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(userId).child("Gender Match");
                currentUserGDb.setValue(radioButton.getText());
                DatabaseReference mDatabase =FirebaseDatabase.getInstance("https://sdafoodbuddy-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users").child(userId);
                mDatabase.child("createdActivity").setValue((boolean)false);
                mDatabase.child("description").setValue(null);
                mDatabase.child("location").setValue(null);
                Context context = getApplicationContext();;
                Toast.makeText(context,"Intalnirea creeata de tine a fost stearsa",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(TimePick.this, MatchesActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

    public void inapoi(View view)
    {


        Intent intent= new Intent(TimePick.this, ChoseCreateOrSearch.class);
        startActivity(intent);
        finish();
        return;

    }


}