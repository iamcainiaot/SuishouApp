package com.example.im.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.easyexpress1.R;
import com.example.im.adapter.FriendListAdapter;
import com.example.im.core.QQConnection.OnMessageListener;
import com.example.im.domain.ContactInfo;
import com.example.im.domain.ContactInfoList;
import com.example.im.domain.QQMessage;
import com.example.im.domain.QQMessageType;
import com.example.im.util.ThreadUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FriendActivity extends Activity {
	@ViewInject(R.id.listview)
	ListView listView;
	// listView�ļ���
	private List<ContactInfo> infos = new ArrayList<ContactInfo>();
	private ImApp app;
	private FriendListAdapter adapter;

	private OnMessageListener listener = new OnMessageListener() {
		public void onReveive(final QQMessage msg) {

			ThreadUtils.runInUiThread(new Runnable() {
				public void run() {
					if (QQMessageType.MSG_TYPE_BUDDY_LIST.equals(msg.type)) {
						String newBuddyListJson = msg.content;
						Gson gson = new Gson();
						ContactInfoList newList = gson.fromJson(
								newBuddyListJson, ContactInfoList.class);
						infos.clear();
						infos.addAll(newList.buddyList);
						if (adapter != null) {
							adapter.notifyDataSetChanged();
						}
					}

				}
			});
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("data", "ddddddddddddddddd");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.im_activity_friend);
		Log.d("data", "ddddddddddddddddd");
		ViewUtils.inject(this);

		// for (int i = 0; i < 20; i++) {
		// ContactInfo info = new ContactInfo();
		// info.account = new Long(100 + i);
		// info.avatar = 0;
		// info.nick = "hjahahah" + i;
		// infos.add(info);
		// }
		app = (ImApp) getApplication();
		app.getMyConn().addOnMessageListener(listener);

		String buddyListJson = app.getBuddyListJson();
		System.out.println(buddyListJson);
		Gson gson = new Gson();
		ContactInfoList list = gson.fromJson(buddyListJson,
				ContactInfoList.class);
		infos.addAll(list.buddyList);
		adapter = new FriendListAdapter(getBaseContext(), infos);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ContactInfo info = infos.get(position);
				if (info.account != app.getMyAccount()) {
					Intent intent = new Intent(getBaseContext(),
							ChatActivity.class);
					intent.putExtra("account", info.account);
					intent.putExtra("nick", info.nick);
					startActivity(intent);

				}

			}
		});

		Intent intent = getIntent();
		intent.getIntExtra("login_status", 404);

	}

	  public void back(View v) {
			finish();
		}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		app.getMyConn().removeOnMessageListener(listener);

	}
}
