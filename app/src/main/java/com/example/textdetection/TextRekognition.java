package com.example.textdetection;


import android.graphics.Bitmap;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.predictions.models.TextFormatType;
import com.amplifyframework.predictions.result.IdentifyTextResult;

public class TextRekognition {
    String newdata1 = new String();
   public int datalength,booldata;
    public String data,newdata="";
    public Bitmap imagedata;
    private UsertActivity UsertActivity;
    //    FinalActivity finalActivity = new FinalActivity();
    public  int detectText(Bitmap image) {
        booldata=2;
        imagedata = image;
         Amplify.Predictions.identify(
                 TextFormatType.PLAIN,
                 image,
                 result -> {
                     IdentifyTextResult identifyResult = (IdentifyTextResult) result;
                     data=identifyResult.getFullText();
                     Log.i("MyAmplifyApp", identifyResult.getFullText());
                     datalength = data.length();
                     for(int i = 0; i < datalength ; i++){   // while counting characters if less than the length add one
                         char character = data.charAt(i); // start on the first character
                         int ascii = (int) character; //convert the first character
                         newdata = newdata+ascii+ "_";
                     }
                     System.out.println(newdata);
                     booldata=1;
                 },
                 error -> {
                     System.out.println("Burdan kapandi");
                    booldata=0;
                    System.out.println(booldata);
                 }
         );
        return booldata;
    }
}