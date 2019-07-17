package me.zwj.commentbottombar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReplyComment {

    @SerializedName("huifuer_touxiang")
    @Expose
    public String avatar;
    @SerializedName("dianzan_count")
    @Expose
    public int favourCounr;
    @SerializedName("isdianzan")
    @Expose
    public boolean isFavour;
    @SerializedName("pingluner_name")
    @Expose
    public String pinglunerName;
    @SerializedName("huifuer_name")
    @Expose
    public String userName;
    @SerializedName("huifu_content")
    @Expose
    public String content;
    @SerializedName("be_huifu_content")
    @Expose
    public String contented;
    @SerializedName("time")
    @Expose
    public Long time;
    @SerializedName("picture")
    @Expose
    public List<Picture> picture=new ArrayList<>();//评论的回复
    @SerializedName("is_identical")
    @Expose
    public boolean isIdentical;//评论的回复

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

    public boolean isFavour() {
        return isFavour;
    }

    public void setFavour(boolean favour) {
        isFavour = favour;
    }

    public String getPinglunerName() {
        return pinglunerName;
    }

    public void setPinglunerName(String pinglunerName) {
        this.pinglunerName = pinglunerName;
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

    public String getContented() {
        return contented;
    }

    public void setContented(String contented) {
        this.contented = contented;
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

    public boolean isIdentical() {
        return isIdentical;
    }

    public void setIdentical(boolean identical) {
        isIdentical = identical;
    }
}
