package com.example.easyhand1;

import android.app.Application;

public class MyApplication extends Application {
	private String name;  
    private boolean firstuseflag; 
    private int zan_num;
      
    @Override    
    public void onCreate() {      
        super.onCreate();   
        setName(NAME); //初始化全局变量  
        SetFirstUseFlag(true);  
    }    
    public String getName() {  
        return name;  
    }  
    public boolean IsFirstUse(){  
        return firstuseflag;  
    }  
    public void SetFirstUseFlag(boolean flag){  
        this.firstuseflag=flag;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    
    public void setZan_num(int zan_num){
    	this.zan_num=zan_num;
    }
    
    public int getZan_num() {
    	return zan_num;	
	}
  
    private static final String NAME = "MyApplication";  
}
