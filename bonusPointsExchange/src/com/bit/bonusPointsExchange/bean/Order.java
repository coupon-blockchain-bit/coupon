package com.bit.bonusPointsExchange.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 * 订单
 * @author gmx
 */

public class Order {
	
	private int orderID;//订单号
	private int point;//交易积分
	private String userName;//积分所属商家名
	private String shopName;//积分所属用户名
	private String wantedShop;//期望兑换商家名
	private int wantedPoint;//期望兑换积分
	private String untilDate;//订单有效期
	private String exchangeUserName;//积分兑换用户名
	private String orderDate;//订单完成时间
	private int orderStatus;//订单状态
	private String shopLogo;//商家图标
	private String wantedShopLogo;//目标商家图标
	

	
	public String getShopLogo() {
		return shopLogo;
	}
	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}
	public String getWantedShopLogo() {
		return wantedShopLogo;
	}
	public void setWantedShopLogo(String wantedShopLogo) {
		this.wantedShopLogo = wantedShopLogo;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getWantedShop() {
		return wantedShop;
	}
	public void setWantedShop(String wantedShop) {
		this.wantedShop = wantedShop;
	}
	public int getWantedPoint() {
		return wantedPoint;
	}
	public void setWantedPoint(int wantedPoint) {
		this.wantedPoint = wantedPoint;
	}
	public String getUntilDate() {
		return untilDate;
	}
	public void setUntilDate(String untilDate) {
		this.untilDate = untilDate;
	}
	public String getExchangeUserName() {
		return exchangeUserName;
	}
	public void setExchangeUserName(String exchangeUserName) {
		this.exchangeUserName = exchangeUserName;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	

	
	
}

