package com.example.im.activity;

import com.example.easyexpress1.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Ts extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.im_tishidenglu);
		Button login_1 = (Button) findViewById(R.id.login_1);
		login_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.login_1:
					Intent intent = new Intent(Ts.this, DengluActivity.class);
					startActivity(intent);
				}
			}
		});
		/*
		 * Button nologin_1 = (Button) findViewById(R.id.nologin_1);
		 * nologin_1.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub switch (v.getId()) { case R.id.nologin_1: Intent intent = new
		 * Intent(tishidenglu.this, DengluActivity.class);
		 * startActivity(intent); } } });
		 */
	}

}
