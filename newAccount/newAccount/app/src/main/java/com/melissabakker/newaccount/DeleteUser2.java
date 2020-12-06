package com.melissabakker.newaccount;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DeleteUser2 extends AppCompatActivity {

    TextView user, email, role, id;
    Button delBtn;
    FirebaseFirestore fStore;
    String userID, userRole, userEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user2);
        fStore = FirebaseFirestore.getInstance();

        user = (TextView) findViewById(R.id.usernameHeader);
        email = (TextView) findViewById(R.id.userEmailDisplay);
        role = (TextView) findViewById(R.id.userRoleDisplay);
        id = (TextView) findViewById(R.id.userIdDisplay);
        delBtn = (Button) findViewById(R.id.button);

        user.setText(getIntent().getStringExtra("userName"));
        userEmail = getIntent().getStringExtra("userEmail");
        email.setText(userEmail);
        userRole = getIntent().getStringExtra("userRole");
        role.setText(userRole);

        DocumentReference documentReference = fStore.collection("emailID").document(email.getText().toString());
        documentReference.addSnapshotListener(DeleteUser2.this, new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userID = value.getString("userID");
                id.setText(userID);
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
                Intent intent = new Intent(view.getContext(), Welcome.class);
                startActivity(intent);
            }
        });
    }

    public void deleteUser() {
        DocumentReference doc = fStore.collection("users").document(userID);
        doc.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User Deleted.", Toast.LENGTH_SHORT).show();
                    DocumentReference doc2 = fStore.collection("emailID").document(userEmail);
                    doc2.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "emailID Deleted.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "emailID Deletion Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    if (userRole.equals("Employee")) {
                        DocumentReference doc3 = fStore.collection("branchServices").document(userID);
                        doc3.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "branch Services Deleted.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "branch Services Deletion Failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        DocumentReference doc4 = fStore.collection("branchSchedule").document(userID);
                        doc4.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "branch Schedule Deleted.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "branch Schedule Deletion Failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                    else{
                    Toast.makeText(getApplicationContext(), "Deletion Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}