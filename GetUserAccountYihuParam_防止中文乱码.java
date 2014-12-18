package com.yihu.account.ws.ipo;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author huangqb</br>
 * @Company yihu.com</br>
 * @Createdate 2013-12-24ÉÏÎç09:24:06</br>
 */
public class GetUserAccountYihuParam implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3986424687803733280L;
	
	
	private String userphone ;
	private Integer operatorid;
	private String operatorname;
	private Integer deptid;
	private String deptname;
	
	public String getUserphone() {
		return userphone;
	}
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}
	public Integer getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(Integer operatorid) {
		this.operatorid = operatorid;
	}
	public String getOperatorname() {
		return operatorname;
	}
	public void setOperatorname(String operatorname) {
		try {
			this.operatorname = java.net.URLDecoder.decode(operatorname,"utf-8");		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Integer getDeptid() {
		return deptid;
	}
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		try {
			this.deptname = java.net.URLDecoder.decode(deptname,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
