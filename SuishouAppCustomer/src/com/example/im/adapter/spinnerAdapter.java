//package com.example.im.adapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.easyexpress1.R;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//
//public class spinnerAdapter extends Activity {
//	private Spinner spinner;
//	private List<String> data_list;
//	private ArrayAdapter<String> arr_adapter;
//
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.faq);
//		spinner = (Spinner) findViewById(R.id.spinner_1);
//
//		// 装载数据
//		data_list = new ArrayList<String>();
//		data_list.add("快递问题");
//		data_list.add("问题1");
//		data_list.add("问题2");
//		data_list.add("问题3");
//	       
//        //适配器
//        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
//        //设置样式
//        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //加载适配器
//        spinner.setAdapter(arr_adapter);
//        
//    }
//	}
// 
