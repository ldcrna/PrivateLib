package com.pengfei.huanlib;

import android.widget.TextView;

import androidx.annotation.Keep;

/**
 * 短信验证倒计时
 */
@Keep
public class MsgCountDownTimer extends android.os.CountDownTimer {

    private final TextView view;

    public MsgCountDownTimer(TextView view, long millisInFuture) {
        super(millisInFuture, 1000);
        this.view = view;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        try {
            String text = "重新获取" + millisUntilFinished / 1000;
            view.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish() {
        try {
            view.setText("获取验证码");
            view.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
