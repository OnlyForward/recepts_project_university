package com.example.a123.recepts_project_university.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.a123.recepts_project_university.R;

public class RegestrationOrNot extends AppCompatActivity {

    private LinearLayout mImageView;
    private Button mRegistration;
    private Button mWhithoutRegistration;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration_or_without);
        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.hamburger)).getBitmap();
        FadedImage image = new FadedImage(bitmap);
        mImageView = (LinearLayout) findViewById(R.id.layout_background);
        mImageView.setBackground(image);

        mRegistration = (Button)findViewById(R.id.btn_registration);
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegestrationOrNot.this,Registration.class);
                startActivity(intent);
            }
        });

        mWhithoutRegistration = (Button)findViewById(R.id.btn_without_registration);
        mWhithoutRegistration.setTranslationZ(37.5f);
        mWhithoutRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegestrationOrNot.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
