package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignIn extends AppCompatActivity {
    private EditText signName;
    private EditText signEmail;
    private EditText signPassword;
    private Button createBtn;
    private ProgressDialog loading;
    private Button backMain;
    private CircleImageView imagee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signName = (EditText) findViewById(R.id.sign_name);
        signEmail = (EditText) findViewById(R.id.sign_email);
        signPassword = (EditText) findViewById(R.id.sign_pass);
        createBtn = (Button) findViewById(R.id.create_btn);
        backMain = (Button) findViewById(R.id.back_sign_in);
//        imagee = (CircleImageView) findViewById(R.id.app_user_image);
        loading = new ProgressDialog(this);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAcc();
            }
        });

        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(SignIn.this,MainActivity.class);
                startActivity(back);
            }
        });
    }

    private void CreateAcc() {
        String name = signName.getText().toString();
        String email = signEmail.getText().toString();
        String password = signPassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();

        } else if (!isEmailAddress(email)) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
        } else {
            loading.setTitle("Creating account");
            loading.setMessage("Creating...");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            CheckName(name, email, password);
        }
    }

    public static boolean isEmailAddress(String email) {
        String expression = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(gmail\\.com|ukr\\.net|example\\.com|icloud\\.com|mail\\.ru)$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void CheckName(String name, String email, String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Users").child(name).exists())) {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("email", email);
                    userDataMap.put("name", name);
                    userDataMap.put("password", password);
//                    userDataMap.put("image", image);
                    RootRef.child("Users").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                loading.dismiss();
                                Toast.makeText(SignIn.this, "Email " + email + " already exists", Toast.LENGTH_LONG).show();
                            } else {
                                RootRef.child("Users").child(name).updateChildren(userDataMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    loading.dismiss();
                                                    Toast.makeText(SignIn.this, "Successful registration", Toast.LENGTH_LONG).show();
                                                    Intent intentLog = new Intent(SignIn.this, LogIn.class);
                                                    startActivity(intentLog);
                                                } else {
                                                    loading.dismiss();
                                                    Toast.makeText(SignIn.this, "Error", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    loading.dismiss();
                    Toast.makeText(SignIn.this, "Name " + name + " already exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}