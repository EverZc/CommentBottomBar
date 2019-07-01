package com.vincent.filepicker.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vincent.filepicker.DividerGridItemDecoration;
import com.vincent.filepicker.FilePicker;
import com.vincent.filepicker.R;
import com.vincent.filepicker.adapter.ImagePickHeadAdapter;
import com.vincent.filepicker.adapter.OnSelectStateListener;
import com.vincent.filepicker.filter.FileFilter;
import com.vincent.filepicker.filter.callback.FilterResultCallback;
import com.vincent.filepicker.filter.entity.Directory;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.vincent.filepicker.FilePicker.RESULT_PICK_IMAGE;

/**
 * 用于头像选择的列表
 * Created by Zc
 * Date: 2018/04/18
 * Time: 16:41
 */

public class ImagePickSetImageActivityPicker extends PickerBaseActivity {
    public static final String IS_NEED_CAMERA = "IsNeedCamera";
    public static final int DEFAULT_MAX_NUMBER = 9; //最大放置图片的数量
    public static final int COLUMN_NUMBER = 4;  //列的数量
    private int mMaxNumber;
    private int mCurrentNumber = 0;
    private Toolbar mTbImagePick;
    private RecyclerView mRecyclerView;
    private ImagePickHeadAdapter mAdapter;
    private boolean isNeedCamera;
    private ArrayList<ImageFile> mSelectedList = new ArrayList<>(); //选择的图片的集合
    private TextView mAlbum; //相簿
    private TextView mPreview; //点击预览
    private TextView mConfirm;
    private int[] mWithAspectRatio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set_image_pick);
        mMaxNumber = getIntent().getIntExtra(FilePicker.MAX_NUMBER, DEFAULT_MAX_NUMBER);
        isNeedCamera = getIntent().getBooleanExtra(IS_NEED_CAMERA, false);
        mWithAspectRatio = getIntent().getIntArrayExtra(FilePicker.WITH_ASPECT_RATIO);
        //已经选中了的图片
        ArrayList<ImageFile> selecteds = getIntent().getParcelableArrayListExtra(RESULT_PICK_IMAGE);
        if (selecteds != null) {
            mSelectedList.addAll(selecteds);
        }
        initView();
        super.onCreate(savedInstanceState);
    }

    @Override
    void permissionGranted() {
        loadData();
    }

    private void initView() {
        mTbImagePick = (Toolbar) findViewById(R.id.tb_image_pick);
        //显示当前的数量
        mTbImagePick.setTitle("  ");
       // mTbImagePick.setTitle(mCurrentNumber + "/" + mMaxNumber);
        setSupportActionBar(mTbImagePick);
        mTbImagePick.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAlbum = (TextView) findViewById(R.id.btn_album);
        mAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开相册
                Intent intent = new Intent(ImagePickSetImageActivityPicker.this, ImageAlbumActivityPicker.class);
                intent.putParcelableArrayListExtra(FilePicker.RESULT_PICK_IMAGE, mSelectedList);
                startActivityForResult(intent, FilePicker.REQUEST_CODE_ALBUM_IMAGE);
            }
        });
        mPreview = (TextView) findViewById(R.id.btn_preview);
        mPreview.setEnabled(false);
        mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageBrowserHeadActivityPicker.launchActivity(
                        ImagePickSetImageActivityPicker.this, mSelectedList.size(), mSelectedList, mWithAspectRatio);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_image_pick);
        GridLayoutManager layoutManager = new GridLayoutManager(this, COLUMN_NUMBER);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mAdapter = new ImagePickHeadAdapter(this, isNeedCamera, mMaxNumber);
        mAdapter.setCurrentNumber(mCurrentNumber);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnSelectStateListener(new OnSelectStateListener<ImageFile>() {
            @Override
            public void OnSelectStateChanged(boolean state, ImageFile file) {
                mSelectedList.clear();
                mSelectedList.add(file);
                ImageBrowserHeadActivityPicker.launchActivity(
                        ImagePickSetImageActivityPicker.this, mSelectedList.size(), mSelectedList, mWithAspectRatio);

              /*  if (mMaxNumber == 1) {//单选
                    mSelectedList.clear();
                    mSelectedList.add(file);
                    mCurrentNumber = state ? 1 : 0;
                } else {
                    if (state) {
                        mSelectedList.add(file);
                        mCurrentNumber++;
                    } else {
                        mSelectedList.remove(file);
                        mCurrentNumber--;
                    }
                }
                mTbImagePick.setTitle("  ");
                //mTbImagePick.setTitle(mCurrentNumber + "/" + mMaxNumber);
                mPreview.setEnabled(mSelectedList.size() > 0);*/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FilePicker.REQUEST_CODE_TAKE_IMAGE:
                if (resultCode == RESULT_OK) {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File file = new File(mAdapter.mImagePath);
                    Uri contentUri = Uri.fromFile(file);
                    mediaScanIntent.setData(contentUri);
                    sendBroadcast(mediaScanIntent);
                    loadData();
                }
                break;
            case FilePicker.REQUEST_CODE_BROWSER_IMAGE:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK, data);
                    finish();
                }
            case FilePicker.REQUEST_CODE_ALBUM_IMAGE:
                if (resultCode == RESULT_OK) {
                    ArrayList<ImageFile> list = data.getParcelableArrayListExtra(RESULT_PICK_IMAGE);
                    mAdapter.refresh(list);
                }
                break;
        }
    }

    private void loadData() {
        FileFilter.getImages(this, new FilterResultCallback<ImageFile>() {
            @Override
            public void onResult(List<Directory<ImageFile>> directories) {
                List<ImageFile> list = new ArrayList<>();
                for (Directory<ImageFile> directory : directories) {
                    list.addAll(directory.getFiles());
                }
//                if (mSelectedList != null) {
//                    for (int i = 0; i < mSelectedList.size(); i++) {
//                        ImageFile imageFile = mSelectedList.get(i);
//                        for (int j = 0; j < list.size(); j++) {
//                            ImageFile imageFile1 = list.get(i);
//                            if (TextUtils.equals(imageFile.getPath(), imageFile1.getPath())) {
//                                mCurrentNumber++;
//                                imageFile1.setSelected(true);
//                            }
//                        }
//                    }
//                }
                Collections.sort(list, new FileComparator());
                mAdapter.refresh(list);
            }
        });
    }

    private class FileComparator implements Comparator {


        @Override
        public int compare(Object o1, Object o2) {
            ImageFile s1 = (ImageFile) o1;
            ImageFile s2 = (ImageFile) o2;
            if(s1.getDate()<s2.getDate()){
                return 1;//最后修改的照片在前
            }else if (s1.getDate()==s2.getDate()){
                return 0;
            }else {
                return -1;
            }
        }

    }
}
