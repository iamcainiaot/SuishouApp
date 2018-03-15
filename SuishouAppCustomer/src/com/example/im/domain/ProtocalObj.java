package com.example.im.domain;

import com.thoughtworks.xstream.XStream;

/**
 * Э��ʵ�壬��Ŀ�е�ÿһ��ʵ�嶼�̳����Э��ʵ�壬��Э��ʵ������һЩ���õķ�����
 * 
 * @author ZHY
 * 
 */
public class ProtocalObj {
	/**
	 * ����xml
	 * 
	 * @return
	 */
	public String toXML() {
		XStream x = new XStream();
		// ���ñ�����Ĭ�������ȫ·����
		x.alias(getClass().getSimpleName(), getClass());
		String xml = x.toXML(this);// ������ת����xml����
		return xml;

	}

	/**
	 * xml-->ʵ����
	 * 
	 * @param xml
	 * @return
	 */
	public Object fromXML(String xml) {
		XStream x = new XStream();
		x.alias(getClass().getSimpleName(), getClass());
		return x.fromXML(xml);

	}

}
