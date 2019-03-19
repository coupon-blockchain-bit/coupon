package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.utils.DBUtils;
import com.sun.org.apache.xpath.internal.operations.And;

//查询订单相关的数据库操作全部写在此处,改变订单的状态
public class QueryOrderManager {
	
	//1.根据用户名查询用户所有的订单信息，传入的是平台注册的用户名
	public List<Order> queryOrderInfo(String userName){
		Connection conn=DBUtils.getConnection();
		LoginShopManger loginShopManger = new LoginShopManger();
		List<Order> list = new ArrayList<Order>();
		Statement stmt=null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select *from bonusPointsExchange.order where userName='"+userName+"'");
								     
			while(rs.next()) {
				Order orderInfo = new Order();
				orderInfo.setOrderID(rs.getInt("orderID"));
				orderInfo.setUserName(userName);
				orderInfo.setShopName(rs.getString("shopName"));
				orderInfo.setPoint(rs.getInt("point"));
				orderInfo.setWantedShop(rs.getString("wantedShop"));
				orderInfo.setWantedPoint(rs.getInt("wantedPoint"));
				orderInfo.setExchangeUserName(rs.getString("exchangeUserName"));
				orderInfo.setUntilDate(rs.getString("untilDate"));
				orderInfo.setOrderDate(rs.getString("orderDate"));
				orderInfo.setOrderStatus(rs.getInt("orderStatus"));
				//查询商家图标
				Shop shop1 = loginShopManger.getShopInfo(rs.getString("shopName"));
				orderInfo.setShopLogo(shop1.getImgUrl());
				//查询目标商家图标
				Shop shop2 = loginShopManger.getShopInfo(rs.getString("wantedShop"));
				orderInfo.setWantedShopLogo(shop2.getImgUrl());
				list.add(orderInfo);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}
		return list;
	}
	
	//2.改变订单的状态
	public boolean changeOrderStatus(int orderID, int orderStatus){
		Connection conn=DBUtils.getConnection();
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			String sql="update bonusPointsExchange.order set orderStatus='"+orderStatus+"'where orderID='"+orderID+"'";
			//System.out.println(sql);
			int res = stmt.executeUpdate(sql);
			if(res != 0) 
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(null, stmt, conn);
		}
		return false;
	}
	
	//3.查询订单信息，根据比率由高到低返回记录集合
	public List<Order> findAllOrderByRate(String shopName,String wantedShop,String userName,int point, int wantedPoint){
		Connection conn=DBUtils.getConnection();
		LoginShopManger loginShopManger = new LoginShopManger();
		List<Order> list = new ArrayList<Order>();
		int downPoint = (int)(point - point * 0.1);
		int upPoint = (int)(point + point * 0.1);	
		int downWantedPoint = (int)(wantedPoint - wantedPoint * 0.1);
		int upWantedPoint = (int)(wantedPoint + wantedPoint * 0.1);
		Statement stmt=null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select *from bonusPointsExchange.order where shopName='"+wantedShop+"' and wantedShop='"+shopName+"' and orderStatus=0 and userName!='"+userName+"' and wantedPoint >= '"+downPoint+"' and wantedPoint <= '"+upPoint+"' and point >= '"+downWantedPoint+"' and point <= '"+upWantedPoint+"' order by point / wantedPoint desc");                                                 		     
			while(rs.next()) {
				Order orderInfo = new Order();
				orderInfo.setOrderID(rs.getInt("orderID"));
				orderInfo.setUserName(rs.getString("userName"));
				orderInfo.setShopName(rs.getString("shopName"));
				orderInfo.setPoint(rs.getInt("point"));
				orderInfo.setWantedShop(rs.getString("wantedShop"));
				orderInfo.setWantedPoint(rs.getInt("wantedPoint"));
				orderInfo.setExchangeUserName(rs.getString("exchangeUserName"));
				orderInfo.setUntilDate(rs.getString("untilDate"));
				orderInfo.setOrderDate(rs.getString("orderDate"));
				orderInfo.setOrderStatus(rs.getInt("orderStatus"));
				//查询商家图标
				Shop shop1 = loginShopManger.getShopInfo(rs.getString("shopName"));
				orderInfo.setShopLogo(shop1.getImgUrl());
				//查询目标商家图标
				Shop shop2 = loginShopManger.getShopInfo(rs.getString("wantedShop"));
				orderInfo.setWantedShopLogo(shop2.getImgUrl());
				list.add(orderInfo);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}
		return list;
	}
	
	//4.查询订单信息，根据时效优先返回记录集合，时效优先期长的在前，也就是新订单在前
	public List<Order> findAllOrderByUntilDate(String shopName,String wantedShop,String userName,int point, int wantedPoint){
		Connection conn=DBUtils.getConnection();
		LoginShopManger loginShopManger = new LoginShopManger();
		List<Order> list = new ArrayList<Order>();
		Statement stmt=null;
		ResultSet rs = null;
		int downPoint = (int)(point - point * 0.1);
		int upPoint = (int)(point + point * 0.1);	
		int downWantedPoint = (int)(wantedPoint - wantedPoint * 0.1);
		int upWantedPoint = (int)(wantedPoint + wantedPoint * 0.1);
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select *from bonusPointsExchange.order where shopName='"+wantedShop+"' and wantedShop='"+shopName+"' and userName!='"+userName+"' and wantedPoint >= '"+downPoint+"' and wantedPoint <= '"+upPoint+"' and point >= '"+downWantedPoint+"' and point <= '"+upWantedPoint+"' and orderStatus=0 order by untilDate desc");                                                 		     
			while(rs.next()) {
				Order orderInfo = new Order();
				orderInfo.setOrderID(rs.getInt("orderID"));
				orderInfo.setUserName(rs.getString("userName"));
				orderInfo.setShopName(rs.getString("shopName"));
				orderInfo.setPoint(rs.getInt("point"));
				orderInfo.setWantedShop(rs.getString("wantedShop"));
				orderInfo.setWantedPoint(rs.getInt("wantedPoint"));
				orderInfo.setExchangeUserName(rs.getString("exchangeUserName"));
				orderInfo.setUntilDate(rs.getString("untilDate"));
				orderInfo.setOrderDate(rs.getString("orderDate"));
				orderInfo.setOrderStatus(rs.getInt("orderStatus"));
				//查询商家图标
				Shop shop1 = loginShopManger.getShopInfo(rs.getString("shopName"));
				orderInfo.setShopLogo(shop1.getImgUrl());
				//查询目标商家图标
				Shop shop2 = loginShopManger.getShopInfo(rs.getString("wantedShop"));
				orderInfo.setWantedShopLogo(shop2.getImgUrl());
				list.add(orderInfo);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}
		return list;
	}
	
	//5.查询订单信息，传入的是平台的用户名，根据时间最新排序，同时查询商家的图标
	public List<Order> QueryLatestOrder(String userName){
		LoginShopManger loginShopManger = new LoginShopManger();
		Connection conn=DBUtils.getConnection();
		List<Order> list = new ArrayList<Order>();
		Statement stmt=null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select *from bonusPointsExchange.order where orderStatus=0 order by untilDate desc");                                                 		     
			while(rs.next()) {
				Order orderInfo = new Order();
				orderInfo.setOrderID(rs.getInt("orderID"));
				orderInfo.setUserName(rs.getString("userName"));
				orderInfo.setShopName(rs.getString("shopName"));
				orderInfo.setPoint(rs.getInt("point"));
				orderInfo.setWantedShop(rs.getString("wantedShop"));
				orderInfo.setWantedPoint(rs.getInt("wantedPoint"));
				orderInfo.setExchangeUserName(rs.getString("exchangeUserName"));
				orderInfo.setUntilDate(rs.getString("untilDate"));
				orderInfo.setOrderDate(rs.getString("orderDate"));
				orderInfo.setOrderStatus(rs.getInt("orderStatus"));
				//查询商家图标
				Shop shop1 = loginShopManger.getShopInfo(rs.getString("shopName"));
				orderInfo.setShopLogo(shop1.getImgUrl());
				//查询目标商家图标
				Shop shop2 = loginShopManger.getShopInfo(rs.getString("wantedShop"));
				orderInfo.setWantedShopLogo(shop2.getImgUrl());
				list.add(orderInfo);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}
		return list;
	}
	
	//6.查询最新完成的交易
	public List<Order> findLatestFinishedOrder(String shopName,String wantedShop){
		Connection conn=DBUtils.getConnection();
		List<Order> list = new ArrayList<Order>();
		Statement stmt=null;
		ResultSet rs = null;
		System.out.println(shopName);
		System.out.println(wantedShop);
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select *from bonusPointsExchange.order where shopName='"+shopName+"' and wantedShop='"+wantedShop+"' and orderStatus=1 order by orderDate desc");                                                 		     
			while(rs.next()) {
				Order orderInfo = new Order();
				orderInfo.setOrderID(rs.getInt("orderID"));
				orderInfo.setUserName(rs.getString("userName"));
				orderInfo.setShopName(rs.getString("shopName"));
				orderInfo.setPoint(rs.getInt("point"));
				orderInfo.setWantedShop(rs.getString("wantedShop"));
				orderInfo.setWantedPoint(rs.getInt("wantedPoint"));
				orderInfo.setExchangeUserName(rs.getString("exchangeUserName"));
				orderInfo.setUntilDate(rs.getString("untilDate"));
				orderInfo.setOrderDate(rs.getString("orderDate"));
				orderInfo.setOrderStatus(rs.getInt("orderStatus"));
				System.out.println("time");
				System.out.println(rs.getString("untilDate"));
				System.out.println("time");
				list.add(orderInfo);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs, stmt, conn);
		}
		return list;
	}
}
