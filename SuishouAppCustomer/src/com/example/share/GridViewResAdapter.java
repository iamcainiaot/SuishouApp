package com.example.share;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.easyexpress1.R;
import com.example.share.ImageLoader.ImageCallback;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridViewResAdapter extends BaseAdapter {

    private static final String TAG = "GridViewResAdapter";
    private int selectPic = -1;
    //����Դ

    private Context mContext;
   // private  ImageLoader imageLoader;
    //����Դ
    private String[] mListResult ;

    public GridViewResAdapter(Context mContext, String[] mListResult) {
        super();
        this.mListResult=mListResult;
        this.mContext = mContext;
        
    }

    @Override
    public int getCount() {
        return mListResult.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

  //���嵱ǰlistview�Ƿ��ڻ���״̬
    private boolean scrollState=false;
    public void setScrollState(boolean scrollState) {
     this.scrollState = scrollState;
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photoitem, parent, false);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.photoView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //holder.iv.setBackgroundResource(R.drawable.ic_launcher);
		String appInfo = mListResult[position]; 
		
		String file = "sdcard/Download/"+appInfo;
        ImageLoader imageLoader=new ImageLoader();
        Drawable cachedImage = imageLoader.loadDrawable(file, new ImageCallback() {  
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {  
               // ImageView imageViewByTag = (ImageView) gridView.findViewWithTag(imageUrl);  
                if (holder.iv != null) {  
                	holder.iv.setImageDrawable(imageDrawable);  
                } else if (holder.iv!= null) {  
                    holder.iv.setBackgroundResource(R.drawable.imageloading);
                }
            }  
        });  
        
//        
//        		String appInfo = mListResult[position]; 
//        		
//        		String file = "sdcard/Download/"+appInfo;
//                
//        		
//        		BitmapFactory.Options options = new BitmapFactory.Options();  
//        		options.inSampleSize = 2; //图片宽高都为原来的二分之一，即图片为原来的四分之一  
//        		options.inTempStorage = new byte[1024*1024*5]; //5MB的临时存储空间  
//        		Bitmap bitmap = BitmapFactory.decodeFile(file,options);
//        		
//        		
//        		Drawable drawable =new BitmapDrawable(bitmap);
//               // Bitmap bm = BitmapFactory.decodeFile(file); 
//                	 
                
//			
     
        return convertView;
    }

    class ViewHolder {

        ImageView iv;


    }
    
}
