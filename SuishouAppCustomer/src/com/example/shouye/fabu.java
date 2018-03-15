package com.example.shouye;

import org.json.JSONException;
import org.json.JSONObject;

import http.HttpUtils;
import http.HttpUtils1;

import com.example.easyhand1.MainActivity;
import com.example.easyexpress1.R;
import com.example.im.activity.ImApp;
import com.example.utils.ContinuousClickUtil;
import com.example.utils.Iputil;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class fabu extends Activity implements OnClickListener {

	static final int UPDATE_REGISTER = 1001;
	static final int QUERY_LOGIN = 1002;

	private EditText pro_name;
	private EditText pro_address;
	private EditText pro_num;
	private Spinner rec_address;
	private EditText ordertype;
	private Button sure;
	ImApp application;
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			String text;
			int statusCode;

			switch (msg.what) {

			case QUERY_LOGIN:
				statusCode = msg.getData().getInt("statusCode");
				Log.i("logB", "QUERY_REGISTER: statusCode=" + statusCode);
				try {
					JSONObject jb1;

					if (statusCode == 200) {
						text = msg.getData().getString("content");
						jb1 = new JSONObject(text);

						// ִ��HttpUtil֮�󣬻�ȡ���������ص���Ӧ��
						String result = jb1.getString("result");
						// String order_num = jb1.getString("order_num");

						Log.i("logB", "result=" + result);

						if (result.equals("success")) {
							text = text.replace("\"", "");
							System.out
									.println("����.java~~~~~~~~~~~~~~" + text);
							Toast.makeText(fabu.this, "发布成功！",
									Toast.LENGTH_SHORT).show();
							// SessionUtil.yonghu = order_num;
							Intent intent2 = new Intent(fabu.this,
									MainActivity.class);
							startActivity(intent2);
						} else if (result.equals("error")) {
							Toast.makeText(fabu.this, "发布失败！",
									Toast.LENGTH_SHORT).show();
						}else if (result.equals("null")) {
							Toast.makeText(fabu.this, "用户名不存在",
									Toast.LENGTH_SHORT).show();
						}
						Log.i("logB", "get content=" + text);
					} else if (statusCode == 500) {
						Toast.makeText(fabu.this, "服务器异常，请稍后重试",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(fabu.this, "网络错误，请重试",
								Toast.LENGTH_SHORT).show();
					}
					break;
				} catch (JSONException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}

			}
		}
	};

	public void fabu(View v) {


		pro_name = (EditText) findViewById(R.id.ordername1);
		pro_address = (EditText) findViewById(R.id.orderAdr1);
		pro_num = (EditText) findViewById(R.id.orderCount1);
		rec_address =  (Spinner) findViewById(R.id.orderG_Adr1);
		ordertype=(EditText) findViewById(R.id.ordertype1);
		if (ContinuousClickUtil.isContinuousClick()) {
			return;
		}
		String pro_name1 = pro_name.getText().toString().trim();
		String pro_address1 = pro_address.getText().toString().trim();
		String pro_num1 = pro_num.getText().toString().trim();
		String rec_address1 = rec_address.getSelectedItem().toString().trim();
		String ordertype1=ordertype.getText().toString().trim();
		int id=application.getId();
		StringBuilder sb = new StringBuilder();
		String url;
		sb.append("pro_name=" + pro_name1);
		sb.append("&pro_address=" + pro_address1);
		sb.append("&pro_num=" + pro_num1);
		sb.append("&rec_address=" + rec_address1);
		sb.append("&ordertype=" + ordertype1);
		sb.append("&id="+id);

		Log.i("logB", "params=" + sb.toString());
		url = Iputil.getFwqIp() + "/SuishouDemo/pro_message";// +"?"+sb.toString();
		Log.i("logB", "url=" + url);
		// HttpUtils.httpGet(fabu.this,
		// url,
		// mHandler,
		// QUERY_LOGIN
		// );
		HttpUtils
				.httpPost(fabu.this, url, sb.toString(), mHandler, QUERY_LOGIN);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fabu);
		 application = (ImApp) this.getApplication();
		sure = (Button) findViewById(R.id.tijiao);
		sure.setOnClickListener(this);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads().detectDiskWrites().detectNetwork()
		.penaltyLog().build());
	}

	public void back(View v) {
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tijiao:
			//Toast.makeText(fabu.this, "�㵽�˵㵽��", Toast.LENGTH_SHORT).show();
			fabu(v);
			break;
		}
	}

}
