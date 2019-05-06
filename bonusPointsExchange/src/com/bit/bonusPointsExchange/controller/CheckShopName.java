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

//����̼������Ƿ����
public class CheckShopName extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CheckShopName");
		// ���������Ϣ�ı���
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		// ���û�ȡ����Ϣ�ı���
		String shopName = request.getParameter("shopName");
		shopName = URLDecoder.decode(shopName, "UTF-8");
		Shop shop = new Shop();
		shop.setShopName(shopName);
		// ���ò�ѯ��������ѯ�����ݿ��и��̼������Ƿ��Ѿ�����
		RegistShopManger registShopManger = new RegistShopManger();
		boolean res = registShopManger.isShopNameExit(shop);
		if (!res) { // sahngjia���Ѵ���
			out.print("N");
		} else { // �̼���������
			out.print("Y");
		}
		out.flush();
		out.close();
	}

}
