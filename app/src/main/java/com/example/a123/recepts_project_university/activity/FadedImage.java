package com.example.a123.recepts_project_university.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FadedImage extends Drawable {
    private Bitmap mBitmap;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    public FadedImage(Bitmap bitmap){
        setBitmap(bitmap);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(mBitmap, null, getBounds(), mPaint);
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmap.getWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmap.getHeight();
    }

    @Override
    public void setAlpha(int i) {
        int oldAlpha = mPaint.getAlpha();
        if(i != oldAlpha){
            mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void setBitmap(Bitmap bitmap){
        mBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(mBitmap);
        final LinearGradient linearGradient = new LinearGradient(0, 0, 0, bitmap.getHeight(), 0xFF000000,0x00000000, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        canvas.drawRect(0, 0 , mBitmap.getWidth(), mBitmap.getHeight(), mPaint);
        mPaint.setShader(null);
        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
        invalidateSelf();
    }


}
