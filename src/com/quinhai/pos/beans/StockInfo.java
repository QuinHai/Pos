package com.quinhai.pos.beans;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component("stockInfo")
public class StockInfo {
	private String Sid;
	private String Pid;
	private Date Sdate;
	private String Sbuyer;
	private Double Stotalprice;
	
	
	public StockInfo() {
		
	}
	
	
	
	public StockInfo(String sid, String pid, Date sdate, String sbuyer, Double stotalprice) {
		super();
		Sid = sid;
		Pid = pid;
		Sdate = sdate;
		Sbuyer = sbuyer;
		Stotalprice = stotalprice;
	}



	public String getSid() {
		return Sid;
	}
	public void setSid(String sid) {
		Sid = sid;
	}
	public String getPid() {
		return Pid;
	}
	public void setPid(String pid) {
		Pid = pid;
	}
	public Date getSdate() {
		return Sdate;
	}
	public void setSdate(Date sdate) {
		Sdate = sdate;
	}
	public String getSbuyer() {
		return Sbuyer;
	}
	public void setSbuyer(String sbuyer) {
		Sbuyer = sbuyer;
	}
	public Double getStotalprice() {
		return Stotalprice;
	}
	public void setStotalprice(Double stotalprice) {
		Stotalprice = stotalprice;
	}
	
	
}
