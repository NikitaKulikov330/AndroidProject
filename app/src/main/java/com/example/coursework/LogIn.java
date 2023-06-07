
package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.Admin.AdminCategory;
import com.example.coursework.Prevalent.Prevalent;
import com.example.coursework.Users.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;

public class LogIn extends AppCompatActivity {
    private TextView forgetPass;
    private EditText editEmail;
    private EditText editPassword;
    private Button enter;
    private Button backMain;
    private ProgressDialog loading;
    private String dbName = "Users";
    private CheckBox rememberCheck;
    private TextView user;
    private TextView admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editPassword = (EditText) findViewById(R.id.edit_pass);
        enter = (Button) findViewById(R.id.enter_btn);
        rememberCheck = (CheckBox) findViewById(R.id.remember_check);
        Paper.init(this);
        forgetPass = (TextView) findViewById(R.id.forget_pass);
        user = (TextView) findViewById(R.id.forusers);
        admin = (TextView) findViewById(R.id.foradmin);
        backMain = (Button) findViewById(R.id.back_sign_in);
        loading = new ProgressDialog(this);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin.setVisibility(View.INVISIBLE);
                user.setVisibility(View.VISIBLE);
                enter.setText("Log in for admins");
                dbName = "Admins";
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin.setVisibility(View.VISIBLE);
                user.setVisibility(View.INVISIBLE);
                enter.setText("Log in");
                dbName = "Users";
            }
        });
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, MainActivity.class);
                startActivity(intent);
            }
        });

        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(LogIn.this, MainActivity.class);
                startActivity(back);
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLogIn();
            }
        });
    }

    private void UserLogIn() {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();

        } else if (!isEmailAddress(email)) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
        } else {
            loading.setTitle("Log In");
            loading.setMessage("Please wait..");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            CheckUser(email, password);
        }
    }

    private void CheckUser(String email, String password) {
        if(rememberCheck.isChecked()){
            Paper.book().write(Prevalent.userEmail,email);
            Paper.book().write(Prevalent.userPassword,password);

        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.child(dbName).orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
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
                        if(dbName.equals("Users")){
                            loading.dismiss();
                            Prevalent.onlineUser = userData;
                            Toast.makeText(LogIn.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogIn.this, Home.class);
                            startActivity(intent);
                        }else if(dbName.equals("Admins")){
                            loading.dismiss();
                            Toast.makeText(LogIn.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogIn.this, AdminCategory.class);
                            startActivity(intent);
                        }
                    }else {
                        loading.dismiss();
                        Toast.makeText(LogIn.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(LogIn.this, "Account " + email + " doesn't exist", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LogIn.this, SignIn.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static boolean isEmailAddress(String email) {
        String expression = "^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(gmail\\.com|ukr\\.net|example\\.com|icloud\\.com|mail\\.ru)$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}