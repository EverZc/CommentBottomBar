package me.pandazhang.commentbottombarlib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import me.pandazhang.filepicker.filter.entity.ImageFile;

/**
 * Created by Zwj on 2019/07/09.
 */

public class ZBottomSheetHolder extends RecyclerView.ViewHolder {

    ImageView mAddView;
    ImageView mCoverView;
    ImageView mDeleteView;
    FrameLayout mAddLayout;
    FrameLayout mCoverLayout;
    FrameLayout mRootLayout;
    private Context mContext;
    private int padding;
    private  int width;
    private OnReleaseImageListener onReleaseImageListener;
    private View mItemView;
    private ImageFile mImageFile;


    public ZBottomSheetHolder(View itemView) {
        super(itemView);
        initView(itemView);
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
         width = wm.getDefaultDisplay().getWidth();

        initListener();
    }

    private void initView(View itemView){
        mItemView = itemView;
        mContext = itemView.getContext();
        mCoverLayout=itemView.findViewById(R.id.cover_layout);
        mAddLayout=itemView.findViewById(R.id.add_layout);
        mDeleteView=itemView.findViewById(R.id.iv_delete);
        mCoverView=itemView.findViewById(R.id.iv_cover);
        mAddView=itemView.findViewById(R.id.iv_add);
        mRootLayout=itemView.findViewById(R.id.item_layout);
    }

    public View getItemView() {
        return mItemView;
    }

    private void initListener() {
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReleaseImageListener != null){
                    onReleaseImageListener.onClick(getLayoutPosition());
                }
            }
        });

        mAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReleaseImageListener != null){
                    onReleaseImageListener.onAddClick();
                }
            }
        });
        mDeleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReleaseImageListener != null){
                    onReleaseImageListener.onDelClick(mImageFile,getLayoutPosition());
                }
            }
        });
    }

    public void setOnReleaseImageListener(OnReleaseImageListener onReleaseImageListener) {
        this.onReleaseImageListener = onReleaseImageListener;
    }

    /**
     * 显示图片
     */
    public void showCover(){
        mAddLayout.setVisibility(View.GONE);
        mCoverLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 显示加载按钮
     */
    public void showAdd(){
        mAddLayout.setVisibility(View.VISIBLE);
        mCoverLayout.setVisibility(View.GONE);
    }

    public void setData(ImageFile imageFile) {
        mImageFile = imageFile;
        Glide.with(mContext)
                .load(imageFile.getPath())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .override(width, (int) (width*0.66))//这里的单位是px
                .into(mCoverView);
    }

    public interface OnReleaseImageListener {
        void onAddClick();
        void onDelClick(ImageFile imageFile, int position);
        void onClick(int positon);
    }
}
