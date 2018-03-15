package com.example.shouye;

import java.util.*;
import org.json.*;
import java.util.ArrayList;
import com.example.bean.*;

public class JsonToJavaObject {
	
	//浠嶫SON瀛楃涓蹭腑鑾峰彇json鏁版嵁
	public void getValue() throws JSONException{  
		//鑾峰彇鑾峰垱寤篔SON鏁版嵁
		String json="{"+"id"+":"+"4}";
		
		//瑙ｆ瀽json
		JSONObject jsonobject = new JSONObject(json); //灏唈son瀛楃涓茶浆鍖栨垚JOSN瀵硅薄
		
		//閫氳繃閿悕鑾峰彇瀵瑰簲鐨勫��
		String id = jsonobject.getString("id"); //閲囩敤杩欑鏂规硶鑾峰彇鏃讹紝褰撹幏鍙栫殑鍊间负绌轰細鎶ョ┖鎸囬拡寮傚父
		String id2 = jsonobject.optString("id"); //閲囩敤杩欑鏂规硶锛屼笉浼氭姤寮傚父
	}
	
	//浠嶫SON鏍煎紡鐨勯泦鍚堜腑鑾峰彇json鏁版嵁
	
	public void getListValue() throws JSONException{
		
		String jsonlist = "[{id:4},{id:4}]";
		
		//灏唈son瀛楃涓茶В鏋愭垚json鏁扮粍
		JSONArray jsonarray = new JSONArray(jsonlist);
		List<Dingdan> list = new ArrayList<Dingdan>();
		
		for(int i = 0;i<jsonarray.length();i++){
			
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			
			if(jsonobject !=null){
				
				int id = jsonobject.optInt("id");
				String name = jsonobject.optString("name");
				
				Dingdan s = new Dingdan();
			//	ShareInfor s = new ShareInfor(id,name);
				
				list.add(s);
			}
		}
		
		for (Object s : list) {
            String str = (String) s;
            System.out.println(str);
        }
	}
}
