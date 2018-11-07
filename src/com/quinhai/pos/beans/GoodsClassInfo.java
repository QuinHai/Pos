package com.quinhai.pos.beans;

import org.springframework.stereotype.Component;

@Component("goodsClassInfo")
public class GoodsClassInfo {
	private String GCid;
	private String GCname;
	
	public GoodsClassInfo(String gcid2, String gcname2) {
		GCid = gcid2;
		GCname = gcname2;
	}
	
	public GoodsClassInfo() {
		
	}
	
	
	public String getGCid() {
		return GCid;
	}
	public void setGCid(String gCid) {
		GCid = gCid;
	}
	public String getGCname() {
		return GCname;
	}
	public void setGCname(String gCname) {
		GCname = gCname;
	}
	
	
}
