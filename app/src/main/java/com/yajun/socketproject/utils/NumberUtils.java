package com.yajun.socketproject.utils;

import java.text.DecimalFormat;

public class NumberUtils {
	/**
	 * ��ʽ���۸�ǿ�Ʊ���2λС��
	 * @param price
	 * @return
	 */
	public static String formatPrice(double price) {
		DecimalFormat df=new DecimalFormat("0.00");
		String format = "��" + df.format(price);
		return format;
	}
}
