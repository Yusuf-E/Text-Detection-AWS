package com.example.textdetection;


import android.graphics.Bitmap;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.predictions.models.TextFormatType;
import com.amplifyframework.predictions.result.IdentifyTextResult;
public class TextRekognition {
    private int datalength;
    private String data,newdata="";

    public void detectText(Bitmap image) {
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
                },
                error -> Log.e("MyAmplifyApp", "Identify text failed", error)
        );
    }
}






