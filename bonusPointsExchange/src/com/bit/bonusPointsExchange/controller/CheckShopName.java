package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.RegistShopManger;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
//检查商家名称是否可用
public class CheckShopName extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("CheckShopName");
		//设置输出信息的编码
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		//设置获取的信息的编码
		String shopName=request.getParameter("shopName");
		shopName = URLDecoder.decode(shopName, "UTF-8");
		Shop shop=new Shop();
		shop.setShopName(shopName);	
		//调用查询函数，查询在数据库中该商家名称是否已经存在
		RegistShopManger registShopManger = new RegistShopManger();
		boolean res = registShopManger.isShopNameExit(shop);
		if(!res){  //sahngjia名已存在
			out.print("N");
		}
		else{  //商家名不存在
			out.print("Y");
		}
		out.flush();
		out.close();
	}

}
