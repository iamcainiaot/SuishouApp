package com.example.shouye;


import java.util.Hashtable;

import com.example.bean.Dingdan;
import com.example.easyexpress1.R;
import com.example.easyhand1.MainActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class Code extends Activity implements OnClickListener  {

	
		private ImageView iv;
		private Button btn;
		static String order_number;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.code);
	        
	        iv=(ImageView) findViewById(R.id.code_iv);
	        btn=(Button) findViewById(R.id.code_btn);
	        btn.setOnClickListener(this);
	        
//	        Intent intent=getIntent();
//	        order_number=(String) intent.getSerializableExtra("order_number");
//	        order_number="111";
	        
	        
	        Log.i("logb","2222222222订单号号"+order_number);
	        Toast.makeText(Code.this, "11111111order_number="+order_number, Toast.LENGTH_SHORT).show();
	        createQRImage(order_number);
	        
	        
	   }
	    
   public void createQRImage(String url)
	   {
	    	
	    	int QR_WIDTH=400;
	    	int QR_HEIGHT=400;
	        try
	        {
	            //判断URL合法性
	            if (url == null || "".equals(url) || url.length() < 1)
	            {
	                return;
	            }
	            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
	            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	            //图像数据转换，使用了矩阵转换
	            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
	            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
	            //下面这里按照二维码的算法，逐个生成二维码的图片，
	            //两个for循环是图片横列扫描的结果
	            for (int y = 0; y < QR_HEIGHT; y++)
	            {
	                for (int x = 0; x < QR_WIDTH; x++)
	                {
	                    if (bitMatrix.get(x, y))
	                    {
	                        pixels[y * QR_WIDTH + x] = 0xff000000;
	                    }
	                    else
	                    {
	                        pixels[y * QR_WIDTH + x] = 0xffffffff;
	                    }
	                }
	            }
	            //生成二维码图片的格式，使用ARGB_8888
	            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
	            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
	            //显示到一个ImageView上面
	            iv.setImageBitmap(bitmap);
	        }
	        catch (WriterException e)
	        {
	            e.printStackTrace();
	        }
	    }


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if (id == R.id.action_settings) {
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }

		@Override
		public void onClick(View view) {
			
			Intent intent1=new Intent(Code.this,MainActivity.class);
			startActivity(intent1);
			
		}
	

}
