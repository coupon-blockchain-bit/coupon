package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageForwardServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String finishOrderRes = request.getParameter("finishOrderRes");
		System.out.println(finishOrderRes.equals("3"));
		if(finishOrderRes.equals("1")||finishOrderRes.equals("2")){
			request.setAttribute("index", "5");
			request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
		}else if(finishOrderRes.equals("3")){

			request.getRequestDispatcher("QueryBindedShopNameServlet?index="+3).forward(request, response);
		}else if(finishOrderRes.equals("4")||finishOrderRes.equals("5")||finishOrderRes.equals("6")){
			request.setAttribute("index", "3");
			request.getRequestDispatcher("order.jsp").forward(request, response);
		}
		
	}

}
