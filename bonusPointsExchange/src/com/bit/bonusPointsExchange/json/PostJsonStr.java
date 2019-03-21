package com.bit.bonusPointsExchange.json;

import java.util.ArrayList;

public class PostJsonStr {//Ïòblockchain·¢ËÍµÄjson×Ö·û´®
	private String jsonrpc="2.0";
	private String method;
	private Params params;
	private int id;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Params getParams() {
		return params;
	}
	public void setParams(Params params) {
		this.params = params;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
