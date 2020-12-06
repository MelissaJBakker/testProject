package com.melissabakker.newaccount;

import android.content.Context;
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
    private static final String SUCCESS = "success";

    Context context;


    @Test
    public void UnitTest_SignUp2_CheckFields_Fail_Address() {
        SignUp2 myObjectUnderTest = new SignUp2(context);
        String result = myObjectUnderTest.checkFields("1234567890", "null", "[]", "null", "null");
        assertFalse(result.equals(SUCCESS));
    }

    @Test
    public void UnitTest_SignUp2_CheckFields_Fail_Phone() {
        SignUp2 myObjectUnderTest = new SignUp2(context);
        String result = myObjectUnderTest.checkFields("1234", "null", "abc", "null", "null");
        assertFalse(result.equals(SUCCESS));
    }

    @Test
    public void UnitTest_SignUp2_CheckFields_Fail_Empty() {
        SignUp2 myObjectUnderTest = new SignUp2(context);
        String result = myObjectUnderTest.checkFields("", "", "", "", "");
        assertFalse(result.equals(SUCCESS));
    }

    @Test
    public void UnitTest_SignUp2_CheckFields_Pass() {
        SignUp2 myObjectUnderTest = new SignUp2(context);
        String result = myObjectUnderTest.checkFields("1234567890", "null", "abc", "null", "null");
        assertTrue(result.equals(SUCCESS));
    }
}