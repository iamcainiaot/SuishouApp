package com.example.wode;

import org.json.JSONException;
import org.json.JSONObject;

import http.HttpUtils;

import com.example.easyhand1.MainActivity;
import com.example.easyexpress1.R;
import com.example.utils.ContinuousClickUtil;
import com.example.utils.SessionUtil;
import com.example.utils.Iputil;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity implements OnClickListener {

	private EditText et_user_name;
	private EditText et_password;
	static final int UPDATE_REGISTER = 1001;// ע��
	static final int QUERY_LOGIN = 1002;// ��¼
	// ������HttpUtils�е��������mHandle�е�ֵ
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String text;
			int statusCode;

			switch (msg.what) {

			case QUERY_LOGIN:
				System.out.println("��¼.java!!!!!!!!!!!!!!!!");
				statusCode = msg.getData().getInt("statusCode");
				Log.i("logB", "QUERY_REGISTER: statusCode=" + statusCode);
				try {
					JSONObject jb1;

					if (statusCode == 200) {
						text = msg.getData().getString("content");
						jb1 = new JSONObject(text);
						String result = jb1.getString("result");
						String YHM = jb1.getString("YHM");
						if (result.equals("success")) {
							text = text.replace("\"", "");
							System.out.println("��¼.java��~~~~~~~~~~~~~~"
									+ text);

							SessionUtil.yonghu = YHM;
							Intent intent2 = new Intent(login.this,
									MainActivity.class);
							startActivity(intent2);
						} else if (result.equals("error")) {
							Toast.makeText(login.this, "�û��������벻��ȷ",
									Toast.LENGTH_SHORT).show();
						} else if (result.equals("null")) {
							Toast.makeText(login.this, "�û���������",
									Toast.LENGTH_SHORT).show();
						}
						Log.i("logB", "get content=" + text);
					} else if (statusCode == 500) {
						Toast.makeText(login.this, "�������쳣�����Ժ�����",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(login.this, "�������������",
								Toast.LENGTH_SHORT).show();
					}
					break;
				} catch (JSONException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}

			case UPDATE_REGISTER:
				statusCode = msg.getData().getInt("statusCode");
				Log.i("logB", "QUERY_LOGIN: statusCode=" + statusCode);

				if (isFinishing()) {
					Log.i("logB", "activity isFinishing");
					return;
				}
				String strHintTryAgain = "";
				if (statusCode == 200) {
					try {
						text = msg.getData().getString("content");// content;
						Log.i("logB", "get content=" + text);
						JSONObject jb = new JSONObject(text);
						String result = jb.getString("result");
						if (result.equals("repeat")) {
							strHintTryAgain = "�û����Ѵ��ڣ�������";
						} else if (result.equals("success")) {
							strHintTryAgain = "ע��ɹ���";
						} else {
							strHintTryAgain = "��Ϣ�ύ���ģ��������������";
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (statusCode == 500) {
					strHintTryAgain = "�������쳣�����Ժ�����";
				} else {
					strHintTryAgain = "�������������";
				}
				Toast.makeText(login.this, strHintTryAgain, Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		initView();
		Button Login = (Button) findViewById(R.id.login);
		Login.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		switch (v.getId()) {

		case R.id.login:
			System.out.println("!!!!!!!!!!!!!!");
			Intent intent1 = new Intent(login.this, MainActivity.class);
			startActivity(intent1);
			break;
		}
	}

	// ��ȡ�ؼ�
	private void initView() {
		et_user_name = (EditText) findViewById(R.id.user_name);
		et_password = (EditText) findViewById(R.id.user_password);

	}

	public void back(View v) {
		finish();
	}

	// ���ע��
	public void zhuce(View v) {

		if (ContinuousClickUtil.isContinuousClick()) {
			return;
		}

		try {
			String strUserName = et_user_name.getText().toString().trim();
			String strPassword = et_password.getText().toString().trim();
			StringBuilder sb = new StringBuilder();
			String url;
			sb.append("name=" + strUserName);
			sb.append("&password=" + strPassword);
			Log.i("logB", "params=" + sb.toString());
			url = Iputil.getFwqIp() + "/Http_text/ServiceDemo";
			Log.i("logB", "url=" + url);
			HttpUtils.httpPost(login.this, url, sb.toString(), mHandler,
					UPDATE_REGISTER);
		} catch (Exception e) {

			// ��׽�쳣����
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �����¼
	public void denglu(View v) {

		// �������������ֻ��Ӧ��һ��
		if (ContinuousClickUtil.isContinuousClick()) {
			return;
		}
		// ͨ����ֵ������ȡ�ı��������ֵ
		// trim�����������ո�
		String strUserName = et_user_name.getText().toString().trim();
		System.out.println(strUserName);
		String strPassword = et_password.getText().toString().trim();
		System.out.println(strPassword);
		StringBuilder sb = new StringBuilder();
		// sb.append("name="+ strUserName);
		// sb.append("&password="+ strPassword);
		// Log.i("logB","params="+sb.toString());
		// String url =
		// "http://192.168.100.100:8080/WebTest/api/login";//"http://www.baidu.com"
		// url += "?"+sb.toString();

		String url;

		// append �ַ���ƴ�ӣ�StringBuilder�����෽��
		sb.append("username=" + strUserName);
		// ���͹�ȥ֮����������Ŀ���̨����Ҵ���ȥ��ֵ
		System.out.println("����̨����� ��¼.java" + sb.toString());
		sb.append("&password=" + strPassword);
		System.out.println(sb.toString());
		// �ڿ���̨(Log)�������־�ļ�
		Log.i("logB", "params=" + sb.toString());
		// ���ӷ����������Service��Ŀ
		// ��ʽ iputil.getFwqIp()+����������Ŀ����+'?'+sb.toString();
		url = Iputil.getFwqIp() + "/Http_text/ServiceDemo" + "?"
				+ sb.toString();
		// url = iputil.getFwqIp()+"/Http_text/ServiceDemo"+"?"+sb.toString();
		Log.i("logB", "url=" + url);
		HttpUtils.httpGet(login.this, url, mHandler, QUERY_LOGIN);

	}

	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	//
	// R.id.denglu:
	// Intent intent1=new Intent(wode.this,friend.class);
	// startActivity(intent1);
	// break;
	// }
	// }
}
