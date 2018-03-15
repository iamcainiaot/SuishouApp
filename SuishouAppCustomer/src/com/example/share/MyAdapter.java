package com.example.share;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bean.TestApplication;
import com.example.easyexpress1.R;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter {
	private class ButtonViewHolder {  
        TextView name;  
        TextView content;  
        TextView date;  
        TextView zan_num;  
        ImageView zan;  
    }  

    private Context mContext;  
    private List<HashMap<String, Object>> list;  
    private ButtonViewHolder holder;  
    private LayoutInflater mInflater;  
    private String[] keyString;  
    private int[] valueViewID;  
	
    
    public MyAdapter(Context context, ArrayList<HashMap<String, Object>> list,  
            int resource, String[] from, int[] to) {  
        this.mContext = context;  
        this.list = list;  

        // 获得布局文件对象  
        mInflater = (LayoutInflater) context  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        keyString = new String[from.length];  
        valueViewID = new int[to.length];  

        // 复制数组  
        System.arraycopy(from, 0, keyString, 0, from.length);  
        System.arraycopy(to, 0, valueViewID, 0, to.length);  
    }  
    
    @Override  
    public int getCount() {  
        return list.size();  
    }  

    @Override  
    public Object getItem(int position) {  
        return list.get(position);  
    }  

    /** 
     * 从list中移除某一项 
     *  
     * @param position 
     */  
    public void removeItem(int position) {  
        list.remove(position);  
        // 通知数据集已改变，请求自刷新  
        this.notifyDataSetChanged();  
    }  

    @Override  
    public long getItemId(int position) {  
        return position;  
    }  

    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        if (convertView != null) {  
            holder = (ButtonViewHolder) convertView.getTag();  
        } else {  
        	//R.id.ItemPer_image,R.id.ItemPer_name,R.id.Itemcontent,R.id.Itemphoto,R.id.Itemdate,R.id.Itemzan_num
            convertView = mInflater.inflate(R.layout.shareitem, null);  
            holder = new ButtonViewHolder();  
            holder.name = (TextView) convertView  
                    .findViewById(R.id.ItemPer_name);
            holder.content = (TextView) convertView  
                    .findViewById(R.id.Itemcontent);
            holder.date = (TextView) convertView  
                    .findViewById(R.id.Itemdate);  
            holder.zan_num = (TextView) convertView  
                    .findViewById(R.id.Itemzan_num);
            holder.zan = (ImageView) convertView.findViewById(R.id.Itemzan);
            convertView.setTag(holder);  
        }  
        Map<String, Object> appInfo = list.get(position);  
        if (appInfo != null) {  
        	  
            String aname = (String) appInfo.get(keyString[1]);  
            String acontent = (String) appInfo.get(keyString[2]);  
            String adate = (String) appInfo.get(keyString[4]);
            int azan_num = (Integer) appInfo.get(keyString[5]);

            holder.name.setText(aname);  
            holder.content.setText(acontent);  
            holder.date.setText(adate);  
            holder.zan_num.setText(String.valueOf(azan_num));  

            // 点赞按钮事件  
            holder.zan.setOnClickListener(new lvButtonListener(position));  
        }  
        return convertView;  
	}

	public void setNotifyDataChange(int id) {
		// TODO Auto-generated method stub
		
	}

}




class lvButtonListener implements OnClickListener {  
    private int position;  
   

    lvButtonListener(int pos) {  
        position = pos;  
    }

    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.i("logb", "赞!!!!");
		
		//share.zan();
	}  


}