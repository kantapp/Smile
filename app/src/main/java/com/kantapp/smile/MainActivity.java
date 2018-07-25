package com.kantapp.smile;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.kantapp.smile.Utils.Mobile;
import com.kantapp.smile.Utils.SP;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public static final String TAG="MainActivity.java";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toast.makeText(this, "Welcome "+ SP.getData(this).getFullname(), Toast.LENGTH_SHORT).show();

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SP.logout(MainActivity.this);
                finish();
            }
        });

        findViewById(R.id.sendEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Boolean status = Mobile.sendEmail(MainActivity.this,"Arvind Kant <kanta1311@gmail.com>","965b63c7c73c9a61a90326b3c18962bb");
//                if (status)
//                {
//                    Toast.makeText(MainActivity.this, "Mail Send", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Somthing wrong", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }



}
