package com.example.bean;

/**
 *评论信息
 * @author XJ
 *
 */
public class CommentInfor {
	
	String pro_image;
	int shareID;    //分享id
	int commentID;  //评论id
	String content;  //评论内容
	String name;  //评论人姓名
	String time;  //评论时间
	
	public CommentInfor(){
		
	}
	public CommentInfor(int shareID,int commentID,String content,String name,String time){
		this.shareID = shareID;
		this.commentID = commentID;
		this.content = content;
		this.name = name;
		this.time = time;
		
	}
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public int getShareID() {
		return shareID;
	}
	public void setShareID(int shareID) {
		this.shareID = shareID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPro_image() {
		return pro_image;
	}
	public void setPro_image(String pro_image) {
		this.pro_image = pro_image;
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

}
