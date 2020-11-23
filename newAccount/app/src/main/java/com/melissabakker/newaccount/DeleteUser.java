package com.melissabakker.newaccount;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DeleteUser extends AppCompatActivity {

    ArrayList<User> users;
    FirebaseFirestore fStore;
   CollectionReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        /** Create the current list of stored users with the custom adapter and user class **/

        users = new ArrayList<User>();
        fStore = FirebaseFirestore.getInstance();
        userRef = fStore.collection("users");
        userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    users.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getString("role").equals("Admin")) {
                            String e = document.getString("email");
                            String r = document.getString("role");
                            String un = document.getString("username");
                            users.add(new User(e, r, un));
                        }
                        Log.d("Main", String.valueOf(users));
                        UserAdapter adapter = new UserAdapter(DeleteUser.this, users);
                        ListView listView = (ListView) findViewById(R.id.user_view);
                        listView.setAdapter(adapter);
                    }
                } else {
                    Log.d("Main", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}