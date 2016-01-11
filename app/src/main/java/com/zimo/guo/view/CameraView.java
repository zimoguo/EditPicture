package com.zimo.guo.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zimo on 15/12/27.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private Camera camera;
    private SurfaceHolder holder;
    private Context context;
    private String picUrl;

    public CameraView(Context context) {
        super(context);
        this.context = context;
        initHolder();
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initHolder();
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initHolder();
    }

    private void initHolder() {
        holder = this.getHolder();
        holder.addCallback(this);
    }

    private boolean existCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * 初始化相机
     */
    private void initCamera() {
        if (camera == null) {
            camera = Camera.open();
        }
    }

    private void imagePreview(SurfaceHolder holder) {
        try {
            if (camera != null) {
                camera.setPreviewDisplay(holder);
                camera.setDisplayOrientation(90);
                camera.startPreview();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setParameters() {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPictureFormat(ImageFormat.JPEG);
            parameters.setRotation(90);
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            camera.setParameters(parameters);
        }
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public void autoFocus(){
        if (camera != null){
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success){
                        takePicture();
                    }
                }
            });
        }
    }

    public void takePicture(){
        if (camera != null){
            camera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    if (picUrl == null) {
                        picUrl = Environment.getExternalStorageDirectory() + File.separator + "zimo.jpg";
                    }
                    File file = new File(picUrl);
                    if (file.exists()) {
                        file.delete();
                    }
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(data);
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    imagePreview(holder);
                }
            });
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (existCamera(context)){
            initCamera();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        imagePreview(holder);
        setParameters();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

}
