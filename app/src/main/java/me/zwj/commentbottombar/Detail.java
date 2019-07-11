package me.zwj.commentbottombar;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public class Detail implements Parcelable {

    @SerializedName("picture_text")
    @Expose
    public String picture_text;//发布者性别
    @SerializedName("text")
    @Expose
    public String text;//发布者性别
    @SerializedName("releaser_sex")
    @Expose
    public String releaseSex;//发布者性别
    @SerializedName("sex")
    @Expose
    public String sex;
    @SerializedName("unit")
    @Expose
    public String unit;//单位

    @SerializedName("muban_Tag")
    @Expose
    public int muban_Tag;//单位

    @SerializedName("uid")
    @Expose
    public String id;//内容ID
    @SerializedName(value = "picture", alternate = "pictures")
    @Expose
    public List<Picture> pictures;//封面图片
    @SerializedName(value = "content_picture")
    @Expose
    public List<Picture> content_pictures;//封面图片

    @SerializedName("title")
    @Expose
    public String title;//标题
    @SerializedName("content")
    @Expose
    public String content;//内容
    @SerializedName("dianzan_count")
    @Expose
    public int favourCount; //点赞数量
    @SerializedName("pinglun_count")
    @Expose
    public int commentCount; // 评论数量
    @SerializedName("dashang_count")
    @Expose
    public String rewardCount; //敬酒数量
    @SerializedName("releaser_id")
    @Expose
    public String authorId; //作者ID
    @SerializedName("releaser_name")
    @Expose
    public String authorName;//作者姓名
    @SerializedName("releaser_touxiang")
    @Expose
    public String authorAvatar; //作者头像
    @SerializedName("releaser_qianming")
    @Expose
    public String authorIntroduction; //作者签名

    @SerializedName("dianzan_hongbao")
    @Expose
    public int favourMoney; //点赞红包
    @SerializedName("fenxiang_hongbao")
    @Expose
    public int shareMonery; //分享红包
    @SerializedName("yuedu")
    @Expose
    public String readCount;//阅读量
    @SerializedName("time")
    @Expose
    public String time;//发表时间
    @SerializedName("pinglun")
    @Expose
    public List<Comment> comments = null;//评论
    @SerializedName("tuijian")
    @Expose
    public List<Recommend> recommends = null; // 内容推荐
    @SerializedName("guanggao_url")
    @Expose
    public String adUrl;
    @SerializedName("share_url")
    @Expose
    public String shareUrl;

    @SerializedName("biaoqian")
    @Expose
    public List<Label> label;
    @SerializedName("isguanzhu")
    @Expose
    public boolean isCare;//是否有关注作者
    @SerializedName("dianpu_id")
    @Expose
    public String shopId;
    @SerializedName("dianpu_name")
    @Expose
    public String shopName;//店铺名称
    @SerializedName("dianpu_pinglun_count")
    @Expose
    public int shopCommentCount;//店铺评论数量
    @SerializedName("price")
    @Expose
    public String money;//
    @SerializedName("dianpu_touxiang")
    @Expose
    public String shopAvatar;//店铺头像
    @SerializedName("dianpu_address")
    @Expose
    public String shopAddress;//店铺地址
    @SerializedName("renzheng_Tag")
    @Expose
    public int isAttestation;//店铺认证
    @SerializedName("isdianzan")
    @Expose
    public boolean isFavour;//是否点赞
    @SerializedName("shoucang_Tag")
    @Expose
    public boolean isCollect;//是否收藏

    @SerializedName("have_dianzan_hongbao")
    @Expose
    public boolean haveDianzanHongbao;//点赞红包
    @SerializedName("have_fenxiang_hongbao")
    @Expose
    public boolean haveFenxiangHongbao;//分享红包

    @SerializedName("guangjie_fenlei_Tag")
    @Expose
    public int isServer;//是否是服务
    @SerializedName("huifu")
    @Expose
    public List reply;
    @SerializedName("freight")
    @Expose
    public double freight;//快递费
    @SerializedName("shuliang")
    @Expose
    public int repertory;//库存
    @SerializedName("phone")
    @Expose
    public String phone;//电话

    @SerializedName("type")
    @Expose
    public String type;//type
    @SerializedName("is_can_pinglun")
    @Expose
    public int isCanPinglun;
    @SerializedName("tuijianday")
    @Expose
    public String tuijianday;//电话
    @SerializedName("shenhe")
    @Expose
    public String shenhe;//电话

    @SerializedName("isRenzheng")
    @Expose
    public boolean isRenzheng;//电话




    public boolean isServer(){
        return isServer == 2;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", favour=" + favourCount +
                ", commentCount=" + commentCount +
                ", rewardCount=" + rewardCount +
                ", authorId='" + authorId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorAvatar='" + authorAvatar + '\'' +
                ", favourMoney=" + favourMoney +
                ", shareMonery=" + shareMonery +
                ", readCount=" + readCount +
                ", time=" + time +
                ", comments=" + comments +
                ", recommends=" + recommends +
                ", adUrl='" + adUrl + '\'' +
                ", label=" + label +
                ", isCare=" + isCare +
                ", shopName=" + shopName +
                ", shopCommentCount=" + shopCommentCount +
                ", money=" + money +
                '}';
    }

    public static class Favour implements Parcelable {
        @SerializedName("dianzan_count")
        @Expose
        public int favourCount;
        @SerializedName("touxiang_list")
        @Expose
        public List<User> users = null;//点赞用户

        public Favour() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.favourCount);
            dest.writeTypedList(this.users);
        }

        protected Favour(Parcel in) {
            this.favourCount = in.readInt();
            this.users = new ArrayList<User>();

        }

        public static final Creator<Favour> CREATOR = new Creator<Favour>() {
            @Override
            public Favour createFromParcel(Parcel source) {
                return new Favour(source);
            }

            @Override
            public Favour[] newArray(int size) {
                return new Favour[size];
            }
        };
    }

    public static class User implements Parcelable {
        @SerializedName("yonghu_id")
        @Expose
        public String id; // 用户ID
        @SerializedName("dianzan_touxiang")
        @Expose
        public String avatar; //用户头像

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.avatar);
        }

        public User() {
        }

        protected User(Parcel in) {
            this.id = in.readString();
            this.avatar = in.readString();
        }

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel source) {
                return new User(source);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };
    }

    public static class FavourMoney implements Parcelable {
        @SerializedName("count")
        @Expose
        public String count;//红包数量
        @SerializedName("price")
        @Expose
        public String price;//价格
        @SerializedName("hongbao_Tag")
        @Expose
        public String hongbaoTag;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.count);
            dest.writeString(this.price);
            dest.writeString(this.hongbaoTag);
        }

        public FavourMoney() {
        }

        protected FavourMoney(Parcel in) {
            this.count = in.readString();
            this.price = in.readString();
            this.hongbaoTag = in.readString();
        }

        public static final Creator<FavourMoney> CREATOR = new Creator<FavourMoney>() {
            @Override
            public FavourMoney createFromParcel(Parcel source) {
                return new FavourMoney(source);
            }

            @Override
            public FavourMoney[] newArray(int size) {
                return new FavourMoney[size];
            }
        };
    }

    public static class ShareMonery implements Parcelable {
        @SerializedName("count")
        @Expose
        public String count;//红包数量
        @SerializedName("price")
        @Expose
        public String price;//价格
        @SerializedName("hongbao_Tag")
        @Expose
        public String hongbaoTag;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.count);
            dest.writeString(this.price);
            dest.writeString(this.hongbaoTag);
        }

        public ShareMonery() {
        }

        protected ShareMonery(Parcel in) {
            this.count = in.readString();
            this.price = in.readString();
            this.hongbaoTag = in.readString();
        }

        public static final Creator<ShareMonery> CREATOR = new Creator<ShareMonery>() {
            @Override
            public ShareMonery createFromParcel(Parcel source) {
                return new ShareMonery(source);
            }

            @Override
            public ShareMonery[] newArray(int size) {
                return new ShareMonery[size];
            }
        };
    }

    public static class Comment implements Parcelable {
        @SerializedName("pinglun_id")
        @Expose
        public String id; //评论ID
        @SerializedName("pingluner_id")
        @Expose
        public String userId; //用户ID
        @SerializedName("pingluner_name")
        @Expose
        public String userName; //用户姓名
        @SerializedName("pingluner_touxiang")
        @Expose
        public String userAvatar; // 用户头像
        @SerializedName("content")
        @Expose
        public String content; // 评论的内容
        @SerializedName("time")
        @Expose
        public Long time; //评论的时间
        @SerializedName("dianzan_count")
        @Expose
        public Favour favour;// 点赞人列表*/
        @SerializedName("isdianzan")
        @Expose
        public boolean isFavuor;//这条评论是否有点赞
        @SerializedName("pingluner_qianming")
        @Expose
        public String memo;//评论人签名
        @SerializedName("huifu")
        @Expose
        public List<ReplyComment> replys;//评论的回复
        @SerializedName("picture")
        @Expose
        public List<Picture> picture;//评论的回复
        @SerializedName("huifu_count")
        @Expose
        public int replyCount;//评论的回复数量

        @SerializedName("type")
        @Expose
        public int type;

        @SerializedName("tiaomu_id")
        @Expose
        public String tiaomu_id;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.userId);
            dest.writeString(this.userName);
            dest.writeString(this.userAvatar);
            dest.writeString(this.content);
            dest.writeValue(this.time);
            dest.writeList(this.picture);
            dest.writeParcelable(this.favour, flags);
            dest.writeByte(this.isFavuor ? (byte) 1 : (byte) 0);
            dest.writeString(this.memo);
            dest.writeList(this.replys);
            dest.writeInt(this.replyCount);
            dest.writeInt(this.type);
            dest.writeString(this.tiaomu_id);
        }

        public Comment() {
        }

        protected Comment(Parcel in) {

            this.id = in.readString();
            this.userId = in.readString();
            this.userName = in.readString();
            this.userAvatar = in.readString();
            this.content = in.readString();
            this.picture = new ArrayList<Picture>();
            this.time = (Long) in.readValue(Long.class.getClassLoader());
            this.favour = in.readParcelable(Favour.class.getClassLoader());
            this.isFavuor = in.readByte() != 0;
            this.memo=in.readString();
            this.replys=new ArrayList<ReplyComment>();
            this.replyCount=in.readInt();
            this.type=in.readInt();
            this.tiaomu_id=in.readString();
        }

        public static final Creator<Comment> CREATOR = new Creator<Comment>() {
            @Override
            public Comment createFromParcel(Parcel source) {
                return new Comment(source);
            }

            @Override
            public Comment[] newArray(int size) {
                return new Comment[size];
            }
        };
    }

    public static class ReplyComment implements Parcelable  {
        @SerializedName("huifu_id")
        @Expose
        public String id;
        @SerializedName("pinglun_id")
        @Expose
        public String commentId;
        @SerializedName("huifuer_id")
        @Expose
        public String userId;
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

        @SerializedName("huifuer_qianming")
        @Expose
        public String memo;
        @SerializedName("time")
        @Expose
        public Long time;
        @SerializedName("picture")
        @Expose
        public List<Picture> picture=new ArrayList<>();//评论的回复
        @SerializedName("is_identical")
        @Expose
        public boolean isIdentical;//评论的回复

        @Override
        public String toString() {
            return "ReplyComment{" +
                    "id='" + id + '\'' +
                    ", commentId='" + commentId + '\'' +
                    ", userId='" + userId + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", favourCounr=" + favourCounr +
                    ", isFavour=" + isFavour +
                    ", userName='" + userName + '\'' +
                    ", content='" + content + '\'' +
                    ", memo='" + memo + '\'' +
                    ", time=" + time +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.isIdentical ? (byte) 1 : (byte) 0);
            dest.writeString(this.id);
            dest.writeString(this.commentId);
            dest.writeString(this.userId);
            dest.writeString(this.avatar);
            dest.writeInt(this.favourCounr);
            dest.writeByte(this.isFavour ? (byte) 1 : (byte) 0);
            dest.writeString(this.pinglunerName);
            dest.writeString(this.userName);
            dest.writeString(this.content);
            dest.writeString(this.contented);
            dest.writeString(this.memo);
            dest.writeValue(this.time);
            dest.writeList(this.picture);

        }

        public ReplyComment() {
        }

        protected ReplyComment(Parcel in) {
            this.isIdentical = in.readByte() != 0;
            this.id = in.readString();
            this.commentId = in.readString();
            this.userId = in.readString();
            this.avatar = in.readString();
            this.favourCounr = in.readInt();
            this.isFavour = in.readByte() != 0;
            this.pinglunerName = in.readString();
            this.userName = in.readString();
            this.content = in.readString();
            this.contented=in.readString();
            this.memo = in.readString();
            this.time = (Long) in.readValue(Long.class.getClassLoader());
            this.picture = new ArrayList<Picture>();
        }

        public static final Creator<ReplyComment> CREATOR = new Creator<ReplyComment>() {
            @Override
            public ReplyComment createFromParcel(Parcel source) {
                return new ReplyComment(source);
            }

            @Override
            public ReplyComment[] newArray(int size) {
                return new ReplyComment[size];
            }
        };
    }

    public static class FavourUser{
        @SerializedName("yonghu_id")
        @Expose
        public String id;
        @SerializedName("dianzan_touxiang")
        @Expose
        public String avatar;

        @Override
        public String toString() {
            return "FavourUser{" +
                    "id='" + id + '\'' +
                    ", avatar='" + avatar + '\'' +
                    '}';
        }
    }

    public static class Recommend implements Parcelable {
        @SerializedName("tuijian_id")
        @Expose
        public String id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("releaser_name")
        @Expose
        public String name;
        @SerializedName("picture")
        @Expose
        public List<Picture> picture = null;
        @SerializedName("time")
        @Expose
        public Long time;
        @SerializedName("type")
        @Expose
        public String type;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.title);
            dest.writeString(this.name);
            dest.writeList(this.picture);
            dest.writeValue(this.time);
        }

        public Recommend() {
        }

        protected Recommend(Parcel in) {
            this.id = in.readString();
            this.title = in.readString();
            this.name = in.readString();
            this.picture = new ArrayList<Picture>();
            in.readList(this.picture, Picture.class.getClassLoader());
            this.time = (Long) in.readValue(Long.class.getClassLoader());
        }

        public static final Creator<Recommend> CREATOR = new Creator<Recommend>() {
            @Override
            public Recommend createFromParcel(Parcel source) {
                return new Recommend(source);
            }

            @Override
            public Recommend[] newArray(int size) {
                return new Recommend[size];
            }
        };
    }

    public static class Picture implements Parcelable {
        @SerializedName("picture_url")
        @Expose
        public String pictureUrl;
        @SerializedName("str")
        @Expose
        public String str;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.pictureUrl);
            dest.writeString(this.str);
        }

        public Picture() {
        }

        protected Picture(Parcel in) {
            this.pictureUrl = in.readString();
            this.str = in.readString();
        }

        public static final Creator<Picture> CREATOR = new Creator<Picture>() {
            @Override
            public Picture createFromParcel(Parcel source) {
                return new Picture(source);
            }

            @Override
            public Picture[] newArray(int size) {
                return new Picture[size];
            }
        };
    }

    public static class Label implements Parcelable {
        @SerializedName("biaoqian")
        @Expose
        public String text;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.text);
        }

        public Label() {
        }

        protected Label(Parcel in) {
            this.text = in.readString();
        }

        public static final Creator<Label> CREATOR = new Creator<Label>() {
            @Override
            public Label createFromParcel(Parcel source) {
                return new Label(source);
            }

            @Override
            public Label[] newArray(int size) {
                return new Label[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.isCanPinglun);
        dest.writeString(this.id);
        dest.writeString(this.picture_text);
        dest.writeString(this.text);
        dest.writeList(this.pictures);
        dest.writeList(this.content_pictures);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeInt(this.favourCount);
        dest.writeInt(this.commentCount);
        dest.writeString(this.rewardCount);
        dest.writeString(this.authorId);
        dest.writeString(this.authorName);
        dest.writeString(this.authorAvatar);
        dest.writeString(this.authorIntroduction);
        dest.writeInt(this.favourMoney);
        dest.writeInt(this.shareMonery);
        dest.writeString(this.shenhe);
        /* dest.writeParcelable(this.favourMoney, flags);
        dest.writeParcelable(this.shareMonery, flags);*/
        dest.writeString(this.readCount);
        dest.writeString(this.time);
        dest.writeList(this.comments);
        dest.writeList(this.recommends);
        dest.writeString(this.adUrl);
        dest.writeString(this.tuijianday);
        dest.writeString(this.shareUrl);
        dest.writeList(this.label);
        dest.writeByte(this.isCare ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRenzheng?(byte) 1 : (byte) 0);
        dest.writeString(this.shopName);
        dest.writeInt(this.shopCommentCount);
        dest.writeString(this.money);
        dest.writeString(this.shopAvatar);
        dest.writeString(this.shopAddress);
        dest.writeInt(this.isAttestation);
        dest.writeString(this.type);
        dest.writeInt(this.muban_Tag);
    }

    public Detail() {
    }

    protected Detail(Parcel in) {
        this.id = in.readString();
        this.pictures = new ArrayList<Picture>();
        this.content_pictures = new ArrayList<Picture>();
        in.readList(this.pictures, Picture.class.getClassLoader());
        in.readList(this.content_pictures,Picture.class.getClassLoader());
        this.title = in.readString();
        this.content = in.readString();
        this.favourCount = in.readInt();
        this.commentCount = in.readInt();
        this.rewardCount = in.readString();
        this.text=in.readString();
        this.picture_text=in.readString();
        this.authorId = in.readString();
        this.authorName = in.readString();
        this.authorAvatar = in.readString();
        this.authorIntroduction=in.readString();
        this.favourMoney = in.readInt();
        this.shareMonery = in.readInt();
        this.tuijianday=in.readString();
        this.shenhe=in.readString();
        /* this.favourMoney = in.readParcelable(FavourMoney.class.getClassLoader());
        this.shareMonery = in.readParcelable(ShareMonery.class.getClassLoader());*/
        this.readCount = in.readString();
        this.time = in.readString();
        this.comments = new ArrayList<Comment>();
        in.readList(this.comments, Comment.class.getClassLoader());
        this.recommends = new ArrayList<Recommend>();
        in.readList(this.recommends, Recommend.class.getClassLoader());
        this.adUrl = in.readString();
        this.shareUrl=in.readString();
        this.label = new ArrayList<Label>();
        in.readList(this.label, Label.class.getClassLoader());
        this.isCare = in.readByte() != 0;
        this.isRenzheng = in.readByte()!=0;
        this.shopName = in.readString();
        this.shopCommentCount = in.readInt();
        this.money = in.readString();
        this.shopAvatar = in.readString();
        this.shopAddress = in.readString();
        this.isAttestation = in.readInt();
        this.type = in.readString();
        this.muban_Tag=in.readInt();
        this.isCanPinglun=in.readInt();
    }

    public static final Creator<Detail> CREATOR = new Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel source) {
            return new Detail(source);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };
}
