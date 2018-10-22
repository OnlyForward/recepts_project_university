package com.example.a123.recepts_project_university.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

public class RoundedImageDrawable extends Drawable {

        private Bitmap mBitmap;
        private int mRadius;
        private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final RectF mDestinationRectF = new RectF();

        public RoundedImageDrawable(Bitmap bitmap, int radius) {
                mRadius = radius;
                setBitmap(bitmap);
                }

        @Override
        public void draw(Canvas canvas) {
                canvas.drawRoundRect(mDestinationRectF, mRadius, mRadius, mPaint);
                }

        @Override
        public int getIntrinsicHeight() {
                return mBitmap.getHeight();
                }

        @Override
        public int getIntrinsicWidth() {
                return mBitmap.getWidth();
                }

        @Override
        public int getOpacity() {
                return PixelFormat.TRANSLUCENT;
                }

        @Override
        public void setAlpha(int alpha) {
                int oldAlpha = mPaint.getAlpha();
                if (alpha != oldAlpha) {
                mPaint.setAlpha(alpha);
                invalidateSelf();
                        }
                }

        public void setBitmap(@NonNull Bitmap bitmap) {
                mBitmap = bitmap;
        final Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                mPaint.setShader(shader);
                invalidateSelf();
                }

        @Override
        public void setColorFilter(ColorFilter cf) {
                mPaint.setColorFilter(cf);
                invalidateSelf();
                }

        @Override
        protected void onBoundsChange(Rect bounds) {
                mDestinationRectF.set(bounds);
                }

}

