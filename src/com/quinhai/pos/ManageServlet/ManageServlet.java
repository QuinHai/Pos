package com.quinhai.pos.ManageServlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.quinhai.pos.DBUtil.*;
import com.quinhai.pos.beans.*;

public class ManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void jump(HttpServletRequest req, HttpServletResponse resp, String url)
			throws ServletException, IOException {
		ServletContext sc = getServletContext();// 得到上下文
		RequestDispatcher rd = sc.getRequestDispatcher(url);
		rd.forward(req, resp);// 页面跳转
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 设置编码格式
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		// 设置请求页面格式
		resp.setContentType("text/html;charset=utf-8");
		// 获取会话对象
		HttpSession session = req.getSession();

		UserBean userBean = (UserBean) session.getAttribute("userBean");
		if (userBean == null) {
			userBean = new UserBean();
		}
		// 获取当前servlet的IOC容器
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		DBUtil db = (DBUtil) wac.getBean("dbutil");
		DBInsert dbin = (DBInsert) wac.getBean("dbinsert");
		DBUpdate dbup = (DBUpdate) wac.getBean("dbupdate");
		DBDelete dbdel = (DBDelete) wac.getBean("dbdelete");
		// 获取页面隐藏域信息
		String action = req.getParameter("action").trim();

		/**
		 * 判断行为
		 */
		if (action.equals("login")) {// 登录动作
			System.out.println("========= Login =========");

			String aname = req.getParameter("uname").trim();
			String apwd = req.getParameter("upwd").trim();
			// 将用户名和密码转码
			aname = new String(aname.getBytes(), "UTF-8");
			apwd = new String(apwd.getBytes(), "UTF-8");

			String hql = "FROM AdminInfo as p " + "WHERE p.Aname = '" + aname + "' AND p.Apwd = '" + apwd + "'";
			List<AdminInfo> list = (List<AdminInfo>) db.getInfo(hql);

			// 页面跳转信息
			String url = "";
			if (!list.isEmpty()) {
				System.out.println("TRUE");
				AdminInfo ai = list.get(0);
				url = "/index.jsp";
				/*
				 * HttpSession in Servlet Not session in hibernate
				 */
				// 将登录者信息存入session
				session.setAttribute("admin", aname);
				session.setAttribute("alevel", ai.getAlevel());
			} else {
				System.out.println("FALSE");
				req.setAttribute("msg", "登录失败，请重试！");
				url = "/info.jsp";
			}
			jump(req, resp, url);
		} else if (action.equals("logout")) {
			req.getSession().invalidate();// 清除session中的信息
			resp.sendRedirect("adminlogin.jsp");// 重定向
		} else if (action.equals("search")) {// 搜索动作
			System.out.println("========= Search =========");

			String key = req.getParameter("key").trim(); // 得到搜索关键字
			String type = req.getParameter("type").trim(); // 得到搜索类型
			userBean.setNowPage(1);
			key = new String(key.getBytes(), "UTF-8");
			String hql = ""; // 记录搜索内容
			String tp = ""; // 获取搜索内容总数(totalPage)
			String url = ""; // 存放跳转地址

			System.out.println("=========" + type + "=========");

			if (type.equals("goodsinfo")) {
				String myradio = req.getParameter("myradio");
				if (myradio != null && myradio.trim().equals("class")) {
					hql = "FROM GoodsInfo AS gi WHERE gi.GCid IN" + " (SELECT gc.GCid FROM GoodsClassInfo AS gc"
							+ " WHERE gc.GCname LIKE '%" + key + "%')";
					tp = "SELECT COUNT(*) FROM GoodsInfo AS gi WHERE gi.GCid IN"
							+ "(SELECT gc.GCid FROM GoodsClassInfo AS gc" + " WHERE gc.GCname LIKE '%" + key + "%')";
				} else {
					hql = "FROM GoodsInfo WHERE Gname LIKE '%" + key + "%'";
					tp = "SELECT COUNT(*) FROM GoodsInfo WHERE Gname LIKE '%" + key + "%'";
				}
				url = "/goodsmanage.jsp";
			} else if (type.equals("goodsclassinfo")) {
				hql = "FROM GoodsClassInfo WHERE GCname like '%" + key + "%'";
				tp = "SELECT COUNT(*) FROM GoodsClassInfo WHERE GCname like '%" + key + "%'";
				url = "/goodsclassmanage.jsp";
			} else if (type.equals("consumerinfo")) {
				hql = "FROM ConsumerInfo WHERE Cname like '%" + key + "%'";
				tp = "SELECT COUNT(*) FROM ConsumerInfo WHERE Cname like '%" + key + "%'";
				url = "/consumermanage.jsp";
			} else if (type.equals("providerinfo")) {
				hql = "FROM ProviderInfo WHERE Pname like '%" + key + "%'";
				tp = "SELECT COUNT(*) FROM ProviderInfo WHERE Pname like '%" + key + "%'";
				url = "/providermanage.jsp";
			} else if (type.equals("stockinfo")) {
				hql = "FROM StockInfo WHERE Sid like '%" + key + "%'";
				tp = "SELECT COUNT(*) FROM StockInfo WHERE Sid like '%" + key + "%'";
				url = "/stockmanage.jsp";
			} else if (type.equals("sellinfo")) {
				hql = "FROM SellInfo WHERE Eid like '%" + key + "%'";
				tp = "SELECT COUNT(*) FROM SellInfo WHERE Eid like '%" + key + "%'";
				url = "/sellmanage.jsp";
			} else if (type.equals("admininfo")) {
				hql = "FROM AdminInfo WHERE Aname like '%" + key + "%'"; // 得到搜索对象的hql
				tp = "SELECT COUNT(*) FROM AdminInfo WHERE Aname like '%" + key + "%'";// 得到搜索总页数的hql
				url = "/adminmanage.jsp"; // 要跳转到的url
			} else if (type.equals("sta")) { // 库存统计
				String myradio = req.getParameter("myradio").trim();
				int gamount = Integer.parseInt(key);
				if (myradio.equals("more")) {
					hql = "FROM GoodsInfo AS gi WHERE gi.Gamount>=" + gamount;
					tp = "SELECT COUNT(*) FROM GoodsInfo AS gi WHERE gi.Gamount>=" + gamount;
				} else {
					hql = "FROM GoodsInfo AS gi WHERE gi.Gamount<=" + gamount;
					tp = "SELECT COUNT(*) FROM GoodsInfo AS gi WHERE gi.Gamount<=" + gamount;
				}
				url = "/statistic.jsp"; // 要跳转到的url
			} else if (type.equals("consumerback")) { // 客户退货
				hql = "FROM ConsumerBack AS cb WHERE cb.CBid like '%" + key + "%'";
				tp = "SELECT COUNT(*) FROM ConsumerBack AS cb WHERE cb.CBid like '%" + key + "%'";
				url = "/consumerbackmanage.jsp";
			} else if (type.equals("providerback")) {
				hql = "FROM ProviderBack AS pb WHERE pb.PBid like '%" + key + "%'";
				tp = "SELECT COUNT(*) FROM ProviderBack AS pb WHERE pb.PBid like '%" + key + "%'";
				url = "/providerbackmanage.jsp";
			}
			userBean.setHql(hql);
			userBean.setPageHql(tp);
			int totalPage = db.getTotalPage(tp, userBean.getSpan());
			userBean.setTotalPage(totalPage);
			// 获取查询内容并放入请求
			List<?> list = db.getPageContent(hql, userBean.getNowPage(), userBean.getSpan());
			req.setAttribute("goodslist", list);
			jump(req, resp, url);
		} else if (action.equals("changePage")) {
			String page = req.getParameter("page").trim(); // 要跳转到的页面
			String url = req.getParameter("pagename").trim(); // 页面名称
			userBean.setNowPage(Integer.parseInt(page));
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan());
			req.setAttribute("goodslist", goodslist);
			jump(req, resp, url);
		} else if (action.equals("addGoods")) {
			String gname = req.getParameter("gname").trim();
			String gcname = req.getParameter("gcname").trim();
			String gunit = req.getParameter("gunit").trim();
			String gpin = req.getParameter("gpin").trim();
			String gpout = req.getParameter("gpout").trim();
			String gamount = req.getParameter("gamount").trim();
			String gid = db.getId("GoodsInfo", "Gid");

			gname = new String(gname.getBytes(), "UTF-8");
			gcname = new String(gcname.getBytes(), "UTF-8");

			String hql = "SELECT gg.GCid FROM GoodsClassInfo AS gg WHERE gg.GCname='" + gcname + "'";
			String gcid = (String) (db.getInfo(hql).get(0));// 获取当前类别的id

			String temp = "FROM GoodsInfo AS gi WHERE gi.Gname='" + gname + "'";
			List<?> list = db.getInfo(temp);// 查重，若未存在gname则list为空
			String url = "";
			if (list.isEmpty()) {
				gunit = new String(gunit.getBytes(), "UTF-8");
				double pin = Double.parseDouble(gpin);
				double pout = Double.parseDouble(gpout);
				int amount = Integer.parseInt(gamount);

				GoodsInfo gi = new GoodsInfo(gid, gname, gcid, gunit, pin, pout, amount);
				dbin.insertTable("GoodsInfo", gi);// 储存新的货物信息

				int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
				userBean.setTotalPage(totalPage);// 更新当前总页数

				// 更新商品列表
				List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan());

				req.setAttribute("goodslist", goodslist);
				url = "/goodsmanage.jsp";
			} else {
				url = "/info.jsp";
				String msg = "该物品已存在";
				req.setAttribute("msg", msg);
			}
			jump(req, resp, url);
		} else if (action.equals("lookGoods")) {
			System.out.println("========== lookGoods ==========");

			String gid = req.getParameter("gid").trim();
			GoodsInfo gi = (GoodsInfo) db.getObject("GoodsInfo", gid);
			req.setAttribute("object", gi);

			jump(req, resp, "/modifygoods.jsp");
		} else if (action.equals("modifyGoods")) {
			System.out.println("========== modifyGoods ==========");

			String gname = req.getParameter("gname").trim();
			String gcname = req.getParameter("gcname").trim();
			String gunit = req.getParameter("gunit").trim();
			String gpin = req.getParameter("gpin").trim();
			String gpout = req.getParameter("gpout").trim();
			String gamount = req.getParameter("gamount").trim();
			String gid = req.getParameter("gid").trim();

			gname = new String(gname.getBytes(), "UTF-8");
			gcname = new String(gcname.getBytes(), "UTF-8");
			gunit = new String(gunit.getBytes(), "UTF-8");

			double pin = Double.parseDouble(gpin);
			double pout = Double.parseDouble(gpout);
			int amount = Integer.parseInt(gamount);

			String hql = "SELECT GCid FROM GoodsClassInfo WHERE GCname='" + gcname + "'";
			String gcid = (String) (db.getInfo(hql).get(0));

			GoodsInfo gi = new GoodsInfo(gid, gname, gcid, gunit, pin, pout, amount);
			dbup.updateTable("GoodsInfo", gi, gid);

			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan());
			req.setAttribute("goodslist", goodslist);
			req.setAttribute("object", gi);

			jump(req, resp, "/modifygoods.jsp");
		} else if (action.equals("deleteGoods")) {
			String gid = req.getParameter("gid").trim();
			dbdel.deleteTable("GoodsInfo", gid);
			int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
			userBean.setTotalPage(totalPage); // 记住当前总页数
			userBean.setNowPage(1); // 设置当前页为1
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到商品列表
			req.setAttribute("goodslist", goodslist);

			jump(req, resp, "/goodsmanage.jsp");
		} else if (action.equals("addGoodsClass")) {
			String gcname = req.getParameter("gcname").trim();
			gcname = new String(gcname.getBytes(), "UTF-8");
			String hql = "FROM GoodsClassInfo AS gci WHERE " + "gci.GCname='" + gcname + "'";
			List<?> list = db.getInfo(hql);
			String url = "/goodsclassmanage.jsp";
			if (list.isEmpty()) {
				String gcid = db.getId("GoodsClassInfo", "GCid");
				GoodsClassInfo gci = new GoodsClassInfo(gcid, gcname);
				dbin.insertTable("GoodsClassInfo", gci);
				int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
				userBean.setTotalPage(totalPage);
				List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan());
				req.setAttribute("goodslist", goodslist);
			} else {
				url = "/info.jsp";
				String msg = "已存在该类别！";
				req.setAttribute("msg", msg);
			}

			jump(req, resp, url);
		} else if (action.equals("lookGoodsClass")) {
			String gcid = req.getParameter("gcid").trim();
			GoodsClassInfo gci = (GoodsClassInfo) db.getObject("GoodsClassInfo", gcid);
			req.setAttribute("object", gci);

			jump(req, resp, "/modifygoodsclass.jsp");
		} else if (action.equals("modifyGoodsClass")) {
			System.out.println("====== modifyGoodsClass ======");

			String gcid = req.getParameter("gcid").trim();
			String gcname = req.getParameter("gcname").trim();
			gcname = new String(gcname.getBytes(), "UTF-8");
			String url = "/goodsclassmanage.jsp";
			String hql = "FROM GoodsClassInfo AS gci WHERE gci.GCname='" + gcname + "'";
			List<?> list = db.getInfo(hql);

			if (list.isEmpty()) {
				GoodsClassInfo gci = new GoodsClassInfo(gcid, gcname);
				dbup.updateTable("GoodsClassInfo", gci, gcid);
				List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan());
				req.setAttribute("goodslist", goodslist);
			} else {
				url = "/info.jsp";
				String msg = "该类名存在！";
				req.setAttribute("msg", msg);
			}
			jump(req, resp, url);
		} else if (action.equals("deleteGoodsClass")) {
			String gcid = req.getParameter("gcid").trim();
			dbdel.deleteTable("GoodsClassInfo", gcid);

			int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
			userBean.setTotalPage(totalPage); // 记住当前总页数
			userBean.setNowPage(1); // 设置当前页为1
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到商品列表
			req.setAttribute("goodslist", goodslist);

			jump(req, resp, "/goodsclassmanage.jsp");
		} else if (action.equals("addConsumer")) {
			String cname = req.getParameter("cname").trim(); // 得到客户名
			String clinkman = req.getParameter("clinkman").trim(); // 得到联系人
			String caddress = req.getParameter("caddress").trim(); // 得到公司地址
			String ctel = req.getParameter("ctel").trim(); // 得到公司电话
			String cemail = req.getParameter("cemail").trim(); // 得到公司E-mail
			String cremark = req.getParameter("cremark").trim(); // 得到客户备注
			if (cemail.equals("")) {
				cemail = "暂无";
			}
			if (cremark.equals("")) {
				cremark = "暂无";
			}
			cname = new String(cname.getBytes(), "UTF-8"); // 将名字转码
			clinkman = new String(clinkman.getBytes(), "UTF-8"); // 将联系人转码
			caddress = new String(caddress.getBytes(), "UTF-8"); // 将地址转码
			ctel = new String(ctel.getBytes(), "UTF-8"); // 将电话转码
			cemail = new String(cemail.getBytes(), "UTF-8"); // 将E-mail转码
			cremark = new String(cremark.getBytes(), "UTF-8"); // 将备注转码
			String url = "/consumermanage.jsp";
			String temp = "FROM ConsumerInfo AS ci WHERE ci.Cname = '" + cname + "'";
			List<?> clist = db.getInfo(temp);
			if (clist.isEmpty()) {
				String cid = db.getId("ConsumerInfo", "Cid");
				ConsumerInfo ci = new ConsumerInfo(cid, cname, clinkman, caddress, ctel, cemail, cremark);
				dbin.insertTable("ConsumerInfo", ci);
				int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
				userBean.setTotalPage(totalPage); // 记住当前总页数
				List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
				req.setAttribute("goodslist", goodslist); // 将列表放到请求中
			} else {
				String msg = "该客户已存在!!!";
				url = "/info.jsp";
				req.setAttribute("msg", msg);
			}
			jump(req, resp, url);
		} else if (action.equals("lookConsumer")) {
			String cid = req.getParameter("cid").trim(); // 得到客户ID
			String type = req.getParameter("type").trim(); // 得到查看类型
			String url = "/lookconsumer.jsp"; // 跳转页面
			if (type.equals("modify")) { // 当类型为修改时
				url = "/modifyconsumer.jsp"; // 更改URL
			}
			ConsumerInfo ci = (ConsumerInfo) db.getObject("ConsumerInfo", cid); // 得到对象
			req.setAttribute("object", ci); // 将类别对象放入请求中
			jump(req, resp, url); // 页面跳转
		} else if (action.equals("modifyConsumer")) {
			String cid = req.getParameter("cid").trim();
			String cname = req.getParameter("cname").trim(); // 得到客户名
			String clinkman = req.getParameter("clinkman").trim(); // 得到联系人
			String caddress = req.getParameter("caddress").trim(); // 得到公司地址
			String ctel = req.getParameter("ctel").trim(); // 得到公司电话
			String cemail = req.getParameter("cemail").trim(); // 得到公司E-mail
			String cremark = req.getParameter("cremark").trim(); // 得到客户备注
			if (cemail.equals("")) {
				cemail = "暂无";
			}
			if (cremark.equals("")) {
				cremark = "暂无";
			}
			cname = new String(cname.getBytes(), "UTF-8"); // 将名字转码
			clinkman = new String(clinkman.getBytes(), "UTF-8"); // 将联系人转码
			caddress = new String(caddress.getBytes(), "UTF-8"); // 将地址转码
			ctel = new String(ctel.getBytes(), "UTF-8"); // 将电话转码
			cemail = new String(cemail.getBytes(), "UTF-8"); // 将E-mail转码
			cremark = new String(cremark.getBytes(), "UTF-8"); // 将备注转码
			ConsumerInfo ci = new ConsumerInfo(cid, cname, clinkman, caddress, ctel, cemail, cremark);
			dbup.updateTable("ConsumerInfo", ci, cid); // 更新表格
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
			req.setAttribute("goodslist", goodslist);
			jump(req, resp, "/consumermanage.jsp");
		} else if (action.equals("deleteConsumer")) {
			String cid = req.getParameter("cid").trim(); // 得到客户ID
			dbdel.deleteTable("ConsumerInfo", cid); // 将该对象删除
			int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
			userBean.setTotalPage(totalPage); // 记住当前总页数
			userBean.setNowPage(1); // 设置当前页为1
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
			req.setAttribute("goodslist", goodslist);
			jump(req, resp, "/consumermanage.jsp");
		} else if (action.equals("addProvider")) {
			String pname = req.getParameter("pname").trim(); // 得到供应商名
			String plinkman = req.getParameter("plinkman").trim(); // 得到联系人
			String paddress = req.getParameter("paddress").trim(); // 得到公司地址
			String ptel = req.getParameter("ptel").trim(); // 得到公司电话
			String pemail = req.getParameter("pemail").trim(); // 得到公司E-mail
			String premark = req.getParameter("premark").trim(); // 得到客户备注
			if (pemail.equals("")) {
				pemail = "暂无";
			}
			if (premark.equals("")) {
				premark = "暂无";
			}
			pname = new String(pname.getBytes(), "UTF-8");
			plinkman = new String(plinkman.getBytes(), "UTF-8");
			paddress = new String(paddress.getBytes(), "UTF-8");
			ptel = new String(ptel.getBytes(), "UTF-8");
			pemail = new String(pemail.getBytes(), "UTF-8");
			premark = new String(premark.getBytes(), "UTF-8");
			String temp = "FROM ProviderInfo AS pi WHERE pi.Pname='" + pname + "'";
			List<?> plist = db.getInfo(temp);
			String url = "/providermanage.jsp";
			if (plist.isEmpty()) {
				String pid = db.getId("ProviderInfo", "Pid");
				ProviderInfo pi = new ProviderInfo(pid, pname, plinkman, paddress, ptel, pemail, premark);
				dbin.insertTable("ProviderInfo", pi);
				int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
				userBean.setTotalPage(totalPage); // 记住当前总页数
				List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
				req.setAttribute("goodslist", goodslist); // 将列表放到请求中
			} else {
				String msg = "该客户已存在!!!";
				url = "/info.jsp";
				req.setAttribute("msg", msg);
			}
			jump(req, resp, url);
		} else if (action.equals("lookProvider")) {
			String pid = req.getParameter("pid").trim(); // 得到供应商ID
			String type = req.getParameter("type").trim(); // 得到查看类型
			String url = "/lookprovider.jsp"; // 跳转地址
			if (type.equals("modify")) { // 当类型为修改时
				url = "/modifyprovider.jsp";
			}
			ProviderInfo pi = (ProviderInfo) db.getObject("ProviderInfo", pid);// 得到对象
			req.setAttribute("object", pi); // 将对象放入请求中
			jump(req, resp, url);
		} else if (action.equals("modifyProvider")) {
			String pid = req.getParameter("pid").trim();
			String pname = req.getParameter("pname").trim(); // 得到供应商名
			String plinkman = req.getParameter("plinkman").trim(); // 得到联系人
			String paddress = req.getParameter("paddress").trim(); // 得到公司地址
			String ptel = req.getParameter("ptel").trim(); // 得到公司电话
			String pemail = req.getParameter("pemail").trim(); // 得到公司E-mail
			String premark = req.getParameter("premark").trim(); // 得到客户备注
			if (pemail.equals("")) {
				pemail = "暂无";
			}
			if (premark.equals("")) {
				premark = "暂无";
			}
			pname = new String(pname.getBytes(), "UTF-8");
			plinkman = new String(plinkman.getBytes(), "UTF-8");
			paddress = new String(paddress.getBytes(), "UTF-8");
			ptel = new String(ptel.getBytes(), "UTF-8");
			pemail = new String(pemail.getBytes(), "UTF-8");
			premark = new String(premark.getBytes(), "UTF-8");

			ProviderInfo pi = new ProviderInfo(pid, pname, plinkman, paddress, ptel, pemail, premark);
			dbup.updateTable("ProviderInfo", pi, pid); // 更新表格
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
			req.setAttribute("goodslist", goodslist); // 将列表放到请求中
			jump(req, resp, "/providermanage.jsp");
		} else if (action.equals("deleteProvider")) {
			String pid = req.getParameter("pid").trim(); // 得到供应商ID
			dbdel.deleteTable("ProviderInfo", pid); // 将该对象删除
			int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
			userBean.setTotalPage(totalPage); // 记住当前总页数
			userBean.setNowPage(1); // 设置当前页为1
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
			req.setAttribute("goodslist", goodslist); // 将列表放入请求中
			jump(req, resp, "/providermanage.jsp");
		} else if (action.equals("addStock")) {
			String stp = req.getParameter("stp").trim();// stotalprice
			String pname = req.getParameter("pname").trim();
			String sbuyer = req.getParameter("sbuyer").trim();
			double stotalprice = Double.parseDouble(stp);
			pname = new String(pname.getBytes(), "UTF-8");
			sbuyer = new String(sbuyer.getBytes(), "UTF-8");

			String hql = "SELECT Pid FROM ProviderInfo WHERE Pname='" + pname + "'";
			String pid = (String) (db.getInfo(hql).get(0));
			String sid = db.getId("StockInfo", "Sid");

			StockInfo si = new StockInfo(sid, pid, new Date(), sbuyer, stotalprice);
			dbin.insertTable("StockInfo", si);
			int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
			userBean.setTotalPage(totalPage);
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan());

			req.setAttribute("goodslist", goodslist);
			jump(req, resp, "/stockmanage.jsp");
		} else if (action.equals("lookStock")) {
			String sid = req.getParameter("sid").trim();
			String type = req.getParameter("type").trim();
			String url = "/addstockdetail.jsp";
			if (type.equals("look")) {
				url = "/lookstock.jsp";
			} else if (type.equals("modify")) {
				url = "/modifystock.jsp";
			}
			StockInfo si = (StockInfo) db.getObject("StockInfo", sid);
			String hql = "FROM StockDetail AS sd WHERE sd.Sid='" + sid + "'";
			List<StockDetail> list = (List<StockDetail>) db.getInfo(hql);
			req.setAttribute("si", si);
			req.setAttribute("list", list);
			jump(req, resp, url);
		} else if (action.equals("addStockDetail")) {
			String sid = req.getParameter("sid").trim();
			String amount = req.getParameter("sdamount").trim();
			String gname = req.getParameter("gname").trim();
			gname = new String(gname.getBytes(), "UTF-8");

			String hql = "FROM GoodsInfo AS gi WHERE gi.Gname='" + gname + "'";
			List<GoodsInfo> list = (List<GoodsInfo>) db.getInfo(hql);
			GoodsInfo gi = list.get(0);
			String sdid = db.getId("StockDetail", "SDid");
			int sdamount = Integer.parseInt(amount);
			double sdprice = gi.getGpin();
			double sdtotalprice = sdprice * sdamount;
			StockDetail sd = new StockDetail(sdid, sid, gi.getGid(), sdamount, sdprice, sdtotalprice);
			dbin.insertTable("StockDetail", sd);
			StockInfo si = (StockInfo) db.getObject("StockInfo", sid);
			String temp = "FROM StockDetail AS sd WHERE sd.Sid='" + sid + "'";
			List<StockDetail> li = (List<StockDetail>) db.getInfo(temp);
			req.setAttribute("list", li);
			req.setAttribute("si", si);
			jump(req, resp, "/addstockdetail.jsp");
		} else if (action.equals("modifyStock")) {
			String sid = req.getParameter("sid").trim();
			String pname = req.getParameter("pname").trim();
			String sdate = req.getParameter("sdate").trim();
			String sbuyer = req.getParameter("sbuyer").trim();
			pname = new String(pname.getBytes(), "UTF-8");
			sbuyer = new String(sbuyer.getBytes(), "UTF-8");

			String hql = "FROM ProviderInfo AS pi WHERE pi.Pname='" + pname + "'";
			ProviderInfo pi = (ProviderInfo) db.getInfo(hql).get(0);
			StockInfo si = (StockInfo) db.getObject("StockInfo", sid);
			si.setPid(pi.getPid());
			si.setSdate(java.sql.Date.valueOf(sdate));
			si.setSbuyer(sbuyer);
			dbup.updateTable("StockInfo", si, sid);

			String temp = "FROM StockDetail AS sd WHERE sd.Sid='" + sid + "'";
			List<StockDetail> list = (List<StockDetail>) db.getInfo(temp);
			req.setAttribute("si", si);
			req.setAttribute("list", list);
			jump(req, resp, "/modifystock.jsp");
		} else if (action.equals("modifyStockDetail")) {
			String sdid = req.getParameter("sdid").trim(); // 得到采购明细ID
			String sdamount = req.getParameter("sdamount").trim(); // 得到修改数量
			StockDetail sd = (StockDetail) db.getObject("StockDetail", sdid); // 得到采购明细对象
			sd.setSDamount(Integer.parseInt(sdamount)); // 设置数量
			dbup.updateTable("StockDetail", sd, sdid); // 执行更新
			String sid = sd.getSid(); // 得到Sid
			StockInfo si = (StockInfo) db.getObject("StockInfo", sid);

			String temp = "FROM StockDetail AS sd WHERE sd.Sid='" + sid + "'";
			List<StockDetail> list = (List<StockDetail>) db.getInfo(temp);
			req.setAttribute("si", si);
			req.setAttribute("list", list);
			jump(req, resp, "/modifystock.jsp");
		} else if (action.equals("deleteStockDetail")) {
			String sdid = req.getParameter("sdid").trim();
			String sid = req.getParameter("sid").trim();
			StockDetail sd = (StockDetail) db.getObject("StockDetail", sdid);
			dbdel.deleteTable("StockDetail", sdid);
			StockInfo si = (StockInfo) db.getObject("StockInfo", sid); // 得到采购对象
			String temp = "FROM StockDetail AS sd WHERE sd.Sid='" + sid + "'";
			List<StockDetail> li = (List<StockDetail>) db.getInfo(temp); // 得到对象列表
			req.setAttribute("si", si); // 将对象放到请求中
			req.setAttribute("list", li); // 将对象列表放入请求中
			jump(req, resp, "/modifystock.jsp"); // 页面跳转
		} else if (action.equals("deleteStock")) {
			String sid = req.getParameter("sid").trim();
			dbdel.deleteTable("StockInfo", sid);
			int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
			userBean.setNowPage(1); // 设置当前页为第一页
			userBean.setTotalPage(totalPage); // 记住当前总页数
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
			req.setAttribute("goodslist", goodslist); // 将列表放到请求中
			jump(req, resp, "/stockmanage.jsp"); // 页面跳转
		} else if (action.equals("addSell")) {
			System.out.println("============ addSell =============");

			String cname = req.getParameter("cname").trim();
			String etp = req.getParameter("etotalprice").trim();
			String eseller = req.getParameter("eseller").trim();
			double etotalprice = Double.parseDouble(etp);
			cname = new String(cname.getBytes(), "UTF-8");
			eseller = new String(eseller.getBytes(), "UTF-8");

			String hql = "SELECT Cid FROM ConsumerInfo WHERE Cname='" + cname + "'";
			String cid = (String) (db.getInfo(hql).get(0));
			String eid = db.getId("SellInfo", "Eid");

			SellInfo ei = new SellInfo(eid, cid, new Date(), eseller, etotalprice);
			dbin.insertTable("SellInfo", ei);
			int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
			userBean.setTotalPage(totalPage);
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan());
			req.setAttribute("goodslist", goodslist);
			jump(req, resp, "/sellmanage.jsp");
		} else if (action.equals("lookSell")) {
			System.out.println("============ LookSell =============");

			String eid = req.getParameter("eid").trim();
			String type = req.getParameter("type").trim();
			String url = "/addselldetail.jsp";
			if (type.equals("look")) {
				url = "/looksell.jsp";
			} else if (type.equals("modify")) {
				url = "/modifysell.jsp";
			}
			SellInfo ei = (SellInfo) db.getObject("SellInfo", eid);
			String hql = "FROM SellDetail AS ed WHERE ed.Eid='" + eid + "'";
			List<SellDetail> list = (List<SellDetail>) db.getInfo(hql);
			req.setAttribute("ei", ei);
			req.setAttribute("list", list);
			jump(req, resp, url);
		} else if (action.equals("addSellDetail")) {
			String eid = req.getParameter("eid").trim();
			String amount = req.getParameter("edamount").trim();
			String gname = req.getParameter("gname").trim();
			gname = new String(gname.getBytes(), "UTF-8");

			String hql = "FROM GoodsInfo AS gi WHERE gi.Gname='" + gname + "'";
			List<GoodsInfo> list = (List<GoodsInfo>) db.getInfo(hql);
			GoodsInfo gi = list.get(0);
			String edid = db.getId("SellDetail", "EDid");
			int edamount = Integer.parseInt(amount);
			String url = "addselldetail.jsp";
			if (gi.getGamount() >= edamount) {
				double edprice = gi.getGpin();
				double edtotalprice = edprice * edamount;
				SellDetail ed = new SellDetail(edid, eid, gi.getGid(), edamount, edprice, edtotalprice);
				dbin.insertTable("SellDetail", ed);
				SellInfo ei = (SellInfo) db.getObject("SellInfo", eid);
				String temp = "FROM SellDetail AS ed WHERE ed.Eid='" + eid + "'";
				List<SellDetail> li = (List<SellDetail>) db.getInfo(temp);
				req.setAttribute("ei", ei);
				req.setAttribute("list", li);
			} else {
				url = "/info.jsp";
				String msg = "商品数量不足,剩余量为:" + gi.getGamount();
				req.setAttribute("msg", msg);
			}

			jump(req, resp, "/addselldetail.jsp");
		} else if (action.equals("modifySell")) {
			String eid = req.getParameter("eid").trim();
			String cname = req.getParameter("cname").trim();
			String edate = req.getParameter("edate").trim();
			String eseller = req.getParameter("eseller").trim();
			cname = new String(cname.getBytes(), "UTF-8");
			eseller = new String(eseller.getBytes(), "UTF-8");

			String hql = "FROM ConsumerInfo AS ci WHERE ci.Cname='" + cname + "'";
			ConsumerInfo ci = (ConsumerInfo) db.getInfo(hql).get(0);
			SellInfo ei = (SellInfo) db.getObject("SellInfo", eid);
			ei.setCid(ci.getCid());
			ei.setEdate(java.sql.Date.valueOf(edate));
			ei.setEseller(eseller);
			dbup.updateTable("SellInfo", ei, eid);

			String temp = "FROM SellDetail AS ed WHERE ed.Eid='" + eid + "'";
			List<SellDetail> list = (List<SellDetail>) db.getInfo(temp);
			req.setAttribute("ei", ei);
			req.setAttribute("list", list);
			jump(req, resp, "/modifysell.jsp");
		} else if (action.equals("modifySellDetail")) {
			String edid = req.getParameter("edid").trim();
			String amount = req.getParameter("edamount").trim();
			int edamount = Integer.parseInt(amount);
			SellDetail ed = (SellDetail) db.getObject("SellDetail", edid);
			GoodsInfo gi = (GoodsInfo) db.getObject("GoodsInfo", ed.getGid());
			String url = "/modifysell.jsp";
			if ((gi.getGamount() + ed.getEDamount() - edamount) >= 0) {
				ed.setEDamount(edamount);
				dbup.updateTable("SellDetail", ed, edid);
				String eid = ed.getEid();
				SellInfo ei = (SellInfo) db.getObject("SellInfo", eid);
				String temp = "FROM SellDetail AS ed WHERE ed.Eid='" + eid + "'";
				List<SellDetail> list = (List<SellDetail>) db.getInfo(temp);
				req.setAttribute("ei", ei);
				req.setAttribute("list", list);
			} else {
				url = "/info.jsp";
				String msg = "商品数量不足,剩余量为:" + gi.getGamount();
				req.setAttribute("msg", msg);
			}
			jump(req, resp, url);
		} else if (action.equals("deleteSellDetail")) {
			String edid = req.getParameter("edid").trim();
			String eid = req.getParameter("eid").trim();
			SellDetail ed = (SellDetail) db.getObject("SellDetail", edid);
			dbdel.deleteTable("SellDetail", edid);
			SellInfo ei = (SellInfo) db.getObject("SellInfo", eid);
			String temp = "FROM SellDetail AS ed WHERE ed.Eid='" + eid + "'";
			List<SellDetail> li = (List<SellDetail>) db.getInfo(temp);
			req.setAttribute("ei", ei);
			req.setAttribute("list", li);
			jump(req, resp, "/modifysell.jsp");
		} else if (action.equals("deleteSell")) {
			String eid = req.getParameter("eid").trim();
			dbdel.deleteTable("SellInfo", eid);
			int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
			userBean.setNowPage(1);
			userBean.setTotalPage(totalPage);
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan());
			req.setAttribute("goodslist", goodslist);
			jump(req, resp, "/sellmanage.jsp");
		} else if (action.equals("addConsumerBack")) {
			String eid = req.getParameter("eid").trim();
			String cbid = db.getId("ConsumerBack", "CBid");
			SellInfo si = (SellInfo) db.getObject("SellInfo", eid);
			String cid = si.getCid();
			ConsumerBack cb = new ConsumerBack(cbid, cid, eid, new Date());
			dbin.insertTable("ConsumerBack", cb);

			int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
			userBean.setTotalPage(totalPage); // 记住当前总页数
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
			req.setAttribute("goodslist", goodslist);
			jump(req, resp, "/consumerbackmanage.jsp");
		} else if (action.equals("addProviderBack")) {
			String sid = req.getParameter("sid").trim(); // 得到采购表ID
			String pbid = db.getId("ProviderBack", "PBid"); // 得到要添加的ID
			StockInfo si = (StockInfo) db.getObject("StockInfo", sid); // 得到采购表对象
			String pid = si.getPid(); // 得到供应商ID
			ProviderBack pb = new ProviderBack(pbid, pid, sid, new Date()); // 得到退货对象
			dbin.insertTable("ProviderBack", pb); // 添加对象
			int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
			userBean.setTotalPage(totalPage); // 记住当前总页数
			List<?> goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
			req.setAttribute("goodslist", goodslist);
			jump(req, resp, "/providerbackmanage.jsp");
		} else if (action.equals("lookConsumerBack")) {
			String cbid = req.getParameter("cbid").trim(); // 得到退货表ID
			String type = req.getParameter("type").trim(); // 得到查看类型
			String url = "/addconsumerbackdetail.jsp"; // 跳转URL
			if (type.equals("look")) { // 当查看类型为look时
				url = "/lookconsumerback.jsp"; // 设置URL
			} else if (type.equals("modify")) { // 当查看类型为modify时
				url = "/modifyconsumerback.jsp"; // 设置URL
			}
			ConsumerBack cb = (ConsumerBack) db.getObject("ConsumerBack", cbid); // 得到对象
			String hql = "FROM ConsumerBackDetail AS cbd WHERE cbd.CBid = '" + cbid + "'"; // 搜索对象的hql
			List<ConsumerBackDetail> list = (List<ConsumerBackDetail>) db.getInfo(hql); // 得到对象列表
			req.setAttribute("cb", cb); // 将对象放到请求中
			req.setAttribute("list", list);
			jump(req, resp, url);
		} else if (action.equals("lookProviderBack")) {
			String pbid = req.getParameter("pbid").trim(); // 得到采购表ID
			String type = req.getParameter("type").trim(); // 得到查看类型
			String url = "/addproviderbackdetail.jsp";
			if (type.equals("look")) { // 当查看类型为look时
				url = "/lookproviderback.jsp"; // 设置URL
			} else if (type.equals("modify")) { // 当查看类型为modify时
				url = "/modifyproviderback.jsp"; // 设置URL
			}
			ProviderBack pb = (ProviderBack) db.getObject("ProviderBack", pbid); // 得到采购退货对象
			String hql = "FROM ProviderBackDetail AS pbd WHERE pbd.PBid = '" + pbid + "'"; // 搜索对象的hql
			List<ProviderBackDetail> list = (List<ProviderBackDetail>) db.getInfo(hql);
			req.setAttribute("pb", pb);
			req.setAttribute("list", list);
			jump(req, resp, url);
		} else if (action.equals("addConsumerBackDetail")) {
			String cbid = req.getParameter("cbid").trim(); // 得到退货单ID
			String gname = req.getParameter("gname").trim(); // 得到退货商品名
			String amount = req.getParameter("cbdamount").trim(); // 得到退货数量
			gname = new String(gname.getBytes(), "UTF-8"); // 将商品名字转码
			String hql = "FROM GoodsInfo AS gi WHERE gi.Gname='" + gname + "'"; // 搜索商品
			List<GoodsInfo> list = (List<GoodsInfo>) db.getInfo(hql); // 得到商品列表
			GoodsInfo gi = list.get(0); // 得到商品对象
			String gid = gi.getGid();
			ConsumerBack cb = (ConsumerBack) db.getObject("ConsumerBack", cbid);
			int cbdamount = Integer.parseInt(amount); // 转换类型
			double cbdprice = gi.getGpout();
			double cbdtotalprice = cbdprice * cbdamount;
			String temp = "FROM SellDetail AS ed WHERE ed.Eid='" + cb.getEid() + "' and ed.Gid='" + gi.getGid() + "'";
			SellDetail sd = ((List<SellDetail>) db.getInfo(temp)).get(0);
			String cbdid = db.getId("ConsumerBackDetail", "CBDid"); // 得到明细ID
			String url = "/addconsumerbackdetail.jsp";
			if (sd.getEDamount() >= cbdamount) {
				ConsumerBackDetail cbd = new ConsumerBackDetail(cbdid, cbid, gid, cbdamount, cbdprice, cbdtotalprice);
				dbin.insertTable("ConsumerBackDetail", cbd);
				String cbdtemp = "FROM ConsumerBackDetail AS cbd WHERE cbd.CBid='" + cbid + "'"; // 搜索对象的hql
				List<ConsumerBackDetail> li = (List<ConsumerBackDetail>) db.getInfo(cbdtemp); // 得到对象列表
				req.setAttribute("cb", cb); // 将对象放到请求中
				req.setAttribute("list", li); // 将对象列表放入请求中
			} else {
				url = "/info.jsp"; // 跳转页面
				String msg = "退货数量超出销售数量,销售量为:" + sd.getEDamount(); // 提示信息
				req.setAttribute("msg", msg); // 将信息放入请求中
			}

			jump(req, resp, url);
		} else if (action.equals("addProviderBackDetail")) {
			String pbid = req.getParameter("pbid").trim(); // 得到退货单ID
			String gname = req.getParameter("gname").trim(); // 得到退货商品名
			String amount = req.getParameter("pbdamount").trim(); // 得到退货数量
			gname = new String(gname.getBytes(), "UTF-8"); // 将商品名字转码
			String hql = "FROM GoodsInfo AS gi WHERE gi.Gname='" + gname + "'"; // 搜索商品
			List<GoodsInfo> list = (List<GoodsInfo>) db.getInfo(hql); // 得到商品列表
			GoodsInfo gi = list.get(0); // 得到商品对象
			String gid = gi.getGid();
			ProviderBack pb = (ProviderBack) db.getObject("ProviderBack", pbid);
			int pbdamount = Integer.parseInt(amount); // 转换类型
			double pbdprice = gi.getGpin();
			double pbdtotalprice = pbdprice * pbdamount;
			String temp = "FROM StockDetail AS sd where sd.Sid='" + pb.getSid() + "' and sd.Gid='" + gi.getGid() + "'";
			StockDetail sd = ((List<StockDetail>) db.getInfo(temp)).get(0);
			String pbdid = db.getId("ProviderBackDetail", "PBDid"); // 得到明细ID
			String url = "/addproviderbackdetail.jsp";
			if (sd.getSDamount() >= pbdamount) {
				ProviderBackDetail pbd = new ProviderBackDetail(pbdid, pbid, gid, pbdamount, pbdprice, pbdtotalprice);
				dbin.insertTable("ProviderBackDetail", pbd);
				String pbdtemp = "FROM ProviderBackDetail AS pbd WHERE pbd.PBid='" + pbid + "'"; // 搜索对象的hql
				List<ProviderBackDetail> li = (List<ProviderBackDetail>) db.getInfo(pbdtemp); // 得到对象列表
				req.setAttribute("pb", pb); // 将对象放到请求中
				req.setAttribute("list", li); // 将对象列表放入请求中
			} else {
				url = "/info.jsp"; // 跳转页面
				String msg = "退货数量超出采购数量,采购量为:" + sd.getSDamount(); // 提示信息
				req.setAttribute("msg", msg); // 将信息放入请求中
			}

			jump(req, resp, url);
		} else if (action.equals("modifyConsumerBackDetail")) {
			String cbdid = req.getParameter("cbdid").trim(); // 得到退货明细ID
			String amount = req.getParameter("cbdamount").trim(); // 得到修改后数量
			int cbdamount = Integer.parseInt(amount); // 数量类型转换
			ConsumerBackDetail cbd = (ConsumerBackDetail) db.getObject("ConsumerBackDetail", cbdid); // 得到明细对象
			ConsumerBack cb = (ConsumerBack) db.getObject("ConsumerBack", cbd.getCBid());// 得到退货单对象
			String hql = "FROM SellDetail AS ed WHERE ed.Eid='" + cb.getEid() + "' and ed.Gid='" + cbd.getGid() + "'";
			SellDetail sd = ((List<SellDetail>) db.getInfo(hql)).get(0); // 得到对应的销售明细对象
			String url = "/modifyconsumerback.jsp"; // 跳转地址
			if (sd.getEDamount() + cbd.getCBDamount() >= cbdamount) {
				cbd.setCBDamount(cbdamount);
				dbup.updateTable("ConsumerBackDetail", cbd, cbdid);
				String cbdtemp = "FROM ConsumerBackDetail AS cbd WHERE cbd.CBid='" + cb.getCBid() + "'"; // 搜索对象的hql
				List<ConsumerBackDetail> li = (List<ConsumerBackDetail>) db.getInfo(cbdtemp); // 得到对象列表
				req.setAttribute("cb", cb); // 将对象放到请求中
				req.setAttribute("list", li); // 将对象列表放入请求中
			} else {
				url = "/info.jsp"; // 跳转页面
				String msg = "退货数量超出销售数量,最大退货量为:" + (sd.getEDamount() + cbd.getCBDamount()); // 提示信息
				req.setAttribute("msg", msg); // 将信息放入请求中
			}

			jump(req, resp, url);
		} else if (action.equals("deleteConsumerBackDetail")) {
			String cbdid = req.getParameter("cbdid").trim();
			String cbid = req.getParameter("cbid").trim();
			ConsumerBackDetail cbd = (ConsumerBackDetail) db.getObject("ConsumerBackDetail", cbdid);
			dbdel.deleteTable("ConsumerBackDetail", cbdid);
			ConsumerBack cb = (ConsumerBack) db.getObject("ConsumerBack", cbid);
			String cbdtemp = "FROM ConsumerBackDetail AS cbd WHERE cbd.CBid='" + cbid + "'"; // 搜索对象的hql
			List<ConsumerBackDetail> li = (List<ConsumerBackDetail>) db.getInfo(cbdtemp); // 得到对象列表
			req.setAttribute("cb", cb); // 将对象放到请求中
			req.setAttribute("list", li);

			jump(req, resp, "/modifyconsumerback.jsp");
		} else if (action.equals("modifyProviderBackDetail")) {
			String pbdid = req.getParameter("pbdid").trim(); // 得到退货明细ID
			String amount = req.getParameter("pbdamount").trim(); // 得到修改后数量
			int pbdamount = Integer.parseInt(amount); // 数量类型转换
			ProviderBackDetail pbd = (ProviderBackDetail) db.getObject("ProviderBackDetail", pbdid); // 得到明细对象
			ProviderBack pb = (ProviderBack) db.getObject("ProviderBack", pbd.getPBid());// 得到退货单对象
			String hql = "FROM StockDetail AS sd WHERE sd.Sid='" + pb.getSid() + "' and sd.Gid='" + pbd.getGid() + "'";
			StockDetail sd = ((List<StockDetail>) db.getInfo(hql)).get(0); // 得到对应的销售明细对象
			String url = "/modifyproviderback.jsp"; // 跳转地址
			if (sd.getSDamount() + pbd.getPBDamount() >= pbdamount) {
				pbd.setPBDamount(pbdamount);
				dbup.updateTable("ProviderBackDetail", pbd, pbdid);
				String pbdtemp = "FROM ProviderBackDetail AS pbd WHERE pbd.PBid='" + pb.getPBid() + "'"; // 搜索对象的hql
				List<ProviderBackDetail> li = (List<ProviderBackDetail>) db.getInfo(pbdtemp); // 得到对象列表
				req.setAttribute("pb", pb); // 将对象放到请求中
				req.setAttribute("list", li); // 将对象列表放入请求中
			} else {
				url = "/info.jsp"; // 跳转页面
				String msg = "退货数量超出采购数量,最大退货量为:" + (sd.getSDamount() + pbd.getPBDamount()); // 提示信息
				req.setAttribute("msg", msg); // 将信息放入请求中
			}
			jump(req, resp, url);
		} else if (action.equals("deleteProviderBackDetail")) {
			String pbdid = req.getParameter("pbdid").trim(); // 得到要删除的明细ID
			String pbid = req.getParameter("pbid").trim(); // 得到采购ID
			ProviderBackDetail pbd = (ProviderBackDetail) db.getObject("ProviderBackDetail", pbdid);// 得到明细对象
			dbdel.deleteTable("ProviderBackDetail", pbdid); // 删除对象
			ProviderBack pb = (ProviderBack) db.getObject("ProviderBack", pbid); // 得到采购对象
			String pbdtemp = "from ProviderBackDetail as pbd where pbd.PBid='" + pbid + "'"; // 搜索对象的hql
			List<ProviderBackDetail> li = (List<ProviderBackDetail>) db.getInfo(pbdtemp); // 得到对象列表
			req.setAttribute("pb", pb); // 将对象放到请求中
			req.setAttribute("list", li);
			jump(req, resp, "/modifyproviderback.jsp");
		} else if (action.equals("deleteProviderBack")) {
			String pbid = req.getParameter("pbid").trim(); // 得到要删除的退货表ID
			dbdel.deleteTable("ProviderBack", pbid);
			int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
			userBean.setNowPage(1); // 设置当前页为第一页
			userBean.setTotalPage(totalPage); // 记住当前总页数
			List<?> list = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
			req.setAttribute("goodslist", list); // 将列表放到请求中
			jump(req, resp, "/providerbackmanage.jsp"); // 页面跳转
		} else if (action.equals("deleteConsumerBack")) {
			String cbid = req.getParameter("cbid").trim(); // 得到要删除的退货表ID
			dbdel.deleteTable("ConsumerBack", cbid);
			int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
			userBean.setNowPage(1); // 设置当前页为第一页
			userBean.setTotalPage(totalPage); // 记住当前总页数
			List<?> list = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
			req.setAttribute("goodslist", list); // 将列表放到请求中
			jump(req, resp, "/consumerbackmanage.jsp"); // 页面跳转
		} else if (action.equals("changepwd")) {
			String aname = req.getParameter("aname").trim();
			String apwd = req.getParameter("apwd").trim();
			String fpwd = req.getParameter("fpwd").trim();
			aname = new String(aname.getBytes(), "UTF-8"); // 将用户名转码
			apwd = new String(apwd.getBytes(), "UTF-8"); // 将密码转码
			fpwd = new String(fpwd.getBytes(), "UTF-8"); // 将新密码转码
			String hql = "FROM AdminInfo AS p " + // hql语句
					"WHERE p.Aname='" + aname + "' and p.Apwd='" + apwd + "'";
			String msg = "";
			List<AdminInfo> list = (List<AdminInfo>) db.getInfo(hql);
			if (!list.isEmpty()) {
				AdminInfo ai = list.get(0);
				ai.setApwd(fpwd);
				dbup.updateTable("AdminInfo", ai, ai.getAid());
				msg = "密码修改成功!!!";
			} else {
				msg = "对不起,用户名或密码错误!!!";
			}
			req.setAttribute("msg", msg);
			jump(req, resp, "/info.jsp");
		} else if (action.equals("addAdmin")) {
			String aname = req.getParameter("aname").trim(); // 得到管理员名称
			String apwd = req.getParameter("apwd").trim(); // 得到密码
			aname = new String(aname.getBytes(), "UTF-8"); // 转码
			apwd = new String(apwd.getBytes(), "UTF-8"); // 转码
			String hql = "FROM AdminInfo AS ai WHERE ai.Aname='" + aname + "'"; // 搜索管理员
			List list = db.getInfo(hql); // 得到列表
			String url = ""; // 用来存放跳转地址
			if (list.isEmpty()) { // 当管理员不存在时
				String aid = db.getId("AdminInfo", "aid"); // 得到管理员ID
				AdminInfo ai = new AdminInfo(aid, aname, apwd, new String("普通".getBytes(), "UTF-8"));
				dbin.insertTable("AdminInfo", ai); // 添加对象
				url = "/adminmanage.jsp"; // 设置跳转地址
				int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
				userBean.setTotalPage(totalPage); // 记住当前总页数
				List goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
				req.setAttribute("goodslist", goodslist); // 将列表放到请求中
			} else { // 当管理员存在时
				String msg = "对不起,该管理员已经存在!!!"; // 提示信息
				req.setAttribute("msg", msg); // 将信息放入请求中
				url = "/info.jsp"; // 设置跳转地址
			}
			jump(req, resp, url); // 页面跳转
		} else if (action.equals("deleteAdmin")) { // 当动作为删除管理员时
			String aid = req.getParameter("aid").trim();
			AdminInfo ai = (AdminInfo) db.getObject("AdminInfo", aid);
			String alevel = new String(ai.getAlevel().getBytes(), "UTF-8");
			String url = "/adminmanage.jsp";
			if (alevel.equals("普通")) {
				dbdel.deleteTable("AdminInfo", aid);
				int totalPage = db.getTotalPage(userBean.getPageHql(), userBean.getSpan());
				userBean.setNowPage(1); // 设置当前页为第一页
				userBean.setTotalPage(totalPage); // 记住当前总页数
				List goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
				req.setAttribute("goodslist", goodslist); // 将列表放到请求中
			} else {
				String msg = "超级管理员不可以删除!!!";
				req.setAttribute("msg", msg);
				url = "/info.jsp";
			}
			jump(req, resp, url); // 页面跳转
		} else if (action.equals("resetApwd")) {
			String aname = req.getParameter("aname").trim(); // 得到管理员名称
			String apwd = req.getParameter("apwd").trim(); // 得到新密码
			aname = new String(aname.getBytes(), "UTF-8"); // 转码
			apwd = new String(apwd.getBytes(), "UTF-8"); // 转码

			String hql = "FROM AdminInfo WHERE Aname='" + aname + "'";
			AdminInfo ai = ((List<AdminInfo>) db.getInfo(hql)).get(0);
			ai.setApwd(apwd);
			dbup.updateTable("AdminInfo", ai, ai.getAid());
			List goodslist = db.getPageContent(userBean.getHql(), userBean.getNowPage(), userBean.getSpan()); // 得到列表
			req.setAttribute("goodslist", goodslist); // 将列表放到请求中
			jump(req, resp, "/adminmanage.jsp");
		}

	}
}
