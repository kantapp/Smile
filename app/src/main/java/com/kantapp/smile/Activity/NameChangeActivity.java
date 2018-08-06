package com.kantapp.smile.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.kantapp.smile.R;

public class NameChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_change);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Name");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
