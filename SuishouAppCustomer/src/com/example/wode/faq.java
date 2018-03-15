package com.example.wode;

import com.example.easyexpress1.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView.BufferType;

public class faq extends Activity {
	 String s = "消费者支付快递费，快递公司收取了快递费，双方在法律上已经形成了有效的合同关系 ，快递公司应当按照约定完成快递服务。“在运输过程中，由于快递公司工作上的失误等原因 ，物品丢失或者损毁，只要消费者能够提供当初快递物品的发票等价值证明，快递公司都 应按照照价赔偿进行赔偿。也就是说，无论是否保价，都适用于《民法通则》、《合同法》等民事法律关于损失赔偿的规定。”没有保价的物品，消费者要保留能证明其价值的凭证，一旦物品出现丢失或损毁，可以利用凭证并依照相关的民事法律，要求快递公司按照实际损失进行赔偿。 ";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq);
		
		//自定义控件
		CollapsibleTextView tv = (CollapsibleTextView) findViewById(R.id.desc_collapse_tv);
		tv.setDesc(s, BufferType.NORMAL);
	}

}
