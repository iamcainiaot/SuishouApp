package com.example.zhuye;

import com.example.expresssender.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Saomiao extends Activity implements OnClickListener {

	// 扫描请求码
	private final static int SCANNIN_GREQUEST_CODE = 1;

	// 声明控件
	private Button saomiao;
	private ImageButton saomiao_back;

	// 显示扫描结果
	private TextView result;
	private TextView result1;
	// 显示扫描的照片
	private ImageView bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.saomiao);
		saomiao = (Button) findViewById(R.id.saomiao);
		saomiao.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(Saomiao.this,
						MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// 点击按钮跳转到二维码扫描界面，使用startActivityForResult跳转
				// 扫描后返回此界面
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});
		saomiao_back = (ImageButton) findViewById(R.id.saomiao_back);
		saomiao_back.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
		result = (TextView) findViewById(R.id.result);
		result1 = (TextView) findViewById(R.id.result1);
		bitmap = (ImageView) findViewById(R.id.bitmap);

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// 显示结果
				result.setText("收货地址：合肥市蜀山区莲花路123号18255119947");
				result1.setText("手机号码：18255555555");
				//
				
				
				
				bitmap.setImageBitmap((Bitmap) data
						.getParcelableExtra("bitmap"));
			}
			break;
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
