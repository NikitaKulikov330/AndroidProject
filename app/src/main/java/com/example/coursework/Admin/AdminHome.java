package com.example.coursework.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminHome extends AppCompatActivity {
    private String category , pName, pDescription,pPrice, saveCurrentDate,getSaveCurrentTime , pRandomKey;
    private String downloadImageUri;
    private ImageView src;
    private EditText addTitle;
    private EditText addDescription;
    private EditText addPrice;
    private Button add;
    private TextView descr;
    final static int gallery = 1;
    private Uri imageUri;
    private StorageReference pImageRef;
    private DatabaseReference productRef;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        init();
        descr.setText(category);

        src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProduct();
            }
        });
    }

    private void ValidateProduct() {
        pDescription = addDescription.getText().toString();
        pName = addTitle.getText().toString();
        pPrice = addPrice.getText().toString();

        if(imageUri == null){
            Toast.makeText(AdminHome.this,"Add image",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(pDescription)){
            Toast.makeText(AdminHome.this,"Add description",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(pName)){
            Toast.makeText(AdminHome.this,"Add title",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(pPrice)){
            Toast.makeText(AdminHome.this,"Add price",Toast.LENGTH_LONG).show();
        }else{
            StoreData();
        }
    }

    private void StoreData() {

        loading.setTitle("Loading data");
        loading.setMessage("Please wait... ");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd,MM,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH,mm,ss");
        getSaveCurrentTime = currentTime.format(calendar.getTime());


        pRandomKey = saveCurrentDate + " - " + getSaveCurrentTime;

        StorageReference filePath = pImageRef.child(imageUri.getLastPathSegment() + pRandomKey + ".jpg");
        final UploadTask uploadTask  = filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                loading.dismiss();
                Toast.makeText(AdminHome.this, "Error " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminHome.this, "Image uploaded", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUri = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AdminHome.this,"Image saved",Toast.LENGTH_LONG).show();
                            Uri downlaodImage = task.getResult();
                            String myUrl = downlaodImage.toString();

                            HashMap<String,Object> productMap = new HashMap<>();
                            productMap.put("pid",pRandomKey);
                            productMap.put("data",saveCurrentDate);
                            productMap.put("time",getSaveCurrentTime);
                            productMap.put("description",pDescription);
                            productMap.put("image",myUrl);
                            productMap.put("category",category);
                            productMap.put("price",pPrice);
                            productMap.put("title",pName);
//                            System.out.println(pName);

                            productRef.child(pRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        loading.dismiss();
                                        Toast.makeText(AdminHome.this,"Goods added",Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(AdminHome.this,AdminCategory.class);
                                        startActivity(intent);
                                    }else{
                                        String message = task.getException().toString();
                                        loading.dismiss();
                                        Toast.makeText(AdminHome.this,"Error " + message,Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

//    private void SavetoDataBase() {
//        HashMap<String,Object> productMap = new HashMap<>();
//        productMap.put("pid",pRandomKey);
//        productMap.put("data",saveCurrentDate);
//        productMap.put("time",getSaveCurrentTime);
//        productMap.put("description",pDescription);
//        productMap.put("image",downloadImageUri);
//        productMap.put("category",category);
//        productMap.put("price",pPrice);
//        productMap.put("title",pName);
//
//        productRef.child(pRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    loading.dismiss();
//                    Toast.makeText(AdminHome.this,"Goods added",Toast.LENGTH_LONG).show();
//
//                    Intent intent = new Intent(AdminHome.this,AdminCategory.class);
//                    startActivity(intent);
//                }else{
//                    String message = task.getException().toString();
//                    loading.dismiss();
//                    Toast.makeText(AdminHome.this,"Error " + message,Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }

    private void OpenGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,gallery);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == gallery && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            src.setImageURI(imageUri);
        }
    }

    private void init(){
        category = getIntent().getExtras().get("category").toString();
        src = findViewById(R.id.add_ctg);

        if(category.equals("lipsticks")){
            src.setImageResource(R.drawable.lipstick2);
        }else if(category.equals("highlighter")){
            src.setImageResource(R.drawable.highlighters3);
        }else if(category.equals("nailpolish")){
            src.setImageResource(R.drawable.nailpolish);
        }else if(category.equals("mascara")){
            src.setImageResource(R.drawable.mascara);
        }else if(category.equals("sculpturing")){
            src.setImageResource(R.drawable.sculpturing);
        }else if(category.equals("concealer")){
            src.setImageResource(R.drawable.concealer);
        }else if(category.equals("powder")){
            src.setImageResource(R.drawable.powder);
        }else if(category.equals("parfum")){
            src.setImageResource(R.drawable.parfums);
        }

        addTitle = (EditText) findViewById(R.id.add_title);
        addDescription = (EditText) findViewById(R.id.add_description);
        addPrice = (EditText) findViewById(R.id.add_price);
        add = (Button) findViewById(R.id.add_btn);
        pImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        loading = new ProgressDialog(this);
        descr = (TextView) findViewById(R.id.descr);
    }
}
