package com.example.textdetection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
      private ImageView imageView;
       public Uri selectedPhoto = null;
       private Button galleryButton;
       private Button convertButton;
       private Button takePıcture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePıcture = ((Button) findViewById(R.id.takePhoto));
        galleryButton = ((Button)findViewById(R.id.gallery));
        takePıcture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);

            }
        });
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,0);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        convertButton = ((Button) findViewById(R.id.convert));
        imageView  = ((ImageView)(findViewById(R.id.imageView2)));
        if (resultCode==Activity.RESULT_OK && requestCode==0 && data!=null){
            selectedPhoto = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedPhoto);
                imageView.setImageBitmap(bitmap);
                convertButton.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(resultCode==Activity.RESULT_OK && requestCode==1 && data != null){
            Bitmap bitmap = ((Bitmap) data.getExtras().get("data"));
            imageView.setImageBitmap(bitmap);
            convertButton.setVisibility(View.VISIBLE);
        }
    }
}