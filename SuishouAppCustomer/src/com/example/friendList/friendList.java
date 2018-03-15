package com.example.friendList;

import android.R.integer;

public class friendList {
	//朋友名字
	private String friend_name;
	
	//朋友头像
	private int img_id;
	
	//构造方法
	public friendList(String friend_name, int img_id) {
		super();
		this.friend_name = friend_name;
		this.img_id = img_id;
	}

	//获取变量
	String getFriend_name() {
		return friend_name;
	}

	void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}

	int getImg_id() {
		return img_id;
	}

	void setImg_id(int img_id) {
		this.img_id = img_id;
	}
	

	
}
