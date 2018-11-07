package com.quinhai.pos.beans;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component("consumerBack")
public class ConsumerBack {
	private String CBid;
	private String Cid;
	private String Eid;
	private Date CBdate;
	
	public ConsumerBack() {
		
	}
	
	public ConsumerBack(String cBid, String cid, String eid, Date cBdate) {
		super();
		CBid = cBid;
		Cid = cid;
		Eid = eid;
		CBdate = cBdate;
	}
	public String getCBid() {
		return CBid;
	}
	public void setCBid(String cBid) {
		CBid = cBid;
	}
	public String getCid() {
		return Cid;
	}
	public void setCid(String cid) {
		Cid = cid;
	}
	public String getEid() {
		return Eid;
	}
	public void setEid(String eid) {
		Eid = eid;
	}
	public Date getCBdate() {
		return CBdate;
	}
	public void setCBdate(Date cBdate) {
		CBdate = cBdate;
	}
	
	
}
