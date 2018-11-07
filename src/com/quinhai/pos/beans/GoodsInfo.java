package com.quinhai.pos.beans;

import org.springframework.stereotype.Component;

@Component("goodsInfo")
public class GoodsInfo {
	private String Gid;
	private String Gname;
	private String GCid;
	private String Gunit;
	private Double Gpin;
	private Double Gpout;
	private Integer Gamount;

	public GoodsInfo() {

	}

	public GoodsInfo(String gid2, String gname2, String gcid2, String gunit2, double gpin2, double gpout2,
			int gamount2) {
		Gid = gid2;
		Gname = gname2;
		GCid = gcid2;
		Gunit = gunit2;
		Gpin = gpin2;
		Gpout = gpout2;
		Gamount = gamount2;
	}

	public String getGid() {
		return Gid;
	}

	public void setGid(String gid) {
		Gid = gid;
	}

	public String getGname() {
		return Gname;
	}

	public void setGname(String gname) {
		Gname = gname;
	}

	public String getGCid() {
		return GCid;
	}

	public void setGCid(String gCid) {
		GCid = gCid;
	}

	public String getGunit() {
		return Gunit;
	}

	public void setGunit(String gunit) {
		Gunit = gunit;
	}

	public Double getGpin() {
		return Gpin;
	}

	public void setGpin(Double gpin) {
		Gpin = gpin;
	}

	public Double getGpout() {
		return Gpout;
	}

	public void setGpout(Double gpout) {
		Gpout = gpout;
	}

	public Integer getGamount() {
		return Gamount;
	}

	public void setGamount(Integer gamount) {
		Gamount = gamount;
	}

}
