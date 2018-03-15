package com.example.shouye;

import http.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.Dingdan;
import com.example.easyexpress1.R;
import com.example.im.activity.ImApp;
import com.example.im.activity.Ts;
//import com.example.easyhand1.MainActivity1;
import com.example.shouye.fabu;
import com.example.shouye.qiangdan;
import com.example.utils.ContinuousClickUtil;
import com.example.utils.Iputil;

import android.content.Intent;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Shouye extends Fragment implements OnClickListener {
	private String lat = "";
	private String lon = "";
	private int login_status = 0;
	private Button fabuButton;
	private Button qiangdanButton;
	private ListView list1;
	private int flag;
	static final int ASKLOCATION = 1111;
	private TextView show_name;
	private ImageView shouye_refresh;
	private String order_number1 = "";
	static final int QUERY_LOGIN = 1002;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.shouye, container, false);
		fabuButton = (Button) view.findViewById(R.id.fabu);
		fabuButton.setOnClickListener(this);
		qiangdanButton = (Button) view.findViewById(R.id.qiangdan);
		qiangdanButton.setOnClickListener(this);
		list1 = (ListView) view.findViewById(R.id.Running);
		shouye_refresh=(ImageView) view.findViewById(R.id.shouye_refresh);
		shouye_refresh.setOnClickListener(this);
		flag = ((ImApp) getActivity().getApplication()).getFlag();
		String name= ((ImApp) getActivity().getApplication()).getName();
		
		
		if(flag==1){
		GetRunningMEssage();
		//show_name.setText(name);
		}
		// 缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，
		// 要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	// public void onActivityCreated(Bundle savedInstanceState) {
	// super.onActivityCreated(savedInstanceState);
	//
	//
	// Log.d("testfrag", "" + flag);
	//
	// }
	//

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// 处理从另一个线程传过来的信息

			String text;
			int statusCode;

			switch (msg.what) {

			case QUERY_LOGIN:
				statusCode = msg.getData().getInt("statusCode");
				Log.i("logB", "QUERY_REGISTER: statusCode=" + statusCode);
				JSONObject jb1;

				if (statusCode == 200) {
					text = msg.getData().getString("content");

					if (text != null) {
						// text = text.replace("\"", "");
						System.out.println("发布.java~~~~~~~~~~~~~~" + text);
						Log.i("logB", "get content=" + text);
						try {
							showMessage(text);// 获取成功则将信息显示
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else if (text == null) {
						Toast.makeText(getActivity(), "获取失败",
								Toast.LENGTH_SHORT).show();

						// Intent intent2 = new Intent(Shouye.this,
						// Shouye.class);//获取失败则返回主页面
						// startActivity(intent2);
					}

				} else if (statusCode == 500) {
					Toast.makeText(getActivity(), "服务器异常，请稍后重试",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "网络错误，请重试",
							Toast.LENGTH_SHORT).show();
				}
				break;

			}
		}
	};

	public void GetRunningMEssage() {

		if (ContinuousClickUtil.isContinuousClick()) {
			return;
		}

		String url;
		// Log.i("logb", "idididdididididididiiiiiiiiiiiiiiiiiiii是的"+((ImApp)
		// getActivity().getApplication()).getId());
		url = Iputil.getFwqIp() + "/SuishouDemo/RunningMessage?" + "user="
				+ ((ImApp) getActivity().getApplication()).getId();
		Log.i("logB", "url=" + url);
		HttpUtils.httpGet(getActivity(), url, mHandler, QUERY_LOGIN);

	}

	public void showMessage(String text) throws JSONException {
		// String text
		// ="[{pro_name:挑子,sender:'',rec_address:河北省,pro_num:12,pro_address:安徽省,receiver:'',oid:0,order_num:'123',code:'',type:水果},{pro_name:g,sender:'1',rec_address:gj,pro_num:1,pro_address:jhg,receiver:'',oid:0,order_num:'',code:'',type:gh},{pro_name:正宗猕猴桃,sender:'2',rec_address:安徽省六安市,pro_num:10,pro_address:安徽省合肥市,receiver:'',oid:0,order_num:'',code:'',type:水果},{pro_name:阿迪达斯上衣,sender:'',rec_address:安徽省六安市,pro_num:1,pro_address:安徽省合肥市,receiver:'',oid:0,order_num:'',code:'',type:衣服},{pro_name:1,sender:'',rec_address:1,pro_num:1,pro_address:1,receiver:'',oid:0,order_num:'',code:'',type:''},{pro_name:1,sender:'',rec_address:1,pro_num:1,pro_address:1,receiver:'',oid:0,order_num:'',code:'',type:''}]";
		// String text =
		// "[{'pro_name':'毛巾','pro_num':'1','pro_type':'生活用品','pro_address':'安徽','rec_address':'安徽'},{'pro_name':'饼干','pro_num':'1','pro_type':'食品','pro_address':'安徽','rec_address':'浙江'}]";

		JSONArray jsonarray = new JSONArray(text);

		final List<Dingdan> list = new ArrayList<Dingdan>();

		for (int i = 0; i < jsonarray.length(); i++) {

			JSONObject jsonobject = jsonarray.getJSONObject(i);

			if (jsonobject != null) {

				String order_num = jsonobject.optString("order_num");
				String pro_name = jsonobject.optString("pro_name");
				String pro_num = jsonobject.optString("pro_num");
				String rec_address = jsonobject.optString("rec_address");
				String pro_type = jsonobject.optString("type");
				String pro_address = jsonobject.optString("pro_address");
				String sender = jsonobject.optString("sender");

				Dingdan s = new Dingdan();

				s.setOrder_num(order_num);
				s.setPro_name(pro_name);
				s.setPro_num(Integer.parseInt(pro_num));
				s.setPro_address(pro_address);
				s.setRec_address(rec_address);
				s.setPro_type(pro_type);
				s.setSender(sender);

				list.add(s);
			}
		}

		// 生成动态数组，加入数据
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		int i = 0;
		for (Dingdan s : list) {

			HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("ItemImage", R.drawable.my);//图像资源的ID
			map.put("ItemPro_name", s.getPro_name());
			map.put("ItemPro_num", "*" + s.getPro_num());
			map.put("ItemPro_type", s.getPro_type());
			map.put("ItemRec_address", s.getRec_address());
			String sender = null;
			sender = s.getSender();
			// Log.i("logb","抢单人"+sender);
			String Item_stan = null;
			if (sender == null || sender.length() <= 0) {
				Item_stan = "待抢中";
			} else {
				Item_stan = "已被抢";
			}
			map.put("Item_stan", Item_stan);
			listItem.add(map);
			i++;
		}
		Log.i("logB", "到这");
		// 生成适配器的Item和动态数组对应的元素
		SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(),
				listItem,// 数据源

				R.layout.runningitem,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemPro_name", "ItemPro_num", "Item_stan" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.Pro_nameItem, R.id.Pro_numItem,
						R.id.Pro_stanItem });

		// 添加并且显示
		list1.setAdapter(listItemAdapter);

		list1.setOnItemClickListener(new OnItemClickListener() { // 点击后显示订单具体信息

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.i("logB", "点击第" + arg2 + "个项目");
				Dingdan a = list.get(arg2);

				// 获取后台传输的快递员的经纬坐标
				StringBuffer sb = new StringBuffer();
				sb.append("order_number=" + a.getOrder_num());

				Toast.makeText(getActivity(), "1111111  " + a.getOrder_num(),
						Toast.LENGTH_SHORT).show();

				String url = com.example.dingwei.IpUtil.getFWip()
						+ "/SuishouDemo/Get";
				HttpUtils.httpPost(getActivity(), url, sb.toString(), handler,
						QUERY_LOGIN);

				// Intent intent = new Intent();
				// //intent.setClass(getActivity(), RoutePlanDemo.class);
				// Bundle bundle = new Bundle();
				// bundle.putSerializable("order", a);
				// intent.putExtras(bundle);
				// startActivity(intent);

			}
		});

	}

	private final Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			String text;
			int statusCode;

			switch (msg.what) {
			case QUERY_LOGIN:

				statusCode = msg.getData().getInt("statusCode");
				Log.i("logB", "QUERY_REGISTER: statusCode=" + statusCode);
				try {
					JSONObject jb1;

					if (statusCode == 200) {

						text = msg.getData().getString("content");
						jb1 = new JSONObject(text);
						String result1 = jb1.getString("result");
						if (result1.equals("success")) {
							// Toast.makeText(getActivity(), "获取数据成功",
							// Toast.LENGTH_SHORT).show();
							lon = jb1.getString("lon");
							lat = jb1.getString("lat");

							// Toast.makeText(getActivity(),
							// "2222经度="+lon+"  纬度="+lat,
							// Toast.LENGTH_SHORT).show();
							// 点击后跳转到地图界面显示快递员位置
							Intent intent = new Intent();
							intent.setClass(getActivity(), GeoCoderDemo.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("lat", lat);
							bundle.putSerializable("lon", lon);
							intent.putExtras(bundle);
							startActivity(intent);

						} else if (result1.equals("error")) {
							Toast.makeText(getActivity(), "储存用户失败",
									Toast.LENGTH_SHORT).show();
						} else if (result1.equals("null")) {
							Toast.makeText(getActivity(), "用户名不存在",
									Toast.LENGTH_SHORT).show();
						}
						Log.i("logB", "get content=" + text);
					} else if (statusCode == 500) {
						Toast.makeText(getActivity(), "服务器异常，请稍后重试",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "网络错误，请重试",
								Toast.LENGTH_SHORT).show();
					}
					break;
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				break;
			}
		};
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fabu:

			if (flag == 1) {
				Intent intent1 = new Intent(getActivity(), fabu.class);
				startActivity(intent1);
				break;
			} else if (flag != 1) {
				/*
				 * Toast.makeText(Shouye.this, "您尚未登录，请先登录在访问本页面！",
				 * Toast.LENGTH_LONG).show();
				 */
				Intent intent3 = new Intent(getActivity(), Ts.class);
				startActivity(intent3);
				break;
				// Intent intent2 = new
				// Intent(MainActivity.this,LoginActivity.class);
				// startActivity(intent2);
				// break;
			}

			break;

		case R.id.qiangdan:

			if (flag == 1) {
				Intent intent4 = new Intent(getActivity(), qiangdan.class);
				startActivity(intent4);
				break;
			} else if (flag != 1) {
				/*
				 * Toast.makeText(Shouye.this, "您尚未登录，请先登录在访问本页面！",
				 * Toast.LENGTH_LONG).show();
				 */
				Intent intent5 = new Intent(getActivity(), Ts.class);
				startActivity(intent5);
				break;
				// Intent intent2 = new
				// Intent(MainActivity.this,LoginActivity.class);
				// startActivity(intent2);
				// break;
			}

			break;
		case R.id.shouye_refresh:
			if(flag==1){
				GetRunningMEssage();
			
				}
			break;
		default:
			break;
		}

	}
}
