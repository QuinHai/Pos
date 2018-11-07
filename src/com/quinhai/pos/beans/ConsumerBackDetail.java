package com.quinhai.pos.beans;

import org.springframework.stereotype.Component;

@Component("consumerBackDetail")
public class ConsumerBackDetail {
	private String CBDid;
	private String CBid;
	private String Gid;
	private Integer CBDamount;
	private Double CBDprice;
	private Double CBDtotalprice;

	public ConsumerBackDetail() {

	}

	public ConsumerBackDetail(String cBDid, String cBid, String gid, Integer cBDamount, Double cBDprice,
			Double cBDtotalprice) {
		super();
		CBDid = cBDid;
		CBid = cBid;
		Gid = gid;
		CBDamount = cBDamount;
		CBDprice = cBDprice;
		CBDtotalprice = cBDtotalprice;
	}

	public String getCBDid() {
		return CBDid;
	}

	public void setCBDid(String cBDid) {
		CBDid = cBDid;
	}

	public String getCBid() {
		return CBid;
	}

	public void setCBid(String cBid) {
		CBid = cBid;
	}

	public String getGid() {
		return Gid;
	}

	public void setGid(String gid) {
		Gid = gid;
	}

	public Integer getCBDamount() {
		return CBDamount;
	}

	public void setCBDamount(Integer cBDamount) {
		CBDamount = cBDamount;
	}

	public Double getCBDprice() {
		return CBDprice;
	}

	public void setCBDprice(Double cBDprice) {
		CBDprice = cBDprice;
	}

	public Double getCBDtotalprice() {
		return CBDtotalprice;
	}

	public void setCBDtotalprice(Double cBDtotalprice) {
		CBDtotalprice = cBDtotalprice;
	}

}
