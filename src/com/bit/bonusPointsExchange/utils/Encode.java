package com.bit.bonusPointsExchange.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密算法
 */
public class Encode {

	/*传入一个字符串，输出它的MD5加密串*/
	public static String MD5Encode(String str) {
		if (null == str) {
			return null;
		}
		StringBuilder sB = new StringBuilder();//创建可变字符序列的对象
		try {
			MessageDigest code = MessageDigest.getInstance("MD5");//创建具体指定算法名称的信息摘要
			code.update(str.getBytes());
			byte[] bs = code.digest();
			for(int i = 0; i < bs.length; i++) {
				int v = bs[i]&0xFF;
				if(v < 16) {
					sB.append(0);
				}
				sB.append(Integer.toHexString(v));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sB.toString().toUpperCase();
	}
}
