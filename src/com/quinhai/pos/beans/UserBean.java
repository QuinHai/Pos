package com.quinhai.pos.beans;

import org.springframework.stereotype.Component;

@Component("userBean")
public class UserBean {
	// 每页显示元素数量
	private Integer span = 8;
	// 总页数
	private Integer totalPage = 1;
	// 当前页数
	private Integer nowPage = 1;
	// 记录搜索内容//在此为默认
	private String hql = "";
	// 获取搜索内容总数//在此为默认
	private String pageHql = "";

	public Integer getSpan() {
		return span;
	}

	public void setSpan(Integer span) {
		this.span = span;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getNowPage() {
		return nowPage;
	}

	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public String getPageHql() {
		return pageHql;
	}

	public void setPageHql(String pageHql) {
		this.pageHql = pageHql;
	}

}
