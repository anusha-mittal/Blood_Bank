package com.example.blood_bank.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.blood_bank.R;

public class Splash_Screen extends AppCompatActivity {
      //added .png image to drawable and added that image as backgroud in activity_splash_screen and then in values/styles.xml
    //changed to NoActionBar to remove the bar
    //In androidManifest.xml added intent filter to splashscreen activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash_Screen.this,LoginActivity.class));
            }
        },2500);
    }
}
