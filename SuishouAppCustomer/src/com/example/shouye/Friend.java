package com.example.shouye;

import com.example.easyexpress1.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Friend extends Fragment {

	// 缓存fragment view
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.friend, container, false);
		}

		// 缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，
		// 要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

}
