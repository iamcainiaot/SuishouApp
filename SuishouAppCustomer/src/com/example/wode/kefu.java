package com.example.wode;

import com.example.easyexpress1.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class kefu extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kefu);
		TextView kefu_titleTextView=(TextView) findViewById(R.id.kefu_title);
		kefu_titleTextView.setText("�ͷ�");
		
	}
	
}
