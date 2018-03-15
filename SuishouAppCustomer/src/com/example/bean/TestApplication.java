package com.example.bean;

import android.app.Application;

public class TestApplication extends Application {
	private int zanzan;
	
	public int getCurIndex() {

		return zanzan;

		}

		public void setzanzan(int zanzan) {

		this.zanzan = zanzan;

		}
}
