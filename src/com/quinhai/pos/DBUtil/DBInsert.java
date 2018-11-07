package com.quinhai.pos.DBUtil;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.quinhai.pos.beans.*;

@Component("dbinsert")
public class DBInsert {
	@Resource(name = "sessionFactory")
	private SessionFactory sf;
	@Resource(name = "dbutil")
	private DBUtil db;

	public SessionFactory getSf() {
		return sf;
	}

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	public DBUtil getDb() {
		return db;
	}

	public void setDb(DBUtil db) {
		this.db = db;
	}

	public void insertTable(String tablename, Object value) {
		Session session = sf.openSession();
		session.beginTransaction(); // 开启事务

		if (tablename.equals("GoodsInfo")) {
			GoodsInfo gi = (GoodsInfo) value;
			session.save(gi);
		} else if (tablename.equals("GoodsClassInfo")) {
			GoodsClassInfo gci = (GoodsClassInfo) value;
			session.save(gci);
		} else if (tablename.equals("ConsumerInfo")) {
			ConsumerInfo ci = (ConsumerInfo) value;
			session.save(ci);
		} else if (tablename.equals("ProviderInfo")) {
			ProviderInfo pi = (ProviderInfo) value;
			session.save(pi);
		} else if (tablename.equals("StockInfo")) {
			StockInfo si = (StockInfo) value;
			session.save(si);
		} else if (tablename.equals("StockDetail")) {
			StockDetail sd = (StockDetail) value;
			String hql = "FROM StockDetail AS sd WHERE sd.Gid = '" + sd.getGid() + "' AND sd.Sid = '" + sd.getSid()
					+ "'";
			List<StockDetail> list = (List<StockDetail>) db.getInfo(hql);
			if (!list.isEmpty()) {
				GoodsInfo gi = (GoodsInfo) session.get(GoodsInfo.class, list.get(0).getSDid());
				gi.setGamount(gi.getGamount() + sd.getSDamount());
				session.save(gi);
				StockDetail sdtemp = (StockDetail) session.get(StockDetail.class, list.get(0).getSDid());
				sdtemp.setSDamount(sdtemp.getSDamount() + sd.getSDamount());
				sdtemp.setSDtotalprice(sdtemp.getSDamount() * sdtemp.getSDprice());
				session.save(sdtemp);
			} else {
				session.save(sd);
			}
			session.getTransaction().commit();
			db.updateTotalprice("StockInfo", sd.getSid());
			session.beginTransaction();
		} else if (tablename.equals("SellInfo")) {
			SellInfo si = (SellInfo) value;
			session.save(si);
		} else if (tablename.equals("SellDetail")) {
			SellDetail ed = (SellDetail) value;
			String hql = "FROM SellDetail AS ed WHERE ed.Gid = '" + ed.getGid() + "' AND ed.Eid = '" + ed.getEid()
					+ "'";
			List<SellDetail> list = (List<SellDetail>) db.getInfo(hql);
			if (!list.isEmpty()) {
				GoodsInfo gi = (GoodsInfo) session.get(GoodsInfo.class, list.get(0).getEDid());
				gi.setGamount(gi.getGamount() - ed.getEDamount());
				session.save(gi);
				SellDetail edtemp = (SellDetail) session.get(SellDetail.class, list.get(0).getEDid());
				edtemp.setEDamount(edtemp.getEDamount() + ed.getEDamount());
				edtemp.setEDtotalprice(edtemp.getEDamount() * edtemp.getEDprice());
				session.save(edtemp);
			} else {
				session.save(ed);
			}
			session.getTransaction().commit();
			db.updateTotalprice("SellInfo", ed.getEid());
			session.beginTransaction();
		} else if (tablename.equals("ConsumerBack")) {
			ConsumerBack cb = (ConsumerBack) value;
			session.save(value);
		} else if (tablename.equals("ProviderBack")) {
			ProviderBack cb = (ProviderBack) value;
			session.save(value);
		} else if (tablename.equals("ConsumerBackDetail")) {
			ConsumerBackDetail cbd = (ConsumerBackDetail) value;
			ConsumerBack cb = (ConsumerBack) session.get(ConsumerBack.class, cbd.getCBid());
			String temp = "FROM SellDetail AS ed WHERE ed.Eid='" + cb.getEid() + "' AND ed.Gid='" + cbd.getGid() + "'";
			SellDetail sd = ((List<SellDetail>) db.getInfo(temp)).get(0);
			String hql = "FROM ConsumerBackDetail AS cbd WHERE cbd.Gid='" + cbd.getGid() + "' and cbd.CBid='"
					+ cbd.getCBid() + "'";
			List<ConsumerBackDetail> list = (List<ConsumerBackDetail>)db.getInfo(hql);		

			GoodsInfo gi = (GoodsInfo)session.get(GoodsInfo.class, cbd.getGid());
			
			gi.setGamount(gi.getGamount()+ cbd.getCBDamount());
			session.save(gi);
			SellDetail sdtemp = (SellDetail)session.get(SellDetail.class,sd.getEDid());
			sdtemp.setEDamount(sdtemp.getEDamount()-cbd.getCBDamount());
			sdtemp.setEDtotalprice(sdtemp.getEDamount()*sdtemp.getEDprice());
			session.save(sdtemp);
			session.getTransaction().commit();
			db.updateTotalprice("SellInfo", sd.getEid());
			session.beginTransaction();
			if(!list.isEmpty()){				
				ConsumerBackDetail cbdtemp = 
					(ConsumerBackDetail)session.get(ConsumerBackDetail.class,list.get(0).getCBDid());
				cbdtemp.setCBDamount(cbdtemp.getCBDamount()+cbd.getCBDamount());
				cbdtemp.setCBDtotalprice(cbdtemp.getCBDamount()*cbdtemp.getCBDprice());			
				session.save(cbdtemp);
			}
			else{				
				session.save(cbd);			
			}		
		}else if (tablename.equals("ProviderBackDetail")) {
			ProviderBackDetail pbd = (ProviderBackDetail) value;
			ProviderBack pb = (ProviderBack) session.get(ProviderBack.class, pbd.getPBid());
			String temp = "FROM StockDetail AS ed WHERE ed.Eid='" + pb.getSid() + "' AND ed.Gid='" + pbd.getGid() + "'";
			StockDetail sd = ((List<StockDetail>) db.getInfo(temp)).get(0);
			String hql = "FROM ProviderBackDetail AS pbd WHERE pbd.Gid='" + pbd.getGid() + "' and pbd.PBid='"
					+ pbd.getPBid() + "'";
			List<ProviderBackDetail> list = (List<ProviderBackDetail>)db.getInfo(hql);		

			GoodsInfo gi = (GoodsInfo)session.get(GoodsInfo.class, pbd.getGid());
			
			gi.setGamount(gi.getGamount()+ pbd.getPBDamount());
			session.save(gi);
			StockDetail sdtemp = (StockDetail)session.get(StockDetail.class,sd.getSDid());
			sdtemp.setSDamount(sdtemp.getSDamount()-pbd.getPBDamount());
			sdtemp.setSDtotalprice(sdtemp.getSDamount()*sdtemp.getSDprice());
			session.save(sdtemp);
			session.getTransaction().commit();
			db.updateTotalprice("StockInfo", sd.getSid());
			session.beginTransaction();
			if(!list.isEmpty()){				
				ProviderBackDetail pbdtemp = 
					(ProviderBackDetail)session.get(ProviderBackDetail.class,list.get(0).getPBDid());
				pbdtemp.setPBDamount(pbdtemp.getPBDamount()+pbd.getPBDamount());
				pbdtemp.setPBDtotalprice(pbdtemp.getPBDamount()*pbdtemp.getPBDprice());			
				session.save(pbdtemp);
			}
			else{				
				session.save(pbd);			
			}	
		}else if(tablename.equals("AdminInfo")){					//添加管理员对象时
			AdminInfo ai = (AdminInfo)value;						//强制类型转换
			session.save(ai);										//保存对象
		}

		session.getTransaction().commit(); // 提交事务
		session.close();
	}
}
