package com.example.android.chatting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterScreen extends AppCompatActivity {

    ProgressBar load;
    EditText username,email,password;
    Button signUp;
    private FirebaseAuth mAuth;
    private android.support.v7.widget.Toolbar topBar;
    private DatabaseReference databaseReference;
    private TextView acc_name,acc_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        mAuth = FirebaseAuth.getInstance();
        load = findViewById(R.id.progressBar2);
        load.setVisibility(View.INVISIBLE);

        topBar =  findViewById(R.id.register_page_toolbar);
        setSupportActionBar(topBar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final TextView create = findViewById(R.id.textView3);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u = username.getText().toString();
                String e = email.getText().toString();
                String p = password.getText().toString();
                hideKeyboardFrom(RegisterScreen.this,view);
                if (TextUtils.isEmpty(u) || TextUtils.isEmpty(e) || TextUtils.isEmpty(p)){
                    Toast.makeText(RegisterScreen.this, "Error while Registering the User. Please check your details.", Toast.LENGTH_SHORT).show();
                }else{
                    register_user(u,e,p);
                    username.animate().alpha(0).setDuration(300);
                    email.animate().alpha(0).setDuration(300);
                    password.animate().alpha(0).setDuration(300);
                    signUp.animate().alpha(0).setDuration(300);
                    create.animate().alpha(0).setDuration(300);
                    load.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void register_user(final String username, String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = currentUser.getUid();

                            databaseReference = FirebaseDatabase.getInstance().getReference("chatting-c8b70").child("Users").child(uid);

                            HashMap<String,String> userMap = new HashMap<>();
                            userMap.put("name",username);
                            userMap.put("status","Hey there!!");
                            userMap.put("image","default");

                            databaseReference.setValue(userMap);
                            Log.i("Done","Done");



                            Intent intent = new Intent(RegisterScreen.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            load.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegisterScreen.this, "Done", Toast.LENGTH_SHORT).show();

                        }else{
                            load.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegisterScreen.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                            Log.i("ERor",task.getException().toString());
                        }
                    }
                });

    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
