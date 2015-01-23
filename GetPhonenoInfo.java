/**  
 * @Title: GetPhonenoInfo.java
 * @Package com.yihu.vas.util
 * @Description: TODO
 * @author chencx
 * @date 2014-8-25
 */
package com.yihu.vas.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

import com.coreframework.ioc.Ioc;
import com.yihu.vas.dao.IAdscriptionDao;
import com.yihu.vas.dao.IBaseDAO;
import com.yihu.vas.dao.IPnLibraryDao;

/**
 * ClassName: GetPhonenoInfo
 * 
 * @Description: 获取手机号的归属地市
 * @author chencx
 * @date 2014-8-25
 */
public class GetPhonenoInfo {
	public static final String GET_URL = "http://wap.ip138.com/sim_search138.asp";
	
	
	private static IPnLibraryDao pnLibraryDao=Ioc.get(IPnLibraryDao.class);
	private static IAdscriptionDao adscriptionDao=Ioc.get(IAdscriptionDao.class);
	private static IBaseDAO dao=Ioc.get(IBaseDAO.class);
	public GetPhonenoInfo(){
		try {
//			pnLibraryDao=Ioc.get(IPnLibraryDao.class);
//			adscriptionDao=Ioc.get(IAdscriptionDao.class);
//			dao=Ioc.get(IBaseDAO.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public static boolean addCityInfo(String tel){
		boolean ret=false;
		try {
			String telInfo=readContentFromGet(tel);
//			System.out.println(telInfo);
			String [] info=telInfo.split(",");
			if(info.length==1){
				System.out.println(tel+" 无法找到信息");
			}else{
				int provinceid=0;
				int cityid=0;
				if(StringUtils.isNotEmpty(info[1])){
					provinceid=dao.getProvinceidByName(info[1]);
				}
				if(StringUtils.isNotEmpty(info[2])){
					cityid=dao.getCityidByName(info[2]);
				}
				adscriptionDao.addAdscription(tel, info[3], info[1], info[2], provinceid, cityid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	
	

	
	public static boolean updateCityInfo(String tel){
		try {
			String telInfo=readContentFromGet(tel);
			String mobileSection="";
			String [] info=telInfo.split(",");
			if(info.length==1&&tel.length()==7){//排除电话号码，只进行号码段更新状态
				adscriptionDao.updateAdscriptionState(tel, 2);
				System.out.println("更新"+tel+"的状态------------");
			}else{
				String pname=info[1];
				String cname=info[2];
				String type=info[3];
				int provinceid=dao.getProvinceidByName(pname);
				int cityid=dao.getCityidByName(cname);
				if(tel.length()>7){
					mobileSection=tel.substring(0, 7);
					if(pnLibraryDao.checkPhonenoExist(tel)){
						pnLibraryDao.updatePnLibrary(provinceid, cityid, tel);
						if(adscriptionDao.checkAdscriptionExist(mobileSection)){
							adscriptionDao.updateAdscription(provinceid, cityid, mobileSection);
						}else{
							adscriptionDao.addAdscription(mobileSection, type, pname, cname, provinceid, cityid);
						}
					}else{
						pnLibraryDao.addPNLibrary(tel, provinceid, cityid);
						if(adscriptionDao.checkAdscriptionExist(mobileSection)){
							adscriptionDao.updateAdscription(provinceid, cityid, mobileSection);
						}else{
							adscriptionDao.addAdscription(mobileSection, type, pname, cname, provinceid, cityid);
						}
					} 
				}else{
					mobileSection=tel;
					if(adscriptionDao.checkAdscriptionExist(mobileSection)){
						adscriptionDao.updateAdscription(provinceid, cityid, mobileSection);
					}else{
						adscriptionDao.addAdscription(mobileSection, type, pname, cname, provinceid, cityid);
					}
				}
			}
			
			System.out.println("更新:"+tel+"信息");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * @Description: TODO
	 * @param @param tel
	 * @param @return
	 * @param @throws IOException   
	 * @return String  返回格式 ：tel,省份,地市,卡类别
	 * @throws
	 * @author chencx
	 * @date 2014-8-25
	 */
	public static String readContentFromGet(String tel) throws IOException {
		
		String ret = "";
		try {
//		String getURL = GET_URL + "?mobile=" + URLEncoder.encode(tel, "utf-8");
			String getURL = GET_URL + "?mobile=" + URLEncoder.encode(tel, "utf-8")+"&action=mobile";
			URL getUrl = new URL(getURL);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
			String lines;
			while ((lines = reader.readLine()) != null) {
				if (lines.indexOf("手机号：") >= 0) {
					String telephone = lines.replaceAll("<div>", "").replaceAll("<br/>", "").replace("手机号：", "");
					if(tel.length()>7){//完整的手机号码
						ret += telephone + ",";
					}else{//号码断
						ret += telephone.substring(0, 7) + ",";
					}
					
				}
				if (lines.indexOf("归属地：") >= 0) {
					String province_city = lines.replaceAll("<br/>", "").replace("归属地：", "");
					if(!province_city.equals("未知")){
						String[] arrays = province_city.split(" ");
						if(arrays.length>1){
							ret += arrays[0] + "," + arrays[1] + ",";
						}else{
							ret += arrays[0] + "," + arrays[0] + ",";
						}
						
					}else{
					}
				}
				if (lines.indexOf("卡类型：") >= 0) {
					String cardType = lines.replaceAll("<br/>", "").replace("卡类型：", "");
					if(!cardType.equals("未知")){
						ret += cardType;
					}
				}
			}
			reader.close();
			connection.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("为"+tel+"获取信息:"+ret);
		return ret;
	}
	

	
	
	public static void main(String[] args) throws IOException {
		GetPhonenoInfo a=new GetPhonenoInfo();
		String ret=a.readContentFromGet("1320094");
		System.out.println("--:"+ret);
		
	}
}
