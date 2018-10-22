package com.example.a123.recepts_project_university.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a123.recepts_project_university.R;

import static android.content.ContentValues.TAG;

public class DialogRecepts extends DialogFragment {

    private TextView mReport;
    private TextView mSave;
    private TextView mLike;
    private TextView mCancel;

    public static final String Code_Return = "Report";
    private int answer = -1;


    DialogCallback dialogCallback = new DialogCallback() {
        @Override
        public void getResults(int results) {
            if(results!=0){
                Log.e(TAG,""+results);
                Intent intent = new Intent();
                intent.putExtra(Code_Return,results);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            }
            dismiss();
        }
    };

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_recepts,container,false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mReport = (TextView)view.findViewById(R.id.dialog_report);
        mReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogYesNo dialogYesNo = new DialogYesNo();
                dialogYesNo.setCallBack(dialogCallback);
                dialogYesNo.show(getFragmentManager(),"DialogRecepts");
                dismiss();
            }
        });

        mSave = (TextView)view.findViewById(R.id.textview_save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = 1;
                Intent intent = new Intent();
                intent.putExtra(Code_Return, answer);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        });

        mLike = (TextView)view.findViewById(R.id.textview_like);
        mLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = 1;
                Intent intent = new Intent();
                intent.putExtra(Code_Return, answer);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        });

        mCancel = (TextView)view.findViewById(R.id.dialog_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = 1;
                Intent intent = new Intent();
                intent.putExtra(Code_Return, answer);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


}
