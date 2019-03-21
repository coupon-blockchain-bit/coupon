package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.manager.QueryOrderManager;

public class QueryOrderInfo extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
	
		//获取用户在平台登录名称
		String userName =(String)request.getSession().getAttribute("userName");
		//查询数据库表order
		QueryOrderManager manager = new QueryOrderManager();
		List<Order> list = manager.queryOrderInfo(userName);
		request.setAttribute("orderInfo", list);
		request.setAttribute("index", "2");
		request.getRequestDispatcher("order.jsp").forward(request, response);
	}

}
