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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar topBar;
    private FirebaseAuth mAuth;
    TextView email,password;
    Button logIn;
    ProgressBar load;
    TextView l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        topBar =  findViewById(R.id.login_page_toolbar);
        setSupportActionBar(topBar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();

        load = findViewById(R.id.progressBar3);
        load.setVisibility(View.INVISIBLE);

        l = findViewById(R.id.textView4);
        email = findViewById(R.id.email_logIn);
        password = findViewById(R.id.password_logIn);
        logIn = findViewById(R.id.logIn_logIn);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = email.getText().toString();
                String p = password.getText().toString();

                if (TextUtils.isEmpty(e) || TextUtils.isEmpty(p)){
                    Toast.makeText(LoginActivity.this, "Something went Wrong. Chack the details", Toast.LENGTH_LONG).show();
                }else{
                    logInUser(e,p);
                    hideKeyboardFrom(LoginActivity.this,view);
                    l.animate().alpha(0).setDuration(300);
                    email.animate().alpha(0).setDuration(300);
                    password.animate().alpha(0).setDuration(300);
                    logIn.animate().alpha(0).setDuration(300);
                    load.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void logInUser(String e, String p){
        mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    l.animate().alpha(1).setDuration(300);
                    email.animate().alpha(1).setDuration(300);
                    password.animate().alpha(1).setDuration(300);
                    logIn.animate().alpha(1).setDuration(300);
                    load.setVisibility(View.INVISIBLE);
                    }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
