package org.jivesoftware.openfire.plugin.util;

import java.io.UnsupportedEncodingException;

public class Codec{

	static char [] tab_enc = "nlmopqQH78SkC012xyMzw9FOPGRTUfghijrI#,=AstKL3de4u6J5Dv+/*&WXYVaBNbcZE".toCharArray();
	static char [] tab_dec = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/*&#,=".toCharArray();

	public static String codec(String content){
		try {
			String v = Base64.encode(content.getBytes("UTF-8"));
//			String v = Base64.encodeToString(content.getBytes("UTF-8"));
			char [] src = v.toCharArray();
			StringBuffer sb = new StringBuffer();
			for(int j=0; j<src.length; j++){
				for(int i=0; i<tab_dec.length; i++){
					if(src[j]==tab_dec[i]){
						sb.append(tab_enc[i]);
					}
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	public static String decode(String input){
		StringBuffer sb = new StringBuffer();
		char [] src = input.toCharArray();
		for(int j=0; j<src.length; j++){
			for(int i=0; i<tab_enc.length; i++){
				if(src[j]==tab_enc[i]){
					sb.append(tab_dec[i]);
				}
			}
		}
		String value = sb.toString();
//		byte [] bArray = Base64.decode(value, Base64.DEFAULT);
		byte [] bArray = Base64.decode(value);
		try {
			return new String(bArray, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
}
