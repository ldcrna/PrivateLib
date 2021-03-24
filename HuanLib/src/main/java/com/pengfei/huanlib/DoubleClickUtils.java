
package com.pengfei.huanlib;

import androidx.annotation.Keep;

/**
 * @author wanghuanlong
 */
@Keep
public class DoubleClickUtils {
    private DoubleClickUtils() {
    }

    public static long downTime;

    public static boolean doubleClickCheck() {
        if (Math.abs(downTime - System.currentTimeMillis()) > 800) {
            downTime = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    private static long exitTime;

    public static boolean checkExitDoubleClick() {
        if (Math.abs(exitTime - System.currentTimeMillis()) > 2000) {
            exitTime = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }
}
