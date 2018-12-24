package com.example.a123.recepts_project_university.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.activity.DescriptionRecipe;
import com.example.a123.recepts_project_university.activity.MainActivity;
import com.example.a123.recepts_project_university.db.model.Receipt;
import com.example.a123.recepts_project_university.model.AppDbHelper;
import com.example.a123.recepts_project_university.model.TakeDb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SavedRecipes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SavedRecipes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedRecipes extends Fragment {
    private RecyclerView mRecyclerView;
    private List<Receipt> mReceipts;


    private ListAdapter mListAdapter;
    private GridLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private OnFragmentInteractionListener mListener;

    public SavedRecipes() {
        // Required empty public constructor
    }


    public static SavedRecipes newInstance() {
        SavedRecipes fragment = new SavedRecipes();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recepts_list,container,false);

        final AppDbHelper appDbHelper = TakeDb.getAppDbHelper();

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(),R.color.colorPrimary),ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mReceipts = appDbHelper.getAllReceipts();
                        mListAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });

        mRecyclerView = (RecyclerView)view.findViewById(R.id.list_of_recepts);

        mReceipts = appDbHelper.getAllReceipts();
        mListAdapter = new ListAdapter();
        mLayoutManager = new GridLayoutManager(getContext(),3);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);

        ((MainActivity)getActivity()).setItemListener(new MainActivity.onQueryWritten() {
            @Override
            public void getReceipts(String receipts) {
                List<Receipt> timed = new ArrayList<>();
                if(receipts!=null || receipts!="") {
                    for (Receipt r :
                            appDbHelper.getAllReceipts()) {
                        if (r.getTitle().toLowerCase().contains(receipts.toLowerCase())) {
                            timed.add(r);
                        }
                    }
                    mReceipts = timed;
                }else{
                    mReceipts = appDbHelper.getAllReceipts();
                }
                mListAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

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
            return mReceipts.size();
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
                    Long key = mReceipts.get(getAdapterPosition()).getId_receipts();
                    Intent intent = DescriptionRecipe.newInstance(key,getContext());
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
                    AppDbHelper appDbHelper = TakeDb.getAppDbHelper();
                    appDbHelper.deleteReceipt(mReceipts.get(getAdapterPosition()),mReceipts.get(getAdapterPosition()).getListStep());
                    mReceipts.remove(getAdapterPosition());
                    mListAdapter.notifyDataSetChanged();
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
                inputStream = getActivity().getAssets().open("icon_full/"+mReceipts.get(position).getIcon());
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
