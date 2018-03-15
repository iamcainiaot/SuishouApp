package com.example.geren;

import com.example.expresssender.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class faq extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq);
		TextView faq_titleTextView=(TextView) findViewById(R.id.faq_title);
		faq_titleTextView.setText("��������");
		
	}
	
}
