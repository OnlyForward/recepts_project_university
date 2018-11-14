package com.example.a123.recepts_project_university.fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.adapter.GridImageAdapter;
import com.example.a123.recepts_project_university.model.FilePaths;
import com.example.a123.recepts_project_university.model.FileSearch;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;


public class Gallery extends Fragment {

    private GridView mGridView;
    private ImageView mGalleryImage;
    private ProgressBar mProgressBar;

    private ArrayList<String> directories;
    private int aboutAdress = 0;
    private boolean changed = false;

    private static final int GRID_COLUMS = 4;
    private String mAppend = "file:/";
     ArrayList<String> imageURL;


     public String getSelectedImage(){
         return  imageURL.get(aboutAdress);
     }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_gallery,container,false);

        mGalleryImage = (ImageView)v.findViewById(R.id.galleryImageView);
        mGridView = (GridView)v.findViewById(R.id.gridView);
        mProgressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        directories = new ArrayList<>();

        init();

        return v;
    }

    private void init(){
        FilePaths filePaths = new FilePaths();

        if(FileSearch.getDirectoryPaths(filePaths.PICTURES)!=null){
            directories = FileSearch.getDirectoryPaths(filePaths.PICTURES);
        }

        directories.add(filePaths.CAMERA);

        setUpGrid();
    }

    private void setUpGrid(){
        imageURL = getAllShownImagesPath();
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/GRID_COLUMS;
        mGridView.setColumnWidth(imageWidth);
        GridImageAdapter adapter = new GridImageAdapter(getContext(),R.layout.layout_grid_image_view,mAppend,imageURL);
        mGridView.setAdapter(adapter);

        if(imageURL.size()>0) {
            setImage(imageURL.get(0), mGalleryImage, mAppend, getContext());
        }

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setImage(imageURL.get(i), mGalleryImage, mAppend, getContext());
                aboutAdress = i;
                Log.v("AddressImage",imageURL.get(aboutAdress));
            }
        });
    }

    private void setImage(String ImageUrl, ImageView image, String append, Context context){
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//        Glide.with(context).load(append + ImageUrl).into(image);
        imageLoader.displayImage(append + ImageUrl, image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                mProgressBar.setVisibility(View.INVISIBLE);

            }
        });
    }

    public ArrayList<String> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = getActivity().getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }

}
