package me.zwj.commentbottombar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.pandazhang.commentbottombarlib.ZBottomConstant;

public class ReplyComment implements Serializable {

    @SerializedName("huifuer_touxiang")
    @Expose
    private String avatar;
    @SerializedName("dianzan_count")
    @Expose
    private int favourCounr;

    @SerializedName("huifuer_name")
    @Expose
    private String userName;
    @SerializedName("huifu_content")
    @Expose
    private String content;

    @SerializedName("time")
    @Expose
    private Long time;

    private List<Picture> picture = new ArrayList<Picture>(ZBottomConstant.ARTICLE_IMAGE_MAX);//评论的回复

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getFavourCounr() {
        return favourCounr;
    }

    public void setFavourCounr(int favourCounr) {
        this.favourCounr = favourCounr;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<Picture> getPicture() {
        return picture;
    }

    public void setPicture(List<Picture> picture) {
        this.picture = picture;
    }
}
