package com.quinhai.pos.beans;

import org.springframework.stereotype.Component;

@Component("sellDetail")
public class SellDetail {
	private String EDid;
	private String Eid;
	private String Gid;
	private Integer EDamount;
	private Double EDprice;
	private Double EDtotalprice;

	public SellDetail() {

	}

	public SellDetail(String eDid, String eid, String gid, Integer eDamount, Double eDprice, Double eDtotalprice) {
		super();
		EDid = eDid;
		Eid = eid;
		Gid = gid;
		EDamount = eDamount;
		EDprice = eDprice;
		EDtotalprice = eDtotalprice;
	}

	public String getEDid() {
		return EDid;
	}

	public void setEDid(String eDid) {
		EDid = eDid;
	}

	public String getEid() {
		return Eid;
	}

	public void setEid(String eid) {
		Eid = eid;
	}

	public String getGid() {
		return Gid;
	}

	public void setGid(String gid) {
		Gid = gid;
	}

	public Integer getEDamount() {
		return EDamount;
	}

	public void setEDamount(Integer eDamount) {
		EDamount = eDamount;
	}

	public Double getEDprice() {
		return EDprice;
	}

	public void setEDprice(Double eDprice) {
		EDprice = eDprice;
	}

	public Double getEDtotalprice() {
		return EDtotalprice;
	}

	public void setEDtotalprice(Double eDtotalprice) {
		EDtotalprice = eDtotalprice;
	}

}
