package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.ShowBindInfo;
import com.bit.bonusPointsExchange.manager.BindShopManager;
//查询用户已经绑定的商家的相关信息，第6个div进行显示
public class QueryBindInfo extends HttpServlet {

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
		//System.out.println(userName);
		//查询数据库，多表联合查询
		BindShopManager bindShopManger = new BindShopManager();
		List< ShowBindInfo> list = bindShopManger.bingShopInfo(userName);
		request.setAttribute("bindInfo", list);
		request.setAttribute("index", "6");
		request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
		
		
	}

}
