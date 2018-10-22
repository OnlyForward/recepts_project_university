package com.example.a123.recepts_project_university.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.model.FilePaths;
import com.example.a123.recepts_project_university.model.FileSearch;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;


public class Gallery extends AppCompatActivity {

    private GridView mGridView;
    private ImageView mGalleryImage;
    private ProgressBar mProgressBar;
    private TextView title;
    private ImageView mClose;
    private TextView next;
    private ImageView imagePreview;
    private ImageButton mBack;
    private Toolbar mToolbar;

    private ArrayList<String> directories;
    private int aboutAdress = 0;
    private boolean changed = false;

    private static final int GRID_COLUMS = 4;
    private String mAppend = "file:/";
     ArrayList<String> imageURL;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_ok:
                if(!changed) {
                    changed = true;
                    finish();
                }
        }

        return true;
    }

    @Override
    public void finish() {
        Intent _intent = new Intent();
//        Bitmap _bitmap = ((BitmapDrawable)mGalleryImage.getDrawable()).getBitmap() ; // your bitmap
//        ByteArrayOutputStream _bs = new ByteArrayOutputStream();
//        _bitmap.compress(Bitmap.CompressFormat.PNG, 50, _bs);
//        _intent.putExtra("byteArray", _bs.toByteArray());
        if(changed) {
            _intent.putExtra("Image", imageURL.get(aboutAdress));
        }else{
            _intent.putExtra("Image","");
        }
        setResult(RESULT_OK, _intent);
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mToolbar = findViewById(R.id.gallery_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mGalleryImage = (ImageView)findViewById(R.id.galleryImageView);
        mGridView = (GridView)findViewById(R.id.gridView);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        directories = new ArrayList<>();
//        mBack = (ImageButton) findViewById(R.id.btn_back);
//        mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        init();
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
        GridImageAdapter adapter = new GridImageAdapter(this,R.layout.layout_grid_image_view,mAppend,imageURL);
        mGridView.setAdapter(adapter);

        if(imageURL.size()>0) {
            setImage(imageURL.get(0), mGalleryImage, mAppend, getApplicationContext());
        }

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setImage(imageURL.get(i), mGalleryImage, mAppend, getApplicationContext());
                aboutAdress = i;
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

        cursor = getContentResolver().query(uri, projection, null,
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
