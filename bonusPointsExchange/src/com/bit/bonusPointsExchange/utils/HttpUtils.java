package com.bit.bonusPointsExchange.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.bit.bonusPointsExchange.json.GetJsonStr;
import com.bit.bonusPointsExchange.json.PostJsonStr;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpUtils {
	
	private  String urlPath = "Http://148.100.4.66:32800/chaincode";//blockchain的url
	private  HttpURLConnection conn;
	
	public  String getHttpConnection(){	
		String conRes="开启请求连接成功";
		try {			
			URL httpUrl = new URL(urlPath);//创建url资源
			conn = (HttpURLConnection) httpUrl.openConnection();//建立http连接
			conn.setDoOutput(true);//设置允许输出
			conn.setDoInput(true);//设置允许输入
			conn.setConnectTimeout(30000);
			conn.setRequestMethod("POST");//设置传递方式
			conn.setUseCaches(false);//设置不用缓存
			conn.setRequestProperty("Connection", "keep-Alive");//设置维持长连接
			conn.setRequestProperty("Charset", "UTF-8");//设置文件字符集
			conn.setRequestProperty("contentTypr", "application/json");//设置文件类型		
			conn.connect();//开始连接请求
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			conRes="开启请求连接失败";
			System.out.println("连接请求开启失败，请检查网络");
		}
		return conRes;
		
	}
	
	public GetJsonStr postJsonToBlockChain(String str){//向blockchain发送json串，并接收blockchain传来的json消息
		GetJsonStr getJsonStr = null;
		try {
			OutputStream out = conn.getOutputStream();	
			JsonParser parser = new JsonParser();//json解析器
			JsonObject jobj = (JsonObject) parser.parse(str);
			Gson gjson = new Gson();
//			String str = gjson.toJson(postJsonStr);//将对象postJsonStr转换成Json字符串
			out.write(jobj.toString().getBytes());//写入请求的字符串  把JSON数据转换成String类型使用输出流向服务器写
			out.flush();
			out.close();
			if(conn.getResponseCode()==200){
				System.out.println("连接成功");
				InputStream in = conn.getInputStream();
				byte[] data1 = new byte[in.available()];
				in.read(data1);
				String result = new String(data1);
				getJsonStr = gjson.fromJson(result, GetJsonStr.class);//从Json字符串转化为到java实体的
				System.out.println("blockchain:"+result);
				
				in.close();
			}else{
				System.out.println("连接blockchain失败");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getJsonStr;
	}
	

	
}
