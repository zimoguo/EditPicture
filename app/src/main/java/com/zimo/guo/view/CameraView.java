package com.zimo.guo.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zimo.guo.util.CameraUtil;

import java.io.IOException;

/**
 * Created by zimo on 15/8/26.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private Camera mCamera;
    private SurfaceHolder mHolder;

    private boolean islargeResolution = true;

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

    /**
     * 初始化surfaceHolder
     */
    private void initSurfaceHolder() {
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        mHolder.setKeepScreenOn(true);
    }

    /**
     * 初始化view
     */
    private void initSurfaceView() {
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.setClickable(true);
    }

    /**
     * 判断手机是否有照相功能
     *
     * @param context
     * @return
     */
    public boolean hasCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * 初始化相机
     */
    public void initCamera() {
        if (mCamera == null) {
            mCamera = Camera.open();
        }
    }

    /**
     * 图片预览
     *
     * @param holder
     */
    public void startPreview(SurfaceHolder holder) {

        try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(holder);
                mCamera.setDisplayOrientation(90);
                mCamera.startPreview();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自动对焦
     *
     * @param isFocus
     */
    public void autoFocus(boolean isFocus) {
        if (mCamera != null && isFocus) {
            mCamera.autoFocus(null);
        }
    }

    /**
     * 拍照
     */
    public void takePicture() {
        mCamera.takePicture(null, null, null);
    }

    public void setIslargeResolution(boolean islargeResolution) {
        this.islargeResolution = islargeResolution;
    }

    /**
     * 释放相机
     */
    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private void setParameters() {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPictureFormat(ImageFormat.JPEG);
            parameters.setRotation(90);
            int width = 0;
            int height = 0;
            if (CameraUtil.getResolutionNum(parameters) == 1) {
                width = CameraUtil.getPictureSize(parameters)[0];
                height = CameraUtil.getPictureSize(parameters)[1];
            } else if (CameraUtil.getResolutionNum(parameters) > 1) {
                if (islargeResolution) {
                    width = CameraUtil.getPictureSize(parameters)[2];
                    height = CameraUtil.getPictureSize(parameters)[3];
                } else {
                    width = CameraUtil.getPictureSize(parameters)[0];
                    height = CameraUtil.getPictureSize(parameters)[1];
                }
            }
            if (width > 0 && height > 0) {
                parameters.setPictureSize(width, height);
            }
            parameters.setFocusMode(Camera.Parameters.FLASH_MODE_AUTO);
            mCamera.setParameters(parameters);
        }
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
