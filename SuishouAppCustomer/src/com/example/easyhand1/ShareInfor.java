package com.example.easyhand1;


/**
 *  分享信息类
 * @author XJ
 *
 */
public class ShareInfor {
	
	int shareID;  //分享编号
	String name;  //分享人的姓名
	String time;   //分享时间
	String content;  //分享的内容
	String image;  //分享的图片，存图片名称
	int zan;  //赞的数量
	
	public ShareInfor(){
		
	}
	public ShareInfor(int shareID,String name,String time,String content,String image,int zan){
		this.shareID = shareID;
		this.name = name;
		this.time = time;
		this.content = content;
	}
	public int getShareID() {
		return shareID;
	}
	public void setShareID(int shareID) {
		this.shareID = shareID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getZan() {
		return zan;
	}
	public void setZan(int zan) {
		this.zan = zan;
	}

}
