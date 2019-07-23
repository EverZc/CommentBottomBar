# CommentBottomBar

[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

## 新框架发布，欢迎大家Star

<br>

底部弹窗控件，可以填写评论文字以及评论图片。图片选择框架使用[Zfilepicker](https://github.com/EverZc/Zfilepicker)，本框架无需处理复杂的逻辑，只需要简单调用即可。

## 效果图


![效果示例](https://upload-images.jianshu.io/upload_images/4677908-78fefb287880dfe1.gif?imageMogr2/auto-orient/strip)


## 方法
|方法名|描述|
|---|---|
|delegation(Context context)|构造方法
|appendText(String text)|拼接评论文字
|show(String hint)|弹出评论框并填写评论的hint
## 使用步骤

#### Step 1.依赖CommentBottomBar


## 混淆代码
```java
# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# CommentBottomBar 的混淆代码
-keep class me.zwj.commentbottombar.** {
    *;
 }

```


## 常见问题

* 问：如何自定图片记载框架？

    * 答：欢迎自定义使用本人的另一个图片加载框架


## Thanks

- [暂无](https://www.baidu.com)

## 更新说明

#### v1.0.0
     更新版本的说明

 * 具体更新的条目说明1
 * 具体更新的条目说明2

### 联系方式
* 我的简书：https://www.jianshu.com/u/197319888337 有兴趣的也可以关注，大家一起交流
