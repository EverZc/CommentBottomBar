package me.zwj.commentbottombar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import me.pandazhang.commentbottombarlib.ZBottomConstant;
import me.pandazhang.commentbottombarlib.ZBottomSheetPictureBar;
import me.pandazhang.filepicker.FilePicker;
import me.pandazhang.filepicker.MyToastPK;
import me.pandazhang.filepicker.activity.ImagePickActivityPicker;
import me.pandazhang.filepicker.filter.entity.ImageFile;


public class MainActivity extends AppCompatActivity {
    public static final String IS_NEED_CAMERA = "IsNeedCamera";
    public static final int REQUEST_CODE_PICK_IMAGE = 1;
    private Button mButtom;

    private ZBottomSheetPictureBar bottomComment;
    private ArrayList<ImageFile> mHuifuImages = new ArrayList<>(ZBottomConstant.ARTICLE_IMAGE_MAX);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtom=findViewById(R.id.buttom);
        mButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomComment = ZBottomSheetPictureBar.delegation(MainActivity.this);
                bottomComment.show("期待你的神回复");
                bottomComment.setOnSeetBarOnClickListener(new ZBottomSheetPictureBar.OnSheetBarOnClickListener() {
                    @Override
                    public void onAddClick() {
                        Intent intent = new Intent(MainActivity.this, ImagePickActivityPicker.class);
                        intent.putExtra(IS_NEED_CAMERA, true);
                        int maxNumber = mHuifuImages.isEmpty() ? ZBottomConstant.ARTICLE_COMMENT_IMAGE_MAX : ZBottomConstant.ARTICLE_COMMENT_IMAGE_MAX - mHuifuImages.size();
                        intent.putExtra(FilePicker.MAX_NUMBER, maxNumber);
                        startActivityForResult(intent, ZBottomConstant.REQUEST_CODE_PICK_IMAGE);
                    }

                    @Override
                    public void onDelClick(ImageFile imageFile, int position) {
                        if (bottomComment.getAdapterData().contains(imageFile)) {
                            bottomComment.getAdapterData().remove(imageFile);
                            bottomComment.adapterNotifyDataSetChanged();
                        }
                        if (mHuifuImages.contains(imageFile)) {
                            mHuifuImages.remove(imageFile);
                        }
                    }

                    @Override
                    public void onCommitClick() {
                        MyToastPK.showSuccess("点击提交",MainActivity.this);
                        bottomComment.dismiss();
                    }

                    @Override
                    public void onDissmiss() {

                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ZBottomConstant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                        ArrayList<ImageFile> imageList = data.getParcelableArrayListExtra(FilePicker.RESULT_PICK_IMAGE);
                        for (int i = 0; i < imageList.size(); i++) {
                            mHuifuImages.add(imageList.get(i));
                        }
                        bottomComment.setImages(imageList);
                }
                break;
        }
    }
}