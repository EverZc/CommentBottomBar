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
|dismiss()|隐藏评论弹出框，并隐藏软键盘
|setImages(ArrayList<ImageFile> mImagess)|添加图片
|getAdapterData()|获取当前评论框内的图片
|getAdapter()|获取弹出框图片的adapter
|getCommentText()|获取评论的内容
|clear()|清理评论文本内容以及评论的图片内容
 
## 使用步骤

#### Step 1.添加依赖CommentBottomBar
首先要在项目的根`build.gradle`下添加：
```
allprojects {
	repositories {
        maven { url "https://jitpack.io" }
    }
}
```
然后要在要依赖的module中添加
```
dependencies {
    implementation 'com.github.EverZc:CommentBottomBar:latest.release.here'
}
```

#### Step 2.使用流程
CommentBottomBar使用起来非常简单
```

    ZBottomSheetPictureBar commentZBSP = ZBottomSheetPictureBar.delegation(MainActivity.this);
    commentZBSP.show("期待您的神回复");
    commentZBSP.setOnSeetBarOnClickListener(new ZBottomSheetPictureBar.OnSheetBarOnClickListener() {
        @Override
        public void onAddClick() {
            Intent intent = new Intent(MainActivity.this, ImagePickActivityPicker.class);
            intent.putExtra(IS_NEED_CAMERA, true);
            int maxNumber = commentZBSP.getAdapterData().isEmpty() ?
                    ZBottomConstant.ARTICLE_IMAGE_MAX : ZBottomConstant.ARTICLE_IMAGE_MAX - commentZBSP.getAdapterData().size();
            intent.putExtra(FilePicker.MAX_NUMBER, maxNumber);
            startActivityForResult(intent, ZBottomConstant.REQUEST_CODE_PICK_IMAGE);
        }

        @Override
        public void onCommitClick(ArrayList<ImageFile> images, EditText editText) {
          //此处是点击按钮，具体处理处理提交评论的文字以及图片
        }
    });

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ZBottomConstant.REQUEST_CODE_PICK_IMAGE:
                //获取选择的图片
                if (resultCode == RESULT_OK) {
                    ArrayList<ImageFile> imageList = data.getParcelableArrayListExtra(FilePicker.RESULT_PICK_IMAGE);
                    commentZBSP.setImages(imageList);
                }
                break;
        }
    }
```

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

* 问：如何自定图片加载框架？

    * 答：欢迎自定义使用本人的另一个图片加载框架，如果使用自定义的框架只要图片的实体修改为ImageFile即可使用。


### 联系方式
* 我的简书：https://www.jianshu.com/u/197319888337 有兴趣的也可以关注，大家一起交流
