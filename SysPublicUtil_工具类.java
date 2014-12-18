package com.yihu.account.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coreframework.vo.ReturnValue;

/**
 * 
 * @author wanghf
 * @company yihu
 * 2013-5-28上午10:15:07
 */
public class SysPublicUtil {
	/**
	 * 判断对象是否为空或者空串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		
		if (obj instanceof String) {
			return (obj == null || obj.toString().trim().length() == 0);
		}
		if (obj instanceof Long) {
			return (obj == null || (Long) obj == 0);
		}
		if (obj instanceof Integer) {
			return (obj == null || (Integer) obj == 0);
		}
		return obj == null;
	}
	
	public static String dateToString(Date dt, String strFormat) {
		SimpleDateFormat sdFormat = new SimpleDateFormat(strFormat);
		String str = "";
		try {
		str = sdFormat.format(dt);
		}
		catch(Exception e) {
		return "";
		}
		if (str.equals("1900-01-01 00:00")) {
		str = "";
		}

		return str;
		}
	public static String getNumberPwd(int i){
		String pwd=MyRandom.getNumberRandom(i);
		boolean flag=true;
		while(flag){
			if(!SysPublicUtil.validatePwd(pwd) && !SysPublicUtil.hasLH(pwd) && !SysPublicUtil.isContinuityCharacter(pwd)){
				flag=false;
			}else{
				pwd=MyRandom.getNumberRandom(i);//随机码
			}
		}
		return pwd;
	}
	public static void main(String[] args) {
		//System.out.println(SysPublicUtil.getNumberPwd(6));
	}
	public static boolean validatePwd(String s){
		Pattern pattern = Pattern.compile("([\\d])\\1{3,5}");
	     Matcher matcher = pattern.matcher(s);
	     if(matcher.matches()) return true;//判断是否为4到6位全一样的数字
	     if(s.length()==4){
	    	 pattern = Pattern.compile("([\\d])\\1{1}([\\d])\\2{1} ");
	    	 matcher = pattern.matcher(s);
	    	 if(matcher.matches()) return true; //判断是否如2233,3355
	    	 if(s.substring(0,2).equals(s.subSequence(2, 4))){//判断是否1212,2525
	    		 return true;
	    	 }
    	    StringBuffer mstr = new StringBuffer(s.subSequence(2, 4));  
    	    if(s.substring(0,2).equals(mstr.reverse().toString())){//判断是否1221,2552
	    		 return true;
    	    }
	     }else if(s.length()==6){
	    	 pattern = Pattern.compile("([\\d])\\1{2}([\\d])\\2{2} ");
	    	 matcher = pattern.matcher(s);
	    	 if(matcher.matches()) return true;//判断是否如222333,333555
	    	 if(s.substring(0,3).equals(s.subSequence(3, 6))){//判断是否123123,256256
	    		 return true;
	    	 }
	    	 StringBuffer mstr = new StringBuffer(s.subSequence(3,6));  
    	    if(s.substring(0,3).equals(mstr.reverse().toString())){//判断是否123321,256652
	    		 return true;
    	    }
	     }
	     return false;
	}
	//判断数字递减 654321
	public static boolean isContinuityCharacter(String s){
		boolean continuity = true;
		char[] data = s.toCharArray();
		for(int i=0; i<data.length-1; i++){
		int a = Integer.parseInt(data[i]+"");
		int b = Integer.parseInt(data[i+1]+"");
		continuity = continuity && (a+1 == b || a-1 == b);
		}
		return continuity;
		}
	//判断数字递增 123456
	public static boolean hasLH(String str) {
		int count=str.length();
		int pre = 0;
		int len = 1;
		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i + 1);
			char c = s.charAt(0);
			if (i == 0) {
				pre = c;
			}
			if (c - 1 == pre) {
				len++;
				if(len>=count){
					return true;
				}
			}else {
				len = 1;
			}
			pre = c;
		}
		return false;
	}
}
