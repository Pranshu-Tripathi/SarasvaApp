package com.example.sarasva;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private EditText nameUser,emailUser,contactNumberUser,passwordUser;
    private Button SignUpButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameUser = findViewById(R.id.nameUserBlog);
        emailUser = findViewById(R.id.mailUserBlog);
        contactNumberUser = findViewById(R.id.contactUserBlog);
        passwordUser = findViewById(R.id.passwordUserBlog);
        SignUpButton = findViewById(R.id.btnSignUp);
        progressBar = findViewById(R.id.signUpProgress);


        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference()
                .child("Sarasva")
                .child("Users")
                .child("Profile");

        progressBar.setVisibility(View.INVISIBLE);



        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(nameUser.getText()) || TextUtils.isEmpty(emailUser.getText()) || TextUtils.isEmpty(contactNumberUser.getText()) || TextUtils.isEmpty(passwordUser.getText())){
                    Toast.makeText(SignUpActivity.this, "All Details Are Mandatory!", Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);

                    AuthenticateUser();


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(SignUpActivity.this,BlogingActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    },5000);

                }
            }
        });

    }

    private void AuthenticateUser(){
        String mailId = emailUser.getText().toString().trim();
        String password = passwordUser.getText().toString().trim();
        firebaseAuth.createUserWithEmailAndPassword(mailId,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignUpActivity.this, "Authentication Successful!\nPlease Wait!", Toast.LENGTH_SHORT).show();
                        firebaseUser = firebaseAuth.getCurrentUser();
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child(firebaseUser.getUid()).child("name").setValue(nameUser.getText().toString());
                                dataSnapshot.getRef().child(firebaseUser.getUid()).child("mailId").setValue(emailUser.getText().toString().toLowerCase().trim());
                                dataSnapshot.getRef().child(firebaseUser.getUid()).child("contact").setValue(contactNumberUser.getText().toString());
                                dataSnapshot.getRef().child(firebaseUser.getUid()).child("profileImg").setValue("NONE");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.i("Issue In Addition",databaseError.getMessage().toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Authentication Failed!\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
    }

}


// TODO : Add the User funtion Completion and Image Upload Completion