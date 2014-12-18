package com.yihu.account.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.coreframework.db.FileOper;


public class TrackLog {

	private TrackLog(){
		
	}
	
	private static TrackLog instance = null;
	
	private static final String LogPath = "D:\\Log\\";
	
	/**
	 * 获取实例对象（单例模式）
	 * @author huangqb</br>
	 * @Createdate 2014-5-26下午02:07:32</br>
	 * @return
	 */
	public static TrackLog getInstance(){
		if(instance == null){
			instance = new TrackLog();
		}
		return instance;
	}
	
	public void saveTrackLog(String moduleName,String fileName,String LogValue){
		File file = new File(LogPath + moduleName );
		if(!file.exists()){
			file.mkdirs();
		}
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowDate = sdf.format(calendar.getTime());
		//System.out.println(LogPath + moduleName + "\\" + fileName  +nowDate + ".txt");
		FileOper.write(LogPath + moduleName + "\\" + fileName + nowDate + ".txt", LogValue ,true);
	}
}
