package com.example.a123.recepts_project_university.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.adapter.ViewPagerAdapter;
import com.example.a123.recepts_project_university.fragments.Gallery;
import com.example.a123.recepts_project_university.fragments.Preview;


public class TakePhoto extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean hide = true;
    private Gallery mGallery;

    private boolean changed;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        menu.setGroupVisible(R.id.overFlowItemsToHide, hide); // Or true to be visible
        this.invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_ok:
                Intent intent = new Intent();
                intent.putExtra("Image", mGallery.getSelectedImage());
                setResult(RESULT_OK, intent);
                finish();

        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_photo);

        changed = false;

        toolbar = (Toolbar) findViewById(R.id.gallery_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    hide = true;
                } else {
                    hide = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mGallery = new Gallery();
        adapter.addFragment(mGallery, "Gallery");
//        if(Build.VERSION.SDK_INT<21) {
            adapter.addFragment(new Preview(), "Camera");
//        }else{
//            adapter.addFragment(new CameraFragment(), "Camera");
//        }
        viewPager.setAdapter(adapter);
    }

}
