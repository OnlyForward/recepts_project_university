package com.example.a123.recepts_project_university.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.activity.Gallery;

import java.util.ArrayList;
import java.util.List;

public class CreateRecipes extends Fragment {
    private TextInputEditText mTitle;
    private RecyclerView mSteps;
    private RecyclerView mIngred;
    private Button mAddStep;
    private Button mReduceStep;
    private Button mAddIng;
    private Button mReduceIng;

    private  List<String> mImage;
    private  List<String> mDescriptionSteps;
    private List<String> mIngredients = new ArrayList<>();

    private int countOfIng = 0;

    private StepsAdapter mStepsAdapter;
    private IngredientsAdapter mIngredientsAdapter;
    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager mLinearLayoutManagerIngredients;

    private static int picture = -1;

    private static final int REQUST_IMAGES = 5;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUST_IMAGES){
            Glide.with(getContext()).load(data.getStringExtra("Image")).into((ImageView) mSteps.getChildAt(picture).findViewById(R.id.image_of_step));

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe,container,false);

        mTitle = (TextInputEditText)view.findViewById(R.id.create_name_recipe);
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mImage = new ArrayList<>();
        mDescriptionSteps = new ArrayList<>();


        mIngred = (RecyclerView)view.findViewById(R.id.ingredients_recipes_recycler);
        mIngred.setLayoutManager(mLinearLayoutManagerIngredients);
        mIngredientsAdapter = new IngredientsAdapter();
        mIngred.setAdapter(mIngredientsAdapter);

        mAddIng = (Button)view.findViewById(R.id.add_ingredient);
        mAddIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIngredients.add("");
                mIngredientsAdapter.notifyDataSetChanged();
            }
        });

        mReduceIng = (Button)view.findViewById(R.id.reduce_ingredient);
        mReduceIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIngredients.remove(mIngredients.size()-1);
                mIngredientsAdapter.notifyDataSetChanged();
            }
        });

        mLinearLayoutManagerIngredients = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mStepsAdapter = new StepsAdapter();
        mSteps = (RecyclerView)view.findViewById(R.id.steps_recipes_recycler);
        mSteps.setAdapter(mStepsAdapter);
        mSteps.setLayoutManager(mLayoutManager);

        mAddStep = (Button)view.findViewById(R.id.add_step);
        mAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImage.add("");
                mDescriptionSteps.add("");
                mStepsAdapter.notifyDataSetChanged();
            }
        });

        mReduceStep = (Button)view.findViewById(R.id.reduce_step);
        mReduceStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mImage.size()!=0){
                    mSteps.removeViewAt(mImage.size()-1);
                    mImage.remove(mImage.size()-1);
                    mDescriptionSteps.remove(mDescriptionSteps.size()-1);
                    mStepsAdapter.notifyDataSetChanged();
                }
            }
        });



        return view;
    }

    private class StepsAdapter extends RecyclerView.Adapter<StepsHolder>{

        private StepsHolder mStepsHolder;

        @NonNull
        @Override
        public StepsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            mStepsHolder = new StepsHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_steps,viewGroup,false));
            return mStepsHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull StepsHolder stepsHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return mImage.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    private class StepsHolder extends RecyclerView.ViewHolder{

        private TextInputEditText mDescription;
        private ImageView mImage;

        public StepsHolder(@NonNull View itemView) {
            super(itemView);

            mDescription = (TextInputEditText)itemView.findViewById(R.id.description_of_the_step);
            mDescription.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mDescriptionSteps.set(getAdapterPosition(),charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            mImage = (ImageView)itemView.findViewById(R.id.image_of_step);
            mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),Gallery.class);
                    picture = getAdapterPosition();
                    startActivityForResult(intent,REQUST_IMAGES);
                }
            });
        }

    }

    public class IngredientsHolder extends RecyclerView.ViewHolder{

        private Spinner mIngred;
        private Spinner mMeasure;
        private TextInputEditText mEditText;

        public IngredientsHolder(@NonNull View itemView) {
            super(itemView);
            mIngred = (Spinner)itemView.findViewById(R.id.spinner_of_ing);
            mMeasure = (Spinner)itemView.findViewById(R.id.spinner_of_ensure);
            mEditText = (TextInputEditText)itemView.findViewById(R.id.edit_count_ing);
        }

        public void onBind(){

        }
    }

    public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsHolder>{

        IngredientsHolder mIngredientsHolder;

        @NonNull
        @Override
        public IngredientsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            mIngredientsHolder = new IngredientsHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_ingredient,viewGroup,false));
            return mIngredientsHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull IngredientsHolder ingredientsHolder, int i) {
            ingredientsHolder.onBind();
        }

        @Override
        public int getItemCount() {
            return mIngredients.size();
        }
    }
}
