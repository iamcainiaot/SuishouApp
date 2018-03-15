package com.example.im.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.example.im.domain.QQMessage;
 
public class QQConnection {

	private String host = "";
	private int port = 8090;
	Socket client;
	private DataInputStream reader;
	private DataOutputStream writer;
	private WaitThread waitThread;
	public boolean isWaiting;

	/**
	 * new��QQConnection�����ʱ���ʼ��IP��ַ�Ͷ˿�
	 * 
	 * @param host
	 * @param port
	 */
	public QQConnection(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	/**
	 * �����������֮�������
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public void connect() throws UnknownHostException, IOException {
		// ��������
		client = new Socket(host, port);
		reader = new DataInputStream(client.getInputStream());
		writer = new DataOutputStream(client.getOutputStream());
		// �������ӵ�ʱ�����ȴ��߳�
		isWaiting = true;
		waitThread = new WaitThread();
		waitThread.start();

	}

	/**
	 * �Ͽ�������ڼ������
	 * 
	 * @throws IOException
	 */
	public void disConnect() throws IOException {
		// �ر����Ӿ����ͷ���Դ
		client.close();
		reader.close();
		writer.close();
		isWaiting = false;
	}

	/**
	 * ����xml��Ϣ
	 * 
	 * @param xml
	 * @throws IOException
	 */
	public void sendMessage(String xml) throws IOException {
		// ������ϢҪ�õ������������������Ϊ��ĳ�Ա�������ڴ������ӵ�ʱ���ʼ�����Ͽ����ӵ�ʱ���ͷ���Դ
		// ������Ϣ��ʵ���ǰ���Ϣд��ȥ
		writer.writeUTF(xml);

	}

	/**
	 * ����java������Ϣ
	 * 
	 * @param xml
	 * @throws IOException
	 */
	public void sendMessage(QQMessage msg) throws IOException {
		writer.writeUTF(msg.toXML());
	}

	/**
	 * �ȴ��߳� ������Ϣ,���ڲ�֪����Ϣʲôʱ�򵽴��Ҫһֱ�ȴ��ţ��ȴ���Ϣ�ĵ���
	 * 
	 * @author ZHY
	 * 
	 */
	private class WaitThread extends Thread {

		@Override
		public void run() {
			super.run();
			while (isWaiting) {
				// ������Ϣ��ʵ���ǽ���Ϣ��ȡ��
				try {
					String xml = reader.readUTF();// ��ȡ��Ϣ
					// ����Ϣת��Java����
					QQMessage msg = new QQMessage();
					msg = (QQMessage) msg.fromXML(xml);
					System.out.println(msg.content);
					// ������յ���Ϣ��������Ϣ�б����type�ֶ��������¼����ȡ��ϵ���б��ǳ��Ȳ���������һ���ֲ�����ȡ����һ���ӿڣ������ڰ�ť�ĵ���¼����������յ���Ϣ��������
					/*
					 * ���յ���Ϣ֮�����ε���ÿ����������onReceive����
					 */
					for (OnMessageListener listener : listeners) {
						listener.onReveive(msg);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
	}

	// �������ᾭ�����ͻ��˷�����Ϣ���ͻ��˻��в�ͬ����Ϣ�����������½�һ���������ļ��ϣ������������һ���������͵���һ��onReveive������
	/*
	 * �������о͵��ã�������û�оͲ�����
	 */
	private List<OnMessageListener> listeners = new ArrayList<OnMessageListener>();

	public void addOnMessageListener(OnMessageListener listener) {
		listeners.add(listener);
	}

	public void removeOnMessageListener(OnMessageListener listener) {
		listeners.remove(listener);
	}

	/**
	 * ��Ϣ�ļ������ӿڣ�������Ϣ������ʱ��͵���һ��onReceive����
	 * 
	 * @author ZHY
	 * 
	 */
	public static interface OnMessageListener {
		public void onReveive(QQMessage msg);
	}
}
