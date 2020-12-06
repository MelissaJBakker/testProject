package com.melissabakker.newaccount;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchServices extends AppCompatActivity {

    private static final String TAG = "doc";
    ListView servicesList;
    FirebaseFirestore db;
    CollectionReference colRef;
    List<String> serviceIDs;
    FirebaseAuth firebaseAuth;
    String userID;
    Button addToBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_services);
        servicesList = (ListView) findViewById(R.id.avaliable_services);
        serviceIDs = new ArrayList<String>();
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        addToBranch = (Button) findViewById(R.id.btnAddToBranch);
        final Map<String, Object> map = new HashMap<>();

        db.collection("services").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentSnapshot snapshot : value){
                    serviceIDs.add(snapshot.getId());
                    map.put(snapshot.getId(),false);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice,serviceIDs);
                adapter.notifyDataSetChanged();
                servicesList.setAdapter(adapter);
            }
        });

        servicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = ((CheckedTextView)view);
                checkedTextView.setChecked(!checkedTextView.isChecked());
                if(checkedTextView.isChecked()) {
                    map.put(serviceIDs.get(position),true);
                }
                else{
                    map.put(serviceIDs.get(position),false);
                }
            }
        });

        addToBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("branchServices").document(userID).set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Services Added/Updated",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}