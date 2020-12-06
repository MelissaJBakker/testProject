package com.melissabakker.newaccount;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class EditService extends AppCompatActivity {

    ListView servicesList;
    FirebaseFirestore db;
    CollectionReference colRef;
    List<String> serviceIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
        servicesList = (ListView) findViewById(R.id.list_view);
        serviceIDs = new ArrayList<String>();
        db = FirebaseFirestore.getInstance();



        colRef = db.collection("services");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    serviceIDs.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("Main", document.getId() + " => " + document.getData());
                        //String str = document.getString("Name");
                        //services.add(str);
                        serviceIDs.add(document.getId());

                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(EditService.this, R.layout.service_list, R.id.textViewName, serviceIDs);
                    servicesList.setAdapter(arrayAdapter);
                } else {
                    Log.d("Main", "Error getting documents: ", task.getException());
                }
            }
        });

        servicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Log.v("long clicked","pos: " + pos);
                Intent intent = new Intent(view.getContext(), EditAndDelete.class);
                intent.putExtra("ServiceID", serviceIDs.get(pos));
                startActivity(intent);
            }
        });

    }

}