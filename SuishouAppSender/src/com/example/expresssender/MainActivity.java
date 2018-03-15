package com.example.expresssender;

import com.example.expresssender.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button zhuye;
	private Button geren;

	private Zhuye mZhuye;
	private Geren mGeren;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Init();
		setDefaultFragment();
	}

	private void setDefaultFragment() {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		mZhuye = new Zhuye();
		ft.replace(R.id.show_fragment, mZhuye);
		ft.commit();
	}

	private void Init() {
		zhuye = (Button) findViewById(R.id.zhuye);
		zhuye.setOnClickListener(this);
		geren = (Button) findViewById(R.id.geren);
		geren.setOnClickListener(this);
	}

	public void onClick(View v) {
		FragmentManager fm=getFragmentManager();
		FragmentTransaction ft=fm.beginTransaction();

		switch (v.getId()) {
		case R.id.zhuye:
			if (mZhuye == null) {
				mZhuye = new Zhuye();
			}
			ft.replace(R.id.show_fragment, mZhuye);
			ft.commit();
			break;
		case R.id.geren:
			if (mGeren == null) {
				mGeren = new Geren();
			}
			ft.replace(R.id.show_fragment, mGeren);
			ft.commit();
			break;
		default:
			break;
		}

	}

}
