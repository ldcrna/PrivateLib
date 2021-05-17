
package com.pengfei.huanlib;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author wanghuanlong
 */
@Keep
public final class GlideImageLoader {
    private static final String TAG = "GlideImageLoader";

    private GlideImageLoader() {
    }

    public static void showImage(@NonNull Context context, @NonNull String url, @NonNull ImageView tarImg) {
        if (url != null && url.endsWith(".gif")) {
            Glide.with(context).asGif().load(url).into(tarImg);
        } else {
            showImage(context, url, tarImg, -1);
        }
    }

    public static void showImage(@NonNull Context context, @NonNull String url, @NonNull ImageView tarImg, int redId) {
        if (tarImg == null) {
            Log.e(TAG, "showImage: tarImg == null");
            return;
        }
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

    /**
     * 用于banner框架加载图片.
     *
     * @param context context
     * @param path path
     * @param imageView imageView
     */
    public void displayImage(@NonNull Context context, @NonNull Object path, @NonNull ImageView imageView) {
        if (!TextUtils.isEmpty(path.toString())) {
            Glide.with(context).load(path.toString()).into(imageView);
        }
    }
}