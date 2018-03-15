package com.example.demo;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class Demo extends ViewPager {
	private Timer timer;

	public Demo(Context context) {
		super(context);

	}

	public Demo(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.arg1 == 1) {
				// ���һ�
				arrowScroll(FOCUS_RIGHT);
			}
			if (msg.arg1 == 2) {
				// �ع�����һҳ
				setCurrentItem(0);
			}

		};
	};

	// �Զ�����
	public synchronized void startAutoScroll(long delay, long period) {
		timer = new Timer();
		timer.schedule(new MyTask(), delay, period);

	}
	// ֹͣ�Զ�����
	public synchronized void stopAutoScroll(){
		timer.cancel();
	}
	private class MyTask extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			scrollAction();
		}

		private void scrollAction() {
			// TODO Auto-generated method stub
			//��ȡ��ǰҳ��
			int pageNo = getCurrentItem();
			//��ȡ��ҳ��
			int pageCount = getAdapter().getCount() -1 ;
			Message msg = new Message();
			if(pageNo<pageCount){
				msg.arg1=2;
			}
			handler.sendMessage(msg);
			
		}
		
	}
	
}
