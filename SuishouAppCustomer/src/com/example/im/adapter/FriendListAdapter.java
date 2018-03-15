package com.example.im.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyexpress1.R;
import com.example.im.domain.ContactInfo;

public class FriendListAdapter extends ArrayAdapter<ContactInfo> {
	public FriendListAdapter(Context context, List<ContactInfo> objects) {
		super(context, 0, objects);
	}

	class ViewHolder {
		ImageView icon;
		TextView title;
		TextView desc;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContactInfo info = getItem(position); 
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(getContext(),
					R.layout.im_view_item_contact, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.desc = (TextView) convertView.findViewById(R.id.desc);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (info.avatar == 0) {
			holder.icon.setImageResource(R.drawable.perlogo);
		} else {
			holder.icon.setImageResource(info.avatar);
		}
 

		holder.title.setText(info.account + "");

		holder.desc.setText(info.nick);
		return convertView;
	}
}
