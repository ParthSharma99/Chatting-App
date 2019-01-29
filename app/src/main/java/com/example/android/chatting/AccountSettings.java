package com.example.android.chatting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountSettings extends AppCompatActivity {
    private DatabaseReference myRef ;
    private FirebaseUser currentUser;
    private TextView acc_name,acc_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        acc_name = findViewById(R.id.account_name);
        acc_status = findViewById(R.id.account_status);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        // Account Settings setup --------------------


        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Toast.makeText(AccountSettings.this, dataSnapshot.child("name").getValue().toString(), Toast.LENGTH_SHORT).show();
                    try{
                        String name = dataSnapshot.child("name").getValue().toString();
                        String status = dataSnapshot.child("status").getValue().toString();
                        acc_name.setText(name);
                        acc_status.setText(status);}catch (Exception e){e.printStackTrace();}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void changeStatus(View view){
        Intent intent = new Intent(AccountSettings.this,ChangeStatus.class);
        startActivity(intent);
    }
}
