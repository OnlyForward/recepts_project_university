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
import com.example.a123.recepts_project_university.activity.Gallery;
import com.example.a123.recepts_project_university.activity.variantOfSorts;

public class Settings extends Fragment {

    private TextView mChangePhoto;
    private ImageView mPhoto;
    private TextView mSort;
    private Spinner mTheme;
    private Switch mNotifivations;
    private int sortDefalut = 0;

    private static final int TAKE_IMAGE = 3;
    private static final int SORT_VAR = 5;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==TAKE_IMAGE){
//            Bitmap _bitmap = BitmapFactory.decodeByteArray(
//                    data.getByteArrayExtra("byteArray"),0,data.getByteArrayExtra("byteArray").length);
//            RoundedImageDrawable drawable = new RoundedImageDrawable(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.hamburger),90);
//            mPhoto.setImageDrawable(Drawable.createFromPath(data.getStringExtra("Image")));
//            mPhoto.setImageURI(Uri.parse(data.getStringExtra("Image")));
//            mPhoto.setImageBitmap(_bitmap);
            Bitmap bitmap = ((BitmapDrawable)Drawable.createFromPath(data.getStringExtra("Image"))).getBitmap();
            Glide.with(getContext()).load(data.getStringExtra("Image")).apply(RequestOptions.circleCropTransform()).into(mPhoto);
//            mPhoto.setImageBitmap(ImageHelper.getRoundedCornerBitmap(bitmap,6000));
        }else if(requestCode==SORT_VAR){
            sortDefalut = data.getIntExtra("Sort",0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        mChangePhoto = (TextView)view.findViewById(R.id.change_photo);
        mChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Gallery.class);
                startActivityForResult(intent,TAKE_IMAGE);
            }
        });

        mPhoto = (ImageView)view.findViewById(R.id.open_photo);
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mSort = (TextView)view.findViewById(R.id.type_of_sort);
        mSort.setText(getActivity().getResources().getStringArray(R.array.sort)[sortDefalut]);
        mSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),variantOfSorts.class);
                intent.putExtra("Variant_Of_search",sortDefalut);
                startActivityForResult(intent, SORT_VAR);
            }
        });

        mTheme = (Spinner)view.findViewById(R.id.spinner_theme);

        mNotifivations = (Switch)view.findViewById(R.id.switch_notifications);
        mNotifivations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        return view;
    }
}
