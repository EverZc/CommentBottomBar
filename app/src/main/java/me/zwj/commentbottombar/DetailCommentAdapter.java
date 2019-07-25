package me.zwj.commentbottombar;

import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Zwj on 2019/07/10 .
 */

public class DetailCommentAdapter extends BaseQuickAdapter<ReplyComment, BaseViewHolder> {

    private OnCommentReplyClickListener mListener;
    private ArrayList mPictureList;
    private ThreeAdapter threeAdapter;

    //遍历点击详情中的图片
    private ArrayList<String> requestPicture = new ArrayList<String>();

    public DetailCommentAdapter(@Nullable List<ReplyComment> data) {
        super(R.layout.item_comment_info, data);
    }

    public void setOnCommentClickListenre(OnCommentReplyClickListener listenre) {
        this.mListener = listenre;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ReplyComment item) {
        ArrayList<String> mData = new ArrayList<>();
        mData.clear();
        if (item.getPicture().size() > 0) {
            for (int i = 0; i < item.getPicture().size(); i++) {
                mData.add(item.getPicture().get(i).pictureUrl);
                requestPicture.add(item.getPicture().get(i).pictureUrl + "");
            }
        }
        //隐藏回复按钮
        TextView tvReply = helper.getView(R.id.iv_comment);
        tvReply.setText("回复");
        tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) return;
                mListener.onContentClick(item);
            }
        });

        ImageView ivHeadImage = helper.getView(R.id.iv_avatar);
        helper.getView(R.id.iv_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) return;
                mListener.onUserClick(item);
            }
        });
        Glide.with(mContext).load(item.getAvatar()).into(ivHeadImage);
        helper.setText(R.id.tv_author, item.getUserName());
        helper.getView(R.id.tv_author).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) return;
                mListener.onUserClick(item);
            }
        });
        //回复详情的内容
        TextView tvContent = helper.getView(R.id.tv_content);
        if (item.getContent().length() == 0) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setVisibility(View.VISIBLE);
        }
        tvContent.setText(item.getContent());
        helper.setText(R.id.tv_time, timeConvertUtil(item.getTime()) + "");

        ThreeGridView llPicture = helper.getView(R.id.threenvGallery);

        List<Picture> pictureHuifu = item.getPicture();

        if (pictureHuifu == null || pictureHuifu.size() == 0) {
            llPicture.setVisibility(View.GONE);
        } else {
            llPicture.setVisibility(View.VISIBLE);
            mPictureList = new ArrayList();
            if (item.getPicture().size() > 0) {
                for (int i = 0; i < item.getPicture().size(); i++) {
                    mPictureList.add(item.getPicture().get(i).pictureUrl + "");
                    requestPicture.add(item.getPicture().get(i).pictureUrl + "");
                }
            }
            threeAdapter = new ThreeAdapter(mContext, mPictureList);
            llPicture.setAdapter(threeAdapter);
            llPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            llPicture.setOnImageClickListener(new ThreeGridView.OnImageClickListener() {
                @Override
                public void onImageCilcked(int position, View view) {
                }
            });
        }
    }

    public interface OnCommentReplyClickListener {
        void onUserClick(ReplyComment comment);

        void onFavourClick(ReplyComment comment);

        void onContentClick(ReplyComment comment);

        void onDeleteClick(String id, int position);
    }

    public static String timeConvertUtil(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String date = simpleDateFormat.format(new Date(time));
        return date;
    }
}