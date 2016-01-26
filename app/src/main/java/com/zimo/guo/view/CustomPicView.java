package com.zimo.guo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.zimo.guo.R;
import com.zimo.guo.util.PhotoUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zimo on 16/1/18.
 */
public class CustomPicView extends View {

    private Bitmap bitmap;
    private Paint paint;

    private int mWidth, mHeight;

    public CustomPicView(Context context) {
        super(context);

        init();
    }

    public CustomPicView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public CustomPicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.demo, options);
        options.inSampleSize = PhotoUtil.calculateInSampleSize(options, 800, 1000);
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.demo, options);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void scalePicByMatrix(float scaleWidth, float scaleHeight) {
        Matrix matrix = new Matrix();
        matrix.setScale(scaleWidth, scaleHeight);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        postInvalidate();
    }

    public void rotatePic(int angle) {
        Matrix matrix = new Matrix();
        matrix.setRotate(angle);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        postInvalidate();
    }

    public void cutPic(int reqWidth, int reqHeight) {
        if (bitmap.getWidth() > reqWidth && bitmap.getHeight() > reqHeight) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, reqWidth, reqHeight);
        } else {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    public void savePic(String path) {
        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
