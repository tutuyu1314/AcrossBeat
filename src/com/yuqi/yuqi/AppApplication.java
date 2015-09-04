/**
 * 
 */
package com.yuqi.yuqi;

import com.yuqi.common.Global;
import com.yuqi.util.DisplayAbout;

import android.app.Application;

/**
 * @author HaoR
 * 
 * @date 2015-7-15 обнГ10:01:16
 * 
 */
public class AppApplication extends Application {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		Global.screenWidth = (int) DisplayAbout.getScreenWidth(getApplicationContext());
		Global.screenHeight = (int) DisplayAbout.getScreenHeight(getApplicationContext());
	}

}
