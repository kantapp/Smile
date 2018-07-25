package com.kantapp.smile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.kantapp.smile.Model.User;
import com.kantapp.smile.Utils.Key;
import com.kantapp.smile.Utils.Mobile;
import com.kantapp.smile.Utils.SP;
import com.kantapp.smile.Utils.URL;
import com.kantapp.smile.Utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity
{
    public static final String TAG="LoginActivity.java";
    private EditText editemail,editpassword;
    private ProgressBar progressBar;
    private LinearLayout userForm;

    private ImageView status;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AndroidNetworking.initialize(getApplicationContext());
        userForm=findViewById(R.id.userForm);
        progressBar=findViewById(R.id.progressBar);
        status=findViewById(R.id.status);
        editemail=findViewById(R.id.email);
        editpassword=findViewById(R.id.password);
        editpassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    Mobile.hideSoftKeyboard(LoginActivity.this);
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button email_sign = findViewById(R.id.email_sign);
        email_sign.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Mobile.hideSoftKeyboard(LoginActivity.this);
                attemptLogin();
            }
        });

        findViewById(R.id.forgotPassword).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });
    }

    private void attemptLogin() {
        editemail.setError(null);
        editpassword.setError(null);

        final String email=editemail.getText().toString().trim().toLowerCase();
        final String password=editpassword.getText().toString().trim().toLowerCase();
        if(!Validation.isEmailValid(email))
        {
            editemail.setError(getString(R.string.error_field_required));
        }
        else if (!Validation.isPasswordValid(password))
        {
            editpassword.setError(getString(R.string.error_invalid_password));
        }
        else
        {
//            params.put("email",email);
//            params.put("password",password);

            userForm.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            AndroidNetworking.post(URL.LOGIN)
                    .addBodyParameter("email",email)
                    .addBodyParameter("password",password)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "onResponse: "+response);
                            try
                            {
                                final JSONObject jsonObjectRoot=new JSONObject(response);
                                boolean login=jsonObjectRoot.getBoolean("error");
                                String message=jsonObjectRoot.getString("message");
                                if (!login)
                                {
                                    status.setVisibility(View.VISIBLE);
                                    status.setBackgroundResource(R.drawable.success);
                                    status.setBackgroundTintList(ContextCompat.getColorStateList(LoginActivity.this, R.color.colorPrimary));
                                    progressBar.setVisibility(View.GONE);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            status.setVisibility(View.GONE);
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                            try
                                            {
                                                User user=new User();
                                                JSONObject jsonObject=jsonObjectRoot.getJSONObject("userData");
                                                user.setId(jsonObject.getInt(User.ID));
                                                user.setEmail(jsonObject.getString(User.EMAIL));
                                                user.setFullname(jsonObject.getString(User.FULLNAME));
                                                user.setProfile(jsonObject.getString(User.PROFILE));
                                                user.setGender(jsonObject.getString(User.GENDER));
                                                user.setToken(jsonObject.getString(User.TOKEN));
                                                Log.d(TAG, "run: "+user);
                                                SP.saveUserdetails(LoginActivity.this,user);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            finish();
                                        }
                                    },700);
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                                    status.setVisibility(View.VISIBLE);
                                    status.setBackgroundResource(R.drawable.failed);
                                    status.setBackgroundTintList(ContextCompat.getColorStateList(LoginActivity.this, R.color.RED));
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            userForm.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);
                                            status.setVisibility(View.GONE);
                                            editemail.setError(getString(R.string.error_invalid_email));
                                            editpassword.setError(getString(R.string.error_incorrect_password));
                                            editpassword.requestFocus();
                                        }
                                    },500);
                                }
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            Log.d(TAG, "onErrorResponse: "+error.getMessage());
                            error.printStackTrace();
                            userForm.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    });

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



}

