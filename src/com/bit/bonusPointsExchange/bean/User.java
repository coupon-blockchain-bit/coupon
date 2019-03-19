package com.bit.bonusPointsExchange.bean;

/**
 * 用户
 * @author gmx
 *
 */
public class User {
	
	private int userId;//编号
	private String userName;//账号
	private String passwd;//密码
	private String email;//邮箱
	private String fullName;//姓名
	private String phone;//电话
	private boolean activated;//是否激活
	private String randomCode;//随机码（激活账户与生成重设密码链接时使用）
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean getActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	public String getRandomCode() {
		return randomCode;
	}
	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}
	
	
	
	
}
