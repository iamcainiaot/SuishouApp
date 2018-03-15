package com.example.get_jingweidu;

import java.util.ArrayList;
import java.util.List;

public class Jingwei {

	static List<Bean> list=new ArrayList<Bean>();
	
	public void add(Bean bean){
		list.add(bean);
	}
	
	public Bean get(){
		
		return list.get(list.size()-1);
	}
}
