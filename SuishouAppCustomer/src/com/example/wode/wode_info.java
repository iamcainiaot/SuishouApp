package com.example.wode;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.easyexpress1.R;
import com.example.shouye.Wode;
import com.example.utils.FileUtil;
import com.example.utils.Iputil;
import com.example.utils.PictureTransform;
import com.example.utils.toRoundBitmap;

public class wode_info extends Activity implements
		android.view.View.OnClickListener,
		android.content.DialogInterface.OnClickListener {

	private ImageButton wode_info_back;
	private Button alter_img;
	private Button alter_name;
	private Button alter_pwd;
	// private String altered_name;
	// 显示设置后的头像（测试用）
	// private ImageView show_img;

	// 保存设为头像的图片
	public static Drawable drawable;

	private Bitmap photo;

	// 选择的图片位置和图片名称
	public static String pic_Path;
	public static String pic_Name;

	private Context context;

	private String[] items = new String[] { "相册", "相机" };

	// 头像名称
	private static final String img_Name = "img.jpg";

	// 是否修改头像
	private boolean imgIsAltered = false;

	// 将drawable转换成bitmap
	private Bitmap bitmap;

	// 请求码
	private static final int ALBUM_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;

	private static final int REQUESTCODE = 0x11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wode_info);
		Init();
		// Intent intent = getIntent();
		// 将修改后的昵称保存在altered_name中 altered_name =
		// data.getStringExtra("altered_name");
		// altered_name = intent.getStringExtra("altered_name");

	}

	public void Init() {
		context = wode_info.this;
		wode_info_back = (ImageButton) findViewById(R.id.wode_info_back);
		wode_info_back.setOnClickListener(this);
		alter_img = (Button) findViewById(R.id.alter_img);
		alter_img.setOnClickListener(this);
		alter_name = (Button) findViewById(R.id.alter_name);
		alter_name.setOnClickListener(this);
		alter_pwd = (Button) findViewById(R.id.alter_pwd);
		alter_pwd.setOnClickListener(this);
		// 获取显示头像的实例
		// show_img = (ImageView) findViewById(R.id.show_img);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.wode_info_back:

			// 若修改了头像则更新头像并上传
			if (imgIsAltered) {

				// 更新用户头像
				drawable = new BitmapDrawable(this.getResources(), photo);
				bitmap = toRoundBitmap.toRoundBitmap(PictureTransform
						.drawableToBitmap(wode_info.drawable));
				Wode.user_imag.setImageBitmap(bitmap);
				Toast.makeText(context, "头像修改成功", Toast.LENGTH_SHORT).show();
				// show_img.setImageDrawable(drawable);

				// 将修改后的头像上传到服务器
				new Thread() {
					@Override
					public void run() {
						// 上传图片
						uploadHttpClient(pic_Path, pic_Name);
					}
				}.start();

				// 删除文件
				deleteDatabase(pic_Path);

			}

			// 2017-7-20-10:01 for test
			/*
			 * Toast.makeText(wode_info.this, "从个人资料跳转到个人中心",
			 * Toast.LENGTH_SHORT) .show();
			 */
			Log.d("跳转跳转跳转", "从个人资料跳转到个人中心");
			// 2017-7-20-10:01 for test

			// 将修改后的信息保存在intent中，返回上一个activity并更新UI
			Intent intent1 = new Intent(wode_info.this, Wode.class);
			setResult(2, intent1);
			finish();

			break;
		case R.id.alter_img:
			// 此方法为弹出选择从相册获取或是从相机获取
			choose_Dialog();
			break;
		case R.id.alter_name:
			Intent intent3 = new Intent(wode_info.this, alter_name.class);
			// startActivityForResult(intent3, REQUEST_NAME_CODE);
			startActivity(intent3);
			break;
		case R.id.alter_pwd:
			Intent intent4 = new Intent(wode_info.this, alter_pwd.class);
			// startActivityForResult(intent4, REQUEST_PWD_CODE);
			startActivity(intent4);
			break;
		default:
			break;
		}

	}

	/*
	 * 显示选择对话框
	 */
	// 此方法为弹出选择从相册获取或是从相机获取
	private void choose_Dialog() {

		new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*");// 设置文件类型
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									ALBUM_REQUEST_CODE);
							break;
						case 1:
							Intent intentFromCamera = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 判断存储卡是否可用，可用则进行存储
							String status = Environment
									.getExternalStorageState();
							if (status.equals(Environment.MEDIA_MOUNTED)) {
								File path = Environment
										.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
								File file = new File(path, img_Name);

								intentFromCamera.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(file));
							}
							startActivityForResult(intentFromCamera,
									CAMERA_REQUEST_CODE);
							break;
						default:
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}

	// 这里需要注意resultCode，正常情况返回值为 -1 没有任何操作直接后退则返回 0
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (REQUESTCODE == requestCode) {
			if (resultCode == RESULT_OK) {
				// 将修改后的昵称保存在altered_name中 altered_name =
				// data.getStringExtra("altered_name");
			}
		}

		// 结果码不等于取消时
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case ALBUM_REQUEST_CODE:
				// 2017-7-19 get Path and Name
				/*
				 * pic_Path = getRealFilePath(context, data.getData()); pic_Name
				 * = getBitmapName(pic_Path);
				 */
				// Toast.makeText(context, pic_Path, Toast.LENGTH_SHORT);
				// 2017-7-19 get Path and Name
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				// 判断存储卡是否可用，可用则进行存储
				String status = Environment.getExternalStorageState();
				if (status.equals(Environment.MEDIA_MOUNTED)) {
					File path = Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
					File tempFile = new File(path, img_Name);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(context, "未找到存储卡！无法存储照片！",
							Toast.LENGTH_SHORT).show();
				}
				break;
			// 图片缩放完成后
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);

				}
				break;
			default:
				break;
			}

			// super.onActivityResult(requestCode, resultCode, data);
		}

	}

	// 获取图片的名称
	public String getBitmapName(String picPath) {
		String bitmapName = "";
		String[] s = picPath.split("/");
		bitmapName = s[s.length - 1];
		return bitmapName;
	}

	/**
	 * android根据Uri得到图片文件的真实路径
	 * 
	 * @param context
	 * @param uri
	 * @return the file path or null
	 */
	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 340);
		intent.putExtra("outputY", 340);
		intent.putExtra("return-data", true);// 是否要返回，如果设置false取到的值就是空值
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			imgIsAltered = true;
			photo = extras.getParcelable("data");
			photo = toRoundBitmap.toRoundBitmap(photo);
			// 2017-7-19 20:46
			// 图片路径
			pic_Path = FileUtil.saveFile(context, "user_Img.jpg", photo);
			pic_Name = getBitmapName(pic_Path);
			// 2017-7-19 20:46

			/*
			 * crop_Pic_path = FileUtil.saveFile(context, "temphead.jpg",
			 * photo); String CropImageName = getBitmapName(crop_Pic_path);
			 * Toast.makeText(context, CropImageName,
			 * Toast.LENGTH_SHORT).show();
			 */

		}
	}

	public void uploadHttpClient(String imgPath, String imgName) {

		String httpUrl = Iputil.getFwqIp() + "/App/PhotoUpload";
		HttpClient httpClient = new DefaultHttpClient(); // ����HttpClient����
		HttpPost post = new HttpPost(httpUrl);
		MultipartEntity entity = new MultipartEntity();

		// ��ȡSD����Ŀ¼
		// File parent =Environment.getExtemalStorageDirectory();
		// File fileAbs = new File(parent,"sky.jpg");

		File fileAbs = new File(imgPath);
		FileBody fileBody = new FileBody(fileAbs);

		entity.addPart(imgName, fileBody);
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

	public void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {
			Log.d("file", "文件不存在！");
		}
	}

}
