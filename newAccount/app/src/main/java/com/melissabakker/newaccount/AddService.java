package com.melissabakker.newaccount;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class AddService extends AppCompatActivity {
    EditText serviceName, price;
    CheckBox fName, lName, dOB, address, licenseType, pOR, pOS, cusPhoto;
    Button createServ;
    private FirebaseFirestore dataBase = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_service);

        serviceName = (EditText) findViewById(R.id.serviceName);

        price = (EditText) findViewById(R.id.servicePrice);

        fName = findViewById(R.id.reqFirstName);
        lName = findViewById(R.id.reqLastName);
        dOB = findViewById(R.id.reqDOB);
        address = findViewById(R.id.reqAddress);
        licenseType = findViewById(R.id.reqLicenseType);
        pOR = findViewById(R.id.reqResidence);
        pOS = findViewById(R.id.reqStatus);
        cusPhoto = findViewById(R.id.reqCustomerPhoto);
        createServ = findViewById(R.id.createService);


        createServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Map<String,Object> fields = new HashMap<>();
                fields.put("First name",fName.isChecked());
                fields.put("Last Name", lName.isChecked());
                fields.put("Date of Birth",dOB.isChecked());
                fields.put("Address",address.isChecked());
                fields.put("License Type",licenseType.isChecked());
                fields.put("Customer Photo",cusPhoto.isChecked());
                fields.put("Price",price.getText().toString());
                fields.put("Proof of Residence",pOR.isChecked());
                fields.put("Proof of Status",pOS.isChecked());

                dataBase.collection("services").document(serviceName.getText().toString())
                        .set(fields)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Toast.makeText(CreateService.this,service,Toast.LENGTH_LONG).show();
                                Toast.makeText(AddService.this,"successful",Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddService.this,"error",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

    }
}
