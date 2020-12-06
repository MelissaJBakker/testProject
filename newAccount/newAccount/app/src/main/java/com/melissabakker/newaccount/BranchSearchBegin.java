package com.melissabakker.newaccount;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class BranchSearchBegin extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button select;
    private AutoCompleteTextView auto;
    /** private EditText time; **/
    /**private TextView shedInputMark **/
    ;
    private String searchType, param;
    private ArrayList<String> autoFillServices, autoFillAddresses, days;
    private ArrayList<Branch> branches, branchesDisplayed;
    private FirebaseFirestore fStore;
    private ListView branchList;
    private CollectionReference branchRef;
    private BranchAdapter branchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_search_begin);

        fStore = FirebaseFirestore.getInstance();

        autoFillServices = new ArrayList<String>();
        autoFillAddresses = new ArrayList<String>();
        days = new ArrayList<String>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

        fStore.collection("services").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot snapshot : value) {
                    autoFillServices.add(snapshot.getId());
                }
            }
        });

        fStore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot snapshot : value) {
                    if (snapshot.getString("role").equals("Employee")) {
                        autoFillAddresses.add(snapshot.getString("Address"));
                    }
                }
            }
        });
        /**
         shedInputMark = (TextView) findViewById(R.id.textView11); **/

        auto = (AutoCompleteTextView) findViewById(R.id.SearchSelectionInput);
        /**
         time = (EditText) findViewById(R.id.timeInput); **/

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                auto.setText("");

                /** if checked button is address radio **/
                if (checkedId == 1000397) {

                    /**
                     shedInputMark.setVisibility(View.INVISIBLE);
                     time.setVisibility(View.INVISIBLE); **/
                    auto.setHint("Enter an address.");
                    searchType = "Address";
                    auto.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, autoFillAddresses));

                }
                /** checked button is service radio **/
                else if (checkedId == 1000348) {

                    /**
                     shedInputMark.setVisibility(View.INVISIBLE);
                     time.setVisibility(View.INVISIBLE); **/
                    auto.setHint("Enter a service.");
                    searchType = "Service";
                    auto.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, autoFillServices));

                }
                /** checked button is schedule radio **/
                else {

                    /**
                     shedInputMark.setVisibility(View.VISIBLE);
                     time.setVisibility(View.VISIBLE); **/
                    auto.setHint("Enter a day.");
                    searchType = "Schedule";
                    auto.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, days));

                }

                auto.setThreshold(1);
                auto.setVisibility(View.VISIBLE);
            }
        });

        select = (Button) findViewById(R.id.SubmitSearch);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                branchList = (ListView) findViewById(R.id.branchList);
                branches = new ArrayList<Branch>();
                branchesDisplayed = new ArrayList<Branch>();
                param = auto.getText().toString().trim();

                branchRef = fStore.collection("users");
                branchRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            branches.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("role").equals("Employee")) {
                                    String e = document.getString("email");
                                    String a = document.getString("address");
                                    String bn = document.getString("branchname");
                                    branches.add(new Branch(e, a, bn));
                                    }
                                }
                            }
                        }
                    });

                if (searchType.equals("Address")) {
                    for (int i = 0; i < branches.size(); i++) {
                        if (branches.get(i).getAddress().equals(param)) {
                            branchesDisplayed.add(branches.get(i));
                        }
                    }
                    branchAdapter = new BranchAdapter(BranchSearchBegin.this, branchesDisplayed, searchType);
                }
                else if (searchType.equals("Service")) {
                    for (int i=0; i < branches.size(); i++) {
                        final DocumentReference documentReference = fStore.collection("branchServices").document(branches.get(i).getID());
                        final int finalI = i;
                        documentReference.addSnapshotListener(BranchSearchBegin.this, new EventListener<DocumentSnapshot>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value.getBoolean(param).equals(true)) {
                                    branchesDisplayed.add(branches.get(finalI));
                                }
                            }
                        });
                    }
                    branchAdapter = new BranchAdapter(BranchSearchBegin.this, branchesDisplayed, searchType);
                }
                /** searchType is Schedule **/
                else {
                    for (int i=0; i < branches.size(); i++) {
                        final DocumentReference documentReference = fStore.collection("branchSchedule").document(branches.get(i).getID());
                        final int finalI = i;
                        documentReference.addSnapshotListener(BranchSearchBegin.this, new EventListener<DocumentSnapshot>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (!value.getString(param).equals("Closed")) {
                                    branchesDisplayed.add(branches.get(finalI));
                                }
                            }
                        });
                    }
                    branchAdapter = new BranchAdapter(BranchSearchBegin.this, branchesDisplayed, searchType, param);
                }
                branchList.setAdapter(branchAdapter);
                branchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        Log.v("long clicked","pos: " + pos);
                        Intent intent = new Intent(view.getContext(), BranchPublic.class);
                        intent.putExtra("Branch", branchesDisplayed.get(pos).getID());
                        startActivity(intent);
                    }
                });
            }
        });
    }
}