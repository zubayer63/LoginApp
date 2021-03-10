package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signInEmail, signInPass;
    private Button signInButton;
    private TextView signUpText;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        this.setTitle("Sign In");

        signInEmail = findViewById(R.id.signInemailId);
        signInPass  = findViewById(R.id.signInpassId);
        signInButton = findViewById(R.id.signInButtonID);
        signUpText = findViewById(R.id.signUpTextID);
        progressBar =findViewById(R.id.signInprograssbarID);

        signInButton.setOnClickListener(this);
        signUpText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.signInButtonID :
              userLogin();
                break;


            case R.id.signUpTextID  :
                Intent intent =new Intent(getApplicationContext(),SignUPActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userLogin() {
        String email = signInEmail.getText().toString();
        String pass =signInPass.getText().toString();
        //check the validity of email;
        if(email.isEmpty()){
            signInEmail.setError("Enter an email address");
            signInEmail.requestFocus(); //use to move the cursor in the email edit text field;
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signInEmail.setError("enter a valid email address");
            signInEmail.requestFocus();
            return;
        }
        //check the validity of password;
        if(pass.isEmpty()){
            signInPass.setError("enter a password");
            signInPass.requestFocus();//use to move the cursor in the pass edit text field;
            return;
        }
        if(pass.length()<6){
            signInPass.setError("minimum length of a password should be 6");
            signInPass.requestFocus();//use to move the cursor in the pass edit text field;
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    finish();//used to finished the sign in activity
                    Intent intent = new Intent(getApplicationContext(),ShowDataActivity.class);
                   // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//used to clear the title bar data;
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"LogIN Unsuccessful",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}