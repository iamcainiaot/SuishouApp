package com.example.geren;

import com.example.expresssender.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class news extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		TextView news_titleTextView=(TextView) findViewById(R.id.news_title);
		news_titleTextView.setText("ϵͳ��Ϣ");
		
	}
	
}
