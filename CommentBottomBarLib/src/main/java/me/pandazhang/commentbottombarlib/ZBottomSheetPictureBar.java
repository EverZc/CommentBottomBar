package me.pandazhang.commentbottombarlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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

public class ZBottomSheetPictureBar {
    private Context mContext;
    private View mRootView;
    private EditText mEditText;
    private TextView mBtnCommit;
    private RecyclerView mRecyclerView;

    private ZBottomDialog mDialog;
    private ZBottomSheetAdapter mAdapter;

    private boolean isFirstMax=true;
    private ArrayList<ImageFile> mImages = new ArrayList<>(ZBottomConstant.ARTICLE_IMAGE_MAX);
    private OnSheetBarOnClickListener mListener;

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
        mDialog = new ZBottomDialog(mContext);
        mDialog.setContentView(mRootView);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                hideSoftInput(mContext, mEditText);
                mDialog.hide();
            }
        });
        mEditText = (EditText) mRootView.findViewById(R.id.et_comment);
        mBtnCommit = (TextView) mRootView.findViewById(R.id.btn_comment);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        initRecycler();
        showSoftInput(mEditText,mContext);

        mBtnCommit.setEnabled(false);
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null){
                    mListener.onCommitClick(mImages,mEditText);
                    mBtnCommit.setEnabled(false);
                }
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
        mAdapter = new ZBottomSheetAdapter(mContext, mImages, ZBottomConstant.ARTICLE_IMAGE_MAX);
        mAdapter.setOnReleaseImageListener(new ZBottomSheetHolder.OnReleaseImageListener() {
            @Override
            public void onAddClick() {
                if (mListener!=null){
                    mListener.onAddClick();
                }
            }

            @Override
            public void onDelClick(ImageFile imageFile,int position) {
                if (mImages.contains(imageFile)) {
                    mImages.remove(imageFile);
                    mAdapter.notifyDataSetChanged();
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
                if (mListener!=null){
                    mListener.onPictureClick(positon);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    //拼接评论文字
    public void appendText(String text) {
        if (!TextUtils.isEmpty(text)) {
            mEditText.append(text);
        }
    }

    //弹出评论框并填写评论的hint
    public void show(String hint) {
        mEditText.setHint(hint);
        mDialog.show();
    }

    //隐藏评论弹出框，并隐藏软键盘
    public void dismiss() {
        hideSoftInput(mContext, mEditText);
        mDialog.dismiss();
    }

    //添加图片
    public void setImages(ArrayList<ImageFile> images) {
        mBtnCommit.setEnabled(true);
        mAdapter.addData(images);
        mAdapter.notifyDataSetChanged();
    }

    //获取当前评论框内的图片
    public ArrayList<ImageFile> getImageDatas(){
        return mImages;
    }



    //获取评论的内容
    public String getCommentText() {
        String result = "";
        if (mEditText.getText().toString().trim() != null) {
            Pattern p = Pattern.compile("(\r?\n(\\s*\r?\n)+)");
            Matcher m = p.matcher(mEditText.getText().toString().trim());
            result = m.replaceAll("\r\n\n");
        }
        return result;
    }

    public EditText getEditText() {
        return mEditText;
    }

    //清理评论文本内容以及评论的图片内容。
    public void refresh(){
        mEditText.setText("");
        mImages.clear();
        mAdapter.notifyDataSetChanged();
    }

    public interface  OnSheetBarOnClickListener{
        //添加图片
        void onAddClick();
        //评论提交
        void onCommitClick(ArrayList<ImageFile> images,EditText content);

        void onPictureClick(int positon);
    }

    public void setOnSeetBarOnClickListener(OnSheetBarOnClickListener listener){
        this.mListener=listener;
    }

    //动态显示软键盘
    private  void showSoftInput(EditText edit,Context context) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.showSoftInput(edit, 0);
    }

    //动态隐藏软键盘
    private void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
