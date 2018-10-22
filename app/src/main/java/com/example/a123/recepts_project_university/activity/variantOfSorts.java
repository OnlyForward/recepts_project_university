package com.example.a123.recepts_project_university.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a123.recepts_project_university.R;

public class variantOfSorts extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;


    private boolean choice;
    private int varSearch;
    private int sortDefault;
    private String[] sortVar;
    private SortVarAdapter mAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_ok:
                choice = true;
                break;
        }

        return true;
    }

    @Override
    public void finish() {
        super.finish();
        Intent intent = new Intent();
        if(choice) {
            intent.putExtra("Sort", varSearch);
        }else {
            intent.putExtra("Sort",sortDefault);
        }
        setResult(RESULT_OK,intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variant_of_sorts);

        mToolbar = (Toolbar)findViewById(R.id.toolbar_choice);

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

        varSearch = getIntent().getIntExtra("Variant_Of_search",-1);
        sortDefault = varSearch;


        mAdapter = new SortVarAdapter();

        mRecyclerView = (RecyclerView)findViewById(R.id.sort_variants);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
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

    private class SortVarHolder extends RecyclerView.ViewHolder{

        private TextView mVariant;

        public SortVarHolder(@NonNull View itemView) {
            super(itemView);
            mVariant = (TextView)itemView.findViewById(R.id.text_var_of_sort);
            mVariant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVariant.setCompoundDrawables(null,null,getResources().getDrawable(R.drawable.ic_okey),null);
                    varSearch = getAdapterPosition();
                }
            });
        }

        public void onBind(int position){
            mVariant.setText(sortVar[position]);
        }
    }
}
