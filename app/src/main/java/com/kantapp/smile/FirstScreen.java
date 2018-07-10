package com.kantapp.smile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FirstScreen extends Activity
{
    private int smileClick=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        final ImageView imageView=findViewById(R.id.imageSmile);
        Glide.with(this)
                .load(R.drawable.testy)
                .apply(RequestOptions
                        .circleCropTransform())
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smileClick++;
                if (smileClick==5)
                {
                    Glide.with(FirstScreen.this)
                            .load(R.drawable.testy_red)
                            .apply(RequestOptions
                                    .circleCropTransform())
                            .into(imageView);
                    smileClick=0;
                }
                else
                {
                    Glide.with(FirstScreen.this)
                            .load(R.drawable.testy)
                            .apply(RequestOptions
                                    .circleCropTransform())
                            .into(imageView);
                }

            }
        });

    }
}
