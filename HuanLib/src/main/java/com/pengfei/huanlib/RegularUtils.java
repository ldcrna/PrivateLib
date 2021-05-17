
package com.pengfei.huanlib;

import androidx.annotation.Keep;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wanghuanlong
 */
@Keep
public class RegularUtils {

    /**
     * 是否是手机号.
     *
     * @param tel 手机号字符串
     * @return 是否是手机号
     */
    public static boolean isTel(String tel) {
        String str = "^[0-9]{11}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    public static boolean isSafePassword(String password) {
        String str = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }


    /**
     * 是否是正确邮箱.
     *
     * @param email  邮箱
     * @return 是否是正确
     */
    public static boolean isEmail(String email) {
        String str =
            "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
