package me.pandazhang.commentbottombarlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
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
        //initWindow();
    }
    private void initialize(final View view) {

        ViewGroup parent = (ViewGroup) view.getParent();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
        behavior = (BottomSheetBehavior) params.getBehavior();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }


}
