package com.melissabakker.newaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ApproveDeny extends AppCompatActivity {

    Button approveBtn, denyBtn, backBtn, residenceBtn, statusBtn, photoBtn;
    String serviceName, userName, name, request;
    TextView serviceLabel, userLabel, firstLabel, lastLabel, birthLabel, addressLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_deny);

        residenceBtn = (Button) findViewById(R.id.residenceBtn);
        statusBtn = (Button) findViewById(R.id.statusBtn);
        photoBtn = (Button) findViewById(R.id.photoBtn);
        approveBtn = (Button) findViewById(R.id.AprroveBtn);
        denyBtn = (Button) findViewById(R.id.DenyBtn);
        backBtn = (Button) findViewById(R.id.BackBtn);
        serviceLabel = (TextView) findViewById(R.id.serviceNameLabel);
        userLabel = (TextView) findViewById(R.id.userNameLabel);
        firstLabel = (TextView) findViewById(R.id.firstNameLabel);
        lastLabel = (TextView) findViewById(R.id.lastNameLabel);
        birthLabel = (TextView) findViewById(R.id.birthDateLabel);
        addressLabel = (TextView) findViewById(R.id.addressView);
        serviceName = getIntent().getStringExtra("RequestID");
        userName =  getIntent().getStringExtra("User");
        name = "Submitted By: " + userName;
        request = serviceName + " Request";
        serviceLabel.setText(request);
        userLabel.setText(name);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(intent);
            }
        });

        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveService(serviceName);
                Intent intent = new Intent(getApplicationContext(), ServiceRequestList.class);
                startActivity(intent);
            }
        });

        denyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denyService(serviceName);
                Intent intent = new Intent(getApplicationContext(), ServiceRequestList.class);
                startActivity(intent);
            }
        });

    }

    public void approveService(String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference requestRef = db.collection("serviceRequests").document(id);
        requestRef.update("Status", "Approved");
    }

    public void denyService(String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference requestRef = db.collection("serviceRequests").document(id);
        requestRef.update("Status", "Denied");
    }

}