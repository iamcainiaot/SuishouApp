package com.example.im.activity;

import com.example.im.core.QQConnection;
import android.app.Application;

public class ImApp extends Application {
	private QQConnection myConn;
	private long myAccount;
	private String buddyListJson;
	private String Name;
	private String password;
	private int flag;
	private int id;
	

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public QQConnection getMyConn() {
		return myConn;
	}

	public void setMyConn(QQConnection myConn) {
		this.myConn = myConn;
	}

	public long getMyAccount() {
		return myAccount;
	}

	public void setMyAccount(long myAccount) {
		this.myAccount = myAccount;
	}

	public String getBuddyListJson() {
		return buddyListJson;
	}

	public void setBuddyListJson(String buddyListJson) {
		this.buddyListJson = buddyListJson;
	}

	// // �����û���������
	// public User getLoginUser() {
	// return loginUser;
	// }
	//
	// public void userLogin(User user) {
	// loginUser.setUserId(user.getUserId());
	// loginUser.setUserName(user.getUserName());
	// }
	//
	// public void userLogout() {
	// loginUser = new User();
	// }
}
