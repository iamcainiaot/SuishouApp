package com.example.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.easyexpress1.R;
import com.example.shouye.qiangdan;

public class getmessage {
	 public void showMessage() throws JSONException{
			
			String text = "[{pro_name:'ë��',pro_num:1,pro_type:'������Ʒ'��pro_address:'����'��rec_address:'����'}," +
					"{pro_name:'����',pro_num:1,pro_type:'ʳƷ'��pro_address:'����'��rec_address:'�㽭'}]";
			
			//将json字符串解析成json数组
			JSONArray jsonarray = new JSONArray(text);
			
			List<Dingdan> list = new ArrayList<Dingdan>();
			
			
			for(int i = 0;i<jsonarray.length();i++){
				
				JSONObject jsonobject = jsonarray.getJSONObject(i);
				
				if(jsonobject !=null){
					
					//
					//int did = jsonobject.optInt("id");
					String pro_name=jsonobject.optString("pro_name");
					String pro_num=jsonobject.optString("pro_num");
					String rec_address=jsonobject.optString("rec_address");
					String pro_type=jsonobject.optString("pro_type");
					String pro_address=jsonobject.optString("pro_address");
					
					
					Dingdan s = new Dingdan();
					s.setPro_name(pro_name);
					s.setPro_num(Integer.parseInt(pro_num));
					s.setPro_address(pro_address);
					s.setRec_address(rec_address);
					s.setPro_type(pro_type);
					
					list.add(s);
				}
			}
			for (Dingdan s : list)  
	        { 
				System.out.println("������"+s.getPro_name());
	        }
	
	 }
	 
	 
}


