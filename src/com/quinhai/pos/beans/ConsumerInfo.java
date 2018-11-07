package com.quinhai.pos.beans;

import org.springframework.stereotype.Component;

@Component("consumerInfo")
public class ConsumerInfo {
	private String Cid;
	private String Cname;
	private String Clinkman;
	private String Caddress;
	private String Ctel;
	private String Cemail;
	private String Cremark;
	
	public ConsumerInfo() {
		
	}
	
	public ConsumerInfo(String cid, String cname, String clinkman, String caddress, String ctel, String cemail,
			String cremark) {
		Cid = cid;
		Cname = cname;
		Clinkman = clinkman;
		Caddress = caddress;
		Ctel = ctel;
		Cemail = cemail;
		Cremark = cremark;
	}



	public String getCid() {
		return Cid;
	}

	public void setCid(String cid) {
		Cid = cid;
	}

	public String getCname() {
		return Cname;
	}

	public void setCname(String cname) {
		Cname = cname;
	}

	public String getClinkman() {
		return Clinkman;
	}

	public void setClinkman(String clinkman) {
		Clinkman = clinkman;
	}

	public String getCaddress() {
		return Caddress;
	}

	public void setCaddress(String caddress) {
		Caddress = caddress;
	}

	public String getCtel() {
		return Ctel;
	}

	public void setCtel(String ctel) {
		Ctel = ctel;
	}

	public String getCemail() {
		return Cemail;
	}

	public void setCemail(String cemail) {
		Cemail = cemail;
	}

	public String getCremark() {
		return Cremark;
	}

	public void setCremark(String cremark) {
		Cremark = cremark;
	}

}
