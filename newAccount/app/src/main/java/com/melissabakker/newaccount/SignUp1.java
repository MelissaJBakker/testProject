package com.melissabakker.newaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp1 extends AppCompatActivity {

    /** variables **/
    Button next;
    EditText fName, lName, pass, pass2, email, user;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Define the input fields **/

        fName = (EditText) findViewById(R.id.firstName);
        lName = (EditText) findViewById(R.id.lastName);
        pass = (EditText) findViewById(R.id.password);
        pass2 = (EditText) findViewById(R.id.passConfirm);
        email = (EditText) findViewById(R.id.email);
        user = (EditText) findViewById(R.id.username);
        next = (Button) findViewById(R.id.nextBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();


        /**Method for listening for button click **/
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** Temporary variable definition for text field inputs
                 * converted to strings, then whitespaces trimmed.**/
                final String password=pass.getText().toString();
                final String firstName=fName.getText().toString().trim();
                final String lastName=lName.getText().toString().trim();
                final String e_mail=email.getText().toString().trim();
                final String passCheck=pass2.getText().toString();
                final String username=user.getText().toString().trim();
                final String role = getIntent().getStringExtra("role");


                /**Check for empty fields and raise a Toast Notice if found **/
                if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()
                        || passCheck.isEmpty() || e_mail.isEmpty() || username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Error! One or more fields are empty.", Toast.LENGTH_SHORT).show();
                }
                /**Check for password inputs not matching, raise Toast if necessary **/
                else if(!(password.equals(passCheck))){
                    Toast.makeText(getApplicationContext(), "Error! Passwords entered don't match.", Toast.LENGTH_SHORT).show();
                }
                /**Check that password is longer than 6 characters, raise Toast if necessary **/
                else if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Error! Your password must be at least 6 characters!", Toast.LENGTH_SHORT).show();
                }

                else {

                    firebaseAuth.createUserWithEmailAndPassword(e_mail,password)
                            .addOnCompleteListener(SignUp1.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        userID=firebaseAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference=fStore.collection("users").document(userID);
                                        Map<String, Object> user= new HashMap<>();
                                        user.put("fName", firstName);
                                        user.put("lName", lastName);
                                        user.put("email", e_mail);
                                        user.put("username", username);
                                        user.put("role", role);
                                        documentReference.set(user);

                                        DocumentReference emailID = fStore.collection("emailID").document(e_mail);
                                        Map<String, Object> emailIDIndex = new HashMap<>();
                                        emailIDIndex.put("userID", userID);
                                        emailID.set(emailIDIndex);

                                        Intent intent= new Intent(getApplicationContext(), Welcome.class);
                                        getApplicationContext().startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), SignUp1.class));
                                    }
                                }});
                }
            }
        });
    }
}























































































