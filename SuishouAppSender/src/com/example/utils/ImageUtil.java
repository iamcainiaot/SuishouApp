package com.example.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import com.example.geren.news;
import com.example.geren.wode_info;

import android.R.integer;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class ImageUtil {

	public final static int from_Camera = 0001;// 选择 拍照 的返回码
	public final static int from_album = 0002;// 选择 相册 的返回码
	
	public final static int SELECT_PIC_KITKAT=0x44;
	public final static int SELECT_PIC=0x33;
	
	// 图片路径URL
	public Uri photoUri;
	private Uri temUri;
	// 图片文件
	public File photoFile;
	private Context context;

	// 构造方法
	public ImageUtil(Context context) {
		super();
		this.context = context;
	}

	// 选择从相机获取
	public void fromCamera() {
		try {
			// 创建文件夹
			File uploadFileDir = new File(
					Environment.getExternalStorageDirectory(), "/icon");
			if (!uploadFileDir.exists()) {
				uploadFileDir.mkdirs();
			}
			// 创建图片文件，以系统时间命名
			photoFile = new File(uploadFileDir,
					SystemClock.currentThreadTimeMillis() + ".png");
			if (!photoFile.exists()) {
				photoFile.createNewFile();
			}
			// 获取图片路径
			temUri = Uri.fromFile(photoFile);
			// 启动相机的Intent，传入图片路径作为存储路径
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, temUri);
			// 启动Intent
			Log.d("cameraIntent", "start");
			Toast.makeText(context, "cameraIntent start", Toast.LENGTH_LONG)
					.show();
			((wode_info) context).startActivityForResult(cameraIntent,
					from_Camera);
			Toast.makeText(context, "cameraIntent end", Toast.LENGTH_LONG)
					.show();
			Log.d("cameraIntent", "end");
			System.out.println("-->temUri:" + temUri.toString() + "-->path"
					+ temUri.getPath());

		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 选择从相册获取
	public void fromAlbum() {
		try {
			// 创建文件夹
			File uploadFileDir = new File(
					Environment.getExternalStorageDirectory(), "/icon");
			if (!uploadFileDir.exists()) {
				uploadFileDir.mkdirs();
			}
			// 创建图片文件，以系统时间命名
			photoFile = new File(uploadFileDir,
					SystemClock.currentThreadTimeMillis() + ".png");
			if (!photoFile.exists()) {
				photoFile.createNewFile();
			}
			// 获取图片路径
			temUri = Uri.fromFile(photoFile);
			// 获得剪辑图片的Intent
			final Intent intent = cutImageByAlbumIntent();
			((wode_info) context).startActivityForResult(intent, from_album);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fromAlbum_afterKitkat(){
		
		
		
	}
	
	// 调用图片剪辑程序的Intent
	private Intent cutImageByAlbumIntent() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		//Intent intent=new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT  
		//intent.addCategory(Intent.CATEGORY_OPENABLE);  
		//intent.setType("image/jpeg");  
		/*if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT){                  
		        startActivityForResult(intent, SELECT_PIC_KITKAT);    
		}else{                
		        startActivityForResult(intent, SELECT_PIC);   
		} */
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		// 设定宽高比
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 设定图片裁剪宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, temUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		return intent;
	}

	// 通过相机拍照后进行剪辑
	public void cutImageByCamera() {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(temUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, temUri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormay", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		((wode_info) context).startActivityForResult(intent, from_album);
	}

	// 对图片进行编码Bitmap
	public Bitmap decodeBitmap() {
		Bitmap bitmap = null;
		try {
			if (temUri != null) {
				photoUri = temUri;
				bitmap = BitmapFactory.decodeStream(context
						.getContentResolver().openInputStream(photoUri));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}
