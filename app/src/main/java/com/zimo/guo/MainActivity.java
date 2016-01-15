package com.zimo.guo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zimo.guo.activity.CameraActivity;
import com.zimo.guo.util.PhotoUtil;

import java.io.File;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button takePhoto, choosePic;
    private ImageView showPic;

    private String path = Environment.getExternalStorageDirectory() + File.separator + "zimo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        takePhoto = (Button) findViewById(R.id.take_photo);
        choosePic = (Button) findViewById(R.id.choose_pic);
        showPic = (ImageView) findViewById(R.id.show_pic);

        takePhoto.setOnClickListener(this);
        choosePic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.take_photo:
                intent.setClass(MainActivity.this, CameraActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.choose_pic:
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                int degree = PhotoUtil.readPictureDegree(path);
                showPic.setImageBitmap(PhotoUtil.rotaingImageView(degree, bitmap));
                break;
            case 2:
                Uri uri = data.getData();
                if (uri != null) {
                    showPic.setImageURI(uri);
                }
                break;
        }
    }
}
