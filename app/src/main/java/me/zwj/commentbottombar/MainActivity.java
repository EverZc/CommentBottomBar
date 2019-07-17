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


public class MainActivity extends AppCompatActivity {
    public static final String IS_NEED_CAMERA = "IsNeedCamera";
    private DetailCommentAdapter adapter;
    private View mActicalHead;
    private View mHeaderLayout;
    private Button mButtom;
    private ZBottomSheetPictureBar bottomComment;
    private ArrayList<ImageFile> mHuifuImages = new ArrayList<>(ZBottomConstant.ARTICLE_IMAGE_MAX);
    private RecyclerView mRecyclerView;
    private ArrayList mData = new ArrayList<ReplyComment>();
    private List<Picture> mCommitImages = new ArrayList<>(ZBottomConstant.ARTICLE_IMAGE_MAX);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        // mActicalHead = LayoutInflater.from(this).inflate(R.layout.layout_header_actical, null, false);
        mHeaderLayout = LayoutInflater.from(this).inflate(R.layout.item_comment_info, null, false);
        adapter = new DetailCommentAdapter(mData);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(adapter);
        //添加布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        // adapter.addHeaderView(mActicalHead);
        adapter.addHeaderView(mHeaderLayout);
        adapter.setOnCommentClickListenre(new DetailCommentAdapter.OnCommentReplyClickListener() {
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
            public void onHuiFuDeleteClick(String id, int position) {

            }
        });
        // initHeader();
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
                if (bottomComment==null){
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
                        Log.e("mcommit size ",mCommitImages.size()+"");comment.getPicture().add(new Picture());
                        mData.add(comment);
                        adapter.notifyDataSetChanged();
                        bottomComment.dismiss();
                        mCommitImages.clear();
                        mHuifuImages.clear();
                        bottomComment.clear();
                    }

                    @Override
                    public void onDissmiss() {

                    }
                });
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

    private void initHeader() {
        LinearLayout mLinearLayout = mActicalHead.findViewById(R.id.ll_content);
        mLinearLayout.setVisibility(View.VISIBLE);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LinearLayout llTypeOne = mActicalHead.findViewById(R.id.ll_type_one);
        LinearLayout llTypeTwo = mActicalHead.findViewById(R.id.ll_type_two);
        LinearLayout llTypeThree = mActicalHead.findViewById(R.id.ll_type_three);
        TextView tvTitleTypeOne = mActicalHead.findViewById(R.id.tv_title_type_one);
        TextView tvTitleTypeTwo = mActicalHead.findViewById(R.id.tv_title_type_two);
        TextView tvTitleTypeThree = mActicalHead.findViewById(R.id.tv_title_type_three);

        TextView tvNameTypeOne = mActicalHead.findViewById(R.id.tv_name_type_one);
        TextView tvNameTypeTwo = mActicalHead.findViewById(R.id.tv_name_type_two);
        TextView tvNameTypeThree = mActicalHead.findViewById(R.id.tv_name_type_three);

        ImageView ivCoverTypeOnemore = mActicalHead.findViewById(R.id.iv_cover_type_onemore);
        ImageView ivCoverTypeThreemoreOne = mActicalHead.findViewById(R.id.iv_cover_type_threemore_one);
        ImageView ivCoverTypeThreemoreTwo = mActicalHead.findViewById(R.id.iv_cover_type_threemore_two);
        ImageView ivCoverTypeThreemoreThree = mActicalHead.findViewById(R.id.iv_cover_type_threemore_three);
        ImageView ivCoverTypeBigimage = mActicalHead.findViewById(R.id.iv_cover_type_bigimage);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14 + 14 + 4 + 4, getResources().getDisplayMetrics());
        int widthEnd = width - padding;
        int mtype = 3;
        LinearLayout ll_comment1 = mActicalHead.findViewById(R.id.ll_comment1);
        LinearLayout ll_favour1 = mActicalHead.findViewById(R.id.ll_favour1);
        LinearLayout ll_comment2 = mActicalHead.findViewById(R.id.ll_comment2);
        LinearLayout ll_favour2 = mActicalHead.findViewById(R.id.ll_favour2);
        LinearLayout ll_comment3 = mActicalHead.findViewById(R.id.ll_comment3);
        LinearLayout ll_favour3 = mActicalHead.findViewById(R.id.ll_comment3);
        if (2 > 0) {
            ll_comment1.setVisibility(View.VISIBLE);
            ll_comment2.setVisibility(View.VISIBLE);
            ll_comment3.setVisibility(View.VISIBLE);
        } else {
            ll_comment1.setVisibility(View.GONE);
            ll_comment2.setVisibility(View.GONE);
            ll_comment3.setVisibility(View.GONE);
        }
        if (2 > 0) {
            ll_favour1.setVisibility(View.VISIBLE);
            ll_favour2.setVisibility(View.VISIBLE);
            ll_favour3.setVisibility(View.VISIBLE);
        } else {
            ll_favour1.setVisibility(View.GONE);
            ll_favour2.setVisibility(View.GONE);
            ll_favour3.setVisibility(View.GONE);
        }

        TextView tv_comment1 = mActicalHead.findViewById(R.id.tv_comment1);
        TextView tv_favour1 = mActicalHead.findViewById(R.id.tv_favour1);
        tv_comment1.setText(String.format("%s", 11 + ""));
        //设置点赞数量
        tv_favour1.setText(String.format("%s", 22 + ""));
        TextView tv_comment2 = mActicalHead.findViewById(R.id.tv_comment2);
        TextView tv_favour2 = mActicalHead.findViewById(R.id.tv_favour2);
        tv_comment2.setText(String.format("%s", 33 + ""));
        //设置点赞数量
        tv_favour2.setText(String.format("%s", 44 + ""));
        TextView tv_comment3 = mActicalHead.findViewById(R.id.tv_comment3);
        TextView tv_favour3 = mActicalHead.findViewById(R.id.tv_favour3);
        tv_comment3.setText(String.format("%s", 55 + ""));
        //设置点赞数量
        tv_favour3.setText(String.format("%s", 66 + ""));

        if (mtype == 2) { //多图单张
            ViewGroup.LayoutParams lp = ivCoverTypeOnemore.getLayoutParams();
            lp.height = (int) (widthEnd / 3 * 0.6667);
            llTypeOne.setVisibility(View.VISIBLE);
            tvTitleTypeOne.setText("标题");
            Glide.with(this)
                    .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2471845590,913308006&fm=27&gp=0.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(ivCoverTypeOnemore);
            tvNameTypeOne.setText("昵称");
            //设置评论数量


        } else if (mtype == 1) { //多图3张
            ViewGroup.LayoutParams lp = ivCoverTypeThreemoreOne.getLayoutParams();
            lp.width = widthEnd / 3;
            lp.height = (int) (widthEnd / 3 * 0.666);
            ivCoverTypeThreemoreOne.setLayoutParams(lp);
            ivCoverTypeThreemoreTwo.setLayoutParams(lp);
            ivCoverTypeThreemoreThree.setLayoutParams(lp);
            llTypeTwo.setVisibility(View.VISIBLE);
            tvTitleTypeTwo.setText("标题");
            Glide.with(this)
                    .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2471845590,913308006&fm=27&gp=0.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(ivCoverTypeThreemoreOne);
            Glide.with(this)
                    .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2471845590,913308006&fm=27&gp=0.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(ivCoverTypeThreemoreTwo);
            Glide.with(this)
                    .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2471845590,913308006&fm=27&gp=0.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(ivCoverTypeThreemoreThree);
            tvNameTypeTwo.setText("昵称");
        } else if (mtype == 3) { //大图一张
            ViewGroup.LayoutParams lp = ivCoverTypeBigimage.getLayoutParams();
            lp.height = (int) ((widthEnd) * 0.5625);
            ivCoverTypeBigimage.setLayoutParams(lp);
            llTypeThree.setVisibility(View.VISIBLE);
            tvTitleTypeThree.setText("标题");
            Glide.with(this)
                    .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2471845590,913308006&fm=27&gp=0.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(ivCoverTypeBigimage);
            tvNameTypeThree.setText("昵称");
        }
    }

    private void onClick() {
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
                        MyToastPK.showSuccess("点击提交", MainActivity.this);
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
                        Picture mPicture=new Picture();
                        mPicture.setPictureUrl(imageList.get(i).getPath());
                        mCommitImages.add(mPicture);
                    }
                    bottomComment.setImages(imageList);
                }
                break;
        }
    }
}