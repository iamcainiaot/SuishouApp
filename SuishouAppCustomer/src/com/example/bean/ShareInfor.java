package com.example.bean;

/**
 *  分享信息
 * @author XJ
 *
 */
public class ShareInfor {
	
	private int shareID;  //分享id
	private String name;  //用户名
	private String pro_image;//用户头像
	private String time;   //分享时间
	private String content;  //分享内容
	private String image;  //分享图片
	private int zan;  //赞数目
	private int zanfouse;
	
	public ShareInfor(){
		
	}
	public ShareInfor(int shareID,String name,String pro_image,String time,String content,String image,int zan,int zanfouse){
		this.shareID = shareID;
		this.name = name;
		this.time = time;
		this.content = content;
		this.zanfouse=zanfouse;
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
	public String getPro_image() {
		return pro_image;
	}
	public void setPro_image(String pro_image) {
		this.pro_image = pro_image;
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
	
	public int getzanfouse() {
		return zanfouse;
	}
	public void setzanfouse(int zanfouse) {
		this.zanfouse = zanfouse;
	}

}
