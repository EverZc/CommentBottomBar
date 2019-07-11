package me.zwj.commentbottombar;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.util.List;

/**
 * Created by Administrator
 * on 2017/5/25 0025.
 */

public class ThreeAdapter extends NineGridViewDefaultAdapter<String> {

    private Context context;
    private List<String> items;

    public ThreeAdapter(Context context1, List<String> items) {
        super(context1, items);
        this.context = context1;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }


    @Override
    public View getView(int position, View recycleView) {

        final String url = getItem(position);

        ImageView imageView;
        if (recycleView == null) {
            imageView = generialDefaultImageView();
        } else {
            imageView = (ImageView) recycleView;
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

       /* Glide.with(context).load(Uri.parse(url))
                .placeholder(R.mipmap.position_square_big)
                .crossFade()
                .dontAnimate()
                .into(imageView);*/

        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher )
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .override(300, 300)//这里的单位是px
                .into(imageView);
        return imageView;
    }
}
