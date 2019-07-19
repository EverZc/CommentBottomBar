package me.zwj.commentbottombar;

import java.io.Serializable;

public class Picture  implements Serializable {
    public String pictureUrl;
    public String str;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
