package me.zwj.commentbottombar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Picture {
    @SerializedName("picture_url")
    @Expose
    public String pictureUrl;
    @SerializedName("str")
    @Expose
    public String str;
}
