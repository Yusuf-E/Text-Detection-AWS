package com.example.textdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.predictions.models.TextFormatType;
import com.amplifyframework.predictions.result.IdentifyTextResult;

public class FinalActivity extends AppCompatActivity {
    TextRekognition textRekognition = new TextRekognition();
    private TextView textview,asciiview;
    private static String data1,newdata1;
    private String data ,newdata="";
    private int datalength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        textview = ((TextView) findViewById(R.id.textView));
       asciiview = ((TextView) findViewById(R.id.textView2));
        Bundle data=getIntent().getExtras();
        String dataString=data.getString("asciikey");
        asciiview.setText(dataString);
        textview.setText(dataString);
//        byte[] byteArray = getIntent().getByteArrayExtra("image");
//        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//        detectText(image);

    }
//    public void detectText(Bitmap image) {
//        Amplify.Predictions.identify(
//                TextFormatType.PLAIN,
//                image,
//                result -> {
//                    IdentifyTextResult identifyResult = (IdentifyTextResult) result;
//                    data=identifyResult.getFullText();
//                    Log.i("MyAmplifyApp", identifyResult.getFullText());
//                    datalength = data.length();
//                    for(int i = 0; i < datalength ; i++){   // while counting characters if less than the length add one
//                        char character = data.charAt(i); // start on the first character
//                        int ascii = (int) character; //convert the first character
//                        newdata = newdata+ascii+ "_";
//                    }
//                    System.out.println(newdata);
//                    newdata1=newdata;
//                    textview.setText(newdata);
//                },
//                error -> Log.e("MyAmplifyApp", "Identify text failed", error)
//        );
//    }


}