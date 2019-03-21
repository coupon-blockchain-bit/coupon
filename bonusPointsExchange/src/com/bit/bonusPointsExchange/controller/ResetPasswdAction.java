package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.bean.User;
import com.bit.bonusPointsExchange.manager.ShopManager;
import com.bit.bonusPointsExchange.manager.UserManager;

/**
 * @author gmx
 * 重新设置密码（忘记密码后）
 */
public class ResetPasswdAction extends Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String name = (String)request.getParameter("methodCode");
		if(name.equals("resetPasswd_user")){
			this.resetPasswdByUser(request,response);
		}else if(name.equals("resetPasswd_shop")){
			this.resetPasswdByShop(request,response);
		}		
          
     
	}
	public void resetPasswdByUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String userName = request.getParameter("userName");  
        String newPassword = request.getParameter("newPassword");  
        UserManager um = new UserManager();
        User user = new User();
        user.setUserName(userName);
        user.setPasswd(newPassword);
        int result = um.alterUserPasswd(user);
        if(result ==1){
        	request.setAttribute("resetPasswdMeg", "success");
            request.getRequestDispatcher("login.jsp").forward(request, response);                  
         }else{
        	request.setAttribute("resetPasswdMeg", "fail");
            request.getRequestDispatcher("retrievePassword_1.jsp").forward(request, response);  
        }
         
	}
	public void resetPasswdByShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String shopName = request.getParameter("userName");  
        String newPassword = request.getParameter("newPassword");     
        ShopManager sm = new ShopManager();
        Shop shop = new Shop();
        shop.setShopName(shopName);
        shop.setPassword(newPassword);            
        int result = sm.alterShopPasswd(shop);
        if(result ==1){
        	request.setAttribute("resetPasswdMeg", "success");
            request.getRequestDispatcher("login_shop.jsp").forward(request, response);     
        }else{ 
        	request.setAttribute("resetPasswdMeg", "fail");
        	request.getRequestDispatcher("retrievePassword_1.jsp").forward(request, response);  
        }
         
	}

}
