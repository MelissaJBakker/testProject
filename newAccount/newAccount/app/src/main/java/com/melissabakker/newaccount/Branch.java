package com.melissabakker.newaccount;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Branch {

    private String email, address, branchName;
    private FirebaseFirestore fStore;

    public Branch(String e, String a, String bN) {
        email = e;
        address = a;
        branchName = bN;
        fStore = FirebaseFirestore.getInstance();
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getID() {
        final String[] id = new String[1];
        DocumentReference documentReference = fStore.collection("emailID").document(email);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                id[0] = value.getString("userID");
            }
        });
        return id[0];
    }

    public String getScheduleForDay(final String day) {
        final String[] dayS = new String[1];
        DocumentReference documentReference = fStore.collection("branchSchedule").document(getID());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                dayS[0] = value.getString(day);
            }
        });
        return dayS[0];
    }
}
