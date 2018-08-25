package com.john.www.abu.GroupsActivity;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class Relationship extends GroupChatActivity {




    @Override
    public DocumentReference getDatabaseRef() {

        DocumentReference  databaseReference1=FirebaseFirestore.getInstance().collection("groups").document("relationship");
        return databaseReference1;
    }
}
