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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.pandazhang.filepicker.DividerGridItemDecoration;
import me.pandazhang.filepicker.MyToastPK;
import me.pandazhang.filepicker.filter.entity.ImageFile;

/**
 * Created by Zwj on 2019/07/09.
 * 底部弹出评论框 用于RecycleView 中的Item
 */
@SuppressWarnings("unused")
public class ZBottomSheetPictureBar {
    public static final int ARTICLE_COMMENT_IMAGE_MAX = 3;//最大能添加几张图片

    private View mRootView;
    private EditText mEditText;

    private Context mContext;
    private TextView mBtnCommit;
    private ZBottomDialog mDialog;
    private RecyclerView mRecyclerView;
    private ZBottomSheetAdapter mAdapter;
    private boolean isFirstMax=true;
    private ArrayList<ImageFile> mImages = new ArrayList<>(ZBottomConstant.ARTICLE_COMMENT_IMAGE_MAX);
    // private EmojiView mEmojiView;
    private ZBottomSheetPictureBar(Context context) {
        this.mContext = context;
    }

    @SuppressLint("InflateParams")
    public static ZBottomSheetPictureBar delegation(Context context) {
        ZBottomSheetPictureBar bar = new ZBottomSheetPictureBar(context);
        bar.mRootView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet, null, false);
        bar.initView();
        return bar;
    }

    private void initView() {
        mEditText = (EditText) mRootView.findViewById(R.id.et_comment);
        mBtnCommit = (TextView) mRootView.findViewById(R.id.btn_comment);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        initRecycler();
        showSoftInput(mEditText,mContext);
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null){
                    mListener.onCommitClick();
                    mBtnCommit.setEnabled(false);
                }
            }
        });
        mBtnCommit.setEnabled(false);
        mDialog = new ZBottomDialog(mContext);
        mDialog.setContentView(mRootView);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                hideSoftInput(mContext, mEditText);
                mDialog.hide();
                if (mListener!=null){
                    mListener.onDissmiss();
                }
//                mFrameLayout.setVisibility(View.GONE);
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mImages.size()>0||mEditText.getText().length()>0){
                    if (s.length()>1000){

                        if (isFirstMax){
                            MyToastPK.showError("评论最大字数限制1000字",mContext);

                            isFirstMax=false;
                        }


                        mBtnCommit.setEnabled(false);
                    }else {
                        mBtnCommit.setEnabled(true);
                        isFirstMax=true;
                    }
                }else {
                    mBtnCommit.setEnabled(false);
                }
            }
        });
    }

    private void initRecycler() {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerGridItemDecoration itemDecoration = new DividerGridItemDecoration(mContext, R.drawable.image_divider_shape);
        mRecyclerView.addItemDecoration(itemDecoration);
        mAdapter = new ZBottomSheetAdapter(mContext, mImages, ZBottomConstant.ARTICLE_COMMENT_IMAGE_MAX);
        mAdapter.setOnReleaseImageListener(new ZBottomSheetHolder.OnReleaseImageListener() {
            @Override
            public void onAddClick() {
                if (mListener!=null){
                    mListener.onAddClick();
                }
            }

            @Override
            public void onDelClick(ImageFile imageFile,int position) {
                if (mListener!=null){
                    mListener.onDelClick(imageFile,position);
                }
                if (mImages.size()>0||mEditText.getText().length()>0){
                    if (mEditText.getText().length()>500){
                        mBtnCommit.setEnabled(false);
                    }else {
                        mBtnCommit.setEnabled(true);
                    }
                }else {
                    mBtnCommit.setEnabled(false);
                }

            }

            @Override
            public void onClick(int positon) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    public void appendText(String text) {
        if (!TextUtils.isEmpty(text)) {
            mEditText.append(text);
        }
    }

    public void show(String hint) {
        mDialog.show();
        if (!"添加评论".equals(hint)) {
            mEditText.setHint(hint + "");
        }
    }

    public void dismiss() {
        hideSoftInput(mContext, mEditText);
        mDialog.dismiss();
        if (mListener!=null){
            mListener.onDissmiss();
        }
    }

    public void setCommitListener(View.OnClickListener listener) {
        mBtnCommit.setOnClickListener(listener);
    }

    public interface  OnSheetBarOnClickListener{
        void onAddClick();
        void onDelClick(ImageFile imageFile,int position);
        void onCommitClick();
        void onDissmiss();
    }
    private OnSheetBarOnClickListener mListener;

    public void setOnSeetBarOnClickListener(OnSheetBarOnClickListener listener){
        this.mListener=listener;
    }

    public void setImages(ArrayList<ImageFile> mImagess) {
        mBtnCommit.setEnabled(true);
        mAdapter.addData(mImagess);
        mAdapter.notifyDataSetChanged();

    }

    public ArrayList<ImageFile> getAdapterData(){
        return mImages;
    }

    public void adapterNotifyDataSetChanged(){
        mAdapter.notifyDataSetChanged();
    }

    public void handleSelectFriendsResult(Intent data) {
        String names[] = data.getStringArrayExtra("names");
        if (names != null && names.length > 0) {
            String text = "";
            for (String n : names) {
                text += "@" + n + " ";
            }
            mEditText.getText().insert(mEditText.getSelectionEnd(), text);
        }
    }

    public EditText getEditText() {
        return mEditText;
    }

    public String getCommentText() {
        String result = "";
        if (mEditText.getText().toString().trim() != null) {
            Pattern p = Pattern.compile("(\r?\n(\\s*\r?\n)+)");
            Matcher m = p.matcher(mEditText.getText().toString().trim());
            result = m.replaceAll("\r\n\n");
        }
        return result;
    }

    public TextView getBtnCommit() {
        return mBtnCommit;
    }

    /**
     * 动态显示软键盘
     *
     * @param edit 输入框
     */
    public static void showSoftInput(EditText edit,Context context) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.showSoftInput(edit, 0);
    }

    /**
     * 动态隐藏软键盘
     *
     * @param context 上下文
     * @param view    视图
     */
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
