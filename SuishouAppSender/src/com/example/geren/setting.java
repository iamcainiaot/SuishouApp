package com.example.geren;

import com.example.expresssender.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class setting extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		TextView setting_titleTextView=(TextView) findViewById(R.id.setting_title);
		setting_titleTextView.setText("����");
	}
	
}
