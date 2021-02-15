package com.pengfei.huanlib.view;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
public class TextViewColorUtil {
    private SpannableStringBuilder spBuilder;
    private final String wholeStr;
    private final String highlightStr;
    private final Context mContext;
    private int color;

    /**
     * @param context      context
     * @param wholeStr     所有文字
     * @param highlightStr 改变颜色的文字
     * @param color        颜色
     */
    public TextViewColorUtil(@NonNull Context context, @NonNull String wholeStr, @NonNull String highlightStr, int color) {
        this.mContext = context;
        this.wholeStr = wholeStr;
        this.highlightStr = highlightStr;
        this.color = color;
    }

    public TextViewColorUtil fillColor() {
        if (mContext == null) {
            return null;
        }
        int start;
        int end ;
        if (!TextUtils.isEmpty(wholeStr) && !TextUtils.isEmpty(highlightStr)) {
            if (wholeStr.contains(highlightStr)) {
                /*
                 *  返回highlightStr字符串wholeStr字符串中第一次出现处的索引。
                 */
                start = wholeStr.indexOf(highlightStr);
                end = start + highlightStr.length();
            } else {
                return null;
            }
        } else {
            return null;
        }
        spBuilder = new SpannableStringBuilder(wholeStr);
        color = mContext.getResources().getColor(color);
        CharacterStyle charaStyle = new ForegroundColorSpan(color);
        spBuilder.setSpan(charaStyle, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringBuilder getResult() {
        return spBuilder;
    }
}
