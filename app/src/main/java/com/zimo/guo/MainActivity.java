package com.zimo.guo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zimo.guo.view.CameraView;

public class MainActivity extends Activity implements View.OnClickListener, CameraView.OnCameraStatus {

    private CameraView mCamera;
    private Button takePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCamera = (CameraView) findViewById(R.id.custom_camera);
        takePic = (Button) findViewById(R.id.take_pic);

        takePic.setOnClickListener(this);
        mCamera.setOnClickListener(this);
        mCamera.setOnCameraStatus(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.custom_camera:
                mCamera.autoFocus(true);
                break;
            case R.id.take_pic:
                mCamera.takePicture();
                break;
        }

    }

    @Override
    public void savePics(byte[] data) {
        mCamera.startRecapturePreview();
        Toast.makeText(MainActivity.this,"保存图片",Toast.LENGTH_SHORT).show();
    }
}
