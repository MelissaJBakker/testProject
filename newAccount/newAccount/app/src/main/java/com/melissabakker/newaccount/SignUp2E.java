package com.melissabakker.newaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp2E extends AppCompatActivity {

    Button confirm;
    EditText name, phone, birthday, streetAddress, post;
    private FirebaseAuth firebaseAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2_e);

        /** variables **/
        confirm = (Button) findViewById(R.id.confirmBtn);
        name = (EditText) findViewById(R.id.branchName);
        phone = (EditText) findViewById(R.id.phoneNumber);
        birthday = (EditText) findViewById(R.id.dob);
        streetAddress = (EditText) findViewById(R.id.streetAddress);
        post = (EditText) findViewById(R.id.postCode);



        /**Method for listening for button click **/
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** Temporary variable definition for text field inputs
                 * converted to strings, then whitespaces trimmed.**/
                final String branchName = name.getText().toString().trim();
                final String phoneNumber = phone.getText().toString().trim();
                final String birth = birthday.getText().toString().trim();
                final String address = streetAddress.getText().toString().trim();
                final String postalCode = post.getText().toString().trim();

                addInfo(branchName, phoneNumber, birth, address, postalCode);

            }
        });

    }

    /** Method that verifies the validity of the inputted values, checks the passwords against each other,
     * and returns true if all checks pass.
     *
     * @param pn phone number
     * @param dob birth date
     * @param ad address
     * @param pc postal code
     * @param bn branch name
     * @return true when the checks pass.
     */
    private boolean checkFields(String bn, String pn, String dob, String ad, String pc) {

        /**Check for empty fields and raise a Toast Notice if found. **/
        if (bn.isEmpty() || pn.isEmpty() || dob.isEmpty() || ad.isEmpty()
                || pc.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Error! One or more fields are empty.", Toast.LENGTH_SHORT).show();
        }
        /**Check that address is valid, raise Toast if necessary. Must not include any symbols. **/
        else if (!ad.matches("[a-zA-Z0-9 ]+")){
            Toast.makeText(getApplicationContext(), "Error! Invalid Address!", Toast.LENGTH_SHORT).show();
        }
        /**Check that phone number is valid, raise Toast if necessary. Must be 10 digits. **/
        else if (!pn.matches("\\d+") || pn.length() != 10){
            Toast.makeText(getApplicationContext(), "Error! Invalid Phone number, use only 10 digits.", Toast.LENGTH_SHORT).show();
        }
        else {
            return true;
        }

        return false;
    }

    /** Method that adds the additional profile information to the database and returns true if successful.
     *
     * @param pn phone number
     * @param dob birth date
     * @param ad address
     * @param pc postal code
     * @param bn branch name
     * @return true when info is added to document.
     */
    private void addInfo(final String bn, final String pn, final String dob, final String ad,
                         final String pc) {

        firebaseAuth=FirebaseAuth.getInstance();

        /** Run checkFields on the input to ensure all the values pass checks, then authenticate and
         * create necessary databases for users.
         */
        if (checkFields(bn, pn, dob, ad, pc)) {

            userID = firebaseAuth.getCurrentUser().getUid();
            FirebaseFirestore d = FirebaseFirestore.getInstance();

            /**Existing Firestore document for user information**/
            DocumentReference userRef = d.collection("users").document(userID);
            userRef.update("branchname", bn);
            userRef.update("pnumber", pn);
            userRef.update("birthday", dob);
            userRef.update("postalcode", pc);
            userRef.update("address", ad);

            Intent intent = new Intent(getApplicationContext(), Welcome.class);
            getApplicationContext().startActivity(intent);

        }

        else {
            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), SignUp2E.class);
            getApplicationContext().startActivity(intent);
        }
    }

}
