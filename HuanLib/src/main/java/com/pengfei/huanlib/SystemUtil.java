package com.pengfei.huanlib;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

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
}
