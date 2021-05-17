
package com.pengfei.huanlib;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @author wanghuanlong
 */
@Keep
public final class AppUtils {
    private AppUtils() {
    }

    /**
     * @param titleStr titleStr
     * @param editTextColor editTextColor
     * @param color color
     * @return Spannable
     */
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
                    // 关键字红色显示
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
            return style;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return style;
    }

    /**
     * 启动 Activity，防止多次点击.
     *
     * @param mActivity mActivity
     * @param intent intent
     */
    public static void startActivity(Activity mActivity, Intent intent) {
        if (DoubleClickUtils.doubleClickCheck()) {
            mActivity.startActivity(intent);
        }
    }

    /**
     * 启动 Activity，防止多次点击.
     *
     * @param mActivity mActivity
     * @param intent intent
     * @param requestCode requestCode
     */
    public static void startActivityForResult(Activity mActivity, Intent intent, int requestCode) {
        if (DoubleClickUtils.doubleClickCheck()) {
            mActivity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 获取版本名.
     *
     * @param context context
     * @return String
     */
    public static String getAppVersionName(Context context) {
        PackageInfo pi;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            if (pi != null) {
                return pi.versionName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取版本号.
     *
     * @param context context
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

    public static String getPackageName(Context context) {
        try {
            return context.getPackageName();
        } catch (Exception e) {
            return null;
        }
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

    /**
     * http://stackoverflow.com/questions/3495890/how-can-i-put-a-listview-into-a-scrollview-without-it-collapsing/3495908#3495908.
     *
     * @param listView listView
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

    public static String getChannel(Context context) {
        try {
            String channel = context.getPackageManager()
                .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData
                    .getString("BaiduMobAd_CHANNEL");
            if (TextUtils.isEmpty(channel)) {
                return "test";
            }
            return channel;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "test";
    }
}
