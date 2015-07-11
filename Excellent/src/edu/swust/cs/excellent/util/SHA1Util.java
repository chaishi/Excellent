package edu.swust.cs.excellent.util;

import org.apache.commons.codec.digest.DigestUtils;


public class SHA1Util {
	
	public final static String SHA1(String s){
		
		String string=DigestUtils.shaHex(s);
		return string;
		
	}
}
