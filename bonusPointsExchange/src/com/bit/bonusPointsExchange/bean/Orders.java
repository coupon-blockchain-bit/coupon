package com.bit.bonusPointsExchange.bean;

import java.util.HashMap;
import java.util.Vector;
/*智能推荐用到*/

public class Orders {
	private int orderID;
	private int shopName;
	private int pointNum;
	private int wantedShop;
	private int wantedNum;
	private boolean isExist;
	private int realid;
	public HashMap<Integer,Vector<Integer>> links;//first is origanID  next is lastID list
	public Orders(int theID,int totalID,int shop,int point,int endShop,int endNum){
		realid=theID;
		shopName=shop;
		pointNum=point;
		wantedShop=endShop;
		wantedNum=endNum;
		orderID=totalID;
		links=new HashMap<Integer,Vector<Integer>>(20);
		isExist=true;
	}
	Orders(Orders some){//copy one order
		orderID=some.getOrderID();
		shopName=some.getShopName();
		pointNum=some.getPointNum();
		wantedShop=some.getWantedShop();
		wantedNum=some.getWantedNum();
		realid=some.getid();
		links=some.links;
	}
	public int getOrderID(){
		return orderID;
	}
	public int getShopName(){
		return shopName;
	}
	public int getPointNum(){
		return pointNum;
	}
	public int getWantedShop(){
		return wantedShop;
	}
	public int getWantedNum(){
		return wantedNum;
	}
	public boolean getExist(){
		return isExist;
	}
	public void setExist(){
		isExist=false;
	}
	public int getid(){
		return realid;
	}
	public boolean ensureNext(Orders next,int origanID){//make sure the next is available
		if(!next.getExist())
			return false;
		Vector<Integer> values =new Vector<Integer>();
		values=next.links.get(origanID);
		boolean flag=true;
		if(values!=null)
			for(Integer now:values){
				if(now==orderID)
				{
					flag=false;
					break;
				}
		}
		if(flag)
			next.addLast(orderID, origanID);
		return flag;
	}
	private void addLast(int lastID,int origanID){//生成organID的vector并且保证最后一个元素是自身
		Vector<Integer> values=links.get(origanID);
		if(values==null){
			values=new Vector<Integer>();
			values.add(new Integer(orderID));//添加队尾元素是自己
			links.put(origanID, values);
		}
		values.remove(new Integer(orderID));
		values.add(lastID);
		values.add(new Integer(orderID));
		return;
	}
}
