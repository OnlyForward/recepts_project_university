package com.example.a123.recepts_project_university.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.dialogs.DialogRecepts;
import com.example.a123.recepts_project_university.fragments.Menu;
import com.example.a123.recepts_project_university.fragments.MenuLab;
import com.example.a123.recepts_project_university.fragments.Str;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DescriptionRecipe extends AppCompatActivity {

    private static final String ARG_MENU_ID = "menu_id";
    String s;
    private List<String> description;
    private List<String> step_for_desc;
    private String text_descriptiont;
    private String text_ingredients;
    private int dotscount;
    private ImageView[] dots;
    String[] sam_recept;
    List<String> images1;


    private Menu mMenu;
    private Toolbar mToolbar;
    private TextView mDescription;
    private TextView mStep;
    LinearLayout sliderDotspanel;
    private ViewPager mViewPager;
    private TextView mTextView;
    private TextView mIngredients;
    private ImageView mImageView;
    private ImageButton mBack;
    private ImageButton mSettings;


    private static final int REQUEST_DIALOG = 7;
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public static Intent newInstance(UUID menuId, Context context){
        Intent args = new Intent(context, DescriptionRecipe.class);
        args.putExtra(ARG_MENU_ID, menuId.toString());
        return args;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.dialog_settings){
            DialogFragment dialog = new DialogRecepts();
                dialog.show(getSupportFragmentManager(),"DescriptionRecipe");
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_description_recipes);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        //mToolbar.setSubtitle("sjaks");
        mToolbar.setTitle("");
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

        collapsingToolbarLayout.setTitle("");

        setSupportActionBar(mToolbar);

        collapsingToolbarLayout.setTitle("sjaksajk");

        //getSupportActionBar().setSubtitle("sdkjlajla");
        mToolbar.setTitle("skalksla");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        UUID menuId = (UUID) UUID.fromString(getIntent().getStringExtra(ARG_MENU_ID));
        mMenu = MenuLab.get(getApplicationContext()).getMenu(menuId);
        AssetManager manager = getAssets();
        images1 = new ArrayList<>();
        mViewPager = (ViewPager) findViewById(R.id.pager_for_images1);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        s = write_description(getAssets(),"sam_recept/recept" + mMenu.getNumber() + ".txt");
        s = s.replace("\n\n\n", "``");
        s = s.replace("\n\n", "``");
        sam_recept = Str.splitString(s, "``");
        images1 = find_images(sam_recept);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext());
        mViewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        for(int i = 0; i < dotscount; i++){
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
                    for(int i = 0; i< dotscount; i++){
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            mImageView = (ImageView)findViewById(R.id.image_detaled);
            Drawable drawable = loadDrawable(getApplicationContext().getAssets(), "icon_full/"+mMenu.getImage());
            mImageView.setImageDrawable(drawable);
            mTextView = (TextView)findViewById(R.id.text_detaled);
            text_descriptiont = write_description(getApplicationContext().getAssets(),"opisanie/opis"+mMenu.getNumber()+".txt");
            mTextView.setText(text_descriptiont);
            mIngredients = (TextView)findViewById(R.id.ingredients);
            text_ingredients = write_description(getApplicationContext().getAssets(), "ingredienti/ing"+mMenu.getNumber()+".txt");
            mIngredients.setText(text_ingredients);
            mViewPager = (ViewPager)findViewById(R.id.pager_for_images1);

    }


    private List<String> find_images(String[] s){
        String timed = "";
        List<String> images = new ArrayList<>();
        description = new ArrayList<>();
        for(int i=0;i<s.length;i++){
            if(s[i].contains(".jpg")){
                images.add(s[i]);
            }else {
                if(s[i].contains("Шаг")) {
                    timed = s[i]+" ";
                }else{
                    description.add(timed+s[i]);
                    timed = "";
                }
            }
        }
        return images;
    }


    private String write_description(AssetManager manager, String fileName){
        try{
            InputStreamReader istream = new InputStreamReader(manager.open(fileName));
            BufferedReader in = new BufferedReader(istream);
            String lines = in.readLine();
            String lines1;
            while((lines1 = in.readLine())!=null){
                if (lines1 != "\n") {
                    lines+="\n"+lines1;
                }else{
                    lines+=lines1;
                }
            }

            istream.close();
            in.close();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Drawable loadDrawable(AssetManager manager, String fileName) {
        try{
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
            return images1.size();
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
            Drawable drawable = loadDrawable(getApplicationContext().getAssets(), "image/"+images1.get(position));
            imageView.setImageDrawable(drawable);
            mDescription = (TextView)view.findViewById(R.id.description);
            mStep = (TextView)view.findViewById(R.id.step);
            if(position<images1.size()-1) {
                mDescription.setText(description.get(position));
            }else{
                mDescription.setText(description.get(position)+"\n"+description.get(position+1));
            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

    }
}


