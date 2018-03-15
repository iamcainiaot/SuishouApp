package com.example.im.util;

import android.os.Handler;

public class ThreadUtils {

	/**
	 * ���������߳�(��������)
	 * 
	 * @param r
	 */
	public static void runInSubThread(Runnable r) {
		new Thread(r).start();
	}

	private static Handler handler = new Handler();

	/**
	 * ���������߳�(UI �߳� ���½���)
	 * 
	 * @param r
	 */
	public static void runInUiThread(Runnable r) {
		handler.post(r);// Message-->handler.sendMessage-->handleMessage()
						// ���߳�-->r.run();
	}
}
