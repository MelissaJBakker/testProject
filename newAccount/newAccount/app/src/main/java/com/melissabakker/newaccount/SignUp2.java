package com.melissabakker.newaccount;

/**This page will eventually be linked to by SignUp1 to collect
 * additional user details that can be stored in a database.
 * Raise flag if any of the values can't be added to the database and send back to sign up 1
 * put restrictions in database for not being default values
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.annotation.RegEx;

public class SignUp2 extends AppCompatActivity {

    /**
     * variables
     **/

    Button confirm;
    EditText phone, birthday, streetAddress, post, municipality;
    private FirebaseAuth firebaseAuth;
    String userID;

    public SignUp2(Context context) {
    }

    public SignUp2() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        /** variables **/
        confirm = (Button) findViewById(R.id.confirmBtn);
        phone = (EditText) findViewById(R.id.phoneNumber);
        birthday = (EditText) findViewById(R.id.dob);
        streetAddress = (EditText) findViewById(R.id.streetAddress);
        post = (EditText) findViewById(R.id.postCode);
        municipality = (EditText) findViewById(R.id.municipality);


        /**Method for listening for button click **/
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** Temporary variable definition for text field inputs
                 * converted to strings, then whitespaces trimmed.**/
                final String phoneNumber = phone.getText().toString().trim();
                final String birth = birthday.getText().toString().trim();
                final String address = streetAddress.getText().toString().trim();
                final String postalCode = post.getText().toString().trim();
                final String area = municipality.getText().toString().trim();

                addInfo(phoneNumber, birth, address, postalCode, area);

            }
        });
    }

    /**
     * Method that verifies the validity of the inputted values, checks the passwords against each other,
     * and returns true if all checks pass.
     *
     * @param pn  phone number
     * @param dob birth date
     * @param ad  address
     * @param pc  postal code
     * @param a   area
     * @return string of error indication or success.
     */
    public String checkFields(String pn, String dob, String ad, String pc, String a) {

        /**Check for empty fields and raise a Toast Notice if found. **/
        if (pn.isEmpty() || dob.isEmpty() || ad.isEmpty()
                || pc.isEmpty() || a.isEmpty()) {
            return "Error! One or more fields are empty.";
        }
        /**Check that address is valid, raise Toast if necessary. Must not include any symbols. **/
        else if (!ad.matches("[a-zA-Z0-9 ]+")) {
            return "Error! Invalid Address!";

        }
        /**Check that phone number is valid, raise Toast if necessary. Must be 10 digits. **/
        else if (!pn.matches("\\d+") || pn.length() != 10) {
            return "Error! Invalid Phone number, use only 10 digits.";
        } else {
            return "success";
        }

    }


    /**
     * Method that adds the additional profile information to the database and returns true if successful.
     *
     * @param pn  phone number
     * @param dob birth date
     * @param ad  address
     * @param pc  postal code
     * @param a   area
     * @return true when info is added to document.
     */
    private void addInfo(final String pn, final String dob, final String ad, final String pc,
                         final String a) {

        firebaseAuth = FirebaseAuth.getInstance();
        String s = checkFields(pn, dob, ad, pc, a);

        /** Run checkFields on the input to ensure all the values pass checks, then authenticate and
         * create necessary databases for users.
         */
        if (s.equals("success")) {

            userID = firebaseAuth.getCurrentUser().getUid();
            FirebaseFirestore d = FirebaseFirestore.getInstance();

            /**Existing Firestore document for user information**/
            DocumentReference userRef = d.collection("users").document(userID);
            userRef.update("pnumber", pn);
            userRef.update("birthday", dob);
            userRef.update("address", ad);
            userRef.update("postalcode", pc);
            userRef.update("municipality", a);


            Intent intent = new Intent(getApplicationContext(), Welcome.class);
            getApplicationContext().startActivity(intent);

        } else {

            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), SignUp2.class);
            getApplicationContext().startActivity(intent);
        }

    }

}

