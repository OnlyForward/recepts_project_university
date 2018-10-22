package com.example.a123.recepts_project_university.dialogs;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a123.recepts_project_university.R;

public class DialogYesNo extends DialogFragment {

    private Button mYes;
    private Button mNo;

    private int answer = 2;

    DialogCallback dialogCallback;

    public DialogFragment setCallBack(DialogCallback dialogCallback){
        this.dialogCallback = dialogCallback;
        return this;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        dialogCallback.getResults(answer);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_yes_no,container,false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mYes = (Button)view.findViewById(R.id.Yes);
        mYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = 3;
                dismiss();
            }
        });

        mNo = (Button)view.findViewById(R.id.No);
        mNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = 2;
                dismiss();
            }
        });

        return view;
    }
}
