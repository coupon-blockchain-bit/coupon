package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.bean.Point;
import com.bit.bonusPointsExchange.bean.ShowBindInfo;
import com.bit.bonusPointsExchange.json.GetJsonStr;
import com.bit.bonusPointsExchange.manager.BindShopManager;
import com.bit.bonusPointsExchange.manager.OrderManager;
import com.bit.bonusPointsExchange.manager.PointManager;
import com.bit.bonusPointsExchange.manager.QueryOrderManager;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;
import com.bit.bonusPointsExchange.service.OrderService;
import com.bit.bonusPointsExchange.utils.HttpUtils;
import com.bit.bonusPointsExchange.utils.TimeUtils;

/**
 * 订单模块
 * @author gmx
 *
 */
public class OrderAction extends Action{
	
	private int orderStatus_unfinished_valid = 0;//0代表未完成且未超过有效期（有效）
	private int orderStatus_finished = 1;//1代表完成
	private int orderStatus_cancel_invalid=2;//2代表撤销或超过有效期（无效)
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String methodCode = request.getParameter("methodCode");
		String sortMeans = request.getParameter("selectSort");
		if(methodCode.equals("release_order")){//发布订单
			this.releaseOrder(request,response);
		}else if(methodCode.equals("findAllOrder")){//查询所有订单
			if(sortMeans.equals("积分优先")){
				this.findAllOrderPriorityPoint(request, response);
			}else if(sortMeans.equals("比率优先")){
				this.findAllOrderByRate(request,response);
			}else if(sortMeans.equals("时效优先")){
				this.findAllOrderByUntilDate(request,response);
			}else if(sortMeans.equals("null")){
				request.setAttribute("index", 3);
				request.getRequestDispatcher("order.jsp").forward(request, response);
			}
		}else if(methodCode.equals("finsh_order")){//进行积分兑换，完成订单,双方交易
			this.finishOrderByTwo(request, response);
		}else if(methodCode.equals("finsh_order_muliti")){//多方交易
			this.finshOrderByMuliti(request, response);
		}
	}
	
	/*发布订单*/
	public void releaseOrder(HttpServletRequest request, HttpServletResponse response){
		String shopName = request.getParameter("shopName");
		int point = Integer.parseInt(request.getParameter("points"));
		String wantedShop = request.getParameter("wantedShop");
		int wantedPoint = Integer.parseInt(request.getParameter("wantedPoint"));
		String userName = (String) request.getSession().getAttribute("userName");
		String untilDate = request.getParameter("utilDate2");
		try {
			OrderManager om = new OrderManager();
			Order order = new Order();
			order.setShopName(shopName);
			order.setPoint(point);
			order.setWantedShop(wantedShop);
			order.setWantedPoint(wantedPoint);
			order.setUserName(userName);
			order.setOrderStatus(orderStatus_unfinished_valid);//订单未完成且有效
			order.setUntilDate(untilDate);
			
			int result = om.addOrder(order);
			if(result>0){
				BindShopManager bindShopManager = new BindShopManager();
				List<ShowBindInfo> shops = bindShopManager.bingShopInfo(userName);
				request.setAttribute("bindShops", shops);
				request.setAttribute("releaseOrderResult", "Y");
				request.getRequestDispatcher("order.jsp").forward(request, response);
		
			}else{
				request.setAttribute("releaseOrderResult", "N");
				request.getRequestDispatcher("order.jsp").forward(request, response);
			}
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*积分优先查询所有订单*/
	public void findAllOrderPriorityPoint(HttpServletRequest request, HttpServletResponse response){//积分优先方式查找所有订单
		String userName = (String)request.getSession().getAttribute("userName");
		String shopName = request.getParameter("shop");
		String wantedShop = request.getParameter("targetShop");
		String point = request.getParameter("point");
		int points= Integer.parseInt(point);//积分数量
		String wantedPoint = request.getParameter("wantedPoint2");
		int wantedPoints = Integer.parseInt(wantedPoint);//目标积分数量
		OrderManager om = new OrderManager();
		Order order = new Order();
		order.setShopName(shopName);
		order.setPoint(points);
		order.setWantedShop(wantedShop);
		order.setWantedPoint(wantedPoints);
		List<Order> orders = om.findAllOrderPriorityPoint(userName,order);
		request.setAttribute("orders", orders);
		request.setAttribute("index", "3");
		request.setAttribute("findRes", "true");
		request.setAttribute("shop", shopName);
		request.setAttribute("wantedShop", wantedShop);
		request.setAttribute("point", point);
		request.setAttribute("wantedPoint", wantedPoint);
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

	//比率优先
	public void	 findAllOrderByRate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String shopName = request.getParameter("shop");
		String wantedShop = request.getParameter("targetShop");
		String userName = (String)request.getSession().getAttribute("userName");
		String point = request.getParameter("point");
		int points= Integer.parseInt(point);//积分数量
		String wantedPoint = request.getParameter("wantedPoint2");
		int wantedPoints = Integer.parseInt(wantedPoint);//目标积分数量
		//查询数据库，调用按比率查询函数
		QueryOrderManager manager = new QueryOrderManager();
		List<Order> list = manager.findAllOrderByRate(shopName, wantedShop,userName,points,wantedPoints);
		request.setAttribute("AllOrderByRate", list);
		request.setAttribute("index", "3");
		request.setAttribute("selectID", "2");//设置界面上显示第几个select
		request.setAttribute("shop", shopName);
		request.setAttribute("wantedShop", wantedShop);
		request.setAttribute("point", point);
		request.setAttribute("wantedPoint", wantedPoint);
		request.getRequestDispatcher("order.jsp").forward(request, response);
	}
		
	//时效优先
	public void	 findAllOrderByUntilDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String shopName = request.getParameter("shop");
		String wantedShop = request.getParameter("targetShop");
		String userName = (String)request.getSession().getAttribute("userName");
		String point = request.getParameter("point");
		int points= Integer.parseInt(point);//积分数量
		String wantedPoint = request.getParameter("wantedPoint2");
		int wantedPoints = Integer.parseInt(wantedPoint);//目标积分数量
		//查询数据库，调用按时效优先查询函数
		QueryOrderManager manager = new QueryOrderManager();
		List<Order> list = manager.findAllOrderByUntilDate(shopName, wantedShop,userName,points,wantedPoints);
		request.setAttribute("AllOrderByUntilDate", list);
		request.setAttribute("index", "3");
		request.setAttribute("selectID", "3");//设置界面上显示第几个select
		request.setAttribute("shop", shopName);
		request.setAttribute("wantedShop", wantedShop);
		request.setAttribute("point", point);
		request.setAttribute("wantedPoint", wantedPoint);
		request.getRequestDispatcher("order.jsp").forward(request, response);
	}
	
	/*兑换积分,完成订单*/  /*检查是否在商家注册了账户*/ /*双方交易*/
	public void finishOrderByTwo(HttpServletRequest request, HttpServletResponse response){	
		int orderID = Integer.parseInt(request.getParameter("orderID"));				
		String exchangeUserName = (String) request.getSession().getAttribute("userName");							
//		String shop = (String)request.getParameter("shop");
//		String wantedShop = (String)request.getParameter("targetShop");
//		int point = Integer.parseInt(request.getParameter("point"));
//		int wantedPoint = Integer.parseInt(request.getParameter("wantedPoint2"));
		OrderService os = new OrderService();
		String finishOrderRes=os.finishOrder(exchangeUserName, orderID);
		 System.out.print(finishOrderRes);
		request.setAttribute("index", 3);	
//		request.setAttribute("shop", shop);
//		request.setAttribute("wantedShop", wantedShop);
//		request.setAttribute("point", point);
//		request.setAttribute("wantedPoint", wantedPoint);
		request.setAttribute("finishOrderRes", finishOrderRes);
		
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
	
	public void finshOrderByMuliti(HttpServletRequest request, HttpServletResponse response){
		OrderService os = new OrderService();
		String finishOrderRes=null;
		OrderManager om = new OrderManager();
		BindShopManager bsm = new BindShopManager();
		String str = request.getParameter("recOrderIds");
		str=str.substring(1, str.length()-1);
		String recOrderIds[] = str.split(",");	
		String exchangeUserName = (String)request.getSession().getAttribute("userName");
		String recBindShops = "商家：";//用户在智能推荐过程中需要绑定的中间商家
		int recBindShopNum = 0;
		for(int i = 0; i < recOrderIds.length; i++) {//查询用户是否绑定只能推荐的链式订单中的中间商家
			int recOrderId=Integer.parseInt(recOrderIds[i].trim());
			Order order = om.findOrderByID(recOrderId);
			boolean isBindRes = bsm.isBindThisShop(exchangeUserName, order.getShopName());
			if(isBindRes==false){
				recBindShops=recBindShops+order.getShopName()+",";
				System.out.print(recBindShops);
				//recBindShops.add(order.getShopName());
				recBindShopNum++;
			}
		}
		if(recBindShopNum!=0){//如果用户有中间商家为绑定返回页面提示信息
			request.setAttribute("recBindShops",recBindShops);
			request.setAttribute("finishOrderRes", "需要绑定中间商家");
		}else{
		
			for(int i = 0; i < recOrderIds.length; i++) {
				int recOrderId=Integer.parseInt(recOrderIds[i].trim());
				finishOrderRes = os.finishOrder(exchangeUserName, recOrderId);
				if(!finishOrderRes.equals("积分兑换成功！")){
					break;
				}
					System.out.println(finishOrderRes);
			}
			System.out.println("end"+finishOrderRes);		
			request.setAttribute("finishOrderRes", finishOrderRes);
		}
		try {
			request.getRequestDispatcher("recommend.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
