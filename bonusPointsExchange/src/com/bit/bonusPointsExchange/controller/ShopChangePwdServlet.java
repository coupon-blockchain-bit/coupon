package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.manager.ShopChangePwdManger;
import com.bit.bonusPointsExchange.utils.Encode;

public class ShopChangePwdServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doPost(request, response);
	}

	//处理商家修改密码的操作
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		
		//获取商家名和旧密码以及新密码
		String shopName = request.getParameter("userName");
		//String oldPassword = request.getParameter("oldPassword");//没什么用，商家是在登录之后才能进入这个页面
		String reNewPassword = request.getParameter("reNewPassword");
		String newPassword = Encode.MD5Encode(reNewPassword);//MD5加密
		//更新数据库
		ShopChangePwdManger changePwdManger = new ShopChangePwdManger();
		boolean res = changePwdManger.updateShopPwd(shopName, newPassword);
		if(res) {
			request.setAttribute("shopChangePwdResult", "Y");// 设置属性，表示修改成功，在前台获取进行提示操作
			request.getRequestDispatcher("login_shop.jsp").forward(request, response);
		}
		else {
			request.setAttribute("shopChangePwdResult", "N");// 设置属性，表示失败
			request.getRequestDispatcher("personal_shop.jsp").forward(request, response);
		}
	}

}
