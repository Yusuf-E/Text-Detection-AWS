package com.example.textdetection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.TypedArrayUtils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Priority;
import com.amplifyframework.datastore.generated.model.Todo;
import com.amplifyframework.predictions.aws.AWSPredictionsPlugin;
import com.amplifyframework.predictions.models.TextFormatType;
import com.amplifyframework.predictions.result.IdentifyTextResult;
import com.jakewharton.processphoenix.ProcessPhoenix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    private ImageView imageView;
    public Uri selectedPhoto = null;
    private Button galleryButton;
    private Button convertButton;
    private Button takePıcture;
    public Bitmap bitmap;
    public int nextTime = 30000;
    public String newdata;
    ConstraintLayout constraintLayout;
    TextRekognition textRekognition = new TextRekognition();
    TextView textView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = ((ConstraintLayout) findViewById(R.id.constraint));
        takePıcture = ((Button) findViewById(R.id.takePhoto));
        galleryButton = ((Button)findViewById(R.id.gallery));
        progressBar = ((ProgressBar) findViewById(R.id.progressBar2));
        progressBar.setVisibility(View.INVISIBLE);
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
        convertButton = findViewById(R.id.convert);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textRekognition.newdata="";
                Timer timer=new Timer();
                progressBar.setVisibility(View.VISIBLE);
                activate(false);
                Toast toast = Toast.makeText(getApplicationContext(), "Dönüştürme işlemi tamamlananıyor..", Toast.LENGTH_LONG);
                toast.show();
                textRekognition.detectText(bitmap);
                timer.scheduleAtFixedRate(new TimerTask(){
                    @Override
                    public void run(){
                       if (textRekognition.newdata!=""){
                           Intent next = new Intent(MainActivity.this,FinalActivity.class);
                           next.putExtra("asciikey",textRekognition.newdata);
                           next.putExtra("datakey",textRekognition.data);
                           System.out.println(textRekognition.newdata);
                           progressBar.setVisibility(View.INVISIBLE);
                           activate(true);
                           timer.cancel();
                           startActivity(next);

                       }
                        if (textRekognition.booldata==0){
                               PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,1000,getIntent(),PendingIntent.FLAG_CANCEL_CURRENT);
                               AlarmManager alarmManager = ((AlarmManager) getSystemService(Context.ALARM_SERVICE));
                                  alarmManager.set(AlarmManager.RTC,System.currentTimeMillis()+5000,pendingIntent);
                            System.exit(0);
                        }
//                       if(textRekognition.booldata==0){
//                           runOnUiThread(new Runnable() {
//                               public void run() {
//                               final Toast toast = Toast.makeText(getApplicationContext(), "AWS SIÇTI", Toast.LENGTH_LONG);
//                                 toast.show();
//
//                               }
//                           });
//                       }
                    }
                },0,500);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                },nextTime);

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
              bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedPhoto);
//                TextRekognition textRekognition = new TextRekognition();
//                textRekognition.detectText(bitmap);
                imageView.setImageBitmap(bitmap);
                convertButton.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(resultCode==Activity.RESULT_OK && requestCode==1 && data != null){
            bitmap = ((Bitmap) data.getExtras().get("data"));
//            TextRekognition textRekognition = new TextRekognition();
//            textRekognition.detectText(bitmap);
            imageView.setImageBitmap(bitmap);
            convertButton.setVisibility(View.VISIBLE);
        }
    }
    public void activate(boolean data){
        if(data==false){
            constraintLayout.setAlpha((float) 0.4);
            convertButton.setClickable(false);
            galleryButton.setClickable(false);
            takePıcture.setClickable(false);
        }
        else{
            constraintLayout.setAlpha((float) 1);
            convertButton.setClickable(true);
            galleryButton.setClickable(true);
            takePıcture.setClickable(true);
        }
    }
}