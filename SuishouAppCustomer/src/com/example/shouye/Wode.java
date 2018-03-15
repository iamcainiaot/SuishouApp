package com.example.shouye;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyexpress1.R;
import com.example.im.activity.DengluActivity;
import com.example.im.activity.ImApp;
import com.example.im.activity.Ts;
import com.example.utils.Iputil;
import com.example.utils.PictureTransform;
import com.example.utils.toRoundBitmap;
import com.example.wode.alter_name;
import com.example.wode.faq;
import com.example.wode.kefu;
import com.example.wode.login;
import com.example.wode.myorder;
import com.example.wode.news;
import com.example.wode.reg;
import com.example.wode.setting;
import com.example.wode.wode_info;

public class Wode extends Fragment implements OnClickListener {

	// 缓存fragment view
	private View view;

	private Button settingButton;
	public static ImageButton user_imag;
	public static TextView show_name;
	private Button loginButton;
	// private Button regButton;
	private Button faqButton;
	private Button newsButton;
	private Button kefuButton;
	private Button myorder;
	private int flag;
	// 设置变量表示登录状态
	// 请求码
	public static final int REQUESTCODE = 0x12;

	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.wode, container, false);
		}
		context = getActivity();

		user_imag = (ImageButton) view.findViewById(R.id.user_img);
		user_imag.setOnClickListener(this);
		// 创建一个新线程用于从网络上获取图片
		/*
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 * } }).start();
		 */
		settingButton = (Button) view.findViewById(R.id.wode_setting);
		settingButton.setOnClickListener(this);

		show_name = (TextView) view.findViewById(R.id.show_name);
		loginButton = (Button) view.findViewById(R.id.wode_login);
		flag = ((ImApp) getActivity().getApplication()).getFlag();
		String name=((ImApp) getActivity().getApplication()).getName();
		Log.d("llloooggg", "flag=" + flag);
		if (flag == 1) {
			loginButton.setVisibility(View.INVISIBLE);
			show_name.setText(name);
		}
		loginButton.setOnClickListener(this);
		// regButton = (Button) view.findViewById(R.id.wode_reg);
		// regButton.setOnClickListener(this);
		faqButton = (Button) view.findViewById(R.id.wode_faq);
		faqButton.setOnClickListener(this);
		newsButton = (Button) view.findViewById(R.id.wode_news);
		newsButton.setOnClickListener(this);
		kefuButton = (Button) view.findViewById(R.id.wode_kefu);
		kefuButton.setOnClickListener(this);
		/*
		 * quitButton = (Button) view.findViewById(R.id.wode_quit);
		 * quitButton.setOnClickListener(this);
		 */
		myorder = (Button) view.findViewById(R.id.wode_myorder);
		myorder.setOnClickListener(this);
		// 缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，
		// 要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_img:
			if (flag == 1) {
				Intent intent_1 = new Intent(context, wode_info.class);
				startActivity(intent_1);
				break;
			} else if (flag != 1) {
				/*
				 * Toast.makeText(Shouye.this, "您尚未登录，请先登录在访问本页面！",
				 * Toast.LENGTH_LONG).show();
				 */
				Intent intent5 = new Intent(context, Ts.class);
				startActivity(intent5);
				break;
				// Intent intent2 = new
				// Intent(MainActivity.this,LoginActivity.class);
				// startActivity(intent2);
				// break;
			}
			break;
		case R.id.wode_login:
			Intent intent2 = new Intent(context, DengluActivity.class);
			startActivity(intent2);
			break;
		/*
		 * case R.id.wode_reg: Intent intent3 = new Intent(getActivity(),
		 * reg.class); startActivity(intent3); break;
		 */
		case R.id.wode_faq:
			Intent intent4 = new Intent(context, faq.class);
			startActivity(intent4);
			break;
		case R.id.wode_news:
			Intent intent5 = new Intent(context, news.class);
			startActivity(intent5);
			break;
		case R.id.wode_kefu:
			Intent intent6 = new Intent(context, kefu.class);
			startActivity(intent6);
			break;
		case R.id.wode_setting:
			Intent intent7 = new Intent(context, setting.class);
			startActivity(intent7);
			break;
		/*
		 * case R.id.wode_quit: Intent intent8 = new Intent(context,
		 * DengluActivity.class); startActivity(intent8); break;
		 */
		case R.id.wode_myorder:
			Intent intent9 = new Intent(context, myorder.class);
			startActivity(intent9);
			break;
		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (REQUESTCODE == requestCode) {
			if (resultCode == 2 && alter_name.isSaved) {
				/*
				 * Toast.makeText(context, "Wode.onActivityResult",
				 * Toast.LENGTH_SHORT).show();
				 */
				// show_name.setText("昵称:" +
				// data.getStringExtra("altered_name"));
				// user_imag.setImageDrawable(wode_info.drawable);
			}
		}
		// 2017-7-19 16:59 for test
		/*
		 * Toast.makeText(getActivity(), wode_info.pic_Name + "---" +
		 * wode_info.pic_Path.toString(), Toast.LENGTH_LONG).show();
		 */
		// 2017-7-19 16:59 for test

	}

}
