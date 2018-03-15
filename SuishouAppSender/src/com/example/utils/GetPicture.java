package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GetPicture {

	public Bitmap getPicture(String path) {
		Bitmap bm = null;
		try {
			URL url = new URL(path);// 创建url对象
			URLConnection uc = url.openConnection();// 获取url对象对应的连接
			uc.connect();// 打开连接
			InputStream is = uc.getInputStream();// 获取输入流对象
			bm = BitmapFactory.decodeStream(is);// 根据输入流对象创建bitmap对象
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bm;
	}

}
