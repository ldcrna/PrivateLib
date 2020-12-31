package com.pengfei.huanlib;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideImageLoader {

    public static void showImage(Context context, String url, ImageView tarImg) {
        if (url != null && url.endsWith(".gif")) {
            Glide.with(context).asGif().load(url).into(tarImg);
        } else {
            showImage(context, url, tarImg, -1);
        }
    }

    public static void showImage(Context context, String url, ImageView tarImg, int redId) {
        if (redId == -1) {
            if (!TextUtils.isEmpty(url)) {
                Glide.with(context).load(url).into(tarImg);
            }
        } else {
            if (TextUtils.isEmpty(url)) {
                tarImg.setBackgroundResource(redId);
            } else {
                RequestOptions options = new RequestOptions().error(redId);
                Glide.with(context).load(url).apply(options).into(tarImg);
            }
        }
    }

    //用于banner框架加载图片

    public void displayImage(Context context, Object path, ImageView imageView) {
        if (path != null && !TextUtils.isEmpty(path.toString()))
            Glide.with(context).load(path.toString()).into(imageView);
    }

}