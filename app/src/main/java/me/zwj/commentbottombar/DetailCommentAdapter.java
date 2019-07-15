package me.zwj.commentbottombar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static me.zwj.commentbottombar.Detail.ReplyComment;

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
        if (item.picture.size()>0){
            for (int i = 0; i < item.picture.size(); i++) {
                mData.add(item.picture.get(i).pictureUrl);
                requestPicture.add(item.picture.get(i).pictureUrl + "");
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
                    mListener.onHuiFuDeleteClick(item.id, helper.getAdapterPosition() - 1);
                }
            }
        });
        //点赞数
        final TextView favourCountView = helper.getView(R.id.tv_praise_number);
        favourCountView.setText(item.favourCounr+"");
        if (item.favourCounr == 0) {
            favourCountView .setVisibility(View.GONE);
        }else {
            favourCountView .setVisibility(View.VISIBLE);
        }
        if (item.isFavour){
            favourCountView.setTextColor(mContext.getResources().getColor(R.color. colorRemind));
        }else {
            favourCountView.setTextColor(mContext.getResources().getColor(R.color.colorSmallText));
        }
       // helper.loadImage(R.id.iv_avatar, item.avatar);
        helper.getView(R.id.iv_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) return;
                mListener.onUserClick(item);
            }
        });
        helper.setText(R.id.tv_author, item.userName);
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

       if (item.content.length()==0){
           tvContent.setVisibility(View.GONE);
       }else {
           tvContent.setVisibility(View.VISIBLE);
       }
        if (!item.isIdentical) {
            tvContent.setText(item.content);
            tvReply.setVisibility(View.GONE);
        } else {

            tvReply.setVisibility(View.GONE);
            SpannableStringBuilder stringBuilder = new SpannableStringUtils.Builder()
                    .append(item.content)
                    .append(" @")
                    .setForegroundColor(mContext.getResources().getColor(R.color.colorSecondText))
                    .append(item.pinglunerName)
                    .setForegroundColor(mContext.getResources().getColor(R.color.colorFriendNickname))
                    .append(" " + item.contented)
                    .setForegroundColor(mContext.getResources().getColor(R.color.colorSmallText))
                    .create();
            tvContent.setText(stringBuilder);
        }


        helper.setText(R.id.tv_time, timeConvertUtil(item.time)+"");
        //helper.setText(R.id.tv_time, TimeUtils.millis2String(item.time, "yyyy-MM-dd mm:ss"));
        final CheckBox favourView = helper.getView(R.id.cb_praise);
        favourView.setSelected(item.isFavour);
        favourView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    favourCountView.setTextColor(mContext.getResources().getColor(R.color. colorRemind));
                    item.isFavour = true;
                    item.favourCounr++;
                    if (item.favourCounr == 0) {
                        favourCountView.setVisibility(View.GONE);
                    } else {
                        favourCountView.setVisibility(View.VISIBLE);
                    }
                    favourCountView.setText(String.format("%s", item.favourCounr));
                    favourView.setSelected(item.isFavour);
                    if (mListener != null) {
                        mListener.onFavourClick(item);
                    }
                } else {
                    favourCountView.setTextColor(mContext.getResources().getColor(R.color.colorSmallText));
                    item.isFavour = false;
                    item.favourCounr--;
                    if (item.favourCounr == 0) {
                        favourCountView.setVisibility(View.GONE);
                    } else {
                        favourCountView.setVisibility(View.VISIBLE);
                    }
                    favourCountView.setText(String.format("%s", item.favourCounr));
                    favourView.setSelected(item.isFavour);
                    if (mListener != null) {
                        mListener.onFavourClick(item);
                    }
                }
            }
        });
        ThreeGridView llPicture = helper.getView(R.id.threenvGallery);

        List<Detail.Picture> pictureHuifu = item.picture;
        if (pictureHuifu == null || pictureHuifu.size() == 0) {
            llPicture.setVisibility(View.GONE);

        } else {
            llPicture.setVisibility(View.VISIBLE);
            mPictureList = new ArrayList();
            if (item.picture.size() > 0) {
                for (int i = 0; i < item.picture.size(); i++) {
                    mPictureList.add(item.picture.get(i).pictureUrl + "");
                    requestPicture.add(item.picture.get(i).pictureUrl + "");
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
                   /* Bundle bundle = new Bundle();
                    Intent intent = new Intent(mContext, ImageDetailsActivity.class);
                    bundle.putStringArrayList("detailpicture", requestPicture);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("page", position);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);*/
                    //mContext.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });


        }

    }



    public interface OnCommentReplyClickListener {
        void onUserClick(Detail.ReplyComment comment);

        void onFavourClick(ReplyComment comment);

        void onContentClick(ReplyComment comment);

        void onHuiFuDeleteClick(String id, int position);
    }

    public static String timeConvertUtil(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String date = simpleDateFormat.format(new Date(time));
        return date;
    }
}
