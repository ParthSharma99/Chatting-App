package com.example.android.chatting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ChangeStatus extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);

        status = findViewById(R.id.currentStatus);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("chatting-c8b70").child("Users").child(uid);


    }
    public void changeStatus(View view){

        HashMap<String,String> userMap = new HashMap<>();
        userMap.put("name",databaseReference.child("name").getKey());
        userMap.put("status",status.getText().toString());
        userMap.put("image",databaseReference.child("image").getKey());

        databaseReference.setValue(userMap);
    }
}
