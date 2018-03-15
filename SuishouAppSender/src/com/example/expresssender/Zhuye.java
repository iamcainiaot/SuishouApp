package com.example.expresssender;

import com.example.expresssender.R;
import com.example.zhuye.Dingwei;
import com.example.zhuye.MipcaActivityCapture;
import com.example.zhuye.Saomiao;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Zhuye extends Fragment implements OnClickListener {

	private View view;

	// 扫描请求码
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private ImageButton saoyisao;
	private ImageButton dingwei;
	private ImageButton call;
	private ImageButton message;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			// true：并且root存在，将xml挂载到root下，返回root false：返回xml的跟布局
			// inflater.inflate(resource, root, attachToRoot)
			view = inflater.inflate(R.layout.zhuye, container, false);
		}

		// 获取控件
		saoyisao = (ImageButton) view.findViewById(R.id.saoyisao);
		saoyisao.setOnClickListener(this);
		dingwei = (ImageButton) view.findViewById(R.id.dingwei);
		dingwei.setOnClickListener(this);
		call = (ImageButton) view.findViewById(R.id.call);
		call.setOnClickListener(this);
		message = (ImageButton) view.findViewById(R.id.message);
		message.setOnClickListener(this);

		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saoyisao:
			Intent intent1 = new Intent(getActivity(),
					Saomiao.class);
			startActivity(intent1);
			//intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// 点击按钮跳转到二维码扫描界面，使用startActivityForResult跳转
			// 扫描后返回此界面
			//startActivityForResult(intent1, SCANNIN_GREQUEST_CODE);
			break;
		case R.id.dingwei:
			Intent intent2 = new Intent(getActivity(), Dingwei.class);
			startActivity(intent2);
			break;
		case R.id.call:
			// 这里"tel:"+电话号码 是固定格式，系统一看是以"tel:"开头的，就知道后面应该是电话号码。
			Intent intent3 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
					+ ""));
			startActivity(intent3);
			break;
		case R.id.message:
			// "smsto:xxx" xxx可以是指定联系人
			Uri someoneUri = Uri.parse("smsto:");
			Intent intent4 = new Intent(Intent.ACTION_SENDTO, someoneUri);
			// "sms_body"必须一样，smsbody是发送短信内容content
			intent4.putExtra("sms_body", "");
			startActivity(intent4);
			break;

		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:

			
			break;

		}

	}

}
