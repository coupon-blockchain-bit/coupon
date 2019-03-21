package com.bit.bonusPointsExchange.bean;

public class Transfer {
	private int pointID;
	private int status;
	private int point;
	public Transfer(int pointID, int status, int point) {
		super();
		this.pointID = pointID;
		this.status = status;
		this.point = point;
	}
	public int getPointID() {
		return pointID;
	}
	public void setPointID(int pointID) {
		this.pointID = pointID;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	
}
