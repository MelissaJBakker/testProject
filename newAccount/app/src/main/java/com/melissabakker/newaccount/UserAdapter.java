package com.melissabakker.newaccount;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {

        FirebaseFirestore fStore = FirebaseFirestore.getInstance();


        public UserAdapter(Context context, ArrayList<User> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
            }


            TextView email = (TextView) convertView.findViewById(R.id.userEmail);
            TextView role = (TextView) convertView.findViewById(R.id.userRole);
            Button delBtn = (Button) convertView.findViewById(R.id.button2);

            final User user = getItem(position);

            email.setText(user.getEmail());
            role.setText(user.getRole());


            delBtn.setTag(position);
            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //int position = (Integer) view.getTag();

                    final String[] userID = new String[1];

                    DocumentReference doc = fStore.collection("emailID").document(user.getEmail());
                    doc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            userID[0] = value.getString("userID");
                        }
                    });
                    doc.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d("Main", userID[0] + " emailID is deleted.");
                            }
                            else{
                                Log.d("Main", "Error! " + userID[0] + " emailID is not deleted.");
                            }
                        }
                    });
                    if (user.getRole().equals("Employee")) {
                        DocumentReference bServices = fStore.collection("branchServices").document(userID[0]);
                        bServices.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Log.d("Main", userID[0] + " branchServices is deleted.");
                                }
                                else{
                                    Log.d("Main", "Error! " + userID[0] + " branchServices is not deleted.");
                                }
                            }
                        });
                    }
                    DocumentReference userDoc = fStore.collection("users").document(userID[0]);
                    userDoc.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d("Main", userID[0] + " user is deleted.");
                            }
                            else{
                                Log.d("Main", "Error! " + userID[0] + " user is not deleted.");
                            }
                        }
                    });
            }});
            return convertView;
        }
}
