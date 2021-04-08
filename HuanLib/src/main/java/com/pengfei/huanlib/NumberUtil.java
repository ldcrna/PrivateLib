
package com.pengfei.huanlib;

import androidx.annotation.Keep;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author MaTianyu
 * @date 2014-11-21
 */
@Keep
public final class NumberUtil {

    private NumberUtil() {
    }

    public static String formatDouble(double value, int maxScal) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(maxScal);
        return nf.format(value);
    }

    /**
     * @return String
     * @Description: 保留两位小数
     * @author helen.yang
     * @da2015年11月24日
     */
    public static String formatNum(double data) {
        String num = new DecimalFormat("##0.00").format(data);
        return num;
    }

    public static String formatZeroNum(double data) {
        String num = new DecimalFormat("##0").format(data);
        return num;
    }

    public static String getFormatterPrice(float f) {
        String str = "";
        int digits = 0;
        String fStr = f + "";
        int length = fStr.length();
        int index = fStr.trim().indexOf(".");
        if (index < 6) {
            if (fStr.length() > 7) {
                digits = 2;
            } else {
                digits = 6 - index;
            }
        } else {
            digits = 2;
            getDigitsLength(f);
        }

        str = getFormatterFloat(f, digits);
        return str;
    }

    public static int getDigitsLength(float f) {
        String str = f + "";
        int length = str.length();
        return length - 1 - str.indexOf(".");
    }

    public static String getFormatterFloat(float f, int digits) {
        StringBuilder sb = new StringBuilder();
        if (digits > 0) {
            sb.append("###0.");
            for (int i = 0; i < digits; i++) {
                sb.append("0");
            }
        } else {
            sb.append("#0");
        }

        DecimalFormat df2 = new DecimalFormat(sb.toString());
        return df2.format(f);
    }

    public static String subtracts(String subt1, String subt2) {
        BigDecimal sub1 = new BigDecimal(subt1);
        BigDecimal sub2 = new BigDecimal(subt2);
        BigDecimal result = sub1.subtract(sub2);
        result = result.setScale(5, RoundingMode.HALF_UP);
        return result.toString();
    }

    public static String multiplys(String factor1, String factor2) {
        BigDecimal fac1 = new BigDecimal(factor1);
        BigDecimal fac2 = new BigDecimal(factor2);
        BigDecimal result = fac1.multiply(fac2);
        result = result.setScale(1, RoundingMode.HALF_UP);
        return result.toString();
    }

    public static String formatDate(double data, int count) {
        String num = "0.00";
        if (count == 1) {
            num = new DecimalFormat("##0.0").format(data);
        } else if (count == 2) {
            num = new DecimalFormat("##0.00").format(data);
        } else if (count == 3) {
            num = new DecimalFormat("##0.000").format(data);
        } else if (count == 4) {
            num = new DecimalFormat("##0.0000").format(data);
        } else if (count == 5) {
            num = new DecimalFormat("##0.00000").format(data);
        } else if (count == 6) {
            num = new DecimalFormat("##0.000000").format(data);
        } else {
            num = String.valueOf(data);
        }
        return num;
    }
}
