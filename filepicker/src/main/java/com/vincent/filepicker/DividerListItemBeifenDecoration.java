package com.vincent.filepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vincent Woo
 * Date: 2016/10/25
 * Time: 14:44
 */

public class DividerListItemBeifenDecoration extends RecyclerView.ItemDecoration {
    private int mOrientation;
    private Drawable mDivider;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public DividerListItemBeifenDecoration(Context context, int orientation, int drawableId) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("Orientation Only Support LinearLayoutManager.VERTICAL " +
                    "or LinearLayoutManager.HORIZONTAL");
        }
        mOrientation = orientation;

        if (drawableId == -1) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        } else {
            mDivider = ContextCompat.getDrawable(context, drawableId);
        }
    }

    public DividerListItemBeifenDecoration(Context context, int orientation) {
        this(context, orientation, -1);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDivider(c, parent);
        } else {
            drawVerticalDivider(c, parent);
        }
    }



   /* @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int getItemCount =  parent.getAdapter().getItemCount();

        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            if (isLastRow(view, parent) || isLastButTwoRow(view, parent)) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            }
        }
        else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
       }
    }*/
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

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        int itemCount = parent.getAdapter().getItemCount();
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            //如果是最后一行不画下边
            outRect.set(0, 0, 0, (itemPosition+1) == itemCount ? 0 : mDivider.getIntrinsicHeight());
        } else {
            //如果是最后一行不画右边
            outRect.set(0, 0, (itemPosition + 1) == itemCount ? 0 : mDivider.getIntrinsicWidth(), 0);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount-2; i++) {

            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawVerticalDivider(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

                final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
