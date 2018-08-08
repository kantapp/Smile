package com.kantapp.smile.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kantapp.smile.R;
import com.kantapp.smile.Utils.Validation;

public class NameChangeActivity extends AppCompatActivity {

    private LinearLayout userForm;
    private ProgressBar progressBar;
    private ImageView status;
    private Button button;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_change);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Name");

        userForm=findViewById(R.id.userForm);
        progressBar=findViewById(R.id.progressBar);
        status=findViewById(R.id.status);
        button=findViewById(R.id.resetName);
        editText=findViewById(R.id.newName);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editText.getText().toString().trim();
                if (!Validation.isValidName(name))
                {
                    editText.setError("Please Enter Valid Name");
                }
                else
                {
                    userForm.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NameChangeActivity.this, "Ready to rumble", Toast.LENGTH_SHORT).show();
                            userForm.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    },2000);
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
