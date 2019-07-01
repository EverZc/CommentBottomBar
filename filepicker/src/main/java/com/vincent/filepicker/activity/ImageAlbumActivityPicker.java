package com.vincent.filepicker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vincent.filepicker.DividerListItemDecoration;
import com.vincent.filepicker.R;
import com.vincent.filepicker.adapter.ImageAlbumAdapter;
import com.vincent.filepicker.adapter.OnItemClickListener;
import com.vincent.filepicker.filter.FileFilter;
import com.vincent.filepicker.filter.callback.FilterResultCallback;
import com.vincent.filepicker.filter.entity.AlbumFile;
import com.vincent.filepicker.filter.entity.Directory;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;
import static com.vincent.filepicker.FilePicker.RESULT_PICK_IMAGE;

/**
 * 选择相册列表
 * Created by Zc on 2017/4/27.
 */

public class ImageAlbumActivityPicker extends PickerBaseActivity implements OnItemClickListener<AlbumFile> {

    private static final String TAG = ImageAlbumActivityPicker.class.getCanonicalName();

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ArrayList<AlbumFile> mAblumFiles = new ArrayList<>();
    private ImageAlbumAdapter mAdapter;
    private ArrayList<ImageFile> mSelecteds;
    private TextView mTitle;

    @Override
    void permissionGranted() {
        loadData();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_ablum);
        Intent intent = getIntent();
        mSelecteds = intent.getParcelableArrayListExtra(RESULT_PICK_IMAGE);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.tb_image_pick);
        mTitle= (TextView) findViewById(R.id.tv_title);
        mTitle.setText("相册");
        setSupportActionBar(mToolbar);
        setTitle("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerListItemDecoration itemDecoration = new DividerListItemDecoration(this, VERTICAL, R.drawable.common_divider_padding14_shape);
        mRecyclerView.addItemDecoration(itemDecoration);
        mAdapter = new ImageAlbumAdapter(this, mAblumFiles);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AlbumFile item, int position) {
        ArrayList<ImageFile> images = item.getImages();
        //将被已经被选中的图片选中
        for (int i = 0; i < mSelecteds.size(); i++) {
            ImageFile imageFile = mSelecteds.get(i);
            if (images.contains(imageFile)) {
                images.get(images.indexOf(imageFile)).setSelected(true);
            }
        }
        Intent intent = getIntent();
        intent.putParcelableArrayListExtra(RESULT_PICK_IMAGE, images);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void loadData() {
        FileFilter.getImages(this, new FilterResultCallback<ImageFile>() {
            @Override
            public void onResult(List<Directory<ImageFile>> directories) {
                AlbumFile allAlbumFile = new AlbumFile();
                int totalCount = 0;

               /* for (int i = 0; i < directories.size(); i++) {
                    if (i==0){
                        AlbumFile albumFile = new AlbumFile();
                        albumFile.setImages(directories.get(0).getFiles());
                        mAblumFiles.add(albumFile);
                    }
                    //Zc自己做的代码
                }*/
                 for (Directory<ImageFile> director : directories) {
                    AlbumFile albumFile = new AlbumFile();
                    albumFile.setImages(director.getFiles());
                    allAlbumFile.setImages(director.getFiles());
                    mAblumFiles.add(albumFile);
                    totalCount += director.getFiles().size();
                }
                /*
               allAlbumFile.setBucketName("所有图片");
                allAlbumFile.setCover(directories.get(0).getFiles().get(0).getPath());
                allAlbumFile.setCount(totalCount);
                //将新的 全部加在的这个相册集合添加在第一个位置上面
                mAblumFiles.add(0, allAlbumFile);*/
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
