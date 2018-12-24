package com.example.a123.recepts_project_university.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import com.example.a123.recepts_project_university.R;

import java.util.regex.Pattern;


public class Registration extends AppCompatActivity implements TextWatcher {
    private Button mRegistrate;
    private TextInputLayout mName;
    private TextInputLayout mLogin;
    private TextInputLayout mMail;
    private TextInputLayout mTelephone;

    private boolean phone;
    private boolean mail;
    private boolean login;
    private boolean name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mName = findViewById(R.id.name);
        mName.getEditText().addTextChangedListener(this);


        mLogin = findViewById(R.id.login);
        mLogin.getEditText().addTextChangedListener(this);

        mMail = findViewById(R.id.myemail);
        mMail.getEditText().addTextChangedListener(this);

        mTelephone = findViewById(R.id.phone);
        mTelephone.getEditText().addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        mRegistrate = findViewById(R.id.btn_finish_registration);
        mRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmailValid(mMail.getEditText().getText())) {
                    mMail.setErrorEnabled(false);
                    mail = true;
                } else {
                    mail = false;
                    mMail.setError("email is incorrect");
                }
                if (isValidPhone(mTelephone.getEditText().getText().toString()) && mTelephone.getEditText().getText().toString().length() == 13) {
                    mTelephone.setErrorEnabled(false);
                    phone = true;
                } else {
                    phone = false;
                    mTelephone.setError("Неверный формат");
                }
                if (mLogin.getEditText().getText().toString().isEmpty()) {
                    mLogin.setError("Введите логин");
                    login = false;
                } else {
                    login = true;
                    mLogin.setErrorEnabled(false);
                }
                if (mName.getEditText().getText().toString().isEmpty()) {
                    mName.setError("Введите имя");
                    name = false;
                } else {
                    name = true;
                    mName.setErrorEnabled(false);
                }
                if (phone && login && name && mail) {
                    Intent intent = new Intent(Registration.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    public boolean isEmailValid(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isPhoneValid(CharSequence phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isValidPhone(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 6 || phone.length() > 13) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


//    @Override
//    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (v instanceof EditText) {
//                v.clearFocus();
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//
//            }
//        }
//
//        return super.dispatchTouchEvent(event);
//    }

}
