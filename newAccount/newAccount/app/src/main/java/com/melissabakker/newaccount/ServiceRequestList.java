package com.melissabakker.newaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ServiceRequestList extends AppCompatActivity {

    ListView requestsList;
    FirebaseFirestore db;
    FirebaseAuth fb;
    List<String> requestIDs;
    List<String> userNames;
    List<String> listLabel;
    String userID;
    Button home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request_list);

        requestsList = (ListView) findViewById(R.id.requestsList);
        db = FirebaseFirestore.getInstance();
        fb = FirebaseAuth.getInstance();
        home = (Button) findViewById(R.id.backBtn);
        requestIDs = new ArrayList<String>();
        userNames = new ArrayList<String>();
        listLabel = new ArrayList<String>();
        userID = fb.getCurrentUser().getUid();
        CollectionReference colRef = db.collection("serviceRequests");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    requestIDs.clear();
                    userNames.clear();
                    listLabel.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.getString("Status").equals("Unchecked") && document.getString("BranchID").equals(userID)) {
                            requestIDs.add(document.getId());
                            userNames.add(document.getString("User"));
                            listLabel.add(document.getId() + " request | Submitted by: " + document.getString("User"));
                        }
                    }
                    ArrayAdapter arrayAdapterR = new ArrayAdapter(ServiceRequestList.this, R.layout.service_requests, R.id.ServiceName, listLabel);
                    requestsList.setAdapter(arrayAdapterR);
                }
                else {
                    Log.d("Main", "Error getting documents: ", task.getException());
                }

            }
        });

        requestsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ApproveDeny.class);
                intent.putExtra("RequestID", requestIDs.get(position));
                intent.putExtra("User", userNames.get(position));
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Welcome.class);
                startActivity(intent);
            }
        });

    }

}