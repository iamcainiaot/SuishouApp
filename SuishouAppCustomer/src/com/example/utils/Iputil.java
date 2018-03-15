package com.example.utils;

public class Iputil {
	// http://172.25.36.236
	static final String scoket_ip = "192.168.1.107";
	static final String FWip = "http://" + scoket_ip + ":8080";

	public static String getFwqIp() {
		return FWip;
	}

	public static String getScoketIp() {
		return scoket_ip;
	}
}
