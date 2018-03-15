package com.example.utils;

import android.util.Log;

public class ContinuousClickUtil {
	private static long lastClickTime=0;
	   public static boolean isContinuousClick(){
	        //�����������ֻ��Ӧ��һ�Σ�������ʾ��ֻ��ʾ��һ�ε�������������ⳤʱ����ʾ��ʾ��Ϣ��
	        //Toast.LENGTH_LONG��3.5�룩��Toast.LENGTH_SHORT��2�룩
	        long time = System.currentTimeMillis();
	        if ( time - lastClickTime < 2000) {
	            Log.i("logB","isFastDoubleClick, ignore this click");
	            return true;
	        }
	        lastClickTime = time;
	        return false;
	    }

}
