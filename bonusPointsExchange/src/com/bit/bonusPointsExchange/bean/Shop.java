package com.bit.bonusPointsExchange.bean;

/**
 * 商家
 */
public class Shop {
	private String shopName;
	private String password;
	private String email;
	private String imgUrl;
	private String number;
	private String shopProperty;
	private String telephone;
	private String shopDec;//商家简介
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Shop() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPassword() {
		return password;
	}
	public Shop(String shopName, String password, String email, String imgUrl,
			String number, String shopProperty, String telephone, String shopDec) {
		super();
		this.shopName = shopName;
		this.password = password;
		this.email = email;
		this.imgUrl = imgUrl;
		this.number = number;
		this.shopProperty = shopProperty;
		this.telephone = telephone;
		this.shopDec = shopDec;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getShopProperty() {
		return shopProperty;
	}
	public void setShopProperty(String shopProperty) {
		this.shopProperty = shopProperty;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getShopDec() {
		return shopDec;
	}
	public void setShopDec(String shopDec) {
		this.shopDec = shopDec;
	}

}
