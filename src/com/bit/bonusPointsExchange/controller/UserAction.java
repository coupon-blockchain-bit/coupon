package com.bit.bonusPointsExchange.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bit.bonusPointsExchange.bean.User;
import com.bit.bonusPointsExchange.manager.UserManager;

/**
 * 用户模块
 * @author gmx
 *
 */
public class UserAction extends Action{

	UserManager um = new UserManager();
	User user = new User();
	String flag = "alterBefore";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub		
		
		String methodCode = request.getParameter("methodCode");
		if(methodCode.equals("alter_user_info")){			
			this.alterUserInfo(request, response);				
		}
		if(methodCode.equals("query_user_info")){
			this.queryUserInfo(request, response);
		}
		if(methodCode.equals("alter_user_passwd")){
			this.alterUserPasswd(request,response);
		}		
	}
	
	/**修改用户个人信息**/
	public void alterUserInfo(HttpServletRequest request, HttpServletResponse response){
		//String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String userName =(String)request.getSession().getAttribute("userName");
		String fullName = request.getParameter("fullName");
		String phone = request.getParameter("phone");		
		user.setUserName(userName);
		user.setEmail(email);
		user.setFullName(fullName);
		user.setPhone(phone);			
		int result = um.alterUserInfo(user);
		try {
			if(result>0){
				flag = "alterAfter";
				request.setAttribute("userChangeResult", "Y");
				this.queryUserInfo(request, response);
				
				//request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
			}else {	
				request.setAttribute("userChangeResult", "N");
				request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
			}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**查询用户信息**/
	public void queryUserInfo(HttpServletRequest request, HttpServletResponse response){	
		String userName=(String)request.getSession().getAttribute("userName");
		user = um.queryUserInfo(userName);	
		request.setAttribute("email", user.getEmail());
		request.setAttribute("fullName", user.getFullName());
		request.setAttribute("phone", user.getPhone());
		try {
			
			//if(flag.equals("alterBefore")){
			
			//request.setAttribute("userChangeResult", "Y");// 设置属性，表示修改信息成功，在前台获取进行提示操作
			request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
			//}else if(flag.equals("alterAfter")){
			//	request.setAttribute("userChangeResult", "Y");// 设置属性，表示修改信息成功，在前台获取进行提示操作
			//	request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
			//}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**修改用户密码 **/
	public void alterUserPasswd(HttpServletRequest request,HttpServletResponse response) {
		UserManager um = new UserManager();
		String userName = (String) request.getSession().getAttribute("userName");
		String oldPasswd = request.getParameter("oldPassword");
		String newPasswd = request.getParameter("newPassword");
		user = um.queryUserPasswd(userName);
		request.setAttribute("index", "2");
		if(user.getPasswd().equals(oldPasswd)){
			user = new User();
			user.setUserName(userName);
			user.setPasswd(newPasswd);
			int result = um.alterUserPasswd(user);
			try {
				if(result==1){					
					request.setAttribute("userChangePasswd", "Y");// 设置属性，表示修改信息成功，在前台获取进行提示操作
					request.getRequestDispatcher("login.jsp").forward(request, response);
					//this.queryUserInfo(request, response);
				}else{
					request.setAttribute("userChangePasswd", "N");
					request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
				}
			} catch (ServletException e) {
				// TODO Auto-generated catch block	
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			
			request.setAttribute("userChangePasswd", "errorPasswd");//输入的旧密码错误
			try {
				request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

}
