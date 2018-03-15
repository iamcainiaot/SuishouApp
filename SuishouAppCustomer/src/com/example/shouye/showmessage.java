package com.example.shouye;

import http.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.Dingdan;
import com.example.easyexpress1.R;
import com.example.easyhand1.MainActivity;
import com.example.im.activity.ImApp;
import com.example.utils.ContinuousClickUtil;
import com.example.utils.Iputil;
import com.example.wode.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class showmessage extends Activity implements OnClickListener{
	
	static final int QUERY_LOGIN =1002;
	private EditText et1;
	private EditText et2;
	private EditText et3;
	private EditText et4;
	private EditText et5;
	private Button bt1;
	Dingdan order1;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        
        Intent intent = getIntent();  
        order1=(Dingdan)intent.getSerializableExtra("order");
   
        Log.i("logb","订单号号"+order1.getOrder_num());
        
        //获取订单号
        Code code=new Code();
        code.order_number=order1.getOrder_num();
        
        bt1=(Button) findViewById(R.id.button1);
        bt1.setOnClickListener(this);
       
        int i=order1.getPro_num();
        et1=(EditText) findViewById(R.id.editText1);
        et2=(EditText) findViewById(R.id.editText2);
        et3=(EditText) findViewById(R.id.editText3);
        et4=(EditText) findViewById(R.id.editText4);
        et5=(EditText) findViewById(R.id.editText5);
       
        et1.setText(order1.getPro_name());
        et2.setText(String.valueOf(i));
        et3.setText(order1.getPro_type());
        et4.setText(order1.getPro_address());
        et5.setText(order1.getRec_address());

   }
	public void back(View v) {
		finish();
	}

	
	 private final  Handler mHandler1 = new Handler() {
    @Override
    public void handleMessage(Message msg) {
    	
		String text;
		int statusCode;

        switch (msg.what) {
        
        case QUERY_LOGIN:
        	statusCode = msg.getData().getInt("statusCode");
        	Log.i("logB","QUERY_REGISTER: statusCode="+statusCode);
        	try {
				JSONObject jb1 ;
			
        	if(statusCode==200){
            	text = msg.getData().getString("content");
            	jb1 = new JSONObject(text);
            	
           //执行HttpUtil之后，获取服务器返回的响应码
            	String result = jb1.getString("result");
            	//String order_num = jb1.getString("order_num");
            	
            	Log.i("logB","result="+result);
            	
            	if(result.equals("success")){
            		text = text.replace("\"", "");
            		System.out.println("发布.java~~~~~~~~~~~~~~"+text);
            		Toast.makeText(showmessage.this,"抢单成功",Toast.LENGTH_SHORT).show();
            		Intent intent2 = new Intent(showmessage.this, Code.class);
            		startActivity(intent2);
            	}else if(result.equals("error")){
            		Toast.makeText(showmessage.this,"抢单失败",Toast.LENGTH_SHORT).show();
				}else if(result.equals("null")){
					Toast.makeText(showmessage.this,"用户名不存在",Toast.LENGTH_SHORT).show();
				}
            	Log.i("logB","get content="+text);
			} else if(statusCode == 500){
				Toast.makeText(showmessage.this,"服务器异常，请稍后重试",Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(showmessage.this,"网络错误，请重试",Toast.LENGTH_SHORT).show();
			}
        	break;
        	} catch (JSONException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		
        }
    }        
};


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		AlertDialog.Builder builder = new Builder(this);  
        builder.setTitle("确认");  
        builder.setMessage("确认抢单");  
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				StringBuilder sb = new StringBuilder();
				
				sb.append("order_num=" + order1.getOrder_num());
				
				sb.append("&sender=" + ((ImApp) getApplication()).getId());//应放抢单人信息
				
				String url;
				url = Iputil.getFwqIp()+"/SuishouDemo/GrabServerDemo";//+"?"+sb.toString();;
				Log.i("logB","url="+url);
			  
			    HttpUtils.httpPost(showmessage.this, url, sb.toString(), mHandler1,
			    		QUERY_LOGIN);
					
				
			}
        });  
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}  
		 
        });  
        builder.show();  
        
	if(ContinuousClickUtil.isContinuousClick()){
    	return;
    }
    
	
	}
	
}
