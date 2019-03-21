package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.LoginShopManger;

public class BindShopQueryInfoServlet extends HttpServlet {

//用户在绑定商家的时候查询商家相关信息
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		//1.获取用户输入的要搜索的商家的名称
		String shopName = request.getParameter("search");
		LoginShopManger loginShopManger = new LoginShopManger();
		//获取商家的详细信息，查询shop表，
		Shop shop = loginShopManger.getShopInfo(shopName);
		//设置属性
		request.setAttribute("shopDec", shop.getShopDec());
		request.setAttribute("shopName", shop.getShopName());
		request.setAttribute("imgURL", shop.getImgUrl());
		request.setAttribute("index", "5");//设置显示第几个div
		//转发到页面进行显示
		request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
	}

}
