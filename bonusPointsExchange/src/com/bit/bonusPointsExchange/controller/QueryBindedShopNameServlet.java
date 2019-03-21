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

public class QueryBindedShopNameServlet extends HttpServlet {
	//查询用户已经绑定的商家的商家名称，显示在平台上
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		//通过session获取用户名
		String userName =(String)request.getSession().getAttribute("userName");	
		BindShopManager bindShopManger = new BindShopManager();
		List< ShowBindInfo> list = bindShopManger.bingShopInfo(userName);
		request.setAttribute("bindInfo", list);
		String index = request.getParameter("index");//获取是第几个div，便于返回的时候，显示
		if(index.equals("3")) 
			request.setAttribute("index", "3");
		if (index.equals("4"))
			request.setAttribute("index", "4");
		if (index.equals("5"))
			request.setAttribute("index", "5");
		request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
		
	}

}
