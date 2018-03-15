package com.example.get_jingweidu;

public class IpUtil {

	static final String scoket_ip="172.25.172.200";
	static final String FWip="http://"+scoket_ip+":8080";
	
	public static String getFWip(){
		return FWip;
	}
	
	public static String getScoKet_ip(){
		return scoket_ip;
	}
}
