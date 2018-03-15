package com.example.utils;

import android.util.Log;

public class ContinuousClickUtil {
	private static long lastClickTime=0;
	   public static boolean isContinuousClick(){
	        //若连续点击，只响应第一次，并且提示。只提示第一次的连续点击，避免长时间显示提示信息。
	        //Toast.LENGTH_LONG（3.5秒）和Toast.LENGTH_SHORT（2秒）
	        long time = System.currentTimeMillis();
	        if ( time - lastClickTime < 2000) {
	            Log.i("logB","isFastDoubleClick, ignore this click");
	            return true;
	        }
	        lastClickTime = time;
	        return false;
	    }

}
