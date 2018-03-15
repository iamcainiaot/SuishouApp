package com.example.share;

import com.example.easyexpress1.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class bigphoto extends Activity implements OnClickListener {
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.bigphoto);
	        
	        Intent intent=getIntent();
	        String ph=intent.getStringExtra("bigp");
	        
	        ImageView im=(ImageView) findViewById(R.id.bigphoto);
	        im.setOnClickListener(this);
	        
	        String file = "sdcard/Download/"+ph;
	        Bitmap bm = BitmapFactory.decodeFile(file); 
	        im.setImageBitmap(bm);
	        
	 }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		this.finish();
	}
}
