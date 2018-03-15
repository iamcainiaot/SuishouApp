package com.example.easyhand1;

import http.HttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.easyexpress1.R;
import com.example.im.activity.FriendActivity;
import com.example.im.activity.ImApp;
import com.example.im.activity.Ts;
import com.example.share.share;
import com.example.shouye.Friend;
import com.example.shouye.Shouye;
import com.example.shouye.Wode;
import com.example.utils.ContinuousClickUtil;
import com.example.utils.Iputil;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private int login_status = 0;
	private Button shouyeButton;
	private Button friendButton;
	private Button shareButton;
	private Button wodeButton;
	public static boolean isPressed = false;

	private Shouye mShouye;
	private Friend mFriend;
	private share mShare;
	private Wode mWode;
	static final int QUERY_LOGIN = 1002;
	ImApp app;
	int flag;
	int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		app = (ImApp) getApplication();
		flag=app.getFlag();
		// 获取控件
		shouyeButton = (Button) findViewById(R.id.shouye);
		shouyeButton.setOnClickListener(this);
		shouyeButton.setSelected(true);

		friendButton = (Button) findViewById(R.id.friend);
		friendButton.setOnClickListener(this);
		shareButton = (Button) findViewById(R.id.share);
		shareButton.setOnClickListener(this);
		wodeButton = (Button) findViewById(R.id.wode);
		wodeButton.setOnClickListener(this);
		Intent intent = getIntent();
		login_status = intent.getIntExtra("login_status", 404);
		id=0;
		Intent intert=getIntent();
		id = intert.getIntExtra("goto",0);
		if (id == 1) {
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			mShare = new share();
			ft.replace(R.id.shouye_content, mShare);
			// 提交事务
			ft.commit();
			}else if(id==2){
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				mWode = new Wode();
				ft.replace(R.id.shouye_content, mWode);
				// 提交事务
				ft.commit();
			}
		if(id==0){
		setDefaultFragment();}
		// 设置默认fragment
		
		getmessage();
		// 设置默认fragment
		//setDefaultFragment();
		ShowSharelist sharelist = new ShowSharelist();
	}

	// 设置默认fragment
	private void setDefaultFragment() {
		// 开启事务
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		mShouye = new Shouye();
		ft.replace(R.id.shouye_content, mShouye);
		// 提交事务
		ft.commit();
	}

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
						Toast.makeText(MainActivity.this, "获取成功",
								Toast.LENGTH_SHORT).show();
						Log.i("logB", "get content=" + text);
						try {
							showMessage(text);// 获取成功则将信息显示
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else if (text == null) {
						Toast.makeText(MainActivity.this, "获取失败",
								Toast.LENGTH_SHORT).show();

						// Intent intent2 = new Intent(Shouye.this,
						// Shouye.class);//获取失败则返回主页面
						// startActivity(intent2);
					}

				} else if (statusCode == 500) {
					Toast.makeText(MainActivity.this, "服务器异常，请稍后重试",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "网络错误，请重试",
							Toast.LENGTH_SHORT).show();
				}
				break;

			}
		}
	};

	public void getmessage() {// 获取内容

		if (ContinuousClickUtil.isContinuousClick()) {
			return;
		}

		new Thread() {
			public void run() {
				int phonenum = (int) app.getMyAccount();
				String url;
				url = Iputil.getFwqIp() + "/SuishouDemo/GetpersonalMess?"
						+ "phonenum=" + phonenum;
				Log.i("logB", "url=" + url);
				HttpUtils
						.httpGet(MainActivity.this, url, mHandler, QUERY_LOGIN);
			}
		}.start();
	}

	public void showMessage(String text) throws JSONException {
		// String text
		// ="[{pro_name:挑子,sender:'',rec_address:河北省,pro_num:12,pro_address:安徽省,receiver:'',oid:0,order_num:'123',code:'',type:水果},{pro_name:g,sender:'1',rec_address:gj,pro_num:1,pro_address:jhg,receiver:'',oid:0,order_num:'',code:'',type:gh},{pro_name:正宗猕猴桃,sender:'2',rec_address:安徽省六安市,pro_num:10,pro_address:安徽省合肥市,receiver:'',oid:0,order_num:'',code:'',type:水果},{pro_name:阿迪达斯上衣,sender:'',rec_address:安徽省六安市,pro_num:1,pro_address:安徽省合肥市,receiver:'',oid:0,order_num:'',code:'',type:衣服},{pro_name:1,sender:'',rec_address:1,pro_num:1,pro_address:1,receiver:'',oid:0,order_num:'',code:'',type:''},{pro_name:1,sender:'',rec_address:1,pro_num:1,pro_address:1,receiver:'',oid:0,order_num:'',code:'',type:''}]";
		// String text =
		// "[{'pro_name':'毛巾','pro_num':'1','pro_type':'生活用品','pro_address':'安徽','rec_address':'安徽'},{'pro_name':'饼干','pro_num':'1','pro_type':'食品','pro_address':'安徽','rec_address':'浙江'}]";

		JSONArray jsonarray = new JSONArray(text);

		final List<User_msg> list = new ArrayList<User_msg>();

		for (int i = 0; i < jsonarray.length(); i++) {

			JSONObject jsonobject = jsonarray.getJSONObject(i);

			if (jsonobject != null) {

				String uname = jsonobject.optString("username");
				int id = jsonobject.optInt("uid");
				String password = jsonobject.optString("password");
				String photo = jsonobject.optString("image");
				int phonenum = jsonobject.optInt("phonenumber");

				User_msg s = new User_msg();
				s.setID(id);
				s.setUsername(uname);
				s.setImage(photo);
				s.setPhonenum(phonenum);
				s.setPassword(password);
				app.setId(id);
				app.setPassword(password);
				app.setName(uname);

				// Log.i("logb",
				// "idididdididididididiiiiiiiiiiiiiiiiiiii是的"+((ImApp)
				// getApplication()).getId());

				list.add(s);
			}
		}

	}

	@Override
	public void onClick(View v) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		switch (v.getId()) {
		case R.id.shouye:
			shouyeButton.setSelected(true);
			friendButton.setSelected(false);
			shareButton.setSelected(false);
			wodeButton.setSelected(false);

			if (mShouye == null) {
				mShouye = new Shouye();
			}
			// Intent intent9 = new Intent(MainActivity.this,Shouye.class);
			// intent9.putExtra("login_status", 1);
			// startActivity(intent9);
			ft.replace(R.id.shouye_content, mShouye);
			break;
		case R.id.friend:
			
			// Toast.LENGTH_SHORT).show();
			if (flag == 1) {
				friendButton.setSelected(true);
				shouyeButton.setSelected(false);
				shareButton.setSelected(false);
				wodeButton.setSelected(false);
				Log.i("friend", "isPressed");
				Intent intent1 = new Intent(MainActivity.this,
						FriendActivity.class);

				startActivity(intent1);
				break;
			} else if (flag != 1) {
				Toast.makeText(MainActivity.this, "您尚未登录，请先登录在访问本页面！",
						Toast.LENGTH_LONG).show();
				Intent intent2 = new Intent(MainActivity.this, Ts.class);
				startActivity(intent2);
				// Intent intent2 = new
				// Intent(MainActivity.this,LoginActivity.class);
				// startActivity(intent2);
				// break;
			}

			break;
		case R.id.share:
			isPressed = true;
			shareButton.setSelected(true);
			shouyeButton.setSelected(false);
			friendButton.setSelected(false);
			wodeButton.setSelected(false);
			if (mShare == null) {
				mShare = new share();
			}
			ft.replace(R.id.shouye_content, mShare);
			break;
		case R.id.wode:
			wodeButton.setSelected(true);
			shouyeButton.setSelected(false);
			friendButton.setSelected(false);
			shareButton.setSelected(false);
			if (mWode == null) {
				mWode = new Wode();
			}
			ft.replace(R.id.shouye_content, mWode);
			break;
		default:
			break;
		}
		// 提交事务
		Log.d("MainActivity", "ft.commit()");
		ft.commit();
	}

}
