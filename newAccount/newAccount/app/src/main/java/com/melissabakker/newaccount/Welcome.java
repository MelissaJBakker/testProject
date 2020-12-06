package com.melissabakker.newaccount;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class Welcome extends AppCompatActivity {

    String welcomeMsg;
    String credMsg;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userID, firstName, lastName;
    Button btnLogout, btnDeleteUser, btnAddService, btnEditService, btnBranchService, btnRequests, btnEditBranch, btnAddSchedule, btnUserServiceRequest;
    TextView roleDisplay, ID, cred, welcome;

    private static String currUserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        //Logout Button
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnAddService = (Button) findViewById(R.id.btnAddService);
        btnEditService = (Button) findViewById(R.id.btnEditService);
        btnDeleteUser = (Button) findViewById(R.id.btnDeleteUser);
        btnBranchService = (Button) findViewById(R.id.btnBranchService);
        btnEditBranch = (Button) findViewById(R.id.btnEditProfile);
        btnRequests = (Button) findViewById(R.id.btnRequests);
        btnAddSchedule = (Button) findViewById(R.id.AddScheduleButton);
        btnUserServiceRequest = (Button) findViewById(R.id.btnUserServRequest);

        welcome = (TextView) findViewById(R.id.Welcome);
        welcomeMsg = "Welcome to Service Novigrad!";
        welcome.setText(welcomeMsg);

        final TextView roleDisplay = (TextView) findViewById(R.id.Role);
        final TextView ID = (TextView) findViewById(R.id.ID);
        final TextView cred = (TextView) findViewById(R.id.Credentials);
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(Welcome.this, new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                roleDisplay.setText("Role: "+value.getString("role"));

                String username = value.getString("username");
                //Credentials Message
                credMsg = "Logged in as: " + username;
                cred.setText(credMsg);

                if (value.getString("role").equals("Admin")) {
                    ID.setText("Administrator");
                    btnAddService.setVisibility(View.VISIBLE);
                    btnEditService.setVisibility(View.VISIBLE);
                    btnDeleteUser.setVisibility(View.VISIBLE);
                }
                else if (value.getString("role").equals("Employee")) {
                    firstName=value.getString("fName");
                    lastName=value.getString("lName");
                    String idMessage = firstName + " "+ lastName;
                    ID.setText(idMessage);
                    btnAddSchedule.setVisibility(View.VISIBLE);
                    btnBranchService.setVisibility(View.VISIBLE);
                    btnEditBranch.setVisibility(View.VISIBLE);
                    btnRequests.setVisibility(View.VISIBLE);
                }
                else {
                firstName=value.getString("fName");
                lastName=value.getString("lName");
                String idMessage = firstName + " "+ lastName;
                ID.setText(idMessage);
                btnUserServiceRequest.setVisibility(View.VISIBLE);
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent login = new Intent(getApplicationContext(), LogIn.class);
                startActivity(login);
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deleteUser = new Intent(getApplicationContext(), DeleteUser.class);
                startActivity(deleteUser);
            }
        });

        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addService = new Intent(getApplicationContext(), AddService.class);
                startActivity(addService);
            }
        });
        btnEditService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editService = new Intent(getApplicationContext(), EditService.class);
                startActivity(editService);
            }
        });

        btnBranchService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent branchService = new Intent(getApplicationContext(), BranchServices.class);
                startActivity(branchService);
            }
        });
        btnEditBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pInfo = new Intent(getApplicationContext(), SignUp2E.class);
                startActivity(pInfo);
            }
        });
        btnRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceRequests = new Intent(getApplicationContext(), ServiceRequestList.class);
                startActivity(serviceRequests);
            }
        });

        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addSchedule = new Intent(getApplicationContext(), AddSchedule.class);
                startActivity(addSchedule);
            }});
        btnUserServiceRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**Intent userServiceRequest = new Intent(getApplicationContext(), UserServiceRequest.class); **/
                Intent userServiceRequest = new Intent(getApplicationContext(), BranchSearchBegin.class);
                startActivity(userServiceRequest);

            }
        });
    }
}