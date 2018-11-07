package com.quinhai.pos.beans;

import org.springframework.stereotype.Component;

@Component("adminInfo")
public class AdminInfo {
	private String Aid;
	private String Aname;
	private String Apwd;
	private String Alevel;
	
	public AdminInfo() {
		
	}
	
	public AdminInfo(String aid, String aname, String apwd, String alevel) {
		super();
		Aid = aid;
		Aname = aname;
		Apwd = apwd;
		Alevel = alevel;
	}
	public String getAid() {
		return Aid;
	}
	public void setAid(String aid) {
		Aid = aid;
	}
	public String getAname() {
		return Aname;
	}
	public void setAname(String aname) {
		Aname = aname;
	}
	public String getApwd() {
		return Apwd;
	}
	public void setApwd(String apwd) {
		Apwd = apwd;
	}
	public String getAlevel() {
		return Alevel;
	}
	public void setAlevel(String alevel) {
		Alevel = alevel;
	}
	
	
}
