package com.kantapp.smile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kantapp.smile.Utils.SP;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SP.getData(this).getToken()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        else
        {
            startActivity(new Intent(getApplicationContext(),FirstScreen.class));
            finish();
        }

        //startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
    }
}
