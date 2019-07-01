package com.vincent.filepicker.filter.entity;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zc on 2017/4/27.
 */

public class AlbumFile extends BaseFile {

    private String cover;
    private int count;
    private int orientation;   //0, 90, 180, 270

    private ArrayList<ImageFile> images = new ArrayList<>();

    public ArrayList<ImageFile> getImages() {
        return images;
    }

    public void setImages(List<ImageFile> images) {
        this.images.addAll(images);
        ImageFile imageFile = images.get(0);
        setCover(imageFile.getPath());
        setBucketName(imageFile.getBucketName());
        setCount(images.size());
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AlbumFile{" +
                "cover='" + cover + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.cover);
        dest.writeInt(this.count);
        dest.writeInt(this.orientation);
        dest.writeTypedList(this.images);
    }

    public AlbumFile() {
    }

    protected AlbumFile(Parcel in) {
        this.cover = in.readString();
        this.count = in.readInt();
        this.orientation = in.readInt();
        this.images = in.createTypedArrayList(ImageFile.CREATOR);
    }

    public static final Creator<AlbumFile> CREATOR = new Creator<AlbumFile>() {
        @Override
        public AlbumFile createFromParcel(Parcel source) {
            return new AlbumFile(source);
        }

        @Override
        public AlbumFile[] newArray(int size) {
            return new AlbumFile[size];
        }
    };
}
