package com.example.shouye;

//获取数据并调用sendHandler传数据

import com.example.easyexpress1.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;

public class loading extends Activity {
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		//showProgressDialog("提示", "正在加载......");
		}
	/*
  * 提示加载
  */
	public void showProgressDialog(String title, String message) {
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(loading.this, title,
					message, true, false);
			} else if (progressDialog.isShowing()) {
				progressDialog.setTitle(title);
				progressDialog.setMessage(message);
				}
		progressDialog.show();
	}
	
	/*
  * 隐藏提示加载
  */
	public void hideProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
	}
	}
}