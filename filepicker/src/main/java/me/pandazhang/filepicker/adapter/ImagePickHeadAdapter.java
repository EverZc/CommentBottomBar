package me.pandazhang.filepicker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import me.pandazhang.filepicker.FilePicker;
import me.pandazhang.filepicker.R;
import me.pandazhang.filepicker.activity.ImagePickActivityPicker;
import me.pandazhang.filepicker.filter.entity.ImageFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_DCIM;

/**
 * Created by Vincent Woo
 * modified by Zwj
 * Date: 2019/07/05
 */

public class ImagePickHeadAdapter extends BaseAdapter<ImageFile, ImagePickHeadAdapter.ImagePickViewHolder> {
    private boolean isNeedCamera;
    private int mMaxNumber;
    private int mCurrentNumber = 0;
    public String mImagePath;
    private boolean isSingle;
    private int mIndex = -1;

    public ImagePickHeadAdapter(Context ctx, boolean needCamera, int max) {
        this(ctx, new ArrayList<ImageFile>(), needCamera, max);
    }

    public ImagePickHeadAdapter(Context ctx, ArrayList<ImageFile> list, boolean needCamera, int max) {
        super(ctx, list);
        isNeedCamera = needCamera;
        mMaxNumber = max;
        this.isSingle = mMaxNumber == 1;
    }

    @Override
    public ImagePickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_image_pick_head, parent, false);
        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        if (params != null) {
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            params.height = width / ImagePickActivityPicker.COLUMN_NUMBER;
        }
        return new ImagePickViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ImagePickViewHolder holder, final int position) {
        if (isNeedCamera && position == 0) {
            holder.mIvCamera.setVisibility(View.VISIBLE);
            holder.mIvThumbnail.setVisibility(View.INVISIBLE);
            holder.mCbx.setVisibility(View.INVISIBLE);
            holder.mShadow.setVisibility(View.INVISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("No.1"," 0.0");
                    Uri imageUrl;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
                    File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM).getAbsolutePath() + "/IMG_" + timeStamp + ".jpg");
                    mImagePath = file.getAbsolutePath();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        imageUrl= Uri.fromFile(file);
                      /* imageUrl = FileProvider.getUriForFile(mContext,
                                mContext.getApplicationContext().getPackageName() + ".provider",
                                file);*/
                    }else {
                        imageUrl= Uri.fromFile(file);
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
                    ((Activity) mContext).startActivityForResult(intent, FilePicker.REQUEST_CODE_TAKE_IMAGE);
                }
            });
        } else {
            holder.mIvCamera.setVisibility(View.INVISIBLE);
            holder.mIvThumbnail.setVisibility(View.VISIBLE);


            ImageFile file;
            if (isNeedCamera) {
                file = mList.get(position - 1);
            } else {
                file = mList.get(position);
            }
            Glide.with(mContext)
                    .load(file.getPath())
                    .centerCrop()
                    .into(holder.mIvThumbnail);

            if (file.isSelected()) {
                holder.mCbx.setSelected(true);
                holder.mShadow.setVisibility(View.VISIBLE);
            } else {
                holder.mCbx.setSelected(false);
                holder.mShadow.setVisibility(View.INVISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSingle) {
                        if (holder.mCbx.isSelected()) {
                            holder.mShadow.setVisibility(View.INVISIBLE);
                            holder.mCbx.setSelected(false);
                        } else {
                            holder.mShadow.setVisibility(View.VISIBLE);
                            holder.mCbx.setSelected(true);
                        }
                        if (mIndex != -1 && mIndex != position) {
                            Log.e("imagePickAdapter : ",mIndex+"");
                           // mList.get(mIndex).setSelected(holder.mCbx.isSelected());
                            //不明觉厉，注释这段解决最后一个数组越界问题
                            notifyItemChanged(mIndex);
                        }
                        mIndex = holder.getAdapterPosition();
                        if (mListener != null) {
                            mListener.OnSelectStateChanged(holder.mCbx.isSelected(), mList.get(isNeedCamera ? mIndex - 1 : mIndex));
                        }
                    } else {
                        if (!holder.mCbx.isSelected() && isUpToMax()) {
                            return;
                        }
                        if (holder.mCbx.isSelected()) {
                            holder.mShadow.setVisibility(View.INVISIBLE);
                            holder.mCbx.setSelected(false);
                            mCurrentNumber--;
                        } else {
                            holder.mShadow.setVisibility(View.VISIBLE);
                            holder.mCbx.setSelected(true);
                            mCurrentNumber++;
                        }
                        mIndex = isNeedCamera ? holder.getAdapterPosition() - 1 : holder.getAdapterPosition();
                        mList.get(mIndex).setSelected(holder.mCbx.isSelected());
                        if (mListener != null) {
                            mListener.OnSelectStateChanged(holder.mCbx.isSelected(), mList.get(mIndex));
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return isNeedCamera ? mList.size() + 1 : mList.size();
    }

    class ImagePickViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvCamera;
        private ImageView mIvThumbnail;
        private View mShadow;
        private ImageView mCbx;

        public ImagePickViewHolder(View itemView) {
            super(itemView);
            mIvCamera = (ImageView) itemView.findViewById(R.id.iv_camera);
            mIvThumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
            mShadow = itemView.findViewById(R.id.shadow);
            mCbx = (ImageView) itemView.findViewById(R.id.cbx);
        }
    }

    private boolean isUpToMax() {
        return mCurrentNumber >= mMaxNumber;
    }

    public void setCurrentNumber(int number) {
        mCurrentNumber = number;
    }
}
