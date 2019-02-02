package com.example.a123.recepts_project_university.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.activity.TakePhoto;
import com.example.a123.recepts_project_university.db.model.Receipt;
import com.example.a123.recepts_project_university.db.model.StepToReceipts;
import com.example.a123.recepts_project_university.model.AppDbHelper;
import com.example.a123.recepts_project_university.model.TakeDb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateRecipes extends Fragment {
    private static final int REQUST_IMAGES = 5;

    private TextInputEditText mTitle;
    private TextInputEditText mDescription;
    private RecyclerView mSteps;
    private RecyclerView mIngred;
    private Button mAddStep;
    private Button mReduceStep;
    private Button mAddIng;
    private Button mReduceIng;
    private Button mFinish;

    private List<String> mImagesList;
    private List<StepToReceipts> mDescriptionSteps;
    private List<String> mIngredients;
    private StepsAdapter mStepsAdapter;
    private IngredientsAdapter mIngredientsAdapter;
    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager mLinearLayoutManagerIngredients;
    private File mFile;
    private List<Drawable> mBitmaps;

    private static int picture = -1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK != resultCode) {
            return;
        }
        if (requestCode == REQUST_IMAGES) {
            Glide.with(getContext()).load(data.getStringExtra("Image")).into((ImageView) mSteps.getChildAt(picture).findViewById(R.id.image_of_step));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe, container, false);

        mIngredients = new ArrayList<>();
        mDescriptionSteps = new ArrayList<>();
        mBitmaps = new ArrayList<>();
        mImagesList = new ArrayList<>();

        mTitle = (TextInputEditText) view.findViewById(R.id.create_name_recipe);
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

        mDescription = (TextInputEditText) view.findViewById(R.id.create_recipes_description);
        mDescription.addTextChangedListener(new TextWatcher() {
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


        mLinearLayoutManagerIngredients = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mIngred = (RecyclerView) view.findViewById(R.id.ingredients_recipes_recycler);
        mIngred.setLayoutManager(mLinearLayoutManagerIngredients);
        mIngredientsAdapter = new IngredientsAdapter();
        mIngred.setAdapter(mIngredientsAdapter);

        mAddIng = (Button) view.findViewById(R.id.add_ingredient);
        mAddIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIngredients.add("");
                mIngredientsAdapter.notifyDataSetChanged();
            }
        });

        mReduceIng = (Button) view.findViewById(R.id.reduce_ingredient);
        mReduceIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIngredients.size() > 0) {
                    mIngredients.remove(mIngredients.size() - 1);
                    mIngredientsAdapter.notifyDataSetChanged();
                }
            }
        });


        mLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mStepsAdapter = new StepsAdapter();
        mSteps = (RecyclerView) view.findViewById(R.id.steps_recipes_recycler);
        mSteps.setAdapter(mStepsAdapter);
        mSteps.setLayoutManager(mLayoutManager);

        mAddStep = (Button) view.findViewById(R.id.add_step);
        mAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImagesList.add("");
                mBitmaps.add(null);
                mDescriptionSteps.add(new StepToReceipts());
                mStepsAdapter.notifyDataSetChanged();
            }
        });

        mReduceStep = (Button) view.findViewById(R.id.reduce_step);
        mReduceStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImagesList.size() != 0) {
                    mBitmaps.remove(mDescriptionSteps.size() - 1);
                    mSteps.removeViewAt(mImagesList.size() - 1);
                    mImagesList.remove(mImagesList.size() - 1);
                    mDescriptionSteps.remove(mDescriptionSteps.size() - 1);
                    mStepsAdapter.notifyDataSetChanged();
                }
            }
        });

        mFinish = (Button) view.findViewById(R.id.btn_send_recipe);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Receipt receipt = new Receipt();
                mImagesList.set(0, getActivity().getExternalFilesDir(null) + "/image_icon/pic1.jpg");
                receipt.setTitle(mTitle.getEditableText().toString());
                receipt.setDescription(mDescription.getEditableText().toString());
                receipt.setIngredients("djasl");
                receipt.setImageMain(mImagesList.get(0));
                receipt.setIcon(getActivity().getExternalFilesDir(null) + "/image_icon/pic1.jpg");
                AppDbHelper appDbHelper = TakeDb.getAppDbHelper();
                long key = appDbHelper.saveReceipts(receipt);
//                for (int i = 0; i < mDescriptionSteps.size(); i++) {
//                    mDescriptionSteps.get(i).setId_receipts(key);
//                    appDbHelper.saveSteps(mDescriptionSteps.get(i));
//                }
                try {
                    for (int i = 0; i < mDescriptionSteps.size(); i++) {
                        saveRecept("/image/", i, key);
                    }

                } catch (IOException e) {
                    Log.i("IOexception", e.getMessage());
                }
            }

        });

        return view;
    }

    private void saveRecept(String path, int number, Long id) throws IOException {
        mBitmaps.set(0, ((ImageView) mSteps.getChildAt(number).findViewById(R.id.image_of_step)).getDrawable());
        String uuid = UUID.randomUUID().toString() + ".jpg";///icon/
        mFile = new File(getActivity().getExternalFilesDir(null) + path, uuid);

//        mDescriptionSteps.get(number).setImageDesciption(((TextView) mSteps.getChildAt(number).findViewById(R.id.text_var_of_sort)).getText().toString());
        mDescriptionSteps.get(number).setImageToStep(getActivity().getExternalFilesDir(null) + path + "image"+uuid);
        mDescriptionSteps.get(number).setId_receipts(id);


//      galleryAddPic(mCurrentPath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) mBitmaps.get(0)).getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 200, true);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        FileOutputStream output = new FileOutputStream(mFile);
        output.write(byteArray);
    }
//
//    private void galleryAddPic(String mCurrentPhotoPath) {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(mCurrentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        getActivity().sendBroadcast(mediaScanIntent);
//        Intent intent = new Intent();
//        intent.putExtra("Image", contentUri.toString());
//        getActivity().setResult(Activity.RESULT_OK,intent);
//        getActivity().finish();
//    }

    private class StepsAdapter extends RecyclerView.Adapter<StepsHolder> {

        private StepsHolder mStepsHolder;

        @NonNull
        @Override
        public StepsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            mStepsHolder = new StepsHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_steps, viewGroup, false));
            return mStepsHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull StepsHolder stepsHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return mDescriptionSteps.size();
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

    private class StepsHolder extends RecyclerView.ViewHolder {

        private TextInputEditText mDescription;
        private ImageView mImage;

        public StepsHolder(@NonNull View itemView) {
            super(itemView);

            mDescription = (TextInputEditText) itemView.findViewById(R.id.description_of_the_step);
            mDescription.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mDescriptionSteps.get(getAdapterPosition()).setImageDesciption(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            mImage = (ImageView) itemView.findViewById(R.id.image_of_step);
            mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), TakePhoto.class);
                    picture = getAdapterPosition();
                    startActivityForResult(intent, REQUST_IMAGES);
                }
            });
        }

    }

    public class IngredientsHolder extends RecyclerView.ViewHolder {

        private Spinner mIngred;
        private Spinner mMeasure;
        private TextInputEditText mEditText;

        public IngredientsHolder(@NonNull View itemView) {
            super(itemView);
            mIngred = (Spinner) itemView.findViewById(R.id.spinner_of_ing);
            mMeasure = (Spinner) itemView.findViewById(R.id.spinner_of_ensure);
            mEditText = (TextInputEditText) itemView.findViewById(R.id.edit_count_ing);
        }

        public void onBind() {

        }
    }

    public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsHolder> {

        IngredientsHolder mIngredientsHolder;

        @NonNull
        @Override
        public IngredientsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            mIngredientsHolder = new IngredientsHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_ingredient, viewGroup, false));
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
