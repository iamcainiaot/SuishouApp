package com.example.bean;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Dingdan  implements Serializable{
	
	public Dingdan(){
		
	}
	
	public Dingdan(Integer did,String order_num, String pro_name,int pro_num,String pro_type,String pro_address
			,String rec_address,String sender, String receiver,String code) {
		this.did=did;
		this.order_num=order_num;
		this.pro_name=pro_name;
		this.pro_num=pro_num;
		this.pro_type=pro_type;
		this.pro_address=pro_address;
		this.rec_address=rec_address;
		this.receiver=receiver;
		this.code=code;
		this.sender=sender;

	}
	// 订单ID
	private Integer did;
	// 订单号
	private String order_num;
	// 物品名称
	private String pro_name;
	// 物品购买数量
	private int pro_num;
	// 物品类型
	private String pro_type;
	// 物品所在地
	private String pro_address;
	// 收货地址
	private String rec_address;
	// 发件人
	private String sender;
	// 收件人
	private String receiver;
	// 二维码
	private String code;
	//价格
	private String price;
	//评价
	private String judgment;

	
	//构造方法
	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	
	public String getJudgment() {
		return judgment;
	}

	public void setJudgment(String judgment) {
		this.judgment = judgment;
	}
	public int getPro_num() {
		return pro_num;
	}

	public void setPro_num(int pro_num) {
		this.pro_num = pro_num;
	}

	public String getPro_type() {
		return pro_type;
	}

	public void setPro_type(String pro_type) {
		this.pro_type = pro_type;
	}

	public String getPro_address() {
		return pro_address;
	}

	public void setPro_address(String pro_address) {
		this.pro_address = pro_address;
	}

	public String getRec_address() {
		return rec_address;
	}

	public void setRec_address(String rec_address) {
		this.rec_address = rec_address;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	
	

//	public Dingdan(Parcel in) {
//		did = in.readInt();
//		order_num = in.readString();
//		pro_name = in.readString();
//		pro_num = in.readInt();
//		pro_type = in.readString();
//		pro_address = in.readString();
//		rec_address = in.readString();
//		sender = in.readString();
//		receiver = in.readString();
//		code = in.readString();
//
//	}
//
//
//	public void writeToParcel(Parcel dest, int flags){
//		dest.writeInt(did);
//		dest.writeString(order_num);
//		dest.writeString(pro_name);
//		dest.writeInt(pro_num);
//		dest.writeString(pro_type);
//		dest.writeString(pro_address);
//		dest.writeString(rec_address);
//		dest.writeString(sender);
//		dest.writeString(receiver);
//		dest.writeString(code);;
//	}
//	
	 /*public static final Parcelable.Creator<Dingdan> CREATOR = new Creator<Dingdan>() {
	       
	        @Override
	        public Dingdan[] newArray(int size) {
	            // TODO Auto-generated method stub
	            return new Dingdan[size];
	        }
	       
	        @Override
	        public Dingdan createFromParcel(Parcel source) {
	            // TODO Auto-generated method stub
	            return new Dingdan(source);
	        }
	    };*/
	
	
     //3、自定义类型中必须含有一个名称为CREATOR的静态成员，该成员对象要求实现Parcelable.Creator接口及其方法
//     public static final Parcelable.Creator<Dingdan> CREATOR = new Parcelable.Creator<Dingdan>() { 
//         @Override 
//         public Dingdan createFromParcel(Parcel source) { 
//             //从Parcel中读取数据
//             //此处read顺序依据write顺序
//             return new Dingdan(source.readInt(), source.readString(), source.readString(), 
//            		 source.readInt(), source.readString(), source.readString(), 
//            		 source.readString(), source.readString(), source.readString(),
//            		 source.readString()); 
//         } 
//         @Override 
//         public Dingdan[] newArray(int size) { 
//                          
//             return new Dingdan[size]; 
//         } 
//                      
//     };

//
//	@Override
//	public int describeContents() {
//		// TODO Auto-generated method stub
//		return 0;
//	} 

	
	
	
}
