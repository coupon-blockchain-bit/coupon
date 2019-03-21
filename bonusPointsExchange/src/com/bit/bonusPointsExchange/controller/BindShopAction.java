package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.bean.ShowBindInfo;
import com.bit.bonusPointsExchange.manager.BindShopManager;

/**
 * 用户绑定的商家
 */
public class BindShopAction extends Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String methodCode = request.getParameter("methodCode");
				
		if(methodCode.equals("find_bindedShops")){//查询用户的所有已绑定商家名 
			this.findBindedShops(request,response);
		}
	}
	
	/*查询用户的所有已绑定商家*/
	public void findBindedShops(HttpServletRequest request, HttpServletResponse response){
		String userName = (String) request.getSession().getAttribute("userName");
		BindShopManager bindShopManager = new BindShopManager();
		List<ShowBindInfo> shops = bindShopManager.bingShopInfo(userName);
		if(shops.isEmpty()){
			request.setAttribute("LaunchTranscationRes","unBindShop");
			try {
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			request.setAttribute("bindShops", shops);
			try {
				request.getRequestDispatcher("order.jsp").forward(request, response);
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
