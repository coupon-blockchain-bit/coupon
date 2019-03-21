package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.LoginShopManger;
import com.bit.bonusPointsExchange.manager.QueryOrderManager;
import com.bit.bonusPointsExchange.utils.MinimalistProportionUtils;

public class ReferencePriceServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		//获取用户输入的商家名称
		String shopName = request.getParameter("search1");
		String wantedShop = request.getParameter("search2");
		//查询商家和目标商家的图标
		LoginShopManger loginShopManger = new LoginShopManger();
		Shop shop =loginShopManger.getShopInfo(shopName);//商家图标
		String ShopImgURL = shop.getImgUrl();
		shop =loginShopManger.getShopInfo(wantedShop);//目标商家图标
		String wantedShopImgURL = shop.getImgUrl();
		//查询最新完成的交易
		QueryOrderManager manager = new QueryOrderManager();
		List<Order> list = manager.findLatestFinishedOrder(shopName, wantedShop);
		if(list.size() != 0) {
			//计算最新比例
			Order orderInfo = (Order)list.get(0);//得到最新的一笔交易
			int point = orderInfo.getPoint();
			int wantedPoint =orderInfo.getWantedPoint();
			//最新比例latestRate
			String latestRate = MinimalistProportionUtils.minimalistProportion(point, wantedPoint);
			//计算最新十笔交易的平均比例，不足十笔有多少算多少
			int size = list.size();
			int poi = 0;//point的和
			int wanPoi = 0;//wantedPoint的和
			if(size < 10) {
				for(int i = 0; i < size; i++) {
					orderInfo = (Order)list.get(i);
					poi += orderInfo.getPoint();
					wanPoi +=orderInfo.getWantedPoint();
				}
			} else {
				for(int i = 0; i < 10; i++) {
					orderInfo = (Order)list.get(i);
					poi += orderInfo.getPoint();
					wanPoi +=orderInfo.getWantedPoint();
				}
			}
			//计算平均比例
			String averageRate = MinimalistProportionUtils.minimalistProportion(poi, wanPoi);
			//页面显示
			request.setAttribute("latestRate", latestRate);
			request.setAttribute("point", point);
			request.setAttribute("wantedPoint", wantedPoint);
			request.setAttribute("averageRate", averageRate);	
		} else {
			request.setAttribute("newOrder", "N");
		}
		request.setAttribute("ShopImgURL", ShopImgURL);
		request.setAttribute("wantedShopImgURL", wantedShopImgURL);
		request.setAttribute("shopName", shopName);
		request.setAttribute("wantedShop", wantedShop);
		request.getRequestDispatcher("reference.jsp").forward(request, response);
	}
}

