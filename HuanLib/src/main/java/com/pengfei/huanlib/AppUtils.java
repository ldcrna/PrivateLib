package com.pengfei.huanlib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import androidx.annotation.Keep;

@Keep
public class AppUtils {
    private AppUtils() {
    }

    public static Spannable getSpanString(String titleStr, String editTextColor, int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(titleStr);
        try {
            int site = titleStr.indexOf(editTextColor);
            if (site == -1) {
                site = titleStr.toUpperCase().indexOf(editTextColor);
                if (site == -1) {
                    site = titleStr.toLowerCase().indexOf(editTextColor);
                }
            }
            if (site >= 0) {
                style.setSpan(new ForegroundColorSpan(color), site, site + editTextColor.length(),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);   //关键字红色显示
            }
            return style;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return style;
    }

    public static void startActivity(Activity mActivity, Intent intent) {
        if (DoubleClickUtils.doubleClickCheck()) {
            mActivity.startActivity(intent);
        }
    }

    public static void startActivityForResult(Activity mActivity, Intent intent, int requestCode) {
        if (DoubleClickUtils.doubleClickCheck()) {
            mActivity.startActivityForResult(intent, requestCode);
        }
    }

    public static String getAppVersionName(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            if (pi != null)
                return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取版本号
     */
    public static int getAppVersionCode(Context context) {
        int versioncode = -1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }

    public static void jumpQQ(Activity mActivity, String qqNum) {
        if (mActivity == null)
            return;
        try {
            mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qqNum + "&version=1")));
        } catch (Exception e) {

        }
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context mActivity, String phoneNum) {
        if (mActivity == null)
            return;
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            mActivity.startActivity(intent);
        } catch (Exception e) {
        }
    }


    public static void sendMail(Context context, String email) {
        Uri uri = Uri.parse("mailto:" + email);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        context.startActivity(Intent.createChooser(intent, "请选择发送邮件的应用"));
    }

    public static String getPackageName(Context context) {
        try {
            return context.getPackageName();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * http://stackoverflow.com/questions/3495890/how-can-i-put-a-listview-into-a-scrollview-without-it-collapsing/3495908#3495908
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildrenExtend(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @SuppressLint("NewAPi")
    public static boolean CopyLink(Context mActivity, String text) {
        ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (!text.isEmpty()) {
            cm.setText(text);
            return true;
        }
        return false;
    }

    public static String getChannel(Context context) {
        String channel = "test";
        try {
            channel = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("BaiduMobAd_CHANNEL");
            if (TextUtils.isEmpty(channel)) {
                return "test";
            }
            return channel;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "test";
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }
}
