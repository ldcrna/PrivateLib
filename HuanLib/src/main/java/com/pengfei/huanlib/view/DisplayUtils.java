package com.pengfei.huanlib.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.InputStream;
import java.lang.reflect.Field;

import androidx.annotation.Keep;

@Keep
public class DisplayUtils {
	
	
	public static int dip2px(Context context, float dipValue) {
		if(context == null)
			return 0 ;
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		if(context == null)
			return 0 ;
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	public static int switch2sp(Context context, int spValue){
		if(context == null)
			return 0 ;
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, dm);
	}

	public static int getWindowWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	public static int getWindowHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 显示输入法
	 * 
	 * @param cont
	 */
	public static void showSoftInput(Activity cont) {
		cont.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}
	
	/**
	 * 以最省内存的方式读取本地资源的图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		try {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			// 获取资源图片
			InputStream is = context.getResources().openRawResource(resId);
			return BitmapFactory.decodeStream(is, null, opt);
		} catch (Exception e) {
			// TODO: handle exception
			return null ;
		}
	}

	/**
	 * 隐藏键盘
	 */
	public static void closeKeyBoard(Activity activity) {
		if(activity != null) {
			InputMethodManager imm = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
			if (isOpen) {
				View currentFocus = activity.getCurrentFocus();
				if (currentFocus != null) {
					imm.hideSoftInputFromWindow(currentFocus.getApplicationWindowToken(), 0);
				}
			}
		}
	}
	
	/**
	 * 获取状态栏的高度   
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Activity context) {
		int statusBarHeight = 0;
		try {
			Class clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			Field field = clazz.getField("status_bar_height");
			int id = Integer.parseInt(field.get(object).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return statusBarHeight;
	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideSoftInputMethod(Activity act) {
		View view = act.getWindow().peekDecorView();
		if (view != null) {
			// 隐藏虚拟键盘
			InputMethodManager inputmanger = (InputMethodManager) act
					.getSystemService(act.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 切换软件盘 显示隐藏
	 */
	public static void switchSoftInputMethod(Activity act) {
		// 方法一(如果输入法在窗口上已经显示，则隐藏，反之则显示)
		InputMethodManager iMM = (InputMethodManager) act
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		iMM.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

}
