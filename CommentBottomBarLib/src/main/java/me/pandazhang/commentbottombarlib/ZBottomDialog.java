package me.pandazhang.commentbottombarlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Description: 自定义buttomsheet行为的dialog
 * @Author: Zwj
 * @CreateDate: 2019/5/10 15:00
 */
public class ZBottomDialog extends BottomSheetDialog {
    private BottomSheetBehavior behavior;

    public ZBottomDialog(@NonNull Context context) {
        super(context, R.style.bottomSheetEdit);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initialize(view);
    }
    private void initialize(final View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
        behavior = (BottomSheetBehavior) params.getBehavior();
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                }
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.d("Bottom Sheet Behaviour", "STATE_COLLAPSED 折叠");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.d("Bottom Sheet Behaviour", "STATE_DRAGGING 过渡状态");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.d("Bottom Sheet Behaviour", "STATE_EXPANDED 完全展开");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.d("Bottom Sheet Behaviour", "STATE_HIDDEN  隐藏状态");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.d("Bottom Sheet Behaviour", "STATE_SETTLING 自由滑动");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }
}
