package com.quinhai.pos.beans;

import org.springframework.stereotype.Component;

@Component("stockDetail")
public class StockDetail {
	private String SDid;
	private String Sid;
	private String Gid;
	private Integer SDamount;
	private Double SDprice;
	private Double SDtotalprice;

	public StockDetail() {

	}

	public StockDetail(String sDid, String sid, String gid, Integer sDamount, Double sDprice, Double sDtotalprice) {
		super();
		SDid = sDid;
		Sid = sid;
		Gid = gid;
		SDamount = sDamount;
		SDprice = sDprice;
		SDtotalprice = sDtotalprice;
	}

	public String getSDid() {
		return SDid;
	}

	public void setSDid(String sDid) {
		SDid = sDid;
	}

	public String getSid() {
		return Sid;
	}

	public void setSid(String sid) {
		Sid = sid;
	}

	public String getGid() {
		return Gid;
	}

	public void setGid(String gid) {
		Gid = gid;
	}

	public Integer getSDamount() {
		return SDamount;
	}

	public void setSDamount(Integer sDamount) {
		SDamount = sDamount;
	}

	public Double getSDprice() {
		return SDprice;
	}

	public void setSDprice(Double sDprice) {
		SDprice = sDprice;
	}

	public Double getSDtotalprice() {
		return SDtotalprice;
	}

	public void setSDtotalprice(Double sDtotalprice) {
		SDtotalprice = sDtotalprice;
	}

}
