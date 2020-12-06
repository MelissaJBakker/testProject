package com.melissabakker.newaccount;

/**This class is the page one is directed to after selecting Make new account on
 * the loading screen. If log in is pressed, user will be redirected back, otherwise
 * user will register for a new account as either an employee or user, which are
 * functionally equivalent roles at present but will have other attributes added later.
 * The role boolean variable is automatically assigned and will be sent to SignUp1 for use.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpMain extends AppCompatActivity {

    /**
     * Variables
     **/
    Button userBtn, empBtn, logInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_main);

        userBtn = (Button) findViewById(R.id.userSignUpBtn);
        empBtn = (Button) findViewById(R.id.empSignUpBtn);
        logInBtn = (Button) findViewById(R.id.logInBtn);

        /**Method for listening for button click **/
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String role = "User";
                Intent intent = new Intent(v.getContext(), SignUp1.class);
                intent.putExtra("role", role);
                v.getContext().startActivity(intent);
            }
        });
        empBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String role = "Employee";
                Intent intent = new Intent(v.getContext(), SignUp1.class);
                intent.putExtra("role", role);
                v.getContext().startActivity(intent);
            }
        });
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LogIn.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}