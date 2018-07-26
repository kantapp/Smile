package com.kantapp.smile;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.kantapp.smile.Utils.Mobile;
import com.kantapp.smile.Utils.URL;
import com.kantapp.smile.Utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText fEmail;
    public static final String TAG="ForgotPassword.java";
    ProgressBar progressBarForgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AndroidNetworking.initialize(this);
        fEmail=findViewById(R.id.fEmail);

        progressBarForgot=findViewById(R.id.progressBarForgot);
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
                    progressBarForgot.setVisibility(View.VISIBLE);
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
                                            sendEmail(ForgotPasswordActivity.this,to,token);


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
    public  void sendEmail(final Activity activity, String to, String token) {


        String from = "postmaster@mg.kantapp.com";
        String subject = "Reset Your Password";
        String url = "https://api.mailgun.net/v3/mg.kantapp.com/messages";

        String mail = "<div>\n" +
                "        <h1 style=\"text-align: center; width: 100%\">Billion Smile</h1>\n" +
                "        <br>\n" +
                "        <h3>Forgot Your Password?</h3>\n" +
                "        \n" +
                "        <p>If you'd like to reset your password, please click the link below, or copy and paste it into your browser:</p>\n" +
                "        <a href=\"https://kantapp.com/Zeist/BillionSmile/public/newpass/" + token + "\" title=\"Reset Password\" class=\"dont-break-out\"> https://kantapp.com/Zeist/BillionSmile/public/newpass/" + token + "</a>\n" +
                "        <br>\n" +
                "        <br>\n" +
                "        <p>If token not exist then request password reset from Mobile App</p>\n" +
                "        <p>If you do not want to reset your password, please ignore this message and your password will not be changed. If you have any questions or concerns, please contact us at <b>Billion Smile Support</b></p>\n" +
                "        <br>\n" +
                "        The Pocket Team\n" +
                "    </div>";
        HashMap<String, String> param = new HashMap<>();
        param.put("from", "Billion Smile <" + from + ">");
        param.put("to", to);
        param.put("subject", subject);
        param.put("html", mail);
        AndroidNetworking.post(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Basic YXBpOmtleS05ZjU4MTkyOWI0NjhjYWQzZjEwMjZjZTBlY2FlZmFlMA==")
                .addBodyParameter(param)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if (message.equals("Queued. Thank you."))
                            {
                                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                                builder.setTitle("Email Send...");
                                builder.setMessage("Please check your email  for password reset link");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                                AlertDialog alertDialog=builder.create();
                                alertDialog.show();
                                fEmail.setText("");
                                progressBarForgot.setVisibility(View.GONE);
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
