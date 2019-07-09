package me.pandazhang.commentbottombarlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.pandazhang.filepicker.DividerGridItemDecoration;
import me.pandazhang.filepicker.filter.entity.ImageFile;

/**
 * Created by Zc on 2019/07/09.
 * 底部弹出评论框 用于RecycleView 中的Item
 */
@SuppressWarnings("unused")
public class ZBottomSheetPictureBar {
    public static final int ARTICLE_COMMENT_IMAGE_MAX = 3;//最大能添加几张图片


    private View mRootView;
    private EditText mEditText;

    private Context mContext;
    private TextView mBtnCommit;


}
