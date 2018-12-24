package com.example.a123.recepts_project_university.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.activity.GalleryActivity;
import com.example.a123.recepts_project_university.activity.variantOfSorts;
import com.example.a123.recepts_project_university.model.AppSettings;

import static android.app.Activity.RESULT_OK;

public class Settings extends Fragment {

    private TextView mChangePhoto;
    private ImageView mPhoto;
    private TextView mSort;
    private Spinner mTheme;
    private Switch mNotifivations;
    private String[] sorts;

    private static final int TAKE_IMAGE = 3;
    private static final int SORT_VAR = 5;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK){
            return;
        }
        if (requestCode == TAKE_IMAGE) {
            Bitmap bitmap = ((BitmapDrawable) Drawable.createFromPath(data.getStringExtra("Image"))).getBitmap();
            Glide.with(getContext()).load(data.getStringExtra("Image")).apply(RequestOptions.circleCropTransform()).into(mPhoto);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        final AppSettings settings = new AppSettings(getActivity());

        mChangePhoto = (TextView) view.findViewById(R.id.change_photo);
        mChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                startActivityForResult(intent, TAKE_IMAGE);
            }
        });

        mPhoto = (ImageView) view.findViewById(R.id.open_photo);
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mSort = (TextView) view.findViewById(R.id.type_of_sort);
        sorts = getActivity().getResources().getStringArray(R.array.sort);
        mSort.setText(sorts[settings.getVarSort()]);
        mSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), variantOfSorts.class);
                startActivity(intent);
            }
        });

        mTheme = (Spinner) view.findViewById(R.id.spinner_theme);

        mNotifivations = (Switch) view.findViewById(R.id.switch_notifications);
        mNotifivations.setChecked(settings.isNotifications());
        mNotifivations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settings.setNotifications(!settings.isNotifications());
            }
        });

        return view;
    }
}
