package me.zwj.commentbottombar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.pandazhang.filepicker.FilePicker;
import me.pandazhang.filepicker.activity.ImagePickActivityPicker;
import me.pandazhang.filepicker.filter.entity.ImageFile;

public class MainActivity extends AppCompatActivity {
    public static final String IS_NEED_CAMERA = "IsNeedCamera";
    public static final int REQUEST_CODE_PICK_IMAGE = 1;
    private Button mButtom;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.iv);
        mButtom=findViewById(R.id.buttom);
        mButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImagePickActivityPicker.class);
                intent.putExtra(IS_NEED_CAMERA, true);
                int maxNumber = 3;
                intent.putExtra(FilePicker.MAX_NUMBER, maxNumber);
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AppConstant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    //处理添加图片的信息
                    ArrayList<ImageFile> list = data.getParcelableArrayListExtra(FilePicker.RESULT_PICK_IMAGE);
                    /*for (int i = 0; i < list.size(); i++) {
                        mRteLayout.insertImage(list.get(i).getPath(), mRteLayout.getMeasuredWidth());
                        mSaveView.setEnabled(true);
                    }*/
                    if (list.size()>0){
                        Glide.with(this).load(list.get(0).getPath()).into(imageView);
                    }

                }
                break;
            case AppConstant.REQUEST_CODE_PICK_IMAGE_ZERO:
                if (resultCode == RESULT_OK) {
                    //处理添加图片的信息
                    ArrayList<ImageFile> list = data.getParcelableArrayListExtra(FilePicker.RESULT_PICK_IMAGE);
                    /*for (int i = 0; i < list.size(); i++) {
                        mRteLayout.insertImage(list.get(i).getPath(), mRteLayout.getMeasuredWidth());
                        mSaveView.setEnabled(true);
                    }*/
                    if (list.size()>0){
                        Glide.with(this).load(list.get(0).getPath()).into(imageView);
                    }

                }
                break;
        }
    }
}