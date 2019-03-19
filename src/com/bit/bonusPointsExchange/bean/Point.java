package com.bit.bonusPointsExchange.bean;

public class Point {
	private int pointID;
	private String userName;
	private String shopName;
	private int platformPoint;
	private String bindtime;
	
	public int getPointID() {
		return pointID;
	}
	public void setPointID(int pointID) {
		this.pointID = pointID;
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
	public int getPlatformPoint() {
		return platformPoint;
	}
	public void setPlatformPoint(int platformPoint) {
		this.platformPoint = platformPoint;
	}
	public String getBindtime() {
		return bindtime;
	}
	public void setBindtime(String bindtime) {
		this.bindtime = bindtime;
	}
	
	
}
