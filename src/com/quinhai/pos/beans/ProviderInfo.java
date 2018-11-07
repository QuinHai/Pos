package com.quinhai.pos.beans;

import org.springframework.stereotype.Component;

@Component("providerInfo")
public class ProviderInfo {
	private String Pid;
	private String Pname;
	private String Plinkman;
	private String Paddress;
	private String Ptel;
	private String Pemail;
	private String Premark;
	
	public ProviderInfo() {
		
	}
	
	public ProviderInfo(String pid, String pname, String plinkname, String paddress, String ptel, String pemail,
			String premark) {
		super();
		Pid = pid;
		Pname = pname;
		Plinkman = plinkname;
		Paddress = paddress;
		Ptel = ptel;
		Pemail = pemail;
		Premark = premark;
	}
	
	public String getPid() {
		return Pid;
	}
	public void setPid(String pid) {
		Pid = pid;
	}
	public String getPname() {
		return Pname;
	}
	public void setPname(String pname) {
		Pname = pname;
	}
	public String getPlinkman() {
		return Plinkman;
	}
	public void setPlinkman(String plinkman) {
		Plinkman = plinkman;
	}
	public String getPaddress() {
		return Paddress;
	}
	public void setPaddress(String paddress) {
		Paddress = paddress;
	}
	public String getPtel() {
		return Ptel;
	}
	public void setPtel(String ptel) {
		Ptel = ptel;
	}
	public String getPemail() {
		return Pemail;
	}
	public void setPemail(String pemail) {
		Pemail = pemail;
	}
	public String getPremark() {
		return Premark;
	}
	public void setPremark(String premark) {
		Premark = premark;
	}
	
	
}
