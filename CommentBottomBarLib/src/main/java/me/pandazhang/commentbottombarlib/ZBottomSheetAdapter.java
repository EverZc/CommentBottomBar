package me.pandazhang.commentbottombarlib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.List;

import me.pandazhang.filepicker.filter.entity.ImageFile;

/**
 * Created by Zwj on 2019/07/09.
 */

public class ZBottomSheetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ADD_IMAGE_TYPE = 0;
    private static final int NORMAL_IMAGE_TYPE = 1;

    private Context mContext;
    private List<ImageFile> mImageFiles;
    private int mMaxCount;

    private ZBottomSheetHolder.OnReleaseImageListener onReleaseImageListener;

    public ZBottomSheetAdapter(Context context, List<ImageFile> mImageFiles, int maxCount) {
        mContext = context;
        this.mImageFiles = mImageFiles;
        mMaxCount = maxCount;
    }

    public void setOnReleaseImageListener(ZBottomSheetHolder.OnReleaseImageListener onReleaseImageListener) {
        this.onReleaseImageListener = onReleaseImageListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        return new ZBottomSheetHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        if (mImageFiles.size() == 0 || position == mImageFiles.size()) {
            return ADD_IMAGE_TYPE;
        } else {
            return NORMAL_IMAGE_TYPE;
        }
    }

    public boolean isEmpty(){
        return mImageFiles.isEmpty();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ZBottomSheetHolder imageHolder = (ZBottomSheetHolder) holder;
        final View itemView = imageHolder.getItemView();
        //AutoUtils.autoSize(itemView);
        if (mMaxCount==3){  //判断如果最大图片为三张的时候,为多图模式  80%的高度
            itemView.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams params = itemView.getLayoutParams();
                    params.height = (int) (itemView.getMeasuredWidth());
                    itemView.setLayoutParams(params);
                }
            });

        }else {     //判断如果最大图片为9张的时候,为发布朋友圈的模式,100%高度
            itemView.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams params = itemView.getLayoutParams();
                    params.height=itemView.getMeasuredWidth();
                    //params.height = (int) (itemView.getMeasuredWidth() * 0.8);
                    itemView.setLayoutParams(params);
                }
            });
        }

        imageHolder.setOnReleaseImageListener(onReleaseImageListener);
        switch (getItemViewType(position)){
            case NORMAL_IMAGE_TYPE:
                ImageFile imageFile = mImageFiles.get(position);
                imageHolder.setData(imageFile);
                imageHolder.showCover();
                break;
            case ADD_IMAGE_TYPE:
                imageHolder.showAdd();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mImageFiles.size() < mMaxCount ? mImageFiles.size() + 1 : mImageFiles.size();
    }

    public void clearData(){
        mImageFiles.clear();

    }
    public void addData(ArrayList<ImageFile> imageFiles){
        mImageFiles.addAll(imageFiles);
        notifyDataSetChanged();
    }

    public void addData(ImageFile data){
        mImageFiles.add(data);
        notifyDataSetChanged();
    }

    public void refresh(ArrayList<ImageFile> imageFiles){
        mImageFiles.clear();
        mImageFiles.addAll(imageFiles);
        notifyDataSetChanged();
    }
}