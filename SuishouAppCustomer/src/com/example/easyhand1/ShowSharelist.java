package com.example.easyhand1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.easyhand1.ShareInfor;

/**
 * 从服务器获取所有的分享信息（包括图片），并解析
 * @author XJ
 *
 */
public class ShowSharelist {
	
	/**
	 * 获得所有分享的文字信息（用户名、内容、时间、赞数）
	 * @throws JSONException 
	 */
	public  List<ShareInfor> getList() throws JSONException{
		
		String url = "http://192.168.1.103:8080/App/ShowShareListServlet";
		List<ShareInfor> list = new ArrayList<ShareInfor>();
		try {
			URL httpUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			if (conn.getResponseCode()==HttpURLConnection.HTTP_OK) {
				Log.d("status", "OKKKKKKKK");
			}
			else {
				Log.d("status", "NOOOOOOOO");
			}
			//获取服务器传来的值
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer  sb = new StringBuffer();
			String str;
			
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
			System.out.println(sb.toString());
			
			//解析json集合
			JSONArray jsonarray = new JSONArray(sb.toString());
			for(int i = 0;i<jsonarray.length();i++){
				JSONObject jsonobject = jsonarray.getJSONObject(i);
				if(jsonobject !=null){
					int shareID = jsonobject.optInt("shareID");
					String name = jsonobject.optString("name");
					String content = jsonobject.optString("content");
					String time = jsonobject.optString("time");
					String images = jsonobject.optString("image");
					int zan = jsonobject.optInt("zan");
					
					ShareInfor s = new ShareInfor();
					s.setShareID(shareID);
					s.setName(name);
					s.setTime(time);
					s.setImage(images);
					s.setZan(zan);
					s.setContent(content);
					list.add(s);
				}
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public void showBylist() throws JSONException{
		List<ShareInfor> list = new ArrayList<ShareInfor>();
		list = getList();
		//依次获取list中的值
		for (int i = 0; i < list.size(); i++) {
			ShareInfor s= (ShareInfor)list.get(i);
			System.out.println(s.getName());
		    System.out.println(s.getContent());
		    System.out.println(s.getTime());
		    System.out.println(s.getZan());
		    
		    //在此调用方法将值显示在手机界面  …………
		    
		    //解析图片信息，发送请求来获取图片
		    if(s.getImage() != null && !s.getImage().equals("")){
		    	String images = s.getImage();
		    	//图片名格式为：1.png/2.png/3.png/4.png/
		    	String[] st=null;
				st = images.split("/");
				for(int j=0 ; j<st.length; j++){
					//也可以定义一个方法来下载图片资源
					System.out.println(st[j]);
				    String sourceUrl="http://localhost:8080/App/photo/";
					sourceUrl += st[j] ;
					System.out.println(sourceUrl);
					downLoad(sourceUrl);
						
					
					//可以在此定义一个方法根据图片的地址来显示图片  …………
					
				}
			}
		 }
	}
	
	/**
	 * 该方法根据图片地址来下载图片
	 * @param sourceUrl
	 */
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
				
				File file = new File("d:/photoFile/"+fileName+"."+expandName);  //创建文件
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

	
//	public static void main(String[] args) {
//		ShowSharelist s = new ShowSharelist();
//		s.showBylist();
//	
//	}
}
