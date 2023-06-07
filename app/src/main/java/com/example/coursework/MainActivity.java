package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.coursework.Prevalent.Prevalent;
import com.example.coursework.Users.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button logIn;
    private Button signIn;
    private ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logIn = (Button) findViewById(R.id.login_btn);
        signIn = (Button) findViewById(R.id.sign_btn);
        loading = new ProgressDialog(this);
        Paper.init(this);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLog = new Intent(MainActivity.this, LogIn.class);
                startActivity(intentLog);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSign = new Intent(MainActivity.this, SignIn.class);
                startActivity(intentSign);
            }
        });


        String userEmail = Paper.book().read(Prevalent.userEmail);
        String userPassword = Paper.book().read(Prevalent.userPassword);
        if(userEmail != "" && userPassword != ""){
            if(!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword)){
                ValidateUser(userEmail,userPassword);

                loading.setTitle("Log in");
                loading.setMessage("Please wait...");
                loading.setCanceledOnTouchOutside(false);
                loading.show();
            }
        }
    }

    private void ValidateUser(String email, String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.child("Users").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String currentName = " ";
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        currentName = childSnapshot.getKey();
//                        System.out.println(currentName);
//                        System.out.println(snapshot.getChildren());
                    }
                    assert currentName != null;
                    Users userData = snapshot.child(currentName).getValue(Users.class);
                    if(userData.getPassword().equals(password)) {
                        loading.dismiss();
                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}