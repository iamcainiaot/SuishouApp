package com.example.share;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.im.activity.ImApp;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.easyexpress1.R;
import com.example.easyhand1.MainActivity;

import com.example.utils.Iputil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

public class shareadd extends Activity implements OnClickListener {  
	  
    private GridView gridView1;              //网格显示缩略图  
    private Button fabiao;        //发布按钮  
    private final int IMAGE_OPEN = 1;        //打开图片标记  
    private String pathImage;                //选择图片路径  
    private Bitmap bmp;                      //导入临时图片  
    private ArrayList<HashMap<String, Object>> imageItem;  
    private SimpleAdapter simpleAdapter;     //适配器  
    private String[] allpath;
    int i=0;
    private EditText ed;
      
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.shareadd);  
        allpath= new String[100];
        /* 
         * 防止键盘挡住输入框 
         * 不希望遮挡设置activity属性 android:windowSoftInputMode="adjustPan" 
         * 希望动态调整高度 android:windowSoftInputMode="adjustResize" 
         */  
        getWindow().setSoftInputMode(WindowManager.LayoutParams.  
                SOFT_INPUT_ADJUST_PAN);  
        //锁定屏幕  
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  
        //setContentView(R.layout.addshare);  
        //获取控件对象  
        gridView1 = (GridView) findViewById(R.id.gridView1); 
        ed=(EditText) findViewById(R.id.editText1);
        fabiao=(Button) findViewById(R.id.fabiao);
        fabiao.setOnClickListener(this);
  
        /* 
         * 载入默认图片添加图片加号 
         * 通过适配器实现 
         * SimpleAdapter参数imageItem为数据源 R.layout.griditem_addpic为布局 
         */  
        //获取资源图片加号  
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.add);  
        imageItem = new ArrayList<HashMap<String, Object>>();  
        HashMap<String, Object> map = new HashMap<String, Object>();  
        map.put("itemImage", bmp);  
        imageItem.add(map);  
        simpleAdapter = new SimpleAdapter(this,   
                imageItem, R.layout.gridviewitem,   
                new String[] { "itemImage"}, new int[] { R.id.imageView1});   
        /* 
         * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如 
         * map.put("itemImage", R.drawable.img); 
         * 解决方法: 
         *              1.自定义继承BaseAdapter实现 
         *              2.ViewBinder()接口实现 
         *  参考 http://blog.csdn.net/admin_/article/details/7257901 
         */  
        simpleAdapter.setViewBinder(new ViewBinder() {   
        	@Override
            public boolean setViewValue(View view, Object data,    
                    String textRepresentation) {    
                // TODO Auto-generated method stub    
                if(view instanceof ImageView && data instanceof Bitmap){    
                    ImageView i = (ImageView)view;    
                    i.setImageBitmap((Bitmap) data);    
                    return true;    
                }    
                return false;    
            }  
        });    
        gridView1.setAdapter(simpleAdapter);
          
        /* 
         * 监听GridView点击事件 
         * 报错:该函数必须抽象方法 故需要手动导入import android.view.View; 
         */  
        gridView1.setOnItemClickListener(new OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)  
            {  
                if( imageItem.size() == 10) { //第一张为默认图片  
                    Toast.makeText(shareadd.this, "图片数9张已满", Toast.LENGTH_SHORT).show();  
                }  
                else if(position == 0) { //点击图片位置为+ 0对应0张图片  
                    Toast.makeText(shareadd.this, "添加图片", Toast.LENGTH_SHORT).show();  
                    //选择图片  
                    Intent intent = new Intent(Intent.ACTION_PICK,         
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);    
                    startActivityForResult(intent, IMAGE_OPEN);    
                    //通过onResume()刷新数据  
                }  
                else {  
                    dialog(position);  
                    //Toast.makeText(MainActivity.this, "点击第"+(position + 1)+" 号图片",   
                    //      Toast.LENGTH_SHORT).show();  
                }  
            }  
        });    
    }  
    
    
    public void back(View v) {
		finish();
	}

      
    //获取图片路径 响应startActivityForResult    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {    
        super.onActivityResult(requestCode, resultCode, data);          
        //打开图片    
        if(resultCode==RESULT_OK && requestCode==IMAGE_OPEN) {          
            Uri uri = data.getData();    
            if (!TextUtils.isEmpty(uri.getAuthority())) {    
                //查询选择图片    
                Cursor cursor = getContentResolver().query(    
                        uri,    
                        new String[] { MediaStore.Images.Media.DATA },    
                        null,     
                        null,     
                        null);    
                //返回 没找到选择图片    
                if (null == cursor) {    
                    return;    
                }    
                //光标移动至开头 获取图片路径    
                cursor.moveToFirst();    
                pathImage = cursor.getString(cursor    
                        .getColumnIndex(MediaStore.Images.Media.DATA)); 
                //Log.i("logb", "地址"+pathImage);
                //cursor.getString(cursor    
                 //  allpath[i]=     .getColumnIndex(MediaStore.Images.Media.DATA));
                allpath[i]=pathImage;
                i++;
            }  
        }  //end if 打开图片  
    }  
      
    //刷新图片  
    @Override  
    protected void onResume() {  
        super.onResume();  
        if(!TextUtils.isEmpty(pathImage)){  
            Bitmap addbmp=BitmapFactory.decodeFile(pathImage);  
            HashMap<String, Object> map = new HashMap<String, Object>();  
            map.put("itemImage", addbmp);  
            imageItem.add(map);  
            simpleAdapter = new SimpleAdapter(this,   
                    imageItem, R.layout.gridviewitem,   
                    new String[] { "itemImage"}, new int[] { R.id.imageView1});   
            simpleAdapter.setViewBinder(new ViewBinder() {    
                public boolean setViewValue(View view, Object data,    
                        String textRepresentation) {    
                    // TODO Auto-generated method stub    
                    if(view instanceof ImageView && data instanceof Bitmap){    
                        ImageView i = (ImageView)view;    
                        i.setImageBitmap((Bitmap) data);    
                        return true;    
                    }    
                    return false;    
                }  
            });   
            gridView1.setAdapter(simpleAdapter);  
            simpleAdapter.notifyDataSetChanged();  
            //刷新后释放防止手机休眠后自动添加  
            pathImage = null;  
        }  
    }  
      
    /* 
     * Dialog对话框提示用户删除操作 
     * position为删除图片位置 
     */  
    protected void dialog(final int position) {  
        AlertDialog.Builder builder = new Builder(shareadd.this);  
        builder.setMessage("确认移除已添加图片吗？");  
        builder.setTitle("提示");  
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                imageItem.remove(position);    
                simpleAdapter.notifyDataSetChanged();  
            }  
        });  
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                }  
            });  
        builder.create().show();  
    }  
  
    
public void uploadHttpClient(String imgPath,String imgName){
		
		String httpUrl=Iputil.getFwqIp()+"/SuishouDemo/PhotoUpload";
		HttpClient httpClient = new DefaultHttpClient(); //创建HttpClient对象
		HttpPost post = new HttpPost(httpUrl);
		MultipartEntity entity = new MultipartEntity();
		
		//获取SD卡根目录
	//	File parent =Environment.getExtemalStorageDirectory();
//		File fileAbs = new File(parent,"sky.jpg");
		
		File fileAbs = new File(imgPath);
		FileBody fileBody = new FileBody(fileAbs);
		
		entity.addPart(imgName,fileBody); 
		post.setEntity(entity);
		
		try {
			HttpResponse response = httpClient.execute(post);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

/**
 *  安卓端获取所有文本框中的内容，将这些内容打包，传至服务器端，存入数据库中
 */
public void postShare(int name,String content,String images){
	
	String url = Iputil.getFwqIp()+"/SuishouDemo/PostShareServlet";
	try {
		URL httpUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		
		//将信息传送中服务器端
		OutputStream out = conn.getOutputStream();
		String infor = "name="+name+"&content="+content+"&images="+images;
		out.write(infor.getBytes());
		 
		 
		 Intent intent=new Intent(shareadd.this,MainActivity.class);
		 intent.putExtra("goto", 1);
		startActivity(intent);

		//获取服务器传来的值
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer  sb = new StringBuffer();
		String str;
		
		while((str=reader.readLine())!=null){
			sb.append(str);
		}
		System.out.println(sb.toString());
		 
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

	@Override
	public void onClick(View v) {
		
		new Thread(){
			public void run(){
		String con=ed.getText().toString();
		String[] photopath=new String[100];
		String pp = "";
		Log.i("logb", String.valueOf(i));
		for(int a=0;a<=i-1;a++){
			
			Log.i("logb", allpath[a]); 
			
			String[] photo=null;
			photo = allpath[a].split("/");
			String path=photo[photo.length-1];
			uploadHttpClient(allpath[a],path);
			photopath[a]=path;
			pp+=photopath[a]+'/';
		}
		int id=((ImApp)getApplication()).getId();
	
		Log.i("logb", pp);
		postShare(id,con,pp);}
		}.start();

	}
} 