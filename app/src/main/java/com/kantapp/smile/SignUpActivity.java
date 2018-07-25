package com.kantapp.smile;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kantapp.smile.Utils.Key;
import com.kantapp.smile.Utils.URL;
import com.kantapp.smile.Utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private EditText regFullname,regEmail,regPassword,regRepassword;
    private RadioGroup genderGroup;
    private RadioButton genderRadio;
    private  static final String TAG="SignUpActivity.java";
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AndroidNetworking.initialize(this);
        regFullname=findViewById(R.id.regFullName);
        regEmail=findViewById(R.id.regEmail);
        regPassword=findViewById(R.id.regPassword);
        regRepassword=findViewById(R.id.regRepassword);
        genderGroup=findViewById(R.id.genderGroup);
        Button button=findViewById(R.id.regSignUp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String fullname=regFullname.getText().toString().trim();
                String email=regEmail.getText().toString().toLowerCase().trim();
                String password=regPassword.getText().toString().toLowerCase().trim();
                String repassword=regRepassword.getText().toString().toLowerCase().trim();

//
//
                if (!Validation.isValidName(fullname))
                {

                    regFullname.setError("Enter valid name");
                }
                else if (!Validation.isEmailValid(email))
                {
                    regEmail.setError("Need valid email");
                }
                else if (password.length()<6)
                {
                    regPassword.setError("Need atleast 6 digit password");
                }
                else if (!repassword.equals(password))
                {
                    regRepassword.setError("Password not matched");
                }
                else if (genderGroup.getCheckedRadioButtonId()==-1)
                {

                    Toast.makeText(SignUpActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    genderRadio=findViewById(genderGroup.getCheckedRadioButtonId());
                    gender=genderRadio.getText().toString().toLowerCase();
                    if (gender.equals("male"))
                    {
                        gender="M";
                    }
                    else if (gender.equals("female"))
                    {
                        gender="F";
                    }

//                    Toast.makeText(SignUpActivity.this, ""+gender , Toast.LENGTH_SHORT).show();
                    saveToDB(fullname,email,password,gender);
                }
//

            }
        });
    }

    private void saveToDB(final String fullname, String email, String password, final String gender) {
        final ProgressBar progressBar=findViewById(R.id.progressBar);
        final ImageView imageView=findViewById(R.id.keyImage);
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(URL.REGISTER)
                .addBodyParameter(Key.FULLNAME,fullname)
                .addBodyParameter(Key.EMAIL,email)
                .addBodyParameter(Key.PASSWORD,password)
                .addBodyParameter(Key.GENDER,gender)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            Boolean status=response.getBoolean("error");

                            if (status)
                            {

                                Toast.makeText(SignUpActivity.this, ""+response.getString("message"), Toast.LENGTH_LONG).show();
                            }
                            else
                            {

                                Toast.makeText(SignUpActivity.this, ""+response.getString("message"), Toast.LENGTH_LONG).show();
                                regFullname.setText("");
                                regEmail.setText("");
                                regPassword.setText("");
                                regRepassword.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.d(TAG, "onError: "+anError.getErrorCode());
                        if (anError.getErrorCode()==0)
                        {
                            Toast.makeText(SignUpActivity.this, "Internet Not Available ", Toast.LENGTH_LONG).show();
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
}
