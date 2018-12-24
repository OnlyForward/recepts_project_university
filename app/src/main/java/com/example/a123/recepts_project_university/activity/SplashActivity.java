package com.example.a123.recepts_project_university.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.model.ReceiptsLab;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends AppCompatActivity {
    private static final String Image_Folder_Icon = "icon/";
    private static final String Image_Folder_Icon_FULL = "icon_full/";
    private static final String IMAGE = "image/";
    private static final String Ingredienti = "ingredienti/";
    private static final String Opisanie = "opisanie/";
    private static final String Sam_Recept = "sam_recept/";

    private String mas[] = {Image_Folder_Icon,Image_Folder_Icon_FULL,IMAGE,Ingredienti,Opisanie,Sam_Recept};

    private ImageView mImageView;

    Timer t = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (isConnectedToNetWork(getApplicationContext())) {
                askPermission();
                ReceiptsLab.get(getApplicationContext());
                for (String directory:
                     mas) {
                    File timed = new File(getExternalFilesDir(null), directory);
                    timed.mkdirs();
                }

            } else {
                finish();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.desert)).getBitmap();
        FadedImage image = new FadedImage(bitmap);
        mImageView = (ImageView) findViewById(R.id.splash_image);
        mImageView.setImageDrawable(image);
        t.schedule(timerTask, 4000);
    }

    public static boolean hasConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static boolean isConnectedToNetWork(Context context) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                connected = ni.isConnected();
            }
        }
        return connected;
    }


    private void askPermission() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent intent = new Intent(SplashActivity.this, RegestrationOrNot.class);
                            startActivity(intent);
                        } else {
                            finish();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }

                }).check();
    }

}
