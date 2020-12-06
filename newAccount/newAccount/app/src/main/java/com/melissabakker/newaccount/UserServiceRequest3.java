package com.melissabakker.newaccount;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserServiceRequest3 extends AppCompatActivity {

    String selectedService;
    FirebaseFirestore db;
    Map<String,Object> map;
    EditText userFirstName,userLastName, userDOB, userAddress;
    Spinner licenseType;
    String[] dLType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_service_request_info);

        Bundle extras = getIntent().getExtras();
        selectedService = extras.getString("selectedService");
        db = FirebaseFirestore.getInstance();
        map = new HashMap<>();
        dLType = new String[]{"","G1","G2","G"};
        userFirstName = findViewById(R.id.userApplicationFName);
        userLastName = findViewById(R.id.userApplicationLName);
        userDOB = findViewById(R.id.userApplicationDOB);
        userAddress = findViewById(R.id.userApplicationAddress);
        licenseType = findViewById(R.id.userApplicationLicenseType);


        db.collection("services").document(selectedService).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                map = value.getData();
                for(Map.Entry<String,Object> entry : map.entrySet()){
                    if(!entry.getKey().equals("Price")){
                        boolean required = ((Boolean) entry.getValue()).booleanValue();
                        if(entry.getKey().equals("Address") && required){
                            userAddress.setVisibility(View.VISIBLE);
                        }
                        else if(entry.getKey().equals("Date of Birth") && required){
                            userDOB.setVisibility(View.VISIBLE);
                        }
                        else if(entry.getKey().equals("First name") && required){
                            userFirstName.setVisibility(View.VISIBLE);
                        }
                        else if(entry.getKey().equals("Last Name") && required){
                            userLastName.setVisibility(View.VISIBLE);
                        }
                        else if(entry.getKey().equals("License Type") && required){
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,dLType);
                            adapter.notifyDataSetChanged();
                            licenseType.setAdapter(adapter);
                            licenseType.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }
}
