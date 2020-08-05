//package com.example.textdetection;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.amplifyframework.AmplifyException;
//import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
//import com.amplifyframework.core.Amplify;
//import com.amplifyframework.predictions.aws.AWSPredictionsPlugin;
//import com.amplifyframework.predictions.models.TextFormatType;
//import com.amplifyframework.predictions.result.IdentifyTextResult;
//
//public class TextRekog extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_text_rekog);
//        try {
//            // Add these lines to add the AWSCognitoAuthPlugin and AWSPredictionsPlugin plugins
//            Amplify.addPlugin(new AWSCognitoAuthPlugin());
//            Amplify.addPlugin(new AWSPredictionsPlugin());
//            Amplify.configure(getApplicationContext());
//
//            Log.i("MyAmplifyApp", "Initialized Amplify");
//        } catch (AmplifyException error) {
//            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
//        }
//    }
//    public void detectText(Bitmap image) {
//        Amplify.Predictions.identify(
//                TextFormatType.PLAIN,
//                image,
//                result -> {
//                    IdentifyTextResult identifyResult = (IdentifyTextResult) result;
//                    Log.i("MyAmplifyApp", identifyResult.getFullText());
//                },
//                error -> Log.e("MyAmplifyApp", "Identify text failed", error)
//        );
//    }
//}