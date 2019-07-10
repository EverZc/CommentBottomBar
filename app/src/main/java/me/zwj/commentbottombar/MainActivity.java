package me.zwj.commentbottombar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.pandazhang.commentbottombarlib.ZBottomConstant;
import me.pandazhang.commentbottombarlib.ZBottomSheetPictureBar;
import me.pandazhang.filepicker.FilePicker;
import me.pandazhang.filepicker.activity.ImagePickActivityPicker;
import me.pandazhang.filepicker.filter.entity.ImageFile;


public class MainActivity extends AppCompatActivity {
    public static final String IS_NEED_CAMERA = "IsNeedCamera";
    public static final int REQUEST_CODE_PICK_IMAGE = 1;
    private Button mButtom;

    private ZBottomSheetPictureBar bottomChildComment;
    private ArrayList<ImageFile> mHuifuImages = new ArrayList<>(ZBottomConstant.ARTICLE_IMAGE_MAX);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtom=findViewById(R.id.buttom);
        mButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomChildComment = ZBottomSheetPictureBar.delegation(MainActivity.this);
                bottomChildComment.show("期待你的神回复");
                bottomChildComment.setOnSeetBarOnClickListener(new ZBottomSheetPictureBar.OnSheetBarOnClickListener() {
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
                        /*if (bottomChildComment.getAdapterData().contains(imageFile)) {
                            LogUtils.e("发布评论删除图片 ："+imageFile.getPath().toString());
                            bottomChildComment.getAdapterData().remove(imageFile);
                            bottomChildComment.adapterNotifyDataSetChanged();
                        }
                        if (mHuifuImages.contains(imageFile)) {
                            LogUtils.e("发布评论删除图片 ："+imageFile.getPath().toString());
                            mHuifuImages.remove(imageFile);
                        }*/
                    }

                    @Override
                    public void onCommitClick() {

                        /*if (mHuifuImages.size() >= 1) {
                            pictureHuifuObservable().subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<String>() {
                                        @Override
                                        public void call(String s) {
                                            EventBus.getDefault().post("", "addOneReply");
                                            mHuifuPicture = s;
                                            LogUtils.e("s : " + mHuifuPicture);
                                            mPresenter.replyCommtent(data.userId, data.id, bottomChildComment.getCommentText()+"", data.id, mHuifuPicture, position, mCommentData);

                                        }
                                    });
                        } else {
                            mPresenter.replyCommtent(data.userId, data.id, bottomChildComment.getCommentText()+"", data.id, null, position, mCommentData);

                        }*/

                        bottomChildComment.dismiss();

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
                        bottomChildComment.setImages(imageList);
                }
                break;
        }
    }
}