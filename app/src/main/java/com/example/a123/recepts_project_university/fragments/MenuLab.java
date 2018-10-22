package com.example.a123.recepts_project_university.fragments;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.a123.recepts_project_university.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MenuLab {
    private static final String TAG = "ImagePath";
    private static final String Image_Folder = "icon";

    private AssetManager mAssetManager;
    private List<Menu> mMenus;

    private static MenuLab sMenuLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private String[] nazvanie;
    private String[] icon_name;

    public List<Menu> getMenus() {
        return mMenus;
    }




    public Menu getMenu(UUID id){
        for(Menu menu:mMenus){
            if(menu.getUUID().equals(id)){
                return menu;
            }
        }
        return null;
    }

    public static MenuLab get(Context context){
        if(sMenuLab == null){
            sMenuLab = new MenuLab(context);
        }
        return sMenuLab;
    }

    private MenuLab(Context context){
        mContext = context.getApplicationContext();
        mMenus=new ArrayList<>();
        mAssetManager = context.getAssets();
        nazvanie = context.getResources().getStringArray(R.array.spisok_nazvaniy);
        icon_name = context.getResources().getStringArray(R.array.icon_name);
        loadImage();
    }



    private void loadImage(){
        String[] ImageNames = null;
        try {
            ImageNames = mAssetManager.list(Image_Folder);
            Log.i(TAG, "В файле "+ImageNames.length);
            for (int i=0;i<icon_name.length;i++) {
                Menu menu = new Menu();
                menu.setImage(icon_name[i]);
                menu.setName(nazvanie[i]);
                menu.setNumber(i);
                mMenus.add(menu);
            }
        }catch (IOException ioe){
            Log.e(TAG, "Не найдено", ioe);
        }
    }



}
