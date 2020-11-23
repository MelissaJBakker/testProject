package com.melissabakker.newaccount;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class User {
    private String email;
    private String role;
    private String username;
    static FirebaseFirestore fStore;
    static CollectionReference userRef;
    static ArrayList<User> users;

    public User(String email, String role, String username) {
        this.email = email;
        this.role = role;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole(){
        return role;
    }

    public String getUsername() {
        return username;
    }

    /**
    public static ArrayList<User> getUsers() {
        users = new ArrayList<User>();
        fStore = FirebaseFirestore.getInstance();
        userRef = fStore.collection("users");
        userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    users.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("Main", document.getId() + " => " + document.getData());
                        if (!document.getString("role").equals("Admin")) {
                            String e = document.getString("email");
                            String r = document.getString("role");
                            String un = document.getString("username");
                            users.add(new User(e, r, un));
                        }
                    }
                }
            }
        });
        Log.d("Main", String.valueOf(users));
        return users;
    } **/
}

/**
 FirebaseFirestore fStore = FirebaseFirestore.getInstance();
 DocumentReference documentReference = fStore.collection("emailID").document(email);
 documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
@Override
public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
userID = value.getString("userID");
}
});
 **/