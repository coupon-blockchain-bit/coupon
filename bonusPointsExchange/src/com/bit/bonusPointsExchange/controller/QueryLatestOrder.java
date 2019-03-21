package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.manager.QueryOrderManager;
//查询最新发布的订单
public class QueryLatestOrder extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String userName =(String)request.getSession().getAttribute("userName");	
		//数据库查询最新订单
		QueryOrderManager manager = new QueryOrderManager();
		List<Order> list = manager.QueryLatestOrder(userName);
		request.setAttribute("latestOrderInfo", list);
		request.getRequestDispatcher("exchange.jsp").forward(request, response);
	}

}
