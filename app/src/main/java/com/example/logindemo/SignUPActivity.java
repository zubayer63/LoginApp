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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUPActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signUpEmail, signUpPass;
    private Button signUpButton;
    private TextView signInText;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_u_p);
        this.setTitle("Sign UP");
        mAuth = FirebaseAuth.getInstance();

        signUpEmail = findViewById(R.id.signUpemailId);
        signUpPass  = findViewById(R.id.signUppassId);
        signUpButton = findViewById(R.id.signUpButtonID);
        signInText = findViewById(R.id.signInTextID);
        progressBar = findViewById(R.id.signUpprograssID);

        signUpButton.setOnClickListener(this);
        signInText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUpButtonID:
                userRegister();
                break;

            case  R.id.signInTextID:
                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void userRegister() {

        String email = signUpEmail.getText().toString();
        String pass =signUpPass.getText().toString();
        //check the validity of email;
        if(email.isEmpty()){
            signUpEmail.setError("Enter an email address");
            signUpEmail.requestFocus(); //use to move the cursor in the email edit text field;
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpEmail.setError("enter a valid email address");
            signUpEmail.requestFocus();
            return;
        }
        //check the validity of password;
        if(pass.isEmpty()){
            signUpPass.setError("enter a password");
            signUpPass.requestFocus();//use to move the cursor in the pass edit text field;
            return;
        }
        if(pass.length()<6){
            signUpPass.setError("minimum length of a password should be 6");
            signUpPass.requestFocus();//use to move the cursor in the pass edit text field;
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Register is Successfull ",Toast.LENGTH_SHORT).show();
                    finish();//used to finished the sign up activity
                    Intent intent = new Intent(getApplicationContext(),ShowDataActivity.class);
                   // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//used to clear the title bar data;
                    startActivity(intent);
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"User is already Registered",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error : " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}