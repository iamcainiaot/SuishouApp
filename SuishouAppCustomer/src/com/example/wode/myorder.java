package com.example.wode;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import http.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.Dingdan;
import com.example.easyexpress1.R;
import com.example.shouye.Judgment;
import com.example.shouye.qiangdan;
import com.example.shouye.showmessage;
import com.example.utils.ContinuousClickUtil;
import com.example.utils.Iputil;
import com.example.im.activity.ImApp;

import android.app.Activity;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class myorder extends Activity implements OnClickListener{
	static final int QUERY_LOGIN =1002;
	private ListView listview;
	private ImageView refresh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myorder);
		listview=(ListView) findViewById(R.id.Ls_myorder);
		refresh=(ImageView) findViewById(R.id.myorder_refresh);
		refresh.setOnClickListener(this);
		GetHistoryMEssage();
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
						Toast.makeText(myorder.this,"获取成功",Toast.LENGTH_SHORT).show();
						Log.i("logB","get content="+text);
						try {
							showMessage(text);//获取成功则将信息显示
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}else if(text==null){
						Toast.makeText(myorder.this,"获取失败",Toast.LENGTH_SHORT).show();

		            	//Intent intent2 = new Intent(Shouye.this, Shouye.class);//获取失败则返回主页面
						//startActivity(intent2);
					}
					
            		} else if(statusCode == 500){   
            				Toast.makeText(myorder.this,"服务器异常，请稍后重试",Toast.LENGTH_SHORT).show();
            		} else {
            				Toast.makeText(myorder.this,"网络错误，请重试",Toast.LENGTH_SHORT).show();
            		}
    			break;
			
            }
        }        
    };

 public void GetHistoryMEssage() {
	 
		if(ContinuousClickUtil.isContinuousClick()){
        	return;
        }
        int id=((ImApp)getApplication()).getId();
		String url;
		url = Iputil.getFwqIp()+"/SuishouDemo/ShowHistoryOrder?"+"user="+id;
		Log.i("logB","url="+url);
        HttpUtils.httpGet(myorder.this,
				url,
                mHandler,
                QUERY_LOGIN
		);
    
	}
 
 
 public void showMessage(String text) throws JSONException{
		//String text ="[{pro_name:挑子,sender:'',rec_address:河北省,pro_num:12,pro_address:安徽省,receiver:'',oid:0,order_num:'123',code:'',type:水果},{pro_name:g,sender:'1',rec_address:gj,pro_num:1,pro_address:jhg,receiver:'',oid:0,order_num:'',code:'',type:gh},{pro_name:正宗猕猴桃,sender:'2',rec_address:安徽省六安市,pro_num:10,pro_address:安徽省合肥市,receiver:'',oid:0,order_num:'',code:'',type:水果},{pro_name:阿迪达斯上衣,sender:'',rec_address:安徽省六安市,pro_num:1,pro_address:安徽省合肥市,receiver:'',oid:0,order_num:'',code:'',type:衣服},{pro_name:1,sender:'',rec_address:1,pro_num:1,pro_address:1,receiver:'',oid:0,order_num:'',code:'',type:''},{pro_name:1,sender:'',rec_address:1,pro_num:1,pro_address:1,receiver:'',oid:0,order_num:'',code:'',type:''}]";
			//String text = "[{'pro_name':'毛巾','pro_num':'1','pro_type':'生活用品','pro_address':'安徽','rec_address':'安徽'},{'pro_name':'饼干','pro_num':'1','pro_type':'食品','pro_address':'安徽','rec_address':'浙江'}]";
			   
			JSONArray jsonarray = new JSONArray(text);
			
			final List<Dingdan> list = new ArrayList<Dingdan>();
			
			for(int i = 0;i<jsonarray.length();i++){
				
				JSONObject jsonobject = jsonarray.getJSONObject(i);

				if(jsonobject !=null){
		
					String order_num=jsonobject.optString("order_num");
					String pro_name=jsonobject.optString("pro_name");
					String pro_num=jsonobject.optString("pro_num");
					String price=jsonobject.optString("price");
					String judgment=jsonobject.optString("judgment");
//					String rec_address=jsonobject.optString("rec_address");
//					String pro_type=jsonobject.optString("type");
//					String pro_address=jsonobject.optString("pro_address");
//					String sender=jsonobject.optString("sender");
					
					
					Dingdan s = new Dingdan();
					
					s.setOrder_num(order_num);
					s.setPro_name(pro_name);
					s.setPro_num(Integer.parseInt(pro_num));
					s.setPrice(price);
					s.setJudgment(judgment);
//					s.setPro_address(pro_address);
//					s.setRec_address(rec_address);
//					s.setPro_type(pro_type);
//					s.setSender(sender);
//					
					list.add(s);
				}
			}
			
			
			  //生成动态数组，加入数据  
	        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>(); 
	        int i=0;
	        for (Dingdan s : list)  
	        {  
	        	
	            HashMap<String, Object> map = new HashMap<String, Object>();  
	           // map.put("ItemImage", R.drawable.my);//图像资源的ID  
	            map.put("ItemPro_name",s.getPro_name());  
	            map.put("ItemPro_num","*"+s.getPro_num() );  
	            map.put("Item_price",s.getPrice());  
//	            map.put("ItemRec_address",s.getRec_address() );
	            String judgment=null;
	            judgment=s.getJudgment();
	           // Log.i("logb","抢单人"+sender);
	            String Item_stan=null;
	            if(judgment==null||judgment.length()<=0){
	            	Item_stan="未评价";
	            }else{
	            	Item_stan="已评价";
	            }
	            map.put("Item_stan",Item_stan );
	            listItem.add(map);  
	            i++;
	        }  
	        Log.i("logB","到这");
	        //生成适配器的Item和动态数组对应的元素  
	        SimpleAdapter listItemAdapter = new SimpleAdapter(myorder.this,listItem,//数据源   
	        		
	            R.layout.orderitem,//ListItem的XML实现  
	            //动态数组与ImageItem对应的子项          
	            new String[] {"ItemPro_name","ItemPro_num","Item_price","Item_stan"},   
	            //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
	            new int[] {R.id.TV_Oproname,R.id.Tv_Onum,R.id.Tv_Oprice,R.id.Tv_Ojudgment}
	        );  
	        
	        //添加并且显示  
	        listview.setAdapter(listItemAdapter);  
	        listview.setOnItemClickListener(new OnItemClickListener() {  //点击后显示订单具体信息
	        	  
	            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
	                    long arg3) {  
	            	Log.i("logB","点击第"+arg2+"个项目");
	            	Dingdan a=list.get(arg2);
	            	Log.i("logB","是"+a.getPro_name());
	            	
	            	if(a.getJudgment()==null||a.getJudgment().length()<=0){	
	            		Intent intent = new Intent();
	            		intent.setClass(myorder.this, Judgment.class);
	            		Bundle bundle = new Bundle();
	            		bundle.putSerializable("order", a);
	            		intent.putExtras(bundle);
	            		startActivity(intent);
	            	}else{
	            		Toast.makeText(myorder.this,"已评价!",Toast.LENGTH_SHORT).show();
	            		}
	            	
	              
	            }  
	        });  
	        
}

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	GetHistoryMEssage();
}


}
