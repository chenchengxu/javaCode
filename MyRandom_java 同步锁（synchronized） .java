package com.yihu.account.util;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import com.coreframework.remoting.standard.DateOper;

public class MyRandom {

	public static void main(String[] args) {
		String a=getNumberRandom(5);
		System.out.println(a);
	}
	/**
	 * 产生随机数（纯数字）
	 * @author huangqb</br>
	 * @Createdate 2013-12-12下午03:54:25</br>
	 * @param i 需要的随机数位数
	 * @return
	 */
	public static String getNumberRandom(int i) {
		try {

			UUID uuid = UUID.randomUUID();

			String s = uuid.toString().replaceAll("-", "");
			char[] ss = s.toCharArray();

			double dbl = 0;

			for (char c : ss) {

				Character c2 = new Character(c);

				dbl += c2.hashCode() * Math.random();

			}

			Date date = new Date();

			long times = date.getTime();

			dbl = times % (dbl * Math.rint(dbl * 200)) % Math.random();

			String str = String.valueOf(dbl);

			StringBuffer sbf = new StringBuffer();

			int k = 0;

			for (char c : str.toCharArray()) {

				if (c == '.' || c == 'E' || c == 'e' || c == '-') {

					continue;

				} else {

					sbf.append(c);

				}
				k++;
			}
			BigInteger doubl = BigInteger
					.valueOf(Long.parseLong(sbf.toString()));

			String retval = doubl.toString().substring(0, i);

			return retval;

		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取支付订单订单号
	 * @author huangqb</br>
	 * @Createdate 2014-1-3上午09:28:55</br>
	 * @return
	 */
	public synchronized static String getPayOrderId(){
		try {
			StringBuffer str = new StringBuffer(DateOper.getNow("yyyyMMddHHmmss"));
			str.append(getNumberRandom(4));
			return str.toString();
		} catch (ParseException e) {
			return "";	
		} catch(Exception e1){
			return "";
		}
		
		
	}
}
