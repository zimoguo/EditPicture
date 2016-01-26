package com.zimo.guo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.zimo.guo.R;
import com.zimo.guo.view.CustomPicView;

import java.io.File;

public class EditPicActivity extends Activity implements View.OnClickListener {

    private Button cut,rotate,scaleLarge,scaleSmall;
    private CustomPicView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_pic);

        initView();
    }

    private void initView() {
        cut = (Button) findViewById(R.id.cut_pic);
        rotate = (Button) findViewById(R.id.rotate_pic);
        scaleLarge = (Button) findViewById(R.id.scale_large);
        scaleSmall = (Button) findViewById(R.id.scale_small);
        image = (CustomPicView) findViewById(R.id.image_view);

        cut.setOnClickListener(this);
        rotate.setOnClickListener(this);
        scaleLarge.setOnClickListener(this);
        scaleSmall.setOnClickListener(this);
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                image.savePic(Environment.getExternalStorageDirectory() + File.separator + "zimozimo.png");
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.cut_pic:
                image.cutPic(400,800);
                break;
            case R.id.rotate_pic:
                image.rotatePic(90);
                break;
            case R.id.scale_large:
                image.scalePicByMatrix(2f,2f);
                break;
            case R.id.scale_small:
                image.scalePicByMatrix(0.5f,0.5f);
                break;
        }

    }
}
