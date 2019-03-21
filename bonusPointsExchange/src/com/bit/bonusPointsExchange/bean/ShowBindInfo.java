package com.bit.bonusPointsExchange.bean;
//用户点击显示已绑定信息，查询出的信息相关信息
public class ShowBindInfo {
	private String shopName;//商家名称
	private String imgURL;//商家头像地址
	private String platformPoints;//用户在我们平台的积分
	public String getShopName() {
		return shopName;
	}
	public ShowBindInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getImgURL() {
		return imgURL;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	public String getPlatformPoints() {
		return platformPoints;
	}
	public void setPlatformPoints(String platformPoints) {
		this.platformPoints = platformPoints;
	}
	public ShowBindInfo(String shopName, String imgURL, String platformPoints) {
		super();
		this.shopName = shopName;
		this.imgURL = imgURL;
		this.platformPoints = platformPoints;
	}
	
	
}
