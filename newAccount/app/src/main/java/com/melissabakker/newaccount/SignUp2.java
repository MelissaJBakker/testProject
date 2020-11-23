package com.melissabakker.newaccount;

/**This page will eventually be linked to by SignUp1 to collect
 * additional user details that can be stored in a database.
 * Raise flag if any of the values can't be added to the database and send back to sign up 1
 * put restrictions in database for not being default values
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.ActionCodeSettings;

public class SignUp2 extends AppCompatActivity {

    /**
     * variables
     **/

    Button confirm;
    EditText phone, birthday, streetAddress, streetAddress2, post, municipality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        /** variables **/
        confirm = (Button) findViewById(R.id.confirmBtn);
        phone = (EditText) findViewById(R.id.phoneNumber);
        //Would like to add some sliding wheels for birthday, and age verification techniques
        birthday = (EditText) findViewById(R.id.dob);
        streetAddress = (EditText) findViewById(R.id.streetAddress);
        streetAddress2 = (EditText) findViewById(R.id.streetAddress2);
        post = (EditText) findViewById(R.id.postCode);
        municipality = (EditText) findViewById(R.id.municipality);


        /**Method for listening for button click **/
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** Temporary variable definition for text field inputs
                 * converted to strings, then whitespaces trimmed.**/
                String phoneNumber = phone.getText().toString().trim();
                String birth = birthday.getText().toString().trim();
                String addressFull = streetAddress.getText().toString().trim() + ", " + streetAddress2.getText().toString().trim();
                String postalCode = post.getText().toString().trim();
                String area = municipality.getText().toString().trim();

                Intent intent= new Intent(v.getContext(), Welcome.class);
                v.getContext().startActivity(intent);

            }
        });
    }
}