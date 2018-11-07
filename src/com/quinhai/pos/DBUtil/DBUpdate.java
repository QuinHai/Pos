package com.quinhai.pos.DBUtil;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.quinhai.pos.beans.*;

@Component("dbupdate")
public class DBUpdate {
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

	public void updateTable(String tablename, Object obj, String id) {
		Session session = sf.openSession();
		session.beginTransaction();

		if (tablename.equals("GoodsInfo")) {
			GoodsInfo value = (GoodsInfo) obj;
			GoodsInfo gi = (GoodsInfo) session.get(GoodsInfo.class, id);
			if (gi == null)
				gi.setGname(value.getGname());
			gi.setGCid(value.getGCid());
			gi.setGunit(value.getGunit());
			gi.setGpin(value.getGpin());
			gi.setGpout(value.getGpout());
			gi.setGamount(value.getGamount());
			session.save(gi);
		} else if (tablename.equals("GoodsClassInfo")) {
			GoodsClassInfo value = (GoodsClassInfo) obj;
			GoodsClassInfo gci = (GoodsClassInfo) session.get(GoodsClassInfo.class, id);
			gci.setGCname(value.getGCname());
			session.save(gci);
		} else if (tablename.equals("ConsumerInfo")) {
			ConsumerInfo value = (ConsumerInfo) obj;
			ConsumerInfo ci = (ConsumerInfo) session.get(ConsumerInfo.class, id);
			ci.setCid(value.getCid());
			ci.setCaddress(value.getCaddress());
			ci.setCemail(value.getCemail());
			ci.setClinkman(value.getClinkman());
			ci.setCname(value.getCname());
			ci.setCremark(value.getCremark());
			ci.setCtel(value.getCtel());
			session.save(ci);
		} else if (tablename.equals("ProviderInfo")) {
			ProviderInfo value = (ProviderInfo) obj;
			ProviderInfo pi = (ProviderInfo) session.get(ProviderInfo.class, id);
			pi.setPid(value.getPid());
			pi.setPaddress(value.getPaddress());
			pi.setPemail(value.getPemail());
			pi.setPlinkman(value.getPlinkman());
			pi.setPname(value.getPname());
			pi.setPremark(value.getPremark());
			pi.setPtel(value.getPtel());
			session.save(pi);
		} else if (tablename.equals("StockInfo")) {
			StockInfo value = (StockInfo) obj;
			StockInfo si = (StockInfo) session.get(StockInfo.class, id);
			si.setPid(value.getPid());
			si.setSdate(value.getSdate());
			si.setSbuyer(value.getSbuyer());
			session.save(si);
		} else if (tablename.equals("StockDetail")) {
			StockDetail value = (StockDetail) obj;
			StockDetail sd = (StockDetail) session.get(StockDetail.class, id);

			GoodsInfo gi = (GoodsInfo) session.get(GoodsInfo.class, sd.getGid());
			gi.setGamount(gi.getGamount() - sd.getSDamount() + value.getSDamount());
			session.save(gi);

			sd.setSDamount(value.getSDamount());
			sd.setSDtotalprice(sd.getSDamount() * sd.getSDprice());
			session.save(sd);
			session.getTransaction().commit();
			db.updateTotalprice("StockInfo", sd.getSid());
			session.beginTransaction();
		} else if (tablename.equals("SellInfo")) {
			SellInfo value = (SellInfo) obj;
			SellInfo ei = (SellInfo) session.get(SellInfo.class, id);
			ei.setCid(value.getCid());
			ei.setEdate(value.getEdate());
			ei.setEseller(value.getEseller());
			session.save(ei);
		} else if (tablename.equals("SellDetail")) {
			SellDetail value = (SellDetail) obj;
			SellDetail ed = (SellDetail) session.get(SellDetail.class, id);

			GoodsInfo gi = (GoodsInfo) session.get(GoodsInfo.class, ed.getGid());
			gi.setGamount(gi.getGamount() + ed.getEDamount() - value.getEDamount());
			session.save(gi);

			ed.setEDamount(value.getEDamount());
			ed.setEDtotalprice(ed.getEDamount() * ed.getEDprice());
			session.save(ed);
			session.getTransaction().commit();
			db.updateTotalprice("SellInfo", ed.getEid());
			session.beginTransaction();
		} else if (tablename.equals("ConsumerBackDetail")) {
			ConsumerBackDetail cbd = (ConsumerBackDetail) session.get(ConsumerBackDetail.class, id);
			ConsumerBackDetail temp = (ConsumerBackDetail) obj; // 强制类型转换
			GoodsInfo gi = (GoodsInfo) session.get(GoodsInfo.class, cbd.getGid()); // 得到销售商品对象
			gi.setGamount(gi.getGamount() - cbd.getCBDamount() + temp.getCBDamount()); // 修改商品数量
			session.save(gi); // 保存商品对象
			ConsumerBack cb = (ConsumerBack) session.get(ConsumerBack.class, cbd.getCBid()); // 得到退货对象
			String hql = "from SellDetail as ed where ed.Eid='" + cb.getEid() + "' and ed.Gid='" + cbd.getGid() + "'"; // 搜索销售对象的hql
			SellDetail sd = (SellDetail) db.getInfo(hql).get(0); // 得到销售对象
			SellDetail sdtemp = (SellDetail) session.get(SellDetail.class, sd.getEDid()); // 用get得到销售对象
			sdtemp.setEDamount(sdtemp.getEDamount() + cbd.getCBDamount() - temp.getCBDamount());// 修改销售数量
			sdtemp.setEDtotalprice(sdtemp.getEDamount() * sdtemp.getEDprice()); // 修改销售总价
			session.save(sdtemp); // 保存销售对象
			session.getTransaction().commit();
			db.updateTotalprice("SellInfo", sdtemp.getEid());
			session.beginTransaction();
			cbd.setCBDamount(temp.getCBDamount()); // 修改明细数量
			cbd.setCBDtotalprice(cbd.getCBDamount() * cbd.getCBDprice()); // 修改明细总价
			session.save(cbd);
		} else if (tablename.equals("ProviderBackDetail")) {
			ProviderBackDetail pbd = (ProviderBackDetail) session.get(ProviderBackDetail.class, id);
			ProviderBackDetail temp = (ProviderBackDetail) obj; // 强制类型转换
			GoodsInfo gi = (GoodsInfo) session.get(GoodsInfo.class, pbd.getGid()); // 得到销售商品对象
			gi.setGamount(gi.getGamount() - pbd.getPBDamount() + temp.getPBDamount()); // 修改商品数量
			session.save(gi); // 保存商品对象
			ProviderBack pb = (ProviderBack) session.get(ProviderBack.class, pbd.getPBid()); // 得到退货对象
			String hql = "from StockDetail as ed where ed.Eid='" + pb.getSid() + "' and ed.Gid='" + pbd.getGid() + "'"; // 搜索销售对象的hql
			StockDetail sd = (StockDetail) db.getInfo(hql).get(0); // 得到销售对象
			StockDetail sdtemp = (StockDetail) session.get(StockDetail.class, sd.getSDid()); // 用get得到销售对象
			sdtemp.setSDamount(sdtemp.getSDamount() + pbd.getPBDamount() - temp.getPBDamount());// 修改销售数量
			sdtemp.setSDtotalprice(sdtemp.getSDamount() * sdtemp.getSDprice()); // 修改销售总价
			session.save(sdtemp); // 保存销售对象
			session.getTransaction().commit();
			db.updateTotalprice("StockInfo", sdtemp.getSid());
			session.beginTransaction();
			pbd.setPBDamount(temp.getPBDamount()); // 修改明细数量
			pbd.setPBDtotalprice(pbd.getPBDamount() * pbd.getPBDprice()); // 修改明细总价
			session.save(pbd);
		} else if (tablename.equals("AdminInfo")) {
			AdminInfo ai = (AdminInfo) session.get(AdminInfo.class, id);
			AdminInfo temp = (AdminInfo) obj;
			ai.setApwd(temp.getApwd());
			session.save(ai);
		}
		session.getTransaction().commit();
		session.close();
	}
}
