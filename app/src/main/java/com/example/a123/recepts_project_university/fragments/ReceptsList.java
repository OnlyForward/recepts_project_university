package com.example.a123.recepts_project_university.fragments;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.activity.DescriptionRecipe;
import com.example.a123.recepts_project_university.db.model.Receipt;
import com.example.a123.recepts_project_university.model.ReceiptsLab;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ReceptsList extends Fragment {

    private RecyclerView mList;
    static int a = 10;

    private List<Receipt> mReceipts;
    private AssetManager mAssetManager;
    private String[] mImages;
    private static List<String> mELements;
    private boolean isLoading;


    private ListAdapter mListAdapter;
    private GridLayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recepts_list,container,false);
        ReceiptsLab receiptsLab = ReceiptsLab.get(getActivity());
        mReceipts = receiptsLab.getReceipts();
        mELements = new ArrayList<>();
        mListAdapter = new ListAdapter();
        mLayoutManager = new GridLayoutManager(getContext(),3);
        isLoading = false;
        mAssetManager = getActivity().getAssets();
        try {
            mImages = mAssetManager.list("icon");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<18;i++){
            mELements.add(mImages[i]);
        }

        mList = (RecyclerView)view.findViewById(R.id.list_of_recepts);
        mList.setLayoutManager(mLayoutManager);
        mList.setAdapter(mListAdapter);
        mList.addOnScrollListener(scrollListener);

        return view;
    }


    public void AddELements(){
        if(a+18<mImages.length) {
            for (int i = a; i < a + 18; i++) {
                mELements.add(mImages[i]);
            }
            isLoading = false;
            a += 19;
            mListAdapter.notifyDataSetChanged();
        }
    }

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();//смотрим сколько элементов на экране
            int totalItemCount = mLayoutManager.getItemCount();//сколько всего элементов
            int firstVisibleItems = mLayoutManager.findFirstVisibleItemPosition();//какая позиция первого элемента
            int lastVisibleItems = mLayoutManager.findLastVisibleItemPosition();

            if (!isLoading) {//проверяем, грузим мы что-то или нет, эта переменная должна быть вне класса  OnScrollListener
                if ( (visibleItemCount+firstVisibleItems) >= totalItemCount) {
                    isLoading = true;//ставим флаг что мы попросили еще элемены
                    AddELements();
//                    if(loadingListener != null){
//                        loadingListener.loadMoreItems(totalItemCount);//тут я использовал калбэк который просто говорит наружу что нужно еще элементов и с какой позиции начинать загрузку
//                    }
                }
            }
        }
    };

    public class ListAdapter extends RecyclerView.Adapter<ListHolder> {

        private ListHolder mListHolder;

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            mListHolder = new ListHolder(LayoutInflater.from(getContext()).inflate(R.layout.recepts_item,viewGroup,false));
            return mListHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder view, int i) {
            view.onBind(i);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return mELements.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    public class ListHolder extends RecyclerView.ViewHolder{
        private ImageView mLiked;
        private ImageView mPicture;
        private TextView mTitle;
        private int delay = 0;


        Handler handler = new Handler();
        Runnable MyThead = new Runnable() {
            @Override
            public void run() {
                if(delay==1) {
                    Intent intent = DescriptionRecipe.newInstance(getAdapterPosition(),getContext());
                    delay = 0;
                    startActivity(intent);
                }
            }
        };
        InputStream inputStream;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            mLiked = itemView.findViewById(R.id.like_image);
            mLiked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mLiked.setImageDrawable(getResources().getDrawable(R.drawable.saved_image));
                }
            });

            mPicture = itemView.findViewById(R.id.pictures_of_recepts);
            mPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delay++;

                    if(delay == 1){
                        handler.postDelayed(MyThead, 250);

                    }else{
                        mLiked.setImageDrawable(getResources().getDrawable(R.drawable.saved_image));
                        delay = 0;
                    }
                }
            });
            mTitle = itemView.findViewById(R.id.title);
        }

        public void onBind(int position){
            try{
                inputStream = getActivity().getAssets().open("icon/"+mELements.get(position));
                Drawable d = Drawable.createFromStream(inputStream, null);
                mPicture.setImageDrawable(d);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            mTitle.setText(mReceipts.get(position).getTitle());
        }
    }


}
