package com.example.share;

import com.example.easyexpress1.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class DialogLoading extends Dialog {  
    Context context;  
  
    ImageView vLoading;
    Animation anim;  
  
    public DialogLoading(Context context) {  
        super(context, R.style.HKDialogLoading);  
        this.context = context;  
    }  
  
    public void onCreate(Bundle savedInstanceState) {  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        requestWindowFeature(Window.PROGRESS_VISIBILITY_ON);  
        super.onCreate(savedInstanceState);  
  
        anim = AnimationUtils.loadAnimation(this.getContext(),  
                R.anim.rotate_repeat);  
        anim.setInterpolator(new LinearInterpolator());  
  
        vLoading = new ImageView(context);  
        vLoading.setImageResource(R.drawable.loading);
  
        
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(  
                FrameLayout.LayoutParams.MATCH_PARENT,  
                FrameLayout.LayoutParams.WRAP_CONTENT);  
        lp.gravity = Gravity.CENTER;  
  
        addContentView(vLoading, lp);  
  
        setCanceledOnTouchOutside(false);  
    }  
  
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK) {  
           
            this.hide();  
            return true;  
        }  
        return false;  
    }  
  
    @Override  
    public void show() {  
        super.show();  
        vLoading.startAnimation(anim);  
    }  
}  
