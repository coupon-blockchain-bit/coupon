package com.bit.bonusPointsExchange.bean;

import java.util.LinkedList;
/*智能推荐用到*/

public class Shoporders {
	private String fromShop;
	private String toShop;
	private LinkedList<Integer> shopOrders=new LinkedList<Integer>();
	public Shoporders(String shop1,String wantedShop){
		fromShop=shop1;
		toShop=wantedShop;
	}
	public String getShop(){
		return fromShop;
	}
	public String getwantedShop(){
		return toShop;
	}
	public boolean addOrder(Orders one){
		if(shopOrders.add(one.getOrderID())){
			return true;
		}
		else
			return false;
	}
	public LinkedList<Integer> getShoporders(){
		return shopOrders;
	}
	public void deleteOrder(int orderNum){
		shopOrders.remove(orderNum);
		return;
	}
}
