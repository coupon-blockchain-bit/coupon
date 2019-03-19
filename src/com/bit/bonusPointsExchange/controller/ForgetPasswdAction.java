package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.bean.User;
import com.bit.bonusPointsExchange.manager.ShopManager;
import com.bit.bonusPointsExchange.manager.UserManager;
import com.bit.bonusPointsExchange.utils.EmailUtils;

/**
 * 忘记密码
 * @author gmx
 *
 */
public class ForgetPasswdAction extends Action{
	
	

	/*
	public void findPasswd(){
		Properties props = new Properties();//声明放置配置信息的配置对象
		props.setProperty("mail.transport.protocol", "smtp");//设置连接哪一台服务器
		props.setProperty("mail.smtp.auth", "true");//设置是否验证
		Session session = Session.getInstance(props);//获取session对象
		session.setDebug(true);//设置调试模式
		MimeMessage msg = new MimeMessage(session);//声明信息，该类是Message的实现类
		try {
			msg.setFrom(new InternetAddress(""));//设置发件人
			msg.setRecipient(RecipientType.TO, new InternetAddress(""));//设置收件人
			msg.setRecipient(RecipientType.CC, new InternetAddress("kdyzm@foxmail.com"));//设置抄送
	        msg.setRecipient(RecipientType.BCC, new InternetAddress("kdyzm@sina.cn"));//设置暗送
	        msg.setSubject("这是用java发送的邮件")//设置主题
	        msg.setContent("这是用java发送的邮件的内容","text/plain;charset=utf-8");//设置邮件内容
	        Transport.send(msg);
			
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		
		String name = (String)request.getParameter("methodCode");
		if(name.equals("forgetPasswd_user")){
			this.forgetPasswdByUser(request,response);
		}else if(name.equals("forgetPasswd_shop")){
			this.forgetPasswdByShop(request,response);
		}
		
    }  
		
		
	public void forgetPasswdByUser(HttpServletRequest request, HttpServletResponse response) {
		User user = new User();
		UserManager um = new UserManager();
		String userName  = request.getParameter("userName");
		String email = request.getParameter("email");
		user.setUserName(userName);
		user.setEmail(email);
		int result = um.queryUserByNameAndEmail(user);
			
		if(result==0){
			try {
				request.setAttribute("errorMsg", "您输入的账号或邮箱不存在！");  
				request.getRequestDispatcher("/retrievePassword_1.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       
		}else{
			// 发送重新设置密码的链接  
			EmailUtils.sendResetPasswordEmail(user);  
          
			request.setAttribute("sendMailMsg", "您的申请已提交成功，请查看您的"+user.getEmail()+"邮箱。");         
			try {
				request.getRequestDispatcher("forgetPasswdSuccess.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	}
	public void forgetPasswdByShop(HttpServletRequest request, HttpServletResponse response) {
		Shop shop = new Shop();
		ShopManager sm = new ShopManager();
		String shopName  = request.getParameter("userName");
		String email = request.getParameter("email");
		shop.setShopName(shopName);
		shop.setEmail(email);
		int result = sm.queryShopByNameAndEmail(shop);
	
		if(result==0){
			request.setAttribute("errorMsg", "您输入的账号或邮箱不存在！");  
			try {
				request.getRequestDispatcher("/retrievePassword_1.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

       
		}else{
			// 发送重新设置密码的链接  
			EmailUtils.sendResetPasswordEmail(shop);  
          
			request.setAttribute("sendMailMsg", "您的申请已提交成功，请查看您的"+shop.getEmail()+"邮箱。");         
			try {
				request.getRequestDispatcher("forgetPasswdSuccess.jsp").forward(request, response);
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
