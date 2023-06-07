package com.example.coursework;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.UUID;

public class CropperActivity extends AppCompatActivity {
    String result;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropper);

        readIntent();

        String dest_uri = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();

        UCrop.Options option = new UCrop.Options();

        UCrop.of(imageUri,Uri.fromFile(new File(getCacheDir(),dest_uri)))
                .withOptions(option)
                .withAspectRatio(0, 0)
                .withMaxResultSize(150, 150)
                .start(CropperActivity.this);

    }

    private void readIntent() {
        Intent intent = getIntent();
        if(intent.getExtras()!=null){
//            System.out.println("MISHA POPUSK");
            result = intent.getStringExtra("DATA");
            System.out.println(result);
            imageUri = Uri.parse(result);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==UCrop.REQUEST_CROP  &&  resultCode==RESULT_OK  &&  data!=null){
//            System.out.println("MISHA POPUSK");
            final Uri resUri = UCrop.getOutput(data);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("RESULT" ,resUri + "");
            setResult(-1,returnIntent);
            finish();
        }else{
           final Throwable throwError = UCrop.getError(data);
        }
    }
}