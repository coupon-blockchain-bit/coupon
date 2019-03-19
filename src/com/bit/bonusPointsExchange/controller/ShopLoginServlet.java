package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.LoginManager;
import com.bit.bonusPointsExchange.manager.LoginShopManger;
import com.bit.bonusPointsExchange.utils.Encode;
//处理登录请求
public class ShopLoginServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();	
		//获取商家名和密码
		String shopName = (String)request.getParameter("userName");
		//System.out.println(shopName);
		String password = (String)request.getParameter("password");
		//System.out.println(password);
		//由于数据库密码存储的是MD5加密后的密码，MD5是不可逆的，此处将用户输入的密码加密后与数据库中的密码进行比对，
		//看是否一致
		String encodePasswordString = Encode.MD5Encode(password);
		//System.out.println(encodePasswordString);
		Shop shop = new Shop();
		shop.setShopName(shopName);
		shop.setPassword(encodePasswordString);
		//查询数据库用户名和密码是否正确
		LoginShopManger loginShopManger = new LoginShopManger();
		int res = loginShopManger.isValid(shop);
		if (res == 1) {//信息正确,跳转到主页，将商家名称保存到session中
			System.out.println("success");
			HttpSession session = request.getSession();
			session.setAttribute("shopName",shopName);
			//session.setAttribute("userName",shopName);
			//查询数据库中的相关信息
			Shop resShop = loginShopManger.getShopInfo(shopName);
			System.out.println(resShop.getImgUrl());
			request.setAttribute("email", resShop.getEmail());//传递给personal_shop页面进行显示
			request.setAttribute("imageURL", resShop.getImgUrl());//传递给personal_shop页面进行显示
			
			request.setAttribute("telephone", resShop.getTelephone());//传递给personal_shop页面进行显示
			request.setAttribute("shopDec", resShop.getShopDec());//传递给personal_shop页面进行显示
	
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else {
			//System.out.println("error");
			out.print("<script language='JavaScript'>alert('登录失败！请重新登录！');location.href='/bonusPointsExchange/login_shop.jsp';</script>");
		}
	}

}
