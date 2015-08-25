package com.zimo.guo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zimo.guo.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by zimo on 15/8/25.
 */
public class CameraActivity extends Activity implements View.OnClickListener, SurfaceHolder.Callback {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private TextView cancel;
    private TextView submit;
    private ImageView takePic;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initView();
        initData();
    }

    private void initData() {
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
    }

    private void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        cancel = (TextView) findViewById(R.id.camera_cancel);
        submit = (TextView) findViewById(R.id.camera_submit);
        takePic = (ImageView) findViewById(R.id.take_pic);

        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
        takePic.setOnClickListener(this);
        mSurfaceView.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkCameraHardware(this) && mCamera == null){
            mCamera = initCamera();
            if (mHolder !=null){
                startPreview(mCamera, mHolder);
            }
        }
        initCamera();
    }

    private void startPreview(Camera mCamera, SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 检查是否具有相机功能
     *
     * @param context context
     * @return 是否具有相机功能
     */
    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    private Camera initCamera() {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
        }
        return camera;
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            /**
             * 自动对焦
             */
            case R.id.surface_view:
                mCamera.autoFocus(null);
                break;
            /**
             * 取消
             */
            case R.id.camera_cancel:
                finish();
                break;
            /**
             * 确定
             */
            case R.id.camera_submit:
                break;
            /**
             * 拍照
             */
            case R.id.take_pic:
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPictureFormat(ImageFormat.JPEG);
//                parameters.setPictureSize(800, 400);
                parameters.setFocusMode(Camera.Parameters.FLASH_MODE_AUTO);
                mCamera.setParameters(parameters);
                mCamera.takePicture(null,null,pictureCallback);
                break;
        }

    }

    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File picFile;
        }
    };


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview(mCamera,holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null){
            return;
        }

        mCamera.stopPreview();

        startPreview(mCamera,holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }
}
