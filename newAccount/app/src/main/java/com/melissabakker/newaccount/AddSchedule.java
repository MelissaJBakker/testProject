package com.melissabakker.newaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddSchedule extends AppCompatActivity {

    Button btnSkip, btnSched;
    SwitchCompat mondaySwitch, tuesdaySwitch, wednesdaySwitch, thursdaySwitch, fridaySwitch, saturdaySwitch, sundaySwitch;
    EditText monStart, monEnd, tuesStart, tuesEnd, wedStart, wedEnd, thursStart, thursEnd, friStart, friEnd, satStart, satEnd, sunStart, sunEnd;
    ArrayList<SwitchCompat> switches;
    ArrayList<EditText> startsAndEnds;
    ArrayList<String> timeRanges;

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);


        /** Input definition **/
        monStart = (EditText) findViewById(R.id.MondayStart);
        monEnd = (EditText) findViewById(R.id.MondayEnd);
        tuesStart = (EditText) findViewById(R.id.TuesdayStart);
        tuesEnd = (EditText) findViewById(R.id.TuesdayEnd);
        wedStart = (EditText) findViewById(R.id.WednesdayStart);
        wedEnd = (EditText) findViewById(R.id.WednesdayStart);
        thursStart = (EditText) findViewById(R.id.ThursdayStart);
        thursEnd = (EditText) findViewById(R.id.ThursdayEnd);
        friStart = (EditText) findViewById(R.id.FridayStart);
        friEnd = (EditText) findViewById(R.id.FridayEnd);
        satStart = (EditText) findViewById(R.id.SaturdayStart);
        satEnd = (EditText) findViewById(R.id.SaturdayEnd);
        sunStart = (EditText) findViewById(R.id.SundayStart);
        sunEnd = (EditText) findViewById(R.id.SundayEnd);

        btnSkip = (Button) findViewById(R.id.btnSkip);
        btnSched = (Button) findViewById(R.id.btnSched);

        mondaySwitch = (SwitchCompat) findViewById(R.id.Monday);
        tuesdaySwitch = (SwitchCompat) findViewById(R.id.Tuesday);
        wednesdaySwitch = (SwitchCompat) findViewById(R.id.Wednesday);
        thursdaySwitch = (SwitchCompat) findViewById(R.id.Thursday);
        fridaySwitch = (SwitchCompat) findViewById(R.id.Friday);
        saturdaySwitch = (SwitchCompat) findViewById(R.id.Saturday);
        sundaySwitch = (SwitchCompat) findViewById(R.id.Sunday);


        startsAndEnds = new ArrayList<>();
        //i=0
        startsAndEnds.add(monStart); //0
        startsAndEnds.add(monEnd); //1
        //i=1
        startsAndEnds.add(tuesStart); //2
        startsAndEnds.add(tuesEnd); //3
        //i=2
        startsAndEnds.add(wedStart); //4
        startsAndEnds.add(wedEnd); //5
        //i=3
        startsAndEnds.add(thursStart); //6
        startsAndEnds.add(thursEnd); //7
        //i=4
        startsAndEnds.add(friStart); //8
        startsAndEnds.add(friEnd); //9
        //i=5
        startsAndEnds.add(satStart); //10
        startsAndEnds.add(satEnd); //11
        //i=6
        startsAndEnds.add(sunStart); //12
        startsAndEnds.add(sunEnd); //13


        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mondaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    monStart.setVisibility(View.VISIBLE);
                    monEnd.setVisibility(View.VISIBLE);
                }
            }
        });

        tuesdaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tuesStart.setVisibility(View.VISIBLE);
                    tuesEnd.setVisibility(View.VISIBLE);
                }
            }
        });

        wednesdaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wedStart.setVisibility(View.VISIBLE);
                    wedEnd.setVisibility(View.VISIBLE);
                }
            }
        });

        thursdaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    thursStart.setVisibility(View.VISIBLE);
                    thursEnd.setVisibility(View.VISIBLE);
                }
            }
        });

        fridaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    friStart.setVisibility(View.VISIBLE);
                    friEnd.setVisibility(View.VISIBLE);
                }
            }
        });

        saturdaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    satStart.setVisibility(View.VISIBLE);
                    satEnd.setVisibility(View.VISIBLE);
                }
            }
        });

        sundaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sunStart.setVisibility(View.VISIBLE);
                    sunEnd.setVisibility(View.VISIBLE);
                }
            }
        });


        /** Pressing skip will send you back the welcome page without adding
         * or editing values **/
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Welcome.class);
                startActivity(intent);
            }
        });

        btnSched.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            try {
                                                if (checkTime(startsAndEnds)) {
                                                    timeRanges = new ArrayList<String>();
                                                    for (int i = 0; i < 7; i++) {
                                                        String t1 = startsAndEnds.get(i * 2).getText().toString().trim();
                                                        String t2 = startsAndEnds.get((i * 2) + 1).getText().toString().trim();
                                                        timeRanges.add(t1 + " - " + t2);

                                                        String userID = firebaseAuth.getCurrentUser().getUid();
                                                        DocumentReference branchSchedule = fStore.collection("branchSchedule").document(userID);
                                                        Map<String, Object> schedule = new HashMap<>();
                                                        schedule.put("Monday", timeRanges.get(0));
                                                        schedule.put("Tuesday", timeRanges.get(1));
                                                        schedule.put("Wednesday", timeRanges.get(2));
                                                        schedule.put("Thursday", timeRanges.get(3));
                                                        schedule.put("Friday", timeRanges.get(4));
                                                        schedule.put("Saturday", timeRanges.get(5));
                                                        schedule.put("Sunday", timeRanges.get(6));
                                                        branchSchedule.set(schedule);
                                                    }
                                                }
                                            } catch (ParseException e) {
                                                Toast.makeText(getApplicationContext(), "Error! Invalid fields.", Toast.LENGTH_SHORT);
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    );
    }

    /**
     * Method to check that for each selected day with a start and end time,
     * neither time is empty, and that the second time is greater than the first.
     *
     * @param timesArray ArrayList<EditText> for all the time input fields
     * @return boolean for whether or not the fields check out.
     */
    public boolean checkTime(ArrayList<EditText> timesArray) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        /** Making an arrayList of switches for computations. **/
        switches = new ArrayList<>();
        switches.add(mondaySwitch);
        switches.add(tuesdaySwitch);
        switches.add(wednesdaySwitch);
        switches.add(thursdaySwitch);
        switches.add(fridaySwitch);
        switches.add(saturdaySwitch);
        switches.add(sundaySwitch);

        for (int i = 0; i < 7; i++) {
            if (switches.get(i).isChecked()) {
                String t1 = startsAndEnds.get(i * 2).getText().toString().trim();
                String t2 = startsAndEnds.get((i * 2) + 1).getText().toString().trim();
                Date time1 = sdf.parse(t1);
                Date time2 = sdf.parse(t2);

                if (t1.isEmpty() || t2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Error! Missing fields.", Toast.LENGTH_SHORT);
                    return false;
                }
                if (time1.after(time2) || time1.equals(time2)) {
                    Toast.makeText(getApplicationContext(), "Error! Invalid time interval.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }
}