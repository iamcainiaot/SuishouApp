package com.example.dingwei;

public class IpUtil {

	static final String scoket_ip="192.168.1.107";
	static final String FWip="http://"+scoket_ip+":8080";
	
	public static String getFWip(){
		return FWip;
	}
	
	public static String getScoKet_ip(){
		return scoket_ip;
	}
}
