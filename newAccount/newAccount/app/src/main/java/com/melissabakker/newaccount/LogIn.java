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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogIn extends AppCompatActivity {

    String em, pw;
    EditText txtEmail, txtPassword;
    Button btnDisplay1;
    Button btnDisplay2;
    String userID;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        txtEmail = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById(R.id.password);

        btnDisplay1 = findViewById(R.id.signin);
        btnDisplay2= findViewById(R.id.createaccount);

        /** Login button **/
        btnDisplay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Get sign in information from the text inputs **/
                String em = txtEmail.getText().toString().trim();
                String pw = txtPassword.getText().toString().trim();

                /** Attempt sign in **/
                firebaseSignIn(em, pw);
            }});

        btnDisplay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(), SignUpMain.class);
                view.getContext().startActivity(intent);
            }
        });

    }


    /** Sign in with Firebase Authorization method: active user information is stored in a Firestore database.
     * When a user is deleted, the document containing their information is deleted, and their id is added to a database of accounts
     * to be cleared from authorization records, but their sign in data remains valid in the firebase system until an admin deletes it
     * manually from the console. To stop users from signing into an account that has been deleted, check before continuing to
     * Welcome services that there is a document storing their info. If they are able to sign in, but there is no document with their user ID,
     * the system will alert them to make a new account with a different email or wait until the admin fully deletes their account
     * records and they will be automatically logged out.
     * @param e email
     * @param p password
     * @return String value for whether the sign in occurs (Junit purposes)
     */
    private String firebaseSignIn(String e, String p) {
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        final String[] returnStatement = new String[1];

        /**Check that neither field is empty **/
        if (e.isEmpty() || p.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Error! One or more fields are empty.", Toast.LENGTH_SHORT).show();
            returnStatement[0] = "Empty fields. Unsuccessful login.";
        } else {
            /**Check for valid admin sign in credentials **/
            if ((e.equals("Admin")) && (p.equals("Admin"))) {
                e = "admin@admin.ca";
                p = "Password";
            }
            firebaseAuth.signInWithEmailAndPassword(e, p)
                    .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                /** If sign in successful, check if "users" collection has a document with their userID
                                 * If the doc exists, proceed to Welcome for options.
                                 * If not, the user was deleted, and a notice is raised. **/
                                userID = firebaseAuth.getCurrentUser().getUid();
                                fStore.collection("users").document(userID).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.getResult().exists()) {
                                                    startActivity(new Intent(getApplicationContext(), Welcome.class));
                                                    returnStatement[0] = "Successful login.";
                                                } else {
                                                    FirebaseAuth.getInstance().signOut();
                                                    Toast.makeText(LogIn.this, "Account in queue to be deleted- unavailable.", Toast.LENGTH_LONG).show();
                                                    returnStatement[0] = "Account in deletion queue. Unsuccessful login";
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(LogIn.this,
                                        "Login Failure! Please check your credentials.", Toast.LENGTH_SHORT).show();
                                returnStatement[0] = "Unsuccessful login.";
                            }
                        }
                    });
        }
        return returnStatement[0];
    }
}