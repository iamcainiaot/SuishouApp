package com.example.shouye;

import com.example.easyexpress1.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;


public class message extends Activity  {
	
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.message);
	        
	        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()

	        .detectDiskReads().detectDiskWrites().detectNetwork()

	        .penaltyLog().build());
	   }
	 


}
