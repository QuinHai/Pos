package com.quinhai.pos.DBUtil;

import javax.annotation.Resource;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.quinhai.pos.beans.*;

@Component("dbdelete")
public class DBDelete {
	@Resource(name = "sessionFactory")
	private SessionFactory sf;
	@Resource(name = "dbutil")
	private DBUtil db;

	public SessionFactory getSf() {
		return this.sf;
	}

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	public DBUtil getDb() {
		return this.db;
	}

	public void setDb(DBUtil db) {
		this.db = db;
	}
	
	public void deleteTable(String tablename, String id) {
		Session session = sf.openSession();
		session.beginTransaction();
		
		if(tablename.equals("GoodsInfo")) {
			GoodsInfo gi = (GoodsInfo)session.get(GoodsInfo.class, id);
			if(gi != null) {
				session.delete(gi);
			}
		}else if(tablename.equals("GoodsClassInfo")) {
			GoodsClassInfo gci = (GoodsClassInfo) session.get(GoodsClassInfo.class, id);
			if(gci != null) {
				session.delete(gci);
			}
		}else if(tablename.equals("ConsumerInfo")) {
			ConsumerInfo ci = (ConsumerInfo) session.get(ConsumerInfo.class, id);
			if(ci != null) {
				session.delete(ci);
			}
		}else if(tablename.equals("ProviderInfo")) {
			ProviderInfo pi = (ProviderInfo) session.get(ProviderInfo.class, id);
			if(pi != null) {
				session.delete(pi);
			}
		}else if(tablename.equals("StockInfo")) {
			StockInfo si = (StockInfo) session.get(StockInfo.class, id);
			if(si != null) {
				String hql = "FROM StockDetail AS sd WHERE sd.Sid = '" + id +"'";
				List<StockDetail> list = (List<StockDetail>)db.getInfo(hql);
				
				for(StockDetail sd:list) {
					GoodsInfo gi = (GoodsInfo)session.get(GoodsInfo.class, sd.getGid());
					gi.setGamount(gi.getGamount()-sd.getSDamount());
					session.save(gi);
					
					//删除采购记录的同时回复该条记录采购物品的数量
				}
				session.delete(si);
			}
		}else if(tablename.equals("SellInfo")) {
			SellInfo ei = (SellInfo) session.get(SellInfo.class, id);
			if(ei != null) {
				String hql = "FROM SellDetail AS ed WHERE ed.Eid = '" + id +"'";
				List<SellDetail> list = (List<SellDetail>)db.getInfo(hql);
				
				for(SellDetail ed:list) {
					GoodsInfo gi = (GoodsInfo)session.get(GoodsInfo.class, ed.getGid());
					gi.setGamount(gi.getGamount() + ed.getEDamount());
					session.save(gi);
					
					//删除采购记录的同时回复该条记录采购物品的数量
				}
				session.delete(ei);
			}
		}else if(tablename.equals("ConsumerBackDetail")) {
			ConsumerBackDetail cbd = (ConsumerBackDetail)session.get(ConsumerBackDetail.class, id);
			if(cbd != null) {
				ConsumerBack cb = (ConsumerBack)session.get(ConsumerBack.class, cbd.getCBid());
				String hql = "FROM SellDetail AS ed WHERE ed.Eid='" + cb.getEid() 
							+ "' AND ed.Gid='" + cbd.getGid() + "'";
				SellDetail temp = ((List<SellDetail>)db.getInfo(hql)).get(0);
				SellDetail sd = (SellDetail) session.get(SellDetail.class, temp.getEDid());
				sd.setEDamount(sd.getEDamount() + cbd.getCBDamount());
				sd.setEDtotalprice(sd.getEDamount()*sd.getEDprice());
				session.save(sd);
				GoodsInfo gi = (GoodsInfo)session.get(GoodsInfo.class, cbd.getGid());
				gi.setGamount(gi.getGamount()- cbd.getCBDamount());
				session.save(gi);
				session.delete(cbd);
				session.getTransaction().commit();
				db.updateTotalprice("SellInfo", sd.getEid());
				session.beginTransaction();
			}
		}else if(tablename.equals("ProviderBackDetail")) {
			ProviderBackDetail pbd = (ProviderBackDetail)session.get(ProviderBackDetail.class, id);
			if(pbd != null) {
				ProviderBack cb = (ProviderBack)session.get(ProviderBack.class, pbd.getPBid());
				String hql = "FROM ProviderDetail AS pd WHERE pd.Eid='" + cb.getSid() 
							+ "' AND ed.Gid='" + pbd.getGid() + "'";
				StockDetail temp = ((List<StockDetail>)db.getInfo(hql)).get(0);
				StockDetail sd = (StockDetail) session.get(StockDetail.class, temp.getSDid());
				sd.setSDamount(sd.getSDamount() + pbd.getPBDamount());
				sd.setSDtotalprice(sd.getSDamount()*sd.getSDprice());
				session.save(sd);
				GoodsInfo gi = (GoodsInfo)session.get(GoodsInfo.class, pbd.getGid());
				gi.setGamount(gi.getGamount()- pbd.getPBDamount());
				session.save(gi);
				session.delete(pbd);
				session.getTransaction().commit();
				db.updateTotalprice("StockInfo", sd.getSid());
				session.beginTransaction();
			}
		}else if(tablename.equals("ProviderBack")) {
			ProviderBack pb = (ProviderBack)session.get(ProviderBack.class, id);
			if(pb!=null) {
				String pbdtemp = "FROM ProviderBackDetail AS pbd WHERE pbd.PBid='" + pb.getPBid() + "'";
				List<ProviderBackDetail> list = (List<ProviderBackDetail>)db.getInfo(pbdtemp);
				for(ProviderBackDetail pbd:list) {
					String hql = "FROM StockDetail AS sd WHERE sd.Sid='" + pb.getSid() 
								+ "' AND sd.Gid='" + pbd.getGid() + "'";
					StockDetail temp = (StockDetail)db.getInfo(hql).get(0);
					StockDetail sd = (StockDetail)session.get(StockDetail.class, temp.getSDid());
					sd.setSDamount(sd.getSDamount() + pbd.getPBDamount());
					sd.setSDtotalprice(sd.getSDamount() * sd.getSDprice());
					session.save(sd);
					GoodsInfo gi = (GoodsInfo)session.get(GoodsInfo.class, pbd.getGid());
					gi.setGamount(gi.getGamount()- pbd.getPBDamount());
					session.save(gi);
					session.delete(pbd);
					session.getTransaction().commit();
					db.updateTotalprice("StockInfo", sd.getSid());
					session.beginTransaction();
				}
				session.delete(pb);
			}
		}else if(tablename.equals("ConsumerBack")) {
			ConsumerBack cb = (ConsumerBack)session.get(ConsumerBack.class, id);
			if(cb!=null) {
				String cbdtemp = "FROM ConsumerBackDetail AS cbd WHERE cbd.CBid='" + cb.getCBid() + "'";
				List<ConsumerBackDetail> list = (List<ConsumerBackDetail>)db.getInfo(cbdtemp);
				for(ConsumerBackDetail cbd:list) {
					String hql = "FROM SellDetail AS sd WHERE sd.Eid='" + cb.getEid() 
								+ "' AND sd.Gid='" + cbd.getGid() + "'";
					SellDetail temp = (SellDetail)db.getInfo(hql).get(0);
					SellDetail sd = (SellDetail)session.get(SellDetail.class, temp.getEDid());
					sd.setEDamount(sd.getEDamount() + cbd.getCBDamount());
					sd.setEDtotalprice(sd.getEDamount() * sd.getEDprice());
					session.save(sd);
					GoodsInfo gi = (GoodsInfo)session.get(GoodsInfo.class, cbd.getGid());
					gi.setGamount(gi.getGamount()- cbd.getCBDamount());
					session.save(gi);
					session.delete(cbd);
					session.getTransaction().commit();
					db.updateTotalprice("SellInfo", sd.getEid());
					session.beginTransaction();
				}
				session.delete(cb);
			}
		}else if(tablename.equals("AdminInfo")){										//删除管理员时
			AdminInfo ai = (AdminInfo)session.get(AdminInfo.class,id);					//得到管理员对象
			if(ai!=null){															//当对象不为空
				session.delete(ai);													//删除对象
			}
		}
		session.getTransaction().commit();
		session.close();
	}
}
