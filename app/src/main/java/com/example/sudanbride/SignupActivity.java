package com.example.sudanbride;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextemail, editTextpassword, editTextconfirm;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextemail = (EditText) findViewById(R.id.editTextemail);
        editTextpassword = (EditText) findViewById(R.id.editTextpassword);
        editTextconfirm = (EditText) findViewById(R.id.editTextconfirm);
        progressBar = (ProgressBar) findViewById(R.id.SignupprogressBar);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btnsignup).setOnClickListener(this);
    }


    private void registeruser(){
        String email = editTextemail.getText().toString().trim();
        String password =  editTextpassword.getText().toString().trim();
        String confirm = editTextconfirm.getText().toString().trim();

        if (email.isEmpty()){
            editTextemail.setError("Email is required!");
            editTextemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Email is not valid!");
            editTextemail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextpassword.setError("Password is required!");
            editTextpassword.requestFocus();
            return;
        }

        if (password.length() <6){

            editTextpassword.setError("Password minimum length is 6");
            editTextpassword.requestFocus();
            return;
        }

        if (confirm.isEmpty()){
            editTextconfirm.setError("Password is required!");
            editTextconfirm.requestFocus();
            return;
        }

        if (confirm.length() <6){

            editTextconfirm.setError("Password minimum length is 6");
            editTextconfirm.requestFocus();
            return;
        }

        if (!confirm.equals(password)){
            editTextconfirm.setError("Password is not same");
            editTextconfirm.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,confirm).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Register Done", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.btnsignup:
             registeruser();
             break;
     }
    }
}
