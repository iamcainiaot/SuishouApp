package com.example.shouye;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.easyexpress1.R;

/**
 * 此demo用来展示如何进行地理编码搜索（用地址检索坐标）、反地理编码搜索（用坐标检索地址）
 */
public class GeoCoderDemo extends Activity implements
		OnGetGeoCoderResultListener, OnClickListener {

	GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	BaiduMap mBaiduMap = null;
	MapView mMapView = null;

	private ImageButton geo_back;
	private Button payButton;

	String lon = "";// 经度
	String lat = "";// 纬度

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SDKInitializer.initialize(getApplicationContext());

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_geocoder);

		// 获取快递员经纬度
		Intent intent = getIntent();
		lon = (String) intent.getSerializableExtra("lon");// 经度
		lat = (String) intent.getSerializableExtra("lat");// 纬度

		CharSequence titleLable = "地理编码功能";
		setTitle(titleLable);

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		geo_back = (ImageButton) findViewById(R.id.geo_back);
		geo_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		payButton = (Button) findViewById(R.id.pay);
		payButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(GeoCoderDemo.this, Pay.class);
				startActivity(intent);
			}
		});

		// Toast.makeText(GeoCoderDemo.this, "1111111lat="+lat+"lon="+lon,
		// Toast.LENGTH_SHORT).show();
		// 设定中心点坐标

		LatLng cenpt = new LatLng((Float.valueOf(lat)),
				(Float.valueOf(lon)));
		// 定义地图状态
		MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(15)
				.build();
		// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// 改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);

		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);

		LatLng ptCenter = new LatLng((Float.valueOf(lat)),
				(Float.valueOf(lon)));
		// 反Geo搜索
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));

		// 构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_marka);
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(ptCenter).icon(
				bitmap);
		// 在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);

	}

	/**
	 * 发起搜索
	 * 
	 * @param v
	 */
	// public void searchButtonProcess(View v) {
	// if (v.getId() == R.id.reversegeocode) {
	// // EditText lat = (EditText) findViewById(R.id.lat);
	// // EditText lon = (EditText) findViewById(R.id.lon);
	//
	// String lat="31.52";
	// String lon="117.17";
	// LatLng ptCenter = new LatLng((Float.valueOf(lat)), (Float.valueOf(lon)));
	// // 反Geo搜索
	// mSearch.reverseGeoCode(new ReverseGeoCodeOption()
	// .location(ptCenter));
	// }
	// else if (v.getId() == R.id.geocode) {
	// EditText editCity = (EditText) findViewById(R.id.city);
	// EditText editGeoCodeKey = (EditText) findViewById(R.id.geocodekey);
	// // Geo搜索
	// mSearch.geocode(new GeoCodeOption().city(
	// editCity.getText().toString()).address(editGeoCodeKey.getText().toString()));
	// }
	// }

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.onDestroy();
		mSearch.destroy();
		super.onDestroy();
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			/*
			 * Toast.makeText(GeoCoderDemo.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
			 * .show();
			 */
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		String strInfo = String.format("纬度：%f 经度：%f",
				result.getLocation().latitude, result.getLocation().longitude);
		Toast.makeText(GeoCoderDemo.this, strInfo, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			/*
			 * Toast.makeText(GeoCoderDemo.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
			 * .show();
			 */
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		Toast.makeText(GeoCoderDemo.this, result.getAddress(),
				Toast.LENGTH_LONG).show();

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}