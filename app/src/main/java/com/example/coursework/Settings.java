package com.example.coursework;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.coursework.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {
    CircleImageView profileImage;
    EditText newEmail,newPhone,newPassword;
    TextView closeSettings,saveSettings;
    private String checker = "";
    private StorageReference storageProfileImage;
    private StorageTask uploadTask;
    ActivityResultLauncher<String> resImage;
    CircleImageView app_user_image;
    private Uri saveUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        profileImage = (CircleImageView) findViewById(R.id.settings_image);
        newEmail = (EditText) findViewById(R.id.change_email);
        newPhone = (EditText) findViewById(R.id.change_name);
        newPassword = (EditText) findViewById(R.id.change_pass);
        closeSettings = (TextView) findViewById(R.id.close_settings);
        saveSettings = (TextView) findViewById(R.id.save_settings);
        storageProfileImage = FirebaseStorage.getInstance().getReference().child("Profile images");
        app_user_image = (CircleImageView) findViewById(R.id.app_user_image);
        userInfoDisplay( profileImage,newEmail,newPhone,newPassword);


        closeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this,Home.class);
                startActivity(intent);
            }
        });
        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checker.equals("clicked")){
                    userInfoSave();
                }else{
                    updateOnlyUserInfo();
                }
                System.out.println("NNNNN");
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";

                resImage.launch("image/*");


            }
        });
        resImage = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Intent intent = new Intent(Settings.this,CropperActivity.class);
                intent.putExtra("DATA",result.toString());
                startActivityForResult(intent, 101);
            }
        });
    }

    private void userInfoDisplay( CircleImageView profileImage, EditText newEmail, EditText newName, EditText newPassword) {
        String name1 = Prevalent.onlineUser.getName();
//        System.out.println(Prevalent.onlineUser.getName());
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(name1);
//        System.out.println("AAAA");
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
//                    System.out.println("EEEEE");
                    if(snapshot.child("image").exists()){
                        String image = snapshot.child("image").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        String email = snapshot.child("email").getValue().toString();
                        String password = snapshot.child("password").getValue().toString();

                        Picasso.get().load(image).into(profileImage);
                        newEmail.setText(email);
//                        System.out.println(email);
                        newName.setText(phone);
//                        System.out.println(phone);
                        newPassword.setText(password);
//                        System.out.println(password);
                    }if(snapshot.child("phone").exists()){
                        String phone = snapshot.child("phone").getValue().toString();
                        String email = snapshot.child("email").getValue().toString();
                        String password = snapshot.child("password").getValue().toString();

                        newEmail.setText(email);
                        newPhone.setText(phone);
                        newPassword.setText(password);

                    }else{
                        String email = snapshot.child("email").getValue().toString();
                        String password = snapshot.child("password").getValue().toString();

                        newEmail.setText(email);
                        newPassword.setText(password);
                    }
                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void updateOnlyUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        if (newEmail.getText().toString().equals(""))
        {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
        }
        else if (newPassword.getText().toString().equals(""))
        {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String,Object> userMap = new HashMap<>();
            userMap.put("phone",newPhone.getText().toString());
            userMap.put("email",newEmail.getText().toString());
            userMap.put("password",newPassword.getText().toString());

            ref.child(Prevalent.onlineUser.getName()).updateChildren(userMap);

            Intent intent = new Intent(Settings.this,Home.class);
            startActivity(intent);
            Toast.makeText(Settings.this, "Data saved", Toast.LENGTH_SHORT).show();
        }


    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
//        {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            imageUri = result.getUri();
//
//            profileImage.setImageURI(imageUri);
//        }
//        else
//        {
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//
//            startActivity(new Intent(Settings.this, Settings.class));
//            finish();
//        }
//    }

    private void userInfoSave() {
        if (TextUtils.isEmpty(newEmail.getText().toString()))
        {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(newPassword.getText().toString()))
        {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked"))
        {
            uploadImage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == -1){
            String result = data.getStringExtra("RESULT");
//            System.out.println(result);
            Uri resultUri = null;
            if(result != null){
//                System.out.println("MISHA NE POPUSK");
                resultUri = Uri.parse(result);
                saveUri = Uri.parse(result);

            }

            profileImage.setImageURI(resultUri);
//            app_user_image.setImageURI(resultUri);

        }
    }

    private void uploadImage() {
        final ProgressDialog progress = new ProgressDialog(Settings.this);
        progress.setTitle("Updating");
        progress.setMessage("Please wait...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        if(saveUri!=null){
            final StorageReference storageRef = storageProfileImage.child(Prevalent.onlineUser.getName() + ".WebP");

            uploadTask = storageRef.putFile(saveUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return storageRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                Uri downlaodImage = task.getResult();
                                String myUrl = downlaodImage.toString();

                                DatabaseReference ref =FirebaseDatabase.getInstance().getReference().child("Users");

                                HashMap<String,Object> newMap = new HashMap<>();
                                    newMap.put("phone",newPhone.getText().toString());
                                    newMap.put("email",newEmail.getText().toString());
                                    newMap.put("password",newPassword.getText().toString());
                                    newMap.put("image",myUrl);
                                ref.child(Prevalent.onlineUser.getName()).updateChildren(newMap);

                                progress.dismiss();

                                Intent intent = new Intent(Settings.this,Home.class);
                                Toast.makeText(Settings.this, "Data saved", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }else{
                                progress.dismiss();
                                Toast.makeText(Settings.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(Settings.this, "Choose image", Toast.LENGTH_SHORT).show();
        }

    }
}