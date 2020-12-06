package com.melissabakker.newaccount;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditAndDelete extends AppCompatActivity {

    EditText editTextPrice;
    SwitchCompat firstnameSwitch, lastnameSwitch, addressSwitch, dobSwitch, licenseSwitch, photoSwitch, residenceSwitch, statusSwitch;
    Button buttonEdit, buttonDelete;
    String serviceID;
    FirebaseFirestore db;
    DocumentReference docRef;
    double price;
    boolean first, last, address, dob, license, photo, residence, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_delete);

        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        firstnameSwitch = (SwitchCompat) findViewById(R.id.Firstname_Switch);
        lastnameSwitch = (SwitchCompat) findViewById(R.id.Lastname_switch);
        addressSwitch = (SwitchCompat) findViewById(R.id.Address_Switch);
        dobSwitch = (SwitchCompat) findViewById(R.id.DOB_Switch);
        licenseSwitch = (SwitchCompat) findViewById(R.id.License_Switch);
        photoSwitch = (SwitchCompat) findViewById(R.id.Photo_Switch);
        residenceSwitch = (SwitchCompat) findViewById(R.id.Residence_Switch);
        statusSwitch = (SwitchCompat) findViewById(R.id.Status_Switch);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonDelete = (Button) findViewById(R.id.buttonDeleteService);
        serviceID = getIntent().getStringExtra("ServiceID");
        db = FirebaseFirestore.getInstance();
        docRef = db.collection("services").document(serviceID);

        //Open Document and pull data
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        first = document.getBoolean("First name");
                        last = document.getBoolean("Last Name");
                        address = document.getBoolean("Address");
                        dob = document.getBoolean("Date of Birth");
                        license = document.getBoolean("License Type");
                        photo = document.getBoolean("Customer Photo");
                        residence = document.getBoolean("Proof of Residence");
                        status = document.getBoolean("Proof of Status");
                        firstnameSwitch.setChecked(first);
                        lastnameSwitch.setChecked(last);
                        addressSwitch.setChecked(address);
                        dobSwitch.setChecked(dob);
                        licenseSwitch.setChecked(license);
                        photoSwitch.setChecked(photo);
                        residenceSwitch.setChecked(residence);
                        statusSwitch.setChecked(status);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteService();
                Intent intent = new Intent(v.getContext(), EditService.class);
                startActivity(intent);
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = editTextPrice.getText().toString().trim();
                first = firstnameSwitch.isChecked();
                last = lastnameSwitch.isChecked();
                address = addressSwitch.isChecked();
                dob = dobSwitch.isChecked();
                license = licenseSwitch.isChecked();
                photo = photoSwitch.isChecked();
                residence = residenceSwitch.isChecked();
                status = statusSwitch.isChecked();

                if(p.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Error! Enter a valid Name and Price.", Toast.LENGTH_SHORT).show();
                }
                else{
                    price = Double.parseDouble(p);
                    docRef.update("Price", price).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("", "DocumentSnapshot successfully updated!"); }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("", "Error updating document", e);
                        }
                    });

                    docRef.update("First Name", first).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("", "DocumentSnapshot successfully updated!"); }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("", "Error updating document", e);
                        }
                    });

                    docRef.update("Last Name", last).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("", "DocumentSnapshot successfully updated!"); }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("", "Error updating document", e);
                        }
                    });

                    docRef.update("Address", address).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("", "DocumentSnapshot successfully updated!"); }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("", "Error updating document", e);
                        }
                    });

                    docRef.update("Date of Birth", dob).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("", "DocumentSnapshot successfully updated!"); }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("", "Error updating document", e);
                        }
                    });

                    docRef.update("License Type", license).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("", "DocumentSnapshot successfully updated!"); }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("", "Error updating document", e);
                        }
                    });

                    docRef.update("Customer Photo", photo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("", "DocumentSnapshot successfully updated!"); }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("", "Error updating document", e);
                        }
                    });

                    docRef.update("Proof of Residence", residence).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("", "DocumentSnapshot successfully updated!"); }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("", "Error updating document", e);
                        }
                    });

                    docRef.update("Proof of Status", status).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("", "DocumentSnapshot successfully updated!"); }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("", "Error updating document", e);
                        }
                    });

                    Intent intent = new Intent(v.getContext(), EditService.class);
                    startActivity(intent);
                }

            }
        });
    }

    public void deleteService(){
        FirebaseFirestore d = FirebaseFirestore.getInstance();
        DocumentReference doc = d.collection("services").document(serviceID);
        doc.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Service Deleted.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Deletion Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}