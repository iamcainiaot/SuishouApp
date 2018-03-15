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
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.bean.Dingdan;
import com.example.bean.ShareInfor;
import com.example.easyexpress1.R;
import com.example.easyhand1.MainActivity;
import com.example.easyhand1.MyApplication;

import com.example.share.ImageLoader.ImageCallback;
import com.example.utils.ContinuousClickUtil;
import com.example.utils.Iputil;

public class share extends Fragment implements OnClickListener {

	static final int QUERY_LOGIN = 1002;
	private ListView list1;
	private String INTENT_TYPE = "image/*";
	private int REQUESTCODE = 100;
	private GridViewResAdapter gridadapter;// GridView适配器
	private DialogLoading dialogloading;// 正在加载提示
	private MyApplication app;
	private ImageView add;// 添加分享
	private ImageView refresh;// 刷新

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.share, container, false);
 		add = (ImageView) view.findViewById(R.id.addshare);
		list1 = (ListView) view.findViewById(R.id.listView1);
		refresh = (ImageView) view.findViewById(R.id.refresh);
 
		add.setOnClickListener(this);
		refresh.setOnClickListener(this);
		dialogloading = new DialogLoading(getActivity());
		dialogloading.show();
		getmessage(); 
		return view;
	}

	/*
	 * 用来接收数据线程
	 */
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// 处理从另一个线程传过来的信息

			String text;
			int statusCode;

			switch (msg.what) {

			case QUERY_LOGIN:
				statusCode = msg.getData().getInt("statusCode");
				Log.i("logB", "QUERY_REGISTER: statusCode=" + statusCode);

				if (statusCode == 200) {
					text = msg.getData().getString("content");

					if (text != null) {
						System.out.println("发布.java~~~~~~~~~~~~~~" + text);
						Log.i("logB", "get content=" + text);
						dialogloading.dismiss();
						try {
							showMessage(text);// 获取成功则将信息显示
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else if (text == null) {
						Toast.makeText(getActivity(), "获取失败",
								Toast.LENGTH_SHORT).show();
						Intent intent2 = new Intent(getActivity(),
								MainActivity.class);// 获取失败则返回主页面
						startActivity(intent2);
					}

				} else if (statusCode == 500) {
					Toast.makeText(getActivity(), "服务器异常，请稍后重试",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "网络错误，请 重试",
							Toast.LENGTH_SHORT).show();
				}
				break;

			}
		}
	};

	public void getmessage() {// 获取内容

		if (ContinuousClickUtil.isContinuousClick()) {
			return;
		}

		String url;
		url = Iputil.getFwqIp() + "/SuishouDemo/ShowShareListServlet";
		Log.i("logB", "url=" + url);
		HttpUtils.httpGet(getActivity(), url, mHandler, QUERY_LOGIN);
	}

	/*
	 * 将获取到的信息通过适配器显示到Listview中 Listview中嵌套GridView,用来显示图片
	 * ListView中的赞可实现点赞效果，点赞后会向后台提交数据；评论也可实现 GridView的图片点击能全屏显示大图
	 */

	public void showMessage(String text) throws JSONException {

		JSONArray jsonarray = new JSONArray(text);
		final List<ShareInfor> list = new ArrayList<ShareInfor>();

		for (int i = jsonarray.length() - 1; i >= 0; i--) {

			JSONObject jsonobject = jsonarray.getJSONObject(i);

			if (jsonobject != null) {
				String pro_image = jsonobject.optString("pro_image");
				int shareid = jsonobject.optInt("shareID");
				String name = jsonobject.optString("name");
				String time = jsonobject.optString("time");
				String content = jsonobject.optString("content");
				String image = jsonobject.optString("image");
				int zan = jsonobject.optInt("zan");
				int zanfouse = jsonobject.optInt("zanfouse");

				ShareInfor s = new ShareInfor();

				s.setPro_image(pro_image);
				s.setShareID(shareid);
				s.setName(name);
				s.setTime(time);
				s.setContent(content);
				s.setImage(image);
				s.setZan(zan);
				s.setzanfouse(zanfouse);
				list.add(s);
			}
		}

		// 生成动态数组，加入数据
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		int i = 0;
		for (final ShareInfor s : list) {

			new Thread() {
				public void run() {

					// 也可以定义一个方法来下载图片资源
					String sourceUrl = Iputil.getFwqIp() + "/App/photo/";
					sourceUrl += s.getPro_image();
					downLoad(sourceUrl);
				}
			}.start();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("Itemshareid", s.getShareID());
			map.put("ItemPer_image", s.getPro_image());// 图像资源的ID
			map.put("ItemPer_name", s.getName());
			map.put("Itemcontent", s.getContent());
			map.put("Itemphoto", s.getImage());
			map.put("Itemdate", s.getTime());
			map.put("Itemzan_num", s.getZan());
			map.put("Itemzanfouse", s.getzanfouse());
			map.put("Itemcomment", R.drawable.share);
			listItem.add(map);
			i++;

		}

		// 生成适配器的Item和动态数组对应的元素
		final MyAdapters listItemAdapter = new MyAdapters(getActivity(),
				listItem, R.layout.shareitem,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "Itemshareid", "ItemPer_image", "ItemPer_name",
						"Itemcontent", "Itemphoto", "Itemdate", "Itemzan_num",
						"Itemzanfouse" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.ItemPer_image, R.id.ItemPer_name,
						R.id.Itemcontent, R.id.photoView, R.id.Itemdate,
						R.id.Itemzan_num });

		// 添加并且显示
		list1.setAdapter(listItemAdapter);

	}

	public void zan(int ShareID, int flag) {// 赞后修改后台数据赞的数目

		if (ContinuousClickUtil.isContinuousClick()) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		String url;
		sb.append("ShareID=" + ShareID);
		sb.append("&&Flag=" + flag);
		Log.i("logB", "params=" + sb.toString());
		url = Iputil.getFwqIp() + "/SuishouDemo/AddZanByShareID" + "?"
				+ sb.toString();
		Log.i("logB", "url=" + url);
		HttpUtils.httpPost(getActivity(), url, sb.toString(), mHandler,
				QUERY_LOGIN);

	}

	public void downLoad(String sourceUrl) {

		boolean flag;
		try {
			URL url = new URL(sourceUrl); // 创建下载地址对应的URL对象
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection(); // 创建一个连接

			// urlConn.setRequestMethod("post"); //指定请求方式为post
			InputStream is = urlConn.getInputStream(); // 获取输入流对象
			if (is != null) {
				String expandName = sourceUrl.substring(
						sourceUrl.lastIndexOf(".") + 1, sourceUrl.length())
						.toLowerCase(); // 获取文件的扩展名

				String fileName = sourceUrl.substring(
						sourceUrl.lastIndexOf("/") + 1,
						sourceUrl.lastIndexOf(".")); // 获取文件名

				File file = new File("sdcard/Download/" + fileName + "."
						+ expandName); // 创建文件
				// File file = new File("/sdcard/pictures/"+fileNam
				// e+"."+expandName); //在SD卡上创建文件
				FileOutputStream fos = new FileOutputStream(file); // 创建一个文件输出流对象

				byte buf[] = new byte[3000]; // 创建一个字节数组
				// 读取文件到输出流对象中
				while (true) {
					int numread = is.read(buf);
					if (numread <= 0) {
						break;
					} else {
						fos.write(buf, 0, numread);
					}
				}
			}
			is.close(); // 关闭输入流对象
			urlConn.disconnect(); // 关闭连接
			flag = true;
		} catch (MalformedURLException e) {
			e.printStackTrace(); // 输出异常信息
			flag = false;
		} catch (IOException e) {
			e.printStackTrace(); // 输出异常信息
			flag = false;
		}
		System.out.println(flag);
	}

	public class MyAdapters extends BaseAdapter {// 自定义适配器

		private class ButtonViewHolder {
			ImageView image;
			TextView name;
			TextView content;
			TextView date;
			TextView zan_num;
			ImageView zan;
			ImageView comment;
			NoScrollGridView photo;

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

		public MyAdapters(Context context,
				ArrayList<HashMap<String, Object>> list, int resource,
				String[] from, int[] to) {
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
				convertView = mInflater.inflate(R.layout.shareitem, null);
				holder = new ButtonViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.ItemPer_image);
				holder.name = (TextView) convertView
						.findViewById(R.id.ItemPer_name);
				holder.content = (TextView) convertView
						.findViewById(R.id.Itemcontent);
				holder.date = (TextView) convertView
						.findViewById(R.id.Itemdate);
				holder.zan_num = (TextView) convertView
						.findViewById(R.id.Itemzan_num);
				holder.zan = (ImageView) convertView.findViewById(R.id.Itemzan);
				holder.comment = (ImageView) convertView
						.findViewById(R.id.Itemcomment);
				holder.photo = (NoScrollGridView) convertView
						.findViewById(R.id.Itemphoto);
				convertView.setTag(holder);

			}

			HashMap<String, Object> appInfo = list.get(position);

			if (appInfo != null) {

				String pro_image = (String) appInfo.get(keyString[1]);
				String aname = (String) appInfo.get(keyString[2]);
				String acontent = (String) appInfo.get(keyString[3]);
				final String aimage = (String) appInfo.get(keyString[4]);
				String adate = (String) appInfo.get(keyString[5]);
				int azan_num = (Integer) appInfo.get(keyString[6]);
				int zanfouse = (Integer) appInfo.get(keyString[7]);

				String file = "sdcard/Download/" + pro_image;
				Bitmap bm = BitmapFactory.decodeFile(file);
				ImageLoader imageLoader = new ImageLoader();
				Drawable cachedImage = imageLoader.loadDrawable(file,
						new ImageCallback() {
							public void imageLoaded(Drawable imageDrawable,
									String imageUrl) {
								// ImageView imageViewByTag = (ImageView)
								// gridView.findViewWithTag(imageUrl);
								if (holder.image != null) {
									holder.image
											.setImageDrawable(imageDrawable);
								}
								if (holder.image != null) {
									holder.image
											.setBackgroundResource(R.drawable.imageloading);
								}
							}
						});
				holder.image.setImageBitmap(bm);
				holder.name.setText(aname);// 设置对应数据
				holder.content.setText(acontent);
				holder.date.setText(adate);
				holder.zan_num.setText(String.valueOf(azan_num));
				holder.comment.setTag(position);
				holder.zan.setTag(position);

				new Thread() {
					public void run() {
						String[] st = null;
						st = aimage.split("/");// 图片名数组
						for (int j = 0; j < st.length; j++) {
							// 也可以定义一个方法来下载图片资源
							String sourceUrl = Iputil.getFwqIp()
									+ "/SuishouDemo/photo/";
							sourceUrl += st[j];
							downLoad(sourceUrl);
						}

					}
				}.start();
				if (zanfouse == 0) {
					holder.zan.setImageResource(R.drawable.good);
				} else {
					holder.zan.setImageResource(R.drawable.zaned);
				}

				String[] st = null;
				st = aimage.split("/");// 图片名数组
				gridadapter = new GridViewResAdapter(getActivity(), st);
				holder.photo.setAdapter(gridadapter);

				holder.photo.setOnItemClickListener(new OnItemClickListener() { // 点击后全屏显示大图

							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								String[] st = null;
								st = aimage.split("/");

								Intent intent = new Intent(getActivity(),
										bigphoto.class);
								intent.putExtra("bigp", st[arg2]);// 点击图片后将该图片名传到bigphoto并跳转
								startActivity(intent);

							}
						});

				// 点赞按钮事件
				holder.zan.setOnClickListener(new lvButtonListener(position) {
					private int position;

					void lvButtonListener(int pos) {
						position = pos;
					}

					@Override
					public void onClick(View v) {
						int index = (Integer) v.getTag(); // 获取该点击项的位置
						HashMap<String, Object> a = list.get(index);
						int num = (Integer) a.get(keyString[6]);
						int ShareID = (Integer) a.get(keyString[0]);
						int zanfouse = (Integer) a.get(keyString[7]);

						if (zanfouse == 0) {
							num++;
							// holder.zan.setImageResource(R.drawable.zaned);
							zan(ShareID, 1);
							a.put("Itemzanfouse", 1);
							a.put("Itemzan_num", num);
							Toast.makeText(getActivity(), "点赞成功",
									Toast.LENGTH_SHORT).show();
							// getmessage();
						} else {
							num--;
							zan(ShareID, 0);
							a.put("Itemzanfouse", 0);
							a.put("Itemzan_num", num);
							Log.i("logb", "赞!!!!" + zanfouse);
							Toast.makeText(getActivity(), "取消点赞成功",
									Toast.LENGTH_SHORT).show();
							// getmessage();
						}
						notifyDataSetChanged();
						// getmessage();
					}

				});

				// 评论按钮事件 ,跳转到评论界面
				holder.comment
						.setOnClickListener(new lvButtonListener(position) {// 赞的点击事件
							private int position;

							void lvButtonListener(int pos) {
								position = pos;
							}

							@Override
							public void onClick(View v) {
								int index = (Integer) v.getTag();
								HashMap<String, Object> a = list.get(index);
								int id = (Integer) a.get(keyString[0]);

								Intent intent3 = new Intent(getActivity(),
										comment.class);// 获取失败则返回主页面
								intent3.putExtra("shareid", id);// 将该项分享的id传给comment
								startActivity(intent3);
							}

						});
			}
			return convertView;

		}
	}

	@Override
	public void onClick(View v) {
	 	switch (v.getId()) {
		case R.id.addshare: // 若点击加号跳转至添加分享页面
			Intent intent = new Intent(getActivity(), shareadd.class);
			startActivity(intent);
			break;
		case R.id.refresh:// 点击刷新则重新加载数据
			getmessage();
			break;
		} 
	}

}
