/**
 * 
 */
package com.yuqi.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author HaoR
 * 
 * @date 2015-7-15 下午8:45:01
 * 
 */
public class DisplayAbout {

	/** 获取屏幕宽度 */
	public static long getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	/** 获取屏幕宽度 */
	public static long getScreenHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}
}
