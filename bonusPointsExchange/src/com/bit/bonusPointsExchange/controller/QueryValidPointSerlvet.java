package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.manager.OrderManager;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;

public class QueryValidPointSerlvet extends HttpServlet {
	//查询用户在平台数据库有多少有效的积分,即用户在平台的积分减去用户发布的未完成订单的积分和

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//查询用户在平台数据库有多少有效的积分,即用户在平台的积分减去用户发布的未完成订单的积分和
				response.setContentType("text/html;charset=utf-8");
				request.setCharacterEncoding("utf-8"); 
				OrderManager om = new OrderManager();
				PrintWriter out = response.getWriter();
				//通过session获取用户名
				String userName =(String)request.getSession().getAttribute("userName");	
				//获取用户提交的相关信息
				String shopName = request.getParameter("shop");
				shopName = URLDecoder.decode(shopName, "UTF-8");
				//System.out.println(shopName);
				UserPointToplatfromManger dbManger = new UserPointToplatfromManger();
				int points = dbManger.ownPointsAtPlatform(userName, shopName);//用户在平台拥有的积分			
				List<Order> orders = om.findOrderByUserShopName(userName, shopName);
				int orderPointSUM=0;
				for(int i=0;i<orders.size();i++){
					orderPointSUM += orders.get(i).getPoint();
				}
				points =points-orderPointSUM;
				System.out.println(points);
				out.print(String.valueOf(points));
				
				
	
	}

}
