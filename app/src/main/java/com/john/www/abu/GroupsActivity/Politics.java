package com.john.www.abu.GroupsActivity;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.john.www.abu.R;

public class Politics extends GroupChatActivity {



    @Override
    public DocumentReference getDatabaseRef() {

        DocumentReference  databaseReference1=FirebaseFirestore.getInstance().collection("groups").document("politics");
        return databaseReference1;
    }
}
