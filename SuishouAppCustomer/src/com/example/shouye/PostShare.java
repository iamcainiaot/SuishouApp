package com.example.shouye;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *  发布评论信息
 * @author XJ
 *
 */
public class PostShare {
	
	// 在安卓端若要上传图片，先将图片名获取，当有多张图片时，以“/”分开，然后将图片名传输
/* 		String p1;  //图片1的名称
 *      String p2;  //图片2的名称
 *      //或者定义一个数组将图片的名称放入该数组中
 *      //将所有图片名称进行连接
 *       String[] img={"1.png","2.png","3.png","4.png"};
	    	  String image = "";
	    	  for(int i=0 ;i<img.length;i++){
	    		  image += img[i]+"/"; 
		       }
	    	  System.out.println(image);
	    	  //输出：1.png/2.png/3.png/4.png/
 */
	/**
	 *  安卓端获取所有文本框中的内容，将这些内容打包，传至服务器端，存入数据库中
	 */
	public void postShare(String name,String content,String images){
		
		String url = "http://localhost:8080/App/PostShareServlet";
		try {
			URL httpUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			//将信息传送中服务器端
			OutputStream out = conn.getOutputStream();
			String infor = "name="+name+"&content="+content+"&images="+images;
			out.write(infor.getBytes());
			
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
	
	/**
	 *   要根据图片的地址及图片名进行上传（图片名可以自己定，若果自己定需要加后缀名）
	 */
	public void uploadHttpClient(String imgPath,String imgName){
		
		String httpUrl="http://localhost:8080/App/PhotoUpload";
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
	
	public static void main(String[] args) {
		PostShare p = new PostShare();
		p.postShare("李敏", "今天要下雨","sdfasdf");
		p.uploadHttpClient("C:/Users/XJ/Pictures/cuo.PNG","cuo.PNG");
	}

}
