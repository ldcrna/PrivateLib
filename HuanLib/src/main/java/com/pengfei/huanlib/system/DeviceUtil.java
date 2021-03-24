
package com.pengfei.huanlib.system;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import androidx.annotation.Keep;
import androidx.core.app.ActivityCompat;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Enumeration;

/**
 * @author wanghuanlong
 */
@Keep
public class DeviceUtil {
    private static final int NETWORK_TYPE_UNAVAILABLE = -1;

    private static final int NETWORK_TYPE_MOBILE = -100;

    private static final int NETWORK_TYPE_WIFI = -101;

    private static final int NETWORK_CLASS_WIFI = -101;

    private static final int NETWORK_CLASS_UNAVAILABLE = -1;

    /**
     * Unknown network class.
     */
    private static final int NETWORK_CLASS_UNKNOWN = 0;

    /**
     * Class of broadly defined "2G" networks.
     */
    private static final int NETWORK_CLASS_2_G = 1;

    /**
     * Class of broadly defined "3G" networks.
     */
    private static final int NETWORK_CLASS_3_G = 2;

    /**
     * Class of broadly defined "4G" networks.
     */
    private static final int NETWORK_CLASS_4_G = 3;

    private static DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Network type is unknown
     */
    public static final int NETWORK_TYPE_UNKNOWN = 0;

    /**
     * Current network is GPRS
     */
    public static final int NETWORK_TYPE_GPRS = 1;

    /**
     * Current network is EDGE
     */
    public static final int NETWORK_TYPE_EDGE = 2;

    /**
     * Current network is UMTS
     */
    public static final int NETWORK_TYPE_UMTS = 3;

    /**
     * Current network is CDMA: Either IS95A or IS95B
     */
    public static final int NETWORK_TYPE_CDMA = 4;

    /**
     * Current network is EVDO revision 0
     */
    public static final int NETWORK_TYPE_EVDO_0 = 5;

    /**
     * Current network is EVDO revision A
     */
    public static final int NETWORK_TYPE_EVDO_A = 6;

    /**
     * Current network is 1xRTT
     */
    public static final int NETWORK_TYPE_1XRTT = 7;

    /**
     * Current network is HSDPA
     */
    public static final int NETWORK_TYPE_HSDPA = 8;

    /**
     * Current network is HSUPA
     */
    public static final int NETWORK_TYPE_HSUPA = 9;

    /**
     * Current network is HSPA
     */
    public static final int NETWORK_TYPE_HSPA = 10;

    /**
     * Current network is iDen
     */
    public static final int NETWORK_TYPE_IDEN = 11;

    /**
     * Current network is EVDO revision B
     */
    public static final int NETWORK_TYPE_EVDO_B = 12;

    /**
     * Current network is LTE
     */
    public static final int NETWORK_TYPE_LTE = 13;

    /**
     * Current network is eHRPD
     */
    public static final int NETWORK_TYPE_EHRPD = 14;

    /**
     * Current network is HSPA+
     */
    public static final int NETWORK_TYPE_HSPAP = 15;

    private static Context mContext;

    private DeviceUtil() {
    }

    /**
     * 判断是否以WIFI方式联网
     *
     * @return
     */
    public static boolean isConnectWifi() {
        // 获取网络连接管理者
        ConnectivityManager connectionManager =
            (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取网络的状态信息，有下面三种方式
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取联网的方式,移动CMNET方式是cmnet,移动CMWAP方式是cmwap,联通3gwap方式是3gwap,联通3gnet方式是3gnet,联通uniwap方式是uniwap,联通uninet方式是uninet
     *
     * @return
     */
    public static String getConnectionType() {
        ConnectivityManager connectionManager =
            (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.getExtraInfo();
        }
        return "error";
    }

    /**
     * 获取网络类型
     *
     * @return
     */
    public static String getCurrentNetworkType() {
        int networkClass = getNetworkClass();
        String type = "CELL";
        switch (networkClass) {
            case NETWORK_CLASS_UNAVAILABLE:
                type = "NONE";
                break;
            case NETWORK_CLASS_WIFI:
                type = "WIFI";
                break;
            case NETWORK_CLASS_2_G:
                type = "CELL_2G";
                break;
            case NETWORK_CLASS_3_G:
                type = "CELL_3G";
                break;
            case NETWORK_CLASS_4_G:
                type = "CELL_4G";
                break;
            case NETWORK_CLASS_UNKNOWN:
                type = "UNKNOWN";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + networkClass);
        }
        return type;
    }

    private static int getNetworkClass() {
        int networkType = NETWORK_TYPE_UNKNOWN;
        try {
            final NetworkInfo network =
                ((ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();
            if (network != null && network.isAvailable() && network.isConnected()) {
                int type = network.getType();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    networkType = NETWORK_TYPE_WIFI;
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    TelephonyManager telephonyManager =
                        (TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);
                    networkType = telephonyManager.getNetworkType();
                }
            } else {
                networkType = NETWORK_TYPE_UNAVAILABLE;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getNetworkClassByType(networkType);

    }

    private static int getNetworkClassByType(int networkType) {
        switch (networkType) {
            case NETWORK_TYPE_UNAVAILABLE:
                return NETWORK_CLASS_UNAVAILABLE;
            case NETWORK_TYPE_WIFI:
                return NETWORK_CLASS_WIFI;
            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_CDMA:
            case NETWORK_TYPE_1XRTT:
            case NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_EVDO_0:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSUPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
                return NETWORK_CLASS_3_G;
            case NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    /**
     * 获取运营商
     *
     * @return
     */
    public static String getProvider() {
        String provider = "未知";
        try {
            TelephonyManager telephonyManager =
                (TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                // ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                // public void onRequestPermissionsResult(int requestCode, String[] permissions,
                // int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "获取权限失败";
            }
            String iMSI = telephonyManager.getSubscriberId();
            if (iMSI == null) {
                if (TelephonyManager.SIM_STATE_READY == telephonyManager.getSimState()) {
                    String operator = telephonyManager.getSimOperator();
                    if (operator != null) {
                        if ("46000".equals(operator) || "46002".equals(operator) || "46007".equals(operator)) {
                            provider = "中国移动";
                        } else if ("46001".equals(operator)) {
                            provider = "中国联通";
                        } else if ("46003".equals(operator)) {
                            provider = "中国电信";
                        }
                    }
                }
            } else {
                if (iMSI.startsWith("46000") || iMSI.startsWith("46002") || iMSI.startsWith("46007")) {
                    provider = "中国移动";
                } else if (iMSI.startsWith("46001")) {
                    provider = "中国联通";
                } else if (iMSI.startsWith("46003")) {
                    provider = "中国电信";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provider;
    }

    public static String getLocalIpAddress() {
        String ipaddress = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                if ("eth0".equals(intf.getName().toLowerCase()) || "wlan0".equals(intf.getName().toLowerCase())) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::")) {
                                return ipaddress;
                            }
                        }
                    }
                } else {
                    continue;
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return ipaddress;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getMobileType() {
        return Build.MODEL;
    }

    /**
     * 获取手机系统版本
     *
     * @return
     */
    public static String getMobileSystem() {
        return Build.VERSION.RELEASE;
    }

    private static Context getApplication() {
        return mContext;
    }

    public static synchronized void initApplicationContext(Context context) {
        mContext = context;
    }

    /**
     * 获取屏幕像素密度
     *
     * @param
     * @return
     */
    public static float getDeviceDensity() {
        DisplayMetrics metric = getApplication().getResources().getDisplayMetrics();
        return metric.density;
    }

    public static String getPackageName() {
        if (mContext == null) {
            return "mContext== null";
        }
        return getApplication().getPackageName();
    }

    public static String getVersionName() {
        try {
            PackageManager packManager = getApplication().getPackageManager();
            PackageInfo packInfo = packManager.getPackageInfo(getApplication().getPackageName(), 0);
            return packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getVersionCode() {
        try {
            PackageManager packManager = getApplication().getPackageManager();
            PackageInfo packInfo = packManager.getPackageInfo(getApplication().getPackageName(), 0);
            return packInfo.versionCode;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getBuildName() {
        return Build.VERSION.RELEASE;
    }

    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }

        return height;
    }
}