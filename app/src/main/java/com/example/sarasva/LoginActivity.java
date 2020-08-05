package com.example.sarasva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText MailIN,PassIn;
    private Button LoginBtn;
    private ProgressBar ProgressLogin;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MailIN = findViewById(R.id.mailUserBlogLogin);
        PassIn= findViewById(R.id.passwordUserBlogLogin);
        LoginBtn = findViewById(R.id.btnLogin);
        ProgressLogin = findViewById(R.id.signInProgress);

        firebaseAuth =FirebaseAuth.getInstance();

        ProgressLogin.setVisibility(View.INVISIBLE);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(MailIN.getText()) || TextUtils.isEmpty(PassIn.getText())){
                    Toast.makeText(LoginActivity.this, "All Parameters Are Mandatory!", Toast.LENGTH_SHORT).show();
                }else{
                    ProgressLogin.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(MailIN.getText().toString().toLowerCase().trim(),PassIn.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    ProgressLogin.setVisibility(View.INVISIBLE);
                                    Toast.makeText(LoginActivity.this, "Logged IN !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this,BlogingActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}