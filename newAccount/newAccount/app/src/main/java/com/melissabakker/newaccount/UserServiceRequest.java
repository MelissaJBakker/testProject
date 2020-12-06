package com.melissabakker.newaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

public class UserServiceRequest extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth fb;
    ListView avaliableBranches, avaliableBranchServices;
    ArrayList<String> branches, services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_service_request);

        avaliableBranches = findViewById(R.id.avaliableBranches);
        avaliableBranchServices = findViewById(R.id.avaliableBranchServices);
        fb = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        branches = new ArrayList<String>();
        services = new ArrayList<String>();


        db.collection("branchServices").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentSnapshot snapshot : value){
                    branches.add(snapshot.getId());
//                    Toast.makeText(getApplicationContext(),snapshot.getData(),Toast.LENGTH_SHORT).show();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item,branches);
                adapter.notifyDataSetChanged();
                avaliableBranches.setAdapter(adapter);
            }
        });

        avaliableBranches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent selectedBranch = new Intent(getApplicationContext(), UserServiceRequest2.class);
                selectedBranch.putExtra("branchName",branches.get(i));
                startActivity(selectedBranch);
            }
        });
    }
}
