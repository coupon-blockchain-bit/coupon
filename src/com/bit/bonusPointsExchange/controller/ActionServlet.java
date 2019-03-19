package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionServlet extends HttpServlet {


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request,response);

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");	
		
		String actionCode=request.getParameter("actionCode");	
		Action action = null;
		if(actionCode.equals("log")){//登陆、注销
			action = new LogAction();
		}else if(actionCode.equals("regist")){//注册
			action = new RegistAction();
		}else if(actionCode.equals("user")){//用户模块
			action = new UserAction();
		}else if(actionCode.equals("forgetPasswd")){//忘记密码
			action = new ForgetPasswdAction();
		}else if(actionCode.equals("resetPasswd")){//重置密码
			action = new ResetPasswdAction();
		}else if(actionCode.equals("bindShop")){
			action = new BindShopAction();
		}else if(actionCode.equals("order")){//订单模块
			action = new OrderAction();
		}else if("recommend".equals(actionCode)){//智能推荐
			action = new RecommendAction();
		}
		
		action.execute(request, response);

	}
	
	/**
	 * Constructor of the object.
	 */
	public ActionServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

}
