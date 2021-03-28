
package com.pengfei.huanlib.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

/**
 * @author wanghuanlong
 */
@Keep
public class SystemUtil {
    private static final String TAG = "SystemUtil";

    /**
     * 打开Wifi网络设置界面
     */
    public static void openWifiSetting(@NonNull Activity activity) {
        if (activity == null) {
            Log.e(TAG, "openSetting: error ,activity is null");
            return;
        }
        try {
            activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context mActivity, String phoneNum) {
        if (mActivity == null) {
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            mActivity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMail(Context context, String email) {
        Uri uri = Uri.parse("mailto:" + email);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        context.startActivity(Intent.createChooser(intent, "请选择发送邮件的应用"));
    }

    public static void jumpQQ(Activity mActivity, String qqNum) {
        if (mActivity == null) {
            return;
        }
        try {
            mActivity.startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qqNum + "&version=1")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewAPi")
    public static boolean copyLink(Context mActivity, String text) {
        ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (!text.isEmpty() && cm != null) {
            cm.setText(text);
            return true;
        }
        return false;
    }
}
