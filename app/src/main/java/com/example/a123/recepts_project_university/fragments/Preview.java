package com.example.a123.recepts_project_university.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a123.recepts_project_university.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Preview extends Fragment implements SurfaceHolder.Callback, Camera.PictureCallback, Camera.PreviewCallback, Camera.AutoFocusCallback, View.OnClickListener
{
    Camera mCamera;
    SurfaceView mLayout;
    private ImageView mButton;
    private SurfaceHolder mHolder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preview,container,false);
        mCamera = switch_on_camera();
//        startFaceDetection();
        mLayout = (SurfaceView)view.findViewById(R.id.myCamPreview);
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mHolder = mLayout.getHolder();
        mHolder.addCallback(this);
        //mLayout.addView(mShowCamera);

        mButton = (ImageView) view.findViewById(R.id.make_photo);
        mButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
            mCamera.autoFocus(this);
    }



    @Override
    public void onAutoFocus(boolean paramBoolean, Camera paramCamera)
    {
        if (paramBoolean)
        {
            Toast.makeText(getContext(),"sjdljlkdajl",Toast.LENGTH_SHORT).show();
            paramCamera.takePicture(null, null, null, this);
        }
    }

    @Override
    public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera)
    {
    }


    @Override
    public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera)
    {

        new SaveInBackground().execute(paramArrayOfByte);
        mCamera.startPreview();
    }

    private void galleryAddPic(String mCurrentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
        Intent intent = new Intent();
        intent.putExtra("Image", contentUri.toString());
        getActivity().setResult(Activity.RESULT_OK,intent);
        getActivity().finish();
    }


    class SaveInBackground extends AsyncTask<byte[], String, String> {
        @Override
        protected String doInBackground(byte[]... arrayOfByte) {
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(arrayOfByte[0], 0, arrayOfByte[0].length);
                MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "myImage" , "");
                String mCurrentPath = String.format("/sdcard/DCIM/Camera/%d.jpg", System.currentTimeMillis());
                FileOutputStream os = new FileOutputStream(mCurrentPath);
                os.write(arrayOfByte[0]);
                galleryAddPic(mCurrentPath);
                os.close();
            } catch (Exception e) {
                //
            }

            return(null);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private Camera switch_on_camera() {
        Camera camera = null;
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        if(this.getResources().getConfiguration().orientation!= Configuration.ORIENTATION_LANDSCAPE){
            parameters.set("orientation","portrait");
            camera.setDisplayOrientation(90);
            parameters.setRotation(90);
        }else{
            parameters.set("orientation","landscape");
            camera.setDisplayOrientation(0);
            parameters.setRotation(0);
        }
        return camera;
    }

    public void startFaceDetection(){
        Camera.Parameters params = mCamera.getParameters();
        if (params.getMaxNumDetectedFaces() > 0){
            mCamera.startFaceDetection();
        }
    }
}
