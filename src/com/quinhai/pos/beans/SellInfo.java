package com.quinhai.pos.beans;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component("sellInfo")
public class SellInfo {
	private String Eid;
	private String Cid;
	private Date Edate;
	private String Eseller;
	private Double Etotalprice;

	public SellInfo() {
		
	}
	
	public SellInfo(String eid, String cid, Date edate, String eseller, Double etotalprice) {
		super();
		Eid = eid;
		Cid = cid;
		Edate = edate;
		Eseller = eseller;
		Etotalprice = etotalprice;
	}

	public String getEid() {
		return Eid;
	}

	public void setEid(String eid) {
		Eid = eid;
	}

	public String getCid() {
		return Cid;
	}

	public void setCid(String cid) {
		Cid = cid;
	}

	public Date getEdate() {
		return Edate;
	}

	public void setEdate(Date edate) {
		Edate = edate;
	}

	public String getEseller() {
		return Eseller;
	}

	public void setEseller(String eseller) {
		Eseller = eseller;
	}

	public Double getEtotalprice() {
		return Etotalprice;
	}

	public void setEtotalprice(Double etotalprice) {
		Etotalprice = etotalprice;
	}

}
