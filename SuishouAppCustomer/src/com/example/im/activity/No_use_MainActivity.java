//package com.example.im.activity;
//package com.example.im.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.example.easyexpress1.R;
//import com.example.utils.ContinuousClickUtil;
//
//public class MainActivity extends Activity {
//
//	private int login_status = 0;
//	private Button friend = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		Intent intent = getIntent();
//		login_status = intent.getIntExtra("login_status", 404);
//		// Log.d("data", "ddddddddddddddddd");
//		// String accountStr = intent.getStringExtra("account");
//		// Log.d("data", "ddddddddddddddddd");
//
//		// Log.d("login_status", String.valueOf(login_status));
//		// Log.d("data", "ddddddddddddddddd");
//		// Log.d("data", accountStr);
//		// final Boolean flag = intent.get;//intent.getBooleanExtra("flag",
//		// true);
//
//		// Log.d("data", accountStr);
//		Init();
//		friend.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (ContinuousClickUtil.isContinuousClick()) {
//					return;
//				}
//				switch (v.getId()) {
//				case R.id.friend:
//					// ��ť�����
//					// Toast.makeText(this, "��ť����ˣ�����",
//					// Toast.LENGTH_SHORT).show();
//					if (login_status == 1) {
//						Log.i("friend", "isPressed");
//						Intent intent1 = new Intent(MainActivity.this,
//								FriendActivity.class);
//
//						startActivity(intent1);
//						break;
//					} else if (login_status != 1) {
//						Toast.makeText(MainActivity.this, "����δ��¼�����ȵ�¼�ڷ��ʱ�ҳ�棡",
//								Toast.LENGTH_LONG).show();
//						Intent intent2 = new Intent(MainActivity.this,
//								tishidenglu.class);
//						startActivity(intent2);
//						// Intent intent2 = new
//						// Intent(MainActivity.this,LoginActivity.class);
//						// startActivity(intent2);
//						// break;
//					}
//
//					break;
//
//				default:
//					break;
//				}
//			}
//		});
//
//	}
//
//	private void Init() {
//		// TODO Auto-generated method stub
//		friend = (Button) findViewById(R.id.friend);
//
//	}
//
//}
