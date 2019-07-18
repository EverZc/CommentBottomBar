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
    @SerializedName("isdianzan")
    @Expose
    private boolean isFavour;
    @SerializedName("pingluner_name")
    @Expose
    private String pinglunerName;
    @SerializedName("huifuer_name")
    @Expose
    private String userName;
    @SerializedName("huifu_content")
    @Expose
    private String content;
    @SerializedName("be_huifu_content")
    @Expose
    private String contented;
    @SerializedName("time")
    @Expose
    private Long time;

    private List<Picture> picture = new ArrayList<Picture>(ZBottomConstant.ARTICLE_IMAGE_MAX);//评论的回复
    @SerializedName("is_identical")
    @Expose
    private boolean isIdentical;//评论的回复

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
