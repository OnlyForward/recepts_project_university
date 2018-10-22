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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends AppCompatActivity {
    private ImageView mImageView;
//    public static DatabaseHelper mDBHelper;
//    public static SQLiteDatabase mDb;

    Timer t = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if(isConnectedToNetWork(getApplicationContext())) {
                askPermission();
            }else {
                finish();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.desert)).getBitmap();
        FadedImage image = new FadedImage(bitmap);
        mImageView = (ImageView)findViewById(R.id.splash_image);
        mImageView.setImageDrawable(image);
        t.schedule(timerTask, 4000);
    }

    public static boolean hasConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    public static boolean isConnectedToNetWork(Context context){
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if(ni!=null){
                connected = ni.isConnected();
            }
        }
        return connected;
    }


    private void askPermission() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
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



//    public static String[] PostDataBase(String id, String[] colums, String table, String key){
//        Cursor cursor = CommonAction.mDb.query(table, colums, key + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        String[] information = new String[colums.length];
//        if(cursor.moveToFirst()){
//            for(int j=0;j<colums.length;j++){
//                String dd="";
//                dd=cursor.getString(j);
//                information[j] = dd;
//            }
//        }
//        cursor.close();
//        return information;
//    }
//
//    public static List<String[]> PostDataBaseList(String id, String[] colums, String table, String key){
//        List<String[]> information1 = new ArrayList<>();
//        Cursor cursor = CommonAction.mDb.query(table, colums, key + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        boolean c = cursor.moveToFirst();
//        while(c){
//            String[] information = new String[colums.length];
//            for(int j=0;j<colums.length;j++){
//                String dd="";
//                dd=cursor.getString(j);
//                information[j] = dd;
//            }
//            information1.add(information);
//            c = cursor.moveToNext();
//        }
//        cursor.close();
//        return information1;
//    }

}
