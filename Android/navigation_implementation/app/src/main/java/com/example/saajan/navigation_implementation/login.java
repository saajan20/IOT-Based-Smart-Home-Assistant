package com.example.saajan.navigation_implementation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth auth;
    private String Email;
    private String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        Email="";
        Password="";

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null)
        {
            Toast.makeText(login.this,"You are logged in", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(login.this,MainActivity.class);
            startActivity(i);
        }
    }

    public void OpenHomePage(View view) {

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        Email = email.getText().toString();
        Password = password.getText().toString();

        System.out.println(Email);
        if (Email.isEmpty() || Password.equals(null)) {
            Toast.makeText(login.this, "Please Enter Your Credentials", Toast.LENGTH_LONG).show();
        } else {
            auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        System.out.println("hey");
                        Toast.makeText(login.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                    } else {
                        Intent i = new Intent(login.this, MainActivity.class);
                        startActivity(i);
                    }
                }
            });

        }
    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
