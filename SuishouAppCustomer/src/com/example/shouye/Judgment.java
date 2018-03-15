package com.example.shouye;

import http.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.Dingdan;
import com.example.easyexpress1.R;
import com.example.easyhand1.MainActivity;
import com.example.share.shareadd;
import com.example.utils.ContinuousClickUtil;
import com.example.utils.Iputil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Judgment extends Activity implements OnClickListener {
	
	private RatingBar ratingBar1;
	private RatingBar ratingBar2;
	private RatingBar ratingBar3;
	private TextView mRatingBarLevel1;
	private TextView mRatingBarLevel2;
	private TextView mRatingBarLevel3;
	private EditText Ed_judgment;
	private Button Bt_judgment;
	static final int QUERY_LOGIN =1002;
	private Dingdan order1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.judgment); 
		Intent intent = getIntent();  
        order1=(Dingdan)intent.getSerializableExtra("order");
   
    
		ratingBar1 = (RatingBar)findViewById(R.id.ratingBar1);//星
		mRatingBarLevel1 = (TextView)findViewById(R.id.mRatingBarLevel1);//星个数
		ratingBar2 = (RatingBar)findViewById(R.id.ratingBar2);//星
		mRatingBarLevel2 = (TextView)findViewById(R.id.mRatingBarLevel2);//星个数
		ratingBar3 = (RatingBar)findViewById(R.id.ratingBar3);//星
		mRatingBarLevel3 = (TextView)findViewById(R.id.mRatingBarLevel3);//星个数
		Ed_judgment=(EditText) findViewById(R.id.Ed_judgment);
		Bt_judgment=(Button) findViewById(R.id.Bt_judgment);
		Bt_judgment.setOnClickListener(this);
		 
		 ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
		        @Override
		        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		          float i =  ratingBar1.getRating();
		          if(i>4){
		            mRatingBarLevel1.setText("非常好");}
		          else if(i>3&&i<=4){
		        	  mRatingBarLevel1.setText("好");
		          }else if(i>2&&i<=3){
		        	  mRatingBarLevel1.setText("一般");
		          }else if(i>1&&i<=2){
		        	  mRatingBarLevel1.setText("差");
		          }else{
		        	  mRatingBarLevel1.setText("非常差");
		          }
		        }
		    });
		 
		 ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
		        @Override
		        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		          float i =  ratingBar2.getRating();
		          if(i>4){
			            mRatingBarLevel2.setText("非常好");}
			          else if(i>3&&i<=4){
			        	  mRatingBarLevel2.setText("好");
			          }else if(i>2&&i<=3){
			        	  mRatingBarLevel2.setText("一般");
			          }else if(i>1&&i<=2){
			        	  mRatingBarLevel2.setText("差");
			          }else{
			        	  mRatingBarLevel2.setText("非常差");
			          }
		        }
		    });
		 
		 ratingBar3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
		        @Override
		        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		          float i =  ratingBar3.getRating();
		          if(i>4){
			            mRatingBarLevel3.setText("非常好");}
			          else if(i>3&&i<=4){
			        	  mRatingBarLevel3.setText("好");
			          }else if(i>2&&i<=3){
			        	  mRatingBarLevel3.setText("一般");
			          }else if(i>1&&i<=2){
			        	  mRatingBarLevel3.setText("差");
			          }else{
			        	  mRatingBarLevel3.setText("非常差");
			          }
		        }
		    });
	}
	

	 private final  Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {//处理从另一个线程传过来的信息
	        	
				String text;
				int statusCode;

	            switch (msg.what) {
	            
	            case QUERY_LOGIN:
	            	statusCode = msg.getData().getInt("statusCode");
	            	Log.i("logB","QUERY_REGISTER: statusCode="+statusCode);
	            	JSONObject jb1 ;

	            	if(statusCode==200){
	            		text = msg.getData().getString("content");
			
						if(text!=null){
							//text = text.replace("\"", "");
							System.out.println("发布.java~~~~~~~~~~~~~~"+text);
							Toast.makeText(Judgment.this,"发布成功",Toast.LENGTH_SHORT).show();
							Log.i("logB","get content="+text);

							 Intent intent=new Intent(Judgment.this,MainActivity.class);
							 intent.putExtra("goto", 2);
							startActivity(intent);
					
						}else if(text==null){
							Toast.makeText(Judgment.this,"获取失败",Toast.LENGTH_SHORT).show();

			            	//Intent intent2 = new Intent(Shouye.this, Shouye.class);//获取失败则返回主页面
							//startActivity(intent2);
						}
						
	            		} else if(statusCode == 500){   
	            				Toast.makeText(Judgment.this,"服务器异常，请稍后重试",Toast.LENGTH_SHORT).show();
	            		} else {
	            				Toast.makeText(Judgment.this,"网络错误， 请重试",Toast.LENGTH_SHORT).show();
	            		}
	    			break;
				
	            }
	        }        
	    };

	 public void SaveJudgment() {
		 String judgment=null;
			if(ContinuousClickUtil.isContinuousClick()){
	        	return;
	        }
			judgment=Ed_judgment.getText().toString();
			 StringBuilder sb = new StringBuilder();
				sb.append("Ed_judgment=" + judgment);
				sb.append("&Ju_pro=" +ratingBar1.getRating());
				sb.append("&Ju_di=" + ratingBar2.getRating());
				sb.append("&Ju_sender=" +ratingBar3.getRating());
				sb.append("&order_num="+order1.getOrder_num());
			String url;
			url = Iputil.getFwqIp()+"/SuishouDemo/SaveJudgment?";
			Log.i("logB","url="+url);
			HttpUtils
			.httpPost(Judgment.this, url, sb.toString(), mHandler, QUERY_LOGIN);
	    
		}
	 

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SaveJudgment();
	}
	 public void back(View v) {
			finish();
		}

}
