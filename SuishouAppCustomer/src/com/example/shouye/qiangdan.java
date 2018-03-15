package com.example.shouye;

import http.HttpUtils;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.bean.Dingdan;
import com.example.easyhand1.MainActivity;
import com.example.easyexpress1.R;
import com.example.im.activity.ImApp;
import com.example.utils.ContinuousClickUtil;
import com.example.utils.Iputil;

public class qiangdan extends Activity  implements OnClickListener {
	
	static final int QUERY_LOGIN =1002;
	
	private ProgressDialog progressDialog;
	private ImageButton qiang_refresh;
	 protected void onCreate(Bundle savedInstanceState)   {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.qiangdan);
	        qiang_refresh=(ImageButton) findViewById(R.id.qiang_refresh);
	        qiang_refresh.setOnClickListener(this);
	       
	        qiangdan();
 
	   }
	  
	 public void back(View v) {
			finish();
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
							Toast.makeText(qiangdan.this,"获取成功",Toast.LENGTH_SHORT).show();
							Log.i("logB","get content="+text);
							try {
								showMessage(text);//获取成功则将信息显示
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}else if(text==null){
							Toast.makeText(qiangdan.this,"获取失败",Toast.LENGTH_SHORT).show();

			            	Intent intent2 = new Intent(qiangdan.this, MainActivity.class);//获取失败则返回主页面
							startActivity(intent2);
						}
						
	            		} else if(statusCode == 500){   
	            				Toast.makeText(qiangdan.this,"服务器异常，请稍后重试",Toast.LENGTH_SHORT).show();
	            		} else {
	            				Toast.makeText(qiangdan.this,"网络错误，请   重试",Toast.LENGTH_SHORT).show();
	            		}
        			break;
				
	            }
	        }        
	    };
	 
	 public void qiangdan() {
		 
			if(ContinuousClickUtil.isContinuousClick()){
	        	return;
	        }
	        
			String url;
			url = Iputil.getFwqIp()+"/SuishouDemo/GetMessageDemo";
			Log.i("logB","url="+url);
	        HttpUtils.httpGet(qiangdan.this,
					url,
	                mHandler,
	                QUERY_LOGIN
			);
	    
		}
	 
	 
	 
	
	 public void showMessage(String text) throws JSONException{
		// String text ="[{pro_name:挑子,sender:'',rec_address:河北省,pro_num:12,pro_address:安徽省,receiver:'',oid:0,order_num:'123',code:'',type:水果},{pro_name:g,sender:'',rec_address:gj,pro_num:1,pro_address:jhg,receiver:'',oid:0,order_num:'',code:'',type:gh},{pro_name:正宗猕猴桃,sender:'',rec_address:安徽省六安市,pro_num:10,pro_address:安徽省合肥市,receiver:'',oid:0,order_num:'',code:'',type:水果},{pro_name:阿迪达斯上衣,sender:'',rec_address:安徽省六安市,pro_num:1,pro_address:安徽省合肥市,receiver:'',oid:0,order_num:'',code:'',type:衣服},{pro_name:1,sender:'',rec_address:1,pro_num:1,pro_address:1,receiver:'',oid:0,order_num:'',code:'',type:''},{pro_name:1,sender:'',rec_address:1,pro_num:1,pro_address:1,receiver:'',oid:0,order_num:'',code:'',type:''}]";
			//String text = "[{'pro_name':'毛巾','pro_num':'1','pro_type':'生活用品','pro_address':'安徽','rec_address':'安徽'},{'pro_name':'饼干','pro_num':'1','pro_type':'食品','pro_address':'安徽','rec_address':'浙江'}]";
			   
			JSONArray jsonarray = new JSONArray(text);
			
			final List<Dingdan> list = new ArrayList<Dingdan>();
			
			for(int i = 0;i<jsonarray.length();i++){
				
				JSONObject jsonobject = jsonarray.getJSONObject(i);

				if(jsonobject !=null){
		
					String order_num=jsonobject.optString("order_num");
					Log.i("logb","订单号"+order_num);
					String pro_name=jsonobject.optString("pro_name");
					String pro_num=jsonobject.optString("pro_num");
					String rec_address=jsonobject.optString("rec_address");
					String pro_type=jsonobject.optString("pro_type");
					String pro_address=jsonobject.optString("pro_address");
					
					Dingdan s = new Dingdan();
					
					s.setOrder_num(order_num);
					s.setPro_name(pro_name);
					s.setPro_num(Integer.parseInt(pro_num));
					s.setPro_address(pro_address);
					s.setRec_address(rec_address);
					s.setPro_type(pro_type);
					
					list.add(s);
				}
			}
			
			
			  //生成动态数组，加入数据  
	        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>(); 
	        int i=0;
	        for (Dingdan s : list)  
	        {  
	        	Log.i("logB","i="+i);
	            HashMap<String, Object> map = new HashMap<String, Object>();  
	            map.put("ItemImage", R.drawable.my);//图像资源的ID  
	            map.put("ItemPro_name",s.getPro_name());  
	            map.put("ItemPro_num",s.getPro_num() );  
	            map.put("ItemPro_type",s.getPro_type() );
	            map.put("ItemPro_address", s.getPro_address());
	            map.put("ItemRec_address",s.getRec_address() );  
	            listItem.add(map);  
	            i++;
	        }  
	        Log.i("logB","到这");
	        //生成适配器的Item和动态数组对应的元素  
	        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源   
	        		
	            R.layout.listitem,//ListItem的XML实现  
	            //动态数组与ImageItem对应的子项          
	            new String[] {"ItemPro_name","ItemPro_address","ItemRec_address"},   
	            //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
	            new int[] {R.id.ItemPro_name,R.id.ItemPro_address,R.id.ItemRec_address}  
	        );  
	        Log.i("logB","到这了");
	        ListView list1 = (ListView) findViewById(R.id.ListView01);
	        //添加并且显示  
	        list1.setAdapter(listItemAdapter);  
	        Log.i("logB","到这了了");
	        
	        list1.setOnItemClickListener(new OnItemClickListener() {  //点击后显示订单具体信息
	        	  
	            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
	                    long arg3) {  
	            	Log.i("logB","点击第"+arg2+"个项目");
	            	Dingdan a=list.get(arg2);
	            	Log.i("logB","是"+a.getPro_name());
	            

	            	Intent intent = new Intent();
	            	intent.setClass(qiangdan.this, showmessage.class);
	            	Bundle bundle = new Bundle();
	            	bundle.putSerializable("order", a);
	            	intent.putExtras(bundle);
	            	startActivity(intent);	
	              
	            }  
	        });  
			
	 }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		qiangdan();
	}
}
