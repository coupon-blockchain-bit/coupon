package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.User;
import com.bit.bonusPointsExchange.manager.UserManager;
import com.bit.bonusPointsExchange.utils.GenerateLinkUtils;

/**
 * 注册时激活账号
 * @author gmx
 *
 */
public class ActivateAccountServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ActivateAccountServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String userName = request.getParameter("userName");  
        UserManager um = new UserManager();
        //UserDao userDao = UserDaoImpl.getInstance();  
        User user = um.queryUserByName(userName);// 得到要激活的帐户  
        user.setActivated(GenerateLinkUtils.verifyCheckcode(user, request));// 校验验证码是否和注册时发送的一致，以此设置是否激活该帐户  
        //userDao.updateUser(user);  
          
        request.getSession().setAttribute("user", user);  
          
        request.getRequestDispatcher("/accountActivate.jsp").forward(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
