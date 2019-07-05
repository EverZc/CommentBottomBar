package me.pandazhang.filepicker;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.pandazhang.filepicker.R;


public class MyToastPK {
    private static Toast toast;


    private static Handler sHandler = new Handler(Looper.getMainLooper());

    /**
     * 展示toast==LENGTH_SHORT
     *
     * @param msg
     */
    public static void showError(final String msg, final Context context) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(msg, Toast.LENGTH_SHORT, R.mipmap.tab_window_toast,context);
            }
        });
    }

    /**
     * 成功
     *
     * @param msg
     */
    public static void showSuccess(final String msg, final Context context) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(msg, Toast.LENGTH_SHORT,R.mipmap.tab_window_toast,context);
            }
        });
    }

    private static void show(String massage, int show_lengt,int imageid,Context context) {

        //使用布局加载器，将编写的toast_layout布局加载进来
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        //获取ImageView
        ImageView image = (ImageView) view.findViewById(R.id.iv_toast);
        //设置图片
        image.setImageResource(imageid);
        //获取TextView
        TextView title = (TextView) view.findViewById(R.id.tv_toast);

        if (toast==null){
            //设置显示的内容
            title.setText(massage);
            toast  = new Toast(context);
            //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
        }else {
            cancelToast();
            //设置显示的内容
            title.setText(massage);
            toast  = new Toast(context);
            //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER, 0, 0);
            //设置显示时间
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
        }



        toast.show();


    }

    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }




}
