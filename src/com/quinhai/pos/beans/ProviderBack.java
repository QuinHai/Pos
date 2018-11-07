package com.quinhai.pos.beans;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component("providerBack")
public class ProviderBack {
	private String PBid;
	private String Pid;
	private String Sid;
	private Date PBdate;

	public ProviderBack() {

	}

	public ProviderBack(String pBid, String pid, String sid, Date pBdate) {
		super();
		PBid = pBid;
		Pid = pid;
		Sid = sid;
		PBdate = pBdate;
	}

	public String getPBid() {
		return PBid;
	}

	public void setPBid(String pBid) {
		PBid = pBid;
	}

	public String getPid() {
		return Pid;
	}

	public void setPid(String pid) {
		Pid = pid;
	}

	public String getSid() {
		return Sid;
	}

	public void setSid(String sid) {
		Sid = sid;
	}

	public Date getPBdate() {
		return PBdate;
	}

	public void setPBdate(Date pBdate) {
		PBdate = pBdate;
	}

}
