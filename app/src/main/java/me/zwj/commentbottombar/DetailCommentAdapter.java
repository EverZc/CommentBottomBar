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
    private int huifuNumber;

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
        if (item.getPicture().size()>0){
            for (int i = 0; i < item.getPicture().size(); i++) {
                mData.add(item.getPicture().get(i).pictureUrl);
                requestPicture.add(item.getPicture().get(i).pictureUrl + "");
            }
        }
        //隐藏回复按钮
        TextView huifuComment = helper.getView(R.id.iv_comment);
        huifuComment.setText("回复");
       /* helper.getView(R.id.item_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) return;
                mListener.onContentClick(item);
            }
        });*/
       huifuComment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (mListener == null) return;
               mListener.onContentClick(item);
           }
       });
        //删除评论
        TextView deleteComment = helper.getView(R.id.iv_delete_comment);

        deleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    //LogUtils.e("getParentPosition(item) : "+helper.getAdapterPosition());
                    mListener.onDeleteClick("", helper.getAdapterPosition() - 1);
                }
            }
        });
        //点赞数
        final TextView favourCountView = helper.getView(R.id.tv_praise_number);
        favourCountView.setText(item.getFavourCounr()+"");
        if (item.getFavourCounr() == 0) {
            favourCountView .setVisibility(View.GONE);
        }else {
            favourCountView .setVisibility(View.VISIBLE);
        }
        if (item.isFavour()){
            favourCountView.setTextColor(mContext.getResources().getColor(R.color. colorRemind));
        }else {
            favourCountView.setTextColor(mContext.getResources().getColor(R.color.colorSmallText));
        }
        ImageView ivHeadImage= helper.getView(R.id.iv_avatar);
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
        //有回复的目标
        TextView tvReply = helper.getView(R.id.tv_reply);

       if (item.getContent().length()==0){
           tvContent.setVisibility(View.GONE);
       }else {
           tvContent.setVisibility(View.VISIBLE);
       }
        if (!item.isIdentical()) {
            tvContent.setText(item.getContent());
            tvReply.setVisibility(View.GONE);
        } else {
            tvReply.setVisibility(View.GONE);
            SpannableStringBuilder stringBuilder = new SpannableStringUtils.Builder()
                    .append(item.getContent())
                    .append(" @")
                    .setForegroundColor(mContext.getResources().getColor(R.color.colorSecondText))
                    .append(item.getPinglunerName())
                    .setForegroundColor(mContext.getResources().getColor(R.color.colorFriendNickname))
                    .append(" " + item.getContented())
                    .setForegroundColor(mContext.getResources().getColor(R.color.colorSmallText))
                    .create();
            tvContent.setText(stringBuilder);
        }


       // helper.setText(R.id.tv_time, timeConvertUtil(item.time)+"");
        //helper.setText(R.id.tv_time, TimeUtils.millis2String(item.time, "yyyy-MM-dd mm:ss"));

        ThreeGridView llPicture = helper.getView(R.id.threenvGallery);

        List<Picture> pictureHuifu = item.getPicture();
        Log.e("pictureHuifu",pictureHuifu.size()+"");
        if (pictureHuifu == null || pictureHuifu.size() == 0) {
            llPicture.setVisibility(View.GONE);
            Log.e("pictureHuifu","pictureHuifu gone");

        } else {
            Log.e("pictureHuifu","pictureHuifu visible");
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
