package com.melissabakker.newaccount;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    LogIn test;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Test
    public void authentication() {
        test.em = "vpatel@gmail.com";
        test.pw = "testing";
        firebaseAuth.signInWithEmailAndPassword(test.em, test.pw)
            .addOnCompleteListener(test, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        System.out.println("Authentication successful");
                    }
                    else {
                        System.out.println("Authentication was not successful");
                    }
                }
            });
    }
}