package com.zimo.guo;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by guolili on 15/8/26.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private Camera mCamera;
    private SurfaceHolder mHolder;

    public CameraView(Context context) {
        super(context);
        initSurfaceView();
        initSurfaceHolder();
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSurfaceView();
        initSurfaceHolder();
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSurfaceView();
        initSurfaceHolder();
    }

    private void initSurfaceHolder() {
        mHolder = this.getHolder();
        mHolder.addCallback(this);
    }

    private void initSurfaceView() {
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.setClickable(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
