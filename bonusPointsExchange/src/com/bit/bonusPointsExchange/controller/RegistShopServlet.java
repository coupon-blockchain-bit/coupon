package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.RegistShopManger;
import com.bit.bonusPointsExchange.utils.Encode;

public class RegistShopServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		//获取用户所填写的信息
		//1.获取商家名称
		String shopName = (String)request.getParameter("shopName");
		//System.out.println(shopName);
		//2.获取图标地址，待解决
		//3.获取密码，进行MD5加密
		String password = (String)request.getParameter("repassword");
		String MD5password = Encode.MD5Encode(password);//加密之后的密码
		//System.out.println(MD5password);
		//4.获取email
		
		String email = (String)request.getParameter("email");
		//System.out.println(email);
		//5.获取商家备案号。待解决(如何判断是否真实)
		String number = (String)request.getParameter("number");
		//6.构建Shop对象，插入数据库
		Shop shop = new Shop();
		shop.setShopName(shopName);
		shop.setImgUrl("defaultIcon.jpg");//设置图标地址(给一个默认的图标)
		shop.setPassword(MD5password);
		shop.setEmail(email);
		shop.setNumber(number);
		//插入数据库
		RegistShopManger registShopManger = new RegistShopManger();
		int res = registShopManger.insertShop(shop);
		if (res != 0) {
			request.setAttribute("registRes", "Y");//注册成功，跳转到登录页面进行注册，同时提示用户注册成功
			request.getRequestDispatcher("login_shop.jsp").forward(request, response);
		} else {
			out.print("<script language='JavaScript'>alert('注册失败！请重新进行注册！');location.href='/bonusPointsExchange/regist_shop.jsp';</script>");
		}
	}
}
