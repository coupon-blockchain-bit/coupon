package com.bit.bonusPointsExchange.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.utils.DBUtils;

//商家登陆数据库相关操作
public class LoginShopManger {
	public int isValid(Shop shop){
		//查询数据库用户名和密码是否正确
		Connection conn=DBUtils.getConnection();
		int result = 0;					
		Statement stmt=null;
		//System.out.println(shop.getShopName());
		//System.out.println(shop.getPassword());
		try {
			String sql ="select *from shop where shopName='"+shop.getShopName()+"' and password='"+shop.getPassword()+"'";
			stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			//System.out.println(sql);
			
			if(rs.next())
				result = 1;  	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(null, stmt, conn);
		}
		//System.out.println(result);
		return result;
	}
	
	//根据商家名称查询商家的详细信息
	public Shop getShopInfo(String shopName){
		Connection conn=DBUtils.getConnection();
		Shop res = new Shop();					
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("select *from shop where shopName='"+shopName+"'");	
			if(rs.next()) {
				//将详细信息保存在Shop对象中，返回
				res.setShopName(rs.getString("shopName"));
				res.setImgUrl(rs.getString("imageURL"));
				res.setEmail(rs.getString("email"));
				res.setNumber(rs.getString("number"));
				res.setShopProperty(rs.getString("shopProperty"));
				res.setShopDec(rs.getString("shopDec"));
				res.setTelephone(rs.getString("telephone"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(null, stmt, conn);
		}
		return res;
	}
}
