package com.example.a123.recepts_project_university.model;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckEdit {

    public boolean isEmailValid(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhone(String phone)
    {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone))
        {
            if(phone.length() < 6 || phone.length() > 13)
            {
                check = false;

            }
            else
            {
                check = true;

            }
        }
        else
        {
            check=false;
        }
        return check;
    }


//    etPhoneNumber.addTextChangedListener(new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            int digits = etPhoneNumber.getText().toString().length();
//            if (digits > 1)
//                lastChar = etPhoneNumber.getText().toString().substring(digits-1);
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            int digits = etPhoneNumber.getText().toString().length();
//            Log.d("LENGTH",""+digits);
//            if (!lastChar.equals("-")) {
//                if (digits == 3 || digits == 7) {
//                    etPhoneNumber.append("-");
//                }
//            }
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    });

}
