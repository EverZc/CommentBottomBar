package me.pandazhang.filepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Zwj
 * Date: 2019/07/05
 */

public class DividerListItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    private int padding=0;

    public DividerListItemDecoration(Context context, int orientation, int drawableId) {
        this.mPaint = new Paint();

        padding= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics());
        // 画笔颜色设置为黄色
        mPaint.setColor(Color.parseColor("#e8e8e8"));
        //mPaint.setColor(context.getResources().getColor(R.color.color8e));
        if (drawableId == -1) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        } else {
            mDivider = ContextCompat.getDrawable(context, drawableId);
        }
        setOrientation(orientation);
    }
    public DividerListItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
            //Log.e("onDraw","竖直");
        } else {
           // Log.e("onDraw","水平");
            drawHorizontal(c, parent);
        }

    }

   @Override
   public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
       int childAdapterPosition = parent.getChildAdapterPosition(view);
       int lastCount = parent.getAdapter().getItemCount()-1 ;
       //Log.e("getItemOffsets",childAdapterPosition+"---"+lastCount);
       if (childAdapterPosition == lastCount-1||childAdapterPosition == lastCount) {
           outRect.set(0, 0, 0, 0);
       }else {
           outRect.set(0, 0, 0, 4);
       }

   }
    // 如果是最后一行
    private boolean isLastRow(View view, RecyclerView parent) {
        int position = parent.getChildAdapterPosition(view);
        int count = parent.getAdapter().getItemCount();
        return position == count - 1;
    }

    private boolean isLastButTwoRow(View view, RecyclerView parent) {
        int position = parent.getChildAdapterPosition(view);
        int count = parent.getAdapter().getItemCount();
        return position == count - 2;
    }

    private Paint mPaint;

    public void drawVertical(Canvas c, RecyclerView parent) {
        // 获取RecyclerView的Child view的个数
        int childCount = parent.getChildCount();
        // 遍历每个Item，分别获取它们的位置信息，然后再绘制对应的分割线
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
            int left = padding;
            int top = child.getTop() - 1;
            int right = child.getRight()-padding;
            int bottom = child.getTop();

            c.drawRect(left, top, right, bottom, mPaint);
        }

    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}

