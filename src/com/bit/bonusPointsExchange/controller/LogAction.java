package com.bit.bonusPointsExchange.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit.bonusPointsExchange.bean.User;
import com.bit.bonusPointsExchange.manager.LoginManager;
import com.bit.bonusPointsExchange.utils.Encode;

/**
 * µÇÂ½
 * @author gmx
 *
 */
public class LogAction extends Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String methodCode = (String)request.getParameter("methodCode");
		if(methodCode.equals("login")){
			this.login(request, response);
		}else if(methodCode.equals("logout")){
			this.logout(request, response);
		}
		
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		HttpSession session = request.getSession();
		
		String userName = request.getParameter("userName");
		String passwd = request.getParameter("passwd");		
		String encodePasswordString = Encode.MD5Encode(passwd);
		LoginManager lm = new LoginManager();
		User user = new User();
		user.setUserName(userName);
		user.setPasswd(encodePasswordString);
		
		int result = lm.isValid(user);
			if(result==1){//success	
				session.setAttribute("userName", user.getUserName());
				request.getRequestDispatcher("index.jsp").forward(request, response);
			
			}else{//fail
				request.setAttribute("loginRes","N");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String logType = (String)request.getParameter("logType");
		if(logType.equals("user")){
			request.getSession().removeAttribute("userName");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else if(logType.equals("shop")){
			request.getSession().removeAttribute("shopName");
			request.getRequestDispatcher("login_shop.jsp").forward(request, response);
		}
		
		
	}

}
