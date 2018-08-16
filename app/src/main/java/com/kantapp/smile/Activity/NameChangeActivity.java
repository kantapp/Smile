package com.kantapp.smile.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.kantapp.smile.Model.User;
import com.kantapp.smile.R;
import com.kantapp.smile.Utils.SP;
import com.kantapp.smile.Utils.URL;
import com.kantapp.smile.Utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

public class NameChangeActivity extends AppCompatActivity {

    private LinearLayout userForm;
    private ProgressBar progressBar;
    private ImageView status;
    private Button button;
    private EditText editText;
    public static final String TAG="NameChangeActivity.java";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_change);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Name");
        AndroidNetworking.initialize(this);
        userForm = findViewById(R.id.userForm);
        progressBar = findViewById(R.id.progressBar);
        status = findViewById(R.id.status);
        button = findViewById(R.id.resetName);
        editText = findViewById(R.id.newName);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString().trim();
                if (!Validation.isValidName(name)) {
                    editText.setError("Please Enter Valid Name");
                } else {
                    userForm.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(NameChangeActivity.this, "Ready to rumble", Toast.LENGTH_SHORT).show();
//                            userForm.setVisibility(View.VISIBLE);
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    },2000);

                    AndroidNetworking.post(URL.NAME_UPDATE)
                            .setPriority(Priority.HIGH)
                            .addBodyParameter("name", name)
                            .addBodyParameter("token", SP.getData(NameChangeActivity.this).getToken())
                            .build()
                            .getAsString(new StringRequestListener() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d(TAG, "onResponse: "+response);
                                    try {
                                        userForm.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                        JSONObject jsonObject = new JSONObject(response);
                                        Boolean status = jsonObject.getBoolean("error");
                                        String message = jsonObject.getString("message");

                                        Toast.makeText(NameChangeActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                        Gson gson = new Gson();
                                        if (!status) {
                                            User user = gson.fromJson(jsonObject.getJSONObject("result").toString(), User.class);
                                            SP.saveUserdetails(NameChangeActivity.this, user);
                                            onBackPressed();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {

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
