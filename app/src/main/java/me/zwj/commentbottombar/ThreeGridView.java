package me.zwj.commentbottombar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Created by ZhangChen on 17/8/22.
 */
public class ThreeGridView extends ViewGroup {
    private static final String TAG = "NineGridView";
    private ThreeAdapter mAdapter;
    private OnImageClickListener mListener;
    /**
     * total rows 行
     */
    private int mRows;
    /**
     * total columns 列
     */
    private int mColumns;
    /**
     * child's space
     */
    private int mSpace;
    private int mChildWidth;
    private int mChildOtherWidth;
    private int mChildHeight;
    private int mChildOtherHeigth;


    public ThreeGridView(Context context) {
        this(context, null);
    }

    public ThreeGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        int defaultSpace = (int) getResources().getDimension(R.dimen.nine_grid_gap);
            mSpace = defaultSpace;
    }

    public void setAdapter(ThreeAdapter adapter) {
        if (adapter == null || adapter.getCount() <= 0) {
            removeAllViews(); //避免listview复用显示脏数据
            return;
        }

        int oldCount = getChildCount();
        int newCount = adapter.getCount();
        removeScrapViews(oldCount, newCount);
        mAdapter = adapter;
        initMatrix(newCount);
        addChildren(adapter);
    }

    private void removeScrapViews(int oldCount, int newCount) {
        if (newCount < oldCount) {
            removeViews(newCount - 1, oldCount - newCount);
        }
    }

    private void initMatrix(int length) {

            mRows = 1;
            mColumns = 3;

    }

    private void addChildren(ThreeAdapter adapter) {
        int childCount = getChildCount();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            boolean hasChild = i < childCount;
            //简单的回收机制,主要是为ListView做优化
            View recycleView = hasChild ? getChildAt(i) : null;
            View child = adapter.getView(i, recycleView);

            if (child != recycleView) {
                if (hasChild) { //为了防止有的逗比不复用RecycleView做处理
                    removeView(recycleView);
                }

                addView(child);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int childCount = getChildCount();
        if (childCount <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        final int minW = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int width = resolveSizeAndState(minW, widthMeasureSpec, 1);
        int availableWidth = width - getPaddingLeft() - getPaddingRight();
        int height = 0;
        switch (childCount) {
            case 1:
                mChildWidth = (availableWidth - mSpace * 2) / 4;
                mChildHeight = mChildWidth;
                break;
            case 2:
                mChildWidth = (availableWidth - mSpace * 2) / 4;
                mChildHeight = mChildWidth;
                break;
            case 3:
                mChildWidth = (availableWidth - mSpace *2) / 4;
                mChildHeight = mChildWidth;
                break;
            case 4:
                mChildWidth = (availableWidth - mSpace * (mColumns - 1)) / 2;
                mChildHeight = mChildWidth;
                break;
            case 5:
                mChildWidth = (availableWidth - mSpace * 1) / 2;
                mChildOtherWidth = (availableWidth - mSpace * 2) / 3;
                mChildHeight = mChildWidth;
                mChildOtherHeigth = mChildOtherWidth;
                break;
            case 6:
                mChildWidth = (availableWidth - mSpace * (mColumns - 1)) / 3;
                mChildHeight = mChildWidth;
                break;
            case 7:
                mChildWidth = (availableWidth - mSpace ) / 2;
                mChildOtherWidth = (availableWidth - mSpace * 2) / 3;
                mChildHeight = mChildWidth;
                mChildOtherHeigth = mChildOtherWidth;
                break;
            case 8:
                mChildWidth = (availableWidth - mSpace ) / 2;
                mChildOtherWidth = (availableWidth - mSpace * 2) / 3;
                mChildHeight = mChildWidth;
                mChildOtherHeigth = mChildOtherWidth;
                break;
            case 9:
                mChildWidth = (availableWidth - mSpace * (mColumns - 1)) / 3;
                mChildHeight = mChildWidth;
                break;
        }
        if (childCount == 5) {
            height = mChildHeight + mChildOtherHeigth + mSpace;
        } else if (childCount == 6) {
            height = mChildHeight * 3 + mSpace * 2;

        } else if (childCount == 7) {
            height = mChildHeight + mChildOtherHeigth + mSpace +mChildHeight+mSpace;
        } else if (childCount == 8) {
            height=mChildHeight+mChildOtherHeigth*2+mSpace*2;
        } else {
            height = mChildHeight * mRows + mSpace * (mRows - 1);
        }
        setMeasuredDimension(width, height + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren();
    }

    protected void layoutChildren() {
        if (mRows <= 0 || mColumns <= 0) {
            return;
        }

        final int childCount = getChildCount();
        switch (childCount) {
            case 1:
                for (int i = 0; i < childCount; i++) {
                    layoutChildrenView(i);
                }
                break;
            case 2:
                for (int i = 0; i < childCount; i++) {
                    layoutChildrenView(i);
                }
                break;
            case 3:
                for (int i = 0; i < childCount; i++) {
                    layoutChildrenView(i);
                }
                break;
            case 4:
                for (int i = 0; i < childCount; i++) {
                    layoutChildrenView(i);
                }
                break;


            case 9:
                for (int i = 0; i < childCount; i++) {
                    layoutChildrenView(i);
                }
                break;
        }
    }





    public void layoutChildrenView(int i) {
        ImageView view = (ImageView) getChildAt(i);

        int row = i / mColumns;
        int col = i % mColumns;
        int left = (mChildWidth + mSpace) * col + getPaddingLeft();
        int top = (mChildHeight + mSpace) * row + getPaddingTop();
        int right = left + mChildWidth;
        int bottom = top + mChildHeight;
        view.layout(left, top, right, bottom);
        final int position = i;
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onImageCilcked(position, v);
                }
            }
        });
    }

    @Override
    public void addView(View child) {
        if (!(child instanceof ImageView)) {
            throw new ClassCastException("addView(View child) NineGridView只能放ImageView");
        }

        setChildLayoutParams(child);
        super.addView(child);
    }

    /**
     * 给child view设置绝对大小,减少child view measure次数
     *
     * @param view
     */
    private void setChildLayoutParams(View view) {
        LayoutParams params = view.getLayoutParams();
        if (params != null) {
            params.width = mChildWidth;
            params.height = mChildHeight;
        }
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        mListener = listener;
    }

    public void setSpace(int space) {
        mSpace = space;
    }

    public int getSpace() {
        return mSpace;
    }

    public int getChildWidth() {
        return mChildWidth;
    }

    public int getChildHeight() {
        return mChildHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
    }

    public interface NineGridAdapter<T> {
        int getCount();

        T getItem(int position);

        View getView(int positon, View recycleView);
    }

    public interface OnImageClickListener {
        void onImageCilcked(int position, View view);
    }
}

