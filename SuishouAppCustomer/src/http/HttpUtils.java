package http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class HttpUtils {

	public static int httpGet(final Context context,
 final String urlPath, final Handler mHandler, final int msgWhat) {
		new Thread() {//该线程用来获取数据并调用sendHandler传数据

			@Override
			public void run() {

				int statusCode = -1;
				String content = "";

				// ---------------------璇锋眰鏁版嵁-----------------------
				try {
					URL url = new URL(urlPath.trim());
					Log.i("logB", "send url=" + url.toString());
					Log.i("logB", "哈哈哈哈");
					//鎵撳紑杩炴帴
					HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

					statusCode = urlConnection.getResponseCode();

					Log.i("logB", "statusCode=" + statusCode);

					if (200 == urlConnection.getResponseCode()) {
						//寰楀埌杈撳叆娴�
						
						InputStream is = urlConnection.getInputStream();
						
				//ByteArrayOutputStream 鍙互鎹曡幏鍐呭瓨缂撳啿鍖虹殑鏁版嵁锛岃浆鎹㈡垚瀛楄妭鏁扮粍銆�
						ByteArrayOutputStream baos = new ByteArrayOutputStream();//字节数组流
						byte[] buffer = new byte[1024];
						int len = 0;
						while (-1 != (len = is.read(buffer))) {//从输入流中读取一定数量的字节，并将其存储在缓冲区数组 buffer 中
							baos.write(buffer, 0, len);//buffer中所有写入baos字节数组流中
							baos.flush();
						}
					
						content = baos.toString("utf-8");
						System.out.println("aaa"+content);
						System.out.println("鏀跺埌");
						sendHandler(context, mHandler, msgWhat, content, statusCode);
					} else {
						sendHandler(context, mHandler, msgWhat, content, statusCode);
					}
				} catch (MalformedURLException e) {
					sendHandler(context, mHandler, msgWhat, content, statusCode);
					e.printStackTrace();
				} catch (IOException e) {
					sendHandler(context, mHandler, msgWhat, content, statusCode);
					e.printStackTrace();
				}
			}
		}.start();
		return 0;
	}

	
 public static void httpPost(final Context context,final String urlPath,
 final String content, final Handler handler, final int msgWhat){

		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Looper.prepare();
				URL url;
				try {
					url = new URL(urlPath);
					Log.i("logB","content111="+content);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(10000);//5
					conn.setReadTimeout(10000);
					conn.setDoOutput(true);// 璁剧疆鍏佽杈撳嚭
					conn.setRequestMethod("POST");
					conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");// "Fiddler"
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					conn.setRequestProperty("Charset", "UTF-8");

					//OutputStream os = conn.getOutputStream();
					//os.write(content.getBytes());
					DataOutputStream os = new DataOutputStream(conn.getOutputStream());
					os.write(content.getBytes());
					os.flush();
					os.close();
					/* 鏈嶅姟鍣ㄨ繑鍥炵殑鍝嶅簲鐮� */
					int code = conn.getResponseCode();
					Log.i("logB","code="+code);
					if (code == 200) {
						BufferedReader in = new BufferedReader(
				 new InputStreamReader(conn.getInputStream(), "UTF-8"));
						String retData = null;
						String responseData = "";
						while ((retData = in.readLine()) != null) {
							responseData += retData;
						}
						Log.i("logB","responseData="+responseData);
						in.close();
						sendHandler(context,handler,msgWhat,responseData,code);
					} else {
						sendHandler(context,handler,msgWhat,"",code);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					Log.i("logB","MalformedURLException");
					sendHandler(context,handler,msgWhat,"",-1);
				} catch (IOException e) {
					e.printStackTrace();
					Log.i("logB","IOException");
					sendHandler(context,handler,msgWhat,"",-1);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					sendHandler(context,handler,msgWhat,"",-1);
				}
				Looper.loop();
			}

		}.start();
	}

	private static void sendHandler(Context context, Handler mHandler,//传数据
				int msgWhat, String content, int statusCode){
		if (context != null) {
			Message msg = mHandler.obtainMessage(msgWhat);
			System.out.println(msgWhat);
			Bundle bundle = new Bundle();
			
			//Bundle涓昏鐢ㄤ簬浼犻�掓暟鎹紱瀹冧繚瀛樼殑鏁版嵁锛屾槸浠ey-value(閿�煎)鐨勫舰寮忓瓨鍦ㄧ殑
			//杩欓噷灏辨槸浠ラ敭鍊煎鐨勫舰寮忓線Bundle涓紶閫佹暟鎹�
			bundle.putString("content", content); // 寰�Bundle涓瓨鏀炬暟鎹�
			bundle.putInt("statusCode", statusCode); // 寰�Bundle涓瓨鏀緎tatusCode
			
			
			//bundle涓殑鏁版嵁閫佺粰msg()
			msg.setData(bundle);
			//灏唌sg涓殑鍊煎彂閫佺粰mhandler 
			mHandler.sendMessage(msg);
			
		}
	}

}
