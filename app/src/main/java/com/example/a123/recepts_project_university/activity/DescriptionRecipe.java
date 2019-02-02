package com.example.a123.recepts_project_university.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.db.model.Receipt;
import com.example.a123.recepts_project_university.dialogs.DialogRecepts;
import com.example.a123.recepts_project_university.model.AppDbHelper;
import com.example.a123.recepts_project_university.model.ReceiptsLab;
import com.example.a123.recepts_project_university.model.TakeDb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DescriptionRecipe extends AppCompatActivity {
    private static final String ARG_MENU_ID = "menu_id";

    private int dotscount;
    private Receipt mReceipt;
    private boolean saved;
    private int answer;

    private ImageView[] dots;
    private Toolbar mToolbar;
    private TextView mDescription;
    private LinearLayout sliderDotspanel;
    private ViewPager mViewPager;
    private TextView mTextView;
    private TextView mIngredients;
    private ImageView mImageView;

    private DialogRecepts dialog;


    private static final int REQUEST_DIALOG = 7;

    public static Intent newInstance(int menuId, Context context) {
        Intent args = new Intent(context, DescriptionRecipe.class);
        args.putExtra(ARG_MENU_ID, menuId);
        args.putExtra("Saved", false);
        return args;
    }

    public static Intent newInstance(Long menuId, Context context) {
        Intent args = new Intent(context, DescriptionRecipe.class);
        args.putExtra(ARG_MENU_ID, menuId);
        args.putExtra("Saved", true);
        return args;
    }

    public void load_image_from_path(int pos){
        File file = new File(mReceipt.getListStep().get(pos).getImageToStep());
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + file.getAbsolutePath());
        intent.setDataAndType(uri,"image/*");
        //startActivity(intent);
       //Glide
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.dialog_settings) {
            dialog.show(getSupportFragmentManager(), "DescriptionRecipe");
            Toast.makeText(getApplicationContext(),String.valueOf(answer),Toast.LENGTH_SHORT).show();
        }

        if (item.getItemId() == R.id.save_receipt_toolbar && !saved) {
            AppDbHelper appDbHelper = TakeDb.getAppDbHelper();
            Long key = appDbHelper.saveReceipts(mReceipt);
            for (int i = 0; i < mReceipt.getListStep().size(); i++) {
                mReceipt.getListStep().get(i).setId_receipts(key);
                appDbHelper.saveSteps(mReceipt.getListStep().get(i));
            }

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_description_recipes);

        dialog = new DialogRecepts();
        dialog.setItemListener(new DialogRecepts.onItemChosen() {
            @Override
            public void getResults(int item) {
                answer = item;
                Toast.makeText(getApplicationContext(),String.valueOf(answer),Toast.LENGTH_SHORT).show();
            }
        });

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        saved = getIntent().getBooleanExtra("Saved", false);
        if (!saved) {
            int menuId = getIntent().getIntExtra(ARG_MENU_ID, 0);
            mReceipt = ReceiptsLab.get(getApplicationContext()).getReceipt(menuId);
        } else {
            AppDbHelper appDbHelper = TakeDb.getAppDbHelper();
            Long menuId = getIntent().getLongExtra(ARG_MENU_ID, 0);
            mReceipt = appDbHelper.getReceipt(menuId);
            mReceipt.setListStep(appDbHelper.getSteps(menuId));
        }

        getSupportActionBar().setTitle(mReceipt.getTitle());

        mViewPager = (ViewPager) findViewById(R.id.pager_for_images1);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext());
        mViewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mImageView = (ImageView) findViewById(R.id.image_detaled);
        Drawable drawable = loadDrawable(getApplicationContext().getAssets(), "icon_full/" + mReceipt.getIcon());
        mImageView.setImageDrawable(drawable);
//        Toast.makeText(getApplicationContext(), mReceipt.getIcon(), Toast.LENGTH_SHORT).show();
//        BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
//        bitmapFactoryOptions.inJustDecodeBounds = true;
//        bitmapFactoryOptions.inSampleSize = calculate_in_sample_size(bitmapFactoryOptions, 150, 200);
//        bitmapFactoryOptions.inJustDecodeBounds = false;
//        Bitmap bmp = BitmapFactory.decodeFile(mReceipt.getIcon(), bitmapFactoryOptions);
//        mImageView.setImageBitmap(bmp);

        mTextView = (TextView) findViewById(R.id.text_detaled);
        mTextView.setText(mReceipt.getDescription());

        mIngredients = (TextView) findViewById(R.id.ingredients);
        mIngredients.setText(mReceipt.getIngredients());

        mViewPager = (ViewPager) findViewById(R.id.pager_for_images1);
    }

    private int calculate_in_sample_size(BitmapFactory.Options bitmapFactoryOptions, int reqHeght, int reqWidth) {
        final int height = bitmapFactoryOptions.outHeight;
        int width = bitmapFactoryOptions.outWidth;
        int size_sample = 1;

        if (height > reqHeght || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while ((halfHeight / size_sample) >= reqHeght && (halfWidth / size_sample) >= reqWidth) {
                size_sample *= 2;
            }
        }
        return size_sample;
    }

    public Drawable loadDrawable(AssetManager manager, String fileName) {
        try {
            InputStream in = manager.open(fileName);
            Drawable drawable = Drawable.createFromStream(in, null);
            in.close();
            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private class ViewPagerAdapter extends PagerAdapter {

        private Context context;
        private LayoutInflater layoutInflater;


        public ViewPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return mReceipt.getListStep().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.for_second_view_pager, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_for_recepts);
            if(!mReceipt.getListStep().get(position).getImageToStep().contains("image")) {
                Drawable drawable = loadDrawable(getApplicationContext().getAssets(), "image/" + mReceipt.getListStep().get(position).getImageToStep());
                imageView.setImageDrawable(drawable);
            }else{
                Glide.with(getApplicationContext()).load(mReceipt.getListStep().get(position).getImageToStep()).into(imageView);

            }
            mDescription = (TextView) view.findViewById(R.id.description);
            mDescription.setText(mReceipt.getListStep().get(position).getImageDesciption());
            ViewPager vp = (ViewPager) container;

            vp.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ViewPager vp = (ViewPager) container;
            View view = (View) object;
            vp.removeView(view);
        }
    }

}


