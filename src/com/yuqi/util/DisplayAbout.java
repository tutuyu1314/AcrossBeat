/**
 * 
 */
package com.yuqi.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author HaoR
 * 
 * @date 2015-7-15 ����8:45:01
 * 
 */
public class DisplayAbout {

	/** ��ȡ��Ļ��� */
	public static long getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	/** ��ȡ��Ļ��� */
	public static long getScreenHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}
}
