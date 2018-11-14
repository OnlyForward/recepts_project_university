package com.example.a123.recepts_project_university.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.fragments.Gallery;

public class GalleryActivity extends AppCompatActivity {
    private Gallery fragment;
    private Toolbar mToolbar;
    private String imagePath = "";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_ok:
                imagePath = fragment.getSelectedImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        if(!imagePath.equals("")) {
            Intent intent = new Intent();
            intent.putExtra("Image", imagePath);
            setResult(RESULT_OK, intent);
            Log.v("CameIntoProfileImage",imagePath);
        }
        Log.v("CameIntoProfileImage",imagePath);

        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_back_gallery);
        FragmentManager fm = getSupportFragmentManager();

        mToolbar = (Toolbar)findViewById(R.id.gallery_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fragment = new Gallery();

        if(fm.findFragmentById(R.id.lay_for_gallery)==null){
            fm.beginTransaction().add(R.id.lay_for_gallery,fragment).commit();
        }
    }
}
