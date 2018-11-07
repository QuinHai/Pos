package com.quinhai.pos.beans;

import org.springframework.stereotype.Component;

@Component("providerBackDetail")
public class ProviderBackDetail {
	private String PBDid;
	private String PBid;
	private String Gid;
	private Integer PBDamount;
	private Double PBDprice;
	private Double PBDtotalprice;

	public ProviderBackDetail() {

	}

	public ProviderBackDetail(String pBDid, String pBid, String gid, Integer pBDamount, Double pBDprice,
			Double pBDtotalprice) {
		super();
		PBDid = pBDid;
		PBid = pBid;
		Gid = gid;
		PBDamount = pBDamount;
		PBDprice = pBDprice;
		PBDtotalprice = pBDtotalprice;
	}

	public String getPBDid() {
		return PBDid;
	}

	public void setPBDid(String pBDid) {
		PBDid = pBDid;
	}

	public String getPBid() {
		return PBid;
	}

	public void setPBid(String pBid) {
		PBid = pBid;
	}

	public String getGid() {
		return Gid;
	}

	public void setGid(String gid) {
		Gid = gid;
	}

	public Integer getPBDamount() {
		return PBDamount;
	}

	public void setPBDamount(Integer pBDamount) {
		PBDamount = pBDamount;
	}

	public Double getPBDprice() {
		return PBDprice;
	}

	public void setPBDprice(Double pBDprice) {
		PBDprice = pBDprice;
	}

	public Double getPBDtotalprice() {
		return PBDtotalprice;
	}

	public void setPBDtotalprice(Double pBDtotalprice) {
		PBDtotalprice = pBDtotalprice;
	}

}
