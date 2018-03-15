package com.example.zhuye;



import org.json.JSONException;
import org.json.JSONObject;



import com.example.expresssender.R;
import com.example.get_jingweidu.HttpUtils;
import com.example.utils.Iputil;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;

public class Dingwei extends Activity implements SensorEventListener{

	public static String order_num;
	static final int QUERY_LOGIN=1002;
	// 定义经纬度

	
	private Handler mHandler =new Handler(){
		
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

						String result = jb1.getString("result");
						// String order_num = jb1.getString("order_num");

						Log.i("logB", "result=" + result);

						if (result.equals("success")) {
							text = text.replace("\"", "");
							// SessionUtil.yonghu = order_num;
							Toast.makeText(Dingwei.this, "111111111订单号="+order_num, Toast.LENGTH_SHORT).show();
						} else if (result.equals("error")) {
							Toast.makeText(Dingwei.this, "����ʧ��",
									Toast.LENGTH_SHORT).show();
						} else if (result.equals("null")) {
							Toast.makeText(Dingwei.this, "获取失败",Toast.LENGTH_SHORT).show();
						}
						Log.i("logB", "get content=" + text);
					} else if (statusCode == 500) {
						Toast.makeText(Dingwei.this, "服务器异常，请稍后重试",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(Dingwei.this, "网络错误，请重试",
								Toast.LENGTH_SHORT).show();
					}
					break;
				} catch (JSONException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}

			}
		};
	};
	
	// 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;

    MapView mMapView;
    BaiduMap mBaiduMap;

    // UI相关
    OnCheckedChangeListener radioButtonListener;
    Button requestLocButton;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 使程序不崩溃的作用
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.dingwei);
		
        requestLocButton = (Button) findViewById(R.id.button1);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = LocationMode.NORMAL;
        requestLocButton.setText("普通");
        OnClickListener btnClickListener = new OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        requestLocButton.setText("跟随");
                        mCurrentMode = LocationMode.FOLLOWING;
                        mBaiduMap
                                .setMyLocationConfiguration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.overlook(0);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                        break;
                    case COMPASS:
                        requestLocButton.setText("普通");
                        mCurrentMode = LocationMode.NORMAL;
                        mBaiduMap
                                .setMyLocationConfiguration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        MapStatus.Builder builder1 = new MapStatus.Builder();
                        builder1.overlook(0);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
                        break;
                    case FOLLOWING:
                        requestLocButton.setText("罗盘");
                        mCurrentMode = LocationMode.COMPASS;
                        mBaiduMap
                                .setMyLocationConfiguration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);

        RadioGroup group = (RadioGroup) this.findViewById(R.id.radioGroup);
        radioButtonListener = new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.defaulticon) {
                    // 传入null则，恢复默认图标
                    mCurrentMarker = null;
                    mBaiduMap
                            .setMyLocationConfiguration(new MyLocationConfiguration(
                                    mCurrentMode, true, null));
                }
                if (checkedId == R.id.customicon) {
                    // 修改为自定义marker
                    mCurrentMarker = BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_launcher);
                    mBaiduMap
                            .setMyLocationConfiguration(new MyLocationConfiguration(
                                    mCurrentMode, true, mCurrentMarker,
                                    accuracyCircleFillColor, accuracyCircleStrokeColor));
                }
            }
        };
        group.setOnCheckedChangeListener(radioButtonListener);

        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(10*1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
	}

	public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            
            //获取快递员经纬度
            String lon=String.valueOf(mCurrentLon);
            String lat=String.valueOf(mCurrentLat);
            StringBuffer sb=new StringBuffer();
        	sb.append("order_number="+order_num);
        	sb.append("&lon="+lon);
        	sb.append("&lat="+lat);
        	
        	
        	String url = Iputil.getFwqIp() + "/SuishouDemo/Send";
    		HttpUtils.httpPost(Dingwei.this, url, sb.toString(), mHandler, QUERY_LOGIN); 
            
//           Toast.makeText(Dingwei.this, "经度："+lon, Toast.LENGTH_SHORT).show(); 
            
            
            
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}
