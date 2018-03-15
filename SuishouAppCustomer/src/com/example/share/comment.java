package com.example.share;

import http.HttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.bean.CommentInfor;
import com.example.bean.Dingdan;
import com.example.easyexpress1.R;
import com.example.easyhand1.MainActivity;
import com.example.im.activity.ImApp;

import com.example.utils.ContinuousClickUtil;
import com.example.utils.Iputil;


public class comment extends Activity implements OnClickListener{
	static final int QUERY_LOGIN =1002;
	int id;
	EditText ed;
	Button send;
	ImageView refresh;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        
       
        send=(Button) findViewById(R.id.send);
        send.setOnClickListener(this);
        refresh=(ImageView) findViewById(R.id.comment_refresh);
        refresh.setOnClickListener(this);
        Intent intent=getIntent();
        id=intent.getIntExtra("shareid", 0);
        Log.i("logb","ididididdi"+String.valueOf(id));
        getmessage();
        
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
            	//JSONObject jb1 ;

            	if(statusCode==200){
            		text = msg.getData().getString("content");
		
					if(text!=null){
						//text = text.replace("\"", "");
						System.out.println("发布.java~~~~~~~~~~~~~~"+text);
						Toast.makeText(comment.this,"获取成功",Toast.LENGTH_SHORT).show();
						Log.i("logB","get content="+text);
						try {
							showMessage(text);//获取成功则将信息显示
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}else if(text==null){
						Toast.makeText(comment.this,"获取失败",Toast.LENGTH_SHORT).show();
		            	Intent intent2 = new Intent(comment.this, MainActivity.class);//获取失败则返回主页面
						startActivity(intent2);
					}
					
            		} else if(statusCode == 500){   
            				Toast.makeText(comment.this,"服务器异常，请稍后重试",Toast.LENGTH_SHORT).show();
            		} else {
            				Toast.makeText(comment.this,"网络错误，请 重试",Toast.LENGTH_SHORT).show();
            		}
    			break;
			
            }
        }        
    };
 
 public void getmessage() {//获取内容
	 
		if(ContinuousClickUtil.isContinuousClick()){
        	return;
        }
        
		
		
		String url;
		url = Iputil.getFwqIp()+"/SuishouDemo/ShowCommentByShare_ID?id="+id ;
		Log.i("logB","url="+url);
        HttpUtils.httpGet(comment.this,
				url,
                mHandler,
                QUERY_LOGIN
		);}
 
 
 public void downLoad(String sourceUrl){
		
		boolean flag;
		try {
			URL url = new URL(sourceUrl);  //创建下载地址对应的URL对象
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();  //创建一个连接
			
	//		urlConn.setRequestMethod("post");  //指定请求方式为post
			InputStream is = urlConn.getInputStream(); //获取输入流对象
			if(is != null){
				String expandName = sourceUrl.substring(
						sourceUrl.lastIndexOf(".")+1,
						sourceUrl.length()).toLowerCase();  //获取文件的扩展名
				
				String fileName = sourceUrl.substring(
						sourceUrl.lastIndexOf("/")+1,
						sourceUrl.lastIndexOf("."));  //获取文件名
				
				File file = new File("sdcard/Download/"+fileName+"."+expandName);  //创建文件
//				File file = new File("/sdcard/pictures/"+fileNam  e+"."+expandName); //在SD卡上创建文件
				FileOutputStream fos = new FileOutputStream(file); //创建一个文件输出流对象
				
				byte buf[] = new byte[3000]; //创建一个字节数组
				//读取文件到输出流对象中
				while(true){
					int numread = is.read(buf);
					if(numread <= 0 ){
						break;
					}else{
						fos.write(buf,0,numread);
					}
				}
			}
			is.close();  //关闭输入流对象
			urlConn.disconnect();  //关闭连接
			flag = true;  
		}catch(MalformedURLException e) {
			e.printStackTrace();  //输出异常信息
			flag = false;
		}catch(IOException e){
			e.printStackTrace();  //输出异常信息
			flag = false;
		}
		System.out.println(flag);
	}
 
	public void showMessage(String text) throws JSONException{
		//String text="[{name:张三,content:质量不错,time:2017-7-7},{name:张三,content:质量不错,time:2017-7-7},{name:张三,content:质量不错,time:2017-7-7}]";  
		JSONArray jsonarray = new JSONArray(text);
			
		final List<CommentInfor> list = new ArrayList<CommentInfor>();
			
		for(int i = 0;i<jsonarray.length();i++){
				
			JSONObject jsonobject = jsonarray.getJSONObject(i);

			if(jsonobject !=null){
		
				String name=jsonobject.optString("name");
				String pro_image=jsonobject.optString("pro_image");
				String content=jsonobject.optString("content");
				String time=jsonobject.optString("time");
			
				CommentInfor s = new CommentInfor();
					
				s.setName(name);
				s.setContent(content);
				s.setTime(time);
				s.setPro_image(pro_image);
				list.add(s);
			}
		}
			
			
		//生成动态数组，加入数据  
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>(); 
	    int i=0;
	    for (final CommentInfor s : list)
	    {  
	    	//final share share=new share();
	        HashMap<String, Object> map = new HashMap<String, Object>();  
	        map.put("ItemImage", s.getPro_image());//图像资源的ID  
	        map.put("Itemname",s.getName());  
	        map.put("Itemtime",s.getTime() );  
	        map.put("Itemcontent",s.getContent() );  
	    
	        new Thread(){
    			public void run(){
    				
		    			//也可以定义一个方法来下载图片资源
		    			String sourceUrl=Iputil.getFwqIp()+"/App/photo/";
		    			sourceUrl +=s.getPro_image() ;
		    			downLoad(sourceUrl);							
    			}
    		}.start();
	        listItem.add(map);  
	        i++;
	        }  
	    
    
	    
	    //Context con=getBaseContext();
	        //生成适配器的Item和动态数组对应的元素  
	    final CAdapters listItemAdapter = new CAdapters(comment.this,listItem,//数据源   	
	        R.layout.commentitem,//ListItem的XML实现  
	        //动态数组与ImageItem对应的子项          
	        new String[] {"ItemImage","Itemname","Itemtime","Itemcontent"},   
	        //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
	        new int[] {R.id.Comment_image,R.id.Comment_name,R.id.Comment_time,R.id.Comment_con}  
	        );  
	        ListView list1 = (ListView) findViewById(R.id.listView1);
	        //添加并且显示  
	        list1.setAdapter(listItemAdapter);  
	
	 }

	
	 public class CAdapters extends BaseAdapter {//自定义适配器

			private class ButtonViewHolder {
				ImageView image;
		        TextView name;  
		        TextView time;  
		        TextView comment;
				public TextView findViewWithTag(String string) {
					// TODO Auto-generated method stub
					return null;
				}
		    }  

		    private Context mContext;  
		    private List<HashMap<String, Object>> list;  
		    private ButtonViewHolder holder;  
		    private LayoutInflater mInflater;  
		    private String[] keyString;  
		    private int[] valueViewID;  
			
		    
		    public CAdapters(Context context, ArrayList<HashMap<String, Object>> list,  
		            int resource, String[] from, int[] to) {  
		        this.mContext = context;  
		        this.list = list;  

		        // 获得布局文件对象  
		        mInflater = (LayoutInflater) context  
		                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		        keyString = new String[from.length];  
		        valueViewID = new int[to.length];  

		        // 复制数组  
		        System.arraycopy(from, 0, keyString, 0, from.length);  
		        System.arraycopy(to, 0, valueViewID, 0, to.length);  
		    }  
		    
		    @Override  
		    public int getCount() {  
		        return list.size();  
		    }  

		    @Override  
		    public Object getItem(int position) {  
		        return list.get(position);  
		    }  

		    public void removeItem(int position) {  
		        list.remove(position);  
		        // 通知数据集已改变，请求自刷新  
		        this.notifyDataSetChanged();  
		    }  

		    @Override  
		    public long getItemId(int position) {  
		        return position;  
		    }  

		    @Override  
		    public View getView(int position, View convertView, ViewGroup parent) {  
		    	
		        if (convertView != null) {  
		            holder = (ButtonViewHolder) convertView.getTag();  
		        } else {  	
		            convertView = mInflater.inflate(R.layout.commentitem, null);  
		            holder = new ButtonViewHolder();  
		            holder.image=(ImageView) convertView.findViewById(R.id.Comment_image);
		            holder.name = (TextView) convertView  
		                    .findViewById(R.id.Comment_name);
		            holder.time = (TextView) convertView  
		                    .findViewById(R.id.Comment_time);  
		            holder.comment=(TextView) convertView.findViewById(R.id.Comment_con);
		            convertView.setTag(holder); 
	
		        }  
		        
		        
		       HashMap<String, Object> appInfo = list.get(position);  
		        
		        if (appInfo != null) {  
		        	 
		        	String pro_image=(String) appInfo.get(keyString[0]);
		            String aname = (String) appInfo.get(keyString[1]);    
		            String adate = (String) appInfo.get(keyString[2]);
		            String acomment = (String) appInfo.get(keyString[3]);

		            
		            Log.i("logb", "评论头像"+pro_image);
		            
		            String file = "sdcard/Download/"+pro_image;
		            
		            Bitmap bm = BitmapFactory.decodeFile(file); 
		            holder.image.setImageBitmap(bm);
		            //holder.image.setBackgroundResource(R.drawable.my);
		            holder.name.setText(aname);//设置对应数据  
		            holder.time.setText(adate);  
		            holder.comment.setText(acomment);


		        }  
		        return convertView;  
			
		    }    
	 }
	
	private final  Handler nHandler = new Handler() {
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
	            	Log.i("logB","result="+result);
	            	
	            	if(result!=null){
	            		text = text.replace("\"", "");
	            		System.out.println("发布.java~~~~~~~~~~~~~~"+text);
	            		Toast.makeText(comment.this,"评论成功",Toast.LENGTH_SHORT).show();
	            		
	            		Intent intent2 = new Intent(comment.this, MainActivity.class);//获取成功跳转至首页
	            		intent2.putExtra("goto", 1);
	            		startActivity(intent2);
	            	}else if(result.equals("error")){
	            		Toast.makeText(comment.this,"评论失败",Toast.LENGTH_SHORT).show();
					}else if(result.equals("null")){
						Toast.makeText(comment.this,"用户名不存在",Toast.LENGTH_SHORT).show();
					}
	            	Log.i("logB","get content="+text);
				} else if(statusCode == 500){
					Toast.makeText(comment.this,"服务器异常，请稍后重试",Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(comment.this,"网络错误，请重试",Toast.LENGTH_SHORT).show();
				}
            	break;
            	} catch (JSONException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			
            }
        }        
    };

	public void back(View v) {
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.send: 
		 ed=(EditText) findViewById(R.id.commented);
		 String comment=ed.getText().toString();
		 int name=((ImApp)getApplication()).getId();
	        StringBuilder sb = new StringBuilder();
			String url;
			sb.append("name=" + name);
			sb.append("&content=" + comment);
			sb.append("&shareID=" + id);
	
			Log.i("logB", "params=" + sb.toString());
			url = Iputil.getFwqIp()+"/SuishouDemo/AddCommentServlet";
			Log.i("logB","url="+url);
			HttpUtils.httpPost(comment.this, url, sb.toString(), nHandler,
		    		QUERY_LOGIN);
			break;
		case R.id.comment_refresh:
			getmessage();
			break;
		}
	}
}
