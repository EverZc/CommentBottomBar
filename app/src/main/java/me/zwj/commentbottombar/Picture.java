package me.zwj.commentbottombar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Picture  implements Serializable {
    @SerializedName("picture_url")
    @Expose
    public String pictureUrl;
    @SerializedName("str")
    @Expose
    public String str;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
