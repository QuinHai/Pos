package com.quinhai.pos.DBUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.quinhai.pos.beans.*;

@Component("dbutil")
public class DBUtil {
	@Resource(name = "sessionFactory")
	private SessionFactory sf;

	public SessionFactory getSf() {
		return sf;
	}

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	/**
	 * 
	 * @param tablename表名
	 * @param columnname主键列名
	 * @return 获取id
	 */
	public String getId(String tablename, String columnname) {
		Session session = sf.openSession();
		String hql = "SELECT MAX(" + columnname + ") FROM " + tablename;
		Query<?> q = session.createQuery(hql);
		List<String> result = (List<String>) q.list();
		if (result.get(0) == null) {
			return "10001";
		}
		Integer id = Integer.valueOf(result.get(0));
		id++;
		session.close();
		return id.toString();
	}

	public List<?> getInfo(String hql) {
		Session session = sf.openSession();
		Query<?> q = session.createQuery(hql);
		List<?> list = q.list();
		session.close();
		return list;
	}

	/**
	 * 
	 * @param 查询元素内容
	 * @param 当前页数
	 * @param 一页显示元素的数量
	 * @return 查询到的元素
	 */
	public List<?> getPageContent(String hql, int page, int span) {
		Session session = sf.openSession();
		System.out.println(hql + "\n" + "Page:" + page);

		Query<?> q = session.createQuery(hql);
		List list = q.list();
		List temp = new ArrayList<>();
		int i = 0;
		while ((page - 1) * span + i < list.size() && i < span) {
			temp.add(list.get((page - 1) * span + i));
			i++;
		}
		session.close();
		return temp;
	}

	/**
	 * 
	 * @param 查询元素的数量
	 * @param 一页显示元素的数量
	 * @return 一共显示页数
	 */
	public int getTotalPage(String hql, int span) {
		Session session = sf.openSession();
		Query<?> q = session.createQuery(hql);
		List<Long> list = (List<Long>) q.list();
		int count = (list.get(0)).intValue();
		int page = (count % span == 0) ? (count / span) : (count / span + 1);
		session.close();
		return page;
	}

	public List<?> getGoodsClass() {
		String hql = "SELECT GCname FROM GoodsClassInfo";
		return getInfo(hql);
	}

	public List<String> getProvider() {
		String hql = "SELECT Pname FROM ProviderInfo";
		return (List<String>) getInfo(hql);
	}

	public List<String> getGoods() {
		String hql = "SELECT Gname FROM GoodsInfo";
		return (List<String>) getInfo(hql);
	}

	public List<String> getConsumer() {
		String hql = "SELECT Cname FROM ConsumerInfo"; // 得到搜索客户名字的hql
		return (List<String>) getInfo(hql); // 返回
	}

	public List<String> getAdmin() {
		String hql = "SELECT Aname FROM AdminInfo"; // 得到搜索管理员名字的hql
		return (List<String>) getInfo(hql); // 返回
	}

	public void updateTotalprice(String name, String id) {
		Session session = sf.openSession();
		session.beginTransaction();
		if (name.equals("StockInfo")) {
			String hql = "SELECT SUM(sd.SDtotalprice) FROM" + " StockDetail AS sd WHERE sd.Sid = '" + id + "'";
			List<Double> list = (List<Double>) getInfo(hql);
			StockInfo si = (StockInfo) session.get(StockInfo.class, id);
			si.setStotalprice(list.get(0));
			session.save(si);
		} else if (name.equals("SellInfo")) {
			String hql = "SELECT SUM(ed.EDtotalprice) FROM" + " SellDetail AS ed WHERE ed.Eid = '" + id + "'";
			List<Double> list = (List<Double>) getInfo(hql);
			SellInfo ei = (SellInfo) session.get(SellInfo.class, id);
			ei.setEtotalprice(list.get(0));
			session.save(ei);
		}
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * 
	 * @param 表名
	 * @param 获取对象的主键
	 * @return 查找到的对象
	 */
	public Object getObject(String tablename, String id) {
		Session session = sf.openSession();
		Object obj = null;
		if (tablename.equals("GoodsInfo")) {
			obj = session.get(GoodsInfo.class, id);
		} else if (tablename.equals("GoodsClassInfo")) {
			obj = session.get(GoodsClassInfo.class, id);
		} else if (tablename.equals("ConsumerInfo")) {
			obj = session.get(ConsumerInfo.class, id);
		} else if (tablename.equals("ProviderInfo")) {
			obj = session.get(ProviderInfo.class, id);
		} else if (tablename.equals("StockInfo")) {
			obj = session.get(StockInfo.class, id);
		} else if (tablename.equals("StockDetail")) {
			obj = session.get(StockDetail.class, id);
		} else if (tablename.equals("SellInfo")) {
			obj = session.get(SellInfo.class, id);
		} else if (tablename.equals("SellDetail")) {
			obj = session.get(SellDetail.class, id);
		} else if (tablename.equals("ConsumerBack")) {
			obj = session.get(ConsumerBack.class, id);
		} else if (tablename.equals("ProviderBack")) {
			obj = session.get(ProviderBack.class, id);
		} else if (tablename.equals("ConsumerBackDetail")) {
			obj = session.get(ConsumerBackDetail.class, id);
		} else if (tablename.equals("ProviderBackDetail")) {
			obj = session.get(ProviderBackDetail.class, id);
		}else if(tablename.equals("AdminInfo")){
			obj = session.get(AdminInfo.class,id);
		}
		// else ...
		session.close();
		return obj;
	}

}
