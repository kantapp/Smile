package com.kantapp.smile.Activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.JsonObject;
import com.kantapp.smile.R;
import com.kantapp.smile.Utils.SP;
import com.kantapp.smile.Utils.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class PasswordChangeActivity extends AppCompatActivity {

    private static final String TAG = "PasswordChangeActivity.java";
    private EditText pass,repass,oldPassword;
    private LinearLayout userForm;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        AndroidNetworking.initialize(this);
        pass=findViewById(R.id.newPassword);
        repass=findViewById(R.id.rePassword);
        oldPassword=findViewById(R.id.oldPassword);
        userForm = findViewById(R.id.userForm);
        progressBar = findViewById(R.id.progressBar);
        findViewById(R.id.passBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass=oldPassword.getText().toString().trim();
                String strPass=pass.getText().toString().trim();
                String strRePass=repass.getText().toString().trim();
                if(oldpass.isEmpty())
                {
                    oldPassword.setError("Old Password Require");
                }
                else if (strPass.length()<6)
                {
                    pass.setError("Password is Too weak...");
                }
                else  if (!strRePass.equals(strPass))
                {
                    repass.setError("Password Not Matched");
                }
                else
                {
                    userForm.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    AndroidNetworking.post(URL.PASSWORD_UPDATE)
                            .setPriority(Priority.HIGH)
                            .addBodyParameter("token", String.valueOf(SP.getData(PasswordChangeActivity.this).getToken()))
                            .addBodyParameter("oldpassword",oldpass)
                            .addBodyParameter("newpassword",strRePass)
                            .build()
                            .getAsString(new StringRequestListener() {
                                @SuppressLint("LongLogTag")
                                @Override
                                public void onResponse(String response) {
                                    try
                                    {
                                        userForm.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                        JSONObject jsonObject=new JSONObject(response);
                                        String message=jsonObject.getString("message");
                                        Boolean error=jsonObject.getBoolean("error");
                                        if (error)
                                        {
                                            Toast.makeText(PasswordChangeActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(PasswordChangeActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                            onBackPressed();
                                        }
                                        Log.d(TAG, "onResponse: "+message);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                        anError.printStackTrace();
                                }
                            });
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
