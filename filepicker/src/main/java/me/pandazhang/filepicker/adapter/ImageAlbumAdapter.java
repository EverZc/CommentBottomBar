package me.pandazhang.filepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import me.pandazhang.filepicker.R;
import me.pandazhang.filepicker.filter.entity.AlbumFile;

import java.util.ArrayList;

/**
 * Created by Vincent Woo
 * modified by Zwj
 * Date: 2019/07/05
 */

public class ImageAlbumAdapter extends BaseAdapter<AlbumFile, ImageAlbumAdapter.ImageAblumHolder> {

    private OnItemClickListener<AlbumFile> mListener;

    public ImageAlbumAdapter(Context ctx, ArrayList<AlbumFile> list) {
        super(ctx, list);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @Override
    public ImageAblumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_image_ablum, parent, false);
        return new ImageAblumHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageAblumHolder holder, final int position) {
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(mList.get(position), position);
            }
        });
        AlbumFile ablumFile = mList.get(position);
        Glide.with(mContext)
                .load(ablumFile.getCover())
                .into(holder.mThumbnail);
        holder.mName.setText(ablumFile.getBucketName());
        holder.mCount.setText(ablumFile.getCount() + "");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ImageAblumHolder extends RecyclerView.ViewHolder{

        public ImageView mThumbnail;
        public TextView mName;
        public TextView mCount;

        private View mItemView;

        public ImageAblumHolder(View itemView) {
            super(itemView);
            mThumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mCount = (TextView) itemView.findViewById(R.id.tv_count);
            mItemView = itemView;
        }

        public View getItemView() {
            return mItemView;
        }
    }
}
