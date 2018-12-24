package com.example.a123.recepts_project_university.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.model.AppSettings;

public class variantOfSorts extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    private int varSearch;
    private String[] sortVar;
    private SortVarAdapter mAdapter;
    private AppSettings mySettings;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_ok:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        mySettings.setVarSort(varSearch);
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variant_of_sorts);

        mySettings = new AppSettings(getBaseContext());
        mToolbar = (Toolbar)findViewById(R.id.toolbar_choice_var);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        sortVar = getResources().getStringArray(R.array.sort);
        varSearch = mySettings.getVarSort();
        mAdapter = new SortVarAdapter();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView)findViewById(R.id.sort_variants);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }




    private class SortVarAdapter extends RecyclerView.Adapter<SortVarHolder>{
        private SortVarHolder mHolder;

        @NonNull
        @Override
        public SortVarHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            mHolder = new SortVarHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_sort,viewGroup,false));
            return mHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SortVarHolder sortVarHolder, int i) {
            sortVarHolder.onBind(i);
        }

        @Override
        public int getItemCount() {
            return sortVar.length;
        }
    }

    private class SortVarHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mVariant;
        private ImageView mOk;

        public SortVarHolder(@NonNull View itemView) {
            super(itemView);

            mOk = (ImageView)itemView.findViewById(R.id.for_image_ok);
            mVariant = (TextView)itemView.findViewById(R.id.text_var_of_sort);
            itemView.setOnClickListener(this);
        }

        public void onBind(int position){
            mVariant.setText(sortVar[position]);
            if(position==varSearch) {
                mOk.setImageDrawable(getResources().getDrawable(R.drawable.ic_okey_black));
            }else {
                mOk.setImageDrawable(null);
            }
        }

        @Override
        public void onClick(View view) {
            ImageView image = mRecyclerView.getChildAt(varSearch).findViewById(R.id.for_image_ok);
            image.setImageDrawable(null);
            mOk.setImageDrawable(getResources().getDrawable(R.drawable.ic_okey_black));
            varSearch = getAdapterPosition();
        }
    }
}
