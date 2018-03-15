package com.example.shouye;

import http.HttpUtils;

import org.json.JSONObject;

import com.example.easyexpress1.R;
import com.example.utils.ContinuousClickUtil;
import com.example.utils.Iputil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.im.activity.ImApp;

public class Pay extends Activity implements OnClickListener {
	private Spinner sp;
	private Button BT_pay;
	private ImageButton pay_back;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);
		sp = (Spinner) findViewById(R.id.spinner);
		BT_pay = (Button) findViewById(R.id.BT_pay);
		BT_pay.setOnClickListener(this);
		pay_back = (ImageButton) findViewById(R.id.pay_back);
		id=((ImApp) getApplication()).getId();
		pay_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String a = sp.getSelectedItem().toString();

		String value = null;
		if (a.equals("招商银行")) {
			value = "CMBCHINA-NET";
		} else if (a.equals("工商银行")) {
			value = "ICBC-NET";
		} else if (a.equals("农业银行")) {
			value = "ABC-NET";
		} else if (a.equals("建设银行")) {
			value = "CCB-NET";
		} else if (a.equals("中国民生银行总行")) {
			value = "CMBC-NET";
		} else if (a.equals("光大银行")) {
			value = "CEB-NET";
		} else if (a.equals("交通银行")) {
			value = "BOCO-NET";
		} else if (a.equals("深圳发展银行")) {
			value = "SDB-NET";
		} else if (a.equals("北京银行")) {
			value = "BCCB-NET";
		}
		Log.i("logb", "选中" + value);
		send(id,value);
	}

	static final int QUERY_LOGIN = 1002;

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// 处理从另一个线程传过来的信息

			String text;
			int statusCode;

			switch (msg.what) {

			case QUERY_LOGIN:
				statusCode = msg.getData().getInt("statusCode");
				Log.i("logB", "QUERY_REGISTER: statusCode=" + statusCode);
				JSONObject jb1;

				if (statusCode == 200) {
					text = msg.getData().getString("content");

					if (text != null) {
						// text = text.replace("\"", "");
						System.out.println("发布.java~~~~~~~~~~~~~~" + text);
						Toast.makeText(Pay.this, "获取成功", Toast.LENGTH_SHORT)
								.show();
						Log.i("logB", "get content=" + text);
						 Uri uri = Uri.parse(text);
			                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			                startActivity(intent);

					} else if (text == null) {
						Toast.makeText(Pay.this, "获取失败", Toast.LENGTH_SHORT)
								.show();

						// Intent intent2 = new Intent(Shouye.this,
						// Shouye.class);//获取失败则返回主页面
						// startActivity(intent2);
					}

				} else if (statusCode == 500) {
					Toast.makeText(Pay.this, "服务器异常，请稍后重试", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(Pay.this, "网络错误，请重试", Toast.LENGTH_SHORT)
							.show();
				}
				break;

			}
		}
	};

	public void send(int uid,String pd_FrpId) {

		if (ContinuousClickUtil.isContinuousClick()) {
			return;
		}
		// String value
		StringBuilder sb = new StringBuilder();
		String url;
		sb.append("uid=" + uid);
		sb.append("&pd_FrpId="+pd_FrpId);

		url = Iputil.getFwqIp() + "/SuishouDemo/TestServlet";// +"?"+sb.toString();
		Log.i("logB", "url=" + url);
		HttpUtils.httpPost(Pay.this, url, sb.toString(), mHandler, QUERY_LOGIN);
	}

}
