package me.zwj.commentbottombar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import me.pandazhang.commentbottombarlib.ZBottomConstant;
import me.pandazhang.commentbottombarlib.ZBottomSheetPictureBar;
import me.pandazhang.filepicker.FilePicker;
import me.pandazhang.filepicker.MyToastPK;
import me.pandazhang.filepicker.activity.ImagePickActivityPicker;
import me.pandazhang.filepicker.filter.entity.ImageFile;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity implements DetailCommentAdapter.OnCommentReplyClickListener{
    public static final String IS_NEED_CAMERA = "IsNeedCamera";
    private DetailCommentAdapter adapter;
    private View mActicalHead;
    private View mHeaderLayout;
    private Button mButtom;
    private ZBottomSheetPictureBar bottomComment;
    private ArrayList<ImageFile> mHuifuImages = new ArrayList<>(ZBottomConstant.ARTICLE_IMAGE_MAX);
    private RecyclerView mRecyclerView;
    private ArrayList<ReplyComment> mData = new ArrayList<ReplyComment>();
    private TextView tvComment;


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
        mHeaderLayout = LayoutInflater.from(this).inflate(R.layout.item_comment_info, null, false);
        adapter = new DetailCommentAdapter(mData);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(adapter);
        //添加布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter.addHeaderView(mHeaderLayout);
        adapter.setOnCommentClickListenre( this);
        initAutherHeader();
    }

    private void initAutherHeader() {
        //评论内容
        TextView tvContent = (TextView) mHeaderLayout.findViewById(R.id.tv_content);
        tvContent.setText("欢迎使用CommentBottomBar,你可以点击回复来体验CommentBottomBar！～");
        //删除评论
        TextView tvDelete = (TextView) mHeaderLayout.findViewById(R.id.iv_delete_comment);
        tvDelete.setVisibility(View.GONE);
        //头像
        RoundedImageView headImage = (RoundedImageView) mHeaderLayout.findViewById(R.id.iv_avatar);
        Glide.with(this).load("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3677209778,3519789803&fm=27&gp=0.jpg")
                .placeholder(R.mipmap.ic_launcher).into(headImage);
        //回复
        TextView tvComment = (TextView) mHeaderLayout.findViewById(R.id.iv_comment);
        tvComment.setText("回复");
        //点击评论
        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainOnClick();
            }
        });
        //昵称
        TextView tvNickName = (TextView) mHeaderLayout.findViewById(R.id.tv_author);
        tvNickName.setText("张文靖同学");
        //时间
        TextView tvTime = (TextView) mHeaderLayout.findViewById(R.id.tv_time);
        tvTime.setText("2019/07/16");
        //评论的图片内容
        ThreeGridView pictureLayout = (ThreeGridView) mHeaderLayout.findViewById(R.id.threenvGallery);
        pictureLayout.setVisibility(View.GONE);

    }

    // 评论张文靖同学
    public void mainOnClick(){
        if (bottomComment == null) {
            bottomComment = ZBottomSheetPictureBar.delegation(MainActivity.this);
        }
        bottomComment.show("期待您的神回复");
        bottomComment.setOnSeetBarOnClickListener(new ZBottomSheetPictureBar.OnSheetBarOnClickListener() {
            @Override
            public void onAddClick() {
                Intent intent = new Intent(MainActivity.this, ImagePickActivityPicker.class);
                intent.putExtra(IS_NEED_CAMERA, true);
                int maxNumber = mHuifuImages.isEmpty() ? ZBottomConstant.ARTICLE_IMAGE_MAX : ZBottomConstant.ARTICLE_IMAGE_MAX - mHuifuImages.size();
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
                ReplyComment comment = new ReplyComment();
                comment.setUserName("游客");
                comment.setContent(bottomComment.getCommentText());
                comment.setAvatar("http://img3.imgtn.bdimg.com/it/u=1295558289,215361504&fm=26&gp=0.jpg");
                for (int i = 0; i < mHuifuImages.size(); i++) {
                    Picture mPicture = new Picture();
                    mPicture.setPictureUrl(mHuifuImages.get(i).getPath());
                    comment.getPicture().add(mPicture);
                    Log.e("size", comment.getPicture().size() + "");

                }
                adapter.addData(comment);
                adapter.notifyDataSetChanged();
                bottomComment.dismiss();

                mHuifuImages.clear();
                bottomComment.clear();
            }

            @Override
            public void onDissmiss() {

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


    //如下四个是adapter中的四个回调。
    @Override
    public void onUserClick(ReplyComment comment) {

    }

    @Override
    public void onFavourClick(ReplyComment comment) {

    }

    @Override
    public void onContentClick(ReplyComment comment) {

    }

    @Override
    public void onDeleteClick(String id, int position) {

    }
}