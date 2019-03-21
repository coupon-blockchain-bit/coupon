package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.manager.OrderManager;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;

public class QueryPointsAtPlatform extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//根据用户选择查询在pingtai数据库中用户有多少积分
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		//通过session获取用户名
		String userName =(String)request.getSession().getAttribute("userName");	
		//获取用户提交的相关信息
		String shopName = request.getParameter("shop");
		shopName = URLDecoder.decode(shopName, "UTF-8");
		//System.out.println(shopName);
		UserPointToplatfromManger dbManger = new UserPointToplatfromManger();
		int points = dbManger.ownPointsAtPlatform(userName, shopName);//用户拥有的积分
		OrderManager om = new OrderManager();
		List<Order> orders = om.findOrderByUserShopName(userName, shopName);//查询用户在某商家发布的所有订单
		int orderPointSUM=0;
		for(int i=0;i<orders.size();i++){
			orderPointSUM+=orders.get(i).getPoint();
		}
		points=points-orderPointSUM;
		out.print(String.valueOf(points));
		
	}

}
