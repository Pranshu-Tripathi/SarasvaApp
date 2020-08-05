package com.example.sarasva;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class BlogingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private Dialog dialog;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private TextView nameOfUser,mailOfUser;
    private View headerNav;
    private CircleImageView ProfileImageofUser;
    private Button UploadImage,UploadIMGStorage;
    private StorageReference storageReference;
    private Uri imgURI;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloging);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_blog);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        navigationView = findViewById(R.id.blogingNav);
        headerNav = navigationView.getHeaderView(0);
        UploadImage = headerNav.findViewById(R.id.UploadImage);
        UploadIMGStorage = headerNav.findViewById(R.id.UploadImageBTN);
        progressBar = headerNav.findViewById(R.id.imgProgress);
        drawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        dialog = new Dialog(this);
        progressBar.setVisibility(View.INVISIBLE);

        storageReference = FirebaseStorage.getInstance().getReference("Blogs/UserProfileImg");

        navigationView.setNavigationItemSelectedListener(BlogingActivity.this);

        checkUser();

        UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OpenGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(OpenGallery,1);
            }
        });

        UploadIMGStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgURI == null){
                    Toast.makeText(BlogingActivity.this, "No File Chosen", Toast.LENGTH_SHORT).show();
                }else{
                    final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imgURI));
                    fileReference.putFile(imgURI)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                                    progressBar.setProgress(0);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    fileReference.getDownloadUrl()
                                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(final Uri uri) {
                                                    reference = FirebaseDatabase.getInstance().getReference()
                                                            .child("Sarasva")
                                                            .child("Users")
                                                            .child("Profile")
                                                            .child(firebaseUser.getUid());

                                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()){
                                                                imgURI = uri;
                                                                dataSnapshot.getRef().child("profileImg").setValue(uri.toString());
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                    Picasso.with(getApplicationContext())
                                                            .load(uri)
                                                            .into(ProfileImageofUser);
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(BlogingActivity.this, "Error Uploading \n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                     progressBar.setVisibility(View.VISIBLE);
                                     double progress = (double) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                                     progressBar.setProgress((int) progress);
                                }
                            });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Uri imgUri = data.getData();
                if(imgUri != null){
                    imgURI = imgUri;
                    ProfileImageofUser.setImageURI(imgUri);
                }
            }
        }
    }

    private void checkUser(){

        if(firebaseUser == null){
            dialog.setContentView(R.layout.login_signup_popup);
            final Button Login,SignUp;
            Login = dialog.findViewById(R.id.loginBlog);
            SignUp = dialog.findViewById(R.id.signUpBlog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();


            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BlogingActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            });

            SignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(BlogingActivity.this,SignUpActivity.class);
                    startActivity(intent);
                }
            });
        }else{



            nameOfUser = headerNav.findViewById(R.id.displayName);
            mailOfUser = headerNav.findViewById(R.id.usernameMail);
            ProfileImageofUser = headerNav.findViewById(R.id.profileUser);

            reference = FirebaseDatabase.getInstance().getReference()
                    .child("Sarasva")
                    .child("Users")
                    .child("Profile")
                    .child(firebaseUser.getUid());

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String imgUrl = dataSnapshot.child("profileImg").getValue().toString();
                        String username = dataSnapshot.child("name").getValue().toString();
                        String mailUser = dataSnapshot.child("mailId").getValue().toString();

                       try {
                           progressBar.setVisibility(View.VISIBLE);
                           nameOfUser.setText(username);
                           mailOfUser.setText(mailUser);

                           Picasso.with(getApplicationContext())
                                   .load(imgUrl)
                                   .placeholder(R.drawable.profileimg)
                                   .into(ProfileImageofUser);
                           progressBar.setVisibility(View.INVISIBLE);
                       }catch (Exception e){
                           Log.i("ERROR>>>>>>",e.getMessage().toString());
                           progressBar.setVisibility(View.INVISIBLE);
                       }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.homeBlog:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(BlogingActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.eventBlog:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent1 = new Intent(BlogingActivity.this,EventList.class);
                startActivity(intent1);
                return true;
            case R.id.AnnouncementsBlog:
                drawerLayout.closeDrawer(GravityCompat.START);
                Toast.makeText(this, "Announcements", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logOut:
                drawerLayout.closeDrawer(GravityCompat.START);
                firebaseAuth.signOut();
                Intent intent2 = new Intent(BlogingActivity.this,MainActivity.class);
                startActivity(intent2);
                return true;
            case R.id.editProfile:
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }

        return false;
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
}