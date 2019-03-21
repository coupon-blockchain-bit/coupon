package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.bean.Orders;
import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.bean.Shoporders;
import com.bit.bonusPointsExchange.manager.LoginShopManger;
import com.bit.bonusPointsExchange.manager.OrderManager;
import com.bit.bonusPointsExchange.manager.ShopManager;


public class RecommendAction extends Action{
	
	private Shoporders[][]allOrders=new Shoporders[10][10]; 
	private HashMap<String,Integer> allShops=new HashMap<String,Integer>();//所有注册商家
	private String[] shopNum=new String[10];
	private int shops=0;
	private int orders=0;
	private Orders orderList[]=new Orders[20];

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String shopName = request.getParameter("shopName");
		shopName = URLDecoder.decode(shopName, "UTF-8");
		String point = request.getParameter("point");
		String wantedShop = request.getParameter("targetShop");
		wantedShop = URLDecoder.decode(wantedShop, "UTF-8");
		String wantedPoint = request.getParameter("wantedPoint");
		String userName = (String)request.getSession().getAttribute("userName");
		ShopManager sm= new ShopManager();
		OrderManager om = new OrderManager();
		LoginShopManger lsm = new LoginShopManger();
		List<Order> orders  = om.findAllOrder(userName);
		String[] shops=sm.findAllShop();
		this.getShops(shops);
		Vector<Vector<Integer>> recOrderIdList = new Vector<Vector<Integer>>();
		Shop recShop = lsm.getShopInfo(shopName);
		Shop recWantShop= lsm.getShopInfo(wantedShop);
		
		for(int i=0;i<orders.size();i++){
			this.setOrders(String.valueOf(orders.get(i).getOrderID()),orders.get(i).getShopName(), orders.get(i).getWantedShop(),String.valueOf(orders.get(i).getPoint()), String.valueOf(orders.get(i).getWantedPoint()));			
		}
		recOrderIdList=this.setOrders(shopName,wantedShop,point,wantedPoint);
		//List<Vector<Integer>> order = new ArrayList<Vector<int[]>>();
		int recPoint=0;
		int recWantedPoint=0;
		List<Integer> recPoints = new ArrayList<Integer>();
		List<Integer> recWantedPoints = new ArrayList<Integer>();
		for(int j=0;j<recOrderIdList.size();j++){
			Order order1=om.findOrderByID(recOrderIdList.get(j).get(0));
			recPoint=order1.getWantedPoint();
			recPoints.add(recPoint);
			Order order2=om.findOrderByID(recOrderIdList.get(j).get(recOrderIdList.size()));
			recWantedPoint=order2.getPoint();
			recWantedPoints.add(recWantedPoint);
			System.out.println(recOrderIdList.get(j));
		}
		//om.findOrderByID(recOrderIdList.get(0).get(0));
		request.setAttribute("recOrderIdList", recOrderIdList);
		request.setAttribute("recShop", recShop);
		request.setAttribute("recWantShop", recWantShop);
		request.setAttribute("recPoints", recPoints);
		request.setAttribute("recWantedPoints", recWantedPoints);
		
		request.getRequestDispatcher("recommend.jsp").forward(request, response);

	}


	public void getShops(String[] s){
		for(String shopName: s){
			if(shopName==null)
				break;
			setShops(shopName);
		}
	}
	private void setShops(String shopName){
		allShops.put(shopName, shops);
		shopNum[shops]=new String(shopName);
		setShopOrders();
		shops++;
		return;
	}
	public Vector<Vector<Integer>> setOrders(String fromShop,String toShop,String fromPoints,String toPoints){
		return setOrders("0",fromShop,toShop,fromPoints,toPoints);
	}
	public Vector<Vector<Integer>> setOrders(String realID,String fromShop,String toShop,String fromPoints,String toPoints){
		Queue <Integer>find=new LinkedList<Integer>();//寻找订单的队列
		Orders newOrder=null;
		Vector <Vector<Integer>>alltrade=new Vector <Vector<Integer>>();
//		int realID;
		try{
//			System.out.println("请输入兑换商家名称:");
//			fromShop=br.readLine();
//			System.out.println("请输入兑换积分数量:");
//			fromPoints=br.readLine();
//			System.out.println("请输入期望兑换商家名称:");
//			toShop=br.readLine();
//			System.out.println("请输入期望兑换积分数量:");
//			toPoints=br.readLine();
//			System.out.println("请输入订单ID：");
//			realID=Integer.valueOf(br.readLine()).intValue();
			newOrder=new Orders(Integer.parseInt(realID),orders,allShops.get(fromShop),Integer.parseInt(fromPoints),allShops.get(toShop),Integer.parseInt(toPoints));
			find.add(orders);
			orderList[orders]=newOrder;
			allOrders[allShops.get(fromShop)][allShops.get(toShop)].addOrder(newOrder);	
		}catch(NumberFormatException e){
			System.out.println("NumberFormatException Error!\n");
		}
//		if(!realID.equals("0")){
//			orders++;
//			return null;
//		}
		while(!find.isEmpty()){
			Orders queueOrder=orderList[find.poll()];
			Vector<Integer> theNext=findNext(queueOrder.getWantedShop(),queueOrder);
			for(int ano:theNext){
				find.add(ano);
			}
		}
		if(newOrder.links==null||!newOrder.links.containsKey(orders)){
			System.out.println("There is no one aviable path!\n");
		}else{
			Vector <Integer>trade=new Vector<Integer>();
			int size=orderList[orders].links.get(orders).size();
			size--;
			while(size>0)
			{
				trade=findTrade(orders);
				if(trade==null)
					break;
				int tradeSize=trade.size();
				for(int n=0;n<tradeSize;n++){
					int temp=trade.get(n);
					trade.set(n, orderList[temp].getid());
				}
				trade.removeElementAt(0);
				for(int n:trade)
				{
					System.out.print(n+" ");
				}
				System.out.println();
				alltrade.addElement(trade);
				size--;
			}
		}
		orders++;
		return alltrade;
	}
	private void setShopOrders(){
		int x=0;
		for(;x<shops;x++)
			allOrders[x][shops]=new Shoporders(shopNum[x],shopNum[shops]);
		for(x=0;x<shops;x++)
			allOrders[shops][x]=new Shoporders(shopNum[shops],shopNum[x]);
		allOrders[shops][shops]=null;
		return;
	}
	private Vector<Integer>findNext(int shopID,Orders one){//从商家里面寻找符合条件的订单
		Vector<Integer>nextOrders=new Vector<Integer>();
		for(int x=0;x<shops;x++){
			if(x==shopID)
				continue;
			Vector<Integer> temp=new Vector<Integer>();
			temp=findOrder(shopID,x,one.getWantedNum(),one.getid());
			for(int ano:temp){
				if(one.ensureNext(orderList[ano], orders))
					nextOrders.addElement(ano);
			}
		}
		return nextOrders;
	}
	private Vector<Integer> findOrder(int fromS,int toS,int points,int lastid){//根据目的商家和期望商家获得下一个订单的列表
		Vector<Integer> backOrders=new Vector<Integer>();
		int listNum=0;
		LinkedList<Integer>shopOrders=allOrders[fromS][toS].getShoporders();
		int now;
		while(listNum<shopOrders.size()){
			now=shopOrders.get(listNum++);
			if(now==orders&&orderList[now].getPointNum()*0.9<points&&orderList[now].getPointNum()*1.1>points){
				backOrders.addElement(now);
			}else if(lastid==0&&orderList[now].getPointNum()>points*0.9&&orderList[now].getPointNum()>points*1.1){
				backOrders.addElement(now);
			}
			else if(orderList[now].getPointNum()==points){
				backOrders.addElement(now);
			}

		}
		return backOrders;
	}
	private Vector<Integer>findTrade(int nowOrder){//广搜结束利用深搜展示订单
		if(orderList[nowOrder].links.get(orders).size()==0)
			return null;
		Vector<Integer> trade=new Vector<Integer>();
		int nextOrder=orderList[nowOrder].links.get(orders).elementAt(0);
		if(nextOrder==orders){
			orderList[nowOrder].links.get(orders).remove(0);
			trade.addElement(nowOrder);
			orderList[nowOrder].links.get(orders).add(nextOrder);
 		}else if(nextOrder==nowOrder){
			trade=null;
			orderList[nowOrder].links.get(orders).remove(0);
			orderList[nowOrder].links.get(orders).add(nowOrder);
		}else{
			Vector<Integer> nextTrade=findTrade(nextOrder);
			if(nextTrade==null){
				int theNext=orderList[nowOrder].links.get(orders).remove(0);
				trade=findTrade(nowOrder);
 				orderList[nowOrder].links.get(orders).add(theNext);
			}else if(nextTrade.size()!=0){
				trade.addElement(nowOrder);
				for(int ids:nextTrade)
					trade.addElement(ids);
			}
		}
		return trade;
	}
	public boolean deleteOrders(int[] finishedTrade){//所有的已经交易的订单不能被搜寻
		boolean flag=true;
		for(int x:finishedTrade){
			if(!orderList[x].getExist())
			{
				flag=false;
				break;
			}else{
				orderList[x].setExist();
				allOrders[orderList[x].getShopName()][orderList[x].getWantedShop()].deleteOrder(x);
			}
		}
		return flag;
	}


}
