package com.example.a123.recepts_project_university.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.a123.recepts_project_university.R;


public class Registration extends AppCompatActivity {
    private Button mRegistrate;
    private TextInputLayout mName;
    private TextInputLayout mLogin;
    private TextInputLayout mMail;
    private TextInputLayout mTelephone;
    private EditText mNameEditText;
    private EditText mLoginEditText;
    private EditText mMailEditText;
    private EditText mTelephoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }
}
