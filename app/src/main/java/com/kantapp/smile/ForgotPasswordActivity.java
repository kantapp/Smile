package com.kantapp.smile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kantapp.smile.Utils.Mobile;
import com.kantapp.smile.Utils.URL;
import com.kantapp.smile.Utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText fEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AndroidNetworking.initialize(this);
        fEmail=findViewById(R.id.fEmail);

        Button fEmailBtn=findViewById(R.id.fEmailBtn);
        fEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mobile.sendEmail(ForgotPasswordActivity.this,"Arvind Kant <billionsmile24@gmail.com>","681bef11fdd50ac8fc4e0c23b0bd4152");
                final String email=fEmail.getText().toString().trim();
                if (!Validation.isEmailValid(email))
                {
                    fEmail.setError("Invalid Email");
                }
                else
                {
                    AndroidNetworking.get(URL.RESETPASS+email)
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try
                                    {
                                        Boolean error=response.getBoolean("error");
                                        String message=response.getString("message");
                                        if (!error)
                                        {
                                            JSONObject result=response.getJSONObject("result");
                                            String username = result.getString("fullname");
                                            String token = result.getString("token");
                                            String to=username+"<"+email+">";
                                            AlertDialog.Builder builder=new AlertDialog.Builder(ForgotPasswordActivity.this);
                                            builder.setTitle("Email Send...");
                                            builder.setMessage("Please check your email  for password reset link");
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    fEmail.setText("");
                                                }
                                            });
                                            AlertDialog alertDialog=builder.create();
                                            if (Mobile.sendEmail(ForgotPasswordActivity.this,to,token))
                                            {
                                                alertDialog.show();
                                            }
                                            else
                                            {
                                                Toast.makeText(ForgotPasswordActivity.this, "Somthing wrong with mail server...\nPlease try again", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        else 
                                        {
                                            Toast.makeText(ForgotPasswordActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                        }
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
