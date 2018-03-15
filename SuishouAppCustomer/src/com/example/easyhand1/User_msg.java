package com.example.easyhand1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 *
 */
public class User_msg implements Parcelable {
	
	private String username;
	private int phonenum;	
	private String address;
	private String image;
	private int id;
	private String password;

	
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getPhonenum() {
		return phonenum;
	}


	public void setPhonenum(int phonenum) {
		this.phonenum = phonenum;
	}

	public int getID() {
		return id;
	}


	public void setID(int id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		address = address;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}
	

//	public User_msg (Parcel in){
//		username=in.readString();
//		phonenum=in.readInt();
//		address=in.readString();
//		image=in.readString();
//		
//	}
	

	@Override
	public int describeContents() {
		// TODO �Զ����ɵķ������
		return 0;
	}
	
	
//	private String username;
//	private int phonenum;	
//	private String Address;
//	private String image;

	public void writeToParcel(Parcel dest, int flags) {
		// TODO �Զ����ɵķ������
        dest.writeString(username);
        dest.writeInt(phonenum);
        dest.writeString(address);
        dest.writeString(image);
	}
	
	
//	public static final Parcelable.Creator<User_msg> CREATOR = new Creator<User_msg>() {
//	       
//        @Override
//        public User_msg[] newArray(int size) {
//            // TODO Auto-generated method stub
//            return new User_msg[size];
//        }
//       
//        @Override
//        public User_msg createFromParcel(Parcel source) {
//            // TODO Auto-generated method stub
//            return new User_msg(source);
//        }
//    };

	
}
