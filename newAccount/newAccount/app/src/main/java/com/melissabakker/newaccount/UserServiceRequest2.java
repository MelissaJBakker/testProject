package com.melissabakker.newaccount;

import android.content.Intent;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserServiceRequest2 extends AppCompatActivity {

    FirebaseFirestore db;
    String selectedBranch;
    ArrayList<String> services;
    ListView avaliableBranchServices;
    Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_branch);

        Bundle extras = getIntent().getExtras();
        selectedBranch = extras.getString("branchName");
        db = FirebaseFirestore.getInstance();
        map = new HashMap<>();
        services = new ArrayList<>();
        avaliableBranchServices = findViewById(R.id.avaliableBranchServices);

        db.collection("branchServices").document(selectedBranch).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                map = value.getData();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    boolean avaliable = ((Boolean) entry.getValue()).booleanValue();
                    if(avaliable){
                        services.add(entry.getKey());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_selectable_list_item,services);
                adapter.notifyDataSetChanged();
                avaliableBranchServices.setAdapter(adapter);
            }
        });

        avaliableBranchServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent selectedService = new Intent(getApplicationContext(),UserServiceRequest3.class);
                selectedService.putExtra("selectedService",services.get(i));
                startActivity(selectedService);
            }
        });
    }
}
