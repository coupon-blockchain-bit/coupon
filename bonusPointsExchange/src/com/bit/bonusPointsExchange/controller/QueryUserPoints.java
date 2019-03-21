package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.ShowBindInfo;
import com.bit.bonusPointsExchange.manager.BindShopManager;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;
//查询用户在商家处所拥有的积分
public class QueryUserPoints extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//根据用户选择查询在商家数据库中（模拟）用户有多少积分
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		//通过session获取用户名
		//String userName =(String)request.getSession().getAttribute("userName");
		//获取用户在商家注册的用户名
		String userNameAtShop = request.getParameter("userNameAtShop");
		userNameAtShop = URLDecoder.decode(userNameAtShop, "UTF-8");
		System.out.println("adadasdasd54555");
		System.out.println(userNameAtShop);
		//获取用户提交的相关信息
		String shopName = request.getParameter("shop");
		shopName = URLDecoder.decode(shopName, "UTF-8");
		//System.out.println(shopName);
		//BindShopManger bindShopManger = new BindShopManger();
		//查询用户在商家拥有的积分
		UserPointToplatfromManger dbManger = new UserPointToplatfromManger();
		int points = dbManger.ownPoints(userNameAtShop, shopName);
		out.print(String.valueOf(points));
		
	}

}
