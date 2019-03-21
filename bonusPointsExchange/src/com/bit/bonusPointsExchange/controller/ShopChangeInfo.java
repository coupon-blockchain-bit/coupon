package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.RegistShopManger;
import com.bit.bonusPointsExchange.manager.ShopChangeInfoManger;

public class ShopChangeInfo extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		//获取用户提交的相关信息
		String oldShopName = (String)request.getSession().getAttribute("shopName");	
		String shopName = request.getParameter("name");
		//String imgURL= request.getParameter("imageURL");//获取商家选择的图标的路径
		String email = request.getParameter("email");
		String telephone = request.getParameter("phone");
		String shopDec = request.getParameter("description");
		
		//System.out.println(shopName);
		//System.out.println(email);
		//System.out.println(telephone);
		//System.out.println(shopDec);
		//System.out.println(imgURL);
		//进行图标的上传工作
		
		//封装成对象
		Shop shop = new Shop();
		shop.setShopName(shopName);
		shop.setEmail(email);
		//shop.setImgUrl(imgUrl);
		shop.setShopDec(shopDec);
		shop.setTelephone(telephone);
		//检验商家名称是否已经存在
		//调用查询函数，查询在数据库中该商家名称是否已经存在
		RegistShopManger registShopManger = new RegistShopManger();
		boolean res = registShopManger.isShopNameExit(shop);
		if(!res && !oldShopName.equals(shopName)){  //sahngjia名已存在,且不是原有的商家名称
			request.setAttribute("shopChangeResult", "N");// 设置属性，表示修改信息失败
			request.setAttribute("telephone", telephone);
			request.setAttribute("email", email);
			request.setAttribute("shopDec", shopDec);
			request.getRequestDispatcher("personal_shop.jsp").forward(request, response);
			return;
		}
		
		//更新数据库
		ShopChangeInfoManger changeInfoManger = new ShopChangeInfoManger();
		res = changeInfoManger.updateShopInfo(shop, oldShopName);
		if(res) {
			request.getSession().setAttribute("shopName",shopName);	//数据更新成功后设置session值
			//request.setAttribute("imageURL", imgURL);
			request.setAttribute("telephone", telephone);
			request.setAttribute("email", email);
			request.setAttribute("shopDec", shopDec);
			request.setAttribute("shopChangeResult", "Y");// 设置属性，表示修改信息成功，在前台获取进行提示操作
			request.getRequestDispatcher("personal_shop.jsp").forward(request, response);
			
		}
		else {
			request.setAttribute("shopChangeResult", "N");// 设置属性，表示修改信息失败
			System.out.println(request.getAttribute("shopChangeResult"));
			request.getRequestDispatcher("personal_shop.jsp").forward(request, response);
		}
	}

}
