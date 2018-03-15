package com.example.im.adapter;

import java.util.List;

import com.example.easyexpress1.R;
import com.example.im.activity.ImApp;
import com.example.im.domain.QQMessage;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ������Ϣ��������
 * 
 * @author ZHY
 * 
 */
public class ChatMessageAdapter extends ArrayAdapter<QQMessage> {
	ImApp app;

	public ChatMessageAdapter(Context context, List<QQMessage> objects) {
		super(context, 0, objects);
		Activity activity = (Activity) context;
		app = (ImApp) activity.getApplication();

	}

	/**
	 * ���ݼ����е�positionλ�ã����ز�ͬ��ֵ������ͬ�Ĳ��� 0�����Լ����͵���Ϣ 1������յ�����Ϣ
	 * 
	 */
	@Override
	public int getItemViewType(int position) { 
		QQMessage msg = getItem(position);
		if (msg.from == app.getMyAccount()) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * ���ֲ���
	 */
	@Override
	public int getViewTypeCount() {

		return 2;
	}

	class ViewHolder {
		TextView time;
		TextView content;
		ImageView head;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		int type = getItemViewType(position);
		if (0 == type) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getContext(),
						R.layout.im_item_chat_send, null);
				holder = new ViewHolder();
				//显示现在的时间
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.content = (TextView) convertView
						.findViewById(R.id.content);
				//显示头像
				holder.head = (ImageView) convertView.findViewById(R.id.head);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// ����ֵ
			QQMessage msg = getItem(position);
			holder.time.setText(msg.sendTime);
			holder.head.setImageResource(msg.fromAvatar);
			holder.content.setText(msg.content);
			return convertView;

		} else {
			// ���յĲ���
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getContext(),
						R.layout.im_item_chat_receive, null);
				holder = new ViewHolder();
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.content = (TextView) convertView
						.findViewById(R.id.content);
				holder.head = (ImageView) convertView.findViewById(R.id.head);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// ����ֵ
			QQMessage msg = getItem(position);
			holder.head.setImageResource(msg.fromAvatar);
			holder.time.setText(msg.sendTime);
			holder.content.setText(msg.content);

			return convertView;
		}

	}
}
