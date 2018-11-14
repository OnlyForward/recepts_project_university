package com.example.a123.recepts_project_university.model;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;


public class AppSettings {

    private static final String VARIANT_OF_SORT = "SORT_VARIANT";
    private static final String NOTIFICATIONS = "NOTIFICATIONS";

    private Theme mThemeApp;

    private int varSort;

    private boolean notifications;

    private Bitmap photo;
    private Context mContext;

    public AppSettings(Context context){
        mContext = context;
        varSort = PreferenceManager.getDefaultSharedPreferences(context).getInt(VARIANT_OF_SORT,1);
        notifications = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(NOTIFICATIONS,true);
    }

    public Theme getThemeApp() {
        return mThemeApp;
    }

    public void setThemeApp(Theme themeApp) {
        mThemeApp = themeApp;
    }

    public int getVarSort() {
        return varSort;
    }

    public void setVarSort(int varSort) {
        this.varSort = varSort;
        PreferenceManager.getDefaultSharedPreferences(mContext).edit().putInt(VARIANT_OF_SORT,varSort).apply();
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
        PreferenceManager.getDefaultSharedPreferences(mContext).edit().putBoolean(NOTIFICATIONS,notifications).apply();
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
