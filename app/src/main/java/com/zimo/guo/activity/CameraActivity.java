package com.zimo.guo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zimo.guo.MainActivity;
import com.zimo.guo.R;
import com.zimo.guo.view.CameraView;

import org.apache.http.impl.client.DefaultTargetAuthenticationHandler;

import java.io.File;
import java.io.IOException;

/**
 * Created by zimo on 15/8/25.
 */
public class CameraActivity extends Activity implements View.OnClickListener {

    private Button takePhoto;
    private CameraView cameraView;
    private String url = Environment.getExternalStorageDirectory() + File.separator + "zimo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initView();
    }

    private void initView() {
        cameraView = (CameraView) findViewById(R.id.custom_camera);
        takePhoto = (Button) findViewById(R.id.take_photos);

        takePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.take_photos:
                cameraView.setPicUrl(url);
                cameraView.takePicture();
                setResult(11);
                finish();
                break;
            case R.id.custom_camera:
                cameraView.autoFocus();
                break;
        }
    }
}
