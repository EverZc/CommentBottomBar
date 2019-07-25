package me.zwj.commentbottombar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import me.pandazhang.commentbottombarlib.ZBottomConstant;
import me.pandazhang.commentbottombarlib.ZBottomSheetPictureBar;
import me.pandazhang.filepicker.FilePicker;
import me.pandazhang.filepicker.MyToastPK;
import me.pandazhang.filepicker.activity.ImagePickActivityPicker;
import me.pandazhang.filepicker.filter.entity.ImageFile;


public class MainActivity extends AppCompatActivity implements DetailCommentAdapter.OnCommentReplyClickListener{
    public static final String IS_NEED_CAMERA = "IsNeedCamera";
    private View headerLayout;
    private RecyclerView mRecyclerView;
    private TextView tvComment;
    private ZBottomSheetPictureBar commentZBSP; //评论的弹出框
    private DetailCommentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvComment=findViewById(R.id.tv_comment);
        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainOnClick();
            }
        });
        headerLayout = LayoutInflater.from(this).inflate(R.layout.item_comment_info, null, false);
        mAdapter = new DetailCommentAdapter(new ArrayList<ReplyComment>());
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        //添加布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.addHeaderView(headerLayout);
        mAdapter.setOnCommentClickListenre(this);
        initAutherHeader();
    }

    private void initAutherHeader() {
        View view= headerLayout.findViewById(R.id.head_padding);
        view.setVisibility(View.VISIBLE);
        //评论内容
        TextView tvContent = (TextView) headerLayout.findViewById(R.id.tv_content);
        tvContent.setText("欢迎使用CommentBottomBar,你可以点击回复来体验CommentBottomBar！～");
        //头像
        RoundedImageView headImage = (RoundedImageView) headerLayout.findViewById(R.id.iv_avatar);
        Glide.with(this).load("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3677209778,3519789803&fm=27&gp=0.jpg")
                .placeholder(R.mipmap.ic_launcher).into(headImage);
        //回复
        TextView tvComment = (TextView) headerLayout.findViewById(R.id.iv_comment);
        tvComment.setText("回复");
        //点击评论
        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainOnClick();
            }
        });
        //昵称
        TextView tvNickName = (TextView) headerLayout.findViewById(R.id.tv_author);
        tvNickName.setText("张文靖同学");
        //时间
        TextView tvTime = (TextView) headerLayout.findViewById(R.id.tv_time);
        tvTime.setText("2019/07/16");
        //评论的图片内容
        ThreeGridView pictureLayout = (ThreeGridView) headerLayout.findViewById(R.id.threenvGallery);
        pictureLayout.setVisibility(View.GONE);

    }

    // 评论张文靖同学
    public void mainOnClick(){
        if (commentZBSP == null) {
            commentZBSP = ZBottomSheetPictureBar.delegation(MainActivity.this);
        }
        commentZBSP.show("期待您的神回复");
        commentZBSP.setOnSeetBarOnClickListener(new ZBottomSheetPictureBar.OnSheetBarOnClickListener() {
            @Override
            public void onAddClick() {
                Intent intent = new Intent(MainActivity.this, ImagePickActivityPicker.class);
                intent.putExtra(IS_NEED_CAMERA, true);
                int maxNumber = commentZBSP.getAdapterData().isEmpty() ?
                        ZBottomConstant.ARTICLE_IMAGE_MAX : ZBottomConstant.ARTICLE_IMAGE_MAX - commentZBSP.getAdapterData().size();
                intent.putExtra(FilePicker.MAX_NUMBER, maxNumber);
                startActivityForResult(intent, ZBottomConstant.REQUEST_CODE_PICK_IMAGE);
            }

            @Override
            public void onCommitClick(ArrayList<ImageFile> images, EditText content) {
                ReplyComment comment = new ReplyComment();
                comment.setUserName("游客");
                comment.setContent(content.getText().toString());
                comment.setAvatar("http://img3.imgtn.bdimg.com/it/u=1295558289,215361504&fm=26&gp=0.jpg");
                comment.setTime(
                        System.currentTimeMillis());
                for (int i = 0; i < images.size(); i++) {
                    Picture mPicture = new Picture();
                    mPicture.setPictureUrl(images.get(i).getPath());
                    comment.getPicture().add(mPicture);
                    Log.e("size", comment.getPicture().size() + "");
                }
                mAdapter.addData(comment);
                mAdapter.notifyDataSetChanged();
                commentZBSP.dismiss();
                commentZBSP.refresh();
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
                    commentZBSP.setImages(imageList);
                }
                break;
        }
    }
    //如下四个是adapter中的四个回调。
    @Override
    public void onUserClick(ReplyComment comment) {
        MyToastPK.showSuccess("用户头像",this);
    }

    @Override
    public void onFavourClick(ReplyComment comment) {
        MyToastPK.showSuccess("点击点赞",this);
    }


    @Override
    public void onContentClick(ReplyComment comment) {
        mainOnClick();
    }

    @Override
    public void onDeleteClick(String id, int position) {
        MyToastPK.showSuccess("点击评论删除",this);
    }
}