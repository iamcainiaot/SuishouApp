//package com.example.connect;
//
//import com.example.easyhand1.R;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//
//public class MainActivityScoket extends Activity {
//	 // ��������ϵ������ı���  
//    EditText input;  
//    TextView show;  
//    // ��������ϵ�һ����ť  
//    Button send;  
//    Handler handler;  
//    // �����������ͨ�ŵ����߳�  
//    ClientThread clientThread; 
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.scoket);
//        input = (EditText) findViewById(R.id.input);  
//        show = (TextView) findViewById(R.id.show);  
//        send = (Button) findViewById(R.id.send);  
//        handler = new Handler() {  
//  
//            @Override  
//            public void handleMessage(Message msg) {  
//                // �����Ϣ�������߳�  
//                if (msg.what == 0x123) {  
//                    // ����ȡ������׷����ʾ���ı�����  
//                    show.append("\n" + msg.obj.toString());  
//                }  
//               
//            }  
//        };  
//        clientThread = new ClientThread(handler);  
//        // �ͻ�������ClientThread�̴߳����������ӡ���ȡ���Է�����������  
//        new Thread(clientThread).start();  
//        send.setOnClickListener(new OnClickListener() {  
//  
//            @Override  
//            public void onClick(View v) {  
//                try {  
//                    // ���û����°�ť֮�󣬽��û���������ݷ�װ��Message  
//                    // Ȼ���͸����߳�Handler  
//                    Message msg = new Message();  
//                    msg.what = 0x345;  
//                    msg.obj = input.getText().toString();  
//                    clientThread.revHandler.sendMessage(msg);  
//                    input.setText("");  
//  
//                } catch (Exception e) {  
//  
//                }  
//            }  
//        });  
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
