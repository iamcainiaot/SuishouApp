package com.example.im.domain;

import com.example.im.util.MyTime;

/**
 * ����Э�鶨�弴ʱͨѶ��ʵ���࣬�����������ݽ���
 * 
 * @author ZHY
 * 
 */
public class QQMessage extends ProtocalObj {
	public String type = QQMessageType.MSG_TYPE_CHAT_P2P; 
	public long from = 0; 
	public String fromNick = ""; 
	public int fromAvatar = 1; 
	public long to = 0;  
	public String content = ""; 
	public String sendTime = MyTime.geTime();  
}
